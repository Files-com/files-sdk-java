package com.files.net;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.SecureRandom;

// The stage of a download on Windows, held through handles of the Windows API (see Win32).
//
// A directory or file created with an ACL of its own still merges in the ACEs it inherits from its
// parent, unless that ACL is protected, and Java can create neither with a protected ACL. So the
// download creates its directory, and then the file inside it, each with a protected ACL that
// grants its owner, the user, full control and no one anything else: from their creation, they
// inherit nothing. The file needs its own: Windows lets anyone who knows a file's path open it,
// without any access to the directories on the way, if the file's own ACL allows it. A volume that
// does not keep ACLs, where nothing can be kept private, is refused before anything is created.
//
// The directory is held open, so that no one else can rename or delete it, and checked through its
// handle: a directory, not a link, the user's own, with exactly the ACL the download gave it. The
// file is held open with no sharing at all, so that no one else can open it. Its bytes are written
// through that handle, which also renames it into place and deletes it: replacing a name, in the
// destination's directory or anywhere else, cannot change what is published or deleted.
//
// A published file that replaces another gets that file's ACL; it does not replace a file this user
// could not write, or that belongs to another user. A new file keeps its owner-only ACL.
final class WindowsDownloadStage extends DownloadStage {
  private static final int BUFFER_BYTES = 64 * 1024;
  private static final SecureRandom RANDOM = new SecureRandom();

  private final Path directory;
  private final Pointer directoryHandle;
  private final Pointer fileHandle;
  // The TOKEN_OWNER whose SID owns the directory and the file.
  private final Memory owner;
  // Native memory the bytes are copied to for writing, allocated once, and the count written.
  private final Memory buffer = new Memory(BUFFER_BYTES);
  private final Memory written = new Memory(4);
  private boolean published;

  private WindowsDownloadStage(Path directory, Pointer directoryHandle, Pointer fileHandle, Memory owner) {
    this.directory = directory;
    this.directoryHandle = directoryHandle;
    this.fileHandle = fileHandle;
    this.owner = owner;
  }

  static DownloadStage create(Path destination) throws IOException {
    Win32.requireAvailable();
    Path parent = destination.getParent();
    requirePersistentAcls(parent);
    Memory owner = Win32.tokenOwner();
    Pointer directoryHandle = null;
    boolean directoryChecked = false;
    Pointer fileHandle = null;
    try {
      String sid = Win32.sidString(owner.getPointer(0));
      Path directory = createDirectory(parent, directoryPrefix(destination), "O:" + sid + "D:P(A;OICI;FA;;;" + sid + ")");
      directoryHandle = Win32.Kernel32.CreateFileW(Win32.path(directory),
          Win32.FILE_LIST_DIRECTORY | Win32.FILE_READ_ATTRIBUTES | Win32.READ_CONTROL | Win32.DELETE | Win32.SYNCHRONIZE,
          Win32.FILE_SHARE_READ | Win32.FILE_SHARE_WRITE, null, Win32.OPEN_EXISTING,
          Win32.FILE_FLAG_BACKUP_SEMANTICS | Win32.FILE_FLAG_OPEN_REPARSE_POINT, null);
      if (Win32.isInvalid(directoryHandle)) {
        directoryHandle = null;
        int error = Win32.lastError();
        if (error == Win32.ERROR_FILE_NOT_FOUND || error == Win32.ERROR_PATH_NOT_FOUND) {
          throw replacedDirectory();
        }
        throw Win32.exception(directory.toString(), error);
      }
      if (!isPrivateDirectory(directoryHandle, owner.getPointer(0))) {
        throw replacedDirectory();
      }
      directoryChecked = true;
      fileHandle = createFile(directory.resolve(NAME), "O:" + sid + "D:P(A;;FA;;;" + sid + ")");
      return new WindowsDownloadStage(directory, directoryHandle, fileHandle, owner);
    } catch (IOException | RuntimeException | Error e) {
      if (fileHandle != null) {
        Win32.markForDeletion(fileHandle);
        Win32.Kernel32.CloseHandle(fileHandle);
      }
      if (directoryHandle != null) {
        // A directory that is not the one the download created is left alone.
        if (directoryChecked) {
          Win32.markForDeletion(directoryHandle);
        }
        Win32.Kernel32.CloseHandle(directoryHandle);
      }
      owner.close();
      throw e;
    }
  }

  // Reopens a retained stage (see DownloadStage.reopen), checked as create checks it: the directory,
  // opened as itself rather than what a link at its name leads to, of the recorded volume and file
  // ID, a directory of the user's whose protected ACL grants the user full control and no one else
  // anything; then the file, opened the same way and with no sharing, of the recorded volume and file
  // ID, the user's, with its protected owner-only ACL. Java gives no identity for Windows files, so it
  // is read from the handles. Not run natively in the SDK's own tests.
  static DownloadStage reopen(Path destination, Identity identity) throws IOException {
    Win32.requireAvailable();
    Path directory = destination.getParent().resolve(identity.directory);
    Memory owner = Win32.tokenOwner();
    Pointer directoryHandle = null;
    Pointer fileHandle = null;
    boolean returned = false;
    try {
      directoryHandle = Win32.Kernel32.CreateFileW(Win32.path(directory),
          Win32.FILE_LIST_DIRECTORY | Win32.FILE_READ_ATTRIBUTES | Win32.READ_CONTROL | Win32.DELETE | Win32.SYNCHRONIZE,
          Win32.FILE_SHARE_READ | Win32.FILE_SHARE_WRITE, null, Win32.OPEN_EXISTING,
          Win32.FILE_FLAG_BACKUP_SEMANTICS | Win32.FILE_FLAG_OPEN_REPARSE_POINT, null);
      if (Win32.isInvalid(directoryHandle)) {
        directoryHandle = null;
        int error = Win32.lastError();
        if (error == Win32.ERROR_FILE_NOT_FOUND || error == Win32.ERROR_PATH_NOT_FOUND) {
          return null;
        }
        throw Win32.exception(directory.toString(), error);
      }
      if (!isPrivateDirectory(directoryHandle, owner.getPointer(0)) || !identity.directoryId.equals(fileId(directoryHandle))) {
        throw changed(directory, "its directory is not the one the download created, or is no longer private");
      }
      fileHandle = Win32.Kernel32.CreateFileW(Win32.path(directory.resolve(NAME)),
          Win32.GENERIC_WRITE | Win32.DELETE | Win32.READ_CONTROL | Win32.WRITE_DAC | Win32.FILE_READ_ATTRIBUTES, 0, null, Win32.OPEN_EXISTING,
          Win32.FILE_ATTRIBUTE_NORMAL | Win32.FILE_FLAG_OPEN_REPARSE_POINT, null);
      if (Win32.isInvalid(fileHandle)) {
        fileHandle = null;
        int error = Win32.lastError();
        if (error == Win32.ERROR_FILE_NOT_FOUND) {
          // Published, or removed: the directory, the download's own, is removed if it is empty.
          Win32.markForDeletion(directoryHandle);
          return null;
        }
        throw Win32.exception(directory.resolve(NAME).toString(), error);
      }
      if (!WindowsCheckpointStore.isPrivateFile(fileHandle, owner.getPointer(0)) || !identity.fileId.equals(fileId(fileHandle))) {
        throw changed(directory, "its file is not the one the download wrote, or is no longer private");
      }
      WindowsDownloadStage stage = new WindowsDownloadStage(directory, directoryHandle, fileHandle, owner);
      returned = true;
      return stage;
    } finally {
      if (!returned) {
        if (fileHandle != null) {
          Win32.Kernel32.CloseHandle(fileHandle);
        }
        if (directoryHandle != null) {
          Win32.Kernel32.CloseHandle(directoryHandle);
        }
        owner.close();
      }
    }
  }

  // The volume and file ID of the regular file at path, opened as itself rather than what a link at
  // its name leads to (see DownloadStage.fileIdentity).
  static String fileIdentity(Path path) throws IOException {
    Win32.requireAvailable();
    Pointer handle = Win32.Kernel32.CreateFileW(Win32.path(path), Win32.FILE_READ_ATTRIBUTES,
        Win32.FILE_SHARE_READ | Win32.FILE_SHARE_WRITE | Win32.FILE_SHARE_DELETE, null, Win32.OPEN_EXISTING,
        Win32.FILE_FLAG_BACKUP_SEMANTICS | Win32.FILE_FLAG_OPEN_REPARSE_POINT, null);
    if (Win32.isInvalid(handle)) {
      int error = Win32.lastError();
      if (error == Win32.ERROR_FILE_NOT_FOUND || error == Win32.ERROR_PATH_NOT_FOUND) {
        return null;
      }
      throw Win32.exception(path.toString(), error);
    }
    try (Memory tag = new Memory(8)) {
      if (!Win32.Kernel32.GetFileInformationByHandleEx(handle, Win32.FILE_ATTRIBUTE_TAG_INFO, tag, 8)) {
        throw Win32.exception(path.toString(), Win32.lastError());
      }
      int attributes = tag.getInt(0);
      if ((attributes & Win32.FILE_ATTRIBUTE_DIRECTORY) != 0 || (attributes & Win32.FILE_ATTRIBUTE_REPARSE_POINT) != 0) {
        return null;
      }
      return fileId(handle);
    } finally {
      Win32.Kernel32.CloseHandle(handle);
    }
  }

  // FILE_ID_INFO { ULONGLONG VolumeSerialNumber; FILE_ID_128 FileId; }, as hexadecimal: what tells
  // one file from every other, as long as it exists.
  static String fileId(Pointer handle) throws IOException {
    try (Memory information = new Memory(24)) {
      if (!Win32.Kernel32.GetFileInformationByHandleEx(handle, Win32.FILE_ID_INFO, information, 24)) {
        throw Win32.exception(null, Win32.lastError());
      }
      StringBuilder id = new StringBuilder(Long.toHexString(information.getLong(0))).append(':');
      for (byte b : information.getByteArray(8, 16)) {
        id.append(String.format("%02x", b & 0xff));
      }
      return id.toString();
    }
  }

  // The stage's directory and file, by their volumes and file IDs.
  @Override
  Identity identity() throws IOException {
    return new Identity(directory.getFileName().toString(), fileId(directoryHandle), fileId(fileHandle));
  }

  // A volume that does not keep ACLs, such as a FAT drive, lets everyone open every file.
  static void requirePersistentAcls(Path directory) throws IOException {
    Pointer handle = Win32.Kernel32.CreateFileW(Win32.path(directory), Win32.FILE_READ_ATTRIBUTES,
        Win32.FILE_SHARE_READ | Win32.FILE_SHARE_WRITE | Win32.FILE_SHARE_DELETE, null, Win32.OPEN_EXISTING, Win32.FILE_FLAG_BACKUP_SEMANTICS,
        null);
    if (Win32.isInvalid(handle)) {
      throw Win32.exception(directory.toString(), Win32.lastError());
    }
    try (Memory flags = new Memory(4)) {
      if (!Win32.Kernel32.GetVolumeInformationByHandleW(handle, null, 0, null, null, flags, null, 0)) {
        throw Win32.exception(directory.toString(), Win32.lastError());
      }
      if ((flags.getInt(0) & Win32.FILE_PERSISTENT_ACLS) == 0) {
        throw new FileSystemException(directory.toString(), null,
            "The volume does not keep file ACLs, so the download could not be kept private and was not started");
      }
    } finally {
      Win32.Kernel32.CloseHandle(handle);
    }
  }

  // Creates a directory of a new name, with prefix and a random suffix, with the ACL of the SDDL.
  private static Path createDirectory(Path parent, String prefix, String sddl) throws IOException {
    Pointer descriptor = Win32.securityDescriptor(sddl);
    try (Memory attributes = Win32.securityAttributes(descriptor)) {
      for (int attempt = 0; ; attempt++) {
        Path directory = parent.resolve(prefix + Long.toUnsignedString(RANDOM.nextLong()));
        if (Win32.Kernel32.CreateDirectoryW(Win32.path(directory), attributes)) {
          return directory;
        }
        int error = Win32.lastError();
        if (error != Win32.ERROR_ALREADY_EXISTS || attempt >= 100) {
          throw Win32.exception(directory.toString(), error);
        }
      }
    } finally {
      Win32.Kernel32.LocalFree(descriptor);
    }
  }

  // Creates the file, which must be new, with the ACL of the SDDL, opened with no sharing.
  private static Pointer createFile(Path file, String sddl) throws IOException {
    Pointer descriptor = Win32.securityDescriptor(sddl);
    try (Memory attributes = Win32.securityAttributes(descriptor)) {
      Pointer handle = Win32.Kernel32.CreateFileW(Win32.path(file),
          Win32.GENERIC_WRITE | Win32.DELETE | Win32.READ_CONTROL | Win32.WRITE_DAC | Win32.FILE_READ_ATTRIBUTES, 0, attributes,
          Win32.CREATE_NEW, Win32.FILE_ATTRIBUTE_NORMAL | Win32.FILE_FLAG_OPEN_REPARSE_POINT, null);
      if (Win32.isInvalid(handle)) {
        int error = Win32.lastError();
        if (error == Win32.ERROR_FILE_EXISTS || error == Win32.ERROR_ALREADY_EXISTS) {
          throw replacedDirectory();
        }
        throw Win32.exception(file.toString(), error);
      }
      return handle;
    } finally {
      Win32.Kernel32.LocalFree(descriptor);
    }
  }

  // Whether the handle is of a directory, not a link, owned by owner, whose protected ACL grants
  // owner full control, inherited by what is created in it, and nothing else.
  static boolean isPrivateDirectory(Pointer handle, Pointer owner) throws IOException {
    try (Memory tag = new Memory(8)) {
      if (!Win32.Kernel32.GetFileInformationByHandleEx(handle, Win32.FILE_ATTRIBUTE_TAG_INFO, tag, 8)) {
        throw Win32.exception(null, Win32.lastError());
      }
      int attributes = tag.getInt(0);
      if ((attributes & Win32.FILE_ATTRIBUTE_DIRECTORY) == 0 || (attributes & Win32.FILE_ATTRIBUTE_REPARSE_POINT) != 0) {
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
        // ACL { BYTE AclRevision; BYTE Sbz1; WORD AclSize; WORD AceCount; WORD Sbz2; }, then its
        // ACEs: ACCESS_ALLOWED_ACE { BYTE AceType; BYTE AceFlags; WORD AceSize; ACCESS_MASK Mask;
        // SID }.
        Pointer dacl = daclOut.getPointer(0);
        if (dacl == null || (dacl.getShort(4) & 0xffff) != 1) {
          return false;
        }
        Pointer ace = dacl.share(8);
        return ace.getByte(0) == Win32.ACCESS_ALLOWED_ACE_TYPE
            && ace.getByte(1) == (Win32.OBJECT_INHERIT_ACE | Win32.CONTAINER_INHERIT_ACE)
            && ace.getInt(4) == Win32.FILE_ALL_ACCESS
            && Win32.Advapi32.EqualSid(ace.share(8), owner);
      } finally {
        Win32.Kernel32.LocalFree(descriptor);
      }
    }
  }

  @Override
  synchronized void restart() throws IOException {
    if (!Win32.Kernel32.SetFilePointerEx(fileHandle, 0, null, Win32.FILE_BEGIN) || !Win32.Kernel32.SetEndOfFile(fileHandle)) {
      throw Win32.exception(directory.resolve(NAME).toString(), Win32.lastError());
    }
  }

  @Override
  synchronized void write(byte[] bytes, int count) throws IOException {
    writeHere(bytes, 0, count);
  }

  // Through the one native buffer and the handle's file pointer, so one write at a time.
  @Override
  synchronized void writeAt(long position, byte[] bytes, int offset, int count) throws IOException {
    if (!Win32.Kernel32.SetFilePointerEx(fileHandle, position, null, Win32.FILE_BEGIN)) {
      throw Win32.exception(directory.resolve(NAME).toString(), Win32.lastError());
    }
    writeHere(bytes, offset, count);
  }

  // Writes at the handle's file pointer.
  private void writeHere(byte[] bytes, int from, int count) throws IOException {
    for (int offset = 0; offset < count; ) {
      int length = Math.min(BUFFER_BYTES, count - offset);
      buffer.write(0, bytes, from + offset, length);
      for (int done = 0; done < length; ) {
        if (!Win32.Kernel32.WriteFile(fileHandle, done == 0 ? buffer : buffer.share(done), length - done, written, null)) {
          throw Win32.exception(directory.resolve(NAME).toString(), Win32.lastError());
        }
        int wrote = written.getInt(0);
        if (wrote <= 0) {
          throw new FileSystemException(directory.resolve(NAME).toString(), null, "No more bytes could be written");
        }
        done += wrote;
      }
      offset += length;
    }
  }

  @Override
  void force() throws IOException {
    if (!Win32.Kernel32.FlushFileBuffers(fileHandle)) {
      throw Win32.exception(directory.resolve(NAME).toString(), Win32.lastError());
    }
  }

  @Override
  void publish(Path target, boolean replace) throws IOException {
    if (!replace && Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
      throw new FileAlreadyExistsException(target.toString());
    }
    // A link at target is replaced, not written through; what it leads to decides the access.
    if (Files.exists(target)) {
      keepRestrictionsOf(target);
    }
    // The rename replaces a file that has appeared at target meanwhile only when replace is set.
    try (Memory information = Win32.renameInformation(target, replace)) {
      if (!Win32.Kernel32.SetFileInformationByHandle(fileHandle, Win32.FILE_RENAME_INFO, information, (int) information.size())) {
        throw Win32.exception(target.toString(), Win32.lastError());
      }
    }
    published = true;
  }

  // The file being replaced keeps its access: the new file gets the DACL GetKernelObjectSecurity returns
  // for it through a READ_CONTROL handle, the documented read counterpart of the SetKernelObjectSecurity
  // that sets it. GetNamedSecurityInfoW, read here before, returned for a DACL that Java's setAcl had set
  // a DACL in another order, and with other entries marked inherited, than GetFileSecurityW, which Java's
  // ACL view reads, returned; copying it changed the ACL Java reported (observed once, on Windows). A file
  // this user could not write, or that belongs to another user, is not replaced.
  private void keepRestrictionsOf(Path target) throws IOException {
    if (!Files.isWritable(target)) {
      throw new AccessDeniedException(target.toString(), null, "The destination cannot be written, so the download did not replace it");
    }
    // Without FILE_FLAG_OPEN_REPARSE_POINT: a link at target is followed, and what it leads to decides the access.
    Pointer handle = Win32.Kernel32.CreateFileW(Win32.path(target), Win32.READ_CONTROL,
        Win32.FILE_SHARE_READ | Win32.FILE_SHARE_WRITE | Win32.FILE_SHARE_DELETE, null, Win32.OPEN_EXISTING, 0, null);
    if (Win32.isInvalid(handle)) {
      throw Win32.exception(target.toString(), Win32.lastError());
    }
    Memory descriptor;
    try {
      descriptor = Win32.kernelObjectSecurity(handle, Win32.OWNER_SECURITY_INFORMATION | Win32.DACL_SECURITY_INFORMATION,
          target.toString());
    } finally {
      Win32.Kernel32.CloseHandle(handle);
    }
    try (Memory ownerOut = new Memory(Native.POINTER_SIZE); Memory ownerDefaulted = new Memory(4)) {
      if (!Win32.Advapi32.GetSecurityDescriptorOwner(descriptor, ownerOut, ownerDefaulted)) {
        throw Win32.exception(target.toString(), Win32.lastError());
      }
      Pointer targetOwner = ownerOut.getPointer(0);
      if (targetOwner == null || !Win32.Advapi32.EqualSid(targetOwner, owner.getPointer(0))) {
        throw new AccessDeniedException(target.toString(), null, "The destination belongs to another user, so the download did not replace it");
      }
      // The file takes the DACL read above, inherited entries included; without propagation, as
      // SetKernelObjectSecurity sets it.
      exposed = true;
      if (!Win32.Advapi32.SetKernelObjectSecurity(fileHandle, Win32.DACL_SECURITY_INFORMATION, descriptor)) {
        throw Win32.exception(directory.resolve(NAME).toString(), Win32.lastError());
      }
    } finally {
      descriptor.close();
    }
  }

  // Deletes what the download created, through its handles: the file, unless publication moved
  // it, and then the directory. A retained stage only closes its handles.
  @Override
  public void close() {
    if (retained) {
      Win32.Kernel32.CloseHandle(fileHandle);
      Win32.Kernel32.CloseHandle(directoryHandle);
      buffer.close();
      written.close();
      owner.close();
      return;
    }
    if (!published) {
      int error = Win32.markForDeletion(fileHandle);
      if (error != 0) {
        warnNotDeleted("file", "Windows error " + error);
      }
    }
    Win32.Kernel32.CloseHandle(fileHandle);
    int error = Win32.markForDeletion(directoryHandle);
    if (error == Win32.ERROR_DIR_NOT_EMPTY) {
      warnNotEmpty();
    } else if (error != 0) {
      warnNotDeleted("directory", "Windows error " + error);
    }
    Win32.Kernel32.CloseHandle(directoryHandle);
    buffer.close();
    written.close();
    owner.close();
  }
}
