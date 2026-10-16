package com.files.net;

import com.files.CancellationToken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.LongSupplier;

// Admits the network attempts of one transfer, first attempts and retries alike: at most the
// controller's target at once, and none while the controller pauses after throttling. Waiting
// attempts are admitted in the order they asked, except that retries of parts already under way go
// before new parts, so a part holding its buffer space is never kept waiting behind parts that took
// new space. A wait ends when its scope stops, and a stopped wait takes nothing.
//
// An admitted attempt still has to get a request slot from the shared limiter and a connection slot
// from the transport, and perhaps a renewed URL, before it is sent; it holds its permit meanwhile,
// which is how the controller tells a shared budget from a shortage of work. Those waits can outlast
// what admitted it: a throttle may start a pause, or a failure lower the target, while it waits. So an
// attempt starts only through tryStart, which checks the pause and the target again at the moment it
// would be sent; an attempt that may no longer start gives back its permit and the slots it holds,
// rather than holding them through the pause, and the part asks again. The epoch an attempt belongs
// to is the one it starts in. An attempt gives its permit back when its request ends, before any
// retry delay, so a part waiting to be sent again takes no capacity. Every change reaches the
// controller under one lock, with the time of the clock it happened at.
final class AttemptAdmission {
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition changed = lock.newCondition();
  private final ConcurrencyController controller;
  private final LongSupplier clock;
  private final List<Waiter> waiters = new ArrayList<Waiter>();
  // Whether the transfers of a scope share it (TransferScope).
  private final boolean shared;

  AttemptAdmission(ConcurrencyController controller, LongSupplier clock) {
    this(controller, clock, false);
  }

  AttemptAdmission(ConcurrencyController controller, LongSupplier clock, boolean shared) {
    this.controller = controller;
    this.clock = clock;
    this.shared = shared;
  }

  // Waits until an attempt may be sent and returns it. Start it with tryStart; complete or close it
  // once its request has ended or it will not be sent. Throws the scope's exception if it stops first.
  Attempt admit(boolean retry, OperationScope scope) {
    lock.lock();
    try {
      long now = clock.getAsLong();
      if (waiters.isEmpty() && canAdmit(now)) {
        controller.granted(now);
        return new Attempt();
      }
      Waiter waiter = new Waiter(retry);
      // A new part goes last; a retry goes after the retries already waiting, ahead of new parts.
      int at = waiters.size();
      if (retry) {
        at = 0;
        while (at < waiters.size() && waiters.get(at).retry) {
          at++;
        }
      }
      waiters.add(at, waiter);
      CancellationToken.Registration wake = scope.onStop(this::wakeAll);
      try {
        while (true) {
          now = clock.getAsLong();
          grantWaiting(now);
          if (waiter.granted) {
            return new Attempt();
          }
          if (scope.isStopped()) {
            waiters.remove(waiter);
            grantWaiting(now);
            scope.check();
          }
          try {
            if (controller.isPaused(now) && controller.permitted() < controller.target()) {
              // Only the pause holds the attempt back: wake when it ends.
              changed.awaitNanos(controller.pausedUntil() - now);
            } else {
              changed.await();
            }
          } catch (InterruptedException e) {
            scope.interrupted();
          }
        }
      } finally {
        wake.close();
      }
    } finally {
      lock.unlock();
    }
  }

  // A file of a shared scope that chooses its part sizes joins, or leaves, the files whose sizes the
  // shared controller may judge (ConcurrencyController.attachSizing).
  void attachSizing(PartSizeFeedback sizer) {
    lock.lock();
    try {
      controller.attachSizing(clock.getAsLong(), sizer);
    } finally {
      lock.unlock();
    }
  }

  void detachSizing(PartSizeFeedback sizer) {
    lock.lock();
    try {
      controller.detachSizing(clock.getAsLong(), sizer);
    } finally {
      lock.unlock();
    }
  }

  // The transfer now sends one part at a time, as Files.com requires.
  void useSerial() {
    lock.lock();
    try {
      controller.useSerial(clock.getAsLong());
    } finally {
      lock.unlock();
    }
  }

  int target() {
    lock.lock();
    try {
      return controller.target();
    } finally {
      lock.unlock();
    }
  }

  // The target with its warm start's phase, and the attempts waiting for it, read together.
  UploadFileAdmission.Startup startupState() {
    lock.lock();
    try {
      return new UploadFileAdmission.Startup(controller.target(), controller.warmStarted(), controller.warming(), waiters.size());
    } finally {
      lock.unlock();
    }
  }

  TransferConcurrency snapshot() {
    lock.lock();
    try {
      return controller.snapshot(clock.getAsLong(), shared);
    } finally {
      lock.unlock();
    }
  }

  private boolean canAdmit(long now) {
    return controller.permitted() < controller.target() && !controller.isPaused(now);
  }

  // Admits waiters in order for as long as the target and the pause allow, and wakes them.
  private void grantWaiting(long now) {
    boolean any = false;
    while (!waiters.isEmpty() && canAdmit(now)) {
      Waiter first = waiters.remove(0);
      controller.granted(now);
      first.granted = true;
      any = true;
    }
    if (any) {
      changed.signalAll();
    }
  }

  private void wakeAll() {
    lock.lock();
    try {
      changed.signalAll();
    } finally {
      lock.unlock();
    }
  }

  private static final class Waiter {
    final boolean retry;
    boolean granted;

    Waiter(boolean retry) {
      this.retry = retry;
    }
  }

  // One admitted network attempt. Holds its permit, and the slots it is given, until it is completed
  // or closed, or tryStart finds it may no longer be sent. Used by one thread at a time.
  final class Attempt implements AutoCloseable {
    private final List<AutoCloseable> held = new ArrayList<AutoCloseable>();
    private int epoch;
    // The number the controller gave it as it started.
    private long number;
    private boolean started;
    private boolean ended;
    private boolean writing;

    private Attempt() {
    }

    // The attempt now holds slot, such as a limiter request slot, which it gives back when it ends.
    void hold(AutoCloseable slot) {
      if (slot != null) {
        held.add(slot);
      }
    }

    // Starts the attempt, if the transfer still allows it now that it holds its slots: no pause, and
    // fewer attempts being sent than the target. Otherwise ends it, giving back its permit and slots,
    // and returns false; the part must ask again.
    boolean tryStart() {
      lock.lock();
      try {
        long now = clock.getAsLong();
        if (!controller.isPaused(now) && controller.inFlight() < controller.target()) {
          started = true;
          epoch = controller.epoch();
          number = controller.sending(now);
          return true;
        }
        ended = true;
        controller.released(now, false, 0);
        grantWaiting(now);
        changed.signalAll();
      } finally {
        lock.unlock();
      }
      closeHeld();
      return false;
    }

    // The started attempt starts (true) or stops (false) writing what it received to a local
    // destination, which moves no bytes over the network meanwhile. Ending it stops that too.
    void writing(boolean now) {
      lock.lock();
      try {
        if (started && !ended && writing != now) {
          writing = now;
          controller.local(clock.getAsLong(), now);
        }
      } finally {
        lock.unlock();
      }
    }

    // Ends the started attempt with what its request found out; returns the controller's decisions,
    // when decisions are wanted.
    List<ConcurrencyController.Decision> complete(ConcurrencyController.Sample sample, boolean wantDecisions) {
      List<ConcurrencyController.Decision> decisions = wantDecisions ? new ArrayList<ConcurrencyController.Decision>() : null;
      end(sample, decisions);
      return decisions != null ? decisions : Collections.<ConcurrencyController.Decision>emptyList();
    }

    // Ends the attempt without a measurement: it was cancelled, or its part could not be read.
    @Override
    public void close() {
      end(null, null);
    }

    private void end(ConcurrencyController.Sample sample, List<ConcurrencyController.Decision> decisions) {
      lock.lock();
      try {
        if (ended) {
          return;
        }
        ended = true;
        long now = clock.getAsLong();
        if (writing) {
          writing = false;
          controller.local(now, false);
        }
        if (sample != null && started) {
          controller.record(now, epoch, sample, decisions);
        }
        controller.released(now, started, number);
        grantWaiting(now);
        changed.signalAll();
      } finally {
        lock.unlock();
      }
      closeHeld();
    }

    private void closeHeld() {
      for (AutoCloseable slot : held) {
        try {
          slot.close();
        } catch (Exception e) {
          // Slots release budgets and do not fail.
        }
      }
      held.clear();
    }
  }
}
