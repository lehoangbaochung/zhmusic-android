package com.zitherharp.zhmusic.model;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Song {
    static final Song EMPTY_SONG
            = new Song(-1, "", "", "", "", "", "", -1, -1);

    public final int id;
    public final String title;
    public final String albumId;
    public final String albumName;
    public final String artistId;
    public final String artistName;
    public final String path;
    public final int duration;
    final int year;

    public Song(int id, String title, String artistName, String pathUri) {
        this.id = id;
        this.title = title;
        this.albumId = null;
        this.albumName = null;
        this.artistId = null;
        this.artistName = artistName;
        this.path = pathUri;
        this.duration = 0;
        this.year = 0;
    }

    public Song(int id, String title, String artistName, int duration, String path, String albumId) {
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.albumName = null;
        this.artistId = null;
        this.artistName = artistName;
        this.path = path;
        this.duration = duration;
        this.year = 0;
    }

    public Song(int id, String title, String albumId, String albumName, String artistId, String artistName,
                String path, int duration, int year) {
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.path = path;
        this.duration = duration;
        this.year = year;
    }

    public static @NotNull String formatDuration(int duration) {
        return String.format(Locale.getDefault(), "%02d:%02d",
             TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) -
             TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getArtistName() { return artistName; }

    public String getAlbumId() { return albumId; }

}
