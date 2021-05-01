package com.zitherharp.zhmusic.helper;

import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;

public class ProviderHelper {
    public static final String PROVIDER_NAME = "com.zitherharp.zhmusic.provider";
    static final String URL = "content://" + PROVIDER_NAME;

    static final String SONG_URL = "/songs";
    static final String PLAYLIST_URL = "/playlists";

    public static final Uri SONG_CONTENT_URI = Uri.parse(URL + SONG_URL);
    public static final Uri PLAYLIST_CONTENT_URI = Uri.parse(URL + PLAYLIST_URL);

    public static final Uri EXTERNAL_CONTENT_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri INTERNAL_CONTENT_URI = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

    DatabaseReference databaseReference;
    ArrayList<Song> songs;

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
                    songs.add(new Song(id, title, artistName, videoId, ""));
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
