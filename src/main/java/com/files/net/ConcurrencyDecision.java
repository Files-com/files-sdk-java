package com.files.net;

/**
 * A change an automatic transfer made to how many parts it sends at once, or why it made none, as
 * {@link TransferConcurrency#getLastDecision()} reports it.
 */
public enum ConcurrencyDecision {
  /** The target rose by the policy's step, as a probe the next windows judge. */
  GREW,
  /** A raised target delivered the gain it needed and was kept. */
  PROBE_ACCEPTED,
  /** A raised target did not deliver the gain it needed, and was withdrawn. */
  PROBE_REJECTED,
  /** Above an unlocked soft ceiling, a raised target lost no more than the policy tolerates, and was kept. */
  PROBE_KEPT_WITHIN_LOSS_TOLERANCE,
  /** A temporary failure lowered the target. */
  SHRANK_AFTER_FAILURE,
  /** Throttling lowered the target. */
  SHRANK_AFTER_THROTTLING,
  /** Requests queued while throughput fell, which lowered the target. */
  SHRANK_FOR_LATENCY,
  /** Throttling paused new requests. */
  COOLDOWN_STARTED,
  /** Requests queued, which held growth. */
  GROWTH_HELD_FOR_LATENCY,
  /** Enough was delivered fast enough to let the target grow past the soft ceiling. */
  SOFT_CEILING_UNLOCKED,
  /**
   * A window was not judged: for too much of it, fewer requests than the target were being sent,
   * because of the shared limiter or connection pool, a lack of ready parts, or parts sent one at a
   * time.
   */
  WINDOW_NOT_MEASURED,
  /** A default startup added parts after measured answers showed they help. */
  WARM_START_RAISED,
  /** A default startup measured a fast link and went to the policy's start. */
  WARM_START_FAST_LINK,
  /** A default startup's raised target did not help, and went back, to rest before trying again. */
  WARM_START_SETTLED
}
