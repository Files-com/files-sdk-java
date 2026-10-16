package com.files;

import java.util.ArrayList;
import java.util.List;

/**
 * Cancels requests and transfers from another thread. Pass a token as the {@code cancellation}
 * request option, or with {@link com.files.net.UploadOptions.Builder#cancellation}, and call
 * {@link #cancel()} to stop the work that uses it.
 *
 * <p>Cancelling aborts the work's requests, including one that is waiting for the server or
 * sending or receiving a body, and ends its waits between retries. The operation then throws
 * {@link com.files.exceptions.OperationCancelledException} with reason
 * {@link com.files.exceptions.OperationCancelledException.Reason#CANCELLED}. A token cannot be
 * reset: once cancelled, every operation that uses it stops at once. One token can be shared by
 * several operations, and it is safe to use from any thread.
 */
public final class CancellationToken {
  private final List<Runnable> listeners = new ArrayList<Runnable>();
  private boolean cancelled;

  /**
   * Cancels every operation using this token. Calling it again has no effect.
   */
  public void cancel() {
    List<Runnable> notify;
    synchronized (listeners) {
      if (cancelled) {
        return;
      }
      cancelled = true;
      notify = new ArrayList<Runnable>(listeners);
      listeners.clear();
    }
    for (Runnable listener : notify) {
      listener.run();
    }
  }

  public boolean isCancelled() {
    synchronized (listeners) {
      return cancelled;
    }
  }

  /**
   * Runs {@code listener} once, when this token is cancelled, on the thread that cancels it; if the
   * token is already cancelled, runs it at once on this thread. Keep the listener quick: it runs
   * before {@link #cancel()} returns.
   *
   * @return a registration that removes the listener when closed
   */
  public Registration onCancel(Runnable listener) {
    synchronized (listeners) {
      if (!cancelled) {
        listeners.add(listener);
        return () -> {
          synchronized (listeners) {
            listeners.remove(listener);
          }
        };
      }
    }
    listener.run();
    return () -> { };
  }

  /**
   * A listener added with {@link #onCancel(Runnable)}. Closing it removes the listener; closing it
   * again, or after the token was cancelled, has no effect.
   */
  public interface Registration extends AutoCloseable {
    @Override
    void close();
  }
}
