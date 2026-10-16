package com.files.net;

/**
 * Whether a transfer adapts how many parts it sends at once.
 */
public enum ConcurrencyMode {
  /**
   * The transfer measures what its parts deliver and adapts how many it sends at once, within its
   * caps, following the policy for its destination (see {@link UploadOptions.Builder#concurrencyMode}).
   * The default.
   */
  AUTOMATIC,
  /** The transfer sends a fixed number of parts at once, its maximum concurrency. */
  STATIC
}
