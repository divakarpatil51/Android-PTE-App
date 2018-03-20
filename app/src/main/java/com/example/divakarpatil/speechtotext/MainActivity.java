package com.example.divakarpatil.speechtotext;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.divakarpatil.speechtotext.R.string.speech_record_stop;

public class MainActivity extends AppCompatActivity implements RecognitionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_RECORD_VOICE = 100;
    private static int currentParagraph = 0;
    private TextView timerTextView, sentencesTextView, accuracyPercentageView, paragraphNumberTextView;
    private Button speechToTextButton, showRecordButton, nextButton, previousButton;
    private SpeechRecognizer recognizer;
    private ArrayList<String> matches = new ArrayList<>(1);
    private SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
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

        accuracyPercentageView = findViewById(R.id.accuracyPercentage);
        sentencesTextView = findViewById(R.id.sentencesTextView);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        paragraphNumberTextView = findViewById(R.id.paragraphNumberTextView);

        SentencesJsonReader.readJson(this);
        previousButton.setEnabled(false);
        sentencesTextView.setText(SentencesJsonReader.getParagraph(currentParagraph));

        timerTextView = findViewById(R.id.timerTextView);

        speechToTextButton = findViewById(R.id.speechRecorderButton);
        speechToTextButton.setOnClickListener(getSpeechToTextListener());

        showRecordButton = findViewById(R.id.showRecordTextButton);
        showRecordButton.setEnabled(false);
        showRecordButton.setOnClickListener(getShowRecordButtonListener());

        handlePermission();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentencesTextView.setText(SentencesJsonReader.getParagraph(++currentParagraph));
                previousButton.setEnabled(currentParagraph != 0);
                nextButton.setEnabled(SentencesJsonReader.getJsonDataSize() != currentParagraph);
                paragraphNumberTextView.setText(String.format(Locale.getDefault(), "%d", currentParagraph));
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentencesTextView.setText(SentencesJsonReader.getParagraph(--currentParagraph));
                previousButton.setEnabled(currentParagraph != 0);
                nextButton.setEnabled(SentencesJsonReader.getJsonDataSize() != currentParagraph);
                paragraphNumberTextView.setText(String.format(Locale.getDefault(), "%d", currentParagraph));
            }
        });
    }

    private void handlePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_RECORD_VOICE);
        }
    }

    @NonNull
    private View.OnClickListener getShowRecordButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(spannableStringBuilder).setTitle(R.string.app_name);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @NonNull
    private View.OnClickListener getSpeechToTextListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speechToTextButton.getText().equals("Start Recording")) {
                    timer.start();
                    speechToTextButton.setText(speech_record_stop);
                    accuracyPercentageView.setText("");
                    spannableStringBuilder = new SpannableStringBuilder();
                    startVoiceInput();
                } else {
                    timer.cancel();
                    speechToTextButton.setText(R.string.speech_record_start);
                    recognizer.stopListening();
                    showRecordButton.setEnabled(false);
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
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(this);
        recognizer.startListening(intent);
    }

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
    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int error) {
        Log.i(TAG, "onError: " + error);
        if (error == SpeechRecognizer.ERROR_CLIENT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Error while recording audio");
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            builder.setNeutralButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.cancel();
                }
            });

        }
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(TAG, "onResults: ");
        matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches == null || speechToTextButton.getText().equals("Stop Recording")) {
            return;
        }

        String[] myText = matches.get(0).toLowerCase().split(" ");
        String[] originalText = sentencesTextView.getText().toString().toLowerCase().split(" ");
        int correctWords = 0;

        for (int i = 0; i < originalText.length; i++) {
            try {
                SpannableString string = new SpannableString(myText[i] + " ");
                if (originalText[i].equals(myText[i])) {
                    correctWords++;
                } else {
                    string.setSpan(new ForegroundColorSpan(Color.RED), 0, myText[i].length(), 0);
                }
                spannableStringBuilder.append(string);
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        double accuracy = ((double) correctWords / (double) originalText.length) * 100;
        accuracyPercentageView.setText(String.format(Locale.getDefault(), "%.2f", accuracy));
        showRecordButton.setEnabled(true);
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
