package com.files.net;

import java.util.List;

// Chooses the size of the parts an upload has not planned yet, from the windows its concurrency
// controller judges, which calls it under its admission lock. It changes the size only while the
// controller is not changing its target: at the target's ceiling, after a raised target was
// withdrawn, or in a warm start's rest, and after the size has been kept for GROWTH_INTERVAL_WINDOWS
// judged windows. Growth is a probe: the parts planned from then on get GROWTH_FACTOR times the size,
// up to the ceiling, and once the first of them has been accepted, each judged window compares its
// throughput with the level before the probe. If none beats it by PROBE_GAIN_PERCENT within
// PROBE_WINDOWS windows, or a failure or throttling interrupts the probe, the size goes back and growth
// holds for HOLD_WINDOWS windows. Every change starts a new size epoch (PartSizes), and the controller's
// windows judge only parts of the current epoch. Parts already planned, sent or waiting to be sent
// again keep their sizes. The values are the reference ones of the adaptive transfer specification.
final class PartSizeFeedback {
  private static final double GROWTH_FACTOR = 2;
  private static final int GROWTH_INTERVAL_WINDOWS = 2;
  private static final double PROBE_GAIN_PERCENT = 10;
  private static final int PROBE_WINDOWS = 2;
  private static final int HOLD_WINDOWS = 4;

  // How a window of a size probe was judged.
  enum Outcome {
    CONTINUING,
    ACCEPTED,
    REJECTED
  }

  private final PartSizes sizes;
  private final long ceiling;
  private int windowsAtSize;
  private int holdWindows;
  private long probeFrom;
  private double probeBaseline;
  private int probeMisses;
  private boolean awaitingProbeParts;
  private int grows;
  private int probesAccepted;
  private int probesRejected;

  private final int growthIntervalWindows;
  private final int probeWindows;
  private final int holdWindowsAfterRejection;

  PartSizeFeedback(PartSizes sizes) {
    this(sizes, GROWTH_INTERVAL_WINDOWS, PROBE_WINDOWS, HOLD_WINDOWS);
  }

  // With other intervals than the reference ones, for testing how they interact with the target's.
  PartSizeFeedback(PartSizes sizes, int growthIntervalWindows, int probeWindows, int holdWindows) {
    this.sizes = sizes;
    this.ceiling = sizes.ceiling();
    this.growthIntervalWindows = growthIntervalWindows;
    this.probeWindows = probeWindows;
    this.holdWindowsAfterRejection = holdWindows;
  }

  // Whether a probe is under way; the controller then judges windows by it alone.
  boolean probing() {
    return probeFrom > 0;
  }

  // Whether the probe's first part has yet to be accepted, until when windows are not measured.
  boolean awaitingProbeParts() {
    return awaitingProbeParts;
  }

  int epoch() {
    return sizes.choice().epoch;
  }

  double probeBaseline() {
    return probeBaseline;
  }

  int grows() {
    return grows;
  }

  int probesAccepted() {
    return probesAccepted;
  }

  int probesRejected() {
    return probesRejected;
  }

  // The first part of the probe's size has been accepted: its windows can start.
  void probePartsArrived() {
    awaitingProbeParts = false;
  }

  // A judged window at the current size, outside a probe.
  void windowJudged() {
    windowsAtSize++;
    if (holdWindows > 0) {
      holdWindows--;
    }
  }

  // Starts a probe with larger parts if the size is due to grow, from a level measured at baseline.
  boolean tryGrow(long now, double throughput, double baseline, List<ConcurrencyController.Decision> decisions) {
    long size = sizes.choice().size;
    if (probing() || holdWindows > 0 || windowsAtSize < growthIntervalWindows || size >= ceiling || baseline <= 0) {
      return false;
    }
    long next = (long) Math.min(ceiling, Math.floor(size * GROWTH_FACTOR));
    if (next <= size) {
      return false;
    }
    probeFrom = size;
    probeBaseline = baseline;
    probeMisses = 0;
    awaitingProbeParts = true;
    windowsAtSize = 0;
    grows++;
    sizes.change(next);
    decide(now, "PART_SIZE_GREW", size, next, decisions, throughput, baseline);
    return true;
  }

  // Judges a window of the probe.
  Outcome judgeProbe(long now, double throughput, List<ConcurrencyController.Decision> decisions) {
    if (throughput >= probeBaseline * (1 + (PROBE_GAIN_PERCENT / 100))) {
      probesAccepted++;
      probeFrom = 0;
      windowsAtSize = 0;
      long size = sizes.choice().size;
      decide(now, "PART_SIZE_PROBE_ACCEPTED", size, size, decisions, throughput, probeBaseline);
      return Outcome.ACCEPTED;
    }
    if (++probeMisses < probeWindows) {
      return Outcome.CONTINUING;
    }
    reject(now, throughput, decisions);
    return Outcome.REJECTED;
  }

  // A failure or throttling interrupted the probe, if one was under way: the size goes back.
  boolean interrupt(long now, List<ConcurrencyController.Decision> decisions) {
    if (!probing()) {
      return false;
    }
    reject(now, 0, decisions);
    return true;
  }

  private void reject(long now, double throughput, List<ConcurrencyController.Decision> decisions) {
    final long previous = sizes.choice().size;
    probesRejected++;
    sizes.change(probeFrom);
    probeFrom = 0;
    awaitingProbeParts = false;
    windowsAtSize = 0;
    holdWindows = holdWindowsAfterRejection;
    decide(now, "PART_SIZE_PROBE_REJECTED", previous, sizes.choice().size, decisions, throughput, probeBaseline);
  }

  private static void decide(long now, String decision, long previous, long size, List<ConcurrencyController.Decision> decisions,
      double throughput, double baseline) {
    if (decisions != null) {
      decisions.add(new ConcurrencyController.Decision(now, null, decision, previous, size, 0, throughput, baseline, 0, null, 0));
    }
  }
}
