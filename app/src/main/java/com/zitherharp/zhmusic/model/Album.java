package com.zitherharp.zhmusic.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Album {
    public final List<Song> songs;

    public Album() {
        songs = new ArrayList<>();
    }

    @NonNull
    private Song getFirstSong() {
        return songs.isEmpty() ? Song.EMPTY_SONG : songs.get(0);
    }

    public String getTitle() {
        return getFirstSong().albumName;
    }

    public String getArtistName() {
        return getFirstSong().artistName;
    }

    public String getArtistId() {
        return getFirstSong().artistId;
    }

    public int getSongCount() {
        return songs.size();
    }

    public int getYear() {
        return getFirstSong().year;
    }
}
