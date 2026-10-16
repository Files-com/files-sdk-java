package com.files.net;

import java.time.Duration;

/**
 * How far a transfer has come. Immutable.
 */
public final class TransferProgress {
  private final String path;
  private final long totalBytes;
  private final long transferredBytes;
  private final long resumedBytes;
  private final long sentBytes;
  private final long completedParts;
  private final int retries;
  private final Duration elapsed;
  private final TransferConcurrency concurrency;

  TransferProgress(String path, long totalBytes, long transferredBytes, long resumedBytes, long sentBytes, long completedParts, int retries,
      Duration elapsed, TransferConcurrency concurrency) {
    this.path = path;
    this.totalBytes = totalBytes;
    this.transferredBytes = transferredBytes;
    this.resumedBytes = resumedBytes;
    this.sentBytes = sentBytes;
    this.completedParts = completedParts;
    this.retries = retries;
    this.elapsed = elapsed;
    this.concurrency = concurrency;
  }

  /** The remote path of the transfer. */
  public String getPath() {
    return path;
  }

  /** The size of the whole transfer in bytes, or -1 while it is not known. */
  public long getTotalBytes() {
    return totalBytes;
  }

  /**
   * The bytes of the file that have been transferred: for an upload, the parts accepted so far,
   * including those an earlier run of a checkpointed upload had accepted ({@link #getResumedBytes()});
   * for a download, the parts written whole so far, including the ranges an earlier run of a
   * checkpointed download had written.
   */
  public long getTransferredBytes() {
    return transferredBytes;
  }

  /**
   * For an upload, the bytes written to the network so far, including parts sent again after a
   * failure and parts not accepted yet; for a download, the bytes received so far, including bytes
   * received again after a failure.
   */
  public long getSentBytes() {
    return sentBytes;
  }

  /**
   * Of {@link #getTransferredBytes()}, the bytes an earlier run of a checkpointed transfer had
   * transferred, which this run continued from: an upload's parts it did not send again, or a
   * download's ranges its partial file held durably, which it did not fetch again; 0 otherwise.
   */
  public long getResumedBytes() {
    return resumedBytes;
  }

  /**
   * The number of parts accepted so far, including those an earlier run of an upload had accepted;
   * for a download, the parts this run wrote whole.
   */
  public long getCompletedParts() {
    return completedParts;
  }

  /** The number of times a part has been sent again after a failed attempt. */
  public int getRetries() {
    return retries;
  }

  /** The time since the transfer started. */
  public Duration getElapsed() {
    return elapsed;
  }

  /**
   * How many parts the transfer sends at once, and why, or null before its first part has been
   * planned.
   */
  public TransferConcurrency getConcurrency() {
    return concurrency;
  }

  /**
   * The bytes this run transferred, {@link #getTransferredBytes()} less {@link #getResumedBytes()},
   * per second of {@link #getElapsed()}; 0 at the start.
   */
  public double getBytesPerSecond() {
    long nanos = elapsed.toNanos();
    return nanos <= 0 ? 0 : (transferredBytes - resumedBytes) * 1e9 / nanos;
  }

  @Override
  public String toString() {
    return "TransferProgress{path=" + path + ", totalBytes=" + totalBytes + ", transferredBytes=" + transferredBytes + ", resumedBytes="
        + resumedBytes
        + ", sentBytes=" + sentBytes + ", completedParts=" + completedParts + ", retries=" + retries
        + ", elapsed=" + elapsed + ", concurrency=" + concurrency + "}";
  }
}
