package com.files.exceptions;

/**
 * The unchecked base class of the SDK's API exceptions: {@link ApiErrorException} and the classes
 * nested in it extend it, so catching {@code SdkException} catches any of them.
 *
 * <p>An {@code SdkException} keeps the message it was created with, and {@link #getMessage()}
 * returns it. {@link #getLocalizedMessage()} and {@link #toString()} use {@code getMessage()}, as
 * {@link Throwable} defines them, so they report the same message, never the cause's. The
 * one-argument constructor sets no cause; one can be attached with {@link #initCause(Throwable)}.
 */
public class SdkException extends RuntimeException {
  private final String message;

  /**
   * Creates an exception with the given message and no cause.
   *
   * @param message the message {@link #getMessage()} returns; may be {@code null}
   */
  public SdkException(String message) {
    this.message = message;
  }

  /**
   * Creates an exception with the failure that caused it. The message is this exception's own: the
   * cause's message is not repeated in it, so it can describe the failure without the cause's
   * details.
   *
   * @param message the message {@link #getMessage()} returns; may be {@code null}
   * @param cause the failure that caused this exception; may be {@code null}
   */
  public SdkException(String message, Throwable cause) {
    super(cause);
    this.message = message;
  }

  /**
   * Returns the message this exception was created with.
   *
   * @return the message, or {@code null} if the exception was created with {@code null}
   */
  @Override
  public String getMessage() {
    return message;
  }
}
