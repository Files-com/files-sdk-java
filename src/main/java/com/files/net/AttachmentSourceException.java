package com.files.net;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * An attachment's own source failed: a file could not be read or changed, or a stream could not be
 * copied. Retrying the request cannot help, so neither HttpClient nor the SDK retries it, and the
 * caller receives {@link #unchecked()}.
 */
final class AttachmentSourceException extends IOException {
  private static final long serialVersionUID = 1L;

  AttachmentSourceException(String message, IOException cause) {
    super(message, cause);
  }

  // Keeps any cleanup failures that were added to this exception.
  UncheckedIOException unchecked() {
    UncheckedIOException unchecked = new UncheckedIOException(getMessage(), (IOException) getCause());
    for (Throwable cleanup : getSuppressed()) {
      unchecked.addSuppressed(cleanup);
    }
    return unchecked;
  }
}
