package com.zitherharp.zhmusic.model;

import com.zitherharp.zhmusic.provider.SongProvider;

import java.util.ArrayList;
import java.util.List;

public class Album {
    public String id, title;
    List<Song> songs;

    public Album(String id, String title) {
        this.id = id;
        this.title = title;
        this.songs = new ArrayList<>();
    }

    public List<Song> getSongs() {
        return songs;
    }

    public int getCount() {
        return songs.size();
    }

    public void o() {
        for (Song song : SongProvider.ONLINE_SONGS) {
            if (id.equals(song.getAlbumId())) {
                songs.add(song);
            }
        }
    }
}
