package com.files.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A job of transfers that learn together: uploads and downloads given the same scope (with
 * {@link UploadOptions.Builder#scope} or {@link DownloadOptions.Builder#scope}) share one request
 * budget, its {@link #getLimiter() limiter}, and, when they are compatible, one automatic
 * concurrency target. Safe for use by several threads at once.
 *
 * <p>Compatible transfers are those in the same direction (uploads, or downloads), with the same
 * concurrency mode, destination policy, startup and caps. They count their part requests together
 * against one target, rather than each having all of it, and every one of them teaches it: accepted
 * parts, failures and throttling. The target, its measurements and its backoff carry on as transfers
 * start and finish, so a transfer of one part still teaches the next, and an upload to S3 with the
 * default startup warms up once for the scope, not once per file. A download that comes in one
 * request counts that request as a part; the first part of a download fetched in parts, which binds
 * its version, and a resumed download's confirmation are asked for alone, outside the target. A file
 * Files.com requires to send its parts one at a time is held to that itself, without slowing the
 * others. A file whose part sizes the SDK chooses still learns them from its own parts alone, one
 * file at a time, while the shared target is settled. Transfers in different scopes, and those given
 * no scope, learn separately, even when they share a limiter.
 *
 * <p>An automatic upload with the default {@link UploadOptions.Builder#startup startup} also waits,
 * before it asks Files.com anything, for its place among the scope's uploads of that kind in progress,
 * as the Files.com CLI admits the files of a job, and keeps it until it ends: completed, failed or
 * cancelled. At most six such uploads begin first: once their parts' shared target exists, it may
 * hold them to fewer. Then as many may be in progress as the lowest of their shared targets plus a
 * quarter of it and one, and at least four, so that a probe of a higher target always has parts to
 * send; and while their parts are too few to keep the targets busy, one more may begin every 100 ms.
 * So many callers start a few files and add more while their parts show they can use them: when
 * needed, the uploads in progress can grow to as many as the callers offer, the same supply an
 * aggressive startup takes at once, reached by probing; while enough of their parts wait, no more
 * begin. Either way the limiter, the part targets and the transport's connections still bound the
 * requests and memory the uploads use. Aggressive uploads, static ones and uploads given no scope do
 * not wait. A waiting upload holds no request slot or buffer space, only what its call had already
 * opened, such as its local file; its wait ends, taking nothing, when it is cancelled, times out or
 * is interrupted.
 *
 * <p>You own the scope: run the job's transfers on threads of your own, and close it once the job is
 * done. Closing it refuses new ones, and lets transfers already running finish, within the scope's
 * limiter: one that had yet to join the shared target when the scope closed learns on its own
 * instead, and uploads already waiting for their place are still admitted. Nothing the scope learned
 * is kept for another scope.
 */
public final class TransferScope implements AutoCloseable {
  private final TransferLimiter limiter;
  private final Map<Key, AttemptAdmission> admissions = new HashMap<Key, AttemptAdmission>();
  // The places of the automatic uploads with the default startup, which outlast closing the scope.
  private final UploadFileAdmission uploadFiles = new UploadFileAdmission(this::uploadStartup, System::nanoTime);
  private boolean closed;

  /**
   * A scope with a limiter of its own: 1024 request slots, the most an automatic transfer may
   * target, and {@link TransferLimiter#DEFAULT_MAX_BUFFERED_BYTES} of buffer space.
   */
  public TransferScope() {
    this(new TransferLimiter(ConcurrencyTuning.MAX_PARTS, TransferLimiter.DEFAULT_MAX_BUFFERED_BYTES));
  }

  /**
   * A scope whose transfers share limiter, unless their options give another.
   *
   * @param limiter the request slots and buffer space the scope's transfers share
   */
  public TransferScope(TransferLimiter limiter) {
    if (limiter == null) {
      throw new IllegalArgumentException("The limiter cannot be null");
    }
    this.limiter = limiter;
  }

  /** The request slots and buffer space the scope's transfers share, unless their options give another. */
  public TransferLimiter getLimiter() {
    return limiter;
  }

  public synchronized boolean isClosed() {
    return closed;
  }

  /** Refuses new transfers; those already running finish. */
  @Override
  public synchronized void close() {
    closed = true;
    admissions.clear();
  }

  // The admission the transfers of key share, made by create for the first of them; null once the
  // scope is closed, when a transfer that started before then admits its attempts on its own.
  synchronized AttemptAdmission admissionFor(Key key, Supplier<AttemptAdmission> create) {
    if (closed) {
      return null;
    }
    return admissions.computeIfAbsent(key, k -> create.get());
  }

  UploadFileAdmission uploadFileAdmission() {
    return uploadFiles;
  }

  // What the shared targets of the scope's automatic uploads with the default startup show
  // (UploadFileAdmission.Startup): the lowest target, the warm start of those that low, and the
  // attempts waiting for any of them; null when there are none, yet or since the scope closed. Each is
  // read under its own lock, briefly, while the scope's is held.
  synchronized UploadFileAdmission.Startup uploadStartup() {
    UploadFileAdmission.Startup lowest = null;
    int waiting = 0;
    for (Map.Entry<Key, AttemptAdmission> entry : admissions.entrySet()) {
      if (!entry.getKey().admitsFiles()) {
        continue;
      }
      UploadFileAdmission.Startup state = entry.getValue().startupState();
      waiting += state.waiting;
      if (lowest == null || state.target < lowest.target) {
        lowest = state;
      } else if (state.target == lowest.target) {
        lowest = new UploadFileAdmission.Startup(lowest.target, lowest.warmStarted || state.warmStarted, lowest.warming || state.warming, 0);
      }
    }
    return lowest == null ? null : new UploadFileAdmission.Startup(lowest.target, lowest.warmStarted, lowest.warming, waiting);
  }

  // What makes transfers compatible: their direction and concurrency settings.
  static final class Key {
    private final boolean upload;
    private final ConcurrencyMode mode;
    private final UploadDestination destination;
    private final UploadStartup startup;
    private final int max;
    private final boolean warmStarts;

    Key(boolean upload, ConcurrencySettings settings) {
      this.upload = upload;
      this.mode = settings.mode;
      this.destination = settings.destination;
      this.startup = settings.startup;
      this.max = settings.max;
      this.warmStarts = upload && settings.warmStarts();
    }

    // Whether the transfers sharing it are automatic uploads with the default startup, whose places
    // its target admits (UploadFileAdmission).
    boolean admitsFiles() {
      return upload && mode == ConcurrencyMode.AUTOMATIC && startup == UploadStartup.DEFAULT;
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Key)) {
        return false;
      }
      Key key = (Key) other;
      return upload == key.upload && mode == key.mode && destination == key.destination && startup == key.startup && max == key.max
          && warmStarts == key.warmStarts;
    }

    @Override
    public int hashCode() {
      return Objects.hash(upload, mode, destination, startup, max, warmStarts);
    }
  }
}
