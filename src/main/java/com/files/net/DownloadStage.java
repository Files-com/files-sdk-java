package com.files.net;

import com.files.exceptions.TransferCheckpointException;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The file a download writes before it is published, and the directory of its own that holds it,
// beside the destination. Until the download has every byte and has checked them, only the current
// user can read the file, and nothing but the download can change what is in the directory. The
// file's bytes are written only through the handle that created it. Publishing moves that file, and
// no other, into place; a stage that someone replaced is never published, written or deleted.
//
// How the stage keeps that promise depends on the platform. On macOS and Windows, for a path of the
// default file system, it uses the operating system through JNA, because Java can neither see nor
// prevent an ACL inherited on macOS, nor create a Windows file or directory whose ACL inherits
// nothing; a download stops before it creates anything where JNA cannot be loaded. Elsewhere it
// uses Java's own file system API.
//
// A download that keeps a checkpoint (DownloadCheckpoint) keeps its stage when it stops before
// publishing, retained: closing it then closes its handles and deletes nothing. A later run reopens
// it, only if its directory and file are still the ones the download created, by the identities the
// checkpoint recorded, and still private to the user, checked through the handles reopening uses, as
// creating it checks them. A publication that began changing the file's access (exposed) leaves the
// stage to be deleted, not retained: its file may no longer be private.
abstract class DownloadStage implements AutoCloseable {
  private static final Logger log = LoggerFactory.getLogger(DownloadStage.class);
  static final String NAME = "download";
  static final String NEW_FILE = "new-file";

  // Whether closing the stage keeps what the download created; and whether publication began
  // changing the file's access.
  boolean retained;
  boolean exposed;

  // A stage for a download to destination, which must be an absolute path.
  static DownloadStage create(Path destination) throws IOException {
    String os = os();
    try {
      // The operating system knows only the default file system's paths.
      if (destination.getFileSystem() == FileSystems.getDefault()) {
        if (os.startsWith("mac")) {
          return DarwinDownloadStage.create(destination);
        }
        if (os.startsWith("windows")) {
          return WindowsDownloadStage.create(destination);
        }
      }
    } catch (LinkageError e) {
      throw NativeAccess.unavailable(e);
    }
    return PosixDownloadStage.create(destination);
  }

  // Reopens the stage identity names, for a download to destination, an absolute path: null when its
  // directory is gone, or holds no file any more (the directory, if the download's own and empty, is
  // then removed); TransferCheckpointException STAGE_CHANGED when what its names lead to is not what
  // the download created, or is no longer private.
  static DownloadStage reopen(Path destination, Identity identity) throws IOException {
    if (!isStageName(destination, identity.directory)) {
      throw changed(destination.getParent(), "its directory's name is not one the download gives");
    }
    String os = os();
    try {
      if (destination.getFileSystem() == FileSystems.getDefault()) {
        if (os.startsWith("mac")) {
          return DarwinDownloadStage.reopen(destination, identity);
        }
        if (os.startsWith("windows")) {
          return WindowsDownloadStage.reopen(destination, identity);
        }
      }
    } catch (LinkageError e) {
      throw NativeAccess.unavailable(e);
    }
    return PosixDownloadStage.reopen(destination, identity);
  }

  // The identity of the regular file at path, not following a link, as identity() gives a stage's
  // file; null when there is none, or it is not a regular file.
  static String fileIdentity(Path path) throws IOException {
    String os = os();
    try {
      if (path.getFileSystem() == FileSystems.getDefault()) {
        if (os.startsWith("mac")) {
          return DarwinDownloadStage.fileIdentity(path);
        }
        if (os.startsWith("windows")) {
          return WindowsDownloadStage.fileIdentity(path);
        }
      }
    } catch (LinkageError e) {
      throw NativeAccess.unavailable(e);
    }
    return PosixDownloadStage.fileIdentity(path);
  }

  private static String os() {
    return System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
  }

  // Whether name is one the download gives its directory: the prefix for destination, then the
  // decimal digits of a random number.
  static boolean isStageName(Path destination, String name) {
    String prefix = directoryPrefix(destination);
    if (name == null || !name.startsWith(prefix) || name.length() == prefix.length()) {
      return false;
    }
    for (int i = prefix.length(); i < name.length(); i++) {
      if (name.charAt(i) < '0' || name.charAt(i) > '9') {
        return false;
      }
    }
    return true;
  }

  static TransferCheckpointException changed(Path directory, String why) {
    return new TransferCheckpointException(TransferCheckpointException.Reason.STAGE_CHANGED, directory, "The partial download its"
        + " checkpoint records cannot be continued: " + why + ". It was left as it is; discard the checkpoint to download the file again");
  }

  // What a checkpoint records of the stage, which reopening it must find again.
  abstract Identity identity() throws IOException;

  // Keeps what the download created when the stage is closed.
  void retain() {
    retained = true;
  }

  // The prefix of the stage directory's name, which shows which download it belongs to; a long name
  // is shortened to stay within file name limits once a random suffix is added.
  static String directoryPrefix(Path destination) {
    String name = destination.getFileName().toString();
    return "." + name.substring(0, name.offsetByCodePoints(0, Math.min(40, name.codePointCount(0, name.length())))) + ".";
  }

  // Empties the file, for a download that starts over.
  abstract void restart() throws IOException;

  abstract void write(byte[] bytes, int count) throws IOException;

  // Writes count bytes of bytes, from offset, at position in the file: a range of a download
  // fetched in parts, which may leave earlier ranges to other threads. Safe to call from several
  // threads at once; each call's bytes are written whole, at their own position.
  abstract void writeAt(long position, byte[] bytes, int offset, int count) throws IOException;

  // Reads at most count bytes, at least one, of the file at position into bytes from offset, through
  // the handle that created it, never by name: how many it read, or -1 at or past the file's end.
  // Only a stage whose file a channel reads (NativeFileStage) needs it; the default refuses.
  int readAt(long position, byte[] bytes, int offset, int count) throws IOException {
    throw new UnsupportedOperationException("This download stage's file cannot be read");
  }

  // The file's size, as its handle reports it now: after a write that failed part way, it may count
  // bytes that write changed. The default refuses, as readAt's does.
  long size() throws IOException {
    throw new UnsupportedOperationException("This download stage's file has no size to report");
  }

  // Shortens the file, through its handle, to length bytes; a file no longer than that is left as it
  // is, never grown. The default refuses, as readAt's does.
  void truncate(long length) throws IOException {
    throw new UnsupportedOperationException("This download stage's file cannot be truncated");
  }

  // Makes the written bytes durable.
  abstract void force() throws IOException;

  // Moves the file into place at target, an absolute path: replacing a file already there when
  // replace is set, and otherwise only if none is.
  abstract void publish(Path target, boolean replace) throws IOException;

  // Deletes what the download created, unless publication moved it, leaving a replacement where it
  // is; or, retained, only closes its handles.
  @Override
  public abstract void close();

  static StageReplaced replacedDirectory() {
    return new StageReplaced("The download's own directory was replaced before anything was written to it, so the download was stopped");
  }

  static IOException replacedFile() {
    return new IOException("The download's own file was replaced before it could be published, so the destination was left as it was");
  }

  static void warnReplaced(String what) {
    log.warn("The unfinished download's staging {} was replaced by another; it was left in place", what);
  }

  static void warnNotDeleted(String what, String reason) {
    log.warn("Could not delete the unfinished download's staging {} ({})", what, reason);
  }

  static void warnNotEmpty() {
    log.warn("The unfinished download's staging directory holds a file it did not create; it was left in place");
  }

  // Deletes path, a file or a directory the download created, if its name still leads to what has
  // expectedKey; a replacement is left where it is.
  static void deleteIfOwned(Path path, Object expectedKey, boolean isDirectory) {
    try {
      BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
      boolean owned = (isDirectory ? attributes.isDirectory() : attributes.isRegularFile())
          && Objects.equals(attributes.fileKey(), expectedKey);
      if (!owned) {
        warnReplaced(isDirectory ? "directory" : "file");
        return;
      }
      Files.delete(path);
    } catch (NoSuchFileException e) {
      // Already gone.
    } catch (DirectoryNotEmptyException e) {
      warnNotEmpty();
    } catch (IOException e) {
      warnNotDeleted(isDirectory ? "directory" : "file", e.getClass().getSimpleName());
    }
  }

  static Object key(Path path) throws IOException {
    return Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).fileKey();
  }

  // A stage as a checkpoint records it: the name of its directory, in the destination's directory,
  // and the identities of the directory and of the file in it (a file key, a device and inode, or a
  // volume and file ID).
  static final class Identity {
    final String directory;
    final String directoryId;
    final String fileId;

    Identity(String directory, String directoryId, String fileId) {
      this.directory = directory;
      this.directoryId = directoryId;
      this.fileId = fileId;
    }
  }

  // The download's own directory was replaced before the download used it.
  static final class StageReplaced extends IOException {
    private static final long serialVersionUID = 1L;

    StageReplaced(String message) {
      super(message);
    }
  }
}
