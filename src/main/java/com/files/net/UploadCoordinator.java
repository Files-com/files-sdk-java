package com.files.net;

import com.files.exceptions.ApiErrorException;
import com.files.exceptions.OperationCancelledException;
import com.files.exceptions.SdkException;
import com.files.exceptions.UploadCompletionUnknownException;
import com.files.models.File;
import com.files.models.FileUploadPart;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongConsumer;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Runs one upload. The calling thread plans each part (negotiating it with begin_upload when
// needed), waits for buffer space from the limiter, prepares the part (reading it, for a stream, or
// for a file's part that will be sent alone), waits for a place among the parts in progress and for
// its first attempt's admission and a request slot from the limiter, and hands it to one of the
// upload's own threads, which reads any other part of a file as its first attempt starts, so that a
// file's parts are read side by side, and sends it, retrying when allowed. Waiting on the calling
// thread keeps later parts behind earlier ones.
//
// How many attempts run at once is the concurrency controller's target (ConcurrencyController),
// which an automatic upload adapts from what its attempts measure, starting as UploadStartup says,
// and a static one keeps. Each attempt is admitted by it (AttemptAdmission), then takes a request
// slot from the limiter and a slot for its storage host from the client's transport, so the upload
// never sends more requests to one host than the transport's pool has connections for, and is sent
// only if the target and any pause after throttling still allow it then. An attempt holds all of
// these only while it is sent, and reports what it measured when it ends: a part waiting to be sent
// again holds none of them. A part Files.com says must be sent alone takes every place among the
// parts in progress, so no later part starts before it has been accepted. The parts in progress,
// those being sent and those waiting to be sent again, are at most twice the most the upload may
// send at once, each on a thread of its own. Progress is reported on the calling thread.
//
// The first failure, the caller's cancellation or its time limit stops everything: requests in
// flight are aborted and every part thread has ended, and its resources are released, before run
// returns or throws. Only then, when every part was accepted, is the upload completed, with a
// single final request.
//
// With a checkpoint (UploadCheckpoint), the upload records as it goes what a later run needs to
// continue it, in the order that record describes: its reference once begun, each part as planned,
// durably before the part is first sent, each part as accepted, and, durably, that the final request
// is about to be sent, before it is. A run that continues a recorded upload sends no part it records
// as accepted, and reports the upload as of unknown outcome, sending nothing, when its record says
// the final request was about to be sent, or when Files.com no longer recognizes the upload.
final class UploadCoordinator {
  private static final Logger log = LoggerFactory.getLogger(UploadCoordinator.class);

  // A part whose URL expires sooner than this gets a new one before it is sent.
  private static final Duration RENEW_URLS_EXPIRING_WITHIN = Duration.ofSeconds(30);

  // The longest a storage server's Retry-After can delay a part's next attempt. A longer wait is not
  // cut short: the part is not sent again.
  private static final long MAX_RETRY_AFTER_MILLIS = TimeUnit.NANOSECONDS.toMillis(ConcurrencyController.MAX_PAUSE_NANOS);

  private static final AtomicInteger THREADS = new AtomicInteger();

  private final ClientRuntime runtime;
  private final String path;
  private final UploadSource source;
  private final UploadOptions options;
  private final Instant modifiedTime;
  private final FileUploadPart firstPart;
  // The upload's checkpoint, or null.
  private final UploadCheckpoint checkpoint;
  private final TransferLimiter limiter;
  private final HashMap<String, Object> callOptions;
  private final int maxRetries;
  private final long retryDelayMillis;

  private final Object lock = new Object();
  private final TreeMap<Long, UploadedPart> accepted = new TreeMap<Long, UploadedPart>();
  private final List<UploadedPart> unreported = new ArrayList<UploadedPart>();
  private final AtomicLong sentBytes = new AtomicLong();
  private final AtomicInteger retries = new AtomicInteger();
  private int inFlight;
  private Throwable failure;
  private long reportedBytes;
  private long reportedParts;
  // What an earlier run recorded and this one continues, or null; the bytes of the parts it had
  // accepted.
  private UploadCheckpoint.Saved resumed;
  private long resumedBytes;
  private long started;
  private boolean parallel;
  // The admission of the upload's attempts, which its concurrency controller decides; set when the
  // first part is planned.
  private volatile AttemptAdmission attempts;
  // Whether the upload shares its admission with the compatible transfers of its scope.
  private boolean shared;
  // The upload's part size feedback, when it chooses its part sizes; in a shared scope, attached to
  // the shared controller while the upload runs.
  private PartSizeFeedback sizeFeedback;
  // The parts handed to the upload's threads and not yet ended.
  private FifoBudget inProgress;
  // The upload's own threads, which send its parts; null until the first part is sent.
  private ExecutorService workers;

  UploadCoordinator(ClientRuntime runtime, String path, UploadSource source, UploadOptions options, Instant modifiedTime,
      FileUploadPart firstPart) {
    this(runtime, path, source, options, modifiedTime, firstPart, null);
  }

  UploadCoordinator(ClientRuntime runtime, String path, UploadSource source, UploadOptions options, Instant modifiedTime,
      FileUploadPart firstPart, UploadCheckpoint checkpoint) {
    this.runtime = runtime;
    this.path = path;
    this.source = source;
    this.options = options;
    this.modifiedTime = modifiedTime;
    this.firstPart = firstPart;
    this.checkpoint = checkpoint;
    this.limiter = options.getLimiter() != null ? options.getLimiter()
        : options.getScope() != null ? options.getScope().getLimiter()
        : new TransferLimiter(ownRequests(options), TransferLimiter.DEFAULT_MAX_BUFFERED_BYTES);
    this.callOptions = options.callOptions();
    this.maxRetries = runtime.maxRetries(callOptions);
    this.retryDelayMillis = runtime.settings().retryDelay().toMillis();
  }

  // The request slots of an upload's own limiter: as many as it may send at once.
  private static int ownRequests(UploadOptions options) {
    if (options.getMaxConcurrency() != null) {
      return options.getMaxConcurrency();
    }
    return options.getConcurrencyMode() == ConcurrencyMode.STATIC ? UploadOptions.DEFAULT_MAX_CONCURRENCY : ConcurrencyTuning.MAX_PARTS;
  }

  // The upload's call options: its request options, cancellation token and time limit.
  HashMap<String, Object> callOptions() {
    return callOptions;
  }

  // Runs the upload within scope, which the caller opened with callOptions() and closes once the
  // source is closed too.
  UploadResult run(OperationScope scope) throws IOException {
    if (options.getScope() != null && options.getScope().isClosed()) {
      throw new IllegalStateException("The transfer scope of the upload to " + path + " is closed");
    }
    started = System.nanoTime();
    // An automatic upload with the default startup in a scope waits for its place among the scope's
    // uploads in progress before it asks Files.com anything, and keeps it until it has ended: planned,
    // confirmed, its parts settled and its completion answered, or failed or cancelled. Its elapsed
    // time includes that wait, as it does its waits for the limiter.
    UploadFileAdmission.Permit place = admitsFiles() ? options.getScope().uploadFileAdmission().admit(scope) : null;
    try {
      return runAdmitted(scope);
    } finally {
      if (place != null) {
        place.close();
      }
    }
  }

  // Whether the upload waits for its place among its scope's uploads in progress (UploadFileAdmission):
  // aggressive and static uploads, and those of no scope, do not.
  private boolean admitsFiles() {
    return options.getScope() != null && options.getConcurrencyMode() == ConcurrencyMode.AUTOMATIC
        && options.getStartup() == UploadStartup.DEFAULT;
  }

  private UploadResult runAdmitted(OperationScope scope) throws IOException {
    UploadCheckpoint.Saved saved = checkpoint != null ? checkpoint.saved() : null;
    if (saved != null && saved.finalizing) {
      throw completionUnknown(saved, "The checkpoint of the upload to " + path + " records that the request completing it was about to be"
          + " sent, so it is not known whether the file was completed. Check it, then discard the checkpoint before uploading the file"
          + " again", null);
    }
    UploadSession session = new UploadSession(runtime, path, ClientRuntime.retainedOptions(callOptions), options.getMkdirParents(),
        maxRetries);
    PartPlanner planner = new PartPlanner(session, source.length(), this::partsPerRequest, options.getPartSizing(),
        options.getMaxStreamLength(), source.holdsWholeParts() ? limiter.getMaxBufferedBytes() : -1);
    if (firstPart != null) {
      session.resume(firstPart.getRef());
      planner.seed(firstPart);
    }
    if (saved != null && saved.ref != null) {
      // Continues the upload recorded: its accepted parts count as done, and are not sent again.
      resumed = saved;
      session.resume(saved.ref);
      planner.resume(saved);
      for (UploadedPart part : saved.acceptedParts()) {
        accepted.put(part.getNumber(), part);
        resumedBytes += part.getLength();
      }
      reportedBytes = resumedBytes;
      reportedParts = accepted.size();
    }
    boolean interrupted = false;
    OperationScope stop = scope.child();
    try {
      PartPlanner.Planned planned = planner.next(stop);
      if (checkpoint != null && resumed == null) {
        checkpoint.begun(session.ref(), planner.sizesChosen(), planner.uploadIdentity());
      }
      if (planned == null) {
        // Every part was accepted by an earlier run: the upload must still be the one recorded.
        planner.confirmResumed(stop);
      } else {
        sendParts(planned, planner, stop);
      }
    } catch (OperationCancelledException | OperationScope.Stopped e) {
      // Reported below: the cancellation, or the failure that stopped the upload.
      stop.fail();
    } catch (Throwable e) {
      fail(e, stop);
    } finally {
      interrupted = awaitParts(stop, false);
      if (workers != null) {
        workers.shutdown();
        interrupted |= awaitTermination(workers);
      }
      if (shared && sizeFeedback != null) {
        attempts.detachSizing(sizeFeedback);
      }
      stop.close();
    }

    Throwable failed;
    synchronized (lock) {
      failed = failure;
    }
    if (failed != null) {
      if (interrupted) {
        Thread.currentThread().interrupt();
      }
      if (resumed != null && failed instanceof ApiErrorException.FileUploadNotFoundException) {
        throw forgotten(failed);
      }
      throw rethrow(failed);
    }
    if (interrupted) {
      scope.interrupted();
    }
    // No new request to complete the upload once it has been cancelled.
    scope.check();
    List<UploadedPart> parts = acceptedParts(planner);
    source.checkUnchanged();
    TransferProgress finalProgress = progress();
    File file = finish(session, planner, parts, scope);
    return new UploadResult(path, planner.plannedBytes(), file, parts, parallel && planner.everyPartParallel(), finalProgress,
        planner.sizeSource());
  }

  // Sends every part, from the first planned on, as the upload's concurrency allows.
  private void sendParts(PartPlanner.Planned first, PartPlanner planner, OperationScope stop) throws IOException {
    PartPlanner.Planned planned = first;
    if (planner.fallback() != null) {
      log.debug("The upload to {} sends the parts Files.com plans: the SDK's own sizes cannot hold it ({})", path, planner.fallback());
    }
    parallel = planned.target.parallelParts;
    ConcurrencySettings settings = settingsFor(planner.destination(), planned.target.uri);
    TransferScope job = options.getScope();
    // The compatible transfers of the scope learn one target together, unless the scope closed
    // since the upload started. The file's sizes are judged by its own parts, one file at a time,
    // and it is held to one part at a time, when Files.com requires it, by its own parts in
    // progress, without slowing the others.
    AttemptAdmission sharedAdmission = job != null && settings.adaptive() ? job.admissionFor(new TransferScope.Key(true, settings),
        () -> new AttemptAdmission(new ConcurrencyController(settings, null, settings.warmStarts()), System::nanoTime, true)) : null;
    shared = sharedAdmission != null;
    sizeFeedback = planner.sizeSource() == PartSizeSource.AUTOMATIC ? new PartSizeFeedback(planner.sizes()) : null;
    if (shared) {
      attempts = sharedAdmission;
      if (sizeFeedback != null) {
        attempts.attachSizing(sizeFeedback);
      }
    } else {
      attempts = new AttemptAdmission(new ConcurrencyController(settings, sizeFeedback, settings.warmStarts()), System::nanoTime);
      if (!parallel) {
        attempts.useSerial();
      }
    }
    inProgress = new FifoBudget(2L * settings.max);
    log.debug("The upload to {} goes to {}: {} concurrency, starting at {} and at most {} parts at once", path, settings.destination,
        settings.mode, attempts.target(), settings.max);
    report(progress());
    while (planned != null) {
      reportAccepted();
      stop.check();
      Part part = prepare(planned, planner, stop);
      if (checkpoint != null && !planned.recorded) {
        part.durableAt = checkpoint.planned(planned.number, planned.offset, part.body.length());
      }
      dispatch(part, planner, stop);
      planned = planner.next(stop);
    }
    awaitParts(stop, true);
  }

  // The upload's own threads for its parts, started when its first part goes to one.
  private ExecutorService workers() {
    if (workers == null) {
      workers = Executors.newCachedThreadPool(runnable -> {
        Thread thread = new Thread(runnable, "files-sdk-upload-" + THREADS.incrementAndGet());
        thread.setDaemon(true);
        return thread;
      });
    }
    return workers;
  }

  // Completes the upload with one request. With a checkpoint, that the request is about to be sent is
  // recorded durably first, or the request is not sent. Once Files.com completes the upload, or refuses
  // it, the record is deleted. When the request did not reach Files.com, or Files.com refused the
  // request, not the upload, by throttling it or refusing its credentials, the record says the request
  // was not sent, so a later run may send it. An outcome that is not known keeps the record as it is,
  // so no later run sends the request again.
  private File finish(UploadSession session, PartPlanner planner, List<UploadedPart> parts, OperationScope scope) {
    if (checkpoint == null) {
      return session.finish(planner.plannedBytes(), parts, modifiedTime, scope);
    }
    checkpoint.finalizing();
    File file;
    try {
      file = session.finish(planner.plannedBytes(), parts, modifiedTime, scope);
    } catch (UploadCompletionUnknownException e) {
      throw e;
    } catch (ApiErrorException.FileUploadNotFoundException e) {
      if (resumed != null) {
        throw forgotten(e);
      }
      checkpoint.delete();
      throw e;
    } catch (ApiErrorException.RateLimitedException | ApiErrorException.NotAuthenticatedException
        | ApiErrorException.NotAuthorizedException e) {
      // Not completed, and the upload is still there to complete later.
      checkpoint.finalNotSent();
      throw e;
    } catch (ApiErrorException e) {
      // Refused, as session.finish throws only Files.com's 4xx answers: not completed, and not to be
      // completed as it is.
      checkpoint.delete();
      throw e;
    } catch (SdkException | IllegalStateException e) {
      // The request did not reach Files.com, such as a connection that could not be made.
      checkpoint.finalNotSent();
      throw e;
    }
    checkpoint.delete();
    return file;
  }

  // Files.com no longer recognizes the upload a checkpoint records: it may have been completed, or it
  // expired or was cancelled. The record is kept.
  private UploadCompletionUnknownException forgotten(Throwable cause) {
    return completionUnknown(resumed, "Files.com no longer recognizes the upload to " + path + " that its checkpoint records: it may have been"
        + " completed, or it expired or was cancelled, so it is not known whether the file was completed. Check it, then discard the"
        + " checkpoint before uploading the file again", cause);
  }

  private UploadCompletionUnknownException completionUnknown(UploadCheckpoint.Saved saved, String message, Throwable cause) {
    return new UploadCompletionUnknownException(path, source.length(), Collections.unmodifiableList(saved.acceptedParts()), message, cause);
  }

  // The upload's concurrency for its destination: its options' mode, startup and cap, within the
  // limiter's request slots and the connections the transport's pool can lease to the storage host of
  // storageUri, when known.
  private ConcurrencySettings settingsFor(UploadDestination destination, String storageUri) {
    int cap = Math.min(limiter.getMaxConcurrentRequests(), options.getMaxConcurrency() != null ? options.getMaxConcurrency() : Integer.MAX_VALUE);
    int connections = storageCapacity(runtime, storageUri);
    if (connections > 0) {
      cap = Math.min(cap, connections);
    }
    if (options.getConcurrencyMode() == ConcurrencyMode.STATIC) {
      int fixed = options.getMaxConcurrency() != null ? options.getMaxConcurrency() : UploadOptions.DEFAULT_MAX_CONCURRENCY;
      return ConcurrencySettings.fixed(Math.max(1, Math.min(fixed, cap)), destination, options.getStartup());
    }
    return ConcurrencySettings.automatic(ConcurrencyTuning.forDestination(destination), Math.max(1, cap), options.getStartup());
  }

  // How many parts to negotiate per begin_upload request: the current target, or one before there is
  // one.
  private int partsPerRequest() {
    AttemptAdmission admission = attempts;
    return admission == null ? 1 : admission.target();
  }

  // Reserves the part's buffer space, prepares its bytes and fixes its length. A part that will be
  // sent alone is also read now, while the part before it is still being sent; any other part of a
  // file is read by its own thread (send).
  private Part prepare(PartPlanner.Planned planned, PartPlanner planner, OperationScope stop) throws IOException {
    long bufferBytes = source.bufferBytes(planned.maxLength);
    if (bufferBytes > limiter.getMaxBufferedBytes()) {
      throw new IllegalStateException("Part " + planned.number + " of the upload to " + path + " is " + planned.maxLength
          + " bytes, more than the " + limiter.getMaxBufferedBytes() + " bytes its transfer limiter may buffer. Allow more with a"
          + " TransferLimiter, or upload from a file, which does not hold whole parts in memory.");
    }
    FifoBudget.Grant buffer = limiter.bufferedBytes().acquire(bufferBytes, stop);
    UploadSource.PartBody body = null;
    try {
      body = source.prepare(planned.offset, planned.maxLength, stop);
      if (!parallel || !planned.target.parallelParts) {
        body.read(stop);
      }
    } catch (IOException | RuntimeException | Error e) {
      if (body != null) {
        body.release();
      }
      buffer.close();
      throw e;
    }
    if (source.length() < 0 && planned.offset + body.length() > options.getMaxStreamLength()) {
      body.release();
      buffer.close();
      throw new IllegalStateException("The stream uploaded to " + path + " holds more than the " + options.getMaxStreamLength()
          + " bytes the upload allows it (UploadOptions.Builder.maxStreamLength), so the upload was not completed");
    }
    planner.commit(planned, body.length(), source.ended());
    boolean last = source.length() >= 0 ? planned.offset + body.length() == source.length() : source.ended();
    return new Part(planned, body, buffer, last);
  }

  // Waits for room for another part in progress, then for its first attempt's admission and a request
  // slot, and starts sending it on a thread of its own. A part the server says must be sent alone
  // takes every place among the parts in progress, which waits for the parts in progress to end and
  // holds back later ones; once a part is sent alone, so is the rest of the upload. A file's first
  // part that is also its last, the whole file, is sent by the upload's caller itself, which has
  // nothing else to do meanwhile; it is admitted, held and sent as on a thread of the upload.
  private void dispatch(Part part, PartPlanner planner, OperationScope stop) {
    part.onCaller = part.last && part.planned.offset == 0 && source instanceof UploadSource.FileSource;
    try {
      if (part.target.expiresWithin(RENEW_URLS_EXPIRING_WITHIN)) {
        part.target = planner.renew(part.planned, part.body.length(), part.last, stop);
      }
      checkContentLength(part);
      if (parallel && !part.target.parallelParts) {
        parallel = false;
        if (!shared) {
          attempts.useSerial();
        }
      }
      part.alone = !parallel;
      part.inProgress = inProgress.acquire(part.alone ? inProgress.capacity() : 1, stop);
      part.attempt = admitAttempt(false, stop);
      synchronized (lock) {
        inFlight++;
      }
      if (!part.onCaller) {
        try {
          workers().execute(() -> stop.runPart(() -> send(part, planner, stop)));
        } catch (RuntimeException e) {
          synchronized (lock) {
            inFlight--;
          }
          throw e;
        }
        return;
      }
    } catch (RuntimeException | Error e) {
      part.release();
      throw e;
    }
    // send settles the part and records any failure itself. An interruption it did not see, such as
    // one while the storage server answered, still stops the upload before it is completed.
    send(part, planner, stop);
    interruptedOnCaller(part, stop);
  }

  // Whether the upload's caller, sending the part itself (dispatch), has been interrupted. If so the
  // upload stops as when its caller is interrupted while it waits for its parts, whatever failure the
  // interruption caused meanwhile, which the scope's watch of the caller may not have seen yet.
  private static boolean interruptedOnCaller(Part part, OperationScope stop) {
    if (part.onCaller && Thread.currentThread().isInterrupted()) {
      stop.interrupted();
      return true;
    }
    return false;
  }

  // Waits for an attempt's admission, a retry ahead of new parts, then for a request slot from the
  // limiter, which the attempt holds. The attempt is not started.
  private AttemptAdmission.Attempt admitAttempt(boolean retry, OperationScope stop) {
    AttemptAdmission.Attempt attempt = attempts.admit(retry, stop);
    try {
      attempt.hold(limiter.requests().acquire(1, stop));
    } catch (RuntimeException | Error e) {
      attempt.close();
      throw e;
    }
    return attempt;
  }

  // The connections the transport's pool can lease to the storage host of uri at once, or 0 when that
  // cannot be known, as for a URL that cannot be read.
  static int storageCapacity(ClientRuntime runtime, String uri) {
    try {
      return runtime.transport().storageCapacity(URI.create(uri));
    } catch (IllegalArgumentException | NullPointerException e) {
      return 0;
    }
  }

  // A slot for the attempt from the transport, for the part's storage host; none for a URL that
  // cannot be read, which the attempt itself reports.
  private AutoCloseable connectionSlot(Part part, OperationScope stop) {
    URI uri;
    try {
      uri = URI.create(part.target.uri);
    } catch (IllegalArgumentException e) {
      return null;
    }
    return runtime.transport().storageRequestSlot(uri, stop);
  }

  private void checkContentLength(Part part) {
    String required = part.target.contentLength;
    if (required != null && !required.equals(Long.toString(part.body.length()))) {
      throw new ApiErrorException.ApiConnectionException("Files.com asked for part " + part.planned.number + " of the upload to " + path
          + " to be sent with Content-Length " + required + ", but the part is " + part.body.length() + " bytes");
    }
  }

  // Sends the part until the storage server accepts it, on one of the upload's threads. A failed
  // attempt is sent again, with the same bytes and part number, only when the failure may be
  // temporary, the server allows retrying parts (retry_parts) and the client's retry limit has not
  // been reached. An expired URL is renewed first, and the renewal must still allow retrying parts.
  // Reading the source is never retried. A part not read yet is read once its first attempt has
  // started (readForFirstAttempt), so that its read counts as time the attempt is in flight, though
  // not as time its request took.
  private void send(Part part, PartPlanner planner, OperationScope stop) {
    try {
      if (checkpoint != null) {
        // A part that may be sent is never planned again with other bytes.
        checkpoint.awaitDurable(part.durableAt);
      }
      int attempt = 0;
      // The last attempt's failure, which the next attempt would retry.
      SdkException failed = null;
      while (true) {
        if (part.attempt == null) {
          part.attempt = admitAttempt(true, stop);
        }
        if (attempt > 0 && part.target.expiresWithin(RENEW_URLS_EXPIRING_WITHIN)) {
          renewForRetry(part, planner, stop, failed);
        }
        part.attempt.hold(connectionSlot(part, stop));
        if (!part.attempt.tryStart()) {
          // A pause or a lowered target overtook the attempt while it waited: it asks again.
          part.attempt = null;
          continue;
        }
        if (attempt == 0) {
          readForFirstAttempt(part, planner, stop);
        }
        attempt++;
        Outcome outcome = attempt(part, stop);
        List<ConcurrencyController.Decision> decisions = part.attempt.complete(sample(part, outcome), log.isDebugEnabled());
        part.attempt = null;
        for (ConcurrencyController.Decision decision : decisions) {
          log.debug("Upload to {}: {}", path, decision);
        }
        if (outcome.error == null) {
          if (checkpoint != null) {
            checkpoint.accepted(part.planned.number, outcome.etag);
          }
          synchronized (lock) {
            UploadedPart uploaded = new UploadedPart(part.planned.number, part.planned.offset, part.body.length(), outcome.etag, attempt);
            accepted.put(uploaded.getNumber(), uploaded);
            unreported.add(uploaded);
            lock.notifyAll();
          }
          return;
        }
        failed = outcome.error;
        boolean expired = outcome.status == 403 && part.target.expiresWithin(Duration.ZERO);
        if (!(outcome.retryable || expired) || !part.target.retryParts || attempt > maxRetries) {
          throw outcome.error;
        }
        if (outcome.retryAfterMillis > MAX_RETRY_AFTER_MILLIS) {
          log.info("Not retrying part {} of the upload to {}: the storage server's Retry-After asks for longer than the SDK waits",
              part.planned.number, path);
          throw outcome.error;
        }
        if (expired) {
          renewForRetry(part, planner, stop, failed);
        }
        long delay = outcome.retryAfterMillis >= 0 ? outcome.retryAfterMillis : retryDelayMillis;
        retries.incrementAndGet();
        log.info("Retrying part {} of the upload to {} (retry {} of {}) in {} ms after {}", part.planned.number, path, attempt,
            maxRetries, delay, outcome.reason);
        stop.sleep(delay);
      }
    } catch (OperationCancelledException | OperationScope.Stopped e) {
      // The upload was cancelled, or stopped by a failure elsewhere, which it reports.
    } catch (Throwable e) {
      // Such as the checkpoint's I/O, when the caller sending the part itself is interrupted.
      if (e instanceof Error || !interruptedOnCaller(part, stop)) {
        fail(e, stop);
      }
    } finally {
      part.release();
      synchronized (lock) {
        inFlight--;
        lock.notifyAll();
      }
    }
  }

  // Reads a part not read yet, on its thread, as its first attempt starts. Dispatch renewed the part's
  // URL if it was about to expire then. If reading the part is what brings it within that time, or a
  // URL already within it, such as one valid only briefly, has expired meanwhile, it is renewed now,
  // before the part is first sent, and checked as dispatch checks it. As there, the renewal need not
  // allow retrying parts, unless an earlier run may have sent the part (PartPlanner.renew).
  private void readForFirstAttempt(Part part, PartPlanner planner, OperationScope stop) throws IOException {
    boolean fresh = !part.target.expiresWithin(RENEW_URLS_EXPIRING_WITHIN);
    part.body.read(stop);
    if (part.target.expiresWithin(fresh ? RENEW_URLS_EXPIRING_WITHIN : Duration.ZERO)) {
      part.target = planner.renew(part.planned, part.body.length(), part.last, stop);
      checkContentLength(part);
      checkStillParallel(part);
    }
  }

  // Renews the URL of a part that failed, on its thread, before its next attempt. The renewal is
  // Files.com's answer for that attempt: if it no longer allows retrying parts (retry_parts), the part
  // is not sent again, and its failure stands.
  private void renewForRetry(Part part, PartPlanner planner, OperationScope stop, SdkException failed) {
    part.target = planner.renew(part.planned, part.body.length(), part.last, stop);
    if (!part.target.retryParts) {
      throw failed;
    }
    checkStillParallel(part);
  }

  // A part's URL renewed on its thread may withdraw permission to send parts in parallel. Unless the
  // part is being sent alone, others are already being sent with it, so the instruction can no longer
  // be followed and the upload stops instead.
  private void checkStillParallel(Part part) {
    if (!part.target.parallelParts && !part.alone) {
      throw new ApiErrorException.ApiConnectionException("Renewing the URL of part " + part.planned.number + " of the upload to " + path
          + ", Files.com withdrew permission to send parts in parallel after the part was already being sent alongside others,"
          + " so the upload was stopped");
    }
  }

  // What the attempt measured, for the concurrency controller.
  private ConcurrencyController.Sample sample(Part part, Outcome outcome) {
    boolean throttled = outcome.status == 429 || outcome.status == 503;
    long retryAfter = outcome.retryAfterMillis >= 0 ? TimeUnit.MILLISECONDS.toNanos(outcome.retryAfterMillis) : -1;
    return new ConcurrencyController.Sample(outcome.error == null, throttled, outcome.retryable, retryAfter, part.body.length(),
        outcome.durationNanos, part.planned.sizeEpoch, sizeFeedback);
  }

  // One attempt to send the part: its ETag, or its failure and whether it may be retried, and how long
  // it took from sending the request to receiving the answer's headers.
  private Outcome attempt(Part part, OperationScope stop) throws IOException {
    StorageRequest request;
    try {
      request = new StorageRequest(part.target.method, URI.create(part.target.uri));
    } catch (IllegalArgumentException e) {
      throw new ApiErrorException.ApiConnectionException("The storage URL of part " + part.planned.number + " of the upload to " + path
          + " is not a valid URL, so it cannot be sent as given");
    }
    for (Map.Entry<String, String> header : part.target.headers) {
      request.addHeader(header.getKey(), header.getValue());
    }
    request.setEntity(new PartEntity(part.body, sentBytes::addAndGet));
    long sending = System.nanoTime();
    try (ClientRuntime.StorageExchange exchange = runtime.executeStorage(request, stop)) {
      long duration = System.nanoTime() - sending;
      CloseableHttpResponse response = exchange.response();
      int status = response.getStatusLine().getStatusCode();
      Header etag = response.getFirstHeader("ETag");
      // Reading the reply's body lets the connection be reused; stopping the upload aborts the
      // read, as the exchange keeps the request abortable until it is closed.
      EntityUtils.consumeQuietly(response.getEntity());
      stop.check();
      if (status >= 200 && status < 300) {
        return Outcome.accepted(etag != null ? etag.getValue() : null, duration);
      }
      boolean retryable = status == 408 || status == 429 || (status >= 500 && status != 501);
      long retryAfter = status == 429 || status == 503
          ? ClientRuntime.retryAfterMillis(response.getFirstHeader("Retry-After"), Long.MAX_VALUE) : -1;
      return Outcome.failed(new ApiErrorException.ApiConnectionException("The storage server answered part " + part.planned.number
          + " of the upload to " + path + " with HTTP status " + status), retryable, status, retryAfter, "HTTP status " + status, duration);
    } catch (UploadSource.SourceFailure e) {
      interruptedOnCaller(part, stop);
      if (stop.isStopped()) {
        throw stop.stopped(null);
      }
      throw e.failure();
    } catch (IOException e) {
      // Before the failure is judged retryable, as reading the file or waiting for a connection ends
      // with one when the caller sending the part itself is interrupted.
      interruptedOnCaller(part, stop);
      if (stop.isStopped()) {
        throw stop.stopped(null);
      }
      String reason = ClientRuntime.describe(e);
      return Outcome.failed(new ApiErrorException.ApiConnectionException("Sending part " + part.planned.number + " of the upload to " + path
          + " failed: " + reason), ClientRuntime.isRetryable(e), 0, -1, reason, System.nanoTime() - sending);
    }
  }

  // Records the upload's first failure and stops everything else.
  private void fail(Throwable e, OperationScope stop) {
    synchronized (lock) {
      if (failure == null) {
        failure = e;
        log.debug("Stopping the upload to {}", path, e);
      }
    }
    stop.fail();
  }

  // Waits until every part in flight has ended. While reporting, progress for each part accepted
  // meanwhile is reported on this thread. Returns whether this thread was interrupted while it
  // waited; the upload is then stopped.
  private boolean awaitParts(OperationScope stop, boolean reporting) {
    boolean interrupted = false;
    while (true) {
      synchronized (lock) {
        while (inFlight > 0 && (!reporting || unreported.isEmpty())) {
          try {
            lock.wait();
          } catch (InterruptedException e) {
            interrupted = true;
            stop.interrupted();
          }
        }
        if (inFlight == 0 && (!reporting || unreported.isEmpty())) {
          return interrupted;
        }
      }
      reportAccepted();
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

  // Reports each part accepted since the last report.
  private void reportAccepted() {
    List<UploadedPart> parts;
    synchronized (lock) {
      if (unreported.isEmpty()) {
        return;
      }
      parts = new ArrayList<UploadedPart>(unreported);
      unreported.clear();
    }
    for (UploadedPart part : parts) {
      reportedBytes += part.getLength();
      reportedParts++;
      report(progress());
    }
  }

  private void report(TransferProgress progress) {
    TransferProgressListener listener = options.getProgressListener();
    if (listener != null) {
      listener.onProgress(progress);
    }
  }

  private TransferProgress progress() {
    AttemptAdmission admission = attempts;
    return new TransferProgress(path, source.length(), reportedBytes, resumedBytes, sentBytes.get(), reportedParts, retries.get(),
        Duration.ofNanos(System.nanoTime() - started), admission == null ? null : admission.snapshot());
  }

  // Every planned part, in order, each accepted exactly once: the only list the upload is
  // completed with.
  private List<UploadedPart> acceptedParts(PartPlanner planner) {
    synchronized (lock) {
      List<UploadedPart> parts = new ArrayList<UploadedPart>(accepted.values());
      for (int i = 0; i < parts.size(); i++) {
        if (parts.get(i).getNumber() != i + 1) {
          throw new IllegalStateException("Part " + (i + 1) + " of the upload to " + path + " was not accepted");
        }
      }
      if (parts.size() != planner.partCount()) {
        throw new IllegalStateException("Only " + parts.size() + " of the " + planner.partCount() + " parts of the upload to " + path
            + " were accepted");
      }
      return Collections.unmodifiableList(parts);
    }
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
    throw new SdkException("The upload failed", failure);
  }

  // A prepared part. It holds its buffer reservation until it has been sent for the last time.
  private static final class Part {
    final PartPlanner.Planned planned;
    final UploadSource.PartBody body;
    final FifoBudget.Grant buffer;
    final boolean last;
    // Replaced when the part's URL is renewed.
    volatile PartPlanner.Target target;
    volatile boolean alone;
    // What the part holds while it is in progress: its place among the parts in progress, and its
    // next attempt, admitted and holding its slots, each null when not held. Used by one thread at a
    // time.
    FifoBudget.Grant inProgress;
    AttemptAdmission.Attempt attempt;
    // With a checkpoint, the record's position its planned entry ends at, which must be durable before
    // the part is first sent; 0 for a part an earlier run recorded.
    long durableAt;
    // Whether the upload's caller sends the part itself (dispatch).
    boolean onCaller;

    Part(PartPlanner.Planned planned, UploadSource.PartBody body, FifoBudget.Grant buffer, boolean last) {
      this.planned = planned;
      this.body = body;
      this.buffer = buffer;
      this.last = last;
      this.target = planned.target;
    }

    // Gives back everything the part holds; it will not be sent again.
    void release() {
      if (attempt != null) {
        attempt.close();
      }
      if (inProgress != null) {
        inProgress.close();
      }
      body.release();
      buffer.close();
    }
  }

  private static final class Outcome {
    final String etag;
    final SdkException error;
    final boolean retryable;
    final int status;
    final long retryAfterMillis;
    final String reason;
    final long durationNanos;

    private Outcome(String etag, SdkException error, boolean retryable, int status, long retryAfterMillis, String reason,
        long durationNanos) {
      this.etag = etag;
      this.error = error;
      this.retryable = retryable;
      this.status = status;
      this.retryAfterMillis = retryAfterMillis;
      this.reason = reason;
      this.durationNanos = durationNanos;
    }

    static Outcome accepted(String etag, long durationNanos) {
      return new Outcome(etag, null, false, 0, -1, null, durationNanos);
    }

    static Outcome failed(SdkException error, boolean retryable, int status, long retryAfterMillis, String reason, long durationNanos) {
      return new Outcome(null, error, retryable, status, retryAfterMillis, reason, durationNanos);
    }
  }

  // A request with the method Files.com negotiated for the part, sent to its URL as given.
  private static final class StorageRequest extends HttpEntityEnclosingRequestBase {
    private final String method;

    StorageRequest(String method, URI uri) {
      this.method = method;
      setURI(uri);
    }

    @Override
    public String getMethod() {
      return method;
    }
  }

  // A part's bytes as a request body: the same bytes each time it is written, with its exact
  // length and no content type of its own.
  private static final class PartEntity extends AbstractHttpEntity {
    private final UploadSource.PartBody body;
    private final LongConsumer sent;

    PartEntity(UploadSource.PartBody body, LongConsumer sent) {
      this.body = body;
      this.sent = sent;
    }

    @Override
    public boolean isRepeatable() {
      return true;
    }

    @Override
    public long getContentLength() {
      return body.length();
    }

    @Override
    public InputStream getContent() {
      throw new UnsupportedOperationException("A part's body is only written");
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
      body.writeTo(out, sent);
    }

    @Override
    public boolean isStreaming() {
      return false;
    }
  }
}
