package com.files.exceptions;

public class SdkException extends RuntimeException {
  private final String message;

  public SdkException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
