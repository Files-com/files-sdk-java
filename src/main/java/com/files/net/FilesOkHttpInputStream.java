package com.files.net;

import com.files.util.FilesInputStream;
import com.files.util.StreamTransfer;
import okhttp3.Response;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FilesOkHttpInputStream extends FilesInputStream implements Closeable {
  private static final int DEFAULT_BUFFER_SIZE = 8192;
  Response response;
  InputStream upstreamInputStream = null;

  public FilesOkHttpInputStream(Response response) {
    this.response = response;
  }

  @Override
  public int read() throws IOException {
    if (upstreamInputStream == null) loadInputStream();
    return upstreamInputStream.read();
  }

  @Override
  public int read(byte b[]) throws IOException {
    if (upstreamInputStream == null) loadInputStream();
    return upstreamInputStream.read(b);
  }

  @Override
  public int read(byte b[], int off, int len) throws IOException {
    if (upstreamInputStream == null) loadInputStream();
    return upstreamInputStream.read(b, off, len);
  }

  @Override
  public long skip(long n) throws IOException {
    if (upstreamInputStream == null) loadInputStream();
    return upstreamInputStream.skip(n);
  }

  @Override
  public int available() throws IOException {
    if (upstreamInputStream == null) loadInputStream();
    return upstreamInputStream.available();
  }

  @Override
  public void close() throws IOException {
    if (upstreamInputStream != null) upstreamInputStream.close();
    response.body().close();
  }

  @Override
  public synchronized void mark(int readLimit) {
    if (upstreamInputStream == null) loadInputStream();
    upstreamInputStream.mark(readLimit);
  }

  @Override
  public synchronized void reset() throws IOException {
    if (upstreamInputStream == null) loadInputStream();
    upstreamInputStream.reset();
  }

  @Override
  public boolean markSupported() {
    if (upstreamInputStream == null) loadInputStream();
    return upstreamInputStream.markSupported();
  }

  @Override
  public long transferTo(OutputStream out) throws IOException {
    if (upstreamInputStream == null) loadInputStream();
    long transferred = StreamTransfer.smartCopy(response.body().contentLength(), this, out);
    this.close();
    return transferred;
  }

  private void loadInputStream() {
    upstreamInputStream = response.body().byteStream();
  }

}
