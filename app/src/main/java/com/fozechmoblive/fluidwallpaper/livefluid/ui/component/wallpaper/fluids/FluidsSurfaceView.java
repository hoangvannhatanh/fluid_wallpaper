package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class FluidsSurfaceView extends GLSurfaceView {
    public FluidsSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FluidsSurfaceView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        InputBuffer.Instance.addEvent(motionEvent);
        return true;
    }

    @Override // android.opengl.GLSurfaceView
    public void onPause() {
        super.onPause();
    }

    @Override // android.opengl.GLSurfaceView
    public void onResume() {
        super.onResume();
    }
}
