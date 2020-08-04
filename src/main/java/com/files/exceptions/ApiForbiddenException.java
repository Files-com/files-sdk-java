package com.files.exceptions;

public class ApiForbiddenException extends ApiCheckedException {
  private static final long serialVersionUID = 4597807602813173429L;

  private final String apiFailureReason;

  public ApiForbiddenException(String apiFailureReason) {
    super(apiFailureReason);
    this.apiFailureReason = apiFailureReason;
  }

  public String getApiFailureReason() {
    return apiFailureReason;
  }

}
