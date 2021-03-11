package com.zitherharp.zhmusic.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zitherharp.zhmusic.model.Album;
import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumProvider {
    static Album getAlbum(List<Album> albums, String albumName) {
        for (Album album : albums) {
            if (!album.songs.isEmpty() && album.songs.get(0).albumName.equals(albumName)) {
                return album;
            }
        }

        Album album = new Album();
        albums.add(album);
        return album;
    }

    @NonNull
    static List<Album> retrieveAlbums(@Nullable final List<Song> songs) {
        List<Album> albums = new ArrayList<>();
        if (songs != null) {
            for (Song song : songs) {
                getAlbum(albums, song.albumName).songs.add(song);
            }
        }

        if (albums.size() > 1) {
            sortAlbums(albums);
        }

        return albums;
    }

    private static void sortAlbums(List<Album> albums) {
        Collections.sort(albums, (obj1, obj2) -> Integer.compare(obj1.getYear(), obj2.getYear()));
    }
}