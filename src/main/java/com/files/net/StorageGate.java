package com.files.net;

import com.files.CancellationToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BooleanSupplier;
import org.apache.http.conn.routing.HttpRoute;

// Admits the storage requests of the transfers sharing a transport no faster than the transport's
// connection pool can lease connections for them: at most the pool's limit for each request's route,
// and at most its total limit for all routes together. The limits are read from the pool at every
// decision, never changed, so a caller that changes its pool's limits is followed, and requests in
// flight when a limit falls keep their slots until they end: no new one is admitted to that route,
// or at all, until enough have.
//
// Waiting requests are admitted in the order they asked, except that a request whose own route is
// full does not hold back one for another route that has room; requests for one route are always
// admitted in order. A wait ends when its scope stops, or when the transport asking closes, and an
// ended wait takes nothing.
//
// The gate counts only the storage requests of transfers. Requests to the Files.com API, and anything
// else using the pool, take connections it does not see, so they can still wait for a lease, and so
// can storage requests while such requests hold the pool's connections.
final class StorageGate {
  // How often waiting requests read the pool's limits again, in case they rose with no request
  // ending meanwhile.
  private static final long RECHECK_NANOS = TimeUnit.MILLISECONDS.toNanos(250);

  // The pool's limits, read when they are needed.
  interface Limits {
    // The most connections the pool leases to route at once.
    int perRoute(HttpRoute route);

    // The most connections the pool leases at once, to all routes.
    int total();
  }

  private final ReentrantLock lock = new ReentrantLock();
  private final Condition changed = lock.newCondition();
  private final Limits limits;
  private final Map<HttpRoute, Integer> inUse = new HashMap<HttpRoute, Integer>();
  private final List<Waiter> waiters = new ArrayList<Waiter>();
  private int totalInUse;

  StorageGate(Limits limits) {
    this.limits = limits;
  }

  // The most storage requests to route the pool could serve at once now.
  int capacity(HttpRoute route) {
    lock.lock();
    try {
      return Math.max(1, Math.min(limits.perRoute(route), limits.total()));
    } finally {
      lock.unlock();
    }
  }

  // Waits for a slot for one storage request to route, to close once the request has ended. Throws
  // the scope's exception if it stops first.
  Slot acquire(HttpRoute route, OperationScope scope) {
    return acquire(route, scope, () -> false);
  }

  // As above, also ending once ended says so, such as when the transport asking for the slot has
  // closed: checked before the request waits and whenever it wakes, as wakeAll wakes it. Such a wait
  // takes nothing, leaves the requests behind it as they were, and returns null.
  Slot acquire(HttpRoute route, OperationScope scope, BooleanSupplier ended) {
    lock.lock();
    try {
      if (ended.getAsBoolean()) {
        return null;
      }
      Waiter waiter = new Waiter(route);
      waiters.add(waiter);
      admitWaiting();
      if (waiter.admitted) {
        return new Slot(route);
      }
      CancellationToken.Registration wake = scope.onStop(this::wakeAll);
      try {
        while (!waiter.admitted) {
          if (scope.isStopped()) {
            waiters.remove(waiter);
            admitWaiting();
            scope.check();
          }
          if (ended.getAsBoolean()) {
            waiters.remove(waiter);
            admitWaiting();
            return null;
          }
          try {
            changed.awaitNanos(RECHECK_NANOS);
          } catch (InterruptedException e) {
            scope.interrupted();
          }
          if (!waiter.admitted) {
            admitWaiting();
          }
        }
      } finally {
        wake.close();
      }
      return new Slot(route);
    } finally {
      lock.unlock();
    }
  }

  // Admits waiting requests in order while the pool has room for them.
  private void admitWaiting() {
    boolean any = false;
    int total = limits.total();
    Map<HttpRoute, Integer> limitOf = new HashMap<HttpRoute, Integer>();
    for (int i = 0; i < waiters.size() && totalInUse < total; ) {
      Waiter waiter = waiters.get(i);
      Integer limit = limitOf.get(waiter.route);
      if (limit == null) {
        limit = limits.perRoute(waiter.route);
        limitOf.put(waiter.route, limit);
      }
      int used = inUse.getOrDefault(waiter.route, 0);
      if (used >= limit) {
        // Its route is full: the requests behind it for other routes may go first.
        i++;
        continue;
      }
      waiters.remove(i);
      inUse.put(waiter.route, used + 1);
      totalInUse++;
      waiter.admitted = true;
      any = true;
    }
    if (any) {
      changed.signalAll();
    }
  }

  private void release(HttpRoute route) {
    lock.lock();
    try {
      int used = inUse.get(route) - 1;
      if (used == 0) {
        inUse.remove(route);
      } else {
        inUse.put(route, used);
      }
      totalInUse--;
      admitWaiting();
      changed.signalAll();
    } finally {
      lock.unlock();
    }
  }

  // Wakes every waiting request to check again whether its wait has ended.
  void wakeAll() {
    lock.lock();
    try {
      changed.signalAll();
    } finally {
      lock.unlock();
    }
  }

  private static final class Waiter {
    final HttpRoute route;
    boolean admitted;

    Waiter(HttpRoute route) {
      this.route = route;
    }
  }

  // One admitted storage request. Closing it gives the slot back; closing it again has no effect.
  final class Slot implements AutoCloseable {
    private final HttpRoute route;
    private boolean closed;

    private Slot(HttpRoute route) {
      this.route = route;
    }

    @Override
    public void close() {
      synchronized (this) {
        if (closed) {
          return;
        }
        closed = true;
      }
      release(route);
    }
  }
}
