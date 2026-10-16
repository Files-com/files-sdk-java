package com.files.net;

// For tests in other packages: how many uploads of a scope wait for their place among its uploads in
// progress (UploadFileAdmission). Tests only; the SDK itself has no such API.
public final class TransferScopeTestAccess {
  private TransferScopeTestAccess() {
  }

  public static int queuedUploads(TransferScope scope) {
    return scope.uploadFileAdmission().waiting();
  }

  public static int uploadsInProgress(TransferScope scope) {
    return scope.uploadFileAdmission().running();
  }
}
