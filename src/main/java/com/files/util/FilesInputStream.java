package com.files.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class FilesInputStream extends InputStream implements Closeable {
    @Override
    public int read() throws IOException {
        return 0;
    }

    public long transferTo(OutputStream out) throws IOException{
        throw new IOException("transferTo not implemented");
    }
}
