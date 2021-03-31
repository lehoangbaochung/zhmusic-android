package com.zitherharp.zhmusic.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.controller.PlayerController;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.service.PlayerService;

import java.util.ArrayList;
import java.util.Collections;

//public class MainActivity extends AppCompatActivity {
//    private AppBarConfiguration mAppBarConfiguration;
//    private NavController navController;
//    private  Toolbar toolbar;
//    Context context;
//    public static final int RUNTIME_PERMISSION_CODE = 7;
//    String[] ListElements = new String[] { };
//    ListView listView;
//    List<String> ListElementsArrayList ;
//    ArrayAdapter<String> adapter ;
//    ContentResolver contentResolver;
//    Cursor cursor;
//    Uri uri;
//    Button button;
//    ImageView imageView;
//    public static View playerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        listView = findViewById(R.id.lvLibrary);
//        imageView = findViewById(R.id.play_button);
//        context = this;
//        ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
//        adapter = new ArrayAdapter<String>
//                (MainActivity.this, android.R.layout.simple_gallery_item, ListElementsArrayList);
//
//        playerView = findViewById(R.id.play_control_layout);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_library, R.id.nav_playlist, R.id.nav_favourite)
//                .setDrawerLayout(drawer).build();
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
//    }
//
//    public void goPlayerFragment(View view) {
//        view.setVisibility(View.GONE);
//        toolbar.setVisibility(View.GONE);
//        navController.navigate(R.id.nav_player);
//        AndroidRuntimePermission();
//        if (playerView == null) {
//            playerView = view;
//        }
//    }
//
//    public void backMainFragment(View view) {
//        playerView.setVisibility(View.VISIBLE);
//        toolbar.setVisibility(View.VISIBLE);
//        navController.navigate(R.id.nav_home);
//    }

public class MainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private Toolbar toolbar;
    public View playerView;
    ImageView imageView;
    public static ArrayList<Song> songList;
    private PlayerService PlayerService;
    private Intent playIntent;
    private boolean musicBound = false;
    private PlayerController controller;
    private boolean paused = false;
    private boolean playbackPaused = false;

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, PlayerService.class);
            bindService(playIntent, musicConnection, BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.play_button);
        playerView = findViewById(R.id.play_control_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_library, R.id.nav_playlist, R.id.nav_favourite)
                .setDrawerLayout(drawer).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//
//        // Retrieve list menu
//        songView = findViewById(R.id.lvLibrary);
        // Instantiate song list
        songList = new ArrayList<>();
        // Get songs from device
        getSongList();
        // Sort alphabetically by title
        Collections.sort(songList, (lhs, rhs) -> lhs.getTitle().compareTo(rhs.getTitle()));

        setController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void goPlayerFragment(View view) {
        view.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        navController.navigate(R.id.nav_player);

        if (playerView == null) {
            playerView = view;
        }
    }

    public void backMainFragment(View view) {
        playerView.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        navController.navigate(R.id.nav_home);
    }

    // Method to retrieve song infos from device
    public void getSongList() {
        // Query external audio resources
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = getContentResolver().query(musicUri, null, null, null, null);
        // Iterate over results if valid
        if (musicCursor != null && musicCursor.moveToFirst()) {
            // Get columns
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                int thisId = musicCursor.getInt(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }
    }

    // Connect with the service
    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.MusicBinder binder = (PlayerService.MusicBinder) service;
            PlayerService = binder.getService();
            PlayerService.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void songPicked(View v) {
        PlayerService.setSong(Integer.parseInt(v.getTag().toString()));
        PlayerService.playSong();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_button:
                PlayerService.setShuffle();
                break;
            case R.id.prev_song_button:
                stopService(playIntent);
                PlayerService = null;
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        PlayerService = null;
        super.onDestroy();
    }

    private void setController() {
        controller = new PlayerController(this);
        controller.setPrevNextListeners(v -> playNext(), v -> playPrev());
        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.lvLibrary));
        controller.setEnabled(true);
    }

    private void playNext() {
        PlayerService.playNext();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    private void playPrev() {
        PlayerService.playPrev();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paused) {
            setController();
            paused = false;
        }
    }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }

    @Override
    public void start() {
        PlayerService.go();
    }

    @Override
    public void pause() {
        playbackPaused = true;
        PlayerService.pausePlayer();
    }

    @Override
    public int getDuration() {
        if (PlayerService != null && musicBound && PlayerService.isPlaying()) {
            return PlayerService.getDur();
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        PlayerService.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if (PlayerService != null && musicBound) {
            return PlayerService.isPlaying();
        } else {
            return false;
        }
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

}