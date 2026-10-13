package com.files.util;

import com.files.util.BufferPool.Buffer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.IntConsumer;

/**
 * Copies bytes from an {@link InputStream} to an {@link OutputStream}, optionally stopping after a
 * known number of bytes and reporting each chunk written.
 *
 * <p>Every method is static and copies on the calling thread, returning when the copy ends. The
 * caller keeps ownership of both streams: these methods neither flush nor close them.
 */
public class StreamTransfer {
  private static final int INITIAL_BUFFER_SIZE = Math.min(1024 * 8, BufferPool.T_BUFFER_SIZE);

  /**
   * Creates a {@code StreamTransfer}. Every method of this class is static, so no instance is
   * needed to copy.
   */
  private StreamTransfer() {
  }

  /**
   * Copies bytes from {@code in} to {@code out} without reporting progress. This is
   * {@link #smartCopy(long, InputStream, OutputStream, IntConsumer)} with a {@code null} progress
   * consumer; {@link #bufferedCopy(long, InputStream, OutputStream, IntConsumer)} describes the
   * copy in full.
   *
   * @param knownSize the most bytes to copy, when positive; 0 or less copies until {@code in}
   *     reaches end of stream
   * @param in the stream to read from; it is left open
   * @param out the stream to write to; it is left open and is not flushed
   * @return the number of bytes written to {@code out}: at most {@code knownSize} when it is
   *     positive, and fewer if {@code in} reached end of stream first
   * @throws IOException if reading from {@code in} or writing to {@code out} fails
   */
  public static long smartCopy(long knownSize, InputStream in, OutputStream out) throws IOException {
    return smartCopy(knownSize, in, out, null);
  }

  /**
   * Copies bytes from {@code in} to {@code out}, reporting each chunk written. It currently copies
   * exactly as {@link #bufferedCopy(long, InputStream, OutputStream, IntConsumer)} does, which
   * describes the copy in full.
   *
   * @param knownSize the most bytes to copy, when positive; 0 or less copies until {@code in}
   *     reaches end of stream
   * @param in the stream to read from; it is left open
   * @param out the stream to write to; it is left open and is not flushed
   * @param progressConsumer called on the calling thread after each write to {@code out} with the
   *     number of bytes in that write, not a running total; may be {@code null}
   * @return the number of bytes written to {@code out}: at most {@code knownSize} when it is
   *     positive, and fewer if {@code in} reached end of stream first
   * @throws IOException if reading from {@code in} or writing to {@code out} fails
   */
  public static long smartCopy(long knownSize, InputStream in, OutputStream out,
      IntConsumer progressConsumer) throws IOException {
    // in the future full duplex and other copies may be possible
    return bufferedCopy(knownSize, in, out, progressConsumer);
  }

  /**
   * Copies bytes from {@code in} to {@code out} through an internal buffer, in chunks no larger
   * than that buffer.
   *
   * <p>When {@code knownSize} is positive, the copy stops once {@code knownSize} bytes have been
   * written, and no read asks {@code in} for more bytes than are still needed, so bytes after them
   * stay unread in {@code in}. If {@code in} reaches end of stream first, the copy stops there and
   * returns the smaller count without an error; compare the return value with {@code knownSize}
   * when the copy must be complete. When {@code knownSize} is 0 or less, the copy continues until
   * {@code in} reaches end of stream. A read that returns no bytes is tried again.
   *
   * <p>The copy runs on the calling thread and returns when it ends. When {@code progressConsumer}
   * is not {@code null}, it is called on that thread after each successful write to {@code out},
   * with the number of bytes in that write. An unchecked exception it throws ends the copy and
   * reaches the caller; the chunk it was called for has already been written.
   *
   * <p>Neither stream is flushed or closed; the caller remains responsible for both. If an
   * exception ends the copy, bytes already written to {@code out} stay written. The internal
   * buffer is released whether the copy completes or throws.
   *
   * @param knownSize the most bytes to copy, when positive; 0 or less copies until {@code in}
   *     reaches end of stream
   * @param in the stream to read from; it is left open
   * @param out the stream to write to; it is left open and is not flushed
   * @param progressConsumer called on the calling thread after each write to {@code out} with the
   *     number of bytes in that write, not a running total; may be {@code null}
   * @return the number of bytes written to {@code out}: at most {@code knownSize} when it is
   *     positive, and fewer if {@code in} reached end of stream first
   * @throws IOException if reading from {@code in} or writing to {@code out} fails
   */
  public static long bufferedCopy(long knownSize, InputStream in, OutputStream out,
      IntConsumer progressConsumer) throws IOException {
    long total = 0;
    try (Buffer b = BufferPool.needBuffer(INITIAL_BUFFER_SIZE)) {
      int c;
      byte[] buffer = b.getBuffer();
      long remaining = Long.MAX_VALUE;
      while ((knownSize < 1 || (remaining = knownSize - total) > 0)
          && (c = in.read(buffer, 0, (int) Math.min(remaining, INITIAL_BUFFER_SIZE))) >= 0) {
        if (c > 0) {
          out.write(buffer, 0, c);
          total += c;
          if (progressConsumer != null) {
            progressConsumer.accept(c);
          }
        } else {
          Thread.yield();
        }
      }
    }
    return total;
  }
}
