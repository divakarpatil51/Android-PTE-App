package com.example.divakarpatil.pte.utils;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.util.Log;

/**
 * Custom implementation of Recognition listener
 * Created by divakar.patil on 21-03-2018.
 */

public class PTERecognitionListener implements RecognitionListener {

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i("RecognitionListener", "onBufferReceived: " + buffer[0]);
    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {

    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
