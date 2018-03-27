package com.example.divakarpatil.pte.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for Paragraph result
 * Created by divakar.patil on 25-03-2018.
 */

public class ParagraphResult implements Parcelable {

    public static final Creator<ParagraphResult> CREATOR = new Creator<ParagraphResult>() {
        @Override
        public ParagraphResult createFromParcel(Parcel in) {
            return new ParagraphResult(in);
        }

        @Override
        public ParagraphResult[] newArray(int size) {
            return new ParagraphResult[size];
        }
    };
    private Integer paragraphNumber;
    private Double accuracyPercentage;
    private Double paragraphRating;
    private String paragraphRecording;

    public ParagraphResult(Integer paragraphNumber, Double accuracyPercentage, Double paragraphRating, String paragraphRecording) {
        this.paragraphNumber = paragraphNumber;
        this.accuracyPercentage = accuracyPercentage;
        this.paragraphRating = paragraphRating;
        this.paragraphRecording = paragraphRecording;
    }

    protected ParagraphResult(Parcel in) {
        paragraphNumber = in.readInt();
        accuracyPercentage = in.readDouble();
        paragraphRating = in.readDouble();
        paragraphRecording = in.readString();
    }

    public static Creator<ParagraphResult> getCREATOR() {
        return CREATOR;
    }

    public Integer getParagraphNumber() {
        return paragraphNumber;
    }

    public Double getAccuracyPercentage() {
        return accuracyPercentage;
    }

    public Double getParagraphRating() {
        return paragraphRating;
    }

    public String getParagraphRecording() {
        return paragraphRecording;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(paragraphNumber);
        dest.writeDouble(accuracyPercentage);
        dest.writeDouble(paragraphRating);
        dest.writeString(paragraphRecording);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
