package com.example.divakarpatil.pte.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.divakarpatil.pte.R;

import java.util.ArrayList;

public class PTEMainActivity extends AppCompatActivity {

    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> childItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptemain);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ExpandableListView expandableListView = findViewById(R.id.list);
        expandableListView.setDividerHeight(2);
        expandableListView.setGroupIndicator(null);
        expandableListView.setClickable(true);

        setGroupItems();
        setChildItems();

        PTEListAdapter listAdapter = new PTEListAdapter(parentItems, childItems);
        listAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableListView.setAdapter(listAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    private void setGroupItems() {
        parentItems.add("Speaking");
        parentItems.add("Writing");
        parentItems.add("Reading");
        parentItems.add("Listening");
    }

    private void setChildItems() {
        ArrayList<String> array = new ArrayList<>();
        array.add(getStringFromId(R.string.personalIntroduction));
        array.add(getStringFromId(R.string.readAloud));
        array.add(getStringFromId(R.string.repeatSentence));
        array.add(getStringFromId(R.string.describeImage));
        array.add(getStringFromId(R.string.retellLecture));
        array.add(getStringFromId(R.string.shortQuestions));
        childItems.add(array);

        array = new ArrayList<>();
        array.add(getStringFromId(R.string.summarizeWrittenText));
        array.add(getStringFromId(R.string.essay));
        childItems.add(array);

        array = new ArrayList<>();
        array.add(getStringFromId(R.string.multipleChoiceMultipleAnswer));
        array.add(getStringFromId(R.string.multipleChoiceSingleAnswer));
        array.add(getStringFromId(R.string.reorderParagraph));
        array.add(getStringFromId(R.string.rFillUps));
        array.add(getStringFromId(R.string.rwFillUps));
        childItems.add(array);

        array = new ArrayList<>();
        array.add(getStringFromId(R.string.summarizeSpokenText));
        array.add(getStringFromId(R.string.listMCMA));
        array.add(getStringFromId(R.string.listMCSA));
        array.add(getStringFromId(R.string.highlightSummary));
        array.add(getStringFromId(R.string.lFillUps));
        array.add(getStringFromId(R.string.missingWord));
        array.add(getStringFromId(R.string.highlightIncorrect));
        array.add(getStringFromId(R.string.writeFromDictation));
        childItems.add(array);
    }


    public String getStringFromId(int id) {
        return getBaseContext().getString(id);
    }
}
