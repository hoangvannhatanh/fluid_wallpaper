package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

import android.util.Log;

import java.io.Writer;

/* compiled from: GLWallpaperServiceRBG.java */

class LogWriter extends Writer {
    private StringBuilder mBuilder = new StringBuilder();

    LogWriter() {
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flushBuilder();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        flushBuilder();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                flushBuilder();
            } else {
                this.mBuilder.append(c);
            }
        }
    }

    private void flushBuilder() {
        if (this.mBuilder.length() > 0) {
            Log.v("GLSurfaceView", this.mBuilder.toString());
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
        }
    }
}
