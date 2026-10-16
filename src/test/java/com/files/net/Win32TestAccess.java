package com.files.net;

import java.io.IOException;

// For tests in other packages that call Windows functions through JNA themselves: has the SDK bind its
// own first, through Win32's load, so that JNA is first used after JnaTempDirectory has decided where
// JNA may write its native library, as in a download. Tests only; the SDK itself has no such API.
public final class Win32TestAccess {
  private Win32TestAccess() {
  }

  // Throws what a download would where the SDK could not bind its Windows functions.
  public static void requireAvailable() throws IOException {
    Win32.requireAvailable();
  }
}
