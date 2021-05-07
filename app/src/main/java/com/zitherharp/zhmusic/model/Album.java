package com.zitherharp.zhmusic.model;

import java.util.List;

public class Album {
    public final String id, artistId;
    public final String vietnameseTitle, pinyinTitle, simplifiedChineseTitle, traditionalChineseTitle;
    public final String vietnameseDescription, simplifiedChineseDescription, traditionalChineseDescription;

    List<Song> songs;

    public Album(String id, String artistId,
                 String vietnameseTitle, String pinyinTitle, String simplifiedChineseTitle, String traditionalChineseTitle,
                 String vietnameseDescription, String simplifiedChineseDescription, String traditionalChineseDescription) {
        this.id = id;
        this.artistId = artistId;
        this.vietnameseTitle = vietnameseTitle;
        this.pinyinTitle = pinyinTitle;
        this.simplifiedChineseTitle = simplifiedChineseTitle;
        this.traditionalChineseTitle = traditionalChineseTitle;
        this.vietnameseDescription = vietnameseDescription;
        this.simplifiedChineseDescription = simplifiedChineseDescription;
        this.traditionalChineseDescription = traditionalChineseDescription;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getId() {
        return id;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getVietnameseTitle() {
        return vietnameseTitle;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public int getCount() {
        return songs.size();
    }
}
