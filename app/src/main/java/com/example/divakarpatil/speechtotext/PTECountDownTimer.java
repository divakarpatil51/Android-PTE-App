package com.example.divakarpatil.speechtotext;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Locale;

/**
 * PTE countdown timer
 * Created by divakar.patil on 21-03-2018.
 */

public class PTECountDownTimer extends CountDownTimer {

    private TextView textView;

    PTECountDownTimer(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setText(String.format(Locale.getDefault(), "00:%2d", (millisUntilFinished / 1000)));
    }

    @Override
    public void onFinish() {

    }
}
