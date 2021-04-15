package com.zitherharp.zhmusic.provider;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;

public class FirebaseProvider {
    DatabaseReference mDatabase;
    ArrayList<Song> songs;
    
    public FirebaseProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Vietnamese");
        songs = new ArrayList<>();
        getData(0);
    }

    void getData(int startIndex) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i = startIndex; i < startIndex + 10; i++) {
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
