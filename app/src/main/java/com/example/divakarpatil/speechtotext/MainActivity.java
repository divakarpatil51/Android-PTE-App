package com.example.divakarpatil.speechtotext;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_RECORD_VOICE = 100;
    private static int currentParagraph = 0;
    private TextView timerTextView, sentencesTextView, accuracyPercentageView, paragraphNumberTextView;
    private Button speechToTextButton, showRecordButton, nextButton, previousButton;
    private SpeechRecognizer recognizer;
    private SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    private PTECountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        handlePermission();
        SentencesJsonReader.readJson(this);

        timer = new PTECountDownTimer(timerTextView, 40000, 1);

        showRecordButton.setEnabled(false);
        previousButton.setEnabled(false);

        sentencesTextView.setText(SentencesJsonReader.getParagraph(currentParagraph));

        speechToTextButton.setOnClickListener(getSpeechToTextListener());
        showRecordButton.setOnClickListener(getShowRecordButtonListener());
        nextButton.setOnClickListener(handleNextButton());
        previousButton.setOnClickListener(handlePreviousButtonListener());
    }

    @NonNull
    private View.OnClickListener handlePreviousButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleParagraphChange(--currentParagraph);
            }
        };
    }

    @NonNull
    private View.OnClickListener handleNextButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleParagraphChange(++currentParagraph);
            }
        };
    }

    private void handleParagraphChange(int currentParagraph) {

        sentencesTextView.setText(SentencesJsonReader.getParagraph(currentParagraph));
        previousButton.setEnabled(currentParagraph != 0);
        nextButton.setEnabled(SentencesJsonReader.getJsonDataSize() != currentParagraph);
        paragraphNumberTextView.setText(String.format(Locale.getDefault(), "%d", currentParagraph));
        resetTimer();
    }

    private void initializeViews() {

        accuracyPercentageView = findViewById(R.id.accuracyPercentage);
        sentencesTextView = findViewById(R.id.sentencesTextView);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        paragraphNumberTextView = findViewById(R.id.paragraphNumberTextView);
        timerTextView = findViewById(R.id.timerTextView);
        speechToTextButton = findViewById(R.id.speechRecorderButton);
        showRecordButton = findViewById(R.id.showRecordTextButton);
    }

    private void handlePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_RECORD_VOICE);
        }
    }

    private void resetTimer() {
        timer.cancel();
        timerTextView.setText(getBaseContext().getString(R.string.time));
    }

    @NonNull
    private View.OnClickListener getShowRecordButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                AlertDialog dialog = builder.setMessage(spannableStringBuilder)
                        .setTitle(getApplicationContext().getString(R.string.accuracy) + ": " + accuracyPercentageView.getText())
                        .create();
                dialog.show();
            }
        };
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
                    nextButton.setEnabled(false);
                    previousButton.setEnabled(false);
                } else {
                    timer.cancel();
                    speechToTextButton.setText(R.string.speech_record_start);
                    recognizer.stopListening();
                    showRecordButton.setEnabled(false);
                    nextButton.setEnabled(currentParagraph != SentencesJsonReader.getJsonDataSize());
                    previousButton.setEnabled(currentParagraph != 0);
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
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 3000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1000000);

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(addRecognitionListener());
        recognizer.startListening(intent);
    }

    @NonNull
    private PTERecognitionListener addRecognitionListener() {

        return new PTERecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                Log.i(TAG, "onResults: ");
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches == null) {
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
        };
    }
}
