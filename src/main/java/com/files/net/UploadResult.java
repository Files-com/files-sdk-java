package com.files.net;

import com.files.models.File;
import java.time.Duration;
import java.util.List;

/**
 * A completed upload.
 */
public final class UploadResult {
  private final String path;
  private final long size;
  private final File file;
  private final List<UploadedPart> parts;
  private final boolean parallelParts;
  private final TransferProgress finalProgress;
  private final PartSizeSource partSizeSource;

  UploadResult(String path, long size, File file, List<UploadedPart> parts, boolean parallelParts, TransferProgress finalProgress,
      PartSizeSource partSizeSource) {
    this.path = path;
    this.size = size;
    this.file = file;
    this.parts = parts;
    this.parallelParts = parallelParts;
    this.finalProgress = finalProgress;
    this.partSizeSource = partSizeSource;
  }

  /** The remote path the upload was made to, as given. */
  public String getPath() {
    return path;
  }

  /** The number of bytes uploaded. */
  public long getSize() {
    return size;
  }

  /**
   * The file, as Files.com described it in response to completing the upload. It uses the client
   * that made the upload.
   */
  public File getFile() {
    return file;
  }

  /** The parts the file was sent in, by part number. Unmodifiable. */
  public List<UploadedPart> getParts() {
    return parts;
  }

  /**
   * Whether Files.com allowed sending parts in parallel for the whole upload: false if any of its
   * instructions for a part, including a renewed one, said parts must be sent one at a time.
   */
  public boolean isParallelParts() {
    return parallelParts;
  }

  /** The bytes written to the network, including parts sent more than once. */
  public long getSentBytes() {
    return finalProgress.getSentBytes();
  }

  /** The time the upload took, until its parts were all accepted. */
  public Duration getElapsed() {
    return finalProgress.getElapsed();
  }

  /**
   * Who chose the sizes of the upload's parts: the SDK, where Files.com's instructions let it and
   * {@link UploadOptions#getPartSizing()} allowed it, the caller, or Files.com.
   */
  public PartSizeSource getPartSizeSource() {
    return partSizeSource;
  }

  /** The upload's progress once every part was accepted. */
  public TransferProgress getFinalProgress() {
    return finalProgress;
  }
}
