package com.files.net;

import com.files.CancellationToken;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Settings for an upload made with {@link Transfers}. Immutable; make one with
 * {@link #builder()}, or use {@link #defaults()}. The same options can be used for any number of
 * uploads.
 */
public final class UploadOptions {
  /**
   * The parts a {@link ConcurrencyMode#STATIC} upload sends at the same time unless
   * {@link Builder#maxConcurrency} is set. An automatic upload has no cap of its own unless it is set.
   */
  public static final int DEFAULT_MAX_CONCURRENCY = 4;

  /**
   * The most bytes a stream of unknown length may hold unless {@link Builder#maxStreamLength} is
   * set: 5 TiB, the largest file Files.com plans uploads for.
   */
  public static final long DEFAULT_MAX_STREAM_LENGTH = PartSizes.MAX_FILE;

  private static final UploadOptions DEFAULTS = builder().build();

  private final Integer maxConcurrency;
  private final ConcurrencyMode concurrencyMode;
  private final UploadStartup startup;
  private final TransferLimiter limiter;
  private final TransferScope scope;
  private final TransferProgressListener progressListener;
  private final Instant modifiedTime;
  private final Boolean mkdirParents;
  private final HashMap<String, Object> requestOptions;
  private final CancellationToken cancellation;
  private final Duration timeout;
  private final PartSizing partSizing;
  private final long maxStreamLength;
  private final Path checkpointDirectory;

  private UploadOptions(Builder builder) {
    this.maxConcurrency = builder.maxConcurrency;
    this.concurrencyMode = builder.concurrencyMode;
    this.startup = builder.startup;
    this.limiter = builder.limiter;
    this.scope = builder.scope;
    this.progressListener = builder.progressListener;
    this.modifiedTime = builder.modifiedTime;
    this.mkdirParents = builder.mkdirParents;
    this.requestOptions = new HashMap<String, Object>(builder.requestOptions);
    this.cancellation = builder.cancellation;
    this.timeout = builder.timeout;
    this.partSizing = builder.partSizing;
    this.maxStreamLength = builder.maxStreamLength;
    this.checkpointDirectory = builder.checkpointDirectory;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static UploadOptions defaults() {
    return DEFAULTS;
  }

  /** The most parts to send at the same time, or null when it was not set. */
  public Integer getMaxConcurrency() {
    return maxConcurrency;
  }

  public ConcurrencyMode getConcurrencyMode() {
    return concurrencyMode;
  }

  public UploadStartup getStartup() {
    return startup;
  }

  /** The shared limiter, or null when each upload has a limiter of its own. */
  public TransferLimiter getLimiter() {
    return limiter;
  }

  /** The job the upload belongs to, or null. */
  public TransferScope getScope() {
    return scope;
  }

  public TransferProgressListener getProgressListener() {
    return progressListener;
  }

  public Instant getModifiedTime() {
    return modifiedTime;
  }

  public Boolean getMkdirParents() {
    return mkdirParents;
  }

  /** A copy of the request options. */
  public HashMap<String, Object> getRequestOptions() {
    return new HashMap<String, Object>(requestOptions);
  }

  public CancellationToken getCancellation() {
    return cancellation;
  }

  public Duration getTimeout() {
    return timeout;
  }

  public PartSizing getPartSizing() {
    return partSizing;
  }

  public long getMaxStreamLength() {
    return maxStreamLength;
  }

  /** The directory the upload keeps its checkpoint in, or null when it keeps none. */
  public Path getCheckpointDirectory() {
    return checkpointDirectory;
  }

  // The options of the upload's calls: its request options, with its cancellation token and time
  // limit, which cover the whole upload.
  HashMap<String, Object> callOptions() {
    HashMap<String, Object> options = new HashMap<String, Object>(requestOptions);
    if (cancellation != null) {
      options.put(ClientRuntime.CANCELLATION_OPTION, cancellation);
    }
    if (timeout != null) {
      options.put(ClientRuntime.TIMEOUT_OPTION, timeout);
    }
    return options;
  }

  /**
   * Builds {@link UploadOptions}. A builder is not safe for use by several threads at once.
   */
  public static final class Builder {
    private Integer maxConcurrency;
    private ConcurrencyMode concurrencyMode = ConcurrencyMode.AUTOMATIC;
    private UploadStartup startup = UploadStartup.DEFAULT;
    private TransferLimiter limiter;
    private TransferScope scope;
    private TransferProgressListener progressListener;
    private Instant modifiedTime;
    private Boolean mkdirParents;
    private Map<String, Object> requestOptions = new HashMap<String, Object>();
    private CancellationToken cancellation;
    private Duration timeout;
    private PartSizing partSizing = PartSizing.automatic();
    private long maxStreamLength = DEFAULT_MAX_STREAM_LENGTH;
    private Path checkpointDirectory;

    private Builder() {
    }

    /**
     * The most parts of the upload to send at the same time, from 1 to 1024. An automatic upload
     * stays within it as well as within its policy's maximum; if it is not set, only the latter
     * applies. A static upload sends this many at once, or
     * {@link UploadOptions#DEFAULT_MAX_CONCURRENCY} if it is not set. When Files.com does not allow
     * sending parts in parallel, they are sent one at a time, in order, whatever this is. The
     * limiter's {@link TransferLimiter#getMaxConcurrentRequests()} and the transport's connections to
     * the storage host ({@link HttpTransport.Builder#maxConnectionsPerRoute}) also apply.
     */
    public Builder maxConcurrency(int maxConcurrency) {
      if (maxConcurrency < 1 || maxConcurrency > ConcurrencyTuning.MAX_PARTS) {
        throw new IllegalArgumentException("An upload must be allowed to send from 1 to 1024 parts at a time");
      }
      this.maxConcurrency = maxConcurrency;
      return this;
    }

    /**
     * Whether the upload adapts how many parts it sends at once. Default
     * {@link ConcurrencyMode#AUTOMATIC}: the upload measures what its parts deliver and follows the
     * policy for its destination, as Files.com's answers show it ({@link UploadDestination}). For S3,
     * the scaled policy starts at 150 parts at once (or from one, with the default
     * {@link #startup(UploadStartup) startup}), measures windows of at least 32 answers, grows by 4
     * parts when a window shows the gain, up to 150 and, once 64 GiB have been delivered at 96 MiB/s,
     * up to 1024; a temporary failure lowers it by 35%, down to 8, throttling by 10%, pausing new
     * parts for the server's {@code Retry-After}, and queued requests by 8%, down to 50. For a Files
     * Agent, it starts at 2 parts and goes up to 8. {@link ConcurrencyMode#STATIC} sends
     * {@link #maxConcurrency} parts at once. {@link TransferProgress#getConcurrency()} reports what the
     * upload does.
     */
    public Builder concurrencyMode(ConcurrencyMode concurrencyMode) {
      if (concurrencyMode == null) {
        throw new IllegalArgumentException("The concurrency mode cannot be null");
      }
      this.concurrencyMode = concurrencyMode;
      return this;
    }

    /**
     * How an automatic upload to S3 starts. Default {@link UploadStartup#DEFAULT}: from one part at
     * a time, adding parts only as measured answers show they help, or going straight to the
     * policy's start of 150 once a window shows 96 MiB/s. {@link UploadStartup#AGGRESSIVE} starts at
     * 150, within {@link #maxConcurrency} and the limiter, for a network known to be fast. Either
     * way the upload then adapts with the same policy. An upload to a Files Agent starts from its
     * own policy's start whichever is chosen, and a static upload cannot be given an aggressive
     * startup. In a {@link #scope}, an automatic upload with the default startup also waits for its
     * place among the scope's uploads in progress before it begins, so that many callers start a
     * few files and add more while their parts show they can use them, up to, when needed, as many
     * as the callers offer, which an aggressive start has at once; an aggressive upload begins at
     * once. See {@link TransferScope}.
     */
    public Builder startup(UploadStartup startup) {
      if (startup == null) {
        throw new IllegalArgumentException("The startup cannot be null");
      }
      this.startup = startup;
      return this;
    }

    /**
     * The limiter whose request slots and buffer space the upload shares with other transfers.
     * Default: the upload has a limiter of its own, allowing {@link #maxConcurrency} requests (if
     * it is not set, 1024 for an automatic upload and {@link UploadOptions#DEFAULT_MAX_CONCURRENCY}
     * for a static one) and {@link TransferLimiter#DEFAULT_MAX_BUFFERED_BYTES} bytes.
     */
    public Builder limiter(TransferLimiter limiter) {
      this.limiter = limiter;
      return this;
    }

    /**
     * The job this upload belongs to: it shares the scope's limiter, unless {@link #limiter} is set,
     * and, when automatic, the concurrency target of the scope's compatible transfers, which it
     * learns with them; with the default {@link #startup}, it also waits for its place among the
     * scope's uploads in progress. See {@link TransferScope}. Default: none; the upload learns on its
     * own.
     */
    public Builder scope(TransferScope scope) {
      this.scope = scope;
      return this;
    }

    /**
     * Receives the upload's progress: once it has started, and each time Files.com's storage
     * accepts a part. The listener is called on the thread that called the upload method, between
     * the upload's steps, so keep it quick. If it throws, the upload stops and throws that
     * exception. No progress is reported after the request that completes the upload is sent.
     */
    public Builder progressListener(TransferProgressListener progressListener) {
      this.progressListener = progressListener;
      return this;
    }

    /**
     * The modification time to record for the file. Default: a file upload records the local
     * file's last modified time, and a stream upload lets Files.com use the time the upload
     * completes.
     */
    public Builder modifiedTime(Instant modifiedTime) {
      this.modifiedTime = modifiedTime;
      return this;
    }

    /**
     * Whether to ask Files.com to create missing parent folders of the destination (the
     * {@code mkdir_parents} parameter). Default: not sent, so the site's own setting decides.
     */
    public Builder mkdirParents(Boolean mkdirParents) {
      this.mkdirParents = mkdirParents;
      return this;
    }

    /**
     * Request options for the upload's Files.com API requests, such as {@code api_key},
     * {@code session_id} or {@code workspace_id}, as the other client methods take them. The map
     * is copied. The part requests, sent to the storage URLs Files.com provides, never carry
     * Files.com credentials.
     */
    public Builder requestOptions(Map<String, Object> requestOptions) {
      this.requestOptions = requestOptions == null ? new HashMap<String, Object>() : new HashMap<String, Object>(requestOptions);
      return this;
    }

    /**
     * Stops the upload when cancelled: wherever it is, its requests are aborted and its waits end,
     * and once every part in flight has stopped the upload throws
     * {@link com.files.exceptions.OperationCancelledException}.
     */
    public Builder cancellation(CancellationToken cancellation) {
      this.cancellation = cancellation;
      return this;
    }

    /**
     * The time limit of the whole upload, from its first request to its last. Default: none,
     * besides the client's own call timeout.
     */
    public Builder timeout(Duration timeout) {
      if (timeout != null && (timeout.isNegative() || timeout.isZero())) {
        throw new IllegalArgumentException("The upload timeout must be positive");
      }
      this.timeout = timeout;
      return this;
    }

    /**
     * How the upload's part sizes are chosen. Default {@link PartSizing#automatic()}: by the SDK
     * where Files.com's instructions let it, and as Files.com plans them everywhere else.
     */
    public Builder partSizing(PartSizing partSizing) {
      if (partSizing == null) {
        throw new IllegalArgumentException("The part sizing cannot be null");
      }
      this.partSizing = partSizing;
      return this;
    }

    /**
     * The most bytes a stream of unknown length may hold, from 0. Default
     * {@link UploadOptions#DEFAULT_MAX_STREAM_LENGTH}. A stream that holds more fails before its
     * upload is completed. Where the SDK chooses part sizes, they are chosen so that 10,000 parts
     * can hold this many bytes: a lower bound allows smaller parts, so that a stream's parts, which
     * are held in memory until the storage server accepts them, fit the limiter's buffer space.
     * Where they cannot, the upload sends the parts Files.com plans. It has no effect on uploads of
     * known length.
     */
    public Builder maxStreamLength(long maxStreamLength) {
      if (maxStreamLength < 0) {
        throw new IllegalArgumentException("The most bytes a stream may hold cannot be negative");
      }
      this.maxStreamLength = maxStreamLength;
      return this;
    }

    /**
     * Keeps a checkpoint of the upload in {@code checkpointDirectory}, so that a later upload of the
     * same file to the same path, with the same options, continues it instead of starting over:
     * parts Files.com's storage accepted are not sent again, and parts that may have been sent are
     * sent again with exactly the same bytes. Only an upload of a local file,
     * {@link Transfers#upload(java.nio.file.Path, String, UploadOptions)}, can keep one; the other
     * upload methods refuse options that set it. Default: none. See {@link Transfers} for what the
     * checkpoint holds and how it is kept private.
     *
     * @param checkpointDirectory a directory of your own that only you can use, or one that does not
     *     exist yet, whose parent does, which is created that way; null for none
     */
    public Builder checkpointDirectory(Path checkpointDirectory) {
      this.checkpointDirectory = checkpointDirectory;
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the options
     * @throws IllegalStateException if a static upload was given an aggressive startup, which applies
     *     only to automatic uploads
     */
    public UploadOptions build() {
      if (concurrencyMode == ConcurrencyMode.STATIC && startup == UploadStartup.AGGRESSIVE) {
        throw new IllegalStateException("An aggressive startup applies only to an automatic upload, not a static one");
      }
      return new UploadOptions(this);
    }
  }
}
