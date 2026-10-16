package com.files.net;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

// Chooses a transfer's concurrency target from what its network attempts measure, and keeps the
// counts that explain it. It is not thread-safe: AttemptAdmission calls it under its lock with the
// time of each event, in nanoseconds of a monotonic clock, so every decision sees one consistent
// state. It never waits and never calls out. This is the reference controller the adaptive transfer
// specification (verification/adaptive-upload-spec.md) describes, for one transfer's own scope.
//
// Measuring. Successful attempts are grouped into windows at one target, each of at least the
// policy's window of completions, one for each request of the target, and lasting at least their
// mean duration. A window's throughput is the bytes they delivered divided by the window's elapsed
// time, never by the sum of their overlapping durations, and its latency is their total duration per
// byte, which puts parts of different sizes on one scale. Comparing that latency with the lowest seen
// gives an estimate of how many requests were queued rather than moving data. A window is judged only
// if the transfer had its target in flight for at least 75% of it: time spent waiting for the shared
// limiter or connection pool, for a part to be read, or sending one part at a time shows nothing about
// what more connections would do, so such a window neither grows the target nor counts against a
// probe. Attempts that complete while new requests are paused belong to the time before the pause and
// are not measured.
//
// Deciding. After the policy's growth interval of judged windows at a target, the target rises by its
// growth step, up to the soft ceiling until the transfer has delivered enough fast enough to unlock
// it, then up to the maximum. Each rise is a probe: within the policy's probe windows its throughput
// must beat the mean of the windows at the target it came from by the required gain, or the target
// goes back (or, with a rejection shrink, lower) and growth holds. A throttling answer pauses new
// attempts and lowers the target; other temporary failures lower it too. Each lowering starts a new
// epoch, and only an attempt started in the current epoch can lower it again, so a burst of failures
// from requests already in flight counts once.
//
// Fast links (the scaled S3 policy). Once a judged window has reached the policy's probe-floor rate,
// raises up to the probe floor need no gain, and neither a rejected probe's shrink nor a latency
// backoff takes the target below it. Above an unlocked soft ceiling, up to the plateau, a probe that
// loses no more than the tolerated loss is kept.
//
// Warm start (an automatic upload to S3 whose startup is the default: ConcurrencySettings.warmStarts).
// The scaled S3 policy starts at 150 parts, which can flood a slow link, so it starts at one instead
// and grows from acknowledged work. The first window warms its connection; the second establishes the
// baseline. A raise adds half the target, rounded up, or one part after a rest. Its first judged window
// opens after the attempts that first filled the raised target have ended (settledWindow); after a full
// policy window of answers, it can judge without that cohort, so a request that never answers does not
// hold growth forever. A raise is kept only when the added parts deliver at least half of what each
// previous part delivered and their estimated queue is less than half the number added; otherwise the
// target goes back and rests for the policy's hold and growth interval before trying one more part.
// Judged warm windows hold at least two answers, and more when their measured variation obscures the
// gain or queue being judged, up to the policy's window: a sampling heuristic, not a confidence
// guarantee. Comparisons use median seconds per byte, against the fastest window judged at the target
// compared with. Any judged window reaching the probe-floor rate promotes straight to the policy's
// start, as does reaching it by measured raises; the ordinary policy and its minimum apply from then
// on. Failures and throttling use normal backoff, with a minimum of one during the warm start.
//
// Part sizes. When the upload chooses its part sizes, a PartSizeFeedback changes the size of parts not
// yet planned, and only one of the two changes at a time: a size probe is judged by the windows alone,
// and the size grows only when the target cannot (at its ceiling, holding after a withdrawn raise, or
// in a warm rest's hold windows). Windows then count only parts sized in the current size epoch,
// starting once the first part of a probe's size has been accepted, and a failure or throttling ends
// a size probe. A static transfer judges its windows for the size alone.
//
// Shared scopes (TransferScope). The uploads of a scope that choose their sizes attach their feedback
// (attachSizing), and one of them at a time, the earliest attached still uploading, has its sizes
// judged, so that one size probe at a time changes one file's parts. Its sizes are judged by its own
// parts alone, in windows of their own (addToSizeWindow): what its requests delivered per second of
// their own time, which its changing share of the target does not move, and only while the shared
// target is settled (at its ceiling, or held after a withdrawn raise), against a level measured at
// that same target. The scope's windows, which hold every file's parts, neither judge nor mature any
// file's size, so a warm start's rests do not either.
//
// Local writes. A download's attempt writing what it received to its destination moves no bytes over
// the network meanwhile (local): time the requests on the network are fewer than the target because
// of such writes is not time at target, and is integrated, never added up.
final class ConcurrencyController {
  // A window is judged only if the transfer had its target in flight for at least this share of it.
  private static final double JUDGED_WINDOW_TIME_AT_TARGET = 0.75;

  // The warm start: its first target, and the share of what each part delivered that each part added
  // must deliver for a raise to be kept: 50% more throughput from 1 part to 2, 25% from 2 to 3.
  static final int WARM_START_TARGET = 1;
  private static final double WARM_GAIN_PER_PART_PERCENT = 50;

  // In sizing a warm window, how many spreads of its queue estimate the point half-way between no
  // queue and the queue that keeps no raise is to lie from either: a rule of thumb.
  private static final double WARM_WINDOW_MARGIN = 2;

  // The longest pause a server's Retry-After can start; a longer wait is not retried.
  static final long MAX_PAUSE_NANOS = TimeUnit.MILLISECONDS.toNanos(Integer.MAX_VALUE);

  private static final double NANOS_PER_SECOND = 1e9;

  // Why fewer network attempts than the target were in flight.
  enum BelowTarget {
    NONE,
    SHARED_LIMITER,
    NO_PART_READY,
    SERIAL_PARTS,
    DESTINATION_WRITES
  }

  private final ConcurrencySettings settings;
  private final ConcurrencyTuning tuning;
  // The size feedback the windows judge: a transfer's own, or, in a shared scope, the earliest
  // attached that is still uploading.
  private PartSizeFeedback sizing;
  private final List<PartSizeFeedback> attachedSizing = new ArrayList<PartSizeFeedback>();

  // What AttemptAdmission has admitted: attempts holding a permit, and those of them being sent.
  private int permitted;
  private int inFlight;
  // Attempts in flight that are writing to the local destination now.
  private int local;
  private int peakInFlight;
  private boolean serial;
  private boolean measuring;
  private long lastEvent;

  private int target;
  private int epoch;
  // The lowest target failures and throttling lower it to: the settings' minimum, or 1 while warming.
  private int minTarget;
  private boolean paused;
  private long pausedUntil;

  // The warm start, while warming: whether it rests (in windows of the policy's size, counted by
  // holdWindows and windowsTowardGrowth), the windows judged at its start target (warmWindows), and the
  // throughput of the window it last rose after (warmLevel, 0 for none), at warmFrom.
  private final boolean warmStarted;
  private boolean warming;
  private boolean resting;
  private int warmWindows;
  private double warmLevel;
  private int warmFrom;
  // Whether it has rested since it last kept a raise, so that its next raise adds one part.
  private boolean restedSinceRaise;
  // The fastest window judged at the target since it was set (levelLowest) and at the target the raise
  // under judgment came from (fromLowest), as the median seconds per byte of its answers, 0 for none,
  // and the queue estimate of the window last judged against them (warmQueue).
  private double levelLowest;
  private double fromLowest;
  private double warmQueue;
  // The variation of answers' times over the windows it judged: the squares of each answer's seconds
  // per byte less its window's mean, relative to that mean, and their degrees of freedom.
  private double variationSquares;
  private int variationAnswers;
  // A raised warm target, until it is judged: the number of the attempt that first filled it
  // (settleLast, 0 until one did), how many of the attempts in flight then are still in flight
  // (settleLeft), when the last of them ended (settledAt), and the answers since the raise. Every
  // attempt started has a number, from 1, which it ends with.
  private boolean settling;
  private long settleLast;
  private int settleLeft;
  private long settledAt;
  private int settleAnswers;
  private long started;
  private boolean unlocked;
  private long acceptedBytes;
  private double lowestSecondsPerByte;

  // The target's level: the judged windows measured at it, and how many more it needs to grow.
  private double levelThroughputSum;
  private int levelWindows;
  private int windowsTowardGrowth;
  private int holdWindows;
  // Whether growth holds because a raised target was withdrawn: a plateau where larger parts may help.
  private boolean holdingAtPlateau;

  // The probe under way, if probeFrom is not 0: the target it came from and that target's throughput.
  private int probeFrom;
  private double probeBaseline;
  private int probeMisses;

  // The current window, from windowStart, and the start of the window last closed, with that window's
  // answers' seconds per byte and their median, which only the warm start uses.
  private long windowStart;
  private long closedWindowStart;
  private List<Double> closedPerByte = new ArrayList<Double>();
  private double lastMedianPerByte;
  private int windowAttempts;
  private long windowBytes;
  private double windowAttemptSeconds;
  // While warming, the seconds per byte of each answer of any bytes.
  private List<Double> windowPerByte = new ArrayList<Double>();
  private double windowInFlightNanos;
  private final long[] windowNanosBelowTarget = new long[BelowTarget.values().length];

  // In a shared scope, the window of the file whose sizes are judged, from sizeWindowStart, with the
  // time in it that the shared target was in flight, and the level of its current size, measured at
  // sizeLevelTarget.
  private long sizeWindowStart;
  private int sizeWindowAttempts;
  private long sizeWindowBytes;
  private double sizeWindowAttemptSeconds;
  private long sizeWindowNanosAtTarget;
  private double sizeLevelSum;
  private int sizeLevelWindows;
  private int sizeLevelTarget;

  // Totals for snapshots.
  private final long[] nanosBelowTarget = new long[BelowTarget.values().length];
  // The best throughput of a judged window: what makes the link fast for the probe floor.
  private double bestJudgedThroughput;
  private ConcurrencyDecision lastDecision;
  private int grows;
  private int shrinks;
  private int probesAccepted;
  private int probesRejected;
  private int measuredWindows;
  private int unmeasuredWindows;
  private int throttles;
  private int transientFailures;
  private double lastThroughput;
  private double bestThroughput;
  private double lastSecondsPerByte;
  private double lastAverageInFlight;
  private double queueEstimate;

  // warmStart: whether to warm-start, for settings that do (ConcurrencySettings.warmStarts).
  ConcurrencyController(ConcurrencySettings settings, PartSizeFeedback sizing, boolean warmStart) {
    this.settings = settings;
    this.tuning = settings.tuning;
    this.sizing = sizing;
    target = settings.start;
    minTarget = settings.min;
    warmStarted = warmStart && settings.adaptive() && settings.start > WARM_START_TARGET && tuning.probeFloorBytesPerSecond > 0;
    if (warmStarted) {
      warming = true;
      target = WARM_START_TARGET;
      minTarget = 1;
    }
  }

  int target() {
    return target;
  }

  boolean warming() {
    return warming;
  }

  // Whether it started with a warm start, under way or over.
  boolean warmStarted() {
    return warmStarted;
  }

  int permitted() {
    return permitted;
  }

  // Attempts started and not yet ended.
  int inFlight() {
    return inFlight;
  }

  // Attempts started now belong to this epoch.
  int epoch() {
    return epoch;
  }

  boolean isPaused(long now) {
    return paused && pausedUntil - now > 0;
  }

  // When the pause ends; meaningful only while isPaused.
  long pausedUntil() {
    return pausedUntil;
  }

  void granted(long now) {
    if (!measuring) {
      // Measuring starts with the first attempt, not with negotiating and reading the first parts.
      measuring = true;
      lastEvent = now;
      openWindow(now);
    }
    advance(now);
    permitted++;
  }

  // Returns the attempt's number, which it gives released when it ends.
  long sending(long now) {
    advance(now);
    inFlight++;
    peakInFlight = Math.max(peakInFlight, inFlight);
    started++;
    if (settling && settleLast == 0 && inFlight >= target) {
      settleLast = started;
      settleLeft = inFlight;
    }
    return started;
  }

  // attempt: the number sending gave the attempt that was sending, or 0 if it was not.
  void released(long now, boolean wasSending, long attempt) {
    advance(now);
    permitted--;
    if (wasSending) {
      inFlight--;
      if (settling && attempt > 0 && attempt <= settleLast && settleLeft > 0 && --settleLeft == 0) {
        settledAt = now;
      }
    }
  }

  // A file of a shared scope that chooses its own part sizes joins (attachSizing) or leaves
  // (detachSizing) the files whose sizes may be judged. The first to join has its sizes judged until it
  // leaves; then the next. A probe of a file that leaves is over, and the next file's level starts.
  void attachSizing(long now, PartSizeFeedback sizer) {
    advance(now);
    attachedSizing.add(sizer);
    if (sizing == null) {
      sizing = sizer;
      resetSizeLevel(now);
    }
  }

  void detachSizing(long now, PartSizeFeedback sizer) {
    advance(now);
    attachedSizing.remove(sizer);
    if (sizing == sizer) {
      sizing = attachedSizing.isEmpty() ? null : attachedSizing.get(0);
      resetSizeLevel(now);
    }
  }

  // An attempt in flight starts (writing) or stops writing what it received to the local destination.
  void local(long now, boolean writing) {
    advance(now);
    local += writing ? 1 : -1;
  }

  // The transfer now sends one part at a time, as Files.com requires.
  void useSerial(long now) {
    advance(now);
    serial = true;
  }

  // Takes one attempt's outcome into account. attemptEpoch is the epoch it started in. Decisions are
  // added to decisions when it is not null.
  void record(long now, int attemptEpoch, Sample sample, List<Decision> decisions) {
    advance(now);
    if (sample.throttled) {
      throttles++;
    } else if (!sample.accepted && sample.transientFailure) {
      transientFailures++;
    }
    if (warming && !sample.accepted && (sample.throttled || sample.transientFailure)) {
      // The warm start rests from where the failure leaves it, and the failure is handled as any other.
      rest();
    }
    if (sample.accepted) {
      acceptedBytes += sample.bytes;
      if (settling) {
        settleAnswers++;
      }
      addToWindow(now, sample, decisions);
    } else if (settings.adaptive() && sample.throttled) {
      throttled(now, attemptEpoch, sample.retryAfterNanos, decisions);
    } else if (settings.adaptive() && sample.transientFailure && attemptEpoch == epoch) {
      int previous = target;
      if (lower(tuning.shrinkPercent, 0)) {
        restart(now);
        decide(now, ConcurrencyDecision.SHRANK_AFTER_FAILURE, previous, decisions, 0, 0, BelowTarget.NONE, 0);
      }
    }
    // A failure disturbs what a size probe measures: the size goes back.
    if (!sample.accepted && (sample.throttled || sample.transientFailure) && sizing != null && sizing.interrupt(now, decisions)) {
      resetLevel(now);
      if (!attachedSizing.isEmpty()) {
        resetSizeLevel(now);
      }
    }
  }

  TransferConcurrency snapshot(long now) {
    return snapshot(now, false);
  }

  // shared: whether the transfers of a scope share the controller.
  TransferConcurrency snapshot(long now, boolean shared) {
    advance(now);
    return new TransferConcurrency(settings.mode, settings.destination, settings.startup, shared, warming, target, serial ? 1 : target, inFlight,
        peakInFlight, minTarget, settings.soft, settings.max, unlocked, serial, lastDecision, epoch, grows, shrinks, probesAccepted,
        probesRejected, measuredWindows, unmeasuredWindows, throttles, transientFailures,
        Duration.ofNanos(isPaused(now) ? pausedUntil - now : 0), lastThroughput, bestThroughput, queueEstimate,
        Duration.ofNanos(nanosBelowTarget[BelowTarget.NONE.ordinal()]), Duration.ofNanos(nanosBelowTarget[BelowTarget.SHARED_LIMITER.ordinal()]),
        Duration.ofNanos(nanosBelowTarget[BelowTarget.NO_PART_READY.ordinal()]), Duration.ofNanos(nanosBelowTarget[BelowTarget.SERIAL_PARTS.ordinal()]),
        Duration.ofNanos(nanosBelowTarget[BelowTarget.DESTINATION_WRITES.ordinal()]));
  }

  // Adds the time since the last event to the reason the transfer had for being where it was.
  private void advance(long now) {
    if (!measuring || now - lastEvent <= 0) {
      return;
    }
    long from = lastEvent;
    lastEvent = now;
    int reason = currentReason().ordinal();
    nanosBelowTarget[reason] += now - from;
    long windowFrom = later(from, windowStart);
    if (now - windowFrom > 0) {
      windowNanosBelowTarget[reason] += now - windowFrom;
      windowInFlightNanos += (inFlight - local) * (double) (now - windowFrom);
    }
    long sizeWindowFrom = later(from, sizeWindowStart);
    if (!attachedSizing.isEmpty() && reason == BelowTarget.NONE.ordinal() && now - sizeWindowFrom > 0) {
      sizeWindowNanosAtTarget += now - sizeWindowFrom;
    }
  }

  private BelowTarget currentReason() {
    if (serial) {
      return BelowTarget.SERIAL_PARTS;
    }
    if (inFlight - local >= target) {
      return BelowTarget.NONE;
    }
    if (inFlight >= target) {
      return BelowTarget.DESTINATION_WRITES;
    }
    return permitted > inFlight ? BelowTarget.SHARED_LIMITER : BelowTarget.NO_PART_READY;
  }

  private void addToWindow(long now, Sample sample, List<Decision> decisions) {
    if (!attachedSizing.isEmpty()) {
      // A shared scope's windows measure every file's parts as they are; the parts of the file whose
      // sizes are judged also count in its own window.
      if (sizing != null && sample.sizer == sizing) {
        addToSizeWindow(now, sample, decisions);
      }
    } else if (sizing != null && sample.sizeEpoch != sizing.epoch()) {
      // A part of an earlier size, prepared, sent or retried before the size changed. Its bytes count
      // toward the transfer, but windows judge only parts of the current size: the window starts again
      // after it, since it shared the network with the window's parts.
      if (!sizing.awaitingProbeParts()) {
        openWindow(now);
      }
      return;
    } else if (sizing != null && sizing.awaitingProbeParts()) {
      // The probe's first part: its windows start with it, since its own time was mostly spent before.
      sizing.probePartsArrived();
      openWindow(now);
      return;
    }
    if (now - windowStart <= 0) {
      return;
    }
    windowAttempts++;
    windowBytes += sample.bytes;
    double seconds = sample.durationNanos / NANOS_PER_SECOND;
    windowAttemptSeconds += seconds;
    if (warming && sample.bytes > 0) {
      windowPerByte.add(seconds / sample.bytes);
    }
    // A window spans a full round of the target's requests: at least one completion for each of them,
    // and at least as long as they take on average. Requests started together finish together, so a
    // shorter window would measure how they bunch up, not how fast they deliver. While warming, a
    // window is one completion per request of the target, except while the warm start rests; a window
    // it judges holds as many more as warmWindowAnswers calls for, but still closes on any answer that
    // leaves it showing a fast link, so that the first answers that show one still decide.
    double elapsed = (now - windowStart) / NANOS_PER_SECOND;
    if (windowAttempts >= (warming && !resting ? target : Math.max(settings.windowAttempts(), target))
        && elapsed >= windowAttemptSeconds / windowAttempts
        && (!warming || resting || windowAttempts >= warmWindowAnswers() || windowBytes / elapsed >= tuning.probeFloorBytesPerSecond)) {
      closeWindow(now, decisions);
    }
  }

  private void closeWindow(long now, List<Decision> decisions) {
    long nanos = now - windowStart;
    final double throughput = windowBytes / (nanos / NANOS_PER_SECOND);
    double averageInFlight = windowInFlightNanos / nanos;
    if (windowBytes > 0 && windowAttemptSeconds > 0) {
      lastSecondsPerByte = windowAttemptSeconds / windowBytes;
      lowestSecondsPerByte = lowestSecondsPerByte > 0 ? Math.min(lowestSecondsPerByte, lastSecondsPerByte) : lastSecondsPerByte;
      queueEstimate = averageInFlight * (1 - (lowestSecondsPerByte / lastSecondsPerByte));
      lastAverageInFlight = averageInFlight;
    }
    final BelowTarget shortfall = shortfall(nanos);
    boolean anyBytes = windowBytes > 0;
    closedWindowStart = windowStart;
    if (warming) {
      List<Double> closed = windowPerByte;
      windowPerByte = closedPerByte;
      closedPerByte = closed;
      lastMedianPerByte = median(closedPerByte);
    }
    openWindow(now);
    if (!anyBytes) {
      return;
    }
    lastThroughput = throughput;
    bestThroughput = Math.max(bestThroughput, throughput);
    if (!settings.adaptive()) {
      if (sizing != null && shortfall == BelowTarget.NONE) {
        judgeSize(now, throughput, decisions);
      }
      return;
    }
    if (shortfall != BelowTarget.NONE) {
      unmeasuredWindows++;
      decide(now, ConcurrencyDecision.WINDOW_NOT_MEASURED, target, decisions, throughput, 0, shortfall, 0);
      return;
    }
    measuredWindows++;
    judge(now, throughput, decisions);
  }

  // Why the window cannot be judged, or NONE if the transfer had its target in flight for enough of it.
  private BelowTarget shortfall(long nanos) {
    if (windowNanosBelowTarget[BelowTarget.NONE.ordinal()] >= JUDGED_WINDOW_TIME_AT_TARGET * nanos) {
      return BelowTarget.NONE;
    }
    BelowTarget longest = BelowTarget.SHARED_LIMITER;
    for (BelowTarget reason : new BelowTarget[]{BelowTarget.NO_PART_READY, BelowTarget.SERIAL_PARTS, BelowTarget.DESTINATION_WRITES}) {
      if (windowNanosBelowTarget[reason.ordinal()] > windowNanosBelowTarget[longest.ordinal()]) {
        longest = reason;
      }
    }
    return longest;
  }

  private void judge(long now, double throughput, List<Decision> decisions) {
    if (!unlocked && settings.soft < settings.max && acceptedBytes >= tuning.softCeilingUnlockBytes
        && throughput >= tuning.softCeilingUnlockBytesPerSecond) {
      unlocked = true;
      decide(now, ConcurrencyDecision.SOFT_CEILING_UNLOCKED, target, decisions, throughput, 0, BelowTarget.NONE, 0);
    }
    bestJudgedThroughput = Math.max(bestJudgedThroughput, throughput);
    if (warming) {
      judgeWarm(now, throughput, decisions);
      return;
    }
    if (sizing != null && sizing.probing()) {
      // A shared scope's file judges its probe in its own windows (addToSizeWindow); the target holds.
      if (attachedSizing.isEmpty()) {
        judgeSizeProbe(now, throughput, decisions);
      }
      return;
    }

    if (probeFrom > 0) {
      if (throughput >= probeBaseline * (1 + (requiredGainPercent(target, probeFrom) / 100))) {
        probesAccepted++;
        decide(now, ConcurrencyDecision.PROBE_ACCEPTED, target, decisions, throughput, probeBaseline, BelowTarget.NONE, 0);
        probeFrom = 0;
      } else if (withinLossTolerance(throughput)) {
        // Above the unlocked soft ceiling, a raise that costs no more than the tolerance is kept.
        probesAccepted++;
        decide(now, ConcurrencyDecision.PROBE_KEPT_WITHIN_LOSS_TOLERANCE, target, decisions, throughput, probeBaseline, BelowTarget.NONE, 0);
        probeFrom = 0;
      } else if (++probeMisses < tuning.probeWindows) {
        return;
      } else {
        // More connections did not help: go back to the level they were added to (or, with a rejection
        // shrink, lower by that much, down to the floor), and wait before trying again.
        final int previous = target;
        final double baseline = probeBaseline;
        target = rejectedProbeTarget();
        probesRejected++;
        shrinks++;
        restart(now);
        holdingAtPlateau = true;
        levelThroughputSum = baseline;
        levelWindows = 1;
        decide(now, ConcurrencyDecision.PROBE_REJECTED, previous, decisions, throughput, baseline, BelowTarget.NONE, 0);
        return;
      }
    }

    double level = levelWindows > 0 ? levelThroughputSum / levelWindows : 0;
    if (tuning.latencyBackoffQueueLimit > 0 && queueEstimate > tuning.latencyBackoffQueueLimit && levelWindows > 0
        && throughput < level * (1 - (Math.max(tuning.probeGainPercent, 1) / 100)) && !(fastLink() && target <= settings.probeFloor)) {
      int previous = target;
      double shrink = tuning.latencyShrinkPercent >= 0 ? tuning.latencyShrinkPercent : tuning.shrinkPercent;
      if (lower(shrink, Math.max(settings.latencyFloor, fastLink() ? settings.probeFloor : 0))) {
        restart(now);
        decide(now, ConcurrencyDecision.SHRANK_FOR_LATENCY, previous, decisions, throughput, level, BelowTarget.NONE, 0);
        return;
      }
    }

    levelThroughputSum += throughput;
    levelWindows++;
    if (sizing != null && attachedSizing.isEmpty()) {
      sizing.windowJudged();
    }
    if (holdWindows > 0) {
      holdWindows--;
      if (holdingAtPlateau) {
        tryGrowParts(now, throughput, decisions);
      }
      return;
    }
    holdingAtPlateau = false;
    if (tuning.latencyGrowthQueueLimit > 0 && queueEstimate > tuning.latencyGrowthQueueLimit) {
      windowsTowardGrowth = 0;
      decide(now, ConcurrencyDecision.GROWTH_HELD_FOR_LATENCY, target, decisions, throughput, level, BelowTarget.NONE, 0);
      return;
    }
    int ceiling = unlocked ? settings.max : settings.soft;
    if (++windowsTowardGrowth < tuning.growthIntervalWindows) {
      return;
    }
    if (target >= ceiling) {
      tryGrowParts(now, throughput, decisions);
      return;
    }

    int from = target;
    probeBaseline = levelThroughputSum / levelWindows;
    probeMisses = 0;
    target = (int) Math.min(ceiling, (long) from + tuning.growthStep);
    // Below the probe floor of a fast link, a raise needs no gain: it is not a probe.
    probeFrom = fastLink() && target <= settings.probeFloor ? 0 : from;
    grows++;
    levelThroughputSum = 0;
    levelWindows = 0;
    windowsTowardGrowth = 0;
    openWindow(now);
    decide(now, ConcurrencyDecision.GREW, from, decisions, throughput, probeBaseline, BelowTarget.NONE, 0);
  }

  // A static transfer's judged window, for its part size alone.
  private void judgeSize(long now, double throughput, List<Decision> decisions) {
    if (sizing.probing()) {
      judgeSizeProbe(now, throughput, decisions);
      return;
    }
    levelThroughputSum += throughput;
    levelWindows++;
    sizing.windowJudged();
    tryGrowParts(now, throughput, decisions);
  }

  // Starts a size probe from the current level, when the size is due to grow. In a shared scope the
  // file's own windows start it (addToSizeWindow).
  private void tryGrowParts(long now, double throughput, List<Decision> decisions) {
    if (sizing != null && attachedSizing.isEmpty() && levelWindows > 0 && sizing.tryGrow(now, throughput, levelThroughputSum / levelWindows, decisions)) {
      resetLevel(now);
    }
  }

  private void judgeSizeProbe(long now, double throughput, List<Decision> decisions) {
    double baseline = sizing.probeBaseline();
    switch (sizing.judgeProbe(now, throughput, decisions)) {
      case ACCEPTED:
        // The new size's level starts with the window that showed its gain.
        resetLevel(now);
        levelThroughputSum = throughput;
        levelWindows = 1;
        break;
      case REJECTED:
        // The old size's level stands.
        resetLevel(now);
        levelThroughputSum = baseline;
        levelWindows = 1;
        break;
      default:
        break;
    }
  }

  // A part of the file whose sizes a shared scope judges. Its window holds only that file's parts of
  // its current size: at least the policy's window of them, and as long as they take on average. It is
  // measured as what those parts' requests delivered per second of their own time, which the file's
  // share of the target does not change, and judged only if the shared target was in flight for
  // enough of it, and only while the shared target is settled, against a level measured at that same
  // target: when the target moves, or restarts, the file's level starts again. Every change of its
  // size starts the shared target's level again too, since what all files deliver changed.
  private void addToSizeWindow(long now, Sample sample, List<Decision> decisions) {
    if (sample.sizeEpoch != sizing.epoch()) {
      if (!sizing.awaitingProbeParts()) {
        openSizeWindow(now);
      }
      return;
    }
    if (sizing.awaitingProbeParts()) {
      sizing.probePartsArrived();
      openSizeWindow(now);
      return;
    }
    if (now - sizeWindowStart <= 0) {
      return;
    }
    // A size probe holds the shared target until it is judged; outside one, the level belongs to one
    // settled target.
    if (!sizing.probing() && (!targetSettled() || target != sizeLevelTarget)) {
      resetSizeLevel(now);
      sizeLevelTarget = target;
      return;
    }
    sizeWindowAttempts++;
    sizeWindowBytes += sample.bytes;
    sizeWindowAttemptSeconds += sample.durationNanos / NANOS_PER_SECOND;
    if (sizeWindowAttempts < settings.windowAttempts() || (now - sizeWindowStart) / NANOS_PER_SECOND < sizeWindowAttemptSeconds / sizeWindowAttempts) {
      return;
    }
    // What the file's own part requests delivered for each second they took: the files share the
    // target's requests unevenly and changingly, so the file's bytes over the window's time would move
    // with its share, not its sizes.
    double throughput = sizeWindowBytes / sizeWindowAttemptSeconds;
    boolean eligible = sizeWindowNanosAtTarget >= JUDGED_WINDOW_TIME_AT_TARGET * (now - sizeWindowStart);
    openSizeWindow(now);
    if (!eligible) {
      return;
    }
    if (sizing.probing()) {
      double baseline = sizing.probeBaseline();
      switch (sizing.judgeProbe(now, throughput, decisions)) {
        case ACCEPTED:
          sizeLevelSum = throughput;
          sizeLevelWindows = 1;
          resetLevel(now);
          break;
        case REJECTED:
          sizeLevelSum = baseline;
          sizeLevelWindows = 1;
          resetLevel(now);
          break;
        default:
          break;
      }
      return;
    }
    sizeLevelSum += throughput;
    sizeLevelWindows++;
    sizing.windowJudged();
    if (sizing.tryGrow(now, throughput, sizeLevelSum / sizeLevelWindows, decisions)) {
      sizeLevelSum = 0;
      sizeLevelWindows = 0;
      resetLevel(now);
    }
  }

  // The file whose sizes a shared scope judges changed, or its size went back: its level starts again.
  private void resetSizeLevel(long now) {
    sizeLevelSum = 0;
    sizeLevelWindows = 0;
    openSizeWindow(now);
  }

  private void openSizeWindow(long now) {
    sizeWindowStart = isPaused(now) ? pausedUntil : now;
    sizeWindowAttempts = 0;
    sizeWindowBytes = 0;
    sizeWindowAttemptSeconds = 0;
    sizeWindowNanosAtTarget = 0;
  }

  // Whether the shared target is not changing: no raise is being probed, and it is at its ceiling or
  // held after a withdrawn raise.
  private boolean targetSettled() {
    return probeFrom == 0 && (holdingAtPlateau || target >= (unlocked ? settings.max : settings.soft));
  }

  // The size changed: measuring at the level starts over.
  private void resetLevel(long now) {
    levelThroughputSum = 0;
    levelWindows = 0;
    windowsTowardGrowth = 0;
    openWindow(now);
  }

  // The link is fast once a judged window reached the policy's probe-floor rate, with a probe floor set.
  private boolean fastLink() {
    return settings.probeFloor > 0 && bestJudgedThroughput >= tuning.probeFloorBytesPerSecond;
  }

  // A probe that missed its gain is kept when it lost no more than the tolerance, above an unlocked
  // soft ceiling and up to the plateau.
  private boolean withinLossTolerance(double throughput) {
    return tuning.probeLossTolerancePercent > 0 && unlocked && target > settings.soft && target <= settings.plateau
        && throughput >= probeBaseline * (1 - (tuning.probeLossTolerancePercent / 100));
  }

  // Where a rejected probe takes the target: back to the level it came from, or, with a rejection
  // shrink, that much lower than the raised target, down to the throughput floor (the probe floor on a
  // fast link). A rejection only ever lowers load: never above the level the probe came from.
  private int rejectedProbeTarget() {
    if (tuning.probeRejectionShrinkPercent < 0) {
      return probeFrom;
    }
    int floor = Math.min(settings.max, Math.max(settings.throughputFloor, fastLink() ? settings.probeFloor : 0));
    int next = (int) Math.floor(target * (100 - tuning.probeRejectionShrinkPercent) / 100);
    return Math.min(probeFrom, Math.max(floor, Math.min(next, target - 1)));
  }

  // The gain, in percent, a probe from `from` to `to` parts at once must show. Parts added above the
  // plateau must each earn their own gain.
  private double requiredGainPercent(int to, int from) {
    int addedAbovePlateau = to - Math.max(from, settings.plateau);
    return addedAbovePlateau > 0 ? Math.max(tuning.probeGainPercent, addedAbovePlateau * tuning.probeGainPerAddedPartPercent)
        : tuning.probeGainPercent;
  }

  private void throttled(long now, int attemptEpoch, long retryAfterNanos, List<Decision> decisions) {
    long pause = tuning.backpressurePauseNanos;
    if (retryAfterNanos > pause) {
      // The whole wait the server asks for, up to the longest the transfer waits.
      pause = Math.min(retryAfterNanos, MAX_PAUSE_NANOS);
    }
    long until = now + pause;
    if (!paused || until - pausedUntil > 0) {
      paused = true;
      pausedUntil = until;
    }

    int previous = target;
    boolean lowered = attemptEpoch == epoch && lower(tuning.backpressureShrinkPercent, 0);
    // Measuring starts again when the pause ends.
    restart(now);
    decide(now, ConcurrencyDecision.COOLDOWN_STARTED, previous, decisions, 0, 0, BelowTarget.NONE, pause);
    if (lowered) {
      decide(now, ConcurrencyDecision.SHRANK_AFTER_THROTTLING, previous, decisions, 0, 0, BelowTarget.NONE, 0);
    }
  }

  // A judged window of the warm start: a fast link goes to the policy's start. While resting, the
  // policy's hold and growth interval pass first, and the last window is the one the target rises
  // from. Otherwise the start target's first window only warms the connection it opened, and its
  // second judges it, as the baseline the target rises from; a raised target is judged by its first
  // settled window, against the window before the raise and the fastest window judged at the target
  // it came from: kept, and the target raised again, when the parts added delivered at least half of
  // what each part delivered before them and fewer than half of them were queued, and otherwise going
  // back to the target before, to rest there. A window whose requests were queued, against the
  // fastest window judged at its own target, raises nothing: the warm start rests.
  private void judgeWarm(long now, double throughput, List<Decision> decisions) {
    if (throughput >= tuning.probeFloorBytesPerSecond) {
      endWarmStart(now, ConcurrencyDecision.WARM_START_FAST_LINK, decisions, throughput);
      return;
    }
    if (resting) {
      // The target holds while the warm start rests, so the part size may change meanwhile: a size
      // probe's windows judge the size alone and leave the rest where it was, and only the rest's hold
      // windows may start one, never the window that ends the rest.
      if (attachedSizing.isEmpty()) {
        if (sizing != null && sizing.probing()) {
          judgeSizeProbe(now, throughput, decisions);
          return;
        }
        levelThroughputSum += throughput;
        levelWindows++;
        if (sizing != null) {
          sizing.windowJudged();
        }
      }
      judgedAtTarget();
      if (holdWindows > 0) {
        holdWindows--;
        tryGrowParts(now, throughput, decisions);
        return;
      }
      if (++windowsTowardGrowth < tuning.growthIntervalWindows) {
        return;
      }
      resting = false;
      if (queued(levelLowest, warmStep())) {
        rest();
        return;
      }
    } else if (!settling) {
      // The start target's first window only warms the connection it opened: it is neither the
      // baseline nor the fastest window the next raise is compared with.
      if (++warmWindows < 2) {
        return;
      }
      judgedAtTarget();
      if (queued(levelLowest, warmStep())) {
        rest();
        return;
      }
    } else if (!settledWindow()) {
      return;
    } else {
      countVariation();
      int added = target - warmFrom;
      boolean queued = queued(fromLowest, added);
      if (throughput < warmLevel * (1 + (WARM_GAIN_PER_PART_PERCENT / 100 * added / warmFrom)) || queued) {
        final int previous = target;
        final double baseline = warmLevel;
        target = warmFrom;
        shrinks++;
        epoch++;
        restart(now);
        rest();
        decide(now, ConcurrencyDecision.WARM_START_SETTLED, previous, decisions, throughput, baseline, BelowTarget.NONE, 0);
        return;
      }
      // Kept: the window is the level the next raise is judged against.
      restedSinceRaise = false;
      levelLowest = lastMedianPerByte;
    }
    warmLevel = throughput;
    warmFrom = target;
    warmRaise(now, decisions, throughput);
  }

  // A window the warm start judged at its target: it may be the fastest there, which the target's
  // next raise is compared with, and its answers' times count toward their variation.
  private void judgedAtTarget() {
    if (lastMedianPerByte > 0) {
      levelLowest = levelLowest > 0 ? Math.min(levelLowest, lastMedianPerByte) : lastMedianPerByte;
    }
    countVariation();
  }

  // Adds the variation of the answers' times of the window just closed, about their mean.
  private void countVariation() {
    if (closedPerByte.size() >= 2) {
      variationSquares += relativeSquares(closedPerByte);
      variationAnswers += closedPerByte.size() - 1;
    }
  }

  // The squares of the values less their mean, relative to that mean; 0 where they are all the same.
  private static double relativeSquares(List<Double> values) {
    double mean = 0;
    for (double value : values) {
      mean += value / values.size();
    }
    double squares = 0;
    for (double value : values) {
      squares += (value - mean) * (value - mean);
    }
    return squares / (mean * mean);
  }

  // The observed relative variance, inflated where a small sample could understate variation, with
  // the Wilson-Hilferty approximation: a heuristic, not a confidence bound. A single degree of freedom
  // is unbounded; identical answers need no inflation.
  private static double smallSampleVariation(double squares, int freedom) {
    if (squares <= 0) {
      return 0;
    }
    double spread = 2.0 / (9 * freedom);
    double root = 1 - spread - (WARM_WINDOW_MARGIN * Math.sqrt(spread));
    return root <= 0 ? Double.POSITIVE_INFINITY : squares / (freedom * root * root * root);
  }

  // The median of the values, sorted in place; zero for none.
  private static double median(List<Double> values) {
    if (values.isEmpty()) {
      return 0;
    }
    Collections.sort(values);
    int middle = values.size() / 2;
    return values.size() % 2 == 1 ? values.get(middle) : (values.get(middle - 1) + values.get(middle)) / 2;
  }

  // Whether the window's requests were queued, against reference, the median latency of the fastest
  // window judged at the target compared with (0 for none, never queued): the estimate, from how much
  // longer each byte took than in that window, is the work of half the given parts or more, the same
  // share each part added must deliver. Records the estimate for the decision (warmQueue).
  private boolean queued(double reference, int parts) {
    warmQueue = reference > 0 && lastMedianPerByte > 0 ? lastAverageInFlight * (1 - (reference / lastMedianPerByte)) : 0;
    return warmQueue >= WARM_GAIN_PER_PART_PERCENT / 100 * parts;
  }

  // The parts the warm start's next raise adds: half again as many as the target, rounded up, or one
  // after it rested.
  private int warmStep() {
    return restedSinceRaise ? 1 : Math.max(1, (target + 1) / 2);
  }

  // Answers needed to judge a warm window, beyond the target's full round. Cold and unsettled windows
  // close early, so that a settled window can open. Judged windows need at least two answers; the
  // variance heuristic asks for m >= 2 v (raised * margin / (0.5 * parts added))^2, bounded by the
  // policy window. It only adapts the sample size; it promises no confidence level.
  private int warmWindowAnswers() {
    boolean judged = settling ? settleLast > 0 && settleLeft == 0 && windowStart - settledAt >= 0 : warmWindows > 0;
    if (!judged) {
      return 0;
    }
    double squares = variationSquares;
    int freedom = variationAnswers;
    if (windowPerByte.size() >= 2) {
      squares += relativeSquares(windowPerByte);
      freedom += windowPerByte.size() - 1;
    }
    int parts = settling ? target - warmFrom : warmStep();
    int raised = settling ? target : target + parts;
    double ratio = raised * WARM_WINDOW_MARGIN / (WARM_GAIN_PER_PART_PERCENT / 100 * parts);
    double needed = freedom == 0 ? 2 : Math.max(2, Math.ceil(2 * smallSampleVariation(squares, freedom) * ratio * ratio));
    return (int) Math.min(settings.windowAttempts(), needed);
  }

  // Whether the window just closed may judge the raised target: it opened once every attempt in flight
  // when the target first filled had ended, or the target has had a policy window of answers since.
  private boolean settledWindow() {
    return (settleLast > 0 && settleLeft == 0 && closedWindowStart - settledAt >= 0)
        || settleAnswers >= Math.max(settings.windowAttempts(), target);
  }

  // The warm start rests where its target is: windows of the policy's size, its hold windows and then
  // its growth interval of them (restart starts both counts over), before it tries one part more.
  private void rest() {
    resting = true;
    settling = false;
    restedSinceRaise = true;
    warmWindows = 0;
    warmLevel = 0;
    levelLowest = 0;
    holdWindows = tuning.holdWindows;
    windowsTowardGrowth = 0;
    levelThroughputSum = 0;
    levelWindows = 0;
  }

  // Raises the warm start's target by warmStep, up to the policy's start, where the warm start is
  // over. The raised target is judged against the fastest window judged at the target it rose from.
  private void warmRaise(long now, List<Decision> decisions, double throughput) {
    int step = warmStep();
    if (target + step >= settings.start) {
      endWarmStart(now, ConcurrencyDecision.WARM_START_RAISED, decisions, throughput);
      return;
    }
    final int previous = target;
    target += step;
    fromLowest = levelLowest;
    levelLowest = 0;
    grows++;
    settling = true;
    settleLast = 0;
    settleLeft = 0;
    settleAnswers = 0;
    openWindow(now);
    decide(now, ConcurrencyDecision.WARM_START_RAISED, previous, decisions, throughput, 0, BelowTarget.NONE, 0);
  }

  // Ends the warm start at the policy's start, from which the policy carries on, and where failures
  // lower the target only as far as the policy's minimum. A size probe of a rest is over, and the size
  // goes back, as after a failure.
  private void endWarmStart(long now, ConcurrencyDecision decision, List<Decision> decisions, double throughput) {
    warming = false;
    resting = false;
    settling = false;
    final int previous = target;
    target = settings.start;
    minTarget = settings.min;
    grows++;
    restart(now);
    decide(now, decision, previous, decisions, throughput, 0, BelowTarget.NONE, 0);
    if (sizing != null) {
      sizing.interrupt(now, decisions);
    }
  }

  // Lowers the target by percent, by at least one part, but not below floor or the lowest target.
  private boolean lower(double percent, int floor) {
    int lowest = Math.max(floor, minTarget);
    if (percent <= 0 || target <= lowest) {
      return false;
    }
    int next = (int) Math.floor(target * (100 - percent) / 100);
    target = Math.max(lowest, Math.min(next, target - 1));
    epoch++;
    shrinks++;
    return true;
  }

  // After the target was lowered or a probe withdrawn: the level starts over and growth holds.
  private void restart(long now) {
    holdingAtPlateau = false;
    probeFrom = 0;
    levelThroughputSum = 0;
    levelWindows = 0;
    windowsTowardGrowth = 0;
    holdWindows = tuning.holdWindows;
    openWindow(now);
    if (!attachedSizing.isEmpty()) {
      resetSizeLevel(now);
    }
  }

  private void openWindow(long now) {
    windowStart = isPaused(now) ? pausedUntil : now;
    windowAttempts = 0;
    windowBytes = 0;
    windowAttemptSeconds = 0;
    windowPerByte.clear();
    windowInFlightNanos = 0;
    Arrays.fill(windowNanosBelowTarget, 0);
  }

  // The later of two times of a monotonic clock, which may wrap.
  private static long later(long a, long b) {
    return a - b >= 0 ? a : b;
  }

  private void decide(long now, ConcurrencyDecision decision, int previousTarget, List<Decision> decisions, double throughput, double baseline,
      BelowTarget reason, long cooldownNanos) {
    lastDecision = decision;
    if (decisions != null) {
      // A warm start's raise, or a raise withdrawn, by the queue estimate it was judged on.
      double queue = decision == ConcurrencyDecision.WARM_START_RAISED || decision == ConcurrencyDecision.WARM_START_SETTLED ? warmQueue
          : queueEstimate;
      decisions.add(new Decision(now, decision, null, previousTarget, target, epoch, throughput, baseline, queue, reason, cooldownNanos));
    }
  }

  // A decision of the controller about its target, or of its PartSizeFeedback about the part size, for
  // the log and for tests: what it decided, at what time, from what to what, on what measurements.
  static final class Decision {
    final long at;
    // The target's decision, or null for the part size's.
    final ConcurrencyDecision target;
    // The part size's decision, or null for the target's.
    final String size;
    // The target, or the part size in bytes, before and after.
    final long previous;
    final long current;
    final int epoch;
    final double throughput;
    final double baseline;
    final double queueEstimate;
    final BelowTarget reason;
    final long cooldownNanos;

    Decision(long at, ConcurrencyDecision target, String size, long previous, long current, int epoch, double throughput, double baseline,
        double queueEstimate, BelowTarget reason, long cooldownNanos) {
      this.at = at;
      this.target = target;
      this.size = size;
      this.previous = previous;
      this.current = current;
      this.epoch = epoch;
      this.throughput = throughput;
      this.baseline = baseline;
      this.queueEstimate = queueEstimate;
      this.reason = reason;
      this.cooldownNanos = cooldownNanos;
    }

    @Override
    public String toString() {
      StringBuilder text = new StringBuilder();
      if (target != null) {
        text.append(target).append(": target ").append(previous).append(" -> ").append(current).append(", epoch ").append(epoch);
      } else {
        text.append(size).append(": part size ").append(previous).append(" -> ").append(current).append(" bytes");
      }
      if (throughput > 0) {
        text.append(String.format(Locale.ROOT, ", %.2f MiB/s", throughput / (1024 * 1024)));
      }
      if (baseline > 0) {
        text.append(String.format(Locale.ROOT, " against %.2f MiB/s", baseline / (1024 * 1024)));
      }
      if (target != null) {
        text.append(String.format(Locale.ROOT, ", queue estimate %.2f", queueEstimate));
      }
      if (reason != null && reason != BelowTarget.NONE) {
        text.append(", below target: ").append(reason);
      }
      if (cooldownNanos > 0) {
        text.append(", cooldown ").append(TimeUnit.NANOSECONDS.toMillis(cooldownNanos)).append(" ms");
      }
      return text.toString();
    }
  }

  // What one network attempt to send a part found out, as the controller uses it.
  static final class Sample {
    final boolean accepted;
    // HTTP 429 or 503: the server asked the client to slow down.
    final boolean throttled;
    // A failure that trying again could fix.
    final boolean transientFailure;
    // The server's Retry-After, or -1 for none.
    final long retryAfterNanos;
    final long bytes;
    // From sending the request to receiving its answer's headers.
    final long durationNanos;
    // The size epoch the part was sized in, when the upload chooses its part sizes.
    final int sizeEpoch;
    // The size feedback of the upload the part belongs to, when it chooses its part sizes: in a shared
    // scope, the controller judges sizes only by the parts of the file whose sizes it judges.
    final PartSizeFeedback sizer;

    Sample(boolean accepted, boolean throttled, boolean transientFailure, long retryAfterNanos, long bytes, long durationNanos,
        int sizeEpoch) {
      this(accepted, throttled, transientFailure, retryAfterNanos, bytes, durationNanos, sizeEpoch, null);
    }

    Sample(boolean accepted, boolean throttled, boolean transientFailure, long retryAfterNanos, long bytes, long durationNanos,
        int sizeEpoch, PartSizeFeedback sizer) {
      this.sizer = sizer;
      this.accepted = accepted;
      this.throttled = throttled;
      this.transientFailure = transientFailure;
      this.retryAfterNanos = retryAfterNanos;
      this.bytes = bytes;
      this.durationNanos = durationNanos;
      this.sizeEpoch = sizeEpoch;
    }
  }
}
