package com.files.net;

/**
 * Who chose the sizes of an upload's parts. See {@link PartSizing}.
 */
public enum PartSizeSource {
  /** The SDK chose them, as {@link PartSizing#automatic()} does where it may. */
  AUTOMATIC,
  /** The caller chose them, with {@link PartSizing#fixed(long)}. */
  FIXED,
  /** Files.com planned them. */
  SERVER
}
