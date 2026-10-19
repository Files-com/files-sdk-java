package com.files.net;

import com.files.models.File;
import java.nio.file.Path;

/**
 * A completed download, to a local file or to a stream of yours.
 */
public final class DownloadResult {
  private final File file;
  private final Path destination;
  private final long size;
  private final boolean versionBound;
  private final TransferProgress finalProgress;

  DownloadResult(File file, Path destination, long size, boolean versionBound, TransferProgress finalProgress) {
    this.file = file;
    this.destination = destination;
    this.size = size;
    this.versionBound = versionBound;
    this.finalProgress = finalProgress;
  }

  /** The remote file, as Files.com described it. */
  public File getFile() {
    return file;
  }

  /**
   * The local file the contents were written to, or null for a download to a stream
   * ({@link Transfers#download(File, java.io.OutputStream, DownloadOptions)}).
   */
  public Path getDestination() {
    return destination;
  }

  /** The number of bytes downloaded: for a download to a stream, the bytes written to it. */
  public long getSize() {
    return size;
  }

  /**
   * Whether the file was fetched in parts bound to one version of it, which every part's answer
   * confirmed; false when it came in one request.
   */
  public boolean isVersionBound() {
    return versionBound;
  }

  /** The download's progress when it was complete. */
  public TransferProgress getFinalProgress() {
    return finalProgress;
  }
}
