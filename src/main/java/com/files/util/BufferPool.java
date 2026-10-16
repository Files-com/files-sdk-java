package com.files.util;

import com.files.FilesConfig;
import com.files.exceptions.StackSuppressedRuntimeException;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A process-wide pool of reusable byte arrays in four size classes, used for buffers such as the
 * ones {@link StreamTransfer} copies through. {@link #needBuffer(int)} lends a {@link Buffer}, and
 * closing the buffer gives its array back to the pool.
 *
 * <p>The size classes ({@link #T_BUFFER_SIZE}, {@link #S_BUFFER_SIZE}, {@link #M_BUFFER_SIZE} and
 * {@link #L_BUFFER_SIZE}) and the allocation limit are read from {@link FilesConfig} once, when
 * this class is initialized; later {@code FilesConfig} changes do not affect them. All of the
 * pool's state is static and shared by every thread in the JVM.
 *
 * <p>The pool reserves allocation permits, one per byte, when it creates an array, and keeps them
 * while the array is lent out or cached for reuse. It gives them back when it discards an array:
 * in full, or in part when it replaces a cached array with a smaller new one. An array given to a
 * public {@link Buffer} constructor has no reservation; closing that buffer caches the array only
 * if the pool can reserve it then, and never gives back permits for it. The allocation figures the
 * pool reports are therefore reservations by this pool, cached arrays included. They are not heap
 * usage, and they do not limit memory the rest of the SDK or the application uses.
 */
public class BufferPool {
  private static final Logger log = LoggerFactory.getLogger(BufferPool.class);

  private static final boolean TRACK_BUFFER_CREATION_STACK = true;
  /** A shared array of length 0. */
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  /**
   * The tiny size class, in bytes: {@link FilesConfig#getCachedBufferTinySize()} when this class
   * was initialized.
   */
  public static final int T_BUFFER_SIZE = FilesConfig.getInstance().getCachedBufferTinySize();
  /**
   * The small size class, in bytes: {@link FilesConfig#getCachedBufferSmallSize()} when this class
   * was initialized.
   */
  public static final int S_BUFFER_SIZE = FilesConfig.getInstance().getCachedBufferSmallSize();
  /**
   * The medium size class, in bytes: {@link FilesConfig#getCachedBufferMediumSize()} when this
   * class was initialized.
   */
  public static final int M_BUFFER_SIZE = FilesConfig.getInstance().getCachedBufferMediumSize();
  /**
   * The large size class, in bytes: {@link FilesConfig#getCachedBufferLargeSize()} when this class
   * was initialized. {@link #needBuffer(int)} rejects larger requests.
   */
  public static final int L_BUFFER_SIZE = FilesConfig.getInstance().getCachedBufferLargeSize();
  /**
   * How many tiny arrays stay cached when the pool trims tiny arrays to make room: a quarter of
   * {@link #MAX_ALLOCATION} divided by {@link #T_BUFFER_SIZE}.
   */
  protected static final int T_BUFFER_TARGET_COUNT;
  /**
   * How many small arrays stay cached when the pool trims small arrays to make room: a quarter of
   * {@link #MAX_ALLOCATION} divided by {@link #S_BUFFER_SIZE}.
   */
  protected static final int S_BUFFER_TARGET_COUNT;
  /**
   * How many medium arrays stay cached when the pool trims medium arrays to make room: a quarter
   * of {@link #MAX_ALLOCATION} divided by {@link #M_BUFFER_SIZE}.
   */
  protected static final int M_BUFFER_TARGET_COUNT;
  /**
   * The allocation limit, in bytes: {@link FilesConfig#getCachedBufferMaxBytes()} when this class
   * was initialized.
   */
  protected static final int MAX_ALLOCATION;
  /**
   * The allocation permits, one per byte, that new arrays reserve; it starts with
   * {@link #MAX_ALLOCATION} permits.
   */
  protected static final Semaphore TOTAL_ALLOCATED;
  /** Tiny arrays cached for reuse; each keeps its reservation. */
  protected static final Queue<byte[]> T_BUFFERS = new ConcurrentLinkedQueue<>();
  /** Small arrays cached for reuse; each keeps its reservation. */
  protected static final Queue<byte[]> S_BUFFERS = new ConcurrentLinkedQueue<>();
  /** Medium arrays cached for reuse; each keeps its reservation. */
  protected static final Queue<byte[]> M_BUFFERS = new ConcurrentLinkedQueue<>();
  /** Large arrays cached for reuse; each keeps its reservation. */
  protected static final Queue<byte[]> L_BUFFERS = new ConcurrentLinkedQueue<>();

  static {
    MAX_ALLOCATION = FilesConfig.getInstance().getCachedBufferMaxBytes();
    TOTAL_ALLOCATED = new Semaphore(MAX_ALLOCATION);
    T_BUFFER_TARGET_COUNT = (int) ((MAX_ALLOCATION * .25) / T_BUFFER_SIZE);
    S_BUFFER_TARGET_COUNT = (int) ((MAX_ALLOCATION * .25) / S_BUFFER_SIZE);
    M_BUFFER_TARGET_COUNT = (int) ((MAX_ALLOCATION * .25) / M_BUFFER_SIZE);
    // remaining % is provided as minimum cached allocations to large buffers
  }

  /**
   * Creates a {@code BufferPool}. Every member is static, so no instance is needed.
   */
  public BufferPool() {
  }

  /**
   * Returns how many more bytes the pool can reserve for new arrays right now: the allocation
   * limit less what its arrays, lent out or cached, reserve.
   *
   * @return the available allocation permits, in bytes
   */
  public static int getAvailableAllocation() {
    return TOTAL_ALLOCATED.availablePermits();
  }

  /**
   * Returns how many bytes the pool's arrays reserve right now, lent out or cached: the allocation
   * limit less {@link #getAvailableAllocation()}. It measures this pool's reservations, not heap
   * usage.
   *
   * @return the reserved allocation, in bytes
   */
  public static int getTotalAllocated() {
    return MAX_ALLOCATION - TOTAL_ALLOCATED.availablePermits();
  }

  /**
   * Returns an estimate of how many threads are blocked waiting to acquire the pool's allocation
   * permits. {@link #needBuffer(int)} never blocks on those permits: it takes them only when they
   * are available and otherwise waits for a cached array. Threads waiting in {@code needBuffer} are
   * therefore not counted.
   *
   * @return the number of threads queued on the allocation permits
   */
  public static int getWaitingToAllocateThreadCount() {
    return TOTAL_ALLOCATED.getQueueLength();
  }

  /**
   * Returns how many tiny arrays are cached for reuse. Other threads can change it at any time, so
   * it is an estimate.
   *
   * @return the number of cached tiny arrays
   */
  public static int availableTinyBufferCount() {
    return T_BUFFERS.size();
  }

  /**
   * Returns how many small arrays are cached for reuse. Other threads can change it at any time, so
   * it is an estimate.
   *
   * @return the number of cached small arrays
   */
  public static int availableSmallBufferCount() {
    return S_BUFFERS.size();
  }

  /**
   * Returns how many medium arrays are cached for reuse. Other threads can change it at any time,
   * so it is an estimate.
   *
   * @return the number of cached medium arrays
   */
  public static int availableMediumBufferCount() {
    return M_BUFFERS.size();
  }

  /**
   * Returns how many large arrays are cached for reuse. Other threads can change it at any time, so
   * it is an estimate.
   *
   * @return the number of cached large arrays
   */
  public static int availableLargeBufferCount() {
    return L_BUFFERS.size();
  }

  /**
   * Lends a buffer of at least {@code minSize} bytes, waiting until one is available. The request
   * goes to the smallest size class that holds {@code minSize}. A cached array of that class is
   * reused if there is one. Otherwise a new array is created if the allocation limit allows: of the
   * class size if possible, or else of exactly {@code minSize} bytes. Otherwise the pool may
   * discard a larger cached array to make room, or release cached arrays of smaller classes. Then,
   * if no array of this class is cached, it asks to wait 50 milliseconds for one to be returned,
   * and tries again. A returned array can end the wait sooner, and thread scheduling and
   * reacquiring the cache's lock can delay the next try. There is no SDK timeout, and waiting
   * callers are not served in any particular order.
   *
   * <p>The buffer's array can be longer than {@code minSize}, and its window starts as the whole
   * array. The caller owns the buffer and closes it exactly once, normally with
   * try-with-resources, and does not use its array after closing it:
   *
   * <pre>{@code
   * try (BufferPool.Buffer buffer = BufferPool.needBuffer(8192)) {
   *   byte[] bytes = buffer.getBuffer(); // can be longer than 8192
   *   // fill and use bytes here; keep no reference to them after the block
   * }
   * }</pre>
   *
   * @param minSize the fewest bytes the buffer must hold; at most {@link #L_BUFFER_SIZE}
   * @return a buffer whose array holds at least {@code minSize} bytes
   * @throws IllegalArgumentException if {@code minSize} is greater than {@link #L_BUFFER_SIZE}
   * @throws RuntimeException if the thread is interrupted while waiting; the thread's interrupt
   *     flag is set again, and the {@code InterruptedException} is the cause
   */
  public static Buffer needBuffer(int minSize) {
    try {
      if (minSize <= T_BUFFER_SIZE) {
        return needTinyBuffer(minSize);
      } else if (minSize <= S_BUFFER_SIZE) {
        return needSmallBuffer(minSize);
      } else if (minSize <= M_BUFFER_SIZE) {
        return needMediumBuffer(minSize);
      } else if (minSize <= L_BUFFER_SIZE) {
        return needLargeBuffer(minSize);
      } else {
        throw new IllegalArgumentException("Buffer size too large: " + minSize);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted waiting for buffer", e);
    }
  }

  private static Buffer atomicPoll(Queue<byte[]> buffers) {
    byte[] bytes = buffers.poll();
    if (bytes == null) {
      return null;
    } else {
      return new Buffer(bytes, true);
    }
  }

  private static Buffer needTinyBuffer(int minSize) throws InterruptedException {
    while (true) {
      Buffer result = atomicPoll(T_BUFFERS);
      if (result == null) {
        result = tryMakeBuffer(minSize, T_BUFFER_SIZE);
        if (result == null) {
          // we can't allocate, lets try to find a larger buffer we can de-allocate first
          Buffer larger = atomicPoll(S_BUFFERS);
          if (larger == null) {
            larger = atomicPoll(M_BUFFERS);
          }
          if (larger == null) {
            larger = atomicPoll(L_BUFFERS);
          }
          if (larger != null) {
            // use swapBuffer to make sure we get a buffer from de-allocation
            return swapBuffer(larger, T_BUFFER_SIZE);
          }
        } else {
          return result;
        }
      } else {
        return result;
      }

      noBufferBlock(T_BUFFERS);
    }
  }

  private static Buffer needSmallBuffer(int minSize) throws InterruptedException {
    int attempt = 0;
    while (true) {
      attempt++;
      Buffer result = atomicPoll(S_BUFFERS);
      if (result == null) {
        result = tryMakeBuffer(minSize, S_BUFFER_SIZE);
        if (result == null) {
          // we can't allocate, lets try to find a larger buffer we can de-allocate first
          Buffer larger = atomicPoll(M_BUFFERS);
          if (larger == null) {
            larger = atomicPoll(L_BUFFERS);
          }
          if (larger != null) {
            // use swapBuffer to make sure we get a buffer from de-allocation
            return swapBuffer(larger, S_BUFFER_SIZE);
          } else if (attempt % 2 == 1) {
            // on every other attempt we make sure our failure to allocate
            // is not due to large amounts of small buffers
            trimCache(T_BUFFER_TARGET_COUNT, T_BUFFERS);
          }
        } else {
          return result;
        }
      } else {
        return result;
      }

      noBufferBlock(S_BUFFERS);
    }
  }

  private static Buffer needMediumBuffer(int minSize) throws InterruptedException {
    int attempt = 0;
    while (true) {
      attempt++;
      Buffer result = atomicPoll(M_BUFFERS);
      if (result == null) {
        result = tryMakeBuffer(minSize, M_BUFFER_SIZE);
        if (result == null) {
          // we can't allocate, lets try to find a larger buffer we can de-allocate first
          Buffer larger = atomicPoll(L_BUFFERS);
          if (larger != null) {
            // use swapBuffer to make sure we get a buffer from de-allocation
            return swapBuffer(larger, M_BUFFER_SIZE);
          } else if (attempt % 2 == 1) {
            // make sure failure to allocate is not due to large amounts of small buffers
            // trim which ever queue would save the most memory
            if (((T_BUFFERS.size() - T_BUFFER_TARGET_COUNT)
                * T_BUFFER_SIZE) <= ((S_BUFFERS.size() - S_BUFFER_TARGET_COUNT) * S_BUFFER_SIZE)) {
              trimCache(S_BUFFER_TARGET_COUNT, S_BUFFERS);
            } else {
              trimCache(T_BUFFER_TARGET_COUNT, T_BUFFERS);
            }
          }
        } else {
          return result;
        }
      } else {
        return result;
      }

      noBufferBlock(M_BUFFERS);
    }
  }

  private static Buffer needLargeBuffer(int minSize) throws InterruptedException {
    int attempt = 0;
    while (true) {
      attempt++;
      Buffer result = atomicPoll(L_BUFFERS);
      if (result == null) {
        result = tryMakeBuffer(minSize, L_BUFFER_SIZE);
        if (result == null) {
          if (attempt % 2 == 1) {
            // make sure failure to allocate is not due to large amounts of small buffers
            // trim which ever queue would save the most memory
            int tinySavings = (T_BUFFERS.size() - T_BUFFER_TARGET_COUNT) * T_BUFFER_SIZE;
            int smallSavings = (S_BUFFERS.size() - S_BUFFER_TARGET_COUNT) * S_BUFFER_SIZE;
            int medSavings = (M_BUFFERS.size() - M_BUFFER_TARGET_COUNT) * M_BUFFER_SIZE;
            if (medSavings >= smallSavings && medSavings >= tinySavings) {
              trimCache(M_BUFFER_TARGET_COUNT, M_BUFFERS);
            } else if (smallSavings >= tinySavings) {
              trimCache(S_BUFFER_TARGET_COUNT, S_BUFFERS);
            } else {
              trimCache(T_BUFFER_TARGET_COUNT, T_BUFFERS);
            }
          }
        } else {
          return result;
        }
      } else {
        return result;
      }

      noBufferBlock(L_BUFFERS);
    }
  }

  private static void noBufferBlock(Collection<?> preferredQueue) throws InterruptedException {
    synchronized (preferredQueue) {
      if (preferredQueue.isEmpty()) {
        preferredQueue.wait(50);
      }
    }
  }

  private static Buffer tryMakeBuffer(int minSize, int idealSize) {
    if (TOTAL_ALLOCATED.tryAcquire(idealSize)) {
      return new Buffer(new byte[idealSize], true);
    } else if (TOTAL_ALLOCATED.tryAcquire(minSize)) {
      return new Buffer(new byte[minSize], true);
    } else {
      return null;
    }
  }

  private static void trimCache(int targetSize, Queue<byte[]> buffers) {
    while (buffers.size() > targetSize) {
      byte[] b = buffers.poll();
      if (b != null) {
        releaseBytes(b);
      }
    }
  }

  private static void cacheBytes(byte[] bytes) {
    Queue<byte[]> bufferCache = null;
    if (bytes.length == T_BUFFER_SIZE) {
      bufferCache = T_BUFFERS;
    } else if (bytes.length == S_BUFFER_SIZE) {
      bufferCache = S_BUFFERS;
    } else if (bytes.length == M_BUFFER_SIZE) {
      bufferCache = M_BUFFERS;
    } else if (bytes.length == L_BUFFER_SIZE) {
      bufferCache = L_BUFFERS;
    }

    if (bufferCache == null) {
      releaseBytes(bytes);
    } else {
      bufferCache.add(bytes);

      synchronized (bufferCache) {
        bufferCache.notifyAll();
      }
    }
  }

  // Caches an array the pool holds no reservation for, when it is of a size class and a free
  // reservation covers it now. Otherwise it drops the array: there is no reservation to give back.
  private static void adoptBytes(byte[] bytes) {
    boolean sizeClass = bytes.length == T_BUFFER_SIZE || bytes.length == S_BUFFER_SIZE
        || bytes.length == M_BUFFER_SIZE || bytes.length == L_BUFFER_SIZE;
    if (sizeClass && TOTAL_ALLOCATED.tryAcquire(bytes.length)) {
      cacheBytes(bytes);
    }
  }

  private static void releaseBytes(byte[] b) {
    TOTAL_ALLOCATED.release(b.length);
  }

  private static Buffer swapBuffer(Buffer larger, int newSize) {
    int releaseAmount = larger.bytes.length - newSize;
    synchronized (larger.bytes) {
      if (releaseAmount <= 0) {
        throw new IllegalStateException("Buffer smaller than new allocation");
      } else if (larger.closed) {
        throw new IllegalStateException("Buffer already released");
      }

      larger.closed = true;
    }
    TOTAL_ALLOCATED.release(releaseAmount);
    return new Buffer(new byte[newSize], true);
  }

  /**
   * A byte array with a window over it, lent by {@link BufferPool#needBuffer(int)} or created
   * directly. The window runs from {@link #getOffset()} to the length set by
   * {@link #setLength(int)}, and {@link #getRemaining()} is the difference. {@link #getBuffer()}
   * returns the array itself, not a copy, and nothing here clears its contents.
   *
   * <p>A buffer from {@code needBuffer} belongs to the code that borrowed it, which closes it
   * exactly once. {@link #close()} gives the array back to the pool for reuse, so the array must
   * not be used afterwards: the pool can lend it to another borrower. {@link #closeAsUnhealthy()}
   * gives back the array's reservation, if the buffer holds one, without letting the pool reuse the
   * array. A buffer that is never closed is closed by {@link #finalize()}, with a warning, if and
   * when it is garbage collected.
   *
   * <p>The offset and length are plain fields; a buffer is meant for one thread at a time. The
   * methods check different things: {@link #getBuffer()}, {@link #getRemaining()} and
   * {@link #getOffset()} throw {@code IllegalStateException} once the buffer is closed, while
   * {@link #setLength(int)}, {@link #setOffset(int)}, {@link #incrementOffset(int)} and
   * {@link #flip()} do not check whether it is closed.
   */
  public static class Buffer implements AutoCloseable {
    /**
     * A closed, empty sentinel, the usual poison-pill marker for the end of a sequence of buffers.
     * {@code needBuffer} never returns it and it holds no data: its accessors throw
     * {@code IllegalStateException}, {@link #close()} does nothing, and
     * {@link #closeAsUnhealthy()} throws.
     */
    public static final Buffer POISON_PILL = new Buffer(EMPTY_BYTE_ARRAY) {
      {
        closed = true;
      }
    };

    private final StackTraceElement[] creationStack = TRACK_BUFFER_CREATION_STACK
        ? Thread.currentThread().getStackTrace()
        : null;
    private final byte[] bytes;
    // Whether the pool holds a reservation for the array: true for buffers the pool lends, false
    // for buffers made with a public constructor.
    private final boolean reserved;
    private int offset;
    private int length;
    /** Whether the buffer has been closed. {@link #POISON_PILL} starts closed. */
    protected volatile boolean closed;

    /**
     * Creates a buffer around a new array of {@code size} bytes, with a window over all of it.
     *
     * <p>This is not the same as {@link BufferPool#needBuffer(int)}: it reserves no allocation
     * permits and never waits, so the buffer holds no reservation. {@link #close()} caches the
     * array only if its size matches a size class and the pool can reserve permits for it at that
     * moment without waiting; otherwise it drops the array. Neither {@link #close()} nor
     * {@link #closeAsUnhealthy()} gives back permits for it, since it holds none.
     *
     * @param size the length of the new array
     */
    public Buffer(int size) {
      this(new byte[size], false);
    }

    /**
     * Creates a buffer around {@code bytes} itself, without copying it, with a window over the
     * whole array.
     *
     * <p>Like {@link #Buffer(int)}, it reserves no allocation permits and holds no reservation.
     * {@link #close()} puts {@code bytes} in the pool's cache only if its length matches a size
     * class and the pool can reserve permits for it at that moment without waiting; from there
     * {@link BufferPool#needBuffer(int)} can lend it to another borrower. Otherwise it drops the
     * array. A caller that closes such a buffer therefore no longer owns the array.
     *
     * @param bytes the array to wrap
     */
    public Buffer(byte[] bytes) {
      this(bytes, false);
    }

    private Buffer(byte[] bytes, boolean reserved) {
      this.bytes = bytes;
      this.reserved = reserved;
      this.offset = 0;
      this.length = bytes.length;
    }

    /**
     * Returns the array itself, not a copy. The array can be longer than the window; use
     * {@link #getOffset()} and {@link #getRemaining()} for the part in use.
     *
     * @return the buffer's array
     * @throws IllegalStateException if the buffer is closed
     */
    public byte[] getBuffer() {
      if (closed) {
        throw new IllegalStateException("Buffer closed");
      }
      return bytes;
    }

    /**
     * Returns the size of the window: the length less the offset. It is negative if
     * {@link #setLength(int)} has set the length below the offset.
     *
     * @return the length less the offset
     * @throws IllegalStateException if the buffer is closed
     */
    public int getRemaining() {
      if (closed) {
        throw new IllegalStateException("Buffer closed");
      }
      return length - offset;
    }

    /**
     * Sets where the window ends. It does not move the offset, so an offset beyond the new length
     * stays where it is. It does not check whether the buffer is closed.
     *
     * @param length the new length, from 0 to the array's length
     * @throws IllegalArgumentException if {@code length} is negative or greater than the array's
     *     length
     */
    public void setLength(int length) {
      if (length > bytes.length) {
        throw new IllegalArgumentException("Length beyond byte size");
      } else if (length < 0) {
        throw new IllegalArgumentException("Length can't be negative");
      }

      this.length = length;
    }

    /**
     * Returns where the window starts.
     *
     * @return the offset
     * @throws IllegalStateException if the buffer is closed
     */
    public int getOffset() {
      if (closed) {
        throw new IllegalStateException("Buffer closed");
      }
      return offset;
    }

    /**
     * Moves the offset forward by {@code count}, as {@link #setOffset(int)} does with the current
     * offset plus {@code count}. It does not check whether the buffer is closed.
     *
     * @param count how far to move the offset
     * @throws IllegalArgumentException if the new offset is negative or greater than the length
     */
    public void incrementOffset(int count) {
      setOffset(offset + count);
    }

    /**
     * Sets where the window starts. It does not check whether the buffer is closed.
     *
     * @param offset the new offset, from 0 to the current length
     * @throws IllegalArgumentException if {@code offset} is negative or greater than the length
     */
    public void setOffset(int offset) {
      if (offset > length) {
        throw new IllegalArgumentException("Offset beyond length");
      } else if (offset < 0) {
        throw new IllegalArgumentException("Offset can't be negative");
      }

      this.offset = offset;
    }

    /**
     * Makes the part written so far the window: the length becomes the current offset, and the
     * offset becomes 0. Write by advancing the offset, then flip to read back what was written. It
     * does not check whether the buffer is closed.
     */
    public void flip() {
      this.length = this.offset;
      this.offset = 0;
    }

    /**
     * Closes the buffer without caching the array, so the pool does not lend this array again, and
     * gives back the array's reservation if the buffer holds one: a buffer from
     * {@link BufferPool#needBuffer(int)} does, and one made with a public constructor does not. Use
     * it instead of {@link #close()} when the array should not be reused. It does not clear the
     * array.
     *
     * @throws IllegalStateException if the buffer is already closed
     */
    public void closeAsUnhealthy() {
      synchronized (bytes) {
        if (closed) {
          throw new IllegalStateException("Buffer already released");
        }
        closed = true;
        if (reserved) {
          releaseBytes(bytes);
        }
      }
    }

    /**
     * Closes the buffer and gives its array back. For a buffer from
     * {@link BufferPool#needBuffer(int)}, an array of a size class goes to that class's cache with
     * its reservation, waking callers waiting for it, and an array of any other length is
     * discarded and its reservation given back. A buffer made with a public constructor holds no
     * reservation: its array goes to the cache only if it is of a size class and the pool can
     * reserve permits for it at that moment without waiting, and is otherwise dropped. Closing a
     * closed buffer does nothing. The array is not cleared, and the pool can lend it to another
     * borrower, so it must not be used after this call.
     */
    @Override
    public void close() {
      if (closed) {
        return;
      }
      atomicClose();
    }

    private void atomicClose() {
      synchronized (bytes) {
        if (!closed) {
          closed = true;

          if (reserved) {
            cacheBytes(bytes);
          } else {
            adoptBytes(bytes);
          }
        }
      }
    }

    private Throwable creationLogThrowable() {
      if (creationStack == null) {
        return null;
      } else {
        StackSuppressedRuntimeException ex = new StackSuppressedRuntimeException();
        ex.setStackTrace(creationStack);
        return ex;
      }
    }

    /**
     * Closes a buffer that was never closed, logging the warning {@code Buffer not released!} with
     * the stack where the buffer was created. It runs only if the garbage collector finalizes the
     * buffer, so close buffers explicitly instead of relying on it.
     */
    @Override
    protected void finalize() {
      if (!closed) {
        log.warn("Buffer not released!", creationLogThrowable());
        atomicClose();
      }
    }
  }
}
