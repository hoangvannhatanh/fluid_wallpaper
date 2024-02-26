package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;


public class MultisampleConfigChooser implements GLSurfaceView.EGLConfigChooser {
    private static final String kTag = "GDC11";
    private boolean mUsesCoverageAa;
    private int[] mValue;

    @Override // android.opengl.GLSurfaceView.EGLConfigChooser
    public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
        EGLConfig[] eGLConfigArr;
        int[] iArr = new int[1];
        this.mValue = iArr;
        int[] iArr2 = {12340, 12344, 12324, 5, 12323, 6, 12322, 5, 12325, 16, 12352, 4, 12338, 1, 12337, 2, 12344};
        if (!egl10.eglChooseConfig(eGLDisplay, iArr2, null, 0, iArr)) {
            LogUtil.i("configchooser", "msaa not found");
        }
        int[] iArr3 = this.mValue;
        int i = 0;
        int i2 = iArr3[0];
        if (i2 <= 0) {
            int[] iArr4 = {12340, 12344, 12324, 5, 12323, 6, 12322, 5, 12325, 16, 12352, 4, 12512, 1, 12513, 2, 12344};
            if (!egl10.eglChooseConfig(eGLDisplay, iArr4, null, 0, iArr3)) {
                LogUtil.i("configchooser", "csaa not found");
            }
            int[] iArr5 = this.mValue;
            int i3 = iArr5[0];
            if (i3 <= 0) {
                int[] iArr6 = {12340, 12344, 12324, 5, 12323, 6, 12322, 5, 12325, 16, 12352, 4, 12344};
                if (!egl10.eglChooseConfig(eGLDisplay, iArr6, null, 0, iArr5)) {
                    LogUtil.i("configchooser", "is there ANY config?!");
                }
                int i4 = this.mValue[0];
                if (i4 <= 0) {
                    LogUtil.i("configchooser", "Damn. There isn't.");
                }
                iArr2 = iArr6;
                i2 = i4;
            } else {
                this.mUsesCoverageAa = true;
                LogUtil.i("configchooser", "CSAA config found");
                i2 = i3;
                iArr2 = iArr4;
            }
        } else {
            LogUtil.i("configchooser", "MSAA config found");
        }
        int i5 = i2;
        EGLConfig[] eGLConfigArr2 = new EGLConfig[i5];
        if (!egl10.eglChooseConfig(eGLDisplay, iArr2, eGLConfigArr2, i5, this.mValue)) {
            LogUtil.i("configchooser", "rotfl. Don't know what happened");
        }
        LogUtil.i("configchooser", "num configs " + i5);
        int i6 = 0;
        while (true) {
            if (i6 >= i5) {
                eGLConfigArr = eGLConfigArr2;
                i6 = -1;
                break;
            }
            eGLConfigArr = eGLConfigArr2;
            if (findConfigAttrib(egl10, eGLDisplay, eGLConfigArr2[i6], 12324, 0) == 8) {
                break;
            }
            i6++;
            eGLConfigArr2 = eGLConfigArr;
        }
        if (i6 == -1) {
            while (true) {
                if (i >= i5) {
                    break;
                } else if (findConfigAttrib(egl10, eGLDisplay, eGLConfigArr[i], 12324, 0) == 5) {
                    i6 = i;
                    break;
                } else {
                    i++;
                }
            }
        }
        if (i6 != -1) {
            LogUtil.i("configchooser", "config index chosen: " + i6 + ", with red channel size: " + findConfigAttrib(egl10, eGLDisplay, eGLConfigArr[i6], 12324, 0));
        }
        if (i6 == -1) {
            LogUtil.w(kTag, "Did not find sane config, using first");
        }
        EGLConfig eGLConfig = i5 > 0 ? eGLConfigArr[i6] : null;
        if (eGLConfig != null) {
            return eGLConfig;
        }
        throw new IllegalArgumentException("No config chosen");
    }

    private int findConfigAttrib(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
        return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.mValue) ? this.mValue[0] : i2;
    }

    public boolean usesCoverageAa() {
        return this.mUsesCoverageAa;
    }
}
