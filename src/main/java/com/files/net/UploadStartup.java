package com.files.net;

/**
 * How an automatic upload to S3 starts, before it has measured anything (see
 * {@link UploadOptions.Builder#startup(UploadStartup)}). Either way the upload stays adaptive, within
 * the same caps, with the same backoff, retries and cancellation, and sends one part at a time when
 * Files.com requires it.
 */
public enum UploadStartup {
  /**
   * Starts with one part at a time and adds parts only as measured answers show they help, or goes
   * straight to the S3 policy's start once a window shows a fast link. The default. It can reach the
   * same throughput and concurrency as {@link #AGGRESSIVE}; only its ramp-up takes longer.
   */
  DEFAULT,
  /**
   * Starts at the S3 policy's start, 150 parts at once, within the upload's caps: for a network
   * known to carry high throughput, such as a cloud VM's. Failures, throttling, queueing and probes
   * that do not pay can still lower it.
   */
  AGGRESSIVE
}
