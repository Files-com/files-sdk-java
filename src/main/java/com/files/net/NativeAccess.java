package com.files.net;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.io.IOException;
import java.util.Map;

// Binds the native functions that downloads use on macOS and Windows, through JNA. A failure to
// bind them, because JNA is missing or cannot load its native library, for example, stops only the
// downloads that need them: each one reports it, before creating anything.
final class NativeAccess {
  private NativeAccess() {
  }

  // Registers the native methods of cls with the library, once, from cls's static initializer.
  // Returns the failure, rather than throwing it, so that every download can report it.
  static Throwable register(Class<?> cls, String library, Map<String, ?> options) {
    try {
      Native.register(cls, NativeLibrary.getInstance(library, options));
      return null;
    } catch (VirtualMachineError e) {
      throw e;
    } catch (Error | RuntimeException e) {
      return e;
    }
  }

  // The failure of a download that needs native functions it could not bind. The message names the
  // innermost cause, such as Java refusing native access, rather than the error that wraps it.
  static IOException unavailable(Throwable cause) {
    Throwable reason = cause;
    while (reason.getCause() != null && reason.getCause() != reason) {
      reason = reason.getCause();
    }
    return new IOException("Downloading to a local path on this platform needs JNA (net.java.dev.jna:jna) to keep the unfinished "
        + "download private, and JNA could not be used, so the download was stopped: " + reason, cause);
  }
}
