package com.files.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The abstract base of the stream type {@link com.files.FilesClient#getFileInputStream} returns.
 * A subclass supplies the bytes; this class is not a working stream on its own.
 *
 * <p>The base keeps no state and provides no download behavior. It does not read from the
 * network, track the end of the stream or progress, check the bytes, or resume a transfer, and the
 * {@link #close()} it inherits from {@link InputStream} does nothing. Its {@link #read()} returns 0
 * without reading, and its {@link #transferTo(OutputStream)} always throws. A usable subclass
 * therefore overrides {@link #read()}, normally {@code read(byte[], int, int)} and
 * {@link #close()} as well, and {@link #transferTo(OutputStream)} if it supports copying.
 */
public abstract class FilesInputStream extends InputStream implements Closeable {
  /**
   * Returns 0 without reading anything. This is a placeholder, not a working read: an
   * {@link InputStream} caller takes 0 as a zero byte, never as the end of the stream, and the
   * inherited {@code read(byte[], int, int)} calls this method for each byte unless a subclass
   * overrides it. A subclass overrides this method to return the next byte, or -1 at the end of
   * the stream.
   *
   * @return 0
   * @throws IOException never by this implementation; declared for subclasses
   */
  @Override
  public int read() throws IOException {
    return 0;
  }

  /**
   * Throws {@code IOException} with the message {@code transferTo not implemented}, without
   * reading this stream or writing to {@code out}. A subclass that can copy its content to an
   * output stream overrides this method. On Java 9 and later it overrides
   * {@code InputStream.transferTo}, so that inherited copy is not available unless a subclass
   * provides its own.
   *
   * @param out the stream a subclass copies to; this implementation does not write to, flush or
   *     close it
   * @return the number of bytes an overriding subclass copied; this implementation never returns
   * @throws IOException always, from this implementation
   */
  public long transferTo(OutputStream out) throws IOException {
    throw new IOException("transferTo not implemented");
  }
}
