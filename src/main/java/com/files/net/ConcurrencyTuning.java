package com.files.net;

import java.util.concurrent.TimeUnit;

// The policy an automatic transfer's concurrency controller follows, for the kind of destination its
// parts go to. Immutable. The values are those of the reference presets the adaptive transfer
// specification (verification/adaptive-upload-spec.md) describes: scaledS3() for S3, and agent() for
// a Files Agent. Rates are in bytes per second; percentages are of the current target or throughput.
final class ConcurrencyTuning {
  // The most parts or ranges any transfer sends at once.
  static final int MAX_PARTS = 1024;

  private static final long MIB = 1024L * 1024;

  final UploadDestination destination;
  // The target a transfer starts with, the lowest failures can lower it to, the highest growth
  // reaches until softCeilingUnlockBytes have been delivered and a window has measured
  // softCeilingUnlockBytesPerSecond, and the highest.
  final int startTarget;
  final int minTarget;
  final int softCeiling;
  final int maxTarget;
  final long softCeilingUnlockBytes;
  final double softCeilingUnlockBytesPerSecond;
  // A window holds at least this many answers; after growthIntervalWindows judged windows the target
  // rises by growthStep.
  final int windowAttempts;
  final int growthIntervalWindows;
  final int growthStep;
  // A raise is kept when a judged window beats the level it came from by probeGainPercent, or, above
  // plateauTarget, by probeGainPerAddedPartPercent for each part added; it is withdrawn after
  // probeWindows judged windows that do not.
  final double probeGainPercent;
  final int plateauTarget;
  final double probeGainPerAddedPartPercent;
  final int probeWindows;
  // A temporary failure lowers the target by shrinkPercent; growth then holds for holdWindows.
  final double shrinkPercent;
  final int holdWindows;
  // A queue estimate above latencyGrowthQueueLimit holds growth; above latencyBackoffQueueLimit, with
  // throughput falling, it lowers the target by latencyShrinkPercent, down to latencyFloorTarget.
  final double latencyGrowthQueueLimit;
  final double latencyBackoffQueueLimit;
  final double latencyShrinkPercent;
  final int latencyFloorTarget;
  // Once a judged window has reached probeFloorBytesPerSecond, the link is fast: raises up to
  // probeFloorTarget need no gain, and backoff stops there. 0 for none.
  final int probeFloorTarget;
  final double probeFloorBytesPerSecond;
  // Above an unlocked soft ceiling, up to the plateau, a raise losing no more than this is kept.
  final double probeLossTolerancePercent;
  // A withdrawn raise lowers the target by this much from the raised target, down to
  // throughputFloorTarget, instead of going back to where it came from; negative for none.
  final double probeRejectionShrinkPercent;
  final int throughputFloorTarget;
  // Throttling lowers the target by backpressureShrinkPercent and pauses new requests for
  // backpressurePauseNanos, or the server's Retry-After when that is longer.
  final double backpressureShrinkPercent;
  final long backpressurePauseNanos;

  ConcurrencyTuning(UploadDestination destination, int startTarget, int minTarget, int softCeiling, int maxTarget,
      long softCeilingUnlockBytes, double softCeilingUnlockBytesPerSecond, int windowAttempts, int growthIntervalWindows, int growthStep,
      double probeGainPercent, int plateauTarget, double probeGainPerAddedPartPercent, int probeWindows, double shrinkPercent,
      int holdWindows, double latencyGrowthQueueLimit, double latencyBackoffQueueLimit, double latencyShrinkPercent,
      int latencyFloorTarget, int probeFloorTarget, double probeFloorBytesPerSecond, double probeLossTolerancePercent,
      double probeRejectionShrinkPercent, int throughputFloorTarget, double backpressureShrinkPercent, long backpressurePauseNanos) {
    this.destination = destination;
    this.startTarget = startTarget;
    this.minTarget = minTarget;
    this.softCeiling = softCeiling;
    this.maxTarget = maxTarget;
    this.softCeilingUnlockBytes = softCeilingUnlockBytes;
    this.softCeilingUnlockBytesPerSecond = softCeilingUnlockBytesPerSecond;
    this.windowAttempts = windowAttempts;
    this.growthIntervalWindows = growthIntervalWindows;
    this.growthStep = growthStep;
    this.probeGainPercent = probeGainPercent;
    this.plateauTarget = plateauTarget;
    this.probeGainPerAddedPartPercent = probeGainPerAddedPartPercent;
    this.probeWindows = probeWindows;
    this.shrinkPercent = shrinkPercent;
    this.holdWindows = holdWindows;
    this.latencyGrowthQueueLimit = latencyGrowthQueueLimit;
    this.latencyBackoffQueueLimit = latencyBackoffQueueLimit;
    this.latencyShrinkPercent = latencyShrinkPercent;
    this.latencyFloorTarget = latencyFloorTarget;
    this.probeFloorTarget = probeFloorTarget;
    this.probeFloorBytesPerSecond = probeFloorBytesPerSecond;
    this.probeLossTolerancePercent = probeLossTolerancePercent;
    this.probeRejectionShrinkPercent = probeRejectionShrinkPercent;
    this.throughputFloorTarget = throughputFloorTarget;
    this.backpressureShrinkPercent = backpressureShrinkPercent;
    this.backpressurePauseNanos = backpressurePauseNanos;
  }

  // The tuning for a destination: the scaled S3 policy, or the Files Agent's.
  static ConcurrencyTuning forDestination(UploadDestination destination) {
    return destination == UploadDestination.FILES_AGENT ? agent() : scaledS3();
  }

  // Starts high and probes far: 150 parts at once, which is also the soft ceiling, up to 1024 once
  // 64 GiB have been delivered and a window has measured 96 MiB/s.
  static ConcurrencyTuning scaledS3() {
    return new ConcurrencyTuning(UploadDestination.S3, 150, 8, 150, MAX_PARTS, 64L << 30, 96 * MIB, 32, 1, 4, 1, 200, 0.15, 2, 35, 1, 96,
        160, 8, 50, 150, 96 * MIB, 2, 8, 50, 10, 0);
  }

  // A Files Agent: from 2 parts at once, up to 8.
  static ConcurrencyTuning agent() {
    return new ConcurrencyTuning(UploadDestination.FILES_AGENT, 2, 1, 4, 8, 1L << 30, 16 * MIB, 4, 2, 1, 10, 4, 5, 2, 50, 3, 1, 2, -1, 0, 0,
        0, 0, -1, 0, 50, TimeUnit.SECONDS.toNanos(2));
  }
}
