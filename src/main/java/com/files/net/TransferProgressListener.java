package com.files.net;

/**
 * Receives a transfer's progress. See {@link UploadOptions.Builder#progressListener}.
 */
@FunctionalInterface
public interface TransferProgressListener {
  void onProgress(TransferProgress progress);
}
