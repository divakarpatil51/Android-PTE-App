package com.example.divakarpatil.pte.speaking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.divakarpatil.pte.R;
import com.example.divakarpatil.pte.speaking.readaloud.ReadAloudSamplesActivity;
import com.example.divakarpatil.pte.speaking.repeatsentence.RepeatSentenceSamplesActivity;
import com.example.divakarpatil.pte.utils.PTETextFileReader;
import com.example.divakarpatil.pte.utils.SectionType;

import java.io.IOException;


public class SectionMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_aloud_method);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final SectionType section = (SectionType) getIntent().getSerializableExtra("Section");

        setTitle(section.getSectionName());
        try {
            WebView readAloudMethodView = findViewById(R.id.readAloudMethod);
            PTETextFileReader reader = new PTETextFileReader(getAssets().open(section.getFilePath()));
            readAloudMethodView.loadDataWithBaseURL(null, reader.getFileData(), "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button tryItButton = findViewById(R.id.tryItButton);
        tryItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (SectionType.getSectionType(section.getSectionName())) {
                    case READ_ALOUD:
                        intent = new Intent(SectionMethodActivity.this, ReadAloudSamplesActivity.class);
                        startActivity(intent);
                        break;
                    case REPEAT_SENTENCE:
                        intent = new Intent(SectionMethodActivity.this, RepeatSentenceSamplesActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(SectionMethodActivity.this, "Not yet implemented", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
