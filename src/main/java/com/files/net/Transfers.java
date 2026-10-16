package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.exceptions.ApiErrorException;
import com.files.models.File;
import com.files.models.FileUploadPart;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;

/**
 * Uploads and downloads that run with one client: {@code client.transfers()}.
 *
 * <p><b>Uploads.</b> A file is sent in parts. Files.com plans them; where its instructions let the
 * SDK choose the sizes, for an ordinary multipart upload to S3, the SDK sizes the parts for the
 * file instead, as {@link UploadOptions.Builder#partSizing} describes. When Files.com
 * allows it, several parts are sent at the same time: by default as many as the upload measures
 * to help, as {@link UploadOptions.Builder#concurrencyMode} and
 * {@link UploadOptions.Builder#startup} describe, within {@link UploadOptions#getMaxConcurrency()},
 * the request slots and buffer space of the upload's {@link TransferLimiter} and the transport's
 * connections to the storage host; otherwise parts are sent one at a time, in order. A part that
 * fails with a connection failure, a request timeout (408), throttling (429) or a server error (5xx
 * other than 501) is sent again, with the same bytes, when Files.com allows retrying parts, up to
 * the client's retry limit. It waits the client's retry delay first; after 429 or 503 it waits the
 * storage server's whole Retry-After instead, or not at all without one, and is not sent again
 * when the Retry-After asks for longer than the SDK can wait. A part whose URL is about to expire gets a new one
 * for the same part. Parts already accepted are never sent again. Once every part has been
 * accepted, the upload is completed with a final request, which is never repeated once it may
 * have reached Files.com; if it fails in a way that does not show whether Files.com completed the
 * file, the upload throws {@link com.files.exceptions.UploadCompletionUnknownException}.
 *
 * <p>Every request uses the client's settings and the upload's request options as they were when
 * it started. The part requests go to the storage URLs Files.com provides, with the method and
 * headers it gives, and carry no Files.com credentials.
 *
 * <p><b>Stopping.</b> Cancelling the upload's {@link UploadOptions.Builder#cancellation token},
 * passing its {@link UploadOptions.Builder#timeout time limit}, interrupting the thread that
 * called the upload method, or closing the client stops the upload wherever it is: waiting for the
 * limiter, reading, sending, waiting for an answer or waiting to retry. Its requests are aborted,
 * and once every part in flight has stopped, and the upload's threads have ended, it throws
 * {@link com.files.exceptions.OperationCancelledException}. The first failure stops the upload
 * the same way and is thrown. Either way nothing more is sent: the upload is not completed, unless
 * its final request had already been sent. A read of your own stream that blocks cannot be
 * interrupted: the upload stops when it returns.
 *
 * <p><b>Checkpoints.</b> An upload of a local file given a
 * {@link UploadOptions.Builder#checkpointDirectory checkpoint directory} keeps a record there of what
 * a later run needs to continue it: Files.com's reference for the upload; each part's number,
 * offset and length, recorded durably before the part is first sent; each part the storage server
 * accepted, with its ETag; and, recorded durably before it is sent, that the request completing the
 * upload is about to be sent. The record holds no credentials, URLs, queries or sessions, and a
 * later run sends nothing it read there but the reference, and that only where its own call goes.
 * The record is bound to the API root the call is set to start at, by the SHA-256 of the whole root,
 * so that no user information, query or fragment it may carry is written; to its workspace, the
 * remote path as given, the local file's real path, and the file as it was: its file key where the
 * platform has one, its length and its modification time; and to the part sizing. A later run with
 * the same arguments continues the upload: Files.com must still recognize its reference and, where
 * the SDK chose the part sizes, give the same S3 upload, or the run throws
 * {@link com.files.exceptions.TransferCheckpointException} ({@code UPLOAD_CHANGED}) before sending
 * anything. Parts recorded as accepted are not sent again, and count in
 * {@link TransferProgress#getResumedBytes()}. A part recorded as planned but not as accepted may
 * have been sent, so it is sent again with exactly its recorded bytes, and only where Files.com's
 * answer to the later run lets parts be retried ({@code retry_parts}); otherwise the run throws
 * {@code UPLOAD_CHANGED} without sending it. A run whose file changed since the record was made
 * starts the upload over; one with another part sizing is refused ({@code OTHER_TRANSFER}). Once
 * Files.com completes or refuses the upload, the record is deleted.
 *
 * <p>When the record says the completing request was about to be sent, or Files.com no longer
 * recognizes the upload it records, the run sends nothing and throws
 * {@link com.files.exceptions.UploadCompletionUnknownException}: the upload may have been completed.
 * Check the file, then {@link #discardUploadCheckpoint discard the checkpoint} before uploading it
 * again. If the record cannot be made durable before the completing request, that request is not
 * sent ({@code NOT_DURABLE}).
 *
 * <p>The directory must be your own and private to you: on POSIX systems, permissions exactly
 * {@code rwx------}, and on macOS also without an ACL, on a volume that does not ignore ownership;
 * on Windows, a protected ACL that grants only you full control. One that does not exist yet, whose
 * parent does, is created that way. One that exists but is not, or that is a link, is refused
 * ({@code UNTRUSTED}) and nothing in it is read; the directories above it are not checked. The
 * directory and the record are opened without following a link at their own names and checked
 * through the handles they are then used through. A run holds its record locked: a second run of
 * the same upload at the same time throws ({@code IN_USE}). The record is written in place, never
 * renamed, so whether it lasts a crash depends only on its own file being flushed. Windows cannot
 * flush a directory's entries, so a record created just before a crash there may be lost, and the
 * upload then starts over; the Windows store has not been run natively in the SDK's tests.
 */
public final class Transfers {
  private static final TypeReference<File> FILE = new TypeReference<File>() {};

  private final ClientRuntime runtime;

  Transfers(ClientRuntime runtime) {
    this.runtime = runtime;
  }

  /**
   * Uploads the local file at {@code source} to {@code destinationPath}, with the default options.
   *
   * @see #upload(Path, String, UploadOptions)
   */
  public UploadResult upload(Path source, String destinationPath) throws IOException {
    return upload(source, destinationPath, UploadOptions.defaults());
  }

  /**
   * Uploads the local file at {@code source} to {@code destinationPath}.
   *
   * <p>The file is opened once and every part, on every attempt, is read through that handle, so
   * renaming or replacing the file during the upload does not change what is uploaded. The length
   * uploaded is the file's length when the upload starts. Each part is read once before it is first
   * sent, to record a digest of its bytes, and again for each attempt; an attempt whose bytes no
   * longer match is stopped before its last bytes are sent, and the upload fails, so a part is only
   * ever accepted with the bytes it had when it was first read, even if the file changes in place
   * with the same size and modification time. Parts are read a copy buffer at a time, so the file's
   * contents are never held in memory. If the file's length or modification time changes during
   * the upload, it is not completed either. The handle is closed when the upload ends, however it
   * ends.
   *
   * @param destinationPath the remote path; if null, the local file's name
   * @throws IOException if the file cannot be opened or read, or changes during the upload
   */
  public UploadResult upload(Path source, String destinationPath, UploadOptions options) throws IOException {
    return uploadFile(null, source, destinationPath != null ? destinationPath : source.getFileName().toString(), options);
  }

  /**
   * Uploads {@code length} bytes read from {@code source} to {@code destinationPath}.
   *
   * <p>The stream is read forward only, from its current position, one part after another, and
   * is never closed, reset or skipped: it is yours. Each part is read whole into memory and kept
   * until the storage server accepts it, so a retry sends exactly the same bytes; the parts held at
   * once are limited by the limiter's {@link TransferLimiter#getMaxBufferedBytes()}, and a part
   * larger than that fails the upload before it is read. To upload large parts without holding them
   * in memory, upload from a file. After a failure or cancellation the stream's position is
   * undefined.
   *
   * @throws java.io.EOFException if the stream ends before {@code length} bytes; the upload is not
   *     completed
   * @throws IOException if reading the stream fails
   */
  public UploadResult upload(InputStream source, long length, String destinationPath, UploadOptions options) throws IOException {
    if (source == null || destinationPath == null) {
      throw new IllegalArgumentException("The stream and the destination path are required");
    }
    if (length < 0) {
      throw new IllegalArgumentException("The length to upload cannot be negative");
    }
    refuseCheckpoint(options, "A stream upload");
    return run(new UploadCoordinator(runtime, destinationPath, new UploadSource.StreamSource(source, length), orDefaults(options),
        orDefaults(options).getModifiedTime(), null), null, destinationPath);
  }

  /**
   * Uploads a stream of unknown length, read until it ends, to {@code destinationPath}.
   *
   * <p>The stream is read as {@link #upload(InputStream, long, String, UploadOptions)} reads it: forward
   * only, a part at a time, never closed, reset or skipped, each part held in memory until the
   * storage server accepts it. Where the SDK chooses part sizes, they start at 8 MiB and grow as
   * needed for 10,000 parts to hold the most the stream may hold,
   * {@link UploadOptions#getMaxStreamLength()}; where parts held in memory could not grow large
   * enough for that, or the SDK may not choose sizes, the parts are those Files.com plans. Several
   * parts may be sent at once, as for any upload. An empty stream is uploaded as an empty file.
   *
   * @throws IllegalStateException if the stream holds more than
   *     {@link UploadOptions#getMaxStreamLength()} bytes; the upload is not completed
   * @throws IOException if reading the stream fails
   */
  public UploadResult upload(InputStream source, String destinationPath, UploadOptions options) throws IOException {
    if (source == null || destinationPath == null) {
      throw new IllegalArgumentException("The stream and the destination path are required");
    }
    refuseCheckpoint(options, "A stream upload");
    return run(new UploadCoordinator(runtime, destinationPath, new UploadSource.StreamSource(source, -1), orDefaults(options),
        orDefaults(options).getModifiedTime(), null), null, destinationPath);
  }

  /**
   * Sends the parts of an upload that was started with {@link File#beginUpload} or
   * {@link File#create}, from the part it returned, and completes it at that part's path. When this
   * client returned the part and the upload has the same request options as the call that returned
   * it, the upload's Files.com requests go where that call's did, even if Files.com relocated it to
   * a server of the site's own. Works like {@link #upload(Path, String, UploadOptions)} otherwise.
   */
  public UploadResult continueUpload(FileUploadPart firstPart, Path source, UploadOptions options) throws IOException {
    refuseCheckpoint(options, "An upload continued from its first part");
    return uploadFile(firstPart, source, destination(firstPart), options);
  }

  /**
   * Sends the parts of an upload that was started with {@link File#beginUpload} or
   * {@link File#create}, reading them from {@code source}, from the part it returned, and completes
   * it at that part's path. With a {@code length} of -1, parts are read until the stream ends, as
   * {@link #upload(InputStream, String, UploadOptions)} reads them. Its Files.com requests go where the call that returned the
   * part sent its own, as in {@link #continueUpload(FileUploadPart, Path, UploadOptions)}. Works
   * like {@link #upload(InputStream, long, String, UploadOptions)} otherwise.
   */
  public UploadResult continueUpload(FileUploadPart firstPart, InputStream source, long length, UploadOptions options) throws IOException {
    if (source == null) {
      throw new IllegalArgumentException("The stream is required");
    }
    if (length < -1) {
      throw new IllegalArgumentException("The length to upload must be -1, when it is not known, or more");
    }
    refuseCheckpoint(options, "An upload continued from its first part");
    String path = destination(firstPart);
    return run(new UploadCoordinator(runtime, path, new UploadSource.StreamSource(source, length), orDefaults(options),
        orDefaults(options).getModifiedTime(), firstPart), firstPart, path);
  }

  private UploadResult run(UploadCoordinator upload, FileUploadPart firstPart, String path) throws IOException {
    try (OperationScope scope = runtime.openUpload("The upload to " + path, upload.callOptions(), firstPart)) {
      return upload.run(scope);
    }
  }

  private UploadResult uploadFile(FileUploadPart firstPart, Path source, String destinationPath, UploadOptions options) throws IOException {
    UploadOptions settings = orDefaults(options);
    // The scope covers the file's handle too, so that closing the client waits until it is closed.
    try (OperationScope scope = runtime.openUpload("The upload to " + destinationPath, settings.callOptions(), firstPart);
         UploadSource.FileSource file = new UploadSource.FileSource(source);
         UploadCheckpoint checkpoint = settings.getCheckpointDirectory() == null ? null
             : UploadCheckpoint.open(settings.getCheckpointDirectory(), binding(source, destinationPath, settings, file))) {
      Instant modified = settings.getModifiedTime() != null ? settings.getModifiedTime() : file.lastModified().toInstant();
      return new UploadCoordinator(runtime, destinationPath, file, settings, modified, firstPart, checkpoint).run(scope);
    }
  }

  // What the checkpoint of an upload of file is bound to (see the class's description).
  private UploadCheckpoint.Binding binding(Path source, String destinationPath, UploadOptions settings, UploadSource.FileSource file)
      throws IOException {
    HashMap<String, Object> callOptions = settings.callOptions();
    PartSizing sizing = settings.getPartSizing();
    Object fileKey = file.fileKey();
    return new UploadCheckpoint.Binding(runtime.configuredRoot(callOptions), runtime.workspace(callOptions), destinationPath,
        source.toRealPath().toString(), fileKey == null ? null : fileKey.toString(), file.length(), file.lastModified().toInstant().toString(),
        sizing.getMode() == PartSizing.Mode.FIXED ? "FIXED:" + sizing.getPartSize() : sizing.getMode().name());
  }

  /**
   * Discards the checkpoint that {@link #upload(Path, String, UploadOptions)}, with the same
   * arguments, would continue from, in the options' {@link UploadOptions#getCheckpointDirectory()},
   * so that the next upload starts over. Files.com is not asked anything, and an upload the checkpoint
   * recorded but did not complete is left for Files.com to expire. The local file must still exist,
   * as its real path names the checkpoint.
   *
   * @param source the local file, as given to the upload
   * @param destinationPath the remote path; if null, the local file's name, as for the upload
   * @param options the upload's options, with its checkpoint directory
   * @return whether there was a checkpoint to discard
   * @throws com.files.exceptions.TransferCheckpointException if the directory cannot be trusted
   *     ({@code UNTRUSTED}), or a run of the upload holds its checkpoint ({@code IN_USE})
   * @throws IOException if the file's real path cannot be found, or the checkpoint cannot be deleted
   */
  public boolean discardUploadCheckpoint(Path source, String destinationPath, UploadOptions options) throws IOException {
    UploadOptions settings = orDefaults(options);
    if (source == null || settings.getCheckpointDirectory() == null) {
      throw new IllegalArgumentException("The local file and the options' checkpoint directory are required");
    }
    HashMap<String, Object> callOptions = settings.callOptions();
    String remotePath = destinationPath != null ? destinationPath : source.getFileName().toString();
    return UploadCheckpoint.discard(settings.getCheckpointDirectory(), UploadCheckpoint.recordName(runtime.configuredRoot(callOptions)
        .toString(), runtime.workspace(callOptions), remotePath, source.toRealPath().toString()));
  }

  // A checkpoint is kept only for an upload of a local file from its start.
  private static void refuseCheckpoint(UploadOptions options, String upload) {
    if (options != null && options.getCheckpointDirectory() != null) {
      throw new IllegalArgumentException(upload + " cannot keep a checkpoint: only an upload of a local file with Transfers.upload can."
          + " Remove the checkpoint directory from its options");
    }
  }

  private static String destination(FileUploadPart firstPart) {
    if (firstPart == null || firstPart.getRef() == null || firstPart.getPath() == null) {
      throw new IllegalArgumentException("The first part must be one Files.com returned, with its ref and path");
    }
    return firstPart.getPath();
  }

  private static UploadOptions orDefaults(UploadOptions options) {
    return options != null ? options : UploadOptions.defaults();
  }

  /**
   * Downloads the remote file at {@code remotePath} to {@code destination}, with the default
   * options.
   *
   * @see #download(File, Path, DownloadOptions)
   */
  public DownloadResult download(String remotePath, Path destination) throws IOException {
    return download(remotePath, destination, DownloadOptions.defaults());
  }

  /**
   * Downloads the remote file at {@code remotePath} to {@code destination}: its description is
   * requested first, then its contents, both within the download's one time limit. See
   * {@link #download(File, Path, DownloadOptions)}.
   */
  public DownloadResult download(String remotePath, Path destination, DownloadOptions options) throws IOException {
    DownloadOptions settings = options != null ? options : DownloadOptions.defaults();
    HashMap<String, Object> callOptions = settings.callOptions();
    // One scope covers describing the file and downloading it, so its time limit is the whole
    // download's. A checkpoint is opened first, so that one another run holds stops the download
    // before any request.
    try (OperationScope scope = runtime.open("The download of " + remotePath, callOptions);
         DownloadCheckpoint checkpoint = openCheckpoint(settings, remotePath, destination)) {
      FilesResponse response = runtime.send(RequestMethods.GET, "/files/" + UrlUtils.encodeUrlPath(remotePath), new HashMap<String, Object>(),
          callOptions, scope, runtime.maxRetries(callOptions));
      File file = runtime.readItem(response.getBody(), FILE);
      runtime.bind(file, callOptions);
      return new Downloader(runtime, requireDownloadUri(file), destination, settings, checkpoint).run(scope);
    }
  }

  /**
   * Downloads the file's contents, from its {@code download_uri}, to {@code destination}.
   *
   * <p>The contents are written to a new file in a directory of the download's own, created beside
   * the destination and private to the current user while it holds unchecked bytes. The file is
   * written only through the handle that created it. Once every byte has arrived and been checked,
   * and the file is still the one the download created, it is moved into place, replacing the
   * destination atomically; a stage replaced meanwhile is never published, written or deleted.
   * The published file keeps the permissions, and group, of the file it replaces; a new file gets
   * the permissions new files in that directory normally get. Where the file system uses ACLs
   * instead of POSIX permissions, a replaced file's ACL is kept, and a new file keeps its private
   * directory's owner-only ACL. A destination that the current user could not write, or that
   * belongs to another user, is not replaced.
   *
   * <p>On macOS and Windows, the directory and the file are created and held through the operating
   * system, with JNA: Java's own file API can neither create a macOS directory that inherits no ACL
   * entries nor create a Windows file or directory whose ACL inherits nothing. On macOS, a replaced
   * file's ACL is kept too, and a new file gets the ACL entries that new files in its directory
   * inherit. Where JNA cannot be loaded, the download throws an {@link IOException} before it
   * creates anything. On Windows, unless the application has set the directory JNA writes its
   * native library to, or turned that writing off, JNA writes its library only to a directory that only
   * this account and administrators can change, which is then set as {@code jna.tmpdir}; where
   * there is none, that too is an {@link IOException}. A library found through {@code jna.boot.library.path}
   * or {@code jna.nosys=false} is loaded as supplied, without being checked by the SDK.
   * On macOS and Windows, a download to a
   * volume that does not enforce file ownership or keep ACLs, such as a FAT drive or a macOS volume
   * set to ignore ownership, where nothing can be kept private, throws one as well.
   *
   * <p>A file Files.com describes as larger than one {@link DownloadOptions.Builder#partSize part}
   * is first asked for its first part alone. Where the storage server's answer binds the download to
   * one version of the file, a 206 with the file's size and a strong entity tag, the rest is fetched
   * in parts, several at once as {@link DownloadOptions.Builder#concurrencyMode} describes, each
   * written at its own place in the file: every part is asked for with that tag in
   * {@code If-Match}, follows no redirect, and must be answered with exactly its bytes of exactly
   * that version, or the file changed and the download fails, not tried again. A part that fails
   * in a way that may be temporary is asked for again from its first byte not yet written. Where
   * the answer binds nothing, the file comes in one response, which must be a successful one; its
   * length must match its Content-Length, and the file's size when Files.com described one, and a
   * failed attempt that may be temporary starts over, up to the client's retry limit. A download URL
   * about to expire is renewed, for the same storage resource. Any answer with Files.com's web
   * application header fails the download. When the download fails or is cancelled, what it created
   * is deleted and the destination is left as it was. The
   * {@link DownloadOptions.Builder#timeout time limit} covers the whole download.
   *
   * <p>With a {@link DownloadOptions.Builder#checkpointDirectory checkpoint directory}, a download
   * fetched in parts keeps its partial file when it stops before publishing, and a later run of the
   * same download, to the same destination, continues it. The checkpoint records the partial file's
   * directory and the identities of that directory and its file, the version of the remote file (its
   * strong entity tag, its size and a SHA-256 of its storage resource), and which ranges of it the
   * partial file holds; each range only once the partial file holds it durably. It is bound to the API
   * root the call is set to start at, as a SHA-256 of the whole root, the workspace, the remote path
   * and the destination's absolute path, and keeps no credentials, URLs or queries: a later run
   * describes the file again, or takes the given File, for a fresh URL. It continues only a partial
   * file that is still the one the download created, by its recorded identities, and still private
   * to the user, checked through the handles it then writes with; anything else at its name throws
   * {@link com.files.exceptions.TransferCheckpointException} ({@code STAGE_CHANGED}) and is left as
   * it is; one that is gone starts the download over. The version is confirmed first: the first
   * range still missing, or the last byte, is asked for with {@code If-Match} and must be answered
   * with exactly those bytes of exactly that version before any is written. If the file changed,
   * the checkpoint forgets the old version before the partial file is emptied, and the download
   * starts over; bytes of two versions are never mixed. While parts are written, what they hold is
   * made durable and recorded at most once a second, and when the download stops. A file that comes
   * in one request keeps no progress. Once the file is published, the checkpoint is deleted; a run
   * that finds the file was published, the destination being the partial file itself, completes
   * without fetching anything while Files.com still describes that version, and otherwise downloads
   * the file again under {@link DownloadOptions.Builder#replaceExisting}. A publication that failed
   * after it began changing the partial file's access deletes it and the checkpoint. A partial file
   * a crash left before the checkpoint recorded it, one refused as changed, and one whose checkpoint
   * became unreadable are not removed: delete its directory beside the destination by hand. See
   * {@link #discardDownloadCheckpoint(String, Path, DownloadOptions)}.
   *
   * @throws java.nio.file.FileAlreadyExistsException if the destination exists and
   *     {@link DownloadOptions.Builder#replaceExisting} is false
   * @throws java.nio.file.AccessDeniedException if the destination cannot be written or belongs to
   *     another user
   */
  public DownloadResult download(File file, Path destination, DownloadOptions options) throws IOException {
    if (file == null || destination == null) {
      throw new IllegalArgumentException("The file and the destination are required");
    }
    DownloadOptions settings = options != null ? options : DownloadOptions.defaults();
    if (settings.getCheckpointDirectory() != null && file.getPath() == null) {
      throw new IllegalArgumentException("A download that keeps a checkpoint needs the File's path, which names its checkpoint");
    }
    try (OperationScope scope = runtime.open("The download of " + file.getPath(), settings.callOptions());
         DownloadCheckpoint checkpoint = openCheckpoint(settings, file.getPath(), destination)) {
      return new Downloader(runtime, requireDownloadUri(file), destination, settings, checkpoint).run(scope);
    }
  }

  private DownloadCheckpoint openCheckpoint(DownloadOptions settings, String remotePath, Path destination) throws IOException {
    return settings.getCheckpointDirectory() == null ? null
        : DownloadCheckpoint.open(settings.getCheckpointDirectory(), downloadBinding(settings, remotePath, destination));
  }

  // What the checkpoint of a download is bound to (see download(File, Path, DownloadOptions)).
  private DownloadCheckpoint.Binding downloadBinding(DownloadOptions settings, String remotePath, Path destination) {
    HashMap<String, Object> callOptions = settings.callOptions();
    return new DownloadCheckpoint.Binding(runtime.configuredRoot(callOptions), runtime.workspace(callOptions), remotePath,
        destination.toAbsolutePath().normalize().toString());
  }

  /**
   * Discards the checkpoint that a download of {@code remotePath} to {@code destination}, with the
   * same options, would continue from, in the options'
   * {@link DownloadOptions#getCheckpointDirectory()}, and the partial file it records, if that is
   * still the one the download created; anything else at its name is left in place. Files.com is not
   * asked anything, and the destination is not touched.
   *
   * @param remotePath the remote path, as given to the download, or the File's path
   * @param destination the destination, as given to the download
   * @param options the download's options, with its checkpoint directory
   * @return whether there was a checkpoint to discard
   * @throws com.files.exceptions.TransferCheckpointException if the directory cannot be trusted
   *     ({@code UNTRUSTED}), or a run of the download holds its checkpoint ({@code IN_USE})
   * @throws IOException if the checkpoint cannot be deleted
   */
  public boolean discardDownloadCheckpoint(String remotePath, Path destination, DownloadOptions options) throws IOException {
    DownloadOptions settings = options != null ? options : DownloadOptions.defaults();
    if (remotePath == null || destination == null || settings.getCheckpointDirectory() == null) {
      throw new IllegalArgumentException("The remote path, the destination and the options' checkpoint directory are required");
    }
    return DownloadCheckpoint.discard(settings.getCheckpointDirectory(), downloadBinding(settings, remotePath, destination),
        destination.toAbsolutePath());
  }

  private static File requireDownloadUri(File file) {
    if (file.getDownloadUri() == null || file.getDownloadUri().isEmpty()) {
      throw new ApiErrorException.ApiConnectionException("Files.com gave no download URL for " + file.getPath());
    }
    return file;
  }

  /**
   * Opens the file's contents, from its {@code download_uri}, as a stream to read and close.
   */
  public FilesApacheHttpInputStream openDownload(File file) {
    runtime.ensureOpen();
    return runtime.openDownload(file.getDownloadUri());
  }
}
