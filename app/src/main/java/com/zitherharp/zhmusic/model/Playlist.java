package com.zitherharp.zhmusic.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    final List<Song> songs;
    final int id;
    final String title;

    public Playlist(int id, String title) {
        songs = new ArrayList<>();
        this.id = id;
        this.title = title;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return songs.size();
    }
}
