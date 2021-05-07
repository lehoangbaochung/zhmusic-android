package com.zitherharp.zhmusic.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.zitherharp.zhmusic.R;

public class PlayerActivity extends Activity {
    SeekBar sbTime;
    ImageView btnCollapse;
    ImageView btnPlay, btnPrev, btnNext;
    ImageView btnFavourite, btnPlayback, btnDownload, btnShare, btnPlaylist;
    TextView tvCurrentTime, tvTotalTime;
    TextView tvSongTitle, tvArtistName, tvSongFullTitle;

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.enableBackgroundPlayback(true);
        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
            this.youTubePlayer = youTubePlayer;
        });

        btnPlay = findViewById(R.id.play_button);
        btnPlay.setOnClickListener(v -> youTubePlayer.pause());

        tvSongTitle = findViewById(R.id.song_title);
        tvArtistName = findViewById(R.id.artist_name);

        tvSongTitle.setText(getIntent().getStringExtra("song_title"));
        tvArtistName.setText(getIntent().getStringExtra("artist_name"));
    }
}