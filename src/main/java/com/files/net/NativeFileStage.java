package com.files.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystemException;
import java.nio.file.Path;

/**
 * The local copy of one remote file that a channel of the {@code java.nio.file} file system of
 * {@link com.files.nio.FilesComFileSystems} reads and writes. It is that file system's, and not
 * meant to be used on its own.
 *
 * <p>The copy is the file the SDK's download stage creates, empty, in a directory of its own in the
 * file system's stage directory, with the same platform facility and privacy as the stage
 * {@link Transfers#download(com.files.models.File, Path, DownloadOptions)} creates beside its
 * destination. Every read and write of the copy, including the download that fills it
 * ({@link #sink}) and the upload that publishes it ({@link #source}), goes through the handle that
 * created it, never through a name: a file or directory put in the place of either, by name, is
 * neither read, written, uploaded nor deleted. The copy can still be used after such a replacement,
 * though its directory's name may then no longer lead to it.
 *
 * <p>Closing the stage releases its handle and deletes the copy, then the directory, each only while
 * it is still the one created, as the download stage's own cleanup does: an attempt, which can leave
 * either behind. A {@link #retain() retained} stage keeps both, for recovery. Positions and sizes
 * are those of the copy, in bytes; the stage is safe for use by several threads, whose operations
 * take turns.
 */
public final class NativeFileStage implements Closeable {
  // The name the download stage's directory is named after, in the stage directory.
  private static final String STAGE_NAME = "files-com-nio";

  private final DownloadStage stage;
  private final Path directory;
  private boolean closed;

  private NativeFileStage(DownloadStage stage, Path directory) {
    this.stage = stage;
    this.directory = directory;
  }

  /**
   * Creates a stage, with its empty copy, in a directory of its own created in
   * {@code stageDirectory}.
   *
   * @param stageDirectory an existing local directory; the stage's own directory is created in it
   * @return the stage, whose copy is empty
   * @throws IOException if the directory or the copy cannot be created, or cannot be kept private
   *     where the download stage requires that
   */
  public static NativeFileStage create(Path stageDirectory) throws IOException {
    Path destination = stageDirectory.toAbsolutePath().resolve(STAGE_NAME);
    DownloadStage stage = DownloadStage.create(destination);
    Path directory;
    try {
      directory = destination.getParent().resolve(stage.identity().directory);
    } catch (IOException e) {
      // The platform gives the stage no identity to name its directory by; its copy is used through
      // its handle all the same.
      directory = null;
    } catch (RuntimeException | Error e) {
      // The stage's cleanup is an attempt; one that throws all the same is kept with this failure,
      // which is the one thrown.
      try {
        stage.close();
      } catch (RuntimeException | Error cleanup) {
        e.addSuppressed(cleanup);
      }
      throw e;
    }
    return new NativeFileStage(stage, directory);
  }

  /**
   * Reads at most {@code count} bytes of the copy, from {@code position}, into {@code bytes} from
   * {@code offset}.
   *
   * @return the number of bytes read, at least one when {@code count} is not 0, or -1 at or past the
   *     copy's end
   * @throws IOException if the copy cannot be read
   */
  public synchronized int read(long position, byte[] bytes, int offset, int count) throws IOException {
    checkOpen(position, bytes, offset, count);
    return count == 0 ? 0 : stage.readAt(position, bytes, offset, count);
  }

  /**
   * Writes {@code count} bytes of {@code bytes}, from {@code offset}, to the copy at
   * {@code position}, all of them, or throws. A write that throws may have changed some of those
   * bytes of the copy, but not others.
   *
   * @throws IOException if the copy cannot be written
   */
  public synchronized void write(long position, byte[] bytes, int offset, int count) throws IOException {
    checkOpen(position, bytes, offset, count);
    if (count > 0) {
      stage.writeAt(position, bytes, offset, count);
    }
  }

  /**
   * Returns the copy's size, as its handle reports it now; after a write that failed, it can count
   * bytes that write changed.
   *
   * @throws IOException if the size cannot be read
   */
  public synchronized long size() throws IOException {
    checkOpen(0, null, 0, 0);
    return stage.size();
  }

  /**
   * Shortens the copy to {@code size} bytes; a copy no longer than that is left as it is, never
   * grown.
   *
   * @throws IOException if the copy cannot be truncated
   */
  public synchronized void truncate(long size) throws IOException {
    checkOpen(size, null, 0, 0);
    stage.truncate(size);
  }

  /**
   * Returns a stream that writes the copy from its start, one write after another, as a download to
   * a stream does: a write that would take the copy past {@code limit} bytes is refused before any
   * of its bytes is written. Closing the stream does nothing: the stage stays open.
   *
   * @param limit the most bytes the stream writes, such as the reserved size of the file it downloads
   * @return the stream
   */
  public OutputStream sink(long limit) {
    return new Sink(limit);
  }

  /**
   * Returns a stream that reads the copy from its start, one read after another, as an upload of a
   * known length reads its source. Closing the stream does nothing: the stage stays open.
   *
   * @return the stream
   */
  public InputStream source() {
    return new Source();
  }

  /**
   * Returns the directory the stage created for its copy, as it was named then: a diagnostic location
   * for a retained copy, not a promise that the name still leads to it.
   *
   * @return the directory, or {@code null} where the platform gives the stage no identity to name it
   *     by
   */
  public Path directory() {
    return directory;
  }

  /**
   * Keeps the copy and its directory when the stage is closed, for recovery: they are then the
   * caller's to delete.
   */
  public synchronized void retain() {
    stage.retain();
  }

  /**
   * Releases the copy's handle and deletes the copy and the stage's directory, each only while it is
   * still the one created, as an attempt that can leave them behind; a retained stage keeps them.
   * Closing the stage again has no effect.
   */
  @Override
  public synchronized void close() {
    if (closed) {
      return;
    }
    closed = true;
    stage.close();
  }

  private void checkOpen(long position, byte[] bytes, int offset, int count) throws IOException {
    if (closed) {
      throw new FileSystemException(directory == null ? null : directory.toString(), null, "The local copy's stage is closed");
    }
    if (position < 0) {
      throw new IllegalArgumentException("A position or size cannot be negative");
    }
    if (bytes != null && (offset < 0 || count < 0 || count > bytes.length - offset)) {
      throw new IndexOutOfBoundsException();
    }
  }

  // Writes the copy from its start, within limit.
  private final class Sink extends OutputStream {
    private final long limit;
    private long written;

    Sink(long limit) {
      this.limit = limit;
    }

    @Override
    public void write(int b) throws IOException {
      write(new byte[]{(byte) b}, 0, 1);
    }

    @Override
    public void write(byte[] bytes, int offset, int count) throws IOException {
      if (count > limit - written) {
        throw new FileSystemException(directory == null ? null : directory.toString(), null,
            "The download has more than the " + limit + " bytes reserved for its local copy");
      }
      NativeFileStage.this.write(written, bytes, offset, count);
      written += count;
    }
  }

  // Reads the copy from its start.
  private final class Source extends InputStream {
    private long position;

    @Override
    public int read() throws IOException {
      byte[] one = new byte[1];
      return read(one, 0, 1) < 0 ? -1 : one[0] & 0xff;
    }

    @Override
    public int read(byte[] bytes, int offset, int count) throws IOException {
      int read = NativeFileStage.this.read(position, bytes, offset, count);
      if (read > 0) {
        position += read;
      }
      return read;
    }
  }
}
