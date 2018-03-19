package com.example.divakarpatil.speechtotext;

import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Json Reader class for Sentences
 * Created by divakar.patil on 14-03-2018.
 */

public class SentencesJsonReader {
    private static ArrayList<String> jsonArray = new ArrayList<>();

    public static void readJson() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new BufferedReader(new FileReader(new File("/sentences.json"))));
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                jsonArray.add(reader.nextString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
