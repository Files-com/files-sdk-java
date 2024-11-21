package com.files.exceptions;

public class StackSuppressedRuntimeException extends RuntimeException {
  @Override
  public synchronized Throwable fillInStackTrace() {
    // Do nothing to prevent stack trace generation.
    return this;
  }
}
