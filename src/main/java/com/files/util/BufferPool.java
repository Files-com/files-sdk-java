package com.files.util;

import java.util.Collection;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import com.files.FilesConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.threadly.util.StackSuppressedRuntimeException;

public class BufferPool {
    private static final Logger log = LogManager.getLogger(BufferPool.class);
    private static final boolean TRACK_BUFFER_CREATION_STACK = true;
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final int T_BUFFER_SIZE = FilesConfig.getInstance().getCachedBufferTinySize();
    public static final int S_BUFFER_SIZE = FilesConfig.getInstance().getCachedBufferSmallSize();
    public static final int M_BUFFER_SIZE = FilesConfig.getInstance().getCachedBufferMediumSize();
    public static final int L_BUFFER_SIZE = FilesConfig.getInstance().getCachedBufferLargeSize();
    protected static final int T_BUFFER_TARGET_COUNT;
    protected static final int S_BUFFER_TARGET_COUNT;
    protected static final int M_BUFFER_TARGET_COUNT;
    protected static final int MAX_ALLOCATION;
    protected static final Semaphore TOTAL_ALLOCATED;
    protected static final Queue<byte[]> T_BUFFERS = new ConcurrentLinkedQueue<>();
    protected static final Queue<byte[]> S_BUFFERS = new ConcurrentLinkedQueue<>();
    protected static final Queue<byte[]> M_BUFFERS = new ConcurrentLinkedQueue<>();
    protected static final Queue<byte[]> L_BUFFERS = new ConcurrentLinkedQueue<>();

    static {
        MAX_ALLOCATION = FilesConfig.getInstance().getCachedBufferMaxBytes();
        TOTAL_ALLOCATED = new Semaphore(MAX_ALLOCATION);
        T_BUFFER_TARGET_COUNT = (int) ((MAX_ALLOCATION * .25) / T_BUFFER_SIZE);
        S_BUFFER_TARGET_COUNT = (int) ((MAX_ALLOCATION * .25) / S_BUFFER_SIZE);
        M_BUFFER_TARGET_COUNT = (int) ((MAX_ALLOCATION * .25) / M_BUFFER_SIZE);
        // remaining % is provided as minimum cached allocations to large buffers
    }

    public static int getAvailableAllocation() {
        return TOTAL_ALLOCATED.availablePermits();
    }

    public static int getTotalAllocated() {
        return MAX_ALLOCATION - TOTAL_ALLOCATED.availablePermits();
    }

    public static int getWaitingToAllocateThreadCount() {
        return TOTAL_ALLOCATED.getQueueLength();
    }

    public static int availableTinyBufferCount() {
        return T_BUFFERS.size();
    }

    public static int availableSmallBufferCount() {
        return S_BUFFERS.size();
    }

    public static int availableMediumBufferCount() {
        return M_BUFFERS.size();
    }

    public static int availableLargeBufferCount() {
        return L_BUFFERS.size();
    }

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
            return new Buffer(bytes);
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
                        if (((T_BUFFERS.size() - T_BUFFER_TARGET_COUNT) * T_BUFFER_SIZE)
                                <= ((S_BUFFERS.size() - S_BUFFER_TARGET_COUNT) * S_BUFFER_SIZE)) {
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

    private static void noBufferBlock(Collection<?> preferedQueue) throws InterruptedException {
        synchronized (preferedQueue) {
            if (preferedQueue.isEmpty()) {
                preferedQueue.wait(50);
            }
        }
    }

    private static Buffer tryMakeBuffer(int minSize, int idealSize) {
        if (TOTAL_ALLOCATED.tryAcquire(idealSize)) {
            return new Buffer(idealSize);
        } else if (TOTAL_ALLOCATED.tryAcquire(minSize)) {
            return new Buffer(minSize);
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
        return new Buffer(newSize);
    }

    public static class Buffer implements AutoCloseable {
        public static final Buffer POISON_PILL = new Buffer(EMPTY_BYTE_ARRAY) {
            {
                closed = true;
            }
        };

        private final StackTraceElement[] creationStack =
                TRACK_BUFFER_CREATION_STACK ? Thread.currentThread().getStackTrace() : null;
        private final byte[] bytes;
        private int offset;
        private int length;
        protected volatile boolean closed = false;

        public Buffer(int size) {
            this.bytes = new byte[size];
            this.offset = 0;
            this.length = size;
        }

        public Buffer(byte[] bytes) {
            this.bytes = bytes;
            this.offset = 0;
            this.length = bytes.length;
        }

        public byte[] getBuffer() {
            if (closed) {
                throw new IllegalStateException("Buffer closed");
            }
            return bytes;
        }

        public int getRemaining() {
            if (closed) {
                throw new IllegalStateException("Buffer closed");
            }
            return length - offset;
        }

        public void setLength(int length) {
            if (length > bytes.length) {
                throw new IllegalArgumentException("Length beyond byte size");
            } else if (length < 0) {
                throw new IllegalArgumentException("Length can't be negative");
            }

            this.length = length;
        }

        public int getOffset() {
            if (closed) {
                throw new IllegalStateException("Buffer closed");
            }
            return offset;
        }

        public void incrementOffset(int count) {
            setOffset(offset + count);
        }

        public void setOffset(int offset) {
            if (offset > length) {
                throw new IllegalArgumentException("Offset beyond length");
            } else if (offset < 0) {
                throw new IllegalArgumentException("Offset can't be negative");
            }

            this.offset = offset;
        }

        public void flip() {
            this.length = this.offset;
            this.offset = 0;
        }

        public void closeAsUnhealthy() {
            synchronized (bytes) {
                if (closed) {
                    throw new IllegalStateException("Buffer already released");
                }
                closed = true;
                releaseBytes(bytes);
            }
        }

        @Override
        public void close() {
            if (closed) {
                return;
            }
            atomicClose();
        }

        private void atomicClose() {
            synchronized (bytes) {
                if (! closed) {
                    closed = true;

                    cacheBytes(bytes);
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

        @Override
        protected void finalize() {
            if (! closed) {
                log.warn("Buffer not released!", creationLogThrowable());
                atomicClose();
            }
        }
    }
}
