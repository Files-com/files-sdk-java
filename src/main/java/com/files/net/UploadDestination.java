package com.files.net;

/**
 * Where an upload's parts go, as the SDK identifies it from what Files.com says about the
 * destination, never from a URL's host. It chooses an automatic upload's concurrency policy; it does
 * not decide whether the SDK may choose part sizes (see {@link PartSizing}).
 */
public enum UploadDestination {
  /**
   * Amazon S3, Files.com's own storage or a bucket of yours: every destination Files.com does not
   * identify as a Files Agent. Files.com's answers do not yet identify a remote server integration,
   * so its uploads are treated as S3 too, and their part sizes still follow Files.com's
   * instructions.
   */
  S3,
  /** A Files Agent: Files.com gives complete direct connection information for the upload. */
  FILES_AGENT
}
