package com.files.net;

import com.files.exceptions.TransferCheckpointException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;

// A directory of the caller's in which transfers keep their checkpoint records (see
// UploadCheckpoint), held open while it is used, and trusted only once it has been checked through
// that handle: the effective user's own, a directory reached without following a link at its own
// name, and private to that user: on POSIX systems, permissions exactly rwx------; on macOS also
// without an ACL, on a volume that does not ignore ownership; on Windows, owned by the user, not a
// link, with a protected ACL granting that user, and no one else, full control, on a volume that
// keeps ACLs. A directory that does not exist yet is created that way, its parent must exist. One
// that exists but is not that way, such as a home directory, is refused, not changed: nothing in it is
// read. The directories above it are not checked: whoever can change them can only put there
// directories they own, which fail the check, or the user's own.
//
// A record is a file in the directory, reached only through the held directory, without following a
// link, and checked the same way: a regular file, the user's own, private (rw-------; on macOS
// without an ACL; on Windows with the protected owner-only ACL). It is held open, locked for one run
// of one transfer at a time, and read, appended to, made durable and deleted only through that handle.
// Records are written in place, never renamed (UploadCheckpoint), so whether a record survives a
// crash depends only on its file being made durable, not its directory.
abstract class CheckpointStore implements AutoCloseable {
  // The most a record may hold.
  static final long MAX_RECORD_BYTES = 64L * 1024 * 1024;

  final Path directory;

  CheckpointStore(Path directory) {
    this.directory = directory;
  }

  // Opens the checkpoint directory, creating it if it does not exist, or refuses it as
  // TransferCheckpointException (UNTRUSTED).
  static CheckpointStore open(Path directory) throws IOException {
    Path absolute = directory.toAbsolutePath().normalize();
    if (absolute.getParent() == null || absolute.getFileName() == null) {
      throw untrusted(absolute, "it is the root of a file system, which is not a private directory");
    }
    String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
    try {
      // The operating system knows only the default file system's paths.
      if (absolute.getFileSystem() == FileSystems.getDefault()) {
        if (os.startsWith("mac")) {
          return DarwinCheckpointStore.open(absolute);
        }
        if (os.startsWith("windows")) {
          return WindowsCheckpointStore.open(absolute);
        }
      }
    } catch (LinkageError e) {
      throw NativeAccess.unavailable(e);
    }
    return PosixCheckpointStore.open(absolute);
  }

  // Opens the record name, a plain file name, creating it empty when there is none, and locks it for
  // this run alone: IN_USE when another run holds it, UNTRUSTED when it is not private.
  abstract Record lock(String name) throws IOException;

  @Override
  public abstract void close();

  static TransferCheckpointException untrusted(Path directory, String why) {
    return new TransferCheckpointException(TransferCheckpointException.Reason.UNTRUSTED, directory,
        "The checkpoint directory " + directory + " cannot be trusted: " + why + ". Use a directory of your own that only you can use"
            + " (on POSIX systems, rwx------), or one that does not exist yet, which is created that way");
  }

  static TransferCheckpointException inUse(Path directory, String name) {
    return new TransferCheckpointException(TransferCheckpointException.Reason.IN_USE, directory,
        "Another run of the same transfer holds its checkpoint record " + name + " in " + directory);
  }

  // One record, held and locked. Used by one thread at a time.
  abstract static class Record implements AutoCloseable {
    abstract long size() throws IOException;

    // The whole record; its size has been checked against MAX_RECORD_BYTES.
    abstract byte[] read() throws IOException;

    // Writes bytes at the end of the record.
    abstract void append(byte[] bytes) throws IOException;

    abstract void truncate(long length) throws IOException;

    // Makes what was written durable, as FileChannel.force(true) does.
    abstract void force() throws IOException;

    // Makes the record's name in the directory durable, where the platform can. A record whose name
    // did not last is lost in a crash, and its transfer starts over; nothing it recorded is relied
    // on without the record.
    abstract void forceName();

    // Whether the record's name still leads to the record held: false once another run removed or
    // replaced it, after this one opened it and before it locked it.
    abstract boolean isNamed() throws IOException;

    // Removes the record's name, while it is held, then closes it.
    abstract void delete() throws IOException;

    @Override
    public abstract void close();
  }
}
