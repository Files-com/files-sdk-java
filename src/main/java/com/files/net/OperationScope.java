package com.files.net;

import com.files.CancellationToken;
import com.files.exceptions.OperationCancelledException;
import com.files.exceptions.OperationCancelledException.Reason;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

// Whether one API call or transfer may continue. It stops when the caller's CancellationToken is
// cancelled, its time limit passes, the calling thread is interrupted, or its client is closed; a
// child scope, for the parts of a transfer, also stops when the transfer fails. Every request and
// wait of the operation registers here, so stopping aborts the request and ends the wait.
//
// The calling thread's interruption is noticed even while it is blocked reading a socket, which
// Java does not interrupt: a scheduler thread checks the thread every WATCH_INTERVAL_MILLIS and
// stops the scope, which aborts the request.
final class OperationScope implements AutoCloseable {
  static final long WATCH_INTERVAL_MILLIS = 50;

  private static final Set<OperationScope> WATCHED = new HashSet<OperationScope>();
  // The threads doing the work of operations, each with the number of operations it is doing it
  // for: the calling thread of each call until the call ends, and each thread of an upload while it
  // sends a part.
  private static final Map<Thread, Integer> WORKING = new HashMap<Thread, Integer>();
  private static ScheduledExecutorService scheduler;
  private static ScheduledFuture<?> watch;

  private final Object lock = new Object();
  private final List<Runnable> listeners = new ArrayList<Runnable>();
  private final List<AutoCloseable> links = new ArrayList<AutoCloseable>();
  private final String subject;
  private final OperationScope parent;
  private final Thread caller;
  private final Duration timeout;
  // The API root the operation's requests go to: the client's when the operation began, or where
  // Files.com relocated the site for one of its requests. A child scope shares its parent's.
  private final AtomicReference<URI> apiRoot;
  private Reason reason;
  private String detail = "";
  private boolean failed;
  private boolean ended;

  private OperationScope(String subject, OperationScope parent, Thread caller, Duration timeout, AtomicReference<URI> apiRoot) {
    this.subject = subject;
    this.parent = parent;
    this.caller = caller;
    this.timeout = timeout;
    this.apiRoot = apiRoot;
  }

  // A scope for work that the current thread runs and waits for. subject names the work in
  // messages, such as "The request"; its requests start at apiRoot.
  static OperationScope open(String subject, CancellationToken token, Duration timeout, URI apiRoot) {
    OperationScope scope = new OperationScope(subject, null, Thread.currentThread(), timeout,
        new AtomicReference<URI>(apiRoot));
    working(scope.caller, 1);
    if (Thread.currentThread().isInterrupted()) {
      scope.cancel(Reason.INTERRUPTED);
      return scope;
    }
    if (token != null) {
      scope.link(token.onCancel(() -> scope.cancel(Reason.CANCELLED)));
    }
    if (timeout != null) {
      ScheduledFuture<?> deadline = scheduler().schedule(() -> scope.cancel(Reason.TIMED_OUT),
          timeout.toNanos(), TimeUnit.NANOSECONDS);
      scope.link(() -> deadline.cancel(false));
    }
    watch(scope);
    return scope;
  }

  // A scope that stops with this one, and also on its own when the work it covers fails.
  OperationScope child() {
    OperationScope child = new OperationScope(subject, this, null, timeout, apiRoot);
    child.link(onStop(() -> {
      Reason parentReason = reason();
      if (parentReason != null) {
        child.stop(parentReason, detail(), false);
      } else {
        child.fail();
      }
    }));
    return child;
  }

  String subject() {
    return subject;
  }

  URI apiRoot() {
    return apiRoot.get();
  }

  // Sends the operation's later requests to relocated, unless another of its requests has already
  // moved them from root.
  void relocate(URI root, URI relocated) {
    apiRoot.compareAndSet(root, relocated);
  }

  void cancel(Reason cancelReason) {
    stop(cancelReason, "", false);
  }

  // The client running the work was closed.
  void cancelForClose() {
    stop(Reason.CANCELLED, " because its client was closed", false);
  }

  // Stops the work because part of it failed; the failure itself is reported by its owner.
  void fail() {
    stop(null, "", true);
  }

  private void stop(Reason cancelReason, String because, boolean failure) {
    List<Runnable> notify;
    synchronized (lock) {
      if (reason != null || failed) {
        return;
      }
      reason = cancelReason;
      detail = because;
      failed = failure;
      notify = new ArrayList<Runnable>(listeners);
      listeners.clear();
      lock.notifyAll();
    }
    for (Runnable listener : notify) {
      listener.run();
    }
  }

  boolean isStopped() {
    synchronized (lock) {
      return reason != null || failed;
    }
  }

  private String detail() {
    synchronized (lock) {
      return detail;
    }
  }

  // The reason for a cancellation, or null while running or after a failure.
  Reason reason() {
    synchronized (lock) {
      return reason;
    }
  }

  // Runs the listener when the scope stops, or at once if it has. Closing the result removes it.
  CancellationToken.Registration onStop(Runnable listener) {
    synchronized (lock) {
      if (reason == null && !failed) {
        listeners.add(listener);
        return () -> {
          synchronized (lock) {
            listeners.remove(listener);
          }
        };
      }
    }
    listener.run();
    return () -> { };
  }

  // Throws if the scope has stopped: the cancellation, or Stopped after a failure elsewhere.
  void check() {
    synchronized (lock) {
      if (reason == null && !failed) {
        return;
      }
    }
    throw stopped(null);
  }

  // The exception for a stopped scope, with the failure it caused, if any.
  RuntimeException stopped(Throwable cause) {
    Reason cancelReason = reason();
    if (cancelReason == null) {
      return new Stopped();
    }
    if (cancelReason == Reason.INTERRUPTED && Thread.currentThread() == caller) {
      Thread.currentThread().interrupt();
    }
    String because;
    synchronized (lock) {
      because = detail;
    }
    return new OperationCancelledException(cancelReason, subject + " " + describe(cancelReason) + because + ".", cause);
  }

  private String describe(Reason cancelReason) {
    switch (cancelReason) {
      case TIMED_OUT:
        return "timed out after " + timeout.toMillis() + " ms";
      case INTERRUPTED:
        return "was interrupted";
      default:
        return "was cancelled";
    }
  }

  // Waits for the time given, or until the scope stops, then throws if it has. An interruption of
  // the waiting thread stops the scope.
  void sleep(long millis) {
    long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(millis);
    synchronized (lock) {
      long remaining;
      while (reason == null && !failed && (remaining = end - System.nanoTime()) > 0) {
        try {
          TimeUnit.NANOSECONDS.timedWait(lock, remaining);
        } catch (InterruptedException e) {
          interrupted();
        }
      }
    }
    check();
  }

  // The thread waiting for this scope's work was interrupted: the work stops, and the thread's
  // interrupt status is restored when the cancellation is thrown.
  void interrupted() {
    if (parent != null) {
      parent.interrupted();
    } else {
      cancel(Reason.INTERRUPTED);
    }
  }

  private void link(AutoCloseable closeable) {
    synchronized (lock) {
      links.add(closeable);
    }
  }

  // Runs the action when the scope is closed.
  void whenClosed(AutoCloseable action) {
    link(action);
  }

  // Runs part of the operation's work on one of its own threads, such as an upload's.
  void runPart(Runnable work) {
    working(Thread.currentThread(), 1);
    try {
      work.run();
    } finally {
      working(Thread.currentThread(), -1);
    }
  }

  // Whether the current thread is doing the work of an operation, of any client: its own call, or
  // part of a transfer. Such a thread cannot wait for operations to end, since they may be waiting
  // for it, in a close of their own or for its part.
  static boolean isWorkingOnCurrentThread() {
    synchronized (WORKING) {
      return WORKING.containsKey(Thread.currentThread());
    }
  }

  private static void working(Thread thread, int change) {
    synchronized (WORKING) {
      Integer count = WORKING.get(thread);
      int now = (count == null ? 0 : count) + change;
      if (now > 0) {
        WORKING.put(thread, now);
      } else {
        WORKING.remove(thread);
      }
    }
  }

  @Override
  public void close() {
    synchronized (lock) {
      if (ended) {
        return;
      }
      ended = true;
    }
    if (caller != null) {
      working(caller, -1);
    }
    unwatch(this);
    List<AutoCloseable> unlink;
    synchronized (lock) {
      unlink = new ArrayList<AutoCloseable>(links);
      links.clear();
    }
    for (AutoCloseable closeable : unlink) {
      try {
        closeable.close();
      } catch (Exception e) {
        // Removing a listener does not fail.
      }
    }
  }

  // The SDK's one scheduler thread: it ends time limits and watches for interrupted callers. It is
  // a daemon, so it never keeps an application running. A cancelled task leaves its queue at once:
  // a scope that ends before its time limit cancels the limit's task, which otherwise would stay
  // queued until the limit's deadline.
  static synchronized ScheduledExecutorService scheduler() {
    if (scheduler == null) {
      ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, runnable -> {
        Thread thread = new Thread(runnable, "files-sdk-scheduler");
        thread.setDaemon(true);
        return thread;
      });
      executor.setRemoveOnCancelPolicy(true);
      scheduler = executor;
    }
    return scheduler;
  }

  private static void watch(OperationScope scope) {
    synchronized (WATCHED) {
      WATCHED.add(scope);
      if (watch == null) {
        watch = scheduler().scheduleWithFixedDelay(OperationScope::checkInterrupts,
            WATCH_INTERVAL_MILLIS, WATCH_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
      }
    }
  }

  private static void unwatch(OperationScope scope) {
    synchronized (WATCHED) {
      if (WATCHED.remove(scope) && WATCHED.isEmpty() && watch != null) {
        watch.cancel(false);
        watch = null;
      }
    }
  }

  private static void checkInterrupts() {
    List<OperationScope> interrupted = new ArrayList<OperationScope>();
    synchronized (WATCHED) {
      for (OperationScope scope : WATCHED) {
        if (scope.caller.isInterrupted()) {
          interrupted.add(scope);
        }
      }
    }
    for (OperationScope scope : interrupted) {
      scope.interrupted();
    }
  }

  // Thrown inside a transfer when one of its parts failed; the transfer reports that failure.
  static final class Stopped extends RuntimeException {
    private static final long serialVersionUID = 1L;

    Stopped() {
      super("Stopped after a failure elsewhere in the operation", null, false, false);
    }
  }
}
