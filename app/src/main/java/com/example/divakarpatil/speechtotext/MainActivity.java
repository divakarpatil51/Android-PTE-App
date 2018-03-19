package com.example.divakarpatil.speechtotext;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.divakarpatil.speechtotext.R.string.speech_record_stop;

public class MainActivity extends AppCompatActivity implements RecognitionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private TextView timerTextView, speechToTextConversionBox, sentencesTextView, accuracyPercentageView;
    private Button speechToTextButton;
    private SpeechRecognizer recognizer;
    private CountDownTimer timer = new CountDownTimer(40000, 1) {
        @Override
        public void onTick(long millisUntilFinished) {
            timerTextView.setText(String.format(Locale.getDefault(), "00:%2d", (millisUntilFinished / 1000)));
        }

        @Override
        public void onFinish() {
            speechToTextButton.setText(speech_record_stop);
            speechToTextButton.performClick();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        speechToTextConversionBox = findViewById(R.id.speechRecordingTextView);

        sentencesTextView = findViewById(R.id.sentencesTextView);
        sentencesTextView.setText(R.string.sample_sentence);

        timerTextView = findViewById(R.id.timerTextView);

        speechToTextButton = findViewById(R.id.speechRecorderButton);
        speechToTextButton.setOnClickListener(getSpeechToTextListener());
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
            }
        }
    }

    @NonNull
    private View.OnClickListener getSpeechToTextListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speechToTextButton.getText().equals("Start Recording")) {
                    timer.start();
                    speechToTextButton.setText(speech_record_stop);
                    speechToTextConversionBox.setText("");
                    accuracyPercentageView.setText("");
                    startVoiceInput();
                } else {
                    timer.cancel();
                    speechToTextButton.setText(R.string.speech_record_start);
                    recognizer.stopListening();
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        speechToTextButton.setText(R.string.speech_record_start);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(this);
        recognizer.startListening(intent);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(TAG, "onReadyForSpeech:");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech: ");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(TAG, "onRmsChanged: ");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(TAG, "onBufferReceived: ");
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech: ");
    }

    @Override
    public void onError(int error) {
        Log.i(TAG, "onError: " + error);
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(TAG, "onResults: ");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches == null) {
            return;
        }
        speechToTextConversionBox.setText(matches.get(0));

        String[] originalText = speechToTextConversionBox.getText().toString().toLowerCase().split(" ");
        String[] myText = sentencesTextView.getText().toString().toLowerCase().split(" ");
        int correctWords = 0;
        for (int i = 0; i < originalText.length; i++) {
            if (originalText[i].equals(myText[i])) {
                correctWords++;
            }
        }
        double accuracy = ((double) correctWords / (double) originalText.length) * 100;
        accuracyPercentageView = findViewById(R.id.accuracyPercentage);
        accuracyPercentageView.setText(String.format(Locale.getDefault(), "%.2f", accuracy));
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(TAG, "onPartialResults: ");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(TAG, "onEvent: ");
    }
}
