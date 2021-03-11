package com.zitherharp.zhmusic.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    public final List<Album> albums;

    public Artist() {
        albums = new ArrayList<>();
    }

    @NonNull
    private Album getFirstAlbum() {
        return albums.isEmpty() ? new Album() : albums.get(0);
    }

    public String getName() {
        return getFirstAlbum().getArtistName();
    }

    public String getId() {
        return getFirstAlbum().getArtistId();
    }

    public int getSongCount() {
        int songCount = 0;
        for (Album album : albums) {
            songCount += album.getSongCount();
        }
        return songCount;
    }
}
