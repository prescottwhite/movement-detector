package com.cse118.movementdetector;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

public class MovementService extends IntentService {

    private SensorListener sensorListener;

    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;

    private final IBinder binder = new LocalBinder();

    public MovementService() {
        super("MovementService");
    }

    public class LocalBinder extends Binder {
        public MovementService getService() {
            return MovementService.this;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            sensorListener = new SensorListener(this);
            sensorListener.run();

            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AppName:WakeLock");
            wakeLock.acquire();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        wakeLock.release();
        super.onDestroy();
    }

    public void resetService() {
        
    }
}
