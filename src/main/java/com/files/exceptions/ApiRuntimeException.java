package com.files.exceptions;

public class ApiRuntimeException extends RuntimeException {
  private static final long serialVersionUID = 2665759897774399981L;

  public ApiRuntimeException() {
    super();
  }

  public ApiRuntimeException(String s) {
    super(s);
  }

  public ApiRuntimeException(String s, String hb) {
    super(s);
  }

  public ApiRuntimeException(String s, String hb, Throwable t) {
    super(s, t);
  }

  public ApiRuntimeException(String s, Throwable t) {
    super(s, t);
  }

  public ApiRuntimeException(Throwable t) {
    super(t);
  }
}
