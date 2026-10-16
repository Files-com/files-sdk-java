package com.files.exceptions;

/**
 * Files.com asked for an upload's parts to be sent in a way this version of the SDK cannot
 * follow, such as with form parameters. The upload stopped before sending the part it concerns.
 */
public class UnsupportedUploadException extends SdkException {
  private static final long serialVersionUID = 1L;

  public UnsupportedUploadException(String message) {
    super(message);
  }
}
