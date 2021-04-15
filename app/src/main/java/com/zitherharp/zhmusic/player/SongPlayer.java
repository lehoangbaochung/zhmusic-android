package com.zitherharp.zhmusic.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.MediaController;

import com.zitherharp.zhmusic.ui.activity.MainActivity;

import java.io.IOException;

public class SongPlayer extends MediaPlayer implements MediaController.MediaPlayerControl {
    MediaPlayer mediaPlayer;
    MainActivity mainActivity;

    public SongPlayer(MainActivity mainActivity) {
        //this.mainActivity = mainActivity;
        mediaPlayer = new MediaPlayer();
    }

    public void onPrepare(String path) throws IOException {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepareAsync();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    public void onStart() {
        mediaPlayer.setOnPreparedListener(mp -> {
            mediaPlayer.reset();
            mediaPlayer.start();
        });
    }
}
