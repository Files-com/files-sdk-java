package com.files.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The content of one attachment for one request. Each {@link #open()} reads the same
 * {@link #length()} bytes from the start, so every attempt of the request sends the same body.
 * Closing it deletes any temporary copy the SDK made.
 */
abstract class AttachmentContent implements Closeable {
  private static final int COPY_BUFFER_SIZE = 64 * 1024;
  // The largest byte array a JVM allocates reliably.
  private static final int MAX_MEMORY_COPY = Integer.MAX_VALUE - 8;

  abstract long length();

  /**
   * Opens the content from its start. Reading fails with {@link AttachmentSourceException} if the
   * source can no longer supply the bytes this content promised.
   */
  abstract InputStream open() throws AttachmentSourceException;

  /** Deletes the SDK's temporary copy, if there is one. The caller's own sources are left alone. */
  @Override
  public void close() throws IOException {
  }

  static AttachmentContent ofBytes(byte[] bytes) {
    return new InMemory(bytes, bytes.length);
  }

  // checkpoint runs before each read, and stops the copy by throwing when its request is cancelled.
  static AttachmentContent ofFile(String parameter, Path path, Runnable checkpoint) throws AttachmentSourceException {
    BasicFileAttributes attributes;
    try {
      attributes = Files.readAttributes(path, BasicFileAttributes.class);
    } catch (IOException e) {
      throw new AttachmentSourceException("Could not read attachment " + parameter, e);
    }
    if (!attributes.isRegularFile()) {
      throw new IllegalArgumentException("Bad parameter: " + parameter + " must be a regular file, and "
          + path.getFileName() + " is not; send other content with Attachment.fromInputStream");
    }
    // Every attempt must send exactly the bytes read here, even one that follows an attempt cut off
    // partway, so their length and SHA-256 digest are recorded before anything is sent.
    MessageDigest digest = sha256();
    long length = 0;
    try (InputStream file = Files.newInputStream(path)) {
      byte[] buffer = new byte[COPY_BUFFER_SIZE];
      int read;
      checkpoint.run();
      while ((read = file.read(buffer)) != -1) {
        digest.update(buffer, 0, read);
        length += read;
        checkpoint.run();
      }
    } catch (IOException e) {
      throw new AttachmentSourceException("Could not read attachment " + parameter, e);
    }
    return new LocalFile(parameter, path, length, digest.digest());
  }

  /**
   * Copies a stream from its current position to its end, keeping up to {@code memoryThreshold}
   * bytes in memory and the rest in a temporary file. Fails once the stream has more than
   * {@code spoolLimit} bytes, reading at most one byte beyond it. Does not close the stream.
   * {@code checkpoint} runs before each read, and stops the copy by throwing when its request is
   * cancelled; a read that blocks in the stream itself ends only when the stream returns.
   */
  static AttachmentContent spooled(String parameter, InputStream stream, long spoolLimit, long memoryThreshold,
      Runnable checkpoint) throws AttachmentSourceException {
    long inMemory = Math.min(memoryThreshold, MAX_MEMORY_COPY);
    ByteArrayOutputStream memory = new ByteArrayOutputStream((int) Math.min(inMemory, COPY_BUFFER_SIZE));
    Path file = null;
    OutputStream fileOut = null;
    long size = 0;
    try {
      byte[] buffer = new byte[COPY_BUFFER_SIZE];
      while (true) {
        // One byte past the limit is enough to find a stream that exceeds it.
        long withinLimit = spoolLimit - size;
        int count = withinLimit < buffer.length ? (int) withinLimit + 1 : buffer.length;
        int read;
        checkpoint.run();
        try {
          read = stream.read(buffer, 0, count);
        } catch (IOException e) {
          throw new AttachmentSourceException("Could not read attachment " + parameter, e);
        }
        if (read == -1) {
          break;
        }
        size += read;
        if (size > spoolLimit) {
          throw new IllegalArgumentException("Bad parameter: " + parameter + " is a stream longer than its spool limit of "
              + spoolLimit + " bytes; raise it with Attachment.withSpoolLimit or send a file with Attachment.fromPath");
        }
        try {
          if (fileOut == null && size > inMemory) {
            file = Files.createTempFile("files-sdk-attachment-", ".tmp");
            fileOut = Files.newOutputStream(file);
            memory.writeTo(fileOut);
            memory = null;
          }
          (fileOut != null ? fileOut : memory).write(buffer, 0, read);
        } catch (IOException e) {
          throw new AttachmentSourceException("Could not copy attachment " + parameter + " to a temporary file", e);
        }
      }
      if (fileOut == null) {
        return new InMemory(memory.toByteArray(), (int) size);
      }
      try {
        fileOut.close();
      } catch (IOException e) {
        throw new AttachmentSourceException("Could not copy attachment " + parameter + " to a temporary file", e);
      }
      return new LocalFile(parameter, file, size, null);
    } catch (Throwable primary) {
      closeAndDelete(fileOut, file, primary);
      throw primary;
    }
  }

  // Undoes a partial copy. A cleanup failure is kept with the original failure, never instead of it.
  private static void closeAndDelete(OutputStream out, Path file, Throwable primary) {
    try {
      if (out != null) {
        out.close();
      }
    } catch (IOException e) {
      primary.addSuppressed(e);
    }
    try {
      if (file != null) {
        Files.deleteIfExists(file);
      }
    } catch (IOException e) {
      primary.addSuppressed(e);
    }
  }

  private static final class InMemory extends AttachmentContent {
    private final byte[] bytes;
    private final int length;

    InMemory(byte[] bytes, int length) {
      this.bytes = bytes;
      this.length = length;
    }

    @Override
    long length() {
      return length;
    }

    @Override
    InputStream open() {
      return new ByteArrayInputStream(bytes, 0, length);
    }
  }

  /**
   * A caller's file, read again for each attempt, or the SDK's own temporary copy of a stream. A
   * caller's file must still have exactly the bytes the SDK read before the first attempt: its length
   * and SHA-256 digest are checked as it is read, and a change fails the read before the request body
   * is complete.
   */
  private static final class LocalFile extends AttachmentContent {
    private final String parameter;
    private final Path path;
    private final long length;
    // The caller's file's digest; null for the SDK's own copy, which only the SDK writes.
    private final byte[] expectedDigest;

    LocalFile(String parameter, Path path, long length, byte[] expectedDigest) {
      this.parameter = parameter;
      this.path = path;
      this.length = length;
      this.expectedDigest = expectedDigest;
    }

    private boolean isTemporaryCopy() {
      return expectedDigest == null;
    }

    @Override
    long length() {
      return length;
    }

    @Override
    InputStream open() throws AttachmentSourceException {
      try {
        return new CheckedStream(Files.newInputStream(path));
      } catch (IOException e) {
        throw new AttachmentSourceException("Could not read attachment " + parameter, e);
      }
    }

    @Override
    public void close() throws IOException {
      if (isTemporaryCopy()) {
        Files.deleteIfExists(path);
      }
    }

    private AttachmentSourceException changed(String how) {
      return new AttachmentSourceException("Attachment " + parameter + " changed while the request was being sent;"
          + " the SDK does not send different content when it retries",
          new IOException(path.getFileName() + " " + how));
    }

    private final class CheckedStream extends InputStream {
      private final InputStream file;
      private final MessageDigest digest = isTemporaryCopy() ? null : sha256();
      private long position;
      private boolean verified;

      CheckedStream(InputStream file) {
        this.file = file;
      }

      @Override
      public int read() throws IOException {
        byte[] one = new byte[1];
        int read = read(one, 0, 1);
        return read == -1 ? -1 : one[0] & 0xff;
      }

      @Override
      public int read(byte[] buffer, int offset, int count) throws IOException {
        if (count == 0) {
          return 0;
        }
        if (position == length) {
          verifyEnd();
          return -1;
        }
        int read = readFile(buffer, offset, (int) Math.min(count, length - position));
        if (read == -1) {
          throw changed("is shorter than the " + length + " bytes it had when the request started");
        }
        if (digest != null) {
          digest.update(buffer, offset, read);
        }
        position += read;
        return read;
      }

      private void verifyEnd() throws IOException {
        if (verified) {
          return;
        }
        if (readFile(new byte[1], 0, 1) != -1) {
          throw changed("is longer than the " + length + " bytes it had when the request started");
        }
        if (digest != null && !MessageDigest.isEqual(expectedDigest, digest.digest())) {
          throw changed("has different content than when the request started");
        }
        verified = true;
      }

      private int readFile(byte[] buffer, int offset, int count) throws AttachmentSourceException {
        try {
          return file.read(buffer, offset, count);
        } catch (IOException e) {
          throw new AttachmentSourceException("Could not read attachment " + parameter, e);
        }
      }

      @Override
      public void close() throws IOException {
        file.close();
      }
    }
  }

  private static MessageDigest sha256() {
    try {
      return MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      // Every Java platform provides SHA-256.
      throw new IllegalStateException(e);
    }
  }
}
