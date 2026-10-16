package com.files.net;

/**
 * One part of an upload, as Files.com's storage accepted it.
 */
public final class UploadedPart {
  private final long number;
  private final long offset;
  private final long length;
  private final String etag;
  private final int attempts;

  UploadedPart(long number, long offset, long length, String etag, int attempts) {
    this.number = number;
    this.offset = offset;
    this.length = length;
    this.etag = etag;
    this.attempts = attempts;
  }

  /** The part number, from 1. */
  public long getNumber() {
    return number;
  }

  /** The position of the part's first byte in the file. */
  public long getOffset() {
    return offset;
  }

  /** The number of bytes in the part. */
  public long getLength() {
    return length;
  }

  /**
   * The ETag the storage server returned when it accepted the part, exactly as sent, or null if
   * it returned none. Some destinations legitimately return none.
   */
  public String getETag() {
    return etag;
  }

  /** The number of times the part was sent: 1 unless it was retried. */
  public int getAttempts() {
    return attempts;
  }

  @Override
  public String toString() {
    return "UploadedPart{number=" + number + ", offset=" + offset + ", length=" + length + ", attempts=" + attempts + "}";
  }
}
