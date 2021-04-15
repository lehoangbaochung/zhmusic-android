package com.zitherharp.zhmusic.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.provider.FirebaseProvider;
import com.zitherharp.zhmusic.provider.SongProvider;
import com.zitherharp.zhmusic.util.IntentCode;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    Toolbar toolbar;
    TextView tvNavHeaderTitle, tvNavHeaderSubtitle;
    ImageView ivNavHeaderImage;
    public static ArrayList<Song> songList;
    public static ArrayList<Song> onlineSongList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initialize();
        displaySignInAccount();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // navigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        tvNavHeaderTitle = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        tvNavHeaderSubtitle = navigationView.getHeaderView(0).findViewById(R.id.nav_user_info);
        ivNavHeaderImage = navigationView.getHeaderView(0).findViewById(R.id.nav_user_avatar);
        // appBarConfiguration
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_online, R.id.nav_offline, R.id.nav_playlist)
                .setDrawerLayout(drawer).build();
        // navigationController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    void initialize() {
        SongProvider songProvider = new SongProvider(this);
        songList = songProvider.getSongList();
        Collections.sort(songList, (lhs, rhs) -> lhs.getTitle().compareTo(rhs.getTitle()));

        FirebaseProvider firebaseProvider = new FirebaseProvider();
        onlineSongList = firebaseProvider.getSongs();
    }

    public void songPicked(@NotNull View v) {
        TextView tvSongTitle = v.findViewById(R.id.song_title);
        TextView tvArtistName = v.findViewById(R.id.artist_name);
        String songTitle = tvSongTitle.getText().toString();
        String artistName = tvArtistName.getText().toString();
        String songFullTitle = songTitle + " - " + artistName;
        String videoId = v.getTag().toString();

        Intent playSong = new Intent(this, PlayerActivity.class);
        playSong.putExtra("song_title", songTitle);
        playSong.putExtra("artist_name", artistName);
        playSong.putExtra("song_full_title", songFullTitle);
        playSong.putExtra("video_id", videoId);
        startActivity(playSong);

        Toast.makeText(this, "Playing: " + songFullTitle, Toast.LENGTH_LONG).show();
    }

    public void goSigninActivity(View v) {
        startActivityForResult(new Intent(this, SigninActivity.class), IntentCode.SIGN_IN);
    }

    public void addPlaylist(MenuItem item) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("Create a new playlist");
        ad.setMessage("Playlist title:");

        final EditText input = new EditText(this);
        ad.setView(input);
        ad.setPositiveButton("OK", (dlg, which) -> {
            String val = input.getText().toString();
            String msg = String.format("Hello %s!", val);
        });

        ad.setNegativeButton("Cancel", (dlg, which) -> dlg.cancel());
        ad.show();
    }

    public void goAboutActivity(MenuItem item) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    void displaySignInAccount() {
        String accountName = getIntent().getStringExtra("account_name");
        String accountInfo = getIntent().getStringExtra("account_info");

        if (accountName == null) {
            tvNavHeaderTitle.setText("Log in");
        } else {
            tvNavHeaderTitle.setText(accountName);
        }
        if (accountInfo == null) {
            tvNavHeaderSubtitle.setText("You have not sign in yet");
        } else {
            tvNavHeaderSubtitle.setText(accountInfo);
        }
    }
}