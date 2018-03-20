package com.example.divakarpatil.speechtotext;

import android.content.Context;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Json Reader class for Sentences
 * <p>
 * Created by Divakar Patil on 14-03-2018.
 */

class SentencesJsonReader {

    private static ArrayList<String> jsonArray = new ArrayList<>();

    static void readJson(Context context) {
        try (InputStream inputStream = context.getAssets().open("sentences.json");
             JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(inputStream)))) {

            reader.beginObject();
            while (reader.hasNext()) {
                reader.nextName();
                jsonArray.add(reader.nextString());
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String getParagraph(int i) {
        return jsonArray.get(i);
    }

    static int getJsonDataSize() {
        return jsonArray.size() - 1;
    }
}
