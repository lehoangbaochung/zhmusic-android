package com.zitherharp.zhmusic.player;

import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.zitherharp.zhmusic.ui.activity.PlayerActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OnlinePlayer implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener {
    PlayerActivity playerActivity;
    YouTubePlayer player;
    String videoId;
    boolean isLoaded;
    int playbackMode = 0;

    public OnlinePlayer(PlayerActivity playerActivity) {
        this.playerActivity = playerActivity;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.player = youTubePlayer;
        player.loadVideo(videoId);
        player.setFullscreen(false);
        player.setPlayerStateChangeListener(this);
        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(playerActivity, "Error: " + youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
    }

    public void onInitialize(@NotNull YouTubePlayerView youTubePlayerView) {
        youTubePlayerView.initialize("AIzaSyB5Jgo4jTYPq5Nep-7k2KqQCHjV4wbWC-w", this);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String videoId) {
        if (!TextUtils.isEmpty(videoId) && player != null) {
            player.play();
            isLoaded = true;
        }
    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {
        isLoaded = false;
        if (playbackMode == 0) {
            player.seekToMillis(0);
        } else if (playbackMode == 1) {
            player.seekToMillis(0);
        } else {
            player.seekToMillis(0);
            player.pause();
        }
        playerActivity.recreate();
    }

    @Override
    public void onError(YouTubePlayer.@NotNull ErrorReason errorReason) {
        Toast.makeText(playerActivity, "Failed: " + errorReason.name(), Toast.LENGTH_LONG).show();
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void play() {
        if (isLoaded) {
            player.play();
        }
    }

    public void pause() {
        if (isLoaded) {
            player.pause();
        }
    }

    public boolean isPlaying() {
        if (isLoaded) {
            return player.isPlaying();
        } else {
            return false;
        }
    }

    public String getTotalTimeToString() {
        if (isLoaded && player != null) {
            return formatDuration(player.getDurationMillis());
        } else {
            return "00:00";
        }
    }

    public String getCurrentTimeToString() {
        if (isLoaded && player != null) {
            return formatDuration(player.getCurrentTimeMillis());
        } else {
            return "00:00";
        }
    }

    public int getTotalTime() {
        if (isLoaded && player != null) {
            return player.getDurationMillis();
        } else {
            return 0;
        }
    }

    public int getCurrentTime() {
        if (isLoaded && player != null) {
            return player.getCurrentTimeMillis();
        } else {
            return 0;
        }
    }

    public void seekTo(int i) {
        if (isLoaded) {
            player.seekToMillis(i);
        }
    }

    public void next() {
        if (isLoaded && player.hasNext()) {
            player.next();
        } else {
            Toast.makeText(playerActivity, "No next songs", Toast.LENGTH_SHORT).show();
        }
    }

    public void previous() {
        if (isLoaded && player.hasPrevious()) {
            player.previous();
        } else {
            Toast.makeText(playerActivity, "No previous songs", Toast.LENGTH_SHORT).show();
        }
    }

    public void setPlaybackMode(int playbackMode) {
        this.playbackMode = playbackMode;
    }

    public int getPlaybackMode() {
        return playbackMode;
    }

    String formatDuration(int duration) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

}
