package com.zitherharp.zhmusic.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.player.OfflinePlayer;
import com.zitherharp.zhmusic.player.OnlinePlayer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends YouTubeBaseActivity implements View.OnClickListener {
    SeekBar sbTime;
    ImageView btnCollapse;
    ImageView btnPlay, btnPrev, btnNext;
    ImageView btnFavourite, btnPlayback, btnDownload, btnShare, btnPlaylist;
    TextView tvCurrentTime, tvTotalTime;
    TextView tvSongTitle, tvArtistName, tvSongFullTitle;

    OnlinePlayer onlinePlayer;
    OfflinePlayer offlinePlayer;

    YouTubePlayerView playerView;

    Timer timer;
    String videoId;
    boolean isOnlinePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        findViewById();
        setOnClickListener();
        loadSong();
        onTimer();
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        timer.purge();
        offlinePlayer.pause();
        super.onDestroy();
    }

    void findViewById() {
        playerView = findViewById(R.id.youtube_view);
        sbTime = findViewById(R.id.time_seekbar);
        // buttons
        btnCollapse = findViewById(R.id.collapse_button);
        btnPlay = findViewById(R.id.play_button);
        btnPrev = findViewById(R.id.prev_song_button);
        btnNext = findViewById(R.id.next_song_button);
        btnFavourite = findViewById(R.id.favourite_button);
        btnPlayback = findViewById(R.id.playback_button);
        btnDownload = findViewById(R.id.download_button);
        btnShare = findViewById(R.id.share_button);
        btnPlaylist = findViewById(R.id.playlist_button);
        // textviews
        tvCurrentTime = findViewById(R.id.current_time);
        tvTotalTime = findViewById(R.id.total_time);
        tvSongTitle = findViewById(R.id.song_title);
        tvArtistName = findViewById(R.id.artist_name);
        tvSongFullTitle = findViewById(R.id.song_full_title);
    }

    void setOnClickListener() {
        btnCollapse.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnFavourite.setOnClickListener(this);
        btnPlayback.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnPlaylist.setOnClickListener(this);
    }

    void loadSong() {
        Intent songIntent = getIntent();
        tvSongTitle.setText(songIntent.getStringExtra("song_title"));
        tvArtistName.setText(songIntent.getStringExtra("artist_name"));
        tvSongFullTitle.setText(songIntent.getStringExtra("song_full_title"));
        videoId = songIntent.getStringExtra("video_id");

        onlinePlayer = new OnlinePlayer(this);
        offlinePlayer = new OfflinePlayer();

        if (videoId.contains("/")) {
            isOnlinePlayer = false;
            playerView.setVisibility(View.GONE);
            try {
                offlinePlayer.setSong(videoId);
                offlinePlayer.play();
            } catch (IOException e) {
                Toast.makeText(this, "Media error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            isOnlinePlayer = true;
            playerView.setVisibility(View.VISIBLE);
            onlinePlayer.setVideoId(videoId);
            onlinePlayer.onInitialize(playerView);
        }
    }

    void onTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(() -> {
                    if (isOnlinePlayer) {
                        sbTime.setProgress(onlinePlayer.getCurrentTime());
                        sbTime.setMax(onlinePlayer.getTotalTime());
                        tvCurrentTime.setText(onlinePlayer.getCurrentTimeToString());
                        tvTotalTime.setText(onlinePlayer.getTotalTimeToString());
                    } else {
                        sbTime.setProgress(offlinePlayer.getCurrentTime());
                        sbTime.setMax(offlinePlayer.getTotalTime());
                        tvCurrentTime.setText(offlinePlayer.getCurrentTimeToString());
                        tvTotalTime.setText(offlinePlayer.getTotalTimeToString());
                    }
                });
            }
        }, 0, 1000);

        sbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (isOnlinePlayer) {
                        onlinePlayer.seekTo(progress);
                    } else {
                        offlinePlayer.seekTo(progress);
                    }
                }
                play(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(@NotNull View v) {
        int id = v.getId();
        if (id == R.id.play_button) {
            play(true);
        } else if (id == R.id.next_song_button) {
            onlinePlayer.next();
        } else if (id == R.id.prev_song_button) {
            onlinePlayer.previous();
        } else if (id == R.id.favourite_button) {
            addToFavourite();
        } else if (id == R.id.playback_button) {
            changePlaybackMode();
        } else if (id == R.id.download_button) {
            downloadSong();
        } else if (id == R.id.share_button) {
            shareSong();
        } else if (id == R.id.playlist_button) {
            addToPlaylist();
        } else if (id == R.id.collapse_button) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    void addToFavourite() {
        Drawable drawable = getDrawable(R.drawable.player_btn_favorite_normal);
        if (btnFavourite.getDrawable() == drawable) {
            btnFavourite.setImageResource(R.drawable.mymusic_icon_favorite_normal);
        } else {
            btnFavourite.setImageDrawable(drawable);
        }
    }

    void changePlaybackMode() {
        if (isOnlinePlayer) {
            int playbackMode = onlinePlayer.getPlaybackMode();
            if (playbackMode == 0) {
                onlinePlayer.setPlaybackMode(1);
                btnPlayback.setImageResource(R.drawable.player_btn_repeatone_normal);
                Toast.makeText(this, "Repeat one", Toast.LENGTH_SHORT).show();
            } else if (playbackMode == 1) {
                onlinePlayer.setPlaybackMode(2);
                btnPlayback.setImageResource(R.drawable.player_btn_random_normal);
                Toast.makeText(this, "Shuffle", Toast.LENGTH_SHORT).show();
            } else {
                onlinePlayer.setPlaybackMode(0);
                btnPlayback.setImageResource(R.drawable.player_btn_repeat_normal);
                Toast.makeText(this, "Repeat all", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (offlinePlayer.isLooping()) {
                offlinePlayer.setLooping(false);
                btnPlayback.setImageResource(R.drawable.player_btn_repeat_normal);
                Toast.makeText(this, "Repeat one is off", Toast.LENGTH_SHORT).show();
            } else {
                offlinePlayer.setLooping(true);
                btnPlayback.setImageResource(R.drawable.player_btn_repeatone_normal);
                Toast.makeText(this, "Repeat one is on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void downloadSong() {
        if (isOnlinePlayer) {
            Intent downloadIntent = new Intent(Intent.ACTION_VIEW);
            downloadIntent.setData(Uri.parse("https://www.y2mate.com/youtube/" + videoId));
            startActivity(downloadIntent);
        } else {
            Toast.makeText(this, "This song saved in your phone", Toast.LENGTH_SHORT).show();
        }
    }

    void shareSong() {
        Intent shareIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "I want to share a beautiful song with you!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, String.format("%s: %s\n%s: %s",
                "Song title", tvSongTitle.getText(), "Artist name", tvArtistName.getText()));
        startActivity(Intent.createChooser(shareIntent, "Share to..."));
    }

    void addToPlaylist() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Create the AlertDialog object and return it
        builder.setPositiveButton("Add", (dialog, id) -> {

        }).setNegativeButton("Cancel", (dialog, id) -> finish());
    }

    void play(boolean fromUser) {
        if (fromUser) {
            if (isOnlinePlayer) {
                if (onlinePlayer.isPlaying()) {
                    onlinePlayer.pause();
                    btnPlay.setImageResource(R.drawable.ring_btnplay);
                } else {
                    onlinePlayer.play();
                    btnPlay.setImageResource(R.drawable.pause_button);
                }
            } else {
                if (offlinePlayer.isPlaying()) {
                    offlinePlayer.pause();
                    btnPlay.setImageResource(R.drawable.ring_btnplay);
                } else {
                    offlinePlayer.play();
                    btnPlay.setImageResource(R.drawable.pause_button);
                }
            }
        } else {
            if (isOnlinePlayer) {
                if (onlinePlayer.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.pause_button);
                } else {
                    btnPlay.setImageResource(R.drawable.ring_btnplay);
                }
            } else {
                if (offlinePlayer.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.pause_button);
                } else {
                    btnPlay.setImageResource(R.drawable.ring_btnplay);
                }
            }
        }
    }
}