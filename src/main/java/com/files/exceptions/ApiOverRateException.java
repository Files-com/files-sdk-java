package com.files.exceptions;


public class ApiOverRateException extends RuntimeException {
  private static final long serialVersionUID = -7183333763588182988L;

  public ApiOverRateException() {
    super();
  }

  public ApiOverRateException(Throwable cause) {
    super(cause);
  }
}
