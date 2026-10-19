package com.files.exceptions;

/**
 * Thrown, or kept as the cause of a native exception, when the SDK does not implement what was
 * asked in the context it was asked: an option, attribute or method whose contract the SDK cannot
 * meet with the Files.com API, such as an atomic exclusive create or an atomic move. It is a
 * client-side refusal, made before anything is changed; the API was not asked and did not reject
 * anything.
 *
 * <p>The SDK's own APIs throw it as an {@link SdkException}. Native Java APIs cannot: the
 * {@code java.nio.file} file system of {@link com.files.nio.FilesComFileSystems} must throw
 * {@code UnsupportedOperationException}, as {@code java.nio.file.Files} callers expect, and Java's
 * single class inheritance keeps one object from being both. There it is the cause of that
 * {@code UnsupportedOperationException}, with the same message, so
 * {@code e.getCause() instanceof NotImplementedException} tells such a refusal from one the JDK
 * itself makes.
 */
public class NotImplementedException extends SdkException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message what is not implemented, and in which context
   */
  public NotImplementedException(String message) {
    super(message);
  }
}
