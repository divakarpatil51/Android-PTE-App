package com.example.divakarpatil.pte.utils;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

/**
 * Class to handle Voice Recording functionality
 * Created by divakar.patil on 23-03-2018.
 */

public class PTEVoicePlayer {

    //TODO: Check how to record voice and do STT synchronously
    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null;
    private String mediaFilePath;

    public PTEVoicePlayer(String mediaFilePath) {
        this.mediaFilePath = mediaFilePath;
    }

    public void startVoiceRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setOutputFile(new File(mediaFilePath).getPath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    public void stopVoiceRecorder() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public void startPlayingVoice() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mediaFilePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public void stopPlayingVoice() {
        mediaPlayer.stop();
        mediaPlayer = null;
    }
}
