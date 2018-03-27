package com.example.divakarpatil.pte.speaking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.divakarpatil.pte.R;
import com.example.divakarpatil.pte.dashboard.PTEMainActivity;
import com.example.divakarpatil.pte.utils.ParagraphResult;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements RecyclerViewFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ArrayList<ParagraphResult> result = getIntent().getParcelableArrayListExtra("Paragraph_Results");
        RecyclerViewFragment fragment = RecyclerViewFragment.newInstance(result);
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();

        ImageView gotoHomeView = findViewById(R.id.gotoHomeView);
        gotoHomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PTEMainActivity.class);
                startActivity(intent);
            }
        });

        ImageView restartTheTest = findViewById(R.id.restartTheTest);
        restartTheTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ReadAloudSamplesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
