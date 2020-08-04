package com.files.exceptions;

public class ApiReportedRuntimeException extends RuntimeException {
  private static final long serialVersionUID = 7128806057900488660L;

  private final int apiFailureStatus;
  private final String apiFailureAction;
  private final String apiFailureReason;

  public ApiReportedRuntimeException(int status, String action) {
    this.apiFailureStatus = status;
    this.apiFailureAction = action;
    this.apiFailureReason = "";
  }

  public ApiReportedRuntimeException(int status, String action, String message) {
    this.apiFailureStatus = status;
    this.apiFailureAction = action;
    this.apiFailureReason = message;
  }

  public int apiFailureStatus() {
    return apiFailureStatus;
  }

  public String apiFailureAction() {
    return apiFailureAction;
  }

  public String apiFailureReason() {
    return apiFailureReason;
  }

  public String getMessage() {
    // TODO: Error Mapping
    return null;
  }

  public String getBrickMessage() {
    return apiFailureStatus + "." + apiFailureAction + "=" + apiFailureReason;
  }

}
