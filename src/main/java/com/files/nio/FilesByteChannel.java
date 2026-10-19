package com.files.nio;

import com.files.models.File;
import com.files.net.NativeFileStage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.FileSystemException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;

/**
 * A channel on a file of a Files.com file system, as {@code Files.newByteChannel},
 * {@code Files.newInputStream} and {@code Files.newOutputStream} open it. It reads and writes a
 * private local copy of the file, which a stage in the file system's stage directory holds, and
 * publishes what it wrote when it is closed. The copy is read, written, filled and published only
 * through the handle that created it, never by its name.
 *
 * <p>Opening it for reading, or for changing an existing file, downloads the file into the copy, with
 * {@code Transfers.download} to a stream of the copy. The file's size, as the API describes it, is
 * first reserved in the stage quota, and the download is refused before it writes a byte past that
 * size: a file whose size the API does not give, or that does not fit, is refused before anything is
 * downloaded, and a failed download, which is not tried again from its start once some of it is
 * written, returns no channel and deletes its partial copy. Opening it for writing is described by
 * {@link FilesComFileSystems}: {@code CREATE} with {@code TRUNCATE_EXISTING}, or
 * {@code TRUNCATE_EXISTING} alone, first prepares the empty copy, so a stage that cannot be made
 * fails the open before the remote file is changed, then publishes the file empty before the channel
 * is returned; the file's description is the one that publication returns, and nothing is
 * downloaded. {@code WRITE}, {@code READ} with {@code WRITE}, and {@code APPEND}, without
 * {@code CREATE} or {@code TRUNCATE_EXISTING}, download the existing file and change its copy. Bytes
 * written beyond what is reserved are reserved before they are written; a write that does not fit
 * fails with a {@code FileSystemException}, writing nothing.
 *
 * <p>Reads and writes start at the channel's position and advance it by the bytes transferred; a
 * read at or past the end returns -1. With {@code APPEND}, each write goes to the copy's end.
 * Truncating to a size smaller than the copy's shortens it, and never grows it; a position past the
 * given size then moves to it. A channel not open for reading throws
 * {@code NonReadableChannelException} from {@code read}; one not open for writing throws
 * {@code NonWritableChannelException} from {@code write} and {@code truncate}; a closed one throws
 * {@code ClosedChannelException}. The channel is safe for use by several threads; its operations
 * take turns. A write or truncation of the copy that fails may have changed some of its bytes: the
 * failure is thrown, the position does not move past the bytes written whole, and the copy, whose
 * content is then uncertain, is no longer published.
 *
 * <p>Nothing is published before the channel is closed: flushing a stream on it does nothing
 * remotely. Closing a channel whose copy was changed uploads the copy once, read from the copy
 * itself, with {@code Transfers.upload} of its known length, and returns only once the upload has
 * completed; the copy is then deleted, as far as the stage's cleanup can. If a write to the copy had
 * failed, or the upload fails or its outcome is unknown, closing throws that failure, as a native
 * exception with its cause, and the remote file may or may not have changed. The copy is then kept,
 * for recovery, and is the caller's to read and delete. {@link FileSystemException#getOtherFile()}
 * gives the name its directory was created with, as the exception's reason says, or null where the
 * platform gave none. That name is a diagnostic, not checked again: a directory renamed or replaced
 * since may not hold the copy, and what is found by the name may not be it. A kept copy
 * no longer counts against the stage quota, which bounds open channels' copies, not kept ones. A
 * channel is closed whether or not its close succeeds; closing it again has no effect, and nothing is
 * uploaded again: a close called while another, such as the file system's, publishes waits for that
 * one to finish. An interrupt ends that wait early, keeping the thread's interrupt status; the close
 * that publishes still finishes, and reports its own outcome.
 */
public final class FilesByteChannel implements SeekableByteChannel {
  // The most bytes one read or write moves through the copy at once.
  private static final int BUFFER_BYTES = 64 * 1024;

  private final FilesFileSystem fileSystem;
  private final FilesPath path;
  private final NativeFileStage stage;
  private final File described;
  private final boolean readable;
  private final boolean writable;
  private final boolean append;
  private long position;
  private long reservedBytes;
  private boolean modified;
  // The first failure of a write or truncation of the copy, which left its content uncertain.
  private Exception localFailure;
  private volatile boolean closed;
  // Whether the close that closed the channel has finished with the copy; guarded by this.
  private boolean settled;
  // The copy's size, for the pending view of a file being written.
  private volatile long pendingSize;

  private FilesByteChannel(FilesFileSystem fileSystem, FilesPath path, NativeFileStage stage, File described, boolean readable,
      boolean writable, boolean append, long reservedBytes, long size) {
    this.fileSystem = fileSystem;
    this.path = path;
    this.stage = stage;
    this.described = described;
    this.readable = readable;
    this.writable = writable;
    this.append = append;
    this.reservedBytes = reservedBytes;
    this.pendingSize = size;
  }

  // Opens path with options, every option being checked before anything is changed.
  static FilesByteChannel open(FilesFileSystem fileSystem, FilesPath path, Set<? extends OpenOption> options,
      FileAttribute<?>... attributes) throws IOException {
    boolean read = false;
    boolean write = false;
    boolean append = false;
    boolean create = false;
    boolean truncate = false;
    for (OpenOption option : options) {
      if (option == StandardOpenOption.READ) {
        read = true;
      } else if (option == StandardOpenOption.WRITE) {
        write = true;
      } else if (option == StandardOpenOption.APPEND) {
        append = true;
      } else if (option == StandardOpenOption.CREATE) {
        create = true;
      } else if (option == StandardOpenOption.TRUNCATE_EXISTING) {
        truncate = true;
      } else if (option == StandardOpenOption.SPARSE || option == LinkOption.NOFOLLOW_LINKS) {
        // A hint, and there are no links: nothing to do.
      } else if (option == StandardOpenOption.CREATE_NEW) {
        throw FilesFileSystem.unsupported("CREATE_NEW needs an atomic exclusive create, which the Files.com API does not offer");
      } else if (option == StandardOpenOption.SYNC || option == StandardOpenOption.DSYNC) {
        throw FilesFileSystem.unsupported(option + " is not supported: a Files.com file is published when its channel closes,"
            + " not as it is written");
      } else if (option == StandardOpenOption.DELETE_ON_CLOSE) {
        throw FilesFileSystem.unsupported("DELETE_ON_CLOSE is not supported for a Files.com file");
      } else if (option == null) {
        throw new NullPointerException("option");
      } else {
        throw FilesFileSystem.unsupported("The open option " + option + " is not supported for a Files.com file");
      }
    }
    if (attributes.length > 0) {
      throw FilesFileSystem.unsupported("A Files.com file cannot be created with initial attributes");
    }
    if (append && read) {
      throw new IllegalArgumentException("READ + APPEND not allowed");
    }
    if (append && truncate) {
      throw new IllegalArgumentException("APPEND + TRUNCATE_EXISTING not allowed");
    }
    write |= append;
    if (write && create && !truncate) {
      throw FilesFileSystem.unsupported("CREATE without TRUNCATE_EXISTING needs an atomic create-if-missing, which the Files.com"
          + " API does not offer; open with CREATE and TRUNCATE_EXISTING, or open an existing file without CREATE");
    }
    fileSystem.checkOpen();
    if (path.isRoot()) {
      throw new FileSystemException(path.toString(), null, "Is a directory");
    }
    if (!write) {
      if (fileSystem.isBeingWritten(path)) {
        throw new FileSystemException(path.toString(), null,
            "A channel of this file system is writing the file; it can be read once that channel publishes it");
      }
      return openCopy(fileSystem, path, null, true, false, false);
    }
    Object claim = fileSystem.claimWriter(path);
    boolean opened = false;
    try {
      FilesByteChannel channel = truncate
          ? openEmpty(fileSystem, path, claim, read, create)
          : openCopy(fileSystem, path, claim, read, true, append);
      opened = true;
      return channel;
    } finally {
      if (!opened) {
        fileSystem.releaseWriter(path, claim);
      }
    }
  }

  // Opens a channel on a copy of the existing file, downloaded through the copy's own handle and
  // never past the size reserved for it.
  private static FilesByteChannel openCopy(FilesFileSystem fileSystem, FilesPath path, Object claim, boolean readable,
      boolean writable, boolean append) throws IOException {
    FilesFileSystem.Operation operation = writable ? FilesFileSystem.Operation.WRITE : FilesFileSystem.Operation.READ;
    File file = fileSystem.describeForDownload(path, operation);
    if (!"file".equals(file.getType())) {
      throw new FileSystemException(path.toString(), null, "directory".equals(file.getType()) ? "Is a directory" : "Not a regular file");
    }
    Long size = file.getSize();
    if (size == null || size < 0) {
      throw new FileSystemException(path.toString(), null,
          "The API did not give the file's size, so its local copy cannot be reserved in the stage quota");
    }
    fileSystem.reserve(path, size);
    NativeFileStage stage = null;
    try {
      try {
        stage = NativeFileStage.create(fileSystem.stageDirectory());
        fileSystem.client().transfers().download(file, stage.sink(size), fileSystem.downloadOptions());
      } catch (IOException | RuntimeException e) {
        throw fileSystem.nativeFailure(operation, path, null, e);
      }
      long held = stage.size();
      if (held != size) {
        throw new FileSystemException(path.toString(), null, "The download gave " + held + " of the " + size + " bytes Files.com described");
      }
      return start(fileSystem, path, claim, new FilesByteChannel(fileSystem, path, stage, file, readable, writable, append, size, held));
    } catch (IOException | RuntimeException e) {
      // The stage's cleanup is an attempt; one that throws all the same is kept with the failure
      // that ended the open, which is the one thrown, and the reservation is released either way.
      try {
        if (stage != null) {
          stage.close();
        }
      } catch (RuntimeException cleanup) {
        e.addSuppressed(cleanup);
      } finally {
        fileSystem.release(size);
      }
      throw e;
    }
  }

  // Opens a channel on an empty copy for a TRUNCATE_EXISTING open: the copy's stage is made first,
  // then the file is published empty, by truncating it or, with CREATE, by creating it if missing.
  private static FilesByteChannel openEmpty(FilesFileSystem fileSystem, FilesPath path, Object claim, boolean readable, boolean create)
      throws IOException {
    checkEmptyTarget(fileSystem, path, create);
    NativeFileStage stage;
    try {
      stage = NativeFileStage.create(fileSystem.stageDirectory());
    } catch (IOException | RuntimeException e) {
      throw fileSystem.nativeFailure(FilesFileSystem.Operation.WRITE, path, null, e);
    }
    try {
      File published = publishEmpty(fileSystem, path);
      return start(fileSystem, path, claim, new FilesByteChannel(fileSystem, path, stage, published, readable, true, false, 0, 0));
    } catch (IOException | RuntimeException e) {
      try {
        stage.close();
      } catch (RuntimeException cleanup) {
        e.addSuppressed(cleanup);
      }
      throw e;
    }
  }

  // Checks, before anything is changed, that path can be published empty: not a folder, and, when
  // missing, creatable with CREATE in an existing folder; without CREATE, a missing file is
  // NoSuchFileException.
  private static void checkEmptyTarget(FilesFileSystem fileSystem, FilesPath path, boolean create) throws IOException {
    try {
      File existing = fileSystem.stat(path, FilesFileSystem.Operation.WRITE);
      if ("directory".equals(existing.getType())) {
        throw new FileSystemException(path.toString(), null, "Is a directory");
      }
    } catch (NoSuchFileException missing) {
      if (!create) {
        throw missing;
      }
      // Created by the publication, only where its folder exists: a missing folder is not created.
      FilesPath parent = (FilesPath) path.toAbsolutePath().normalize().getParent();
      if (parent != null && !parent.isRoot()
          && !"directory".equals(fileSystem.stat(parent, FilesFileSystem.Operation.WRITE).getType())) {
        throw new FileSystemException(path.toString(), null, "Not a directory");
      }
    }
  }

  // Publishes the file empty, and returns it as the API described it on completing that upload,
  // or null where it described none.
  private static File publishEmpty(FilesFileSystem fileSystem, FilesPath path) throws IOException {
    try {
      return fileSystem.client().transfers().upload(new ByteArrayInputStream(new byte[0]), 0, path.apiPath(), fileSystem.uploadOptions())
          .getFile();
    } catch (IOException | RuntimeException e) {
      throw fileSystem.nativeFailure(FilesFileSystem.Operation.WRITE, path, null, e);
    }
  }

  // Makes channel the writer of path in place of claim, when there is one, and registers it with the
  // file system; a file system closed meanwhile refuses it.
  private static FilesByteChannel start(FilesFileSystem fileSystem, FilesPath path, Object claim, FilesByteChannel channel) {
    if (claim != null) {
      fileSystem.bindWriter(path, claim, channel);
    }
    if (!fileSystem.register(channel)) {
      if (claim != null) {
        fileSystem.releaseWriter(path, channel);
      }
      throw new ClosedFileSystemException();
    }
    return channel;
  }

  @Override
  public synchronized int read(ByteBuffer destination) throws IOException {
    ensureOpen();
    if (!readable) {
      throw new NonReadableChannelException();
    }
    int wanted = Math.min(destination.remaining(), BUFFER_BYTES);
    if (wanted == 0) {
      return 0;
    }
    byte[] bytes = new byte[wanted];
    int read = stage.read(position, bytes, 0, wanted);
    if (read > 0) {
      destination.put(bytes, 0, read);
      position += read;
    }
    return read;
  }

  @Override
  public synchronized int write(ByteBuffer source) throws IOException {
    ensureOpen();
    if (!writable) {
      throw new NonWritableChannelException();
    }
    if (append) {
      position = stage.size();
    }
    int count = source.remaining();
    if (count == 0) {
      return 0;
    }
    long end;
    try {
      end = Math.addExact(position, count);
    } catch (ArithmeticException e) {
      throw new FileSystemException(path.toString(), null, "A write past the largest size a file can have");
    }
    if (end > reservedBytes) {
      fileSystem.reserve(path, end - reservedBytes);
      reservedBytes = end;
    }
    byte[] bytes = new byte[Math.min(count, BUFFER_BYTES)];
    int written = 0;
    while (written < count) {
      int length = Math.min(count - written, bytes.length);
      source.get(bytes, 0, length);
      // Changed before the write is attempted: one that fails may have changed some of the bytes.
      modified = true;
      try {
        stage.write(position, bytes, 0, length);
      } catch (IOException | RuntimeException e) {
        source.position(source.position() - length);
        localFailed(e);
        throw e;
      }
      position += length;
      written += length;
      pendingSize = Math.max(pendingSize, position);
    }
    return written;
  }

  @Override
  public synchronized long position() throws IOException {
    ensureOpen();
    return position;
  }

  @Override
  public synchronized FilesByteChannel position(long newPosition) throws IOException {
    ensureOpen();
    if (newPosition < 0) {
      throw new IllegalArgumentException("A position cannot be negative");
    }
    position = newPosition;
    return this;
  }

  @Override
  public synchronized long size() throws IOException {
    ensureOpen();
    return stage.size();
  }

  @Override
  public synchronized FilesByteChannel truncate(long size) throws IOException {
    ensureOpen();
    if (size < 0) {
      throw new IllegalArgumentException("A size cannot be negative");
    }
    if (!writable) {
      throw new NonWritableChannelException();
    }
    if (size < stage.size()) {
      modified = true;
      try {
        stage.truncate(size);
      } catch (IOException | RuntimeException e) {
        localFailed(e);
        throw e;
      }
      pendingSize = size;
    }
    if (position > size) {
      position = size;
    }
    return this;
  }

  // Remembers the first failed change of the copy, whose content is then uncertain, and takes its
  // size, for the pending view, from the copy's handle, which may count bytes the change wrote.
  private void localFailed(Exception failure) {
    if (localFailure == null) {
      localFailure = failure;
    }
    try {
      pendingSize = stage.size();
    } catch (IOException e) {
      failure.addSuppressed(e);
    }
  }

  @Override
  public boolean isOpen() {
    return !closed;
  }

  // The pending view of the file while this channel writes it: as described when the channel
  // opened, with the copy's size.
  FilesFileSystemProvider.Attributes pendingAttributes() {
    return FilesFileSystemProvider.Attributes.of(described, pendingSize);
  }

  /**
   * Closes the channel, publishing a changed copy as the class description describes. A close
   * called while another closes the channel waits for that one to finish, then returns; an
   * interrupt ends that wait early, keeping the thread's interrupt status, and the other close still
   * reports its own outcome.
   *
   * @throws IOException the first failure: a failed change of the copy, which is then not
   *     published, or its publication. The copy is then kept, and
   *     {@link FileSystemException#getOtherFile()} is the name its directory was created with, or
   *     null: a diagnostic name, not checked again, which may no longer lead to the copy. A cleanup
   *     that fails after a publication is reported as such, the file staying published.
   */
  @Override
  public void close() throws IOException {
    synchronized (this) {
      if (closed) {
        awaitSettled();
        return;
      }
      closed = true;
    }
    IOException failure = null;
    boolean keep = false;
    boolean published = false;
    try {
      if (writable && localFailure != null) {
        keep = true;
        String directory = recovery();
        failure = new FileSystemException(path.toString(), directory,
            namingCopyDirectory("A change of the local copy failed, so the copy was not published", directory));
        failure.initCause(localFailure);
      } else if (writable && modified) {
        try {
          long length = stage.size();
          fileSystem.client().transfers().upload(stage.source(), length, path.apiPath(), fileSystem.uploadOptions());
          published = true;
        } catch (IOException | RuntimeException e) {
          keep = true;
          failure = fileSystem.nativeFailure(FilesFileSystem.Operation.WRITE, path, null, e, recovery());
        }
      }
    } finally {
      try {
        if (keep) {
          stage.retain();
        }
        stage.close();
      } catch (RuntimeException e) {
        // The stage's cleanup is an attempt; one that throws all the same is reported, after any
        // earlier failure, without undoing a publication.
        if (failure != null) {
          failure.addSuppressed(e);
        } else {
          String directory = recovery();
          failure = new FileSystemException(path.toString(), directory, namingCopyDirectory(published
              ? "The file was published, but cleaning up its local copy failed" : "Cleaning up the local copy failed", directory));
          failure.initCause(e);
        }
      } finally {
        fileSystem.release(reservedBytes);
        if (writable) {
          fileSystem.releaseWriter(path, this);
        }
        fileSystem.unregister(this);
        synchronized (this) {
          settled = true;
          notifyAll();
        }
      }
    }
    if (failure != null) {
      throw failure;
    }
  }

  // Waits, holding this, for the close that closed the channel to finish; an interrupt stops the
  // wait, and is kept.
  private void awaitSettled() {
    while (!settled) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }
  }

  // The directory a kept copy is in, as a diagnostic location, or null.
  private String recovery() {
    // Only the name the directory was created with: it is not checked again, and a directory
    // renamed or replaced since may not hold the copy.
    Path directory = stage.directory();
    return directory == null ? null : directory.toString();
  }

  // reason, saying what the other file of a failure that gives directory, the name of the copy's
  // directory, is; with no such name, reason as it is.
  static String namingCopyDirectory(String reason, String directory) {
    return directory == null ? reason : reason + "; the other file names the local copy's directory as it was created,"
        + " unchecked since, so it may no longer lead to the copy";
  }

  private void ensureOpen() throws ClosedChannelException {
    if (closed) {
      throw new ClosedChannelException();
    }
  }
}
