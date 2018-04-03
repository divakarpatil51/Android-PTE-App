package com.example.divakarpatil.pte.speaking.repeatsentence;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.divakarpatil.pte.R;
import com.example.divakarpatil.pte.speaking.SectionMethodActivity;
import com.example.divakarpatil.pte.speaking.readaloud.ResultActivity;
import com.example.divakarpatil.pte.utils.ParagraphResult;
import com.example.divakarpatil.pte.utils.SectionType;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RepeatSentenceSamplesActivity extends AppCompatActivity {

    private int recordingNumber = 1;
    private ImageView nextButton;
    private ParagraphResult result = null;
    private Double ratingForParagraph = 0.0;
    private double accuracy = 0.0;
    private ArrayList<ParagraphResult> paragraphResults = new ArrayList<>();
    private SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_sentence_samples);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nextButton = findViewById(R.id.repeatSentenceNextButton);
        ImageView repeatSentencePlayImageView = findViewById(R.id.repeatSentencePlayImageView);
        repeatSentencePlayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int currentResId = (int) R.raw.class.getFields()[recordingNumber].get("methods");
                    MediaPlayer player = MediaPlayer.create(RepeatSentenceSamplesActivity.this, currentResId);
                    player.start();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Rest", "Recording number: " + R.raw.class.getFields()[recordingNumber].getName());
                result = new ParagraphResult(recordingNumber, accuracy, ratingForParagraph, Html.toHtml(spannableStringBuilder, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
                paragraphResults.add(result);

                Field[] fields = R.raw.class.getFields();
                if (recordingNumber > fields.length) {
                    Intent intent = new Intent(RepeatSentenceSamplesActivity.this, ResultActivity.class);
                    intent.putParcelableArrayListExtra("Paragraph_Results", paragraphResults);
                    intent.putExtra("PARENT_ACTIVITY", "RepeatSentenceSamplesActivity");
                    startActivity(intent);
                    return;
                }
                boolean isLastParagraph = recordingNumber == fields.length;

                nextButton.setBackground(isLastParagraph ? getDrawable(R.drawable.ic_done_all_black_24dp)
                        : getDrawable(R.drawable.ic_navigate_next_black_24dp));
                recordingNumber++;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = getParentActivityIntent();
                if (intent == null) {
                    intent = new Intent(RepeatSentenceSamplesActivity.this, SectionMethodActivity.class);
                }
                NavUtils.navigateUpTo(RepeatSentenceSamplesActivity.this, intent.putExtra("Section", SectionType.REPEAT_SENTENCE));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
