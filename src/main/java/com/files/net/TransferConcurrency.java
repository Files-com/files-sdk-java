package com.files.net;

import java.time.Duration;

/**
 * How many parts a transfer sends at once, and why, as {@link TransferProgress#getConcurrency()}
 * reports it. A snapshot: immutable.
 *
 * <p>The target is how many attempts the transfer lets run at once; the attempts in flight can be
 * fewer, when parts wait for the limiter, the connection pool or their own preparation, or more for
 * a while after the target was lowered, since attempts already running finish normally. Times are
 * totals since the transfer's first attempt, split by what held the attempts in flight below the
 * target, or at it.
 */
public final class TransferConcurrency {
  private final ConcurrencyMode mode;
  private final UploadDestination destination;
  private final UploadStartup startup;
  private final boolean shared;
  private final boolean warmingUp;
  private final int target;
  private final int limit;
  private final int activeAttempts;
  private final int peakActiveAttempts;
  private final int minTarget;
  private final int softCeiling;
  private final int maxTarget;
  private final boolean softCeilingUnlocked;
  private final boolean serial;
  private final ConcurrencyDecision lastDecision;
  private final int epoch;
  private final int grows;
  private final int shrinks;
  private final int probesAccepted;
  private final int probesRejected;
  private final int measuredWindows;
  private final int unmeasuredWindows;
  private final int throttles;
  private final int transientFailures;
  private final Duration cooldownRemaining;
  private final double lastThroughputBytesPerSecond;
  private final double bestThroughputBytesPerSecond;
  private final double queueEstimate;
  private final Duration timeAtTarget;
  private final Duration timeWaitingForLimiter;
  private final Duration timeWithoutReadyPart;
  private final Duration timeSendingSerially;
  private final Duration timeWritingDestination;

  TransferConcurrency(ConcurrencyMode mode, UploadDestination destination, UploadStartup startup, boolean shared, boolean warmingUp, int target,
      int limit,
      int activeAttempts, int peakActiveAttempts, int minTarget, int softCeiling, int maxTarget, boolean softCeilingUnlocked,
      boolean serial, ConcurrencyDecision lastDecision, int epoch, int grows, int shrinks, int probesAccepted, int probesRejected,
      int measuredWindows, int unmeasuredWindows, int throttles, int transientFailures, Duration cooldownRemaining,
      double lastThroughputBytesPerSecond, double bestThroughputBytesPerSecond, double queueEstimate, Duration timeAtTarget,
      Duration timeWaitingForLimiter, Duration timeWithoutReadyPart, Duration timeSendingSerially, Duration timeWritingDestination) {
    this.mode = mode;
    this.destination = destination;
    this.startup = startup;
    this.shared = shared;
    this.warmingUp = warmingUp;
    this.target = target;
    this.limit = limit;
    this.activeAttempts = activeAttempts;
    this.peakActiveAttempts = peakActiveAttempts;
    this.minTarget = minTarget;
    this.softCeiling = softCeiling;
    this.maxTarget = maxTarget;
    this.softCeilingUnlocked = softCeilingUnlocked;
    this.serial = serial;
    this.lastDecision = lastDecision;
    this.epoch = epoch;
    this.grows = grows;
    this.shrinks = shrinks;
    this.probesAccepted = probesAccepted;
    this.probesRejected = probesRejected;
    this.measuredWindows = measuredWindows;
    this.unmeasuredWindows = unmeasuredWindows;
    this.throttles = throttles;
    this.transientFailures = transientFailures;
    this.cooldownRemaining = cooldownRemaining;
    this.lastThroughputBytesPerSecond = lastThroughputBytesPerSecond;
    this.bestThroughputBytesPerSecond = bestThroughputBytesPerSecond;
    this.queueEstimate = queueEstimate;
    this.timeAtTarget = timeAtTarget;
    this.timeWaitingForLimiter = timeWaitingForLimiter;
    this.timeWithoutReadyPart = timeWithoutReadyPart;
    this.timeSendingSerially = timeSendingSerially;
    this.timeWritingDestination = timeWritingDestination;
  }

  /** Whether the transfer adapts its concurrency. */
  public ConcurrencyMode getMode() {
    return mode;
  }

  /** Where the parts go, which chose the automatic policy. */
  public UploadDestination getDestination() {
    return destination;
  }

  /** The startup chosen; it changes only how an automatic upload to S3 starts. */
  public UploadStartup getStartup() {
    return startup;
  }

  /**
   * Whether the target is the one the compatible transfers of a {@link TransferScope} share; its
   * counts are then theirs together.
   */
  public boolean isShared() {
    return shared;
  }

  /** Whether a default startup is still adding parts from one at a time. */
  public boolean isWarmingUp() {
    return warmingUp;
  }

  /** How many attempts the transfer lets run at once now. */
  public int getTarget() {
    return target;
  }

  /** How many parts may be sent at once now: the target, or 1 while parts must be sent one at a time. */
  public int getLimit() {
    return limit;
  }

  /** The attempts in flight now. */
  public int getActiveAttempts() {
    return activeAttempts;
  }

  /** The most attempts that were in flight at once. */
  public int getPeakActiveAttempts() {
    return peakActiveAttempts;
  }

  /** The lowest target failures can lower it to now. */
  public int getMinTarget() {
    return minTarget;
  }

  /** The highest target growth reaches until the soft ceiling is unlocked. */
  public int getSoftCeiling() {
    return softCeiling;
  }

  /** The highest target: the policy's, within the transfer's caps. */
  public int getMaxTarget() {
    return maxTarget;
  }

  /** Whether enough was delivered fast enough to let the target grow past the soft ceiling. */
  public boolean isSoftCeilingUnlocked() {
    return softCeilingUnlocked;
  }

  /** Whether Files.com requires the parts to be sent one at a time. */
  public boolean isSerial() {
    return serial;
  }

  /** The most recent change to the target, or why a window was not measured; null if none yet. */
  public ConcurrencyDecision getLastDecision() {
    return lastDecision;
  }

  /** How many times the target was lowered or a raise withdrawn after a failure, throttling or a rejected warm-up raise. */
  public int getEpoch() {
    return epoch;
  }

  /** How many times the target rose. */
  public int getGrows() {
    return grows;
  }

  /** How many times the target was lowered. */
  public int getShrinks() {
    return shrinks;
  }

  /** How many raised targets were kept. */
  public int getProbesAccepted() {
    return probesAccepted;
  }

  /** How many raised targets were withdrawn. */
  public int getProbesRejected() {
    return probesRejected;
  }

  /** How many windows were judged. */
  public int getMeasuredWindows() {
    return measuredWindows;
  }

  /** How many windows were not judged, because the target was not in flight for enough of them. */
  public int getUnmeasuredWindows() {
    return unmeasuredWindows;
  }

  /** How many answers asked the transfer to slow down (HTTP 429 or 503). */
  public int getThrottles() {
    return throttles;
  }

  /** How many attempts failed in a way that trying again could fix, other than throttling. */
  public int getTransientFailures() {
    return transientFailures;
  }

  /** How long new attempts remain paused after throttling; zero when they are not paused. */
  public Duration getCooldownRemaining() {
    return cooldownRemaining;
  }

  /** The throughput of the last window, in bytes per second; 0 before the first. */
  public double getLastThroughputBytesPerSecond() {
    return lastThroughputBytesPerSecond;
  }

  /** The best throughput of a window, in bytes per second. */
  public double getBestThroughputBytesPerSecond() {
    return bestThroughputBytesPerSecond;
  }

  /** How many requests the last window's latency suggests were queued rather than moving data. */
  public double getQueueEstimate() {
    return queueEstimate;
  }

  /** The time the target was in flight. */
  public Duration getTimeAtTarget() {
    return timeAtTarget;
  }

  /** The time admitted attempts waited for the limiter or the connection pool. */
  public Duration getTimeWaitingForLimiter() {
    return timeWaitingForLimiter;
  }

  /** The time fewer parts than the target were ready to send. */
  public Duration getTimeWithoutReadyPart() {
    return timeWithoutReadyPart;
  }

  /** The time parts were sent one at a time, as Files.com required. */
  public Duration getTimeSendingSerially() {
    return timeSendingSerially;
  }

  /** For a download, the time attempts were writing to the destination rather than receiving. */
  public Duration getTimeWritingDestination() {
    return timeWritingDestination;
  }

  @Override
  public String toString() {
    return "TransferConcurrency{" + mode + ", " + destination + ", startup=" + startup + ", target=" + target + ", active=" + activeAttempts
        + ", peak=" + peakActiveAttempts + ", lastDecision=" + lastDecision + "}";
  }
}
