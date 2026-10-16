package com.files.net;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The checkpoint store on macOS, held through file descriptors of the C library (see Darwin), as a
// download's stage is (DarwinDownloadStage). The directory is opened without following a link and
// checked through its descriptor: a directory, the effective user's own, rwx------, without an ACL,
// on a volume that does not ignore ownership. macOS checks an ACL before the permissions, so a new
// directory is made, as the stage's is, with mkdirx_np and an empty ACL that inherits nothing, where
// its parent's ACL would otherwise be inherited. A record is opened through the directory's
// descriptor, without following a link, and locked as it is opened (O_EXLOCK, flock's exclusive
// lock), then checked through its own descriptor: a regular file, the user's own, rw-------,
// without an ACL.
final class DarwinCheckpointStore extends CheckpointStore {
  private static final Logger log = LoggerFactory.getLogger(DarwinCheckpointStore.class);
  private static final int CHUNK_BYTES = 64 * 1024;

  private final int directoryFd;

  private DarwinCheckpointStore(Path directory, int directoryFd) {
    super(directory);
    this.directoryFd = directoryFd;
  }

  static CheckpointStore open(Path directory) throws IOException {
    Darwin.requireAvailable();
    byte[] path = Darwin.path(directory);
    int fd = openDirectory(path);
    if (fd < 0 && Darwin.errno() == Darwin.ENOENT) {
      create(directory, path);
      fd = openDirectory(path);
    }
    if (fd < 0) {
      int error = Darwin.errno();
      if (error == Darwin.ENOTDIR || error == Darwin.ELOOP) {
        throw untrusted(directory, error == Darwin.ELOOP ? "it is a link" : "it is not a directory");
      }
      throw Darwin.exception(directory.toString(), error);
    }
    try {
      Darwin.Stat opened = Darwin.fstat(fd);
      if (!opened.isDirectory()) {
        throw untrusted(directory, "it is not a directory");
      }
      if (opened.owner != Darwin.geteuid()) {
        throw untrusted(directory, "it belongs to another user");
      }
      if ((opened.mode & 07777) != 0700) {
        throw untrusted(directory, "its permissions are " + Integer.toOctalString(opened.mode & 07777) + ", not 700 (rwx------)");
      }
      if ((Darwin.volumeFlags(fd) & Darwin.MNT_IGNORE_OWNERSHIP) != 0) {
        throw untrusted(directory, "its volume ignores file ownership");
      }
      if (DarwinDownloadStage.hasAcl(fd)) {
        throw untrusted(directory, "it has an ACL");
      }
      return new DarwinCheckpointStore(directory, fd);
    } catch (IOException | RuntimeException | Error e) {
      Darwin.close(fd, "checkpoint directory");
      throw e;
    }
  }

  private static int openDirectory(byte[] path) {
    return Darwin.open(path, Darwin.O_RDONLY | Darwin.O_DIRECTORY | Darwin.O_NOFOLLOW | Darwin.O_CLOEXEC);
  }

  // Makes the directory, rwx------ less what the umask removes, without the entries of its parent's
  // ACL that it would otherwise inherit. One made meanwhile is checked like any other.
  private static void create(Path directory, byte[] path) throws IOException {
    Pointer filesec = Darwin.inheritsAcl(directory.getParent()) ? DarwinDownloadStage.withoutInheritance() : null;
    try {
      int result = filesec != null ? Darwin.mkdirx_np(path, filesec) : Darwin.mkdir(path, (short) 0700);
      if (result != 0 && Darwin.errno() != Darwin.EEXIST) {
        throw Darwin.exception(directory.toString(), Darwin.errno());
      }
    } finally {
      if (filesec != null) {
        Darwin.filesec_free(filesec);
      }
    }
  }

  @Override
  Record lock(String name) throws IOException {
    byte[] entry = Darwin.name(name);
    int fd = Darwin.openat(directoryFd, entry, Darwin.O_RDWR | Darwin.O_CREAT | Darwin.O_NOFOLLOW | Darwin.O_CLOEXEC | Darwin.O_EXLOCK
        | Darwin.O_NONBLOCK, 0600);
    if (fd < 0) {
      int error = Darwin.errno();
      if (error == Darwin.EAGAIN) {
        throw inUse(directory, name);
      }
      if (error == Darwin.ELOOP) {
        throw untrusted(directory, "its record " + name + " is a link");
      }
      throw Darwin.exception(directory.resolve(name).toString(), error);
    }
    try {
      Darwin.Stat stat = Darwin.fstat(fd);
      if (!stat.isRegularFile() || stat.owner != Darwin.geteuid() || (stat.mode & 07777) != 0600 || DarwinDownloadStage.hasAcl(fd)) {
        throw untrusted(directory, "its record " + name + " is not a private file of this user's (rw------- without an ACL)");
      }
      return new DarwinRecord(name, entry, fd, stat);
    } catch (IOException | RuntimeException | Error e) {
      Darwin.close(fd, "checkpoint record");
      throw e;
    }
  }

  @Override
  public void close() {
    Darwin.close(directoryFd, "checkpoint directory");
  }

  private void forceDirectory() {
    try {
      Darwin.force(directoryFd, directory.toString());
    } catch (IOException e) {
      log.debug("Could not make the checkpoint directory's entries durable", e);
    }
  }

  private final class DarwinRecord extends Record {
    private final String name;
    private final byte[] entry;
    private final int fd;
    private final Darwin.Stat identity;
    private final Memory buffer = new Memory(CHUNK_BYTES);
    private boolean closed;

    DarwinRecord(String name, byte[] entry, int fd, Darwin.Stat identity) {
      this.name = name;
      this.entry = entry;
      this.fd = fd;
      this.identity = identity;
    }

    @Override
    long size() throws IOException {
      return Darwin.fstat(fd).size;
    }

    @Override
    byte[] read() throws IOException {
      long size = size();
      if (size > MAX_RECORD_BYTES) {
        throw new IOException("The checkpoint record holds " + size + " bytes, more than a record may hold");
      }
      byte[] bytes = new byte[(int) size];
      for (int done = 0; done < bytes.length; ) {
        long count = Darwin.pread(fd, buffer, Math.min(CHUNK_BYTES, bytes.length - done), done);
        if (count < 0) {
          int error = Darwin.errno();
          if (error == Darwin.EINTR) {
            continue;
          }
          throw Darwin.exception(directory.resolve(name).toString(), error);
        }
        if (count == 0) {
          return java.util.Arrays.copyOf(bytes, done);
        }
        buffer.read(0, bytes, done, (int) count);
        done += (int) count;
      }
      return bytes;
    }

    @Override
    void append(byte[] bytes) throws IOException {
      long position = size();
      for (int offset = 0; offset < bytes.length; ) {
        int length = Math.min(CHUNK_BYTES, bytes.length - offset);
        buffer.write(0, bytes, offset, length);
        for (int done = 0; done < length; ) {
          long written = Darwin.pwrite(fd, done == 0 ? buffer : buffer.share(done), length - done, position + offset + done);
          if (written < 0) {
            int error = Darwin.errno();
            if (error == Darwin.EINTR) {
              continue;
            }
            throw Darwin.exception(directory.resolve(name).toString(), error);
          }
          if (written == 0) {
            throw new FileSystemException(directory.resolve(name).toString(), null, "No more bytes could be written");
          }
          done += (int) written;
        }
        offset += length;
      }
    }

    @Override
    void truncate(long length) throws IOException {
      if (Darwin.ftruncate(fd, length) != 0) {
        throw Darwin.exception(directory.resolve(name).toString(), Darwin.errno());
      }
    }

    // As FileChannel.force(true) does on macOS (Darwin.force).
    @Override
    void force() throws IOException {
      Darwin.force(fd, directory.resolve(name).toString());
    }

    @Override
    void forceName() {
      forceDirectory();
    }

    @Override
    boolean isNamed() throws IOException {
      return identity.isSameFile(Darwin.fstatat(directoryFd, entry));
    }

    @Override
    void delete() throws IOException {
      try {
        if (!isNamed()) {
          throw new FileNotFoundException("The checkpoint record " + name + " was removed or replaced while it was held");
        }
        if (Darwin.unlinkat(directoryFd, entry, 0) != 0) {
          throw Darwin.exception(directory.resolve(name).toString(), Darwin.errno());
        }
        forceDirectory();
      } finally {
        close();
      }
    }

    @Override
    public void close() {
      if (!closed) {
        closed = true;
        buffer.close();
        Darwin.close(fd, "checkpoint record");
      }
    }
  }
}
