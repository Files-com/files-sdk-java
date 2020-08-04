package com.files.exceptions;


public class HttpRedirectException extends Exception {
  private static final long serialVersionUID = -7454978594211599593L;

  private final int code;
  private final String message;
  private final String redirectLocation;

  public HttpRedirectException(int code, String redirectLocation) {
    this.code = code;
    this.redirectLocation = redirectLocation;
    this.message = "";
  }

  public HttpRedirectException(int code, String redirectLocation, String message) {
    this.code = code;
    this.redirectLocation = redirectLocation;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public String getRedirectLocation() {
    return redirectLocation;
  }
}
