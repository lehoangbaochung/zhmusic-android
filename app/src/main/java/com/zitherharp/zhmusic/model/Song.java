package com.zitherharp.zhmusic.model;

import android.net.Uri;

public class Song {
    public static final Song EMPTY_SONG
            = new Song(-1, null, null, null);

    public int id;
    public String title;
    public String albumId;
    public String albumName;
    public Uri albumUri;
    public String artistId;
    public String artistName;
    public String path;
    public String videoId;
    public int duration;
    int year;

    public Song(int id, String title, String artistName, String videoId) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.videoId = videoId;
    }

    public Song(int id, String title, String artistName, int duration, String videoId, String albumId) {
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.artistName = artistName;
        this.videoId = videoId;
        this.duration = duration;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getArtistName() { return artistName; }

    public String getVideoId() { return videoId; }

    public String getAlbumId() { return albumId; }

    public int getDuration() {
        return duration;
    }
}
