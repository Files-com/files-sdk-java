package com.files.util;

import com.files.util.BufferPool.Buffer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.IntConsumer;

public class StreamTransfer {
    private static final int INITIAL_BUFFER_SIZE = Math.min(1024 * 8, BufferPool.T_BUFFER_SIZE);

    public static long smartCopy(long knownSize, InputStream in, OutputStream out) throws IOException {
        return smartCopy(knownSize, in, out, null);
    }

    public static long smartCopy(long knownSize, InputStream in, OutputStream out,
                                 IntConsumer progressConsumer) throws IOException {
        // in the future full duplex and other copies may be possible
        return bufferedCopy(knownSize, in, out, progressConsumer);
    }

    public static long bufferedCopy(long knownSize, InputStream in, OutputStream out,
                                    IntConsumer progressConsumer) throws IOException {
        long total = 0;
        try (Buffer b = BufferPool.needBuffer(INITIAL_BUFFER_SIZE)) {
            int c;
            byte[] buffer = b.getBuffer();
            long remaining = Long.MAX_VALUE;
            while ((knownSize < 1 || (remaining = knownSize - total) > 0)
                && (c = in.read(buffer, 0, (int) Math.min(remaining, INITIAL_BUFFER_SIZE))) >= 0) {
                if (c > 0) {
                    out.write(buffer, 0, c);
                    total += c;
                    if (progressConsumer != null) {
                        progressConsumer.accept(c);
                    }
                } else {
                    Thread.yield();
                }
            }
        }
        return total;
    }
}
