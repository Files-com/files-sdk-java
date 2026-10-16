package com.files.net;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;

// The checkpoint store on Windows, held through handles of the Windows API (see Win32), as a
// download's stage is (WindowsDownloadStage). A new directory is created with a protected ACL that
// grants the user full control, inherited by what is created in it, and no one anything else. The
// directory, new or not, is opened as itself, not a link it may be, held open so that no one else can
// rename or delete it, and checked through its handle: a directory, the user's own, with exactly
// that ACL, on a volume that keeps ACLs. A record is created with a protected ACL of its own granting
// the user full control, and opened with no sharing at all: while a run holds it, no one else can
// open, rename or delete it, which is the run's lock. It is checked through its handle the same way.
//
// Records are written in place and made durable with FlushFileBuffers; Windows offers no way to
// make a directory's entries durable, so a record created just before a crash may not last, and its
// transfer then starts over (see CheckpointStore.Record.forceName).
//
// The SDK's own tests run on macOS and Linux: this store's Windows behavior has not been run
// natively here.
final class WindowsCheckpointStore extends CheckpointStore {
  private static final int CHUNK_BYTES = 64 * 1024;

  private final Pointer directoryHandle;
  private final Memory owner;
  private final String sid;

  private WindowsCheckpointStore(Path directory, Pointer directoryHandle, Memory owner, String sid) {
    super(directory);
    this.directoryHandle = directoryHandle;
    this.owner = owner;
    this.sid = sid;
  }

  static CheckpointStore open(Path directory) throws IOException {
    Win32.requireAvailable();
    Memory owner = Win32.tokenOwner();
    Pointer handle = null;
    try {
      String sid = Win32.sidString(owner.getPointer(0));
      handle = openDirectory(directory);
      if (handle == null) {
        create(directory, "O:" + sid + "D:P(A;OICI;FA;;;" + sid + ")");
        handle = openDirectory(directory);
        if (handle == null) {
          throw Win32.exception(directory.toString(), Win32.lastError());
        }
      }
      WindowsDownloadStage.requirePersistentAcls(directory);
      if (!WindowsDownloadStage.isPrivateDirectory(handle, owner.getPointer(0))) {
        throw untrusted(directory, "it is not a directory of this user's whose protected ACL grants only this user full control");
      }
      return new WindowsCheckpointStore(directory, handle, owner, sid);
    } catch (IOException | RuntimeException | Error e) {
      if (handle != null) {
        Win32.Kernel32.CloseHandle(handle);
      }
      owner.close();
      throw e;
    }
  }

  // The directory, opened as itself rather than what a link at its name leads to, shared for reading
  // and writing but not deletion; null when it does not exist.
  private static Pointer openDirectory(Path directory) throws IOException {
    Pointer handle = Win32.Kernel32.CreateFileW(Win32.path(directory),
        Win32.FILE_LIST_DIRECTORY | Win32.FILE_READ_ATTRIBUTES | Win32.READ_CONTROL | Win32.SYNCHRONIZE,
        Win32.FILE_SHARE_READ | Win32.FILE_SHARE_WRITE, null, Win32.OPEN_EXISTING,
        Win32.FILE_FLAG_BACKUP_SEMANTICS | Win32.FILE_FLAG_OPEN_REPARSE_POINT, null);
    if (Win32.isInvalid(handle)) {
      int error = Win32.lastError();
      if (error == Win32.ERROR_FILE_NOT_FOUND) {
        return null;
      }
      throw Win32.exception(directory.toString(), error);
    }
    return handle;
  }

  private static void create(Path directory, String sddl) throws IOException {
    Pointer descriptor = Win32.securityDescriptor(sddl);
    try (Memory attributes = Win32.securityAttributes(descriptor)) {
      if (!Win32.Kernel32.CreateDirectoryW(Win32.path(directory), attributes) && Win32.lastError() != Win32.ERROR_ALREADY_EXISTS) {
        throw Win32.exception(directory.toString(), Win32.lastError());
      }
    } finally {
      Win32.Kernel32.LocalFree(descriptor);
    }
  }

  @Override
  Record lock(String name) throws IOException {
    Path file = directory.resolve(name);
    Pointer descriptor = Win32.securityDescriptor("O:" + sid + "D:P(A;;FA;;;" + sid + ")");
    Pointer handle;
    // CreateFileW's error, read at once: the cleanup below calls the Windows API again, which may replace it.
    int error;
    try (Memory attributes = Win32.securityAttributes(descriptor)) {
      handle = Win32.Kernel32.CreateFileW(Win32.path(file),
          Win32.GENERIC_READ | Win32.GENERIC_WRITE | Win32.DELETE | Win32.READ_CONTROL | Win32.FILE_READ_ATTRIBUTES, 0, attributes,
          Win32.OPEN_ALWAYS, Win32.FILE_ATTRIBUTE_NORMAL | Win32.FILE_FLAG_OPEN_REPARSE_POINT, null);
      error = Win32.lastError();
    } finally {
      Win32.Kernel32.LocalFree(descriptor);
    }
    if (Win32.isInvalid(handle)) {
      if (error == Win32.ERROR_SHARING_VIOLATION || error == Win32.ERROR_ACCESS_DENIED) {
        // Held by another run, or being deleted by one.
        throw inUse(directory, name);
      }
      throw Win32.exception(file.toString(), error);
    }
    try {
      if (!isPrivateFile(handle, owner.getPointer(0))) {
        throw untrusted(directory, "its record " + name + " is not a file of this user's whose protected ACL grants only this user full"
            + " control");
      }
      return new WindowsRecord(name, handle);
    } catch (IOException | RuntimeException | Error e) {
      Win32.Kernel32.CloseHandle(handle);
      throw e;
    }
  }

  // Whether the handle is of a file, not a directory or a link, owned by owner, whose protected ACL
  // grants owner full control and nothing else.
  static boolean isPrivateFile(Pointer handle, Pointer owner) throws IOException {
    try (Memory tag = new Memory(8)) {
      if (!Win32.Kernel32.GetFileInformationByHandleEx(handle, Win32.FILE_ATTRIBUTE_TAG_INFO, tag, 8)) {
        throw Win32.exception(null, Win32.lastError());
      }
      int attributes = tag.getInt(0);
      if ((attributes & Win32.FILE_ATTRIBUTE_DIRECTORY) != 0 || (attributes & Win32.FILE_ATTRIBUTE_REPARSE_POINT) != 0) {
        return false;
      }
    }
    try (Memory ownerOut = new Memory(Native.POINTER_SIZE);
         Memory daclOut = new Memory(Native.POINTER_SIZE);
         Memory descriptorOut = new Memory(Native.POINTER_SIZE)) {
      int error = Win32.Advapi32.GetSecurityInfo(handle, Win32.SE_FILE_OBJECT,
          Win32.OWNER_SECURITY_INFORMATION | Win32.DACL_SECURITY_INFORMATION, ownerOut, null, daclOut, null, descriptorOut);
      if (error != 0) {
        throw Win32.exception(null, error);
      }
      Pointer descriptor = descriptorOut.getPointer(0);
      try (Memory control = new Memory(2); Memory revision = new Memory(4)) {
        if (!Win32.Advapi32.EqualSid(ownerOut.getPointer(0), owner)
            || !Win32.Advapi32.GetSecurityDescriptorControl(descriptor, control, revision)
            || (control.getShort(0) & Win32.SE_DACL_PROTECTED) == 0) {
          return false;
        }
        // ACL { BYTE AclRevision; BYTE Sbz1; WORD AclSize; WORD AceCount; WORD Sbz2; }, then
        // ACCESS_ALLOWED_ACE { BYTE AceType; BYTE AceFlags; WORD AceSize; ACCESS_MASK Mask; SID }.
        Pointer dacl = daclOut.getPointer(0);
        if (dacl == null || (dacl.getShort(4) & 0xffff) != 1) {
          return false;
        }
        Pointer ace = dacl.share(8);
        return ace.getByte(0) == Win32.ACCESS_ALLOWED_ACE_TYPE && ace.getByte(1) == 0 && ace.getInt(4) == Win32.FILE_ALL_ACCESS
            && Win32.Advapi32.EqualSid(ace.share(8), owner);
      } finally {
        Win32.Kernel32.LocalFree(descriptor);
      }
    }
  }

  @Override
  public void close() {
    Win32.Kernel32.CloseHandle(directoryHandle);
    owner.close();
  }

  private final class WindowsRecord extends Record {
    private final String name;
    private final Pointer handle;
    private final Memory buffer = new Memory(CHUNK_BYTES);
    private final Memory count = new Memory(4);
    private boolean closed;

    WindowsRecord(String name, Pointer handle) {
      this.name = name;
      this.handle = handle;
    }

    // FILE_STANDARD_INFO { LARGE_INTEGER AllocationSize; LARGE_INTEGER EndOfFile; DWORD NumberOfLinks;
    // BOOLEAN DeletePending; BOOLEAN Directory; }.
    @Override
    long size() throws IOException {
      try (Memory information = new Memory(24)) {
        if (!Win32.Kernel32.GetFileInformationByHandleEx(handle, Win32.FILE_STANDARD_INFO, information, 24)) {
          throw failure();
        }
        return information.getLong(8);
      }
    }

    @Override
    byte[] read() throws IOException {
      long size = size();
      if (size > MAX_RECORD_BYTES) {
        throw new IOException("The checkpoint record holds " + size + " bytes, more than a record may hold");
      }
      seek(0, Win32.FILE_BEGIN);
      byte[] bytes = new byte[(int) size];
      for (int done = 0; done < bytes.length; ) {
        if (!Win32.Kernel32.ReadFile(handle, buffer, Math.min(CHUNK_BYTES, bytes.length - done), count, null)) {
          throw failure();
        }
        int read = count.getInt(0);
        if (read == 0) {
          return java.util.Arrays.copyOf(bytes, done);
        }
        buffer.read(0, bytes, done, read);
        done += read;
      }
      return bytes;
    }

    @Override
    void append(byte[] bytes) throws IOException {
      seek(0, Win32.FILE_END);
      for (int offset = 0; offset < bytes.length; ) {
        int length = Math.min(CHUNK_BYTES, bytes.length - offset);
        buffer.write(0, bytes, offset, length);
        for (int done = 0; done < length; ) {
          if (!Win32.Kernel32.WriteFile(handle, done == 0 ? buffer : buffer.share(done), length - done, count, null)) {
            throw failure();
          }
          int wrote = count.getInt(0);
          if (wrote <= 0) {
            throw new FileSystemException(directory.resolve(name).toString(), null, "No more bytes could be written");
          }
          done += wrote;
        }
        offset += length;
      }
    }

    @Override
    void truncate(long length) throws IOException {
      seek(length, Win32.FILE_BEGIN);
      if (!Win32.Kernel32.SetEndOfFile(handle)) {
        throw failure();
      }
    }

    @Override
    void force() throws IOException {
      if (!Win32.Kernel32.FlushFileBuffers(handle)) {
        throw failure();
      }
    }

    @Override
    void forceName() {
      // Windows cannot make a directory's entries durable.
    }

    // Held with no sharing: no one else can rename or delete it while it is held.
    @Override
    boolean isNamed() {
      return true;
    }

    @Override
    void delete() throws IOException {
      try {
        int error = Win32.markForDeletion(handle);
        if (error != 0) {
          throw Win32.exception(directory.resolve(name).toString(), error);
        }
      } finally {
        close();
      }
    }

    private void seek(long position, int method) throws IOException {
      if (!Win32.Kernel32.SetFilePointerEx(handle, position, null, method)) {
        throw failure();
      }
    }

    private IOException failure() {
      return Win32.exception(directory.resolve(name).toString(), Win32.lastError());
    }

    @Override
    public void close() {
      if (!closed) {
        closed = true;
        buffer.close();
        count.close();
        Win32.Kernel32.CloseHandle(handle);
      }
    }
  }
}
