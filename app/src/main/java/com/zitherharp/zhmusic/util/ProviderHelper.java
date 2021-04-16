package com.zitherharp.zhmusic.util;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.ui.activity.MainActivity;

import java.util.ArrayList;

public class ProviderHelper {
    public static final String PROVIDER_NAME = "com.zitherharp.zhmusic.provider.Music";
    static final String URL = "content://" + PROVIDER_NAME;

    static final String SONG_URL = "/songs";
    static final String PLAYLIST_URL = "/playlists";

    public static final Uri SONG_CONTENT_URI = Uri.parse(URL + SONG_URL);
    public static final Uri PLAYLIST_CONTENT_URI = Uri.parse(URL + PLAYLIST_URL);

    public static final Uri EXTERNAL_CONTENT_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri INTERNAL_CONTENT_URI = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

    DatabaseReference databaseReference;

    MainActivity mainActivity;
    ArrayList<Song> songs;

    public ProviderHelper(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void retrieveOfflineSongs(Uri contentUri) {
        songs = new ArrayList<>();
        Cursor cursor = mainActivity.getContentResolver()
                .query(contentUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathColumn = cursor.getColumnIndex(MediaStore.Audio.Media.RELATIVE_PATH);
            int fileNameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);

            do {
                String path = cursor.getString(pathColumn);
                String displayName = cursor.getString(fileNameColumn);
                String pathUri = Environment.getExternalStorageDirectory().getPath() + "/" + path + displayName;

                int thisId = cursor.getInt(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(artistColumn);
                songs.add(new Song(thisId, thisTitle, thisArtist, pathUri));
            }
            while (cursor.moveToNext());
        }
    }

    public void retrieveOnlineSongs(String songsLanguage) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(songsLanguage);
        songs = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i = 0; i < 15; i++) {
                    String resultIndex = i + "";
                    DataSnapshot result = snapshot.child(resultIndex);

                    int id = Integer.parseInt(result.child("ID").getValue().toString());
                    String title = result.child("Title").getValue().toString();
                    String artistName = result.child("Artist").getValue().toString();
                    String videoId = result.child("VideoID").getValue().toString();
                    songs.add(new Song(id, title, artistName, videoId));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList getSongs() {
        return songs;
    }
}
