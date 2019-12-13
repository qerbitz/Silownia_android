package com.example.silownia.Fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.easyfitness.utils.AlarmReceiver;
import com.easyfitness.utils.UnitConverter;
import com.example.silownia.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.text.DecimalFormat;

import gr.antoniom.chronometer.Chronometer;
import gr.antoniom.chronometer.Chronometer.OnChronometerTickListener;

public class CountdownDialogbox extends Dialog implements
    View.OnClickListener {

    public Activity activity;
    public Dialog d;
    public Button exit;
    public TextView chronoValue;
    public Chronometer chrono;
    private int iRestTime = 60;


    private OnChronometerTickListener onChronometerTick = new OnChronometerTickListener() {
        boolean bFirst = true;
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            int secElapsed = (int) (chrono.getTimeElapsed() / 1000); //secElapsed is a negative value

            if (secElapsed >= 0) {
                chrono.stop();
                dismiss();
            }
        }
    };

    public CountdownDialogbox(Activity a, int pRestTime) {
        super(a);
        this.activity = a;
        iRestTime = pRestTime;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getContext().getResources().getString(R.string.Resttime));
        setContentView(R.layout.dialog_rest);
        this.setCanceledOnTouchOutside(true); // make it not modal



        exit = findViewById(R.id.btn_exit);
        chronoValue = findViewById(R.id.chronoValue);
        exit.setOnClickListener(this);


        //SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getContext());
        //int defaultUnit = Integer.valueOf(SP.getString("defaultUnit", "0"));


        chrono.setOnChronometerTickListener(onChronometerTick);
        chrono.setBase(SystemClock.elapsedRealtime() + (iRestTime + 1) * 1000);
        chrono.setPrecision(false);
        chrono.start();

        setOnDismissListener(onDismissChrono);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_exit) {
            chrono.stop();
            chrono.setText("00:00");
            dismiss();
        }
    }


}
