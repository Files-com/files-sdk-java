package com.files.net;

/**
 * Limits the resources that transfers use together: how many part requests they send at once,
 * and how many bytes of file contents they hold in memory. Give the same limiter to several
 * uploads, including uploads made with different clients, to have them share one budget.
 *
 * <p>Uploads receive request slots and buffer space in the order they ask for them, so uploads
 * sharing a limiter take turns instead of one using all of it. A request slot is held for one
 * request: a part waiting to be sent again gives its slot back and asks again. A limiter holds no
 * credentials, settings or connections: each upload sends its requests with its own client. It is
 * safe to use from many threads, and its current use is cheap to read at any time.
 */
public final class TransferLimiter {
  /** The number of concurrent part requests a limiter allows unless another is given. */
  public static final int DEFAULT_MAX_CONCURRENT_REQUESTS = 8;

  /** The number of buffered bytes a limiter allows unless another is given: 128 MiB. */
  public static final long DEFAULT_MAX_BUFFERED_BYTES = 128L * 1024 * 1024;

  private final FifoBudget requests;
  private final FifoBudget bufferedBytes;

  /**
   * A limiter with {@link #DEFAULT_MAX_CONCURRENT_REQUESTS} and {@link #DEFAULT_MAX_BUFFERED_BYTES}.
   */
  public TransferLimiter() {
    this(DEFAULT_MAX_CONCURRENT_REQUESTS, DEFAULT_MAX_BUFFERED_BYTES);
  }

  /**
   * @param maxConcurrentRequests the most part requests its transfers send at the same time
   * @param maxBufferedBytes the most bytes of file contents its transfers hold in memory at the
   *     same time: whole parts read from streams, which are kept until the server accepts them so
   *     they can be sent again, and the copy buffers of parts read from files
   * @throws IllegalArgumentException if a limit is less than 1
   */
  public TransferLimiter(int maxConcurrentRequests, long maxBufferedBytes) {
    if (maxConcurrentRequests < 1) {
      throw new IllegalArgumentException("A transfer limiter must allow at least one request");
    }
    if (maxBufferedBytes < 1) {
      throw new IllegalArgumentException("A transfer limiter must allow at least one buffered byte");
    }
    this.requests = new FifoBudget(maxConcurrentRequests);
    this.bufferedBytes = new FifoBudget(maxBufferedBytes);
  }

  /** The most part requests this limiter's transfers send at the same time. */
  public int getMaxConcurrentRequests() {
    return (int) requests.capacity();
  }

  /** The most bytes of file contents this limiter's transfers hold in memory at the same time. */
  public long getMaxBufferedBytes() {
    return bufferedBytes.capacity();
  }

  /** The number of part requests being sent now. */
  public int getActiveRequests() {
    return (int) requests.inUse();
  }

  /** The number of bytes of file contents held now. */
  public long getBufferedBytes() {
    return bufferedBytes.inUse();
  }

  /** The number of part requests waiting for a request slot. */
  public int getQueuedRequests() {
    return requests.waiting();
  }

  /** The number of parts waiting for buffer space. */
  public int getQueuedBufferReservations() {
    return bufferedBytes.waiting();
  }

  FifoBudget requests() {
    return requests;
  }

  FifoBudget bufferedBytes() {
    return bufferedBytes;
  }
}
