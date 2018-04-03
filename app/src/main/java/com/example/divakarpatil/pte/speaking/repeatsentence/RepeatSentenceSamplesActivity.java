package com.example.divakarpatil.pte.speaking.repeatsentence;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divakarpatil.pte.R;
import com.example.divakarpatil.pte.speaking.SectionMethodActivity;
import com.example.divakarpatil.pte.speaking.readaloud.ResultActivity;
import com.example.divakarpatil.pte.utils.ParagraphResult;
import com.example.divakarpatil.pte.utils.SectionType;

import java.util.ArrayList;
import java.util.Locale;

public class RepeatSentenceSamplesActivity extends AppCompatActivity {

    private int recordingNumber = 0;
    private ImageView nextButton;
    private ParagraphResult result = null;
    private Double ratingForParagraph = 0.0;
    private double accuracy = 0.0;
    private ArrayList<ParagraphResult> paragraphResults = new ArrayList<>();
    private SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    private TextView paragraphNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_sentence_samples);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nextButton = findViewById(R.id.repeatSentenceNextButton);

        paragraphNumberTextView = findViewById(R.id.paragraphNumberTextView);

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
                recordingNumber++;
                int fieldsLength = R.raw.class.getFields().length;
                boolean isLastParagraph = recordingNumber == (fieldsLength);

                result = new ParagraphResult(recordingNumber, accuracy, ratingForParagraph, Html.toHtml(spannableStringBuilder, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
                paragraphResults.add(result);


                if (isLastParagraph) {
                    Intent intent = new Intent(RepeatSentenceSamplesActivity.this, ResultActivity.class);
                    intent.putParcelableArrayListExtra("Paragraph_Results", paragraphResults);
                    intent.putExtra("PARENT_ACTIVITY", "RepeatSentenceSamplesActivity");
                    startActivity(intent);
                    return;
                }
                paragraphNumberTextView.setText(String.format(Locale.getDefault(), "%d", recordingNumber + 1));
                nextButton.setBackground(recordingNumber == fieldsLength - 1 ? getDrawable(R.drawable.ic_done_all_black_24dp)
                        : getDrawable(R.drawable.ic_navigate_next_black_24dp));
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
