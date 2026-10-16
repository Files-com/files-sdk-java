package com.files.exceptions;

/**
 * An unchecked exception that records no stack trace when it is created, because its
 * {@link #fillInStackTrace()} does nothing. It has only the default constructor, so it has no
 * message and no cause unless one is attached with {@link #initCause(Throwable)}. Causes,
 * suppressed exceptions and {@link #setStackTrace(StackTraceElement[])} work as they do for any
 * {@link RuntimeException}, so code can, for example, give it frames captured elsewhere before
 * logging it.
 */
public class StackSuppressedRuntimeException extends RuntimeException {
  /**
   * Returns this exception without recording the current thread's stack, unlike
   * {@link Throwable#fillInStackTrace()}. {@link Throwable} calls this method while the exception
   * is constructed, so the exception records no frames, and {@link #getStackTrace()} has none to
   * return unless frames are set with {@link #setStackTrace(StackTraceElement[])}. Calling it
   * again leaves any frames already set in place.
   *
   * @return this exception
   */
  @Override
  public synchronized Throwable fillInStackTrace() {
    // Do nothing to prevent stack trace generation.
    return this;
  }
}
