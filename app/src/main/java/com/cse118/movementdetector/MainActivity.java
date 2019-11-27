package com.cse118.movementdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewStatus;
    private Button mButtonReset;
    private Button mButtonExit;

    private MovementService movementService;

    private final long TIME_WAIT = 15000;

    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewStatus = findViewById(R.id.AM_textView_moved);
        mButtonReset = findViewById(R.id.AM_button_reset);
        mButtonExit = findViewById(R.id.AM_button_exit);

        mTextViewStatus.setText(R.string.status_not_moved);

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        mButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, MovementService.class);
        this.startService(intent);
        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (isBound && movementService != null) {
            Date timeNow = new Date();
            if ((movementService.didItMove()) && (movementService.getFirstTimeMoved() != 0) && (timeNow.getTime() > movementService.getFirstTimeMoved() + TIME_WAIT)) {
                mTextViewStatus.setText(R.string.status_moved);
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MovementService.LocalBinder localBinder = (MovementService.LocalBinder) service;
            movementService = localBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            movementService = null;
            isBound = false;
        }
    };

    public void reset() {
        mTextViewStatus.setText(R.string.status_not_moved);
        if (movementService != null) {
            movementService.reset();
        }
    }
}
