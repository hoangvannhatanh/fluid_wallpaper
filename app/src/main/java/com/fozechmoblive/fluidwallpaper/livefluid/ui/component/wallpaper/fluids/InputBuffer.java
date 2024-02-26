package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

import android.view.MotionEvent;

import com.magicfluids.MotionEventWrapper;

import java.util.concurrent.ArrayBlockingQueue;


/* loaded from: classes.dex */
public class InputBuffer {
    public static InputBuffer Instance = new InputBuffer();
    static final int MAX_EVENTS = 1024;
    private ArrayBlockingQueue<MotionEventWrapper> eventPool;
    private ArrayBlockingQueue<MotionEventWrapper> newEvents;

    InputBuffer() {
        init();
    }

    void init() {
        this.eventPool = new ArrayBlockingQueue<>(1024);
        this.newEvents = new ArrayBlockingQueue<>(1024);
        for (int i = 0; i < 1024; i++) {
            this.eventPool.add(new MotionEventWrapper());
        }
    }

    boolean addNewEvent(int i, int i2, float f, float f2) {
        MotionEventWrapper poll = this.eventPool.poll();
        if (poll == null) {
            return false;
        }
        poll.Type = i;
        poll.PosX = f;
        poll.PosY = f2;
        poll.ID = i2;
        this.newEvents.add(poll);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        int i = 0;
        if (actionMasked == 0) {
            for (int i2 = 0; i2 < motionEvent.getPointerCount() && addNewEvent(0, motionEvent.getPointerId(i2), motionEvent.getX(i2), motionEvent.getY(i2)); i2++) {
            }
        } else if (actionMasked == 1 || actionMasked == 3) {
            while (i < motionEvent.getPointerCount() && addNewEvent(1, motionEvent.getPointerId(i), motionEvent.getX(i), motionEvent.getY(i))) {
                i++;
            }
        } else {
            if (actionMasked == 5) {
                int actionIndex = motionEvent.getActionIndex();
                if (!addNewEvent(5, motionEvent.getPointerId(actionIndex), motionEvent.getX(actionIndex), motionEvent.getY(actionIndex))) {
                    return;
                }
            }
            if (actionMasked == 6) {
                int actionIndex2 = motionEvent.getActionIndex();
                if (!addNewEvent(6, motionEvent.getPointerId(actionIndex2), motionEvent.getX(actionIndex2), motionEvent.getY(actionIndex2))) {
                    return;
                }
            }
            if (actionMasked == 2) {
                while (i < motionEvent.getPointerCount() && addNewEvent(2, motionEvent.getPointerId(i), motionEvent.getX(i), motionEvent.getY(i))) {
                    i++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getCurrentInputState(Input input) {
        input.NumEvents = 0;
        int size = this.newEvents.size();
        while (input.NumEvents < size) {
            MotionEventWrapper poll = this.newEvents.poll();
            if (poll != null) {
                MotionEventWrapper[] motionEventWrapperArr = input.Events;
                int i = input.NumEvents;
                input.NumEvents = i + 1;
                motionEventWrapperArr[i].set(poll);
                this.eventPool.add(poll);
            }
        }
    }
}
