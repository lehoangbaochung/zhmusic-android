package com.zitherharp.zhmusic.model;

public class Song {
    public int id, duration;
    public String title;
    public String artistId, artistName;
    public String albumId, albumTitle;
    public String path, pathId;
    public String lyricVietnamese, lyricPinyinChinese, lyricSimplifiedChinese, lyricTraditionalChinese;

    public Song() {}

    public Song(int id, String title, String artistName, String videoId, String albumId) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.pathId = videoId;
        this.albumId = albumId;
    }

    public Song(int id, String title, String artistName, int duration, String videoId, String albumId) {
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.artistName = artistName;
        this.pathId = videoId;
        this.duration = duration;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getArtistId() { return artistId; }

    public String getArtistName() { return artistName; }

    public String getPathId() { return pathId; }

    public String getAlbumId() { return albumId; }

    public String getAlbumTitle() { return albumTitle; }

    public int getDuration() {
        return duration;
    }
}