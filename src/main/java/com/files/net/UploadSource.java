package com.files.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongConsumer;

// Where an upload's bytes come from. prepare gives each part, in order, a body that writes the
// same bytes every time it is sent, once it has been read, so a retried part is identical.
abstract class UploadSource implements AutoCloseable {
  // The copy buffer a part read from a file uses while it is sent.
  static final int COPY_BUFFER_BYTES = 64 * 1024;

  // The length to upload, or -1 when it is known only once the source ends.
  abstract long length();

  // The memory a part of this length holds while it waits and is sent.
  abstract long bufferBytes(long partLength);

  // Whether each part is held whole in memory until it has been sent for the last time, which bounds
  // its size by the limiter's buffer space.
  abstract boolean holdsWholeParts();

  // The part at offset, of at most maxLength bytes: exactly maxLength when the length is known.
  abstract PartBody prepare(long offset, long maxLength, OperationScope scope) throws IOException;

  // Whether the source ended within the part last prepared.
  abstract boolean ended();

  // Checks, before the upload is completed, that the source still holds what was uploaded.
  void checkUnchanged() throws IOException {
  }

  @Override
  public abstract void close();

  // The bytes of one part.
  interface PartBody {
    long length();

    // Reads the part, if it has not been read yet, before it is first sent: a part of a file
    // records the digest every attempt must match. One thread at a time may use the body.
    void read(OperationScope scope) throws IOException;

    // Writes the part, which has been read, reporting each chunk written to sent.
    void writeTo(OutputStream out, LongConsumer sent) throws IOException;

    // The part will not be sent again.
    void release();
  }

  static MessageDigest sha256() {
    try {
      return MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 is unavailable", e);
    }
  }

  // A failure reading the upload's own source: sending the part again cannot fix it.
  static final class SourceFailure extends IOException {
    private static final long serialVersionUID = 1L;

    SourceFailure(IOException cause) {
      super(cause.getMessage(), cause);
    }

    IOException failure() {
      return (IOException) getCause();
    }
  }

  // A local file, opened once when the upload starts: every part, on every attempt, is read
  // through that handle, so renaming or replacing the file during the upload does not change
  // what is uploaded. The length uploaded is the file's length when it was opened. Parts are read a
  // copy buffer at a time, so the file's contents are never held in memory.
  static final class FileSource extends UploadSource {
    private final Path path;
    private final FileChannel channel;
    private final long length;
    private final FileTime modified;
    private final Object fileKey;

    FileSource(Path path) throws IOException {
      this.path = path;
      BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
      if (!attributes.isRegularFile()) {
        throw new IOException("Only a regular file can be uploaded");
      }
      this.channel = FileChannel.open(path, StandardOpenOption.READ);
      this.length = channel.size();
      this.modified = attributes.lastModifiedTime();
      this.fileKey = attributes.fileKey();
    }

    FileTime lastModified() {
      return modified;
    }

    // What identifies the file on its file system, such as its device and inode, or null where the
    // platform has nothing.
    Object fileKey() {
      return fileKey;
    }

    @Override
    long length() {
      return length;
    }

    @Override
    long bufferBytes(long partLength) {
      return Math.min(partLength, COPY_BUFFER_BYTES);
    }

    @Override
    boolean holdsWholeParts() {
      return false;
    }

    // The part is read later, by FileRegion.read, so that the parts of one file can be read on the
    // threads that send them rather than one after another on the thread that plans them.
    @Override
    PartBody prepare(long offset, long maxLength, OperationScope scope) {
      return new FileRegion(offset, maxLength);
    }

    private int readAt(ByteBuffer buffer, long position, long end) throws IOException {
      buffer.clear();
      buffer.limit((int) Math.min(buffer.capacity(), end - position));
      return channel.read(buffer, position);
    }

    @Override
    boolean ended() {
      return false;
    }

    // A file changed during the upload may have been read partly before and partly after the
    // change, so it is not completed.
    @Override
    void checkUnchanged() throws IOException {
      BasicFileAttributes attributes;
      try {
        attributes = Files.readAttributes(path, BasicFileAttributes.class);
      } catch (IOException e) {
        // Renamed or removed: the open handle still read the original contents.
        return;
      }
      if (channel.size() != length || !attributes.lastModifiedTime().equals(modified)) {
        throw new IOException("The file changed while it was being uploaded, so the upload was not completed");
      }
    }

    @Override
    public void close() {
      try {
        channel.close();
      } catch (IOException e) {
        // Closing a file opened for reading loses nothing.
      }
    }

    // A part of the file, read again for each attempt. The last chunk is written only once the
    // bytes read match the digest taken before the first attempt, so a part whose bytes changed in
    // place, even with the same size and modification time, is never sent complete: the attempt
    // fails, and the upload with it.
    private final class FileRegion implements PartBody {
      private final long offset;
      private final long length;
      // Set by read, before the first attempt.
      private byte[] digest;

      FileRegion(long offset, long length) {
        this.offset = offset;
        this.length = length;
      }

      @Override
      public long length() {
        return length;
      }

      // Reads the part once, before it is first sent, and records its digest: every attempt to send
      // it must send exactly these bytes. The file is not copied, so a part held for a retry costs
      // no memory or disk beyond a copy buffer.
      @Override
      public void read(OperationScope scope) throws IOException {
        if (digest != null) {
          return;
        }
        MessageDigest original = sha256();
        ByteBuffer buffer = ByteBuffer.allocate((int) bufferBytes(length));
        long position = offset;
        long end = offset + length;
        try {
          while (position < end) {
            scope.check();
            int count = readAt(buffer, position, end);
            if (count < 0) {
              throw new EOFException("The file became shorter while it was being uploaded");
            }
            original.update(buffer.array(), 0, count);
            position += count;
          }
        } catch (ClosedByInterruptException e) {
          scope.interrupted();
          throw scope.stopped(e);
        }
        digest = original.digest();
      }

      @Override
      public void writeTo(OutputStream out, LongConsumer sent) throws IOException {
        MessageDigest check = sha256();
        ByteBuffer buffer = ByteBuffer.allocate((int) bufferBytes(length));
        long position = offset;
        long end = offset + length;
        while (position < end) {
          int read;
          try {
            read = readAt(buffer, position, end);
          } catch (IOException e) {
            throw new SourceFailure(e);
          }
          if (read < 0) {
            throw new SourceFailure(new EOFException("The file became shorter while it was being uploaded"));
          }
          check.update(buffer.array(), 0, read);
          position += read;
          if (position == end && !MessageDigest.isEqual(check.digest(), digest)) {
            throw new SourceFailure(new IOException("The file changed while it was being uploaded, so the upload was stopped"));
          }
          out.write(buffer.array(), 0, read);
          sent.accept(read);
        }
      }

      @Override
      public void release() {
      }
    }
  }

  // A stream the caller owns, read forward once, a part at a time: each part is read whole into
  // memory and kept until the storage server accepts it, so a retry sends exactly the same bytes.
  // The stream is never closed, reset or skipped. A read of it that blocks can only end on its own:
  // cancellation takes effect when it returns.
  static final class StreamSource extends UploadSource {
    private static final int CHUNK_BYTES = 1024 * 1024;

    private final InputStream stream;
    private final long length;
    private long read;
    private int carried = -1;
    private boolean ended;

    StreamSource(InputStream stream, long length) {
      this.stream = stream;
      this.length = length;
    }

    @Override
    long length() {
      return length;
    }

    @Override
    long bufferBytes(long partLength) {
      return partLength;
    }

    @Override
    boolean holdsWholeParts() {
      return true;
    }

    @Override
    boolean ended() {
      return ended;
    }

    @Override
    PartBody prepare(long offset, long maxLength, OperationScope scope) throws IOException {
      Chunks chunks = new Chunks(maxLength);
      long partRead = 0;
      if (carried >= 0) {
        chunks.append((byte) carried);
        carried = -1;
        partRead = 1;
      }
      try {
        while (partRead < maxLength) {
          scope.check();
          byte[] chunk = chunks.space();
          int count = stream.read(chunk, chunks.used(), (int) Math.min(chunk.length - chunks.used(), maxLength - partRead));
          if (count < 0) {
            break;
          }
          chunks.advance(count);
          partRead += count;
        }
        if (length >= 0 && partRead < maxLength) {
          throw new EOFException("The stream ended after " + (read + partRead) + " of the " + length + " bytes to upload");
        }
        if (length < 0) {
          // A full part may still be the last one: one more byte tells.
          ended = partRead < maxLength || (carried = stream.read()) < 0;
        }
      } catch (InterruptedIOException | ClosedByInterruptException e) {
        scope.interrupted();
        throw scope.stopped(e);
      }
      scope.check();
      read += partRead;
      return chunks;
    }

    @Override
    public void close() {
    }

    // A part's bytes, in chunks of at most CHUNK_BYTES so that no part needs one large array.
    private static final class Chunks implements PartBody {
      private final List<byte[]> chunks = new ArrayList<byte[]>();
      private final long capacity;
      private long length;
      private int used;

      Chunks(long capacity) {
        this.capacity = capacity;
      }

      // The chunk to read into next: the last one while it has room, otherwise a new one.
      byte[] space() {
        if (chunks.isEmpty() || used == chunks.get(chunks.size() - 1).length) {
          chunks.add(new byte[(int) Math.min(CHUNK_BYTES, Math.max(1, capacity - length))]);
          used = 0;
        }
        return chunks.get(chunks.size() - 1);
      }

      int used() {
        return used;
      }

      void advance(int count) {
        used += count;
        length += count;
      }

      void append(byte value) {
        space()[used] = value;
        advance(1);
      }

      @Override
      public long length() {
        return length;
      }

      // Read when it was prepared.
      @Override
      public void read(OperationScope scope) {
      }

      @Override
      public void writeTo(OutputStream out, LongConsumer sent) throws IOException {
        long remaining = length;
        for (byte[] chunk : chunks) {
          int count = (int) Math.min(chunk.length, remaining);
          out.write(chunk, 0, count);
          sent.accept(count);
          remaining -= count;
        }
      }

      @Override
      public void release() {
        chunks.clear();
      }
    }
  }
}
