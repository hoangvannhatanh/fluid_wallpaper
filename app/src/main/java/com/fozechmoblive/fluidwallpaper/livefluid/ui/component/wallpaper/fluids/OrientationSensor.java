package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.WindowManager;


public class OrientationSensor implements SensorEventListener {
    public float AccelerationX;
    public float AccelerationY;
    public int Orientation;
    private float[] acceleration;
    private Sensor accelerometer;
    private Application application;
    private boolean registered;
    private SensorManager sensorManager;

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public OrientationSensor(Context context, Application application) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensorManager = sensorManager;
        this.accelerometer = sensorManager.getDefaultSensor(1);
        this.application = application;
        this.registered = false;
    }

    public void register() {
        if (this.registered) {
            return;
        }
        this.sensorManager.registerListener(this, this.accelerometer, 3);
        this.registered = true;
    }

    public void unregister() {
        if (this.registered) {
            this.sensorManager.unregisterListener(this);
            this.registered = false;
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] fArr = (float[]) sensorEvent.values.clone();
        this.acceleration = fArr;
        this.AccelerationX = fArr[1];
        this.AccelerationY = fArr[0];
        this.Orientation = ((WindowManager) this.application.getSystemService("window")).getDefaultDisplay().getRotation();
    }
}
