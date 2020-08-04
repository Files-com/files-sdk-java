package com.files.exceptions;

import java.io.IOException;

public class ReportableIoException extends IOException {
  private static final long serialVersionUID = 7576106636929137462L;

  private final int apiFailureStatus;
  private final String apiFailureAction;
  private final String apiFailureReason;

  public ReportableIoException(int status, String action) {
    this.apiFailureStatus = status;
    this.apiFailureAction = action;
    this.apiFailureReason = "";
  }

  public ReportableIoException(int status, String action, String message) {
    this.apiFailureStatus = status;
    this.apiFailureAction = action;
    this.apiFailureReason = message;
  }

  public ReportableIoException(com.files.exceptions.ApiReportedRuntimeException ex) {
    this.apiFailureStatus = ex.apiFailureStatus();
    this.apiFailureAction = ex.apiFailureAction();
    this.apiFailureReason = ex.apiFailureReason();
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
    //TODO Better error mapping;
    return null;
  }

  public String getBrickMessage() {
    return apiFailureStatus + "." + apiFailureAction + "=" + apiFailureReason;
  }

}
