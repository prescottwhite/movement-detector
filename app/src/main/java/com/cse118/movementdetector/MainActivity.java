package com.cse118.movementdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewStatus;
    private Button mButtonReset;
    private Button mButtonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewStatus = findViewById(R.id.AM_textView_moved);
        mButtonReset = findViewById(R.id.AM_button_reset);
        mButtonExit = findViewById(R.id.AM_button_exit);

        mButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
