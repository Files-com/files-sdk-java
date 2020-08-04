package com.files.exceptions;

public class ApiCheckedException extends Exception {
  private static final long serialVersionUID = -5050286401142662224L;

  public ApiCheckedException() {
    super();
  }

  public ApiCheckedException(String s) {
    super(s);
  }

  public ApiCheckedException(Exception e) {
    super(e);
  }
}
