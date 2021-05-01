package com.zitherharp.zhmusic.player;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OfflinePlayer {
    MediaPlayer mediaPlayer;

    public OfflinePlayer() {
        mediaPlayer = new MediaPlayer();
        setListener();
    }

    public void setSong(String path) throws IOException {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepare();
    }

    void setListener() {
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.seekTo(0);
            mp.pause();
        });
    }

    String formatDuration(int duration) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public int getCurrentTime() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getTotalTime() {
        return mediaPlayer.getDuration();
    }

    public String getCurrentTimeToString() {
        return formatDuration(getCurrentTime());
    }

    public String getTotalTimeToString() {
        return formatDuration(getTotalTime());
    }

    public void seekTo(int duration) {
        mediaPlayer.seekTo(duration);
    }

    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }
}
