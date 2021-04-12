package com.zitherharp.zhmusic.ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.controller.MusicController;
import com.zitherharp.zhmusic.controller.PlayerController;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.player.SongPlayer;
import com.zitherharp.zhmusic.provider.SongProvider;
import com.zitherharp.zhmusic.service.MusicService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    static final int LAUNCH_SIGNIN_ACTIVITY = 1000;

    AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    Toolbar toolbar;
    TextView tvNavHeaderTitle, tvNavHeaderSubtitle;
    ImageView ivNavHeaderImage;
    SongPlayer songPlayer;
    MediaPlayer mediaPlayer;
    Intent playIntent;

    public static ArrayList<Song> songList;
    private MusicService playerService;
    private boolean musicBound = false;
    MusicController controller;
    PlayerController playerController;
    boolean playbackPaused = false;

    // Connect with the service
    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            playerService = binder.getService();
            playerService.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

        @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SIGNIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                final String account_display_name = data.getStringExtra("account_display_name");
                final String account_user_name = data.getStringExtra("account_user_name");
                //final String account_photo_uri = data.getStringExtra("account_photo_uri");
                // Sử dụng kết quả result bằng cách hiện Toast
                tvNavHeaderTitle.setText(account_display_name);
                tvNavHeaderSubtitle.setText(account_user_name);
                //ivNavHeaderImage.setImageURI(Uri.parse(account_photo_uri));
            } else {
                Toast.makeText(this, "Sign in unsucessfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void findViewById() {
        // toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // drawerLayout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // navigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        tvNavHeaderTitle = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        tvNavHeaderSubtitle = navigationView.getHeaderView(0).findViewById(R.id.nav_user_info);
        ivNavHeaderImage = navigationView.getHeaderView(0).findViewById(R.id.nav_user_avatar);
        // appBarConfiguration
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_library, R.id.nav_playlist, R.id.nav_favourite)
                .setDrawerLayout(drawer).build();
        // navigationController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    void initialize() {
        mediaPlayer = new MediaPlayer();
        songPlayer = new SongPlayer(this);
        SongProvider songProvider = new SongProvider(this);
        songList = songProvider.getSongList();
        Collections.sort(songList, (lhs, rhs) -> lhs.getTitle().compareTo(rhs.getTitle()));
    }

    public void songPicked(@NotNull View v) throws IOException {

    }

    void setController() {
        controller = new MusicController(this);
        controller.setPrevNextListeners(v -> playNext(), v -> playPrev());
        controller.setMediaPlayer((MediaController.MediaPlayerControl) this);
        controller.setAnchorView(findViewById(R.id.lvLibrary));
        controller.setEnabled(true);
    }

    void playNext() {
        playerService.playNext();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    void playPrev() {
        playerService.playPrev();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    public void goSigninActivity(View v) {
        startActivityForResult(
                new Intent(this, SigninActivity.class), LAUNCH_SIGNIN_ACTIVITY);
    }

    public void goPlayerActivity(View v) {
        Intent playerIntent = new Intent(this, PlayerActivity.class);
        startActivity(playerIntent);
    }
}