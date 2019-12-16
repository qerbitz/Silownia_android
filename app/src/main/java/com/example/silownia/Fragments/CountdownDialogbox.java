package com.example.silownia.Fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.silownia.R;

import java.util.Locale;

public class CountdownDialogbox extends Dialog implements
    View.OnClickListener {

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;


    public Activity activity;
    public Dialog d;
    public Button exit;
    public TextView timeText;
    private long RestTime;



    public CountdownDialogbox(Activity a, int pRestTime) {
        super(a);
        this.activity = a;
        RestTime = pRestTime*1000;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getContext().getResources().getString(R.string.Resttime));
        setContentView(R.layout.dialog_rest);
        this.setCanceledOnTouchOutside(true); // make it not modal



        exit = findViewById(R.id.btn_exit);
        timeText= findViewById(R.id.timeText);
        exit.setOnClickListener(this);


        startTimer();

        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);


    }

    public void vibration(){
        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(RestTime, 1000) {
            @Override
            public void onTick(long s_to_end) {
                RestTime = s_to_end;
                updateCountDownText();
                if(RestTime<=5000)vibration();
            }

            @Override
              public void onFinish() {
              mTimerRunning = false;
              dismiss();
              }
              }.start();

             mTimerRunning = true;
        }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_exit) {
            //chrono.stop();
           // chrono.setText("00:00");
            dismiss();

        }
    }

    private void updateCountDownText() {
        int minutes = (int) (RestTime / 1000) / 60;
        int seconds = (int) (RestTime / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timeText.setText(timeLeftFormatted);
    }


}
