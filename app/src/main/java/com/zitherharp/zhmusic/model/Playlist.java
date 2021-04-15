package com.zitherharp.zhmusic.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public final List<Song> songs;
    public final String title;

    public Playlist(String title) {
        songs = new ArrayList<>();
        this.title = title;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return songs.size();
    }
}
