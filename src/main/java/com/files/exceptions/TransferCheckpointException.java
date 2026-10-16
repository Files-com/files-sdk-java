package com.files.exceptions;

import java.nio.file.Path;

/**
 * A transfer's checkpoint could not be used, so the transfer was stopped before it relied on it.
 * {@link #getReason()} tells why. The record is kept for you to inspect or discard: a refusal of the
 * directory or the record changes nothing in it, and a record that could not go on being written
 * keeps what it holds.
 */
public class TransferCheckpointException extends SdkException {
  private static final long serialVersionUID = 1L;

  /**
   * Why a checkpoint could not be used.
   */
  public enum Reason {
    /**
     * The checkpoint directory, or the record in it, is not private to the user running the
     * transfer: not that user's own, readable or writable by others, a link, or on a volume that
     * keeps no ownership or ACLs. Nothing in it was read.
     */
    UNTRUSTED,
    /** Another run of the same transfer holds the record. */
    IN_USE,
    /** The record is not one this SDK can read: another format or version, damaged, or too large. */
    UNREADABLE,
    /** The record is of the same transfer with other settings, such as another part sizing. */
    OTHER_TRANSFER,
    /**
     * Files.com no longer gives the upload the record describes as it was: another S3 upload for its
     * reference, or other sizes for the parts it recorded; or it does not allow sending a part again
     * ({@code retry_parts}), and the record shows a part that an earlier run may have sent without
     * its acceptance being recorded. The part concerned was not sent again. Discard the checkpoint and
     * upload the file again from the start.
     */
    UPLOAD_CHANGED,
    /**
     * The partial download the record describes is not as the download left it: its directory or
     * file, beside the destination, was replaced, is a link, belongs to another user or is no longer
     * private to this one. Nothing there was read, written or deleted. Discard the checkpoint to
     * download the file again; discarding leaves what is there in place.
     */
    STAGE_CHANGED,
    /**
     * The record could not be made durable where the transfer had to rely on it, such as before the
     * request completing an upload, which was therefore not sent.
     */
    NOT_DURABLE,
    /** The record has grown to the most a record may hold, so the transfer could not go on recording. */
    TOO_LARGE
  }

  private final transient Path directory;
  private final Reason reason;

  /**
   * Creates the exception.
   *
   * @param reason why the checkpoint could not be used
   * @param directory the checkpoint directory
   * @param message what happened
   */
  public TransferCheckpointException(Reason reason, Path directory, String message) {
    super(message);
    this.reason = reason;
    this.directory = directory;
  }

  /**
   * Creates the exception.
   *
   * @param reason why the checkpoint could not be used
   * @param directory the checkpoint directory
   * @param message what happened
   * @param cause the failure that made the checkpoint unusable
   */
  public TransferCheckpointException(Reason reason, Path directory, String message, Throwable cause) {
    super(message, cause);
    this.reason = reason;
    this.directory = directory;
  }

  /** Why the checkpoint could not be used. */
  public Reason getReason() {
    return reason;
  }

  /** The checkpoint directory. */
  public Path getDirectory() {
    return directory;
  }
}
