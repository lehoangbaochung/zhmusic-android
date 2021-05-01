package com.zitherharp.zhmusic.ui.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.credential.google.LoginCredential;
import com.zitherharp.zhmusic.helper.DatabaseHelper;
import com.zitherharp.zhmusic.helper.ProviderHelper;
import com.zitherharp.zhmusic.model.Account;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.provider.SongProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Song> songList;
    public static ArrayList<Song> onlineSongList;

    AppBarConfiguration mAppBarConfiguration;

    NavController navController;
    Toolbar toolbar;
    TextView tvNavHeaderTitle, tvNavHeaderSubtitle;
    ImageView ivNavHeaderImage;

    public LoginCredential loginCredential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        initialize();

        loginCredential = new LoginCredential(this);
        loginCredential.getResultsFromApi();

        Account account = loginCredential.getAccount();
        if (account != null) {
            tvNavHeaderTitle.setText(String.format("Hi, %s!", account.getDisplayName()));
            tvNavHeaderSubtitle.setText(account.getUserName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginCredential.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu); 
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    void findViewById() {
        // toolbar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.main_layout);
        // navigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        tvNavHeaderTitle = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        tvNavHeaderSubtitle = navigationView.getHeaderView(0).findViewById(R.id.nav_user_info);
        ivNavHeaderImage = navigationView.getHeaderView(0).findViewById(R.id.nav_user_avatar);
        // appBarConfiguration
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_library,
                R.id.nav_home, R.id.nav_online, R.id.nav_offline, R.id.nav_playlist).setDrawerLayout(drawer).build();
        // navigationController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    
    void initialize() {
        SongProvider songProvider = new SongProvider(this);
        songProvider.retrieveOfflineSongs(ProviderHelper.EXTERNAL_CONTENT_URI);
        songList = songProvider.getSongs();
        Collections.sort(songList, (lhs, rhs) -> lhs.getTitle().compareTo(rhs.getTitle()));
    }

    public void songPicked(@NotNull View v) {
        TextView tvSongTitle = v.findViewById(R.id.song_title);
        TextView tvArtistName = v.findViewById(R.id.artist_name);
        String songTitle = tvSongTitle.getText().toString();
        String artistName = tvArtistName.getText().toString();
        String songFullTitle = songTitle + " - " + artistName;
        String videoId = v.getTag().toString();

        TextView tvSongTitleBar = findViewById(R.id.bar_song_title);
        TextView tvArtistNameBar = findViewById(R.id.bar_artist_name);
        tvSongTitleBar.setText(songTitle);
        tvArtistNameBar.setText(artistName);

        Intent playSong = new Intent(this, PlayerActivity.class);
        playSong.putExtra("song_title", songTitle);
        playSong.putExtra("artist_name", artistName);
        playSong.putExtra("song_full_title", songFullTitle);
        playSong.putExtra("video_id", videoId);
        startActivity(playSong);

        Toast.makeText(this, "Playing: " + songFullTitle, Toast.LENGTH_LONG).show();
    }

    public void goSigninActivity(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void addPlaylist(MenuItem item) {
        if (tvNavHeaderTitle.getText().equals("Sign in")) {
            Toast.makeText(this, "You need sign in to add new playlist", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Create a new playlist");
            ad.setMessage("Playlist title:");

            final EditText input = new EditText(this);
            ad.setView(input);
            ad.setPositiveButton("OK", (dlg, which) -> {
                // Add a new playlist record
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.TITLE, input.getText().toString());

                Uri uri = getContentResolver().insert(ProviderHelper.PLAYLIST_CONTENT_URI, values);
                Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
            });
            ad.setNegativeButton("Cancel", (dlg, which) -> dlg.cancel());
            ad.show();
        }
    }

    public void goAboutActivity(MenuItem item) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void goPlayerActivity(View v) {
        startActivity(new Intent(this, PlayerActivity.class));
    }
}