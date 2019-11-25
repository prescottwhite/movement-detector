package com.cse118.movementdetector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Date;

public class SensorListener implements SensorEventListener, Runnable {

    private float xInit;
    private float yInit;

    private Date timeZero;
    private Date timeMoved;

    private final int TIME_WEIGHT = 5000;

    private boolean isMoved;

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;

    SensorListener(Context context) {
        xInit = 0f;
        yInit = SensorManager.GRAVITY_EARTH;

        timeZero = new Date();
        timeMoved = null;

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (((Math.abs(event.values[0]) - xInit) > 0.5) || ((Math.abs(event.values[1]) - yInit) > 0.5)) {
                timeMoved = new Date();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void run() {
        isMoved = false;
        while(true) {
            synchronized (this) {
                if(timeMoved != null) {
                    isMoved = didItMove();
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean getIsMoved() {
        return isMoved;
    }

    public boolean didItMove() {
        if ((timeMoved != null) && (timeMoved.getTime() > timeZero.getTime() + TIME_WEIGHT)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void reset() {

    }
}