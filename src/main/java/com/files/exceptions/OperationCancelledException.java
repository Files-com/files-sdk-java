package com.files.exceptions;

/**
 * A request or transfer stopped before it finished because it was cancelled, its thread was
 * interrupted, or it ran out of time. {@link #getReason()} tells which.
 *
 * <p>The SDK throws this only once the work has stopped: its requests are aborted and its own
 * threads have ended. When the thread was interrupted, its interrupt status is set again before
 * this is thrown.
 */
public class OperationCancelledException extends SdkException {
  private static final long serialVersionUID = 1L;

  /**
   * Why an operation stopped.
   */
  public enum Reason {
    /** Its {@link com.files.CancellationToken} was cancelled, or its client was closed. */
    CANCELLED,
    /** The thread running it, or waiting for it, was interrupted. */
    INTERRUPTED,
    /** Its time limit passed: the {@code timeout} request option or the client's call timeout. */
    TIMED_OUT
  }

  private final Reason reason;

  public OperationCancelledException(Reason reason, String message) {
    super(message);
    this.reason = reason;
  }

  public OperationCancelledException(Reason reason, String message, Throwable cause) {
    super(message, cause);
    this.reason = reason;
  }

  public Reason getReason() {
    return reason;
  }
}
