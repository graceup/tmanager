package com.igustudio.tmanager.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class BackwardsFileStream extends InputStream {
    private RandomAccessFile raf;
    private long seekPos;

    public BackwardsFileStream(File file) throws IOException {
        raf = new RandomAccessFile(file, "r");
        seekPos = raf.length();
    }

    public BackwardsFileStream(File file, long pos) throws IOException {
        raf = new RandomAccessFile(file, "r");
        seekPos = pos;
    }

    public int read() throws IOException {
        if (seekPos > 0) {
            raf.seek(--seekPos);
            return raf.read();
        } else {
            //
            // return EOF (so to speak)
            //
            return -1;
        }
    }

    public void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }
}
