package com.files.net;

import com.sun.jna.FunctionMapper;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The functions of the macOS C library (libSystem) that DarwinDownloadStage uses, bound through
// JNA's direct mapping, with the types and values of the macOS SDK's headers. Paths are passed as
// NUL-terminated UTF-8, the encoding of macOS file names.
final class Darwin {
  private static final Logger log = LoggerFactory.getLogger(Darwin.class);

  // <sys/fcntl.h>
  static final int O_RDONLY = 0x0000;
  static final int O_WRONLY = 0x0001;
  static final int O_RDWR = 0x0002;
  static final int O_NONBLOCK = 0x00000004;
  // Takes flock's exclusive lock on the file as it is opened.
  static final int O_EXLOCK = 0x00000020;
  static final int O_NOFOLLOW = 0x00000100;
  static final int O_CREAT = 0x00000200;
  static final int O_EXCL = 0x00000800;
  static final int O_DIRECTORY = 0x00100000;
  static final int O_CLOEXEC = 0x01000000;
  static final int F_FULLFSYNC = 51;
  static final int AT_FDCWD = -2;
  static final int AT_SYMLINK_NOFOLLOW = 0x0020;
  static final int AT_REMOVEDIR = 0x0080;
  static final int FILESEC_MODE = 4;
  static final int FILESEC_ACL = 5;
  // <stdio.h>
  static final int RENAME_EXCL = 0x00000004;
  // <sys/acl.h>
  static final int ACL_TYPE_EXTENDED = 0x00000100;
  static final int ACL_FIRST_ENTRY = 0;
  static final int ACL_NEXT_ENTRY = -1;
  static final int ACL_FLAG_NO_INHERIT = 1 << 17;
  static final int ACL_ENTRY_INHERITED = 1 << 4;
  static final int ACL_ENTRY_FILE_INHERIT = 1 << 5;
  static final int ACL_ENTRY_DIRECTORY_INHERIT = 1 << 6;
  static final int ACL_ENTRY_LIMIT_INHERIT = 1 << 7;
  static final int ACL_ENTRY_ONLY_INHERIT = 1 << 8;
  // <sys/unistd.h>
  static final int PC_EXTENDED_SECURITY_NP = 13;
  static final int PC_AUTH_OPAQUE_NP = 14;
  // <sys/mount.h>
  static final int MNT_LOCAL = 0x00001000;
  static final int MNT_IGNORE_OWNERSHIP = 0x00200000;
  // <sys/_types/_s_ifmt.h>
  static final int S_IFMT = 0170000;
  static final int S_IFDIR = 0040000;
  static final int S_IFREG = 0100000;
  // <sys/errno.h>
  static final int EPERM = 1;
  static final int ENOENT = 2;
  static final int EINTR = 4;
  static final int EAGAIN = 35;
  static final int EACCES = 13;
  static final int EEXIST = 17;
  static final int ENOTDIR = 20;
  static final int EINVAL = 22;
  static final int ENOTSUP = 45;
  static final int ELOOP = 62;
  static final int ENOTEMPTY = 66;

  // struct stat with 64-bit inode numbers (<sys/stat.h>): its size and the offsets of the fields
  // read here.
  private static final int STAT_SIZE = 144;
  private static final int ST_DEV = 0;
  private static final int ST_MODE = 4;
  private static final int ST_INO = 8;
  private static final int ST_UID = 16;
  private static final int ST_GID = 20;
  private static final int ST_SIZE = 96;
  // struct statfs with 64-bit inode numbers (<sys/mount.h>).
  private static final int STATFS_SIZE = 2168;
  private static final int F_FLAGS = 64;

  // The functions called through JNA's interface mapping rather than its direct mapping (see
  // Proxied).
  private static Proxied proxied;
  private static final Throwable FAILURE = load();

  private Darwin() {
  }

  private static Throwable load() {
    try {
      Map<String, Object> options = Collections.<String, Object>singletonMap(Library.OPTION_FUNCTION_MAPPER, new Inode64Names());
      Throwable failure = NativeAccess.register(Darwin.class, "c", options);
      if (failure == null) {
        proxied = Native.load("c", Proxied.class, options);
      }
      return failure;
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

  // openat takes its mode as a variadic argument, which Apple silicon passes differently from a
  // fixed one, and JNA's interface mapping handles variadic calls. renameatx_np exists only from
  // macOS 10.12, and the interface mapping looks a function up only when it is first called, so its
  // absence fails that call alone instead of every function's binding.
  interface Proxied extends Library {
    int openat(int fd, byte[] path, int flags, Object... mode);

    int renameatx_np(int fromFd, byte[] from, int toFd, byte[] to, int flags);
  }

  // On Intel Macs, the functions that fill in a struct stat or struct statfs with 64-bit inode
  // numbers have a $INODE64 suffix, as the SDK's headers name them; Apple silicon has only those,
  // without the suffix.
  private static final class Inode64Names implements FunctionMapper {
    @Override
    public String getFunctionName(NativeLibrary library, Method method) {
      String name = method.getName();
      boolean stat = name.equals("fstat") || name.equals("fstatat") || name.equals("fstatfs") || name.equals("statfs");
      return stat && "x86-64".equals(Platform.ARCH) ? name + "$INODE64" : name;
    }
  }

  static native int geteuid();

  // open is variadic too, but takes its mode only with O_CREAT, which it is never given here.
  static native int open(byte[] path, int flags);

  static native int close(int fd);

  static native int fstat(int fd, Pointer stat);

  static native int fstatat(int fd, byte[] path, Pointer stat, int flags);

  static native int fstatfs(int fd, Pointer statfs);

  static native int statfs(byte[] path, Pointer statfs);

  static native long pathconf(byte[] path, int name);

  static native int mkdir(byte[] path, short mode);

  static native int mkdirx_np(byte[] path, Pointer filesec);

  static native Pointer filesec_init();

  static native int filesec_set_property(Pointer filesec, int property, Pointer value);

  static native void filesec_free(Pointer filesec);

  static native Pointer acl_init(int count);

  static native int acl_set_fd_np(int fd, Pointer acl, int type);

  static native Pointer acl_get_fd_np(int fd, int type);

  static native Pointer acl_get_file(byte[] path, int type);

  static native int acl_get_entry(Pointer acl, int entryId, Pointer entry);

  static native int acl_create_entry(Pointer acl, Pointer entry);

  static native int acl_copy_entry(Pointer destination, Pointer source);

  static native int acl_get_flagset_np(Pointer object, Pointer flagset);

  static native int acl_set_flagset_np(Pointer object, Pointer flagset);

  static native int acl_get_flag_np(Pointer flagset, int flag);

  static native int acl_add_flag_np(Pointer flagset, int flag);

  static native int acl_delete_flag_np(Pointer flagset, int flag);

  static native int acl_free(Pointer object);

  static native long pwrite(int fd, Pointer buffer, long count, long offset);

  static native long pread(int fd, Pointer buffer, long count, long offset);

  static native int ftruncate(int fd, long length);

  static native int fsync(int fd);

  // fcntl is variadic, but F_FULLFSYNC takes nothing after the command, and the fixed arguments are
  // passed the same way either way.
  static native int fcntl(int fd, int command);

  static native int fchmod(int fd, short mode);

  static native int fchown(int fd, int owner, int group);

  static native int unlinkat(int fd, byte[] path, int flags);

  static native int renameat(int fromFd, byte[] from, int toFd, byte[] to);

  static native String strerror(int error);

  static int openat(int fd, byte[] path, int flags, int mode) {
    return proxied.openat(fd, path, flags, mode);
  }

  // Throws UnsatisfiedLinkError where macOS does not have the function, before 10.12.
  static int renameatx_np(int fromFd, byte[] from, int toFd, byte[] to, int flags) {
    return proxied.renameatx_np(fromFd, from, toFd, to, flags);
  }

  static int errno() {
    return Native.getLastError();
  }

  static byte[] path(Path path) {
    return name(path.toString());
  }

  static byte[] name(String name) {
    byte[] bytes = name.getBytes(StandardCharsets.UTF_8);
    return Arrays.copyOf(bytes, bytes.length + 1);
  }

  // The file of fd: its device, inode number, type, permissions and owner, and its size.
  static Stat fstat(int fd) throws IOException {
    try (Memory buffer = new Memory(STAT_SIZE)) {
      if (fstat(fd, buffer) != 0) {
        throw exception(null, errno());
      }
      return new Stat(buffer);
    }
  }

  // What name in the directory of fd is, without following a link, or null if there is nothing.
  static Stat fstatat(int fd, byte[] name) throws IOException {
    try (Memory buffer = new Memory(STAT_SIZE)) {
      if (fstatat(fd, name, buffer, AT_SYMLINK_NOFOLLOW) != 0) {
        int error = errno();
        if (error == ENOENT) {
          return null;
        }
        throw exception(null, error);
      }
      return new Stat(buffer);
    }
  }

  // Makes what was written to fd durable as FileChannel.force does on macOS: with F_FULLFSYNC, which
  // has the drive write its cache to its medium, as fsync alone does not; or, on a volume that is not
  // local, which may not support it, with fsync.
  static void force(int fd, String file) throws IOException {
    if (fcntl(fd, F_FULLFSYNC) == 0) {
      return;
    }
    int error = errno();
    if ((volumeFlags(fd) & MNT_LOCAL) == 0 && fsync(fd) == 0) {
      return;
    }
    throw exception(file, error);
  }

  // The mount flags of the volume holding the file of fd.
  static int volumeFlags(int fd) throws IOException {
    try (Memory buffer = new Memory(STATFS_SIZE)) {
      if (fstatfs(fd, buffer) != 0) {
        throw exception(null, errno());
      }
      return buffer.getInt(F_FLAGS);
    }
  }

  // The mount flags of the volume holding what path leads to.
  static int volumeFlags(Path path) throws IOException {
    try (Memory buffer = new Memory(STATFS_SIZE)) {
      if (statfs(path(path), buffer) != 0) {
        throw exception(path.toString(), errno());
      }
      return buffer.getInt(F_FLAGS);
    }
  }

  // Whether the kernel, creating something in the directory path leads to, gives it the entries of
  // that directory's ACL that it inherits: on a volume with ACLs whose server does not apply them
  // itself (see vn_attribute_prepare in XNU's vfs_subr.c, and kauth_acl_inherit in its
  // kern_authorization.c).
  static boolean inheritsAcl(Path directory) throws IOException {
    return pathconf(directory, PC_EXTENDED_SECURITY_NP) == 1 && pathconf(directory, PC_AUTH_OPAQUE_NP) == 0;
  }

  private static long pathconf(Path path, int name) throws IOException {
    long value = pathconf(path(path), name);
    if (value == -1) {
      throw exception(path.toString(), errno());
    }
    return value;
  }

  // The ACL of what path leads to, or null if it has none; acl_free frees it.
  static Pointer acl(Path path) throws IOException {
    Pointer acl = acl_get_file(path(path), ACL_TYPE_EXTENDED);
    if (acl == null) {
      int error = errno();
      if (error != ENOENT && error != ENOTSUP) {
        throw exception(path.toString(), error);
      }
    }
    return acl;
  }

  static void close(int fd, String what) {
    if (fd >= 0 && close(fd) != 0) {
      log.debug("Could not close the download's {}: {}", what, strerror(errno()));
    }
  }

  // The exception for the C library's error number, as Java's own file system API would throw it.
  static IOException exception(String file, int error) {
    switch (error) {
      case ENOENT:
        return new NoSuchFileException(file);
      case EEXIST:
        return new FileAlreadyExistsException(file);
      case EACCES:
      case EPERM:
        return new AccessDeniedException(file);
      case ENOTEMPTY:
        return new DirectoryNotEmptyException(file);
      default:
        return new FileSystemException(file, null, strerror(error));
    }
  }

  static final class Stat {
    final long device;
    final long inode;
    final int mode;
    final int owner;
    final int group;
    final long size;

    private Stat(Pointer stat) {
      device = stat.getInt(ST_DEV) & 0xffffffffL;
      mode = stat.getShort(ST_MODE) & 0xffff;
      inode = stat.getLong(ST_INO);
      owner = stat.getInt(ST_UID);
      group = stat.getInt(ST_GID);
      size = stat.getLong(ST_SIZE);
    }

    boolean isDirectory() {
      return (mode & S_IFMT) == S_IFDIR;
    }

    boolean isRegularFile() {
      return (mode & S_IFMT) == S_IFREG;
    }

    boolean isSameFile(Stat other) {
      return other != null && device == other.device && inode == other.inode;
    }

    // The file's device and inode, as a checkpoint records them.
    String identity() {
      return device + ":" + inode;
    }
  }
}
