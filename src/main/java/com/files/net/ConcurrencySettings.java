package com.files.net;

// The concurrency settings one transfer runs with, after its caps have been applied. The bounds keep
// their order: min <= start <= soft <= max, and min <= plateau <= max.
final class ConcurrencySettings {
  // Static transfers measure windows of this many requests for their progress reports, and decide
  // nothing.
  private static final int STATIC_WINDOW_ATTEMPTS = 4;

  final ConcurrencyMode mode;
  // The policy of an automatic transfer; null for a static one.
  final ConcurrencyTuning tuning;
  final UploadDestination destination;
  final UploadStartup startup;
  final int start;
  final int min;
  final int soft;
  final int max;
  final int plateau;
  // The lowest target a latency backoff lowers it to.
  final int latencyFloor;
  // The fast-link probe floor, within max, or 0 for none.
  final int probeFloor;
  // The lowest target a withdrawn raise's shrink lowers it to.
  final int throughputFloor;

  private ConcurrencySettings(ConcurrencyMode mode, ConcurrencyTuning tuning, UploadDestination destination, UploadStartup startup,
      int start, int min, int soft, int max, int plateau, int latencyFloor, int probeFloor, int throughputFloor) {
    this.mode = mode;
    this.tuning = tuning;
    this.destination = destination;
    this.startup = startup;
    this.start = start;
    this.min = min;
    this.soft = soft;
    this.max = max;
    this.plateau = plateau;
    this.latencyFloor = latencyFloor;
    this.probeFloor = probeFloor;
    this.throughputFloor = throughputFloor;
  }

  // A transfer that sends concurrency parts at once.
  static ConcurrencySettings fixed(int concurrency, UploadDestination destination, UploadStartup startup) {
    return new ConcurrencySettings(ConcurrencyMode.STATIC, null, destination, startup, concurrency, concurrency, concurrency, concurrency,
        concurrency, concurrency, 0, concurrency);
  }

  // An automatic transfer following tuning within cap, the most parts it may send at once, or 0 for
  // no cap of its own.
  static ConcurrencySettings automatic(ConcurrencyTuning tuning, int cap, UploadStartup startup) {
    int max = Math.min(tuning.maxTarget, cap > 0 ? cap : Integer.MAX_VALUE);
    int min = Math.min(tuning.minTarget, max);
    int latencyFloor = Math.max(min, Math.min(tuning.latencyFloorTarget > 0 ? tuning.latencyFloorTarget : min, max));
    int probeFloor = tuning.probeFloorTarget > 0 && tuning.probeFloorBytesPerSecond > 0 ? Math.max(min, Math.min(tuning.probeFloorTarget, max))
        : 0;
    int throughputFloor = Math.max(min, Math.min(tuning.throughputFloorTarget > 0 ? tuning.throughputFloorTarget : min, max));
    return new ConcurrencySettings(ConcurrencyMode.AUTOMATIC, tuning, tuning.destination, startup, Math.min(tuning.startTarget, max), min,
        Math.min(tuning.softCeiling, max), max, Math.min(tuning.plateauTarget, max), latencyFloor, probeFloor, throughputFloor);
  }

  boolean adaptive() {
    return mode == ConcurrencyMode.AUTOMATIC;
  }

  int windowAttempts() {
    return tuning != null ? tuning.windowAttempts : STATIC_WINDOW_ATTEMPTS;
  }

  // Whether the transfer warm-starts (ConcurrencyController): an automatic upload to S3 whose startup
  // is the default. Otherwise it starts at start.
  boolean warmStarts() {
    return adaptive() && destination == UploadDestination.S3 && startup == UploadStartup.DEFAULT;
  }
}
