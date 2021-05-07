package com.zitherharp.zhmusic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.Random;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer player;
    private ArrayList<Song> songs;
    private int songPos;
    private final IBinder musicBind = new MusicBinder();
    private String songTitle = "";
    private static final int NOTIFY_ID = 1;
    private boolean shuffle = false;
    private Random rand;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        songPos = 0;
        // Khởi tạo một bộ phát đa phương tiện mới.
        player = new MediaPlayer();
    }

    public void initMusicPlayer() {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnErrorListener(this);
        player.setOnCompletionListener(this);
    }

    public void setList(ArrayList<Song> theSongs) {
        songs = theSongs;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (player.getCurrentPosition() > 0) {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
        this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder nBuilder = new Notification.Builder(this);
        nBuilder.setContentIntent(pendingIntent).setTicker(songTitle).setSmallIcon(R.drawable.ring_btnplay).setOngoing(true)
            .setContentTitle("Playing").setContentText(songTitle);
        Notification notif = nBuilder.getNotification();
        startForeground(NOTIFY_ID, notif);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    public void playSong() {

    }

    public void setSong(int songIndex) {
        songPos = songIndex;
    }

    public int getSongPos() {
        return player.getCurrentPosition();
    }

    public int getDur() {
        return player.getDuration();
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void pausePlayer() {
        player.pause();
    }

    public void seek(int pos) {
        player.seekTo(pos);
    }

    public void go() {
        player.start();
    }

    public void playPrev() {
        songPos--;
        if (songPos < 0) songPos = songs.size() - 1;
            playSong();
    }

    public void playNext() {
        if (shuffle) {
            int newSongPos = songPos;
            while (newSongPos == songPos) {
                newSongPos = rand.nextInt(songs.size());
            }
            songPos = newSongPos;
        } else {
            songPos++;
            if (songPos >= songs.size()) songPos = 0;
            }
        playSong();
        }

        @Override
    public void onDestroy() {
        stopForeground(true);
    }

    public void setShuffle() {
        if (shuffle) shuffle = false;
        else shuffle = true;
    }
}