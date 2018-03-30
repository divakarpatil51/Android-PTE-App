package com.example.divakarpatil.pte.utils;

/**
 * Enum for PTE SectionType
 * Created by divakar.patil on 25-03-2018.
 */

public enum SectionType {

    PERSONAL_INTRODUCTION("Personal Introduction", "Speaking/Personal_Introduction.html"),

    READ_ALOUD("Read Aloud", "Speaking/Read_Aloud.html"),
    REPEAT_SENTENCE("Repeat Sentence", "Speaking/Repeat_Sentence.html"),
    DESCRIBE_IMAGE("Describe Image", "Speaking/Describe_Image.html"),
    RETELL_LECTURE("Re-tell Lecture", "Speaking/Retell_Lecture.html"),
    ANSWER_SHORT_QUESTIONS("Answer Short Questions", "Speaking/Answer_Short_Question.html"),

    SUMMARIZE_WRITTEN_TEXT("Summarize Written Text", "Writing/Summarize_Written_Text.html"),
    WRITE_ESSAY("Write Essay", "Writing/Essay.html"),

    READING_MCC_SINGLE_ANSWER("Multiple Choice Choose Single Answer", "Reading/Reading_Multiple_Choice_Single_Answer.html"),
    READING_MCC_MULTIPLE_ANSWER("Multiple Choice Choose Multiple Answer", "Reading/Reading_Multiple_Choice_Multiple_Answer.html"),
    REORDER_PARAGRAPH("Re-order Paragraphs", "Reading/Reorder_Paragraphs.html"),
    READING_FILL_IN_THE_BLANKS("Reading - Fill In The Blanks", "Reading/Reading_Fill_In_The_Blanks.html"),
    READING_WRITING_FILL_IN_THE_BLANKS("Reading and Writing - Fill In The Blanks", "Reading/Reading_Writing_Fill_In_The_Blanks.html"),

    SUMMARIZE_SPOKEN_TEXT("Summarize Spoken Text", "Listening/Summarize_Spoken_Text.html"),
    LISTENING_MCC_MULTIPLE_ANSWER("Listening - Multiple Choice Choose Multiple Answer", "Listening/Listening_Multiple_Choice_Multiple_Answer.html"),
    LISTENING_MCC_SINGLE_ANSWER("Listening - Multiple Choice Choose Single Answer", "Listening/Listening_Multiple_Choice_Single_Answer.html"),
    LISTENING_FILL_IN_THE_BLANKS("Listening - Fill In The Blanks", "Listening/Listening_Fill_In_The_Blanks.html"),
    HIGHLIGHT_CORRECT_SUMMARY("Highlight Correct Summary", "Listening/Highlight_Correct_Summary.html"),
    HIGHLIGHT_INCORRECT_WORD("Highlight Incorrect Words", "Listening/Highlight_Incorrect_Words.html"),
    SELECT_MISSING_WORD("Select Missing Word", "Listening/Select_Missing_Word.html"),
    WRITE_FROM_DICTATION("Write From Dictation", "Listening/Write_From_Dictation.html");

    String sectionName, filePath;

    SectionType(String sectionName, String filePath) {
        this.sectionName = sectionName;
        this.filePath = filePath;
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

    public String getFilePath() {
        return filePath;
    }
}
