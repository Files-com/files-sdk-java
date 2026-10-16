package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.OperationCancelledException;
import com.files.exceptions.SdkException;
import com.files.exceptions.TransferCheckpointException;
import com.files.models.File;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Downloads one file's contents into a stage of the download's own beside the destination (see
// DownloadStage), checks them, and only then publishes the stage at the destination. A failure or
// cancellation deletes what the download created and leaves the destination, and every other
// file, as it was.
//
// How the bytes are fetched depends on what the storage server answers, never on its host:
//
//   bound to a version  A file Files.com describes as larger than one part is first asked for its
//                       first part alone: a Range request without a condition, which follows no
//                       redirect. An answer of 206 from byte 0, with a Content-Range stating the
//                       file's size and a strong entity tag, binds the download to that version.
//                       Every later request carries If-Match with that tag, follows no redirect, goes
//                       to the same storage resource (the URL before its query, which renewed URLs
//                       must keep), and must be answered 206 with exactly the bytes asked for, of
//                       exactly that version: a Content-Range naming them and the version's size, a
//                       matching Content-Length if any, the version's entity tag, and a body of exactly
//                       that length. The rest of the file is fetched in parts, several at once, as the
//                       download's concurrency allows (ConcurrencyController; an automatic download
//                       starts at its policy's start), each written at its own position in the stage.
//                       A part whose request fails in a way that may be temporary is asked for again
//                       from its first byte not yet written. 412, another size or another entity tag
//                       mean the file changed: the download fails and is not tried again.
//   not bound           Otherwise (a file of one part or of unknown size, or a first answer of 200, a
//                       redirect, 416, or 206 without a strong entity tag), the file comes in one
//                       request, from its first byte, started over after a failure that may be
//                       temporary; it must have the length its answer declares and the size Files.com
//                       described.
//
// A bound part answered 200 shows a server that ignores Range: the download starts over in one
// request. An answer from the Files.com web application is refused before any byte is written. A
// storage Retry-After is waited in full; one longer than the SDK can wait is not retried.
//
// Every storage request, a bound part's attempt, the first part, a resumed version's confirmation and
// the file in one request alike, holds a request slot of the download's limiter (and, where it counts
// against a shared target, its admission there), then a slot for its storage host from the transport,
// until its response is closed, and gives them all back before any retry waits. It copies through a
// buffer whose space it holds in the limiter: a bound part from before its first attempt until it has
// been written, retries included; the others only during each attempt. In a scope whose automatic
// downloads share a target (TransferScope), the bound parts and the requests for the file in one
// request count against it and teach it; the first part and a resumed version's confirmation are
// asked for alone, outside it.
//
// With a checkpoint (DownloadCheckpoint), the stage is kept when the download stops before
// publishing, and the record says which of its ranges of which version it holds durably, in the
// order the record describes. A later run reopens that stage (DownloadStage.reopen) and confirms
// the version: the first range still missing, or the last byte when none is, is asked for with
// If-Match and must come back as exactly those bytes of exactly that version, its Content-Range,
// size, entity tag and length checked, before any of them is written. Then only the missing ranges
// are fetched, under the rules above. A changed size, entity tag or storage resource, 412, 416, a
// redirect or a range that is not the one asked for means the recorded version is gone: the record
// forgets it, durably, the stage is emptied, and the download starts over as a first run would; 200
// means the file comes in one request, after the same. Bytes of two versions are never mixed.
final class Downloader {
  private static final Logger log = LoggerFactory.getLogger(Downloader.class);
  private static final TypeReference<File> FILE = new TypeReference<File>() {};
  private static final long MAX_RETRY_AFTER_MILLIS = TimeUnit.NANOSECONDS.toMillis(ConcurrencyController.MAX_PAUSE_NANOS);
  private static final int COPY_BUFFER_BYTES = 64 * 1024;
  // Each part being fetched copies through a buffer of its own of at most this many bytes, which it
  // holds in the limiter's buffer space.
  private static final int PART_BUFFER_BYTES = 256 * 1024;
  // A URL that expires sooner than this is renewed before a part is fetched from it.
  private static final Duration RENEW_URLS_EXPIRING_WITHIN = Duration.ofSeconds(30);
  // A checkpointed download records the ranges it has written at most this often while it fetches
  // parts, and when it stops.
  private static final long SNAPSHOT_INTERVAL_NANOS = TimeUnit.SECONDS.toNanos(1);
  private static final Pattern CONTENT_RANGE = Pattern.compile("bytes (\\d+)-(\\d+)/(\\d+)");
  private static final AtomicInteger THREADS = new AtomicInteger();

  private final ClientRuntime runtime;
  private final File file;
  private final Path destination;
  private final DownloadOptions options;
  private final String path;
  private final HashMap<String, Object> callOptions;
  private final int maxRetries;
  private final long retryDelayMillis;
  private final TransferLimiter limiter;
  // The download's checkpoint, or null.
  private final DownloadCheckpoint checkpoint;

  private final Object lock = new Object();
  private final Object renewLock = new Object();
  private final AtomicLong receivedBytes = new AtomicLong();
  private final AtomicInteger retries = new AtomicInteger();
  // The URL the contents are fetched from, renewed when it is about to expire.
  private volatile String url;
  // The version the download is bound to, once its first answer has bound it.
  private volatile Version version;
  private volatile AttemptAdmission attempts;
  private long started;
  private volatile long totalBytes = -1;
  // Bytes of parts written whole, and parts written whole, and those not yet reported.
  private long completedBytes;
  private long completedParts;
  private int unreported;
  private int inFlight;
  private Throwable failure;
  // The ranges of the version written whole, which a checkpoint records once they are durable; a
  // count of their changes, and the change last recorded; when they were last recorded; and the
  // bytes an earlier run had written.
  private final DownloadCheckpoint.Ranges writtenRanges = new DownloadCheckpoint.Ranges();
  private long rangesChanges;
  private long rangesRecorded;
  private long lastSnapshot;
  private volatile long resumedBytes;

  Downloader(ClientRuntime runtime, File file, Path destination, DownloadOptions options) {
    this(runtime, file, destination, options, null);
  }

  Downloader(ClientRuntime runtime, File file, Path destination, DownloadOptions options, DownloadCheckpoint checkpoint) {
    this.runtime = runtime;
    this.checkpoint = checkpoint;
    this.file = file;
    this.destination = destination;
    this.options = options;
    this.path = file.getPath();
    this.callOptions = options.callOptions();
    this.maxRetries = runtime.maxRetries(callOptions);
    this.retryDelayMillis = runtime.settings().retryDelay().toMillis();
    this.limiter = options.getLimiter() != null ? options.getLimiter()
        : options.getScope() != null ? options.getScope().getLimiter()
        : new TransferLimiter(ownRequests(options), TransferLimiter.DEFAULT_MAX_BUFFERED_BYTES);
    this.url = file.getDownloadUri();
  }

  // The request slots of a download's own limiter: as many as it may fetch at once.
  private static int ownRequests(DownloadOptions options) {
    if (options.getMaxConcurrency() != null) {
      return options.getMaxConcurrency();
    }
    return options.getConcurrencyMode() == ConcurrencyMode.STATIC ? DownloadOptions.DEFAULT_MAX_CONCURRENCY : ConcurrencyTuning.MAX_PARTS;
  }

  // Runs the download within scope, which covers the whole download: the caller may already have
  // spent part of its time limit describing the file.
  DownloadResult run(OperationScope scope) throws IOException {
    if (options.getScope() != null && options.getScope().isClosed()) {
      throw new IllegalStateException("The transfer scope of the download of " + path + " is closed");
    }
    started = System.nanoTime();
    Path target = destination.toAbsolutePath();
    if (checkpoint != null) {
      return runCheckpointed(target, scope);
    }
    if (!options.isReplaceExisting() && Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
      throw new FileAlreadyExistsException(target.toString());
    }
    try (DownloadStage stage = DownloadStage.create(target)) {
      long size = fetch(stage, scope, null);
      scope.check();
      stage.publish(target, options.isReplaceExisting());
      return new DownloadResult(file, target, size, version != null, progress());
    }
  }

  // Runs the download keeping its checkpoint: continues the partial download an earlier run
  // recorded, or starts one and records it. When the download stops before publishing, its stage is
  // kept while the record holds a version of which it claims ranges, and a publication that failed
  // did not begin changing the file's access; otherwise the stage and the record are deleted, as
  // without a checkpoint. Once the file is published, the record is deleted.
  private DownloadResult runCheckpointed(Path target, OperationScope scope) throws IOException {
    DownloadCheckpoint.Saved saved = checkpoint.saved();
    if (saved != null && saved.publishing && isPublished(target, saved)) {
      if (describesRecordedVersion(saved.version)) {
        return publishedEarlier(target, saved);
      }
      // Files.com now describes another version than the one the earlier run published: that run is
      // done with, and the file is downloaded again, as a first run would, under the caller's replace
      // policy. Only what is left of its stage, if it is the download's own, is removed; the record is
      // replaced once this run records its stage.
      log.info("Files.com describes {} otherwise than the version an earlier run published; the download starts over", path);
      removePublishedStage(target, saved);
      saved = null;
    }
    if (!options.isReplaceExisting() && Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
      throw new FileAlreadyExistsException(target.toString());
    }
    DownloadStage stage = saved != null && saved.stage != null ? DownloadStage.reopen(target, saved.stage) : null;
    boolean reopened = stage != null;
    if (!reopened) {
      if (saved != null && saved.stage != null) {
        log.info("The partial download of {} that its checkpoint records is gone; the download starts over", path);
      }
      stage = DownloadStage.create(target);
      try {
        checkpoint.recordStage(stage.identity());
      } catch (IOException | RuntimeException | Error e) {
        stage.close();
        throw e;
      }
    }
    boolean published = false;
    try {
      if (reopened && saved.version == null) {
        // Kept by a run that stopped before it bound a version: what it holds is not claimed, and
        // is emptied, so that no byte of it can outlast the file this run writes.
        stage.restart();
      }
      long size = fetch(stage, scope, reopened && saved.version != null ? saved : null);
      scope.check();
      if (version != null) {
        checkpoint.publishing();
      }
      stage.publish(target, options.isReplaceExisting());
      published = true;
      DownloadResult result = new DownloadResult(file, target, size, version != null, progress());
      checkpoint.delete();
      return result;
    } finally {
      if (!published) {
        if (checkpoint.hasVersion() && !stage.exposed) {
          stage.retain();
        } else {
          checkpoint.delete();
        }
      }
      stage.close();
    }
  }

  // Whether an earlier run published the stage the record describes at target and stopped before
  // deleting the record: target, not followed if a link, is the stage's own file, of the version's
  // size. A rename into place keeps a file's identity.
  private static boolean isPublished(Path target, DownloadCheckpoint.Saved saved) throws IOException {
    String identity = DownloadStage.fileIdentity(target);
    return identity != null && identity.equals(saved.stage.fileId)
        && Files.readAttributes(target, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).size() == saved.version.size;
  }

  // Whether Files.com's description of the file, as this run was given it, is of the version recorded,
  // by the rules resuming applies before any request: the same size, where it gives one, and the same
  // storage resource.
  private boolean describesRecordedVersion(DownloadCheckpoint.Version recorded) {
    return (file.getSize() == null || file.getSize() == recorded.size) && recorded.resource.equals(DownloadCheckpoint.digest(resourceOf(url)));
  }

  // The download an earlier run published, of the version Files.com still describes: what is left of
  // its stage, if it is the download's own, and the record are deleted, and nothing is fetched.
  private DownloadResult publishedEarlier(Path target, DownloadCheckpoint.Saved saved) throws IOException {
    log.info("The download of {} was published by an earlier run; its checkpoint is deleted", path);
    removePublishedStage(target, saved);
    checkpoint.delete();
    totalBytes = saved.version.size;
    resumedBytes = saved.version.size;
    synchronized (lock) {
      completedBytes = saved.version.size;
    }
    return new DownloadResult(file, target, saved.version.size, true, progress());
  }

  // Removes what is left of the stage of a download an earlier run published, if it is still the
  // download's own: its empty directory, or its own name for a file published as a hard link.
  // Anything else is left in place.
  private void removePublishedStage(Path target, DownloadCheckpoint.Saved saved) {
    try {
      DownloadStage left = DownloadStage.reopen(target, saved.stage);
      if (left != null) {
        left.close();
      }
    } catch (IOException | TransferCheckpointException e) {
      log.warn("What is left of the published download's stage is no longer the download's own, or could not be opened; it was left"
          + " in place");
      log.debug("Reopening the stage of the published download of {} failed", path, e);
    }
  }

  // Fetches the file into the stage: continuing the version resumed records, when it is not null.
  private long fetch(DownloadStage stage, OperationScope scope, DownloadCheckpoint.Saved resumed) throws IOException {
    if (resumed != null) {
      Resumption resumption = resume(stage, resumed, scope);
      if (resumption == Resumption.BOUND) {
        try {
          return fetchParts(stage, version, missing(version.size), scope);
        } catch (Unbound e) {
          unbound();
          return fetchWhole(stage, scope);
        }
      }
      if (resumption == Resumption.WHOLE) {
        unbound();
        return fetchWhole(stage, scope);
      }
      startOver(stage);
    }
    long stated = file.getSize() != null ? file.getSize() : -1;
    if (stated > options.getPartSize()) {
      totalBytes = stated;
      Version bound = establish(stage, scope);
      if (bound != null) {
        version = bound;
        totalBytes = bound.size;
        long first = Math.min(options.getPartSize(), bound.size);
        synchronized (lock) {
          writtenRanges.add(0, first);
          rangesChanges++;
        }
        if (checkpoint != null) {
          stage.force();
          checkpoint.recordVersion(bound.etag, bound.size, bound.resource, writtenRanges());
          synchronized (lock) {
            rangesRecorded = rangesChanges;
          }
        }
        try {
          return fetchParts(stage, bound, missing(bound.size), scope);
        } catch (Unbound e) {
          unbound();
        }
      }
    }
    return fetchWhole(stage, scope);
  }

  // A bound part was answered 200: the parts written so far are forgotten, and the file comes in one
  // request.
  private void unbound() {
    log.info("The storage server ignored a range of the download of {}; downloading it in one request", path);
    version = null;
    attempts = null;
    resumedBytes = 0;
    synchronized (lock) {
      completedBytes = 0;
      completedParts = 0;
      writtenRanges.clear();
      rangesChanges++;
    }
  }

  private enum Resumption {
    // The version is confirmed, and its first missing range, if any, written.
    BOUND,
    // The storage server ignores ranges: the file comes in one request.
    WHOLE,
    // The recorded version is not the storage server's any more.
    CHANGED
  }

  // Continues the version an earlier run recorded, once the storage server confirms it: the first
  // range still missing, or the last byte when none is, is asked for with If-Match, and must come
  // back as exactly those bytes of exactly that version before any of them is written.
  private Resumption resume(DownloadStage stage, DownloadCheckpoint.Saved saved, OperationScope scope) throws IOException {
    DownloadCheckpoint.Version recorded = saved.version;
    synchronized (lock) {
      for (long[] range : saved.ranges) {
        writtenRanges.add(range[0], range[1]);
      }
      completedBytes = writtenRanges.bytes();
      rangesRecorded = rangesChanges;
      resumedBytes = completedBytes;
    }
    totalBytes = recorded.size;
    if (file.getSize() != null && file.getSize() != recorded.size) {
      log.info("Files.com describes {} with another size than its checkpoint records; the download starts over", path);
      return Resumption.CHANGED;
    }
    List<long[]> missing = missing(recorded.size);
    long from = missing.isEmpty() ? recorded.size - 1 : missing.get(0)[0];
    long to = missing.isEmpty() ? from : Math.min(from + options.getPartSize(), missing.get(0)[1]) - 1;
    // A copy buffer for the missing range, no larger than a part's; none for the last byte alone.
    int bufferBytes = missing.isEmpty() ? 0 : copyBufferBytes(Math.min(PART_BUFFER_BYTES, to - from + 1));
    for (int retry = 0; ; retry++) {
      scope.check();
      Attempt attempt;
      try (StorageRequest storage = storageRequest(bufferBytes, null, false, scope)) {
        String uri = storage.uri;
        if (!recorded.resource.equals(DownloadCheckpoint.digest(resourceOf(uri)))) {
          log.info("Files.com gives the contents of {} from another storage resource than its checkpoint records; the download starts over",
              path);
          return Resumption.CHANGED;
        }
        CloseableHttpResponse response = storage.execute(request(uri, from, to, recorded.etag), false);
        refuseWebApplication(response);
        int status = response.getStatusLine().getStatusCode();
        if (status == 206) {
          ContentRange range = ContentRange.of(response);
          String etag = entityTag(response);
          long declared = response.getEntity() != null ? response.getEntity().getContentLength() : -1;
          if (range == null || !isStrongEntityTag(etag) || !etag.equals(recorded.etag) || range.total != recorded.size || range.start != from
              || range.end != to || (declared >= 0 && declared != to - from + 1)) {
            log.info("The storage server answered the download of {} with another version, or other bytes, than its checkpoint records;"
                + " the download starts over", path);
            return Resumption.CHANGED;
          }
          if (missing.isEmpty()) {
            version = new Version(recorded.etag, recorded.size, resourceOf(uri));
            return Resumption.BOUND;
          }
          long length = to - from + 1;
          byte[] buffer = new byte[bufferBytes];
          long written = copyRange(response.getEntity(), buffer, stage, from, length, scope, null, new long[1]);
          if (written == length) {
            version = new Version(recorded.etag, recorded.size, resourceOf(uri));
            synchronized (lock) {
              writtenRanges.add(from, to + 1);
              rangesChanges++;
              completedBytes += length;
              completedParts++;
            }
            return Resumption.BOUND;
          }
          attempt = Attempt.failed(new ApiErrorException.ApiConnectionException("Bytes " + from + "-" + to + " of the download of " + path
              + " ended after " + written + " bytes"), true, -1, "a short response");
        } else if (status == 200) {
          return Resumption.WHOLE;
        } else if (status == 412 || status == 416 || (status >= 300 && status < 400)) {
          log.info("The storage server no longer has the version of {} that its checkpoint records (HTTP {}); the download starts over",
              path, status);
          return Resumption.CHANGED;
        } else {
          attempt = failedStatus(response, status);
        }
      } catch (LocalFailure e) {
        throw e.failure();
      } catch (IOException e) {
        attempt = connectionFailure(e, scope);
      }
      waitToRetry(attempt, retry, scope);
    }
  }

  // The version an earlier run recorded is gone: the record forgets it, durably, and only then is the
  // stage emptied, so the record never claims bytes of another version. The download then starts as
  // a first run would.
  private void startOver(DownloadStage stage) throws IOException {
    checkpoint.clearVersion();
    stage.restart();
    version = null;
    totalBytes = -1;
    resumedBytes = 0;
    synchronized (lock) {
      writtenRanges.clear();
      rangesChanges++;
      completedBytes = 0;
      completedParts = 0;
    }
  }

  // The ranges of a file of size not yet written.
  private List<long[]> missing(long size) {
    synchronized (lock) {
      return writtenRanges.missing(size);
    }
  }

  private List<long[]> writtenRanges() {
    synchronized (lock) {
      return writtenRanges.list();
    }
  }

  // Records the ranges written whole, once the stage holds them durably: at most once a second while
  // parts are written, and whenever now is set. The stage is made durable first, so the record
  // never claims a byte that a crash could lose. Only a download that keeps a checkpoint holding a
  // version records them.
  private void checkpointProgress(DownloadStage stage, boolean now) throws IOException {
    if (checkpoint == null || !checkpoint.hasVersion()) {
      return;
    }
    List<long[]> ranges;
    long changes;
    synchronized (lock) {
      if (rangesChanges == rangesRecorded || (!now && System.nanoTime() - lastSnapshot < SNAPSHOT_INTERVAL_NANOS)) {
        return;
      }
      changes = rangesChanges;
      ranges = writtenRanges.list();
    }
    lastSnapshot = System.nanoTime();
    stage.force();
    checkpoint.snapshot(ranges);
    synchronized (lock) {
      rangesRecorded = changes;
    }
  }

  // Records what was written before the download stopped, as far as it can: a failure to is logged,
  // and the download's own failure thrown.
  private void keepProgress(DownloadStage stage) {
    try {
      checkpointProgress(stage, true);
    } catch (IOException | RuntimeException e) {
      log.debug("Could not record what the download of {} had written", path, e);
    }
  }

  // Reports the parts written since the last report, and records them in the checkpoint when it is
  // time to.
  private void progressed(DownloadStage stage) throws IOException {
    reportCompleted();
    checkpointProgress(stage, false);
  }

  // Asks for the first part alone, without a condition. Returns the version its answer binds the
  // download to, having written the part, or null when it binds none and the file is to come in one
  // request instead. A failure that may be temporary is tried again.
  private Version establish(DownloadStage stage, OperationScope scope) throws IOException {
    long length = options.getPartSize();
    // One copy buffer for the first part, no larger than a part's: the file is larger than one part.
    int bufferBytes = copyBufferBytes(Math.min(PART_BUFFER_BYTES, length));
    for (int retry = 0; ; retry++) {
      scope.check();
      Attempt attempt;
      try (StorageRequest storage = storageRequest(bufferBytes, null, false, scope)) {
        String uri = storage.uri;
        CloseableHttpResponse response = storage.execute(request(uri, 0, length - 1, null), false);
        refuseWebApplication(response);
        int status = response.getStatusLine().getStatusCode();
        if (status == 206) {
          ContentRange range = ContentRange.of(response);
          String etag = entityTag(response);
          long declared = response.getEntity() != null ? response.getEntity().getContentLength() : -1;
          boolean firstPart = range != null && range.start == 0 && range.end == Math.min(length, range.total) - 1;
          if (firstPart && file.getSize() != null && range.total != file.getSize()) {
            // The storage server's file is not the one Files.com described: neither binding a version
            // nor one request could give its described size, so the download fails before any byte is
            // written, and is not tried again.
            throw sizeMismatch(range.total);
          }
          if (!firstPart || !isStrongEntityTag(etag) || (declared >= 0 && declared != range.end + 1)) {
            log.debug("The first answer for {} binds no version of it; downloading it in one request", path);
            return null;
          }
          byte[] buffer = new byte[bufferBytes];
          long written = copyRange(response.getEntity(), buffer, stage, 0, range.end + 1, scope, null, new long[1]);
          if (written == range.end + 1) {
            synchronized (lock) {
              completedBytes += written;
              completedParts++;
            }
            return new Version(etag, range.total, resourceOf(uri));
          }
          attempt = Attempt.failed(new ApiErrorException.ApiConnectionException("The first part of the download of " + path + " ended after "
              + written + " of its " + (range.end + 1) + " bytes"), true, -1, "a short response");
        } else if (status == 200 || status == 416 || (status >= 300 && status < 400)) {
          return null;
        } else {
          attempt = failedStatus(response, status);
        }
      } catch (LocalFailure e) {
        throw e.failure();
      } catch (IOException e) {
        attempt = connectionFailure(e, scope);
      }
      waitToRetry(attempt, retry, scope);
    }
  }

  // Fetches the missing ranges of the version, in parts, several at once, and makes them durable.
  private long fetchParts(DownloadStage stage, Version bound, List<long[]> missing, OperationScope scope) throws IOException {
    ConcurrencySettings settings = settings();
    AttemptAdmission sharedAdmission = sharedAdmission(settings);
    attempts = sharedAdmission != null ? sharedAdmission : new AttemptAdmission(new ConcurrencyController(settings, null, false), System::nanoTime);
    FifoBudget inProgress = new FifoBudget(2L * settings.max);
    long partSize = options.getPartSize();
    int bufferBytes = (int) Math.min(PART_BUFFER_BYTES, partSize);
    log.debug("The download of {} is bound to a version of {} bytes: {} concurrency, starting at {} and at most {} parts at once", path,
        bound.size, settings.mode, attempts.target(), settings.max);
    boolean interrupted = false;
    ExecutorService workers = null;
    OperationScope stop = scope.child();
    try {
      workers = Executors.newCachedThreadPool(runnable -> {
        Thread thread = new Thread(runnable, "files-sdk-download-" + THREADS.incrementAndGet());
        thread.setDaemon(true);
        return thread;
      });
      report(progress());
      for (long[] range : missing) {
        for (long start = range[0]; start < range[1]; start += partSize) {
          progressed(stage);
          stop.check();
          dispatch(new Part(start, Math.min(partSize, range[1] - start)), bufferBytes, inProgress, stage, workers, stop);
        }
      }
      awaitParts(stop, stage);
    } catch (OperationCancelledException | OperationScope.Stopped e) {
      // Reported below: the cancellation, or the failure that stopped the download.
      stop.fail();
    } catch (Throwable e) {
      fail(e, stop);
    } finally {
      interrupted = awaitParts(stop, null);
      if (workers != null) {
        workers.shutdown();
        interrupted |= awaitTermination(workers);
      }
      stop.close();
    }
    Throwable failed;
    synchronized (lock) {
      failed = failure;
      failure = null;
    }
    try {
      if (failed != null) {
        if (interrupted) {
          Thread.currentThread().interrupt();
        }
        throw rethrow(failed);
      }
      if (interrupted) {
        scope.interrupted();
      }
      scope.check();
      synchronized (lock) {
        if (completedBytes != bound.size) {
          throw new IllegalStateException("Only " + completedBytes + " of the " + bound.size + " bytes of the download of " + path
              + " were written");
        }
      }
      stage.force();
      reportCompleted();
      checkpointProgress(stage, true);
      return bound.size;
    } catch (IOException | RuntimeException | Error e) {
      // What was written whole before the download stopped is kept for a later run.
      keepProgress(stage);
      throw e;
    }
  }

  // Waits for the part's buffer space, a place among the parts in progress and its first attempt's
  // admission and request slot, and starts fetching it on a thread of its own.
  private void dispatch(Part part, int bufferBytes, FifoBudget inProgress, DownloadStage stage, ExecutorService workers, OperationScope stop) {
    try {
      part.buffer = limiter.bufferedBytes().acquire(bufferBytes, stop);
      part.inProgress = inProgress.acquire(1, stop);
      part.attempt = admitAttempt(false, stop);
      synchronized (lock) {
        inFlight++;
      }
      try {
        workers.execute(() -> stop.runPart(() -> fetchPart(part, bufferBytes, stage, stop)));
      } catch (RuntimeException e) {
        synchronized (lock) {
          inFlight--;
        }
        throw e;
      }
    } catch (RuntimeException | Error e) {
      part.release();
      throw e;
    }
  }

  // Waits for an attempt's admission, a retry ahead of new parts, then for a request slot from the
  // limiter, which the attempt holds. The attempt is not started.
  private AttemptAdmission.Attempt admitAttempt(boolean retry, OperationScope stop) {
    return admitAttempt(attempts, retry, stop);
  }

  private AttemptAdmission.Attempt admitAttempt(AttemptAdmission admission, boolean retry, OperationScope stop) {
    AttemptAdmission.Attempt attempt = admission.admit(retry, stop);
    try {
      attempt.hold(limiter.requests().acquire(1, stop));
    } catch (RuntimeException | Error e) {
      attempt.close();
      throw e;
    }
    return attempt;
  }

  // Fetches the part until all of it has been written, on one of the download's threads. A failed
  // attempt that may be temporary is followed by one for the part's bytes not yet written, within the
  // client's retry limit.
  private void fetchPart(Part part, int bufferBytes, DownloadStage stage, OperationScope stop) {
    byte[] buffer = new byte[bufferBytes];
    try {
      int attempt = 0;
      while (true) {
        if (part.attempt == null) {
          part.attempt = admitAttempt(true, stop);
        }
        renewIfExpiring(stop);
        String uri = url;
        part.attempt.hold(connectionSlot(uri, stop));
        if (!part.attempt.tryStart()) {
          // A pause or a lowered target overtook the attempt while it waited: it asks again.
          part.attempt = null;
          continue;
        }
        attempt++;
        long[] writing = new long[1];
        long before = part.written;
        long sending = System.nanoTime();
        Attempt outcome = fetchRange(part, uri, buffer, stage, stop, writing);
        List<ConcurrencyController.Decision> decisions = part.attempt.complete(sample(outcome, part.written - before,
            System.nanoTime() - sending - writing[0]), log.isDebugEnabled());
        part.attempt = null;
        for (ConcurrencyController.Decision decision : decisions) {
          log.debug("Download of {}: {}", path, decision);
        }
        if (outcome.error == null) {
          synchronized (lock) {
            completedBytes += part.length;
            completedParts++;
            unreported++;
            writtenRanges.add(part.start, part.start + part.length);
            rangesChanges++;
            lock.notifyAll();
          }
          return;
        }
        if (!outcome.retryable || attempt > maxRetries) {
          throw outcome.error;
        }
        if (outcome.retryAfterMillis > MAX_RETRY_AFTER_MILLIS) {
          log.info("Not retrying a part of the download of {}: the storage server's Retry-After asks for longer than the SDK waits", path);
          throw outcome.error;
        }
        long delay = outcome.retryAfterMillis >= 0 ? outcome.retryAfterMillis : retryDelayMillis;
        retries.incrementAndGet();
        log.info("Retrying bytes {}-{} of the download of {} (retry {} of {}) in {} ms after {}", part.start + part.written,
            part.start + part.length - 1, path, attempt, maxRetries, delay, outcome.reason);
        stop.sleep(delay);
      }
    } catch (OperationCancelledException | OperationScope.Stopped e) {
      // The download was cancelled, or stopped by a failure elsewhere, which it reports.
    } catch (Throwable e) {
      fail(e, stop);
    } finally {
      part.release();
      synchronized (lock) {
        inFlight--;
        lock.notifyAll();
      }
    }
  }

  // One request for the part's bytes not yet written, of the version, written at their positions a
  // buffer at a time as they arrive (copyRange). writing[0] receives the time spent writing to the
  // stage.
  private Attempt fetchRange(Part part, String uri, byte[] buffer, DownloadStage stage, OperationScope stop, long[] writing) throws IOException {
    Version bound = version;
    if (!bound.resource.equals(resourceOf(uri))) {
      throw changed("Files.com gave a download URL for another storage resource");
    }
    long from = part.start + part.written;
    long to = part.start + part.length - 1;
    HttpGet request = request(uri, from, to, bound.etag);
    try (ClientRuntime.StorageExchange exchange = runtime.executeStorage(request, stop, false)) {
      CloseableHttpResponse response = exchange.response();
      refuseWebApplication(response);
      int status = response.getStatusLine().getStatusCode();
      if (status == 412) {
        throw changed("the storage server no longer has the version it started with (HTTP 412)");
      }
      if (status == 200) {
        throw new Unbound();
      }
      if (status != 206) {
        return failedStatus(response, status);
      }
      ContentRange range = ContentRange.of(response);
      String etag = entityTag(response);
      if (range != null && range.total != bound.size) {
        throw changed("the storage server describes it with another size");
      }
      if (etag == null || !bound.matches(etag)) {
        throw changed("the storage server answered with another version of it");
      }
      long declared = response.getEntity() != null ? response.getEntity().getContentLength() : -1;
      if (range == null || range.start != from || range.end != to || (declared >= 0 && declared != to - from + 1)) {
        throw new ApiErrorException.ApiConnectionException("The storage server answered bytes " + from + "-" + to + " of the download of " + path
            + " with other bytes than those asked for");
      }
      long written = copyRange(response.getEntity(), buffer, stage, from, to - from + 1, stop, part, writing);
      if (written < to - from + 1) {
        return Attempt.failed(new ApiErrorException.ApiConnectionException("Bytes " + from + "-" + to + " of the download of " + path
            + " ended after " + written + " bytes"), true, -1, "a short response");
      }
      return Attempt.succeeded(written);
    } catch (LocalFailure e) {
      throw e.failure();
    } catch (IOException e) {
      return connectionFailure(e, stop);
    }
  }

  // Copies length bytes of the response body to the stage, at position, through buffer, and returns
  // how many were written: fewer if the body ended early. The body's reads, however short, fill the
  // buffer before it is written, so it is written in few large writes. A part's written count follows
  // each write, so that a failure leaves it at the first byte not yet written: the bytes read before a
  // read fails are written first, unless the download has stopped, and none read once it has stopped
  // are. Its attempt is marked as writing locally only while it writes. writing[0] receives the time
  // spent writing.
  private long copyRange(HttpEntity entity, byte[] buffer, DownloadStage stage, long position, long length, OperationScope stop, Part part,
      long[] writing) throws IOException {
    if (entity == null) {
      return 0;
    }
    long written = 0;
    try (InputStream in = entity.getContent()) {
      boolean ended = false;
      while (written < length && !ended) {
        int wanted = (int) Math.min(buffer.length, length - written);
        int filled = 0;
        try {
          while (filled < wanted) {
            int count = in.read(buffer, filled, wanted - filled);
            if (count < 0) {
              ended = true;
              break;
            }
            stop.check();
            receivedBytes.addAndGet(count);
            filled += count;
          }
        } catch (IOException e) {
          if (!stop.isStopped()) {
            writeRange(buffer, filled, stage, position + written, part, writing);
          }
          throw e;
        }
        writeRange(buffer, filled, stage, position + written, part, writing);
        written += filled;
      }
    }
    return written;
  }

  // Writes the first count bytes of buffer to the stage at position, as the part's next bytes when
  // there is a part, marking its attempt as writing locally meanwhile.
  private void writeRange(byte[] buffer, int count, DownloadStage stage, long position, Part part, long[] writing) throws LocalFailure {
    if (count == 0) {
      return;
    }
    long writingSince = System.nanoTime();
    AttemptAdmission.Attempt attempt = part != null ? part.attempt : null;
    if (attempt != null) {
      attempt.writing(true);
    }
    try {
      stage.writeAt(position, buffer, 0, count);
    } catch (IOException e) {
      throw new LocalFailure(e);
    } finally {
      if (attempt != null) {
        attempt.writing(false);
      }
      writing[0] += System.nanoTime() - writingSince;
    }
    if (part != null) {
      part.written += count;
    }
  }

  // Writes the contents to stage from one response, starting over after a failure that may be
  // temporary. A checkpoint first forgets any version it records, durably, before the stage is
  // emptied: the record claims nothing of a download in one request.
  private long fetchWhole(DownloadStage stage, OperationScope scope) throws IOException {
    if (checkpoint != null) {
      checkpoint.clearVersion();
    }
    report(progress());
    for (int retry = 0; ; retry++) {
      scope.check();
      Attempt attempt = whole(stage, scope, retry > 0);
      if (attempt.error == null) {
        synchronized (lock) {
          completedBytes = attempt.size;
          completedParts = 1;
        }
        totalBytes = attempt.size;
        report(progress());
        return attempt.size;
      }
      waitToRetry(attempt, retry, scope);
    }
  }

  // One request for the whole file. In a scope whose compatible downloads share a target, it is admitted
  // by that target as a part is, a retry ahead of new parts, and what it finds out teaches the target.
  private Attempt whole(DownloadStage stage, OperationScope scope, boolean retry) throws IOException {
    AttemptAdmission admission = sharedAdmission(settings());
    if (admission != null) {
      // The download's progress reports the target it shares.
      attempts = admission;
    }
    try (StorageRequest storage = storageRequest(copyBufferBytes(COPY_BUFFER_BYTES), admission, retry, scope)) {
      HttpGet request;
      try {
        request = new HttpGet(URI.create(storage.uri));
      } catch (IllegalArgumentException e) {
        throw new ApiErrorException.ApiConnectionException("The download URL of " + path + " is not a valid URL");
      }
      // The bytes as stored, so their length can be checked.
      request.setHeader("Accept-Encoding", "identity");
      Attempt outcome;
      long size = 0;
      try {
        // It may follow a redirect; its slot is for the storage host it asks first.
        CloseableHttpResponse response = storage.execute(request, true);
        HttpEntity entity = response.getEntity();
        int status = response.getStatusLine().getStatusCode();
        refuseWebApplication(response);
        if (status != 200) {
          outcome = failedStatus(response, status);
        } else {
          long expected = entity != null ? entity.getContentLength() : 0;
          size = copy(entity, stage, scope, storage);
          if (expected >= 0 && size != expected) {
            outcome = Attempt.failed(new ApiErrorException.ApiConnectionException("The download of " + path + " ended after " + size
                + " of its " + expected + " bytes"), true, -1, "a short response");
          } else if (file.getSize() != null && size != file.getSize()) {
            throw sizeMismatch(size);
          } else {
            outcome = Attempt.succeeded(size);
          }
        }
      } catch (LocalFailure e) {
        throw e.failure();
      } catch (IOException e) {
        outcome = connectionFailure(e, scope);
      }
      storage.complete(outcome, size);
      return outcome;
    }
  }

  // Copies the response body into the stage, from its start, through the request's copy buffer, and
  // makes it durable; the request is marked as writing locally meanwhile. The request stays abortable
  // while the body is read, so stopping the download ends a read that waits.
  private long copy(HttpEntity entity, DownloadStage stage, OperationScope scope, StorageRequest storage) throws IOException {
    try {
      stage.restart();
    } catch (IOException e) {
      throw new LocalFailure(e);
    }
    long size = 0;
    if (entity != null) {
      byte[] buffer = new byte[storage.bufferBytes];
      try (InputStream in = entity.getContent()) {
        int count;
        while ((count = in.read(buffer)) >= 0) {
          scope.check();
          storage.writing(true);
          try {
            stage.write(buffer, count);
          } catch (IOException e) {
            throw new LocalFailure(e);
          } finally {
            storage.writing(false);
          }
          receivedBytes.addAndGet(count);
          size += count;
        }
      }
    }
    storage.writing(true);
    try {
      stage.force();
    } catch (IOException e) {
      throw new LocalFailure(e);
    } finally {
      storage.writing(false);
    }
    return size;
  }

  // Waits before trying again after a failure, or throws it when it may not be retried.
  private void waitToRetry(Attempt attempt, int retry, OperationScope scope) {
    if (!attempt.retryable || retry >= maxRetries) {
      throw attempt.error;
    }
    if (attempt.retryAfterMillis > MAX_RETRY_AFTER_MILLIS) {
      log.info("Not retrying the download of {}: the storage server's Retry-After asks for longer than the SDK waits", path);
      throw attempt.error;
    }
    long delay = attempt.retryAfterMillis >= 0 ? attempt.retryAfterMillis : retryDelayMillis;
    retries.incrementAndGet();
    log.info("Retrying the download of {} (retry {} of {}) in {} ms after {}", path, retry + 1, maxRetries, delay, attempt.reason);
    scope.sleep(delay);
  }

  // A request for the URL's bytes from to to, with If-Match etag when it is not null. Executed so
  // that it follows no redirect (executeStorage), so that its answer comes from the resource it names.
  private HttpGet request(String uri, long from, long to, String etag) {
    HttpGet request;
    try {
      request = new HttpGet(URI.create(uri));
    } catch (IllegalArgumentException e) {
      throw new ApiErrorException.ApiConnectionException("The download URL of " + path + " is not a valid URL");
    }
    request.setHeader("Accept-Encoding", "identity");
    request.setHeader("Range", "bytes=" + from + "-" + to);
    if (etag != null) {
      request.setHeader("If-Match", etag);
    }
    return request;
  }

  private void refuseWebApplication(CloseableHttpResponse response) {
    if (response.getFirstHeader("X-Files-Frontend-App") != null) {
      throw new ApiErrorException.ApiConnectionException("The download URL of " + path
          + " answered with the Files.com web application instead of the file's contents");
    }
  }

  private Attempt failedStatus(CloseableHttpResponse response, int status) {
    EntityUtils.consumeQuietly(response.getEntity());
    boolean retryable = status == 408 || status == 429 || (status >= 500 && status != 501);
    long retryAfter = status == 429 || status == 503 ? ClientRuntime.retryAfterMillis(response.getFirstHeader("Retry-After"), Long.MAX_VALUE) : -1;
    return Attempt.failed(new ApiErrorException.ApiConnectionException("The storage server answered the download of " + path
        + " with HTTP status " + status), retryable, retryAfter, "HTTP status " + status, status);
  }

  private Attempt connectionFailure(IOException e, OperationScope scope) {
    if (scope.isStopped()) {
      throw scope.stopped(null);
    }
    log.debug("Downloading {} failed", path, e);
    String reason = ClientRuntime.describe(e);
    return Attempt.failed(new ApiErrorException.ApiConnectionException("Downloading " + path + " failed: " + reason),
        ClientRuntime.isRetryable(e), -1, reason);
  }

  // The file's contents have another size than Files.com described: downloading again cannot fix it.
  private ApiErrorException.ApiConnectionException sizeMismatch(long size) {
    return new ApiErrorException.ApiConnectionException("The download of " + path + " has " + size + " bytes, but Files.com described it with "
        + file.getSize());
  }

  private ApiErrorException.ApiConnectionException changed(String why) {
    return new ApiErrorException.ApiConnectionException("The file " + path + " changed while it was being downloaded: " + why
        + ", so the download was stopped and nothing was put in place");
  }

  // Gives the download a new URL when its URL is about to expire: Files.com's download URL for the
  // file again, which, once the download is bound, must name the same storage resource.
  private void renewIfExpiring(OperationScope scope) {
    if (!expiresSoon(url)) {
      return;
    }
    synchronized (renewLock) {
      if (!expiresSoon(url)) {
        return;
      }
      FilesResponse response = runtime.send(RequestMethods.GET, "/files/" + UrlUtils.encodeUrlPath(path), new HashMap<String, Object>(),
          ClientRuntime.retainedOptions(callOptions), scope, maxRetries);
      File renewed = runtime.readItem(response.getBody(), FILE);
      String uri = renewed.getDownloadUri();
      if (uri == null || uri.isEmpty()) {
        throw new ApiErrorException.ApiConnectionException("Files.com gave no new download URL for " + path);
      }
      Version bound = version;
      if (bound != null && !bound.resource.equals(resourceOf(uri))) {
        throw changed("Files.com gave a download URL for another storage resource");
      }
      url = uri;
    }
  }

  private static boolean expiresSoon(String uri) {
    Instant expires = UrlUtils.expiresAt(uri);
    return expires != null && !expires.isAfter(Instant.now().plus(RENEW_URLS_EXPIRING_WITHIN));
  }

  // The download's concurrency for where its parts come from, within its caps: the limiter's request
  // slots, maxConcurrency and the transport's connections to one host, when known.
  private ConcurrencySettings settingsFor(UploadDestination destination, String storageUri) {
    int cap = Math.min(limiter.getMaxConcurrentRequests(), options.getMaxConcurrency() != null ? options.getMaxConcurrency() : Integer.MAX_VALUE);
    int connections = UploadCoordinator.storageCapacity(runtime, storageUri);
    if (connections > 0) {
      cap = Math.min(cap, connections);
    }
    if (options.getConcurrencyMode() == ConcurrencyMode.STATIC) {
      int fixed = options.getMaxConcurrency() != null ? options.getMaxConcurrency() : DownloadOptions.DEFAULT_MAX_CONCURRENCY;
      return ConcurrencySettings.fixed(Math.max(1, Math.min(fixed, cap)), destination, UploadStartup.DEFAULT);
    }
    return ConcurrencySettings.automatic(ConcurrencyTuning.forDestination(destination), Math.max(1, cap), UploadStartup.DEFAULT);
  }

  // The download's concurrency for where its contents come from, for its URL now.
  private ConcurrencySettings settings() {
    return settingsFor(UploadGrant.isComplete(file.getDirectConnectionInfo()) ? UploadDestination.FILES_AGENT : UploadDestination.S3, url);
  }

  // The admission the compatible downloads of the scope share, made for the first of them; null without
  // a scope, for a static download, or once the scope has closed since the download started.
  private AttemptAdmission sharedAdmission(ConcurrencySettings settings) {
    TransferScope job = options.getScope();
    if (job == null || !settings.adaptive()) {
      return null;
    }
    return job.admissionFor(new TransferScope.Key(false, settings),
        () -> new AttemptAdmission(new ConcurrencyController(settings, null, false), System::nanoTime, true));
  }

  // A copy buffer of at most wanted bytes, within the limiter's buffer space: any size copies.
  private int copyBufferBytes(long wanted) {
    return (int) Math.min(wanted, limiter.getMaxBufferedBytes());
  }

  // What an attempt teaches its concurrency target: its outcome, the bytes it delivered, and its time
  // from sending its request to its end, less the time it spent writing locally.
  private static ConcurrencyController.Sample sample(Attempt outcome, long bytes, long nanos) {
    return new ConcurrencyController.Sample(outcome.error == null, outcome.status == 429 || outcome.status == 503, outcome.retryable,
        outcome.retryAfterMillis >= 0 ? TimeUnit.MILLISECONDS.toNanos(outcome.retryAfterMillis) : -1, bytes, Math.max(0, nanos), 0);
  }

  // Waits for what a storage request of the download that is not a bound part's attempt holds, in a
  // part's order: bufferBytes of buffer space (none for 0); its admission by the shared target, when
  // there is one, a retry ahead of new parts; and a request slot of the limiter. Then it renews the URL
  // if it is about to expire, and waits for a slot for the storage host from the transport, so that it
  // waits there rather than for a connection from the pool. An admitted attempt that a pause or a
  // lowered target overtook meanwhile gives its slots back and asks again.
  private StorageRequest storageRequest(int bufferBytes, AttemptAdmission admission, boolean retry, OperationScope scope) {
    FifoBudget.Grant buffer = bufferBytes > 0 ? limiter.bufferedBytes().acquire(bufferBytes, scope) : null;
    try {
      if (admission == null) {
        FifoBudget.Grant request = limiter.requests().acquire(1, scope);
        try {
          renewIfExpiring(scope);
          String uri = url;
          return new StorageRequest(uri, bufferBytes, buffer, null, request, connectionSlot(uri, scope), scope);
        } catch (RuntimeException | Error e) {
          request.close();
          throw e;
        }
      }
      while (true) {
        AttemptAdmission.Attempt admitted = admitAttempt(admission, retry, scope);
        String uri;
        try {
          renewIfExpiring(scope);
          uri = url;
          admitted.hold(connectionSlot(uri, scope));
        } catch (RuntimeException | Error e) {
          admitted.close();
          throw e;
        }
        if (admitted.tryStart()) {
          return new StorageRequest(uri, bufferBytes, buffer, admitted, null, null, scope);
        }
        retry = true;
      }
    } catch (RuntimeException | Error e) {
      if (buffer != null) {
        buffer.close();
      }
      throw e;
    }
  }

  // A storage request of the download that is not a bound part's attempt: the first part, the
  // confirmation of a resumed version, or the whole file in one request (storageRequest). It holds its
  // buffer space, its request slot and its slot for the storage host, the slots through its admission
  // when it was admitted, until its response is closed. Closing it closes the response, then gives them
  // all back, the admission's permit without a measurement; complete gives the admitting target the
  // attempt's outcome first. Used by one thread.
  private final class StorageRequest implements AutoCloseable {
    final String uri;
    final int bufferBytes;
    private final FifoBudget.Grant buffer;
    private final AttemptAdmission.Attempt admitted;
    private final FifoBudget.Grant request;
    private final StorageGate.Slot slot;
    private final OperationScope scope;
    private ClientRuntime.StorageExchange exchange;
    private long sending;
    private long writingSince;
    private long writingNanos;

    StorageRequest(String uri, int bufferBytes, FifoBudget.Grant buffer, AttemptAdmission.Attempt admitted, FifoBudget.Grant request,
        StorageGate.Slot slot, OperationScope scope) {
      this.uri = uri;
      this.bufferBytes = bufferBytes;
      this.buffer = buffer;
      this.admitted = admitted;
      this.request = request;
      this.slot = slot;
      this.scope = scope;
    }

    // Sends the request and returns its response, which closing this closes.
    CloseableHttpResponse execute(HttpGet get, boolean followRedirects) throws IOException {
      sending = System.nanoTime();
      exchange = runtime.executeStorage(get, scope, followRedirects);
      return exchange.response();
    }

    // The request starts (true) or stops (false) writing what it received to the stage.
    void writing(boolean now) {
      if (now) {
        writingSince = System.nanoTime();
      } else {
        writingNanos += System.nanoTime() - writingSince;
      }
      if (admitted != null) {
        admitted.writing(now);
      }
    }

    // Closes the response, then ends the request with its outcome, which teaches the target that
    // admitted it, if one did, and gives everything back.
    void complete(Attempt outcome, long bytes) {
      closeExchange();
      if (admitted != null) {
        for (ConcurrencyController.Decision decision : admitted.complete(sample(outcome, bytes, System.nanoTime() - sending - writingNanos),
            log.isDebugEnabled())) {
          log.debug("Download of {}: {}", path, decision);
        }
      }
      close();
    }

    private void closeExchange() {
      if (exchange != null) {
        exchange.close();
        exchange = null;
      }
    }

    @Override
    public void close() {
      try {
        closeExchange();
      } finally {
        if (admitted != null) {
          admitted.close();
        }
        if (slot != null) {
          slot.close();
        }
        if (request != null) {
          request.close();
        }
        if (buffer != null) {
          buffer.close();
        }
      }
    }
  }

  // A slot for the attempt from the transport, for the storage host; none for a URL that cannot be
  // read, which the attempt itself reports, or where the transport's connections are not counted.
  private StorageGate.Slot connectionSlot(String uri, OperationScope stop) {
    URI parsed;
    try {
      parsed = URI.create(uri);
    } catch (IllegalArgumentException e) {
      return null;
    }
    return runtime.transport().storageRequestSlot(parsed, stop);
  }

  // Records the download's first failure and stops everything else.
  private void fail(Throwable e, OperationScope stop) {
    synchronized (lock) {
      if (failure == null) {
        failure = e;
        log.debug("Stopping the download of {}", path, e);
      }
    }
    stop.fail();
  }

  // Waits until every part in flight has ended, reporting each part written meanwhile, and recording
  // it in the checkpoint when it is time to, when reporting to stage, which is null otherwise. Returns
  // whether this thread was interrupted while it waited; the download is then stopped.
  private boolean awaitParts(OperationScope stop, DownloadStage stage) throws IOException {
    boolean reporting = stage != null;
    boolean interrupted = false;
    while (true) {
      synchronized (lock) {
        while (inFlight > 0 && (!reporting || unreported == 0)) {
          try {
            lock.wait();
          } catch (InterruptedException e) {
            interrupted = true;
            stop.interrupted();
          }
        }
        if (inFlight == 0 && (!reporting || unreported == 0)) {
          return interrupted;
        }
      }
      progressed(stage);
    }
  }

  private static boolean awaitTermination(ExecutorService workers) {
    boolean interrupted = false;
    while (true) {
      try {
        if (workers.awaitTermination(1, TimeUnit.SECONDS)) {
          return interrupted;
        }
      } catch (InterruptedException e) {
        interrupted = true;
      }
    }
  }

  // Reports the parts written since the last report.
  private void reportCompleted() {
    synchronized (lock) {
      if (unreported == 0) {
        return;
      }
      unreported = 0;
    }
    report(progress());
  }

  private void report(TransferProgress progress) {
    TransferProgressListener listener = options.getProgressListener();
    if (listener != null) {
      listener.onProgress(progress);
    }
  }

  private TransferProgress progress() {
    long bytes;
    long parts;
    synchronized (lock) {
      bytes = completedBytes;
      parts = completedParts;
    }
    AttemptAdmission admission = attempts;
    return new TransferProgress(path, totalBytes, bytes, resumedBytes, receivedBytes.get(), parts, retries.get(),
        Duration.ofNanos(System.nanoTime() - started),
        admission == null ? null : admission.snapshot());
  }

  private static IOException rethrow(Throwable failure) {
    if (failure instanceof IOException) {
      return (IOException) failure;
    }
    if (failure instanceof RuntimeException) {
      throw (RuntimeException) failure;
    }
    if (failure instanceof Error) {
      throw (Error) failure;
    }
    throw new SdkException("The download failed", failure);
  }

  // The response's entity tag, as sent, or null when it has none, or more than one.
  private static String entityTag(CloseableHttpResponse response) {
    Header[] tags = response.getHeaders("ETag");
    return tags.length == 1 ? tags[0].getValue() : null;
  }

  // entity-tag = DQUOTE *etagc DQUOTE, etagc = %x21 / %x23-7E (RFC 9110), without the weak prefix.
  static boolean isStrongEntityTag(String value) {
    if (value == null || value.length() < 2 || value.charAt(0) != '"' || value.charAt(value.length() - 1) != '"') {
      return false;
    }
    for (int i = 1; i < value.length() - 1; i++) {
      char c = value.charAt(i);
      if (c != 0x21 && (c < 0x23 || c > 0x7E)) {
        return false;
      }
    }
    return true;
  }

  // The storage resource a URL names: its text before the query and fragment, which hold the
  // signature and its expiry and change with every URL Files.com gives for the same object.
  static String resourceOf(String uri) {
    int end = uri.length();
    int query = uri.indexOf('?');
    int fragment = uri.indexOf('#');
    if (query >= 0) {
      end = query;
    }
    if (fragment >= 0 && fragment < end) {
      end = fragment;
    }
    return uri.substring(0, end);
  }

  // The version of the file the download is bound to, as storage answered for it: its strong entity
  // tag, exactly as sent, which every later request carries in If-Match; its size, from the first
  // answer's Content-Range; and the storage resource that answer came from. It grants nothing.
  private static final class Version {
    final String etag;
    final long size;
    final String resource;

    Version(String etag, long size, String resource) {
      this.etag = etag;
      this.size = size;
      this.resource = resource;
    }

    // Compared strongly: a weak tag never matches.
    boolean matches(String other) {
      return !other.startsWith("W/") && other.equals(etag);
    }
  }

  // A Content-Range of bytes start-end/total.
  private static final class ContentRange {
    final long start;
    final long end;
    final long total;

    private ContentRange(long start, long end, long total) {
      this.start = start;
      this.end = end;
      this.total = total;
    }

    static ContentRange of(CloseableHttpResponse response) {
      Header[] headers = response.getHeaders("Content-Range");
      if (headers.length != 1) {
        return null;
      }
      Matcher matcher = CONTENT_RANGE.matcher(headers[0].getValue().trim());
      if (!matcher.matches()) {
        return null;
      }
      try {
        long start = Long.parseLong(matcher.group(1));
        long end = Long.parseLong(matcher.group(2));
        long total = Long.parseLong(matcher.group(3));
        return start <= end && end < total ? new ContentRange(start, end, total) : null;
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

  // A part of a bound download: its bytes from start, and how many of them have been written.
  private static final class Part {
    final long start;
    final long length;
    // Written by the one thread fetching the part at a time.
    long written;
    FifoBudget.Grant buffer;
    FifoBudget.Grant inProgress;
    AttemptAdmission.Attempt attempt;

    Part(long start, long length) {
      this.start = start;
      this.length = length;
    }

    void release() {
      if (attempt != null) {
        attempt.close();
      }
      if (inProgress != null) {
        inProgress.close();
      }
      if (buffer != null) {
        buffer.close();
      }
    }
  }

  // A bound part answered 200: the server ignores Range, so the parts cannot be fetched.
  private static final class Unbound extends RuntimeException {
    private static final long serialVersionUID = 1L;

    Unbound() {
      super("The storage server ignored a range request", null, false, false);
    }
  }

  // A failure writing the download's own file: downloading again cannot fix it.
  private static final class LocalFailure extends IOException {
    private static final long serialVersionUID = 1L;

    LocalFailure(IOException cause) {
      super(cause.getMessage(), cause);
    }

    IOException failure() {
      return (IOException) getCause();
    }
  }

  private static final class Attempt {
    final long size;
    final SdkException error;
    final boolean retryable;
    final long retryAfterMillis;
    final String reason;
    final int status;

    private Attempt(long size, SdkException error, boolean retryable, long retryAfterMillis, String reason, int status) {
      this.size = size;
      this.error = error;
      this.retryable = retryable;
      this.retryAfterMillis = retryAfterMillis;
      this.reason = reason;
      this.status = status;
    }

    static Attempt succeeded(long size) {
      return new Attempt(size, null, false, -1, null, 0);
    }

    static Attempt failed(SdkException error, boolean retryable, long retryAfterMillis, String reason) {
      return failed(error, retryable, retryAfterMillis, reason, 0);
    }

    static Attempt failed(SdkException error, boolean retryable, long retryAfterMillis, String reason, int status) {
      return new Attempt(0, error, retryable, retryAfterMillis, reason, status);
    }
  }
}
