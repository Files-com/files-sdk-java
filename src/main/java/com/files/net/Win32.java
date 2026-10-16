package com.files.net;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.win32.StdCallLibrary;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

// The Windows functions that WindowsDownloadStage uses, from kernel32 and advapi32, bound through
// JNA's direct mapping, with the types and values of the Windows SDK's headers. Structures are laid
// out by hand in native memory: each starts with a 32-bit field, which pointers then follow at the
// alignment of a pointer.
final class Win32 {
  // winnt.h
  static final int DELETE = 0x00010000;
  static final int READ_CONTROL = 0x00020000;
  static final int SYNCHRONIZE = 0x00100000;
  static final int GENERIC_READ = 0x80000000;
  static final int GENERIC_WRITE = 0x40000000;
  static final int WRITE_DAC = 0x00040000;
  static final int FILE_LIST_DIRECTORY = 0x00000001;
  static final int FILE_READ_ATTRIBUTES = 0x00000080;
  static final int FILE_ALL_ACCESS = 0x001F01FF;
  static final int FILE_SHARE_READ = 0x00000001;
  static final int FILE_SHARE_WRITE = 0x00000002;
  static final int FILE_SHARE_DELETE = 0x00000004;
  static final int FILE_ATTRIBUTE_DIRECTORY = 0x00000010;
  static final int FILE_ATTRIBUTE_NORMAL = 0x00000080;
  static final int FILE_ATTRIBUTE_REPARSE_POINT = 0x00000400;
  static final int FILE_PERSISTENT_ACLS = 0x00000008;
  static final int TOKEN_QUERY = 0x0008;
  static final int TOKEN_OWNER = 4;
  static final int OWNER_SECURITY_INFORMATION = 0x00000001;
  static final int DACL_SECURITY_INFORMATION = 0x00000004;
  static final int SE_DACL_PROTECTED = 0x1000;
  static final int ACCESS_ALLOWED_ACE_TYPE = 0x00;
  static final int OBJECT_INHERIT_ACE = 0x01;
  static final int CONTAINER_INHERIT_ACE = 0x02;
  // fileapi.h and winbase.h
  static final int CREATE_NEW = 1;
  static final int OPEN_EXISTING = 3;
  static final int OPEN_ALWAYS = 4;
  static final int FILE_FLAG_OPEN_REPARSE_POINT = 0x00200000;
  static final int FILE_FLAG_BACKUP_SEMANTICS = 0x02000000;
  static final int FILE_BEGIN = 0;
  static final int FILE_END = 2;
  static final int FILE_STANDARD_INFO = 1;
  static final int FILE_RENAME_INFO = 3;
  static final int FILE_DISPOSITION_INFO = 4;
  static final int FILE_ATTRIBUTE_TAG_INFO = 9;
  static final int FILE_ID_INFO = 18;
  // accctrl.h and sddl.h
  static final int SE_FILE_OBJECT = 1;
  static final int SDDL_REVISION_1 = 1;
  // winerror.h
  static final int ERROR_FILE_NOT_FOUND = 2;
  static final int ERROR_PATH_NOT_FOUND = 3;
  static final int ERROR_ACCESS_DENIED = 5;
  static final int ERROR_SHARING_VIOLATION = 32;
  static final int ERROR_FILE_EXISTS = 80;
  static final int ERROR_INSUFFICIENT_BUFFER = 122;
  static final int ERROR_DIR_NOT_EMPTY = 145;
  static final int ERROR_ALREADY_EXISTS = 183;
  static final int ERROR_NO_TOKEN = 1008;

  private static final Throwable FAILURE = load();

  private Win32() {
  }

  private static Throwable load() {
    try {
      // Before JNA is first used here, which may have it write its native library.
      IOException unsafe = JnaTempDirectory.prepare();
      if (unsafe != null) {
        return unsafe;
      }
      // The Windows API's calling convention, stdcall, differs from C's only on 32-bit x86; JNA's
      // direct mapping refuses it elsewhere.
      Map<String, Object> options = Platform.is64Bit() ? Collections.<String, Object>emptyMap()
          : Collections.<String, Object>singletonMap(Library.OPTION_CALLING_CONVENTION, StdCallLibrary.STDCALL_CONVENTION);
      Throwable failure = NativeAccess.register(Kernel32.class, "kernel32", options);
      return failure != null ? failure : NativeAccess.register(Advapi32.class, "advapi32", options);
    } catch (VirtualMachineError e) {
      throw e;
    } catch (Error | RuntimeException e) {
      return e;
    }
  }

  // Throws if the functions could not be bound.
  static void requireAvailable() throws IOException {
    if (FAILURE != null) {
      throw NativeAccess.unavailable(FAILURE);
    }
  }

  static final class Kernel32 {
    private Kernel32() {
    }

    static native boolean CreateDirectoryW(WString path, Pointer securityAttributes);

    static native Pointer CreateFileW(WString path, int access, int share, Pointer securityAttributes, int disposition, int flags,
        Pointer template);

    static native boolean WriteFile(Pointer file, Pointer buffer, int count, Pointer written, Pointer overlapped);

    static native boolean ReadFile(Pointer file, Pointer buffer, int count, Pointer read, Pointer overlapped);

    static native boolean FlushFileBuffers(Pointer file);

    static native boolean SetFilePointerEx(Pointer file, long distance, Pointer position, int method);

    static native boolean SetEndOfFile(Pointer file);

    static native boolean GetFileInformationByHandleEx(Pointer file, int informationClass, Pointer information, int size);

    static native boolean SetFileInformationByHandle(Pointer file, int informationClass, Pointer information, int size);

    static native boolean GetVolumeInformationByHandleW(Pointer file, Pointer volumeName, int volumeNameSize, Pointer serialNumber,
        Pointer maximumComponentLength, Pointer fileSystemFlags, Pointer fileSystemName, int fileSystemNameSize);

    static native boolean CloseHandle(Pointer handle);

    static native Pointer LocalFree(Pointer memory);

    static native Pointer GetCurrentThread();

    static native Pointer GetCurrentProcess();
  }

  static final class Advapi32 {
    private Advapi32() {
    }

    static native boolean OpenThreadToken(Pointer thread, int access, boolean openAsSelf, Pointer token);

    static native boolean OpenProcessToken(Pointer process, int access, Pointer token);

    static native boolean GetTokenInformation(Pointer token, int informationClass, Pointer information, int length, Pointer returnLength);

    static native boolean ConvertSidToStringSidW(Pointer sid, Pointer string);

    static native boolean ConvertStringSecurityDescriptorToSecurityDescriptorW(WString descriptor, int revision, Pointer securityDescriptor,
        Pointer size);

    static native int GetSecurityInfo(Pointer handle, int objectType, int information, Pointer owner, Pointer group, Pointer dacl,
        Pointer sacl, Pointer securityDescriptor);

    static native boolean GetKernelObjectSecurity(Pointer handle, int information, Pointer securityDescriptor, int length,
        Pointer lengthNeeded);

    static native boolean SetKernelObjectSecurity(Pointer handle, int information, Pointer securityDescriptor);

    static native boolean GetSecurityDescriptorOwner(Pointer securityDescriptor, Pointer owner, Pointer ownerDefaulted);

    static native boolean EqualSid(Pointer sid, Pointer other);

    static native boolean GetSecurityDescriptorControl(Pointer securityDescriptor, Pointer control, Pointer revision);
  }

  static int lastError() {
    return Native.getLastError();
  }

  static boolean isInvalid(Pointer handle) {
    return handle == null || Pointer.nativeValue(handle) == -1;
  }

  // A path as the Windows API takes it, with the prefix that lifts the length limit on a long one, as
  // Java's own file system API adds it.
  static WString path(Path path) {
    String text = path.toString();
    if (text.length() < 248 || text.startsWith("\\\\?\\")) {
      return new WString(text);
    }
    return new WString(text.startsWith("\\\\") ? "\\\\?\\UNC\\" + text.substring(2) : "\\\\?\\" + text);
  }

  // The token owner of the current thread, or of the process when the thread is not impersonating:
  // the owner of the files and directories it creates. A TOKEN_OWNER, whose first field points to
  // the owner's SID, within the same memory.
  static Memory tokenOwner() throws IOException {
    try (Memory tokenOut = new Memory(Native.POINTER_SIZE)) {
      if (!Advapi32.OpenThreadToken(Kernel32.GetCurrentThread(), TOKEN_QUERY, true, tokenOut)) {
        if (lastError() != ERROR_NO_TOKEN || !Advapi32.OpenProcessToken(Kernel32.GetCurrentProcess(), TOKEN_QUERY, tokenOut)) {
          throw exception(null, lastError());
        }
      }
      Pointer token = tokenOut.getPointer(0);
      try (Memory length = new Memory(4)) {
        length.setInt(0, 0);
        if (!Advapi32.GetTokenInformation(token, TOKEN_OWNER, null, 0, length) && lastError() != ERROR_INSUFFICIENT_BUFFER) {
          throw exception(null, lastError());
        }
        Memory owner = new Memory(Math.max(length.getInt(0), Native.POINTER_SIZE));
        if (!Advapi32.GetTokenInformation(token, TOKEN_OWNER, owner, (int) owner.size(), length)) {
          int error = lastError();
          owner.close();
          throw exception(null, error);
        }
        return owner;
      } finally {
        Kernel32.CloseHandle(token);
      }
    }
  }

  static String sidString(Pointer sid) throws IOException {
    try (Memory out = new Memory(Native.POINTER_SIZE)) {
      if (!Advapi32.ConvertSidToStringSidW(sid, out)) {
        throw exception(null, lastError());
      }
      Pointer string = out.getPointer(0);
      try {
        return string.getWideString(0);
      } finally {
        Kernel32.LocalFree(string);
      }
    }
  }

  // The self-relative security descriptor of the object of handle, the parts of it information names, as
  // GetKernelObjectSecurity returns it, in memory the caller closes.
  static Memory kernelObjectSecurity(Pointer handle, int information, String name) throws IOException {
    try (Memory length = new Memory(4)) {
      length.setInt(0, 0);
      if (!Advapi32.GetKernelObjectSecurity(handle, information, null, 0, length) && lastError() != ERROR_INSUFFICIENT_BUFFER) {
        throw exception(name, lastError());
      }
      Memory descriptor = new Memory(Math.max(length.getInt(0), 1));
      if (!Advapi32.GetKernelObjectSecurity(handle, information, descriptor, (int) descriptor.size(), length)) {
        int error = lastError();
        descriptor.close();
        throw exception(name, error);
      }
      return descriptor;
    }
  }

  // The self-relative security descriptor of the SDDL text, which the caller frees with LocalFree.
  static Pointer securityDescriptor(String sddl) throws IOException {
    try (Memory out = new Memory(Native.POINTER_SIZE)) {
      if (!Advapi32.ConvertStringSecurityDescriptorToSecurityDescriptorW(new WString(sddl), SDDL_REVISION_1, out, null)) {
        throw exception(null, lastError());
      }
      return out.getPointer(0);
    }
  }

  // SECURITY_ATTRIBUTES { DWORD nLength; LPVOID lpSecurityDescriptor; BOOL bInheritHandle; }.
  static Memory securityAttributes(Pointer securityDescriptor) {
    int pointer = Native.POINTER_SIZE;
    Memory attributes = new Memory(3L * pointer);
    attributes.clear();
    attributes.setInt(0, 3 * pointer);
    attributes.setPointer(pointer, securityDescriptor);
    return attributes;
  }

  // FILE_RENAME_INFO { union { BOOLEAN ReplaceIfExists; DWORD Flags; }; HANDLE RootDirectory;
  // DWORD FileNameLength; WCHAR FileName[]; }, naming target by its absolute path.
  static Memory renameInformation(Path target, boolean replace) {
    char[] name = path(target).toString().toCharArray();
    int pointer = Native.POINTER_SIZE;
    int nameOffset = 2 * pointer + 4;
    int size = (nameOffset + (name.length + 1) * 2 + pointer - 1) / pointer * pointer;
    Memory information = new Memory(size);
    information.clear();
    information.setInt(0, replace ? 1 : 0);
    information.setInt(2L * pointer, name.length * 2);
    information.write(nameOffset, name, 0, name.length);
    return information;
  }

  // Marks the file or directory of handle, which must have been opened with DELETE access, to be
  // deleted once its last handle is closed. Returns 0, or the error.
  static int markForDeletion(Pointer handle) {
    try (Memory information = new Memory(4)) {
      information.clear();
      information.setByte(0, (byte) 1);
      return Kernel32.SetFileInformationByHandle(handle, FILE_DISPOSITION_INFO, information, 1) ? 0 : lastError();
    }
  }

  // The exception for the Windows error, as Java's own file system API would throw it.
  static IOException exception(String file, int error) {
    switch (error) {
      case ERROR_FILE_NOT_FOUND:
      case ERROR_PATH_NOT_FOUND:
        return new NoSuchFileException(file);
      case ERROR_FILE_EXISTS:
      case ERROR_ALREADY_EXISTS:
        return new FileAlreadyExistsException(file);
      case ERROR_ACCESS_DENIED:
        return new AccessDeniedException(file);
      case ERROR_DIR_NOT_EMPTY:
        return new DirectoryNotEmptyException(file);
      default:
        return new FileSystemException(file, null, "Windows error " + error);
    }
  }
}
