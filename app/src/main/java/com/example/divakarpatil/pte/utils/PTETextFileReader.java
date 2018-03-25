package com.example.divakarpatil.pte.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Text file reader for PTE SectionType
 * <p>
 * Created by divakar.patil on 24-03-2018.
 */

public class PTETextFileReader {

    private InputStream inputStream;
    private String line = null;

    public PTETextFileReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileData() {

        try {
            byte[] buffer = new byte[inputStream.available()];
            int input = inputStream.read(buffer);
            if (input == 0)
                return "";
            inputStream.close();
            line = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

}
