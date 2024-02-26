package com.magicfluids;


public class MotionEventWrapper {
    static final int EVENT_DOWN = 0;
    static final int EVENT_MOVE = 2;
    static final int EVENT_POINTER_DOWN = 5;
    static final int EVENT_POINTER_UP = 6;
    static final int EVENT_UP = 1;
    public int ID;
    public float PosX;
    public float PosY;
    public int Type;

    
    public void set(MotionEventWrapper motionEventWrapper) {
        this.Type = motionEventWrapper.Type;
        this.PosX = motionEventWrapper.PosX;
        this.PosY = motionEventWrapper.PosY;
        this.ID = motionEventWrapper.ID;
    }
}
