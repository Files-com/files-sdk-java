package com.files.net;

import com.files.CancellationToken;
import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// An amount of something, such as request slots or bytes, handed out first come, first served: a
// request waits while an earlier one is waiting, even if the amount it wants is free, so a large
// request is never starved by a stream of small ones. A wait ends when its scope stops, and a
// stopped wait takes nothing.
final class FifoBudget {
  private final ReentrantLock lock = new ReentrantLock();
  private final ArrayDeque<Waiter> waiters = new ArrayDeque<Waiter>();
  private final long capacity;
  private long available;

  FifoBudget(long capacity) {
    this.capacity = capacity;
    this.available = capacity;
  }

  long capacity() {
    return capacity;
  }

  long inUse() {
    lock.lock();
    try {
      return capacity - available;
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

  // Waits for the amount, which must not exceed the capacity, and returns it as a grant to close
  // when it is no longer needed. Throws the scope's exception if it stops first.
  Grant acquire(long amount, OperationScope scope) {
    if (amount > capacity) {
      throw new IllegalArgumentException("Cannot acquire " + amount + " of a budget of " + capacity);
    }
    lock.lock();
    try {
      if (waiters.isEmpty() && available >= amount) {
        available -= amount;
        return new Grant(amount);
      }
      Waiter waiter = new Waiter(amount, lock.newCondition());
      waiters.addLast(waiter);
      CancellationToken.Registration wake = scope.onStop(() -> signal(waiter));
      try {
        while (!waiter.granted) {
          if (scope.isStopped()) {
            waiters.remove(waiter);
            grantWaiting();
            scope.check();
          }
          try {
            waiter.condition.await();
          } catch (InterruptedException e) {
            scope.interrupted();
          }
        }
      } finally {
        wake.close();
      }
      return new Grant(amount);
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

  private void release(long amount) {
    lock.lock();
    try {
      available += amount;
      grantWaiting();
    } finally {
      lock.unlock();
    }
  }

  // Hands out the amount to waiters in order, stopping at the first that does not fit.
  private void grantWaiting() {
    Waiter first;
    while ((first = waiters.peekFirst()) != null && available >= first.amount) {
      waiters.removeFirst();
      available -= first.amount;
      first.granted = true;
      first.condition.signal();
    }
  }

  // An amount taken from the budget. Closing it returns the amount; closing it again has no effect.
  final class Grant implements AutoCloseable {
    private final long amount;
    private boolean closed;

    private Grant(long amount) {
      this.amount = amount;
    }

    @Override
    public void close() {
      synchronized (this) {
        if (closed) {
          return;
        }
        closed = true;
      }
      release(amount);
    }
  }

  private static final class Waiter {
    final long amount;
    final Condition condition;
    boolean granted;

    Waiter(long amount, Condition condition) {
      this.amount = amount;
      this.condition = condition;
    }
  }
}
