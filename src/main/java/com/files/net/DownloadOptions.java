package com.files.net;

import com.files.CancellationToken;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Settings for a download made with {@link Transfers}. Immutable; make one with
 * {@link #builder()}, or use {@link #defaults()}.
 */
public final class DownloadOptions {
  /** The parts a {@link ConcurrencyMode#STATIC} download fetches at once unless {@link Builder#maxConcurrency} is set. */
  public static final int DEFAULT_MAX_CONCURRENCY = 4;

  /** The size of the parts a download fetches unless {@link Builder#partSize} is set: 8 MiB. */
  public static final long DEFAULT_PART_SIZE = 8L * 1024 * 1024;

  private static final DownloadOptions DEFAULTS = builder().build();

  private final boolean replaceExisting;
  private final HashMap<String, Object> requestOptions;
  private final CancellationToken cancellation;
  private final Duration timeout;
  private final ConcurrencyMode concurrencyMode;
  private final Integer maxConcurrency;
  private final long partSize;
  private final TransferLimiter limiter;
  private final TransferScope scope;
  private final TransferProgressListener progressListener;
  private final Path checkpointDirectory;

  private DownloadOptions(Builder builder) {
    this.replaceExisting = builder.replaceExisting;
    this.requestOptions = new HashMap<String, Object>(builder.requestOptions);
    this.cancellation = builder.cancellation;
    this.timeout = builder.timeout;
    this.concurrencyMode = builder.concurrencyMode;
    this.maxConcurrency = builder.maxConcurrency;
    this.partSize = builder.partSize;
    this.limiter = builder.limiter;
    this.scope = builder.scope;
    this.progressListener = builder.progressListener;
    this.checkpointDirectory = builder.checkpointDirectory;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static DownloadOptions defaults() {
    return DEFAULTS;
  }

  public boolean isReplaceExisting() {
    return replaceExisting;
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

  public ConcurrencyMode getConcurrencyMode() {
    return concurrencyMode;
  }

  /** The most parts to fetch at the same time, or null when it was not set. */
  public Integer getMaxConcurrency() {
    return maxConcurrency;
  }

  public long getPartSize() {
    return partSize;
  }

  /** The shared limiter, or null when each download has a limiter of its own. */
  public TransferLimiter getLimiter() {
    return limiter;
  }

  /** The job the download belongs to, or null. */
  public TransferScope getScope() {
    return scope;
  }

  public TransferProgressListener getProgressListener() {
    return progressListener;
  }

  /** The directory the download keeps its checkpoint in, or null when it keeps none. */
  public Path getCheckpointDirectory() {
    return checkpointDirectory;
  }

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
   * Builds {@link DownloadOptions}. A builder is not safe for use by several threads at once.
   */
  public static final class Builder {
    private boolean replaceExisting = true;
    private Map<String, Object> requestOptions = new HashMap<String, Object>();
    private CancellationToken cancellation;
    private Duration timeout;
    private ConcurrencyMode concurrencyMode = ConcurrencyMode.AUTOMATIC;
    private Integer maxConcurrency;
    private long partSize = DEFAULT_PART_SIZE;
    private TransferLimiter limiter;
    private TransferScope scope;
    private TransferProgressListener progressListener;
    private Path checkpointDirectory;

    private Builder() {
    }

    /**
     * Whether a completed download replaces a file already at the destination. Default true;
     * when false, the download fails if the destination exists when it would be moved into place.
     */
    public Builder replaceExisting(boolean replaceExisting) {
      this.replaceExisting = replaceExisting;
      return this;
    }

    /**
     * Request options for the Files.com API request that describes the file, such as
     * {@code api_key}. The map is copied. The request for the contents, sent to the URL Files.com
     * provides, carries no Files.com credentials.
     */
    public Builder requestOptions(Map<String, Object> requestOptions) {
      this.requestOptions = requestOptions == null ? new HashMap<String, Object>() : new HashMap<String, Object>(requestOptions);
      return this;
    }

    /**
     * Stops the download when cancelled; its own file is deleted, unless a
     * {@link #checkpointDirectory checkpoint} keeps it, and the destination is left as it was.
     */
    public Builder cancellation(CancellationToken cancellation) {
      this.cancellation = cancellation;
      return this;
    }

    /**
     * The time limit of the whole download. Default: none, besides the client's own call timeout.
     */
    public Builder timeout(Duration timeout) {
      if (timeout != null && (timeout.isNegative() || timeout.isZero())) {
        throw new IllegalArgumentException("The download timeout must be positive");
      }
      this.timeout = timeout;
      return this;
    }

    /**
     * Whether the download adapts how many parts it fetches at once. Default
     * {@link ConcurrencyMode#AUTOMATIC}: a download fetched in parts follows the policy for where
     * they come from, as for uploads, but starts at that policy's start: 150 parts at once from S3,
     * within the caps, or 2 from a Files Agent. {@link ConcurrencyMode#STATIC} fetches
     * {@link #maxConcurrency} parts at once. Either way a file is fetched in parts only when the
     * storage server binds them to one version of it (see {@link Transfers#download(com.files.models.File,
     * java.nio.file.Path, DownloadOptions)}); otherwise it comes in one request.
     */
    public Builder concurrencyMode(ConcurrencyMode concurrencyMode) {
      if (concurrencyMode == null) {
        throw new IllegalArgumentException("The concurrency mode cannot be null");
      }
      this.concurrencyMode = concurrencyMode;
      return this;
    }

    /**
     * The most parts to fetch at the same time, from 1 to 1024. An automatic download stays within
     * it; a static one fetches this many, or {@link DownloadOptions#DEFAULT_MAX_CONCURRENCY} if it is
     * not set. The limiter's request slots and the transport's connections to the storage host also
     * apply.
     */
    public Builder maxConcurrency(int maxConcurrency) {
      if (maxConcurrency < 1 || maxConcurrency > ConcurrencyTuning.MAX_PARTS) {
        throw new IllegalArgumentException("A download must be allowed to fetch from 1 to 1024 parts at a time");
      }
      this.maxConcurrency = maxConcurrency;
      return this;
    }

    /**
     * The size in bytes of each part (range) fetched, at least 1. Default
     * {@link DownloadOptions#DEFAULT_PART_SIZE}. The last part may be smaller.
     */
    public Builder partSize(long partSize) {
      if (partSize < 1) {
        throw new IllegalArgumentException("A download's part size must be at least 1 byte");
      }
      this.partSize = partSize;
      return this;
    }

    /**
     * The limiter whose request slots and buffer space the download shares with other transfers.
     * Default: a limiter of its own, allowing {@link #maxConcurrency} requests (if it is not set,
     * 1024 for an automatic download and {@link DownloadOptions#DEFAULT_MAX_CONCURRENCY} for a static
     * one) and {@link TransferLimiter#DEFAULT_MAX_BUFFERED_BYTES} bytes. Every request for the file's
     * contents holds a request slot until its answer has been read, and gives it back, with its place
     * in the scope's target and among the storage host's connections, before it waits to be retried.
     * A part holds its copy buffer's space until it has been written, retries included; the whole
     * file's request holds it only during each attempt.
     */
    public Builder limiter(TransferLimiter limiter) {
      this.limiter = limiter;
      return this;
    }

    /**
     * The job this download belongs to: it shares the scope's limiter, unless {@link #limiter} is
     * set, and, when automatic, the concurrency target of the scope's compatible downloads, which it
     * learns with them: its parts, or its requests for the whole file when it comes in one, count
     * against the target and teach it. The first part, which binds a version, and a resumed
     * download's confirmation are asked for alone, outside the target. See {@link TransferScope}.
     * Default: none.
     */
    public Builder scope(TransferScope scope) {
      this.scope = scope;
      return this;
    }

    /**
     * Receives the download's progress: once it knows how it fetches the file, and as each part is
     * written. The listener is called on the thread that called the download method; keep it
     * quick. If it throws, the download stops and throws that exception.
     */
    public Builder progressListener(TransferProgressListener progressListener) {
      this.progressListener = progressListener;
      return this;
    }

    /**
     * Keeps a checkpoint of the download in {@code checkpointDirectory}, so that a download fetched
     * in parts that stops before it is complete keeps its partial file, and a later download of the
     * same file to the same destination, with the same options, continues it instead of starting
     * over: only the ranges it does not hold are fetched, once the storage server confirms the
     * file's version. A file that comes in one request keeps no progress. Applies to the downloads
     * of {@link Transfers} to a {@link java.nio.file.Path}. Default: none. See
     * {@link Transfers#download(com.files.models.File, java.nio.file.Path, DownloadOptions)} for
     * what the checkpoint holds, and {@link UploadOptions.Builder#checkpointDirectory} for what the
     * directory must be.
     *
     * @param checkpointDirectory a directory of your own that only you can use, or one that does not
     *     exist yet, whose parent does, which is created that way; null for none
     */
    public Builder checkpointDirectory(Path checkpointDirectory) {
      this.checkpointDirectory = checkpointDirectory;
      return this;
    }

    public DownloadOptions build() {
      return new DownloadOptions(this);
    }
  }
}
