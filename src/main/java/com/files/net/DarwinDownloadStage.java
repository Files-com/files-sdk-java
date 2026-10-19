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
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

// The stage of a download on macOS, held through file descriptors of the C library (see Darwin).
//
// A directory created in the destination's directory inherits that directory's inheritable ACL
// entries, and macOS checks a file's ACL before its permissions, so mode rwx------ alone does not
// keep others out; Java can neither see nor prevent an ACL on macOS. So the download creates its
// directory with mkdirx_np, rwx------ and with an initial ACL that is empty and flagged to inherit
// nothing, from which the kernel makes the directory without any ACL (see kauth_acl_inherit in
// XNU's kern_authorization.c). Where the kernel would not have given it the destination directory's
// entries anyway, on a volume without ACLs or whose server applies them, it is created with mkdir.
// The download then opens the directory, without following a link, and makes sure, before it
// changes or creates anything in it, that it opened the directory its name led to just after it
// was created: the user's own, rwx------ and without an ACL. A directory that is not is left as it
// is, never changed or deleted. A volume set to ignore ownership, where permissions keep no one
// out, is refused: before the directory is created, and again once it is opened. The file is
// created in the directory: new, since a name already there stops the download, rw-------, with
// nothing to inherit, and found only by a lookup in the directory, which only the owner may make.
// Its bytes are written only through the descriptor that created it.
//
// The file is reached only through the held directory: it is published by renaming it from there,
// once its name still leads to the file the download wrote, and deleted from there. Replacing the
// directory's name, or anything else in the destination's directory, cannot change what is
// published or which file is deleted. The directory itself is removed by its name, once that name
// still leads to the directory the download holds.
//
// Once every byte is checked, and only then, the file gets its final access, still unreachable in
// the private directory until it is renamed into place. A file that replaces another gets that
// file's permissions, group and ACL; it does not replace a file it could not have written, or that
// belongs to another user. A new file gets the permissions a new file in the destination's
// directory normally gets, and the ACL the kernel would give it there: the directory's entries that
// files inherit, in their order, marked inherited.
final class DarwinDownloadStage extends DownloadStage {
  private static final int BUFFER_BYTES = 64 * 1024;
  private static final byte[] FILE_NAME = Darwin.name(NAME);
  private static final byte[] NEW_FILE_NAME = Darwin.name(NEW_FILE);
  private static final SecureRandom RANDOM = new SecureRandom();

  private final Path directory;
  private final byte[] directoryPath;
  private final int directoryFd;
  private final Darwin.Stat directoryStat;
  private final int fileFd;
  private final Darwin.Stat fileStat;
  // Native memory the bytes are copied to for writing, allocated once.
  private final Memory buffer = new Memory(BUFFER_BYTES);
  private long size;
  private boolean published;
  // The destination's name while publication has taken it without yet moving the file there.
  private Path claim;
  private Object claimKey;

  private DarwinDownloadStage(Path directory, int directoryFd, Darwin.Stat directoryStat, int fileFd, Darwin.Stat fileStat) {
    this.directory = directory;
    this.directoryPath = Darwin.path(directory);
    this.directoryFd = directoryFd;
    this.directoryStat = directoryStat;
    this.fileFd = fileFd;
    this.fileStat = fileStat;
  }

  static DownloadStage create(Path destination) throws IOException {
    Darwin.requireAvailable();
    Path parent = destination.getParent();
    if ((Darwin.volumeFlags(parent) & Darwin.MNT_IGNORE_OWNERSHIP) != 0) {
      throw notPrivate(parent, "The volume ignores file ownership");
    }
    Path directory = createDirectory(parent, directoryPrefix(destination));
    byte[] directoryPath = Darwin.path(directory);
    int directoryFd = -1;
    // The directory, once the download is sure it is its own; until then, nothing in it is changed,
    // and it is never deleted.
    Darwin.Stat directoryStat = null;
    int fileFd = -1;
    Darwin.Stat fileStat = null;
    try {
      Darwin.Stat created = Darwin.fstatat(Darwin.AT_FDCWD, directoryPath);
      directoryFd = Darwin.open(directoryPath, Darwin.O_RDONLY | Darwin.O_DIRECTORY | Darwin.O_NOFOLLOW | Darwin.O_CLOEXEC);
      if (directoryFd < 0) {
        int error = Darwin.errno();
        if (error == Darwin.ENOENT || error == Darwin.ENOTDIR || error == Darwin.ELOOP) {
          throw replacedDirectory();
        }
        throw Darwin.exception(directory.toString(), error);
      }
      Darwin.Stat opened = Darwin.fstat(directoryFd);
      if (!opened.isDirectory() || !opened.isSameFile(created) || opened.owner != Darwin.geteuid() || (opened.mode & 07777) != 0700) {
        throw replacedDirectory();
      }
      if ((Darwin.volumeFlags(directoryFd) & Darwin.MNT_IGNORE_OWNERSHIP) != 0) {
        throw notPrivate(parent, "The volume ignores file ownership");
      }
      if (hasAcl(directoryFd)) {
        throw notPrivate(parent, "The download's own directory had an ACL when it was checked");
      }
      directoryStat = opened;
      fileFd = Darwin.openat(directoryFd, FILE_NAME, Darwin.O_RDWR | Darwin.O_CREAT | Darwin.O_EXCL | Darwin.O_NOFOLLOW | Darwin.O_CLOEXEC,
          0600);
      if (fileFd < 0) {
        int error = Darwin.errno();
        if (error == Darwin.EEXIST) {
          throw replacedDirectory();
        }
        throw Darwin.exception(directory.resolve(NAME).toString(), error);
      }
      fileStat = Darwin.fstat(fileFd);
      return new DarwinDownloadStage(directory, directoryFd, directoryStat, fileFd, fileStat);
    } catch (IOException | RuntimeException | Error e) {
      if (fileFd >= 0) {
        deleteIfOwned(directoryFd, FILE_NAME, fileStat);
        Darwin.close(fileFd, "file");
      }
      if (directoryFd >= 0) {
        Darwin.close(directoryFd, "directory");
      }
      if (directoryStat != null) {
        deleteDirectoryIfOwned(directoryPath, directoryStat);
      }
      throw e;
    }
  }

  // Reopens a retained stage (see DownloadStage.reopen), checked as create checks it: the directory,
  // opened without following a link, of the recorded device and inode, the user's own, rwx------,
  // without an ACL, on a volume that does not ignore ownership; then the file, opened through the
  // directory without following a link, of the recorded device and inode, the user's own, rw-------,
  // without an ACL.
  static DownloadStage reopen(Path destination, Identity identity) throws IOException {
    Darwin.requireAvailable();
    Path directory = destination.getParent().resolve(identity.directory);
    byte[] directoryPath = Darwin.path(directory);
    int directoryFd = Darwin.open(directoryPath, Darwin.O_RDONLY | Darwin.O_DIRECTORY | Darwin.O_NOFOLLOW | Darwin.O_CLOEXEC);
    if (directoryFd < 0) {
      int error = Darwin.errno();
      if (error == Darwin.ENOENT) {
        return null;
      }
      if (error == Darwin.ENOTDIR || error == Darwin.ELOOP) {
        throw changed(directory, "its directory is a link, or not a directory");
      }
      throw Darwin.exception(directory.toString(), error);
    }
    int fileFd = -1;
    boolean returned = false;
    try {
      Darwin.Stat directoryStat = Darwin.fstat(directoryFd);
      if (!directoryStat.isDirectory() || !directoryStat.identity().equals(identity.directoryId) || directoryStat.owner != Darwin.geteuid()
          || (directoryStat.mode & 07777) != 0700 || (Darwin.volumeFlags(directoryFd) & Darwin.MNT_IGNORE_OWNERSHIP) != 0
          || hasAcl(directoryFd)) {
        throw changed(directory, "its directory is not the one the download created, or is no longer private");
      }
      fileFd = Darwin.openat(directoryFd, FILE_NAME, Darwin.O_RDWR | Darwin.O_NOFOLLOW | Darwin.O_CLOEXEC, 0);
      if (fileFd < 0) {
        int error = Darwin.errno();
        if (error == Darwin.ENOENT) {
          // Published, or removed: the directory, the download's own, is removed if it is empty.
          deleteDirectoryIfOwned(directoryPath, directoryStat);
          return null;
        }
        if (error == Darwin.ELOOP) {
          throw changed(directory, "its file is a link");
        }
        throw Darwin.exception(directory.resolve(NAME).toString(), error);
      }
      Darwin.Stat fileStat = Darwin.fstat(fileFd);
      if (!fileStat.isRegularFile() || !fileStat.identity().equals(identity.fileId) || fileStat.owner != Darwin.geteuid()
          || (fileStat.mode & 07777) != 0600 || hasAcl(fileFd)) {
        throw changed(directory, "its file is not the one the download wrote, or is no longer private");
      }
      DarwinDownloadStage stage = new DarwinDownloadStage(directory, directoryFd, directoryStat, fileFd, fileStat);
      stage.size = fileStat.size;
      returned = true;
      return stage;
    } finally {
      if (!returned) {
        if (fileFd >= 0) {
          Darwin.close(fileFd, "file");
        }
        Darwin.close(directoryFd, "directory");
      }
    }
  }

  // The device and inode of the regular file at path, not following a link (see
  // DownloadStage.fileIdentity).
  static String fileIdentity(Path path) throws IOException {
    Darwin.requireAvailable();
    Darwin.Stat named = Darwin.fstatat(Darwin.AT_FDCWD, Darwin.path(path));
    return named != null && named.isRegularFile() ? named.identity() : null;
  }

  // The stage's directory and file, by their devices and inodes.
  @Override
  Identity identity() {
    return new Identity(directory.getFileName().toString(), directoryStat.identity(), fileStat.identity());
  }

  // Where permissions keep no one out, such as on a volume that ignores ownership, nothing can be
  // kept private.
  private static IOException notPrivate(Path directory, String reason) {
    return new FileSystemException(directory.toString(), null, reason + ", so the download could not be kept private and was stopped");
  }

  // Creates a directory of a new name, with prefix and a random suffix, rwx------ less what the
  // umask removes, and without the entries of parent's ACL that it would otherwise inherit.
  private static Path createDirectory(Path parent, String prefix) throws IOException {
    Pointer filesec = Darwin.inheritsAcl(parent) ? withoutInheritance() : null;
    try {
      for (int attempt = 0; ; attempt++) {
        Path directory = parent.resolve(prefix + Long.toUnsignedString(RANDOM.nextLong()));
        byte[] path = Darwin.path(directory);
        if ((filesec != null ? Darwin.mkdirx_np(path, filesec) : Darwin.mkdir(path, (short) 0700)) == 0) {
          return directory;
        }
        int error = Darwin.errno();
        if (error != Darwin.EEXIST || attempt >= 100) {
          throw Darwin.exception(directory.toString(), error);
        }
      }
    } finally {
      if (filesec != null) {
        Darwin.filesec_free(filesec);
      }
    }
  }

  // The file security for mkdirx_np: mode rwx------ and an initial ACL that is empty and flagged
  // ACL_FLAG_NO_INHERIT, so that the kernel gives the directory none of its parent's entries.
  static Pointer withoutInheritance() throws IOException {
    Pointer filesec = Darwin.filesec_init();
    if (filesec == null) {
      throw Darwin.exception(null, Darwin.errno());
    }
    Pointer acl = Darwin.acl_init(0);
    try (Memory flagset = new Memory(Native.POINTER_SIZE); Memory mode = new Memory(2); Memory aclRef = new Memory(Native.POINTER_SIZE)) {
      if (acl == null || Darwin.acl_get_flagset_np(acl, flagset) != 0 || Darwin.acl_add_flag_np(flagset.getPointer(0), Darwin.ACL_FLAG_NO_INHERIT) != 0
          || Darwin.acl_set_flagset_np(acl, flagset.getPointer(0)) != 0) {
        throw Darwin.exception(null, Darwin.errno());
      }
      mode.setShort(0, (short) 0700);
      aclRef.setPointer(0, acl);
      if (Darwin.filesec_set_property(filesec, Darwin.FILESEC_MODE, mode) != 0
          || Darwin.filesec_set_property(filesec, Darwin.FILESEC_ACL, aclRef) != 0) {
        throw Darwin.exception(null, Darwin.errno());
      }
      Pointer result = filesec;
      filesec = null;
      return result;
    } finally {
      if (acl != null) {
        Darwin.acl_free(acl);
      }
      if (filesec != null) {
        Darwin.filesec_free(filesec);
      }
    }
  }

  static boolean hasAcl(int fd) throws IOException {
    Pointer acl = Darwin.acl_get_fd_np(fd, Darwin.ACL_TYPE_EXTENDED);
    if (acl == null) {
      int error = Darwin.errno();
      if (error == Darwin.ENOENT || error == Darwin.ENOTSUP) {
        return false;
      }
      throw Darwin.exception(null, error);
    }
    try (Memory entry = new Memory(Native.POINTER_SIZE)) {
      return Darwin.acl_get_entry(acl, Darwin.ACL_FIRST_ENTRY, entry) == 0;
    } finally {
      Darwin.acl_free(acl);
    }
  }

  @Override
  synchronized void restart() throws IOException {
    if (Darwin.ftruncate(fileFd, 0) != 0) {
      throw Darwin.exception(directory.resolve(NAME).toString(), Darwin.errno());
    }
    size = 0;
  }

  @Override
  synchronized void write(byte[] bytes, int count) throws IOException {
    writeAt(size, bytes, 0, count);
  }

  // Through the one native buffer, so one write at a time. The size is the end of the furthest
  // bytes written, which publish checks the file against.
  @Override
  synchronized void writeAt(long position, byte[] bytes, int offset, int count) throws IOException {
    for (int at = 0; at < count; ) {
      int length = Math.min(BUFFER_BYTES, count - at);
      buffer.write(0, bytes, offset + at, length);
      for (int done = 0; done < length; ) {
        long written = Darwin.pwrite(fileFd, done == 0 ? buffer : buffer.share(done), length - done, position + at + done);
        if (written < 0) {
          int error = Darwin.errno();
          if (error == Darwin.EINTR) {
            continue;
          }
          throw Darwin.exception(directory.resolve(NAME).toString(), error);
        }
        if (written == 0) {
          throw new FileSystemException(directory.resolve(NAME).toString(), null, "No more bytes could be written");
        }
        done += (int) written;
      }
      at += length;
    }
    size = Math.max(size, position + count);
  }

  // Through the same native buffer, so one read or write at a time; positional, as writes are.
  @Override
  synchronized int readAt(long position, byte[] bytes, int offset, int count) throws IOException {
    int length = Math.min(BUFFER_BYTES, count);
    while (true) {
      long read = Darwin.pread(fileFd, buffer, length, position);
      if (read < 0) {
        int error = Darwin.errno();
        if (error == Darwin.EINTR) {
          continue;
        }
        throw Darwin.exception(directory.resolve(NAME).toString(), error);
      }
      if (read == 0) {
        return -1;
      }
      buffer.read(0, bytes, offset, (int) read);
      return (int) read;
    }
  }

  // The descriptor's size, not size: that is only the end of the bytes written whole.
  @Override
  synchronized long size() throws IOException {
    return Darwin.fstat(fileFd).size;
  }

  @Override
  synchronized void truncate(long length) throws IOException {
    if (length >= size()) {
      return;
    }
    if (Darwin.ftruncate(fileFd, length) != 0) {
      throw Darwin.exception(directory.resolve(NAME).toString(), Darwin.errno());
    }
    size = Math.min(size, length);
  }

  // As FileChannel.force(true) does on macOS (Darwin.force).
  @Override
  void force() throws IOException {
    Darwin.force(fileFd, directory.resolve(NAME).toString());
  }

  @Override
  void publish(Path target, boolean replace) throws IOException {
    requireOwned();
    if (!replace && Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
      throw new FileAlreadyExistsException(target.toString());
    }
    // A link at target is replaced, not written through; what it leads to decides the access.
    if (Files.exists(target)) {
      keepRestrictionsOf(target);
    } else {
      giveNewFilePermissions(target.getParent());
    }
    if (replace) {
      if (Darwin.renameat(directoryFd, FILE_NAME, Darwin.AT_FDCWD, Darwin.path(target)) != 0) {
        throw Darwin.exception(target.toString(), Darwin.errno());
      }
    } else {
      publishWithoutReplacing(target);
    }
    published = true;
  }

  // The file's name in the held directory must still lead to the file the download wrote, whole.
  private void requireOwned() throws IOException {
    Darwin.Stat named = Darwin.fstatat(directoryFd, FILE_NAME);
    Darwin.Stat written = Darwin.fstat(fileFd);
    if (named == null || !named.isRegularFile() || !named.isSameFile(fileStat) || written.size != size) {
      throw replacedFile();
    }
  }

  // A rename that does not replace fails, atomically, if a file has appeared at target meanwhile.
  // Where the file system cannot rename that way, the name is first taken, atomically, with an
  // empty file no one can open, which the rename then replaces.
  private void publishWithoutReplacing(Path target) throws IOException {
    byte[] targetPath = Darwin.path(target);
    int error;
    try {
      if (Darwin.renameatx_np(directoryFd, FILE_NAME, Darwin.AT_FDCWD, targetPath, Darwin.RENAME_EXCL) == 0) {
        return;
      }
      error = Darwin.errno();
    } catch (UnsatisfiedLinkError e) {
      // macOS before 10.12 has no renameatx_np.
      error = Darwin.ENOTSUP;
    }
    if (error != Darwin.ENOTSUP && error != Darwin.EINVAL) {
      throw Darwin.exception(target.toString(), error);
    }
    Files.createFile(target, PosixFilePermissions.asFileAttribute(EnumSet.noneOf(PosixFilePermission.class)));
    claim = target;
    claimKey = key(target);
    if (Darwin.renameat(directoryFd, FILE_NAME, Darwin.AT_FDCWD, targetPath) != 0) {
      throw Darwin.exception(target.toString(), Darwin.errno());
    }
    claim = null;
  }

  // The file being replaced keeps its access: the new file gets its permissions, its group and its
  // ACL. A file this user could not write, or that belongs to another user, is not replaced.
  private void keepRestrictionsOf(Path target) throws IOException {
    if (!Files.isWritable(target)) {
      throw new AccessDeniedException(target.toString(), null, "The destination cannot be written, so the download did not replace it");
    }
    Map<String, Object> existing = Files.readAttributes(target, "unix:uid,gid,permissions");
    if ((Integer) existing.get("uid") != fileStat.owner) {
      throw new AccessDeniedException(target.toString(), null, "The destination belongs to another user, so the download did not replace it");
    }
    int group = (Integer) existing.get("gid");
    exposed = true;
    if (group != fileStat.group && Darwin.fchown(fileFd, -1, group) != 0) {
      throw Darwin.exception(directory.resolve(NAME).toString(), Darwin.errno());
    }
    @SuppressWarnings("unchecked")
    Set<PosixFilePermission> permissions = (Set<PosixFilePermission>) existing.get("permissions");
    setPermissions(permissions);
    setAcl(Darwin.acl(target));
  }

  // A new file created in the private directory gets the permissions new files normally get,
  // which the published file then takes on, with the ACL a new file in the target's directory gets.
  private void giveNewFilePermissions(Path targetDirectory) throws IOException {
    int fd = Darwin.openat(directoryFd, NEW_FILE_NAME, Darwin.O_WRONLY | Darwin.O_CREAT | Darwin.O_EXCL | Darwin.O_NOFOLLOW | Darwin.O_CLOEXEC,
        0666);
    if (fd < 0) {
      throw Darwin.exception(directory.resolve(NEW_FILE).toString(), Darwin.errno());
    }
    int mode;
    try {
      mode = Darwin.fstat(fd).mode & 0777;
    } finally {
      Darwin.close(fd, "file");
      Darwin.unlinkat(directoryFd, NEW_FILE_NAME, 0);
    }
    exposed = true;
    if (Darwin.fchmod(fileFd, (short) mode) != 0) {
      throw Darwin.exception(directory.resolve(NAME).toString(), Darwin.errno());
    }
    setAcl(inheritedAcl(targetDirectory));
  }

  private void setPermissions(Set<PosixFilePermission> permissions) throws IOException {
    // PosixFilePermission lists the permissions in the order of their mode bits, from owner read
    // (0400) to others execute (0001).
    int mode = 0;
    for (PosixFilePermission permission : permissions) {
      mode |= 0400 >> permission.ordinal();
    }
    if (Darwin.fchmod(fileFd, (short) mode) != 0) {
      throw Darwin.exception(directory.resolve(NAME).toString(), Darwin.errno());
    }
  }

  // Gives the file acl, and frees it; the file, created without one, keeps none if acl is null.
  private void setAcl(Pointer acl) throws IOException {
    if (acl == null) {
      return;
    }
    try {
      if (Darwin.acl_set_fd_np(fileFd, acl, Darwin.ACL_TYPE_EXTENDED) != 0) {
        throw Darwin.exception(directory.resolve(NAME).toString(), Darwin.errno());
      }
    } finally {
      Darwin.acl_free(acl);
    }
  }

  // The ACL that the kernel gives a file created in directory, or null where it gives none: the
  // entries of the directory's ACL that files inherit, in their order, each marked inherited and
  // without flags of inheritance of its own, as kauth_acl_inherit in XNU's kern_authorization.c
  // makes them.
  private static Pointer inheritedAcl(Path directory) throws IOException {
    if (!Darwin.inheritsAcl(directory)) {
      return null;
    }
    Pointer source = Darwin.acl(directory);
    if (source == null) {
      return null;
    }
    // acl_init allocates room for the most entries an ACL can have, so acl_create_entry never moves
    // the ACL (see Libc's posix1e/acl.c and acl_entry.c).
    Pointer acl = Darwin.acl_init(0);
    boolean returned = false;
    try (Memory entry = new Memory(Native.POINTER_SIZE); Memory copy = new Memory(Native.POINTER_SIZE);
         Memory flagset = new Memory(Native.POINTER_SIZE); Memory aclRef = new Memory(Native.POINTER_SIZE)) {
      if (acl == null) {
        throw Darwin.exception(null, Darwin.errno());
      }
      aclRef.setPointer(0, acl);
      int inherited = 0;
      for (int id = Darwin.ACL_FIRST_ENTRY; Darwin.acl_get_entry(source, id, entry) == 0; id = Darwin.ACL_NEXT_ENTRY) {
        if (Darwin.acl_get_flagset_np(entry.getPointer(0), flagset) != 0) {
          throw Darwin.exception(null, Darwin.errno());
        }
        if (Darwin.acl_get_flag_np(flagset.getPointer(0), Darwin.ACL_ENTRY_FILE_INHERIT) != 1) {
          continue;
        }
        if (Darwin.acl_create_entry(aclRef, copy) != 0 || Darwin.acl_copy_entry(copy.getPointer(0), entry.getPointer(0)) != 0
            || Darwin.acl_get_flagset_np(copy.getPointer(0), flagset) != 0) {
          throw Darwin.exception(null, Darwin.errno());
        }
        Pointer flags = flagset.getPointer(0);
        Darwin.acl_add_flag_np(flags, Darwin.ACL_ENTRY_INHERITED);
        Darwin.acl_delete_flag_np(flags, Darwin.ACL_ENTRY_FILE_INHERIT);
        Darwin.acl_delete_flag_np(flags, Darwin.ACL_ENTRY_DIRECTORY_INHERIT);
        Darwin.acl_delete_flag_np(flags, Darwin.ACL_ENTRY_LIMIT_INHERIT);
        Darwin.acl_delete_flag_np(flags, Darwin.ACL_ENTRY_ONLY_INHERIT);
        if (Darwin.acl_set_flagset_np(copy.getPointer(0), flags) != 0) {
          throw Darwin.exception(null, Darwin.errno());
        }
        inherited++;
      }
      returned = inherited > 0;
      return returned ? acl : null;
    } finally {
      if (acl != null && !returned) {
        Darwin.acl_free(acl);
      }
      Darwin.acl_free(source);
    }
  }

  // Deletes what the download created: the file, unless publication moved it, a name it took for
  // publication and did not use, and then the directory. A replacement is left where it is. A
  // retained stage only closes its descriptors.
  @Override
  public void close() {
    if (retained) {
      Darwin.close(fileFd, "file");
      Darwin.close(directoryFd, "directory");
      buffer.close();
      return;
    }
    if (!published) {
      deleteIfOwned(directoryFd, FILE_NAME, fileStat);
    }
    Darwin.close(fileFd, "file");
    if (claim != null) {
      deleteIfOwned(claim, claimKey, false);
    }
    deleteDirectoryIfOwned(directoryPath, directoryStat);
    Darwin.close(directoryFd, "directory");
    buffer.close();
  }

  // Deletes the file of that name in the held directory, if it is the one the download created.
  private static void deleteIfOwned(int directoryFd, byte[] name, Darwin.Stat expected) {
    try {
      Darwin.Stat named = Darwin.fstatat(directoryFd, name);
      if (named == null) {
        return;
      }
      if (!named.isRegularFile() || !named.isSameFile(expected)) {
        warnReplaced("file");
        return;
      }
      if (Darwin.unlinkat(directoryFd, name, 0) != 0) {
        int error = Darwin.errno();
        if (error != Darwin.ENOENT) {
          warnNotDeleted("file", Darwin.strerror(error));
        }
      }
    } catch (IOException e) {
      warnNotDeleted("file", e.getClass().getSimpleName());
    }
  }

  // Deletes the directory if its name still leads to the expected one, the one the download holds.
  private static void deleteDirectoryIfOwned(byte[] directoryPath, Darwin.Stat expected) {
    try {
      Darwin.Stat named = Darwin.fstatat(Darwin.AT_FDCWD, directoryPath);
      if (named == null) {
        return;
      }
      if (!named.isDirectory() || !named.isSameFile(expected)) {
        warnReplaced("directory");
        return;
      }
      if (Darwin.unlinkat(Darwin.AT_FDCWD, directoryPath, Darwin.AT_REMOVEDIR) != 0) {
        int error = Darwin.errno();
        if (error == Darwin.ENOTEMPTY) {
          warnNotEmpty();
        } else if (error != Darwin.ENOENT) {
          warnNotDeleted("directory", Darwin.strerror(error));
        }
      }
    } catch (IOException e) {
      warnNotDeleted("directory", e.getClass().getSimpleName());
    }
  }
}
