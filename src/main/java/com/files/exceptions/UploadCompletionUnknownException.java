package com.files.exceptions;

import com.files.net.UploadedPart;
import java.util.List;

/**
 * Every part of an upload was accepted, but the request to complete it did not show whether
 * Files.com completed the file: the connection failed, the server reported an error of its own,
 * its answer could not be read, or the upload was cancelled after the request was sent.
 *
 * <p>The SDK never repeats the final request once it may have reached Files.com, and never starts
 * the upload again by itself: Files.com forgets an upload once it completes it, so a repeated
 * request could not tell a completed upload from a missing one. Check the file at
 * {@link #getPath()}, such as its size, before uploading it again. {@link #getCause()} describes
 * the failure.
 */
public class UploadCompletionUnknownException extends SdkException {
  private static final long serialVersionUID = 1L;

  private final String path;
  private final long size;
  private final transient List<UploadedPart> parts;

  public UploadCompletionUnknownException(String path, long size, List<UploadedPart> parts, Throwable cause) {
    this(path, size, parts, "The request to complete the upload to " + path + " failed (" + cause.getClass().getSimpleName()
        + ") after it may have reached Files.com, so it is not known whether the file was completed. Check it before uploading it again.",
        cause);
  }

  /**
   * An outcome a checkpointed upload cannot know when it continues: its checkpoint records that the
   * request completing it was about to be sent, or Files.com no longer recognizes the upload it
   * records. {@code message} says which; {@code cause} is the failure, or null.
   */
  public UploadCompletionUnknownException(String path, long size, List<UploadedPart> parts, String message, Throwable cause) {
    super(message, cause);
    this.path = path;
    this.size = size;
    this.parts = parts;
  }

  /** The remote path of the upload. */
  public String getPath() {
    return path;
  }

  /** The number of bytes uploaded. */
  public long getSize() {
    return size;
  }

  /** The parts Files.com accepted, by part number. */
  public List<UploadedPart> getParts() {
    return parts;
  }
}
