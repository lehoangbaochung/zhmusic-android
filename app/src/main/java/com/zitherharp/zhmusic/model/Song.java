package com.zitherharp.zhmusic.model;

public class Song {
    public final String id, videoId;
    public String artistId, artistName;
    public String albumId, albumTitle;
    public final String vietnameseTitle, pinyinTitle, simplifiedChineseTitle, traditionalChineseTitle;
    public final String vietnameseLyric, pinyinLyric, simplifiedChineseLyric, traditionalChineseLyric;
    public final String vietnameseDescription, simplifiedChineseDescription, traditionalChineseDescription;
    public final String genre;

    public Song(String id, String videoId, String artistId, String albumId,
                String vietnameseTitle, String pinyinTitle, String simplifiedChineseTitle, String traditionalChineseTitle,
                String vietnameseLyric, String pinyinLyric, String simplifiedChineseLyric, String traditionalChineseLyric,
                String vietnameseDescription, String simplifiedChineseDescription, String traditionalChineseDescription,
                String genre) {
        this.id = id;
        this.videoId = videoId;
        this.artistId = artistId;
        this.albumId = albumId;
        this.vietnameseTitle = vietnameseTitle;
        this.pinyinTitle = pinyinTitle;
        this.simplifiedChineseTitle = simplifiedChineseTitle;
        this.traditionalChineseTitle = traditionalChineseTitle;
        this.vietnameseLyric = vietnameseLyric;
        this.pinyinLyric = pinyinLyric;
        this.simplifiedChineseLyric = simplifiedChineseLyric;
        this.traditionalChineseLyric = traditionalChineseLyric;
        this.vietnameseDescription = vietnameseDescription;
        this.simplifiedChineseDescription = simplifiedChineseDescription;
        this.traditionalChineseDescription = traditionalChineseDescription;
        this.genre = genre;
    }

    public Song(String id, String videoId, String artistId, String artistName, String albumId, String albumTitle,
                String vietnameseTitle, String pinyinTitle, String simplifiedChineseTitle, String traditionalChineseTitle,
                String vietnameseLyric, String pinyinLyric, String simplifiedChineseLyric, String traditionalChineseLyric,
                String vietnameseDescription, String simplifiedChineseDescription, String traditionalChineseDescription,
                String genre) {
        this.id = id;
        this.videoId = videoId;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.vietnameseTitle = vietnameseTitle;
        this.pinyinTitle = pinyinTitle;
        this.simplifiedChineseTitle = simplifiedChineseTitle;
        this.traditionalChineseTitle = traditionalChineseTitle;
        this.vietnameseLyric = vietnameseLyric;
        this.pinyinLyric = pinyinLyric;
        this.simplifiedChineseLyric = simplifiedChineseLyric;
        this.traditionalChineseLyric = traditionalChineseLyric;
        this.vietnameseDescription = vietnameseDescription;
        this.simplifiedChineseDescription = simplifiedChineseDescription;
        this.traditionalChineseDescription = traditionalChineseDescription;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getVietnameseTitle() {
        return vietnameseTitle;
    }

    public String getPinyinTitle() {
        return pinyinTitle;
    }

    public String getSimplifiedChineseTitle() {
        return simplifiedChineseTitle;
    }

    public String getTraditionalChineseTitle() {
        return traditionalChineseTitle;
    }

    public String getVietnameseLyric() {
        return vietnameseLyric;
    }

    public String getPinyinLyric() {
        return pinyinLyric;
    }

    public String getSimplifiedChineseLyric() {
        return simplifiedChineseLyric;
    }

    public String getTraditionalChineseLyric() {
        return traditionalChineseLyric;
    }

    public String getVietnameseDescription() {
        return vietnameseDescription;
    }

    public String getSimplifiedChineseDescription() {
        return simplifiedChineseDescription;
    }

    public String getTraditionalChineseDescription() {
        return traditionalChineseDescription;
    }

    public String getGenre() {
        return genre;
    }
}