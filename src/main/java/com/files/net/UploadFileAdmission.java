package com.files.net;

import com.files.CancellationToken;
import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

// Admits the automatic uploads of a scope that start the default way (TransferScope), before they ask
// Files.com anything, to as many in progress at once as the parts they share can use, as the Files.com
// CLI admits the files of a job: max(target + target / 4 + 1, MIN_FILES) for the lowest target of the
// scope's shared targets for such uploads, which leaves a target's next probe more parts than it can
// send; MIN_FILES + MIN_FILES / 4 + 1 before there is one. While those targets have fewer parts waiting
// than a quarter of the lowest and one, every SUPPLY_STEP_NANOS one more upload may start, so that
// uploads still beginning or finishing do not starve the parts; a raise of a warm start's target while
// warming raises that supply in proportion; and once enough parts wait, the supply falls back by one
// each step. The supply is never more than the uploads offered, in progress and waiting: unlike the
// CLI's job, which caps its files at 128, a scope has the callers its application gives it, and with
// enough of them the default startup reaches, by these steps, the supply an aggressive one starts with.
// An upload keeps its place until it ends, completed, failed or cancelled.
//
// Waiting uploads are admitted in the order they asked. The first of them looks at the targets again
// every RECHECK_NANOS, or every supply step while there are targets to supply, since targets change
// without any upload ending; the others wait to be admitted. A wait ends when its scope stops, and a
// stopped wait takes nothing. The targets are read under this admission's lock, through the scope and
// each shared admission's own lock, in that order only; nothing calls back.
final class UploadFileAdmission {
  // The fewest the targets can hold it to, so that uploads beginning, finishing and probing overlap.
  static final int MIN_FILES = 4;
  static final long RECHECK_NANOS = TimeUnit.SECONDS.toNanos(1);
  static final long SUPPLY_STEP_NANOS = TimeUnit.MILLISECONDS.toNanos(100);

  // What the shared targets of such uploads show, each read with its phase under its own lock: the
  // lowest target, whether any of the targets that low warm-started, or are warming, and the part
  // attempts waiting for any of the targets.
  static final class Startup {
    final int target;
    final boolean warmStarted;
    final boolean warming;
    final int waiting;

    Startup(int target, boolean warmStarted, boolean warming, int waiting) {
      this.target = target;
      this.warmStarted = warmStarted;
      this.warming = warming;
      this.waiting = waiting;
    }
  }

  private final ReentrantLock lock = new ReentrantLock();
  private final ArrayDeque<Waiter> waiters = new ArrayDeque<Waiter>();
  // The targets now, or null when the scope has none for such uploads (none yet, or it closed).
  private final Supplier<Startup> targets;
  private final LongSupplier clock;
  private int running;
  // The supply the targets were given: how many uploads they let run (0 for none yet), when that last
  // changed, and the lowest target, with whether it was warming, when it was last looked at (0 before).
  private int grown;
  private long changedAt;
  private int grownTarget;
  private boolean grownWarm;

  UploadFileAdmission(Supplier<Startup> targets, LongSupplier clock) {
    this.targets = targets;
    this.clock = clock;
    this.changedAt = clock.getAsLong();
  }

  // Waits for the upload's place and returns it, to close once the upload has ended. Throws the
  // scope's exception if it stops first.
  Permit admit(OperationScope scope) {
    lock.lock();
    try {
      Waiter waiter = new Waiter(lock.newCondition());
      waiters.addLast(waiter);
      CancellationToken.Registration wake = scope.onStop(() -> signal(waiter));
      try {
        while (true) {
          if (!waiter.granted && scope.isStopped()) {
            waiters.remove(waiter);
            wakeFirst();
            scope.check();
          }
          long recheck = grantWaiting();
          if (waiter.granted) {
            return new Permit();
          }
          try {
            if (waiters.peekFirst() == waiter) {
              waiter.condition.awaitNanos(recheck);
            } else {
              waiter.condition.await();
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

  int waiting() {
    lock.lock();
    try {
      return waiters.size();
    } finally {
      lock.unlock();
    }
  }

  int running() {
    lock.lock();
    try {
      return running;
    } finally {
      lock.unlock();
    }
  }

  private void signal(Waiter waiter) {
    lock.lock();
    try {
      waiter.condition.signal();
    } finally {
      lock.unlock();
    }
  }

  // Admits waiting uploads in order while the targets allow, wakes the first left to look again, and
  // returns how long it waits before it does.
  private long grantWaiting() {
    Startup state = targets.get();
    long recheck = RECHECK_NANOS;
    while (!waiters.isEmpty()) {
      int allowed = limit(state);
      if (running >= allowed) {
        recheck = supplyRecheck(state);
        break;
      }
      Waiter first = waiters.removeFirst();
      first.granted = true;
      running++;
      first.condition.signal();
    }
    wakeFirst();
    return recheck;
  }

  private void wakeFirst() {
    Waiter first = waiters.peekFirst();
    if (first != null) {
      first.condition.signal();
    }
  }

  // How many uploads may be in progress now.
  private int limit(Startup state) {
    if (state == null) {
      // Without the targets it learned from, a supply is stale: it starts again with them.
      grown = 0;
      grownTarget = 0;
    }
    int target = state != null ? state.target : MIN_FILES;
    int limit = Math.max(target + target / 4 + 1, MIN_FILES);
    if (running < limit) {
      return limit;
    }
    return Math.max(limit, supply(state, limit));
  }

  // The uploads the targets are supplied, past what the lowest allows (limit), adjusted by one upload a
  // step as the parts waiting for them show, and never more than the uploads offered.
  private int supply(Startup state, int limit) {
    if (state == null) {
      return limit;
    }
    long now = clock.getAsLong();
    int offered = running + waiters.size();
    if (state.warmStarted) {
      if (grownTarget == 0) {
        // Uploads supplied before the first look fed the warm start's first target.
        grownTarget = 1;
        grownWarm = true;
      }
      if (grown > 0 && state.target > grownTarget && (grownWarm || state.warming)) {
        // A warm start's raise needs as many more uploads as it adds parts, in proportion.
        grown = (int) Math.min(offered, ((long) grown * state.target + grownTarget - 1) / grownTarget);
      }
    }
    grownTarget = state.target;
    grownWarm = state.warming;
    grown = Math.min(grown, offered);
    boolean tooFew = state.waiting < state.target / 4 + 1;
    if (now - changedAt >= SUPPLY_STEP_NANOS && ((tooFew && running >= grown) || (!tooFew && grown > limit))) {
      grown = tooFew ? Math.min(offered, running + 1) : grown - 1;
      changedAt = now;
    }
    return grown;
  }

  // How long the first waiting upload waits before it looks again: until the supply may next grow,
  // while the targets have too few parts waiting.
  private long supplyRecheck(Startup state) {
    if (state == null) {
      return RECHECK_NANOS;
    }
    if (state.waiting < state.target / 4 + 1) {
      long left = SUPPLY_STEP_NANOS - (clock.getAsLong() - changedAt);
      if (left > 0) {
        return left;
      }
    }
    return SUPPLY_STEP_NANOS;
  }

  // An upload's place. Closing it gives the place to the next waiting upload; closing it again has no
  // effect.
  final class Permit implements AutoCloseable {
    private boolean closed;

    private Permit() {
    }

    @Override
    public void close() {
      lock.lock();
      try {
        if (closed) {
          return;
        }
        closed = true;
        running--;
        grantWaiting();
      } finally {
        lock.unlock();
      }
    }
  }

  private static final class Waiter {
    final Condition condition;
    boolean granted;

    Waiter(Condition condition) {
      this.condition = condition;
    }
  }
}
