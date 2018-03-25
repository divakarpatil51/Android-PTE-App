package com.example.divakarpatil.pte.utils;

/**
 * Enum for PTE SectionType
 * Created by divakar.patil on 25-03-2018.
 */

public enum SectionType {

    PERSONAL_INTRODUCTION("Personal Introduction", "Personal_Introduction.html"),

    READ_ALOUD("Read Aloud", "Read_Aloud.html"),
    REPEAT_SENTENCE("Repeat Sentence", "Repeat_Sentence.html"),
    DESCRIBE_IMAGE("Describe Image", "Describe_Image.html"),
    RETELL_LECTURE("Re-tell Lecture", "Retell_lecture.html"),
    ANSWER_SHORT_QUESTIONS("Answer Short Questions", "Answer_Short_Questions.html"),

    SUMMARIZE_WRITTEN_TEXT("Summarize Written Text", "Summarize_Written_Text.html"),
    WRITE_ESSAY("Write Essay", "Write_Essay.html"),

    READING_MCC_SINGLE_ANSWER("Multiple Choice Choose Single Answer", ""),
    READING_MCC_MULTIPLE_ANSWER("Multiple Choice Choose Multiple Answer", ""),
    REORDER_PARAGRAPH("Re-order Paragraphs", ""),
    READING_FILL_IN_THE_BLANKS("Reading - Fill In The Blanks", ""),
    READING_WRITING_FILL_IN_THE_BLANKS("Reading and Writing - Fill In The Blanks", ""),

    SUMMARIZE_SPOKEN_TEXT("Summarize Spoken Text", ""),
    LISTENING_MCC_MULTIPLE_ANSWER("Listening - Multiple Choice Choose Multiple Answer", ""),
    LISTENING_MCC_SINGLE_ANSWER("Listening - Multiple Choice Choose Single Answer", ""),
    LISTENING_FILL_IN_THE_BLANKS("Listening - Fill In The Blanks", ""),
    HIGHLIGHT_CORRECT_SUMMARY("Highlight Correct Summary", ""),
    HIGHLIGHT_INCORRECT_WORD("Highlight Incorrect Words", ""),
    SELECT_MISSING_WORD("Select Missing Word", ""),
    WRITE_FROM_DICTATION("Write From Dictation", "");

    String sectionName, fileName;

    SectionType(String sectionName, String fileName) {
        this.sectionName = sectionName;
        this.fileName = fileName;
    }

    public static SectionType getSectionType(String sectionName) {
        for (SectionType section : SectionType.values()) {
            if (sectionName.equals(section.getSectionName())) {
                return section;
            }
        }
        return READ_ALOUD;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getFileName() {
        return fileName;
    }
}
