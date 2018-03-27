package com.example.divakarpatil.pte.utils;

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

public class SentencesJsonReader {

    private static ArrayList<String> jsonArray;

    public static void readJson(Context context) {
        jsonArray = new ArrayList<>();
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

    public static String getParagraph(int i) {
        return jsonArray.get(i);
    }

    public static int getJsonDataSize() {
        return jsonArray.size();
    }
}
