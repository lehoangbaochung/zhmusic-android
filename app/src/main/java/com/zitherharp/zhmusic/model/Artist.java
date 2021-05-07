package com.zitherharp.zhmusic.model;

import java.util.List;

public class Artist {
    public final String id, playlistId;
    public final String vietnameseName, pinyinName, simplifiedChineseName, traditionalChineseName;
    public final String vietnameseDescription, simplifiedChineseDescription, traditionalChineseDescription;

    List<Song> songs;
    List<Album> albums;

    public Artist(String id, String playlistId,
                  String vietnameseName, String pinyinName, String simplifiedChineseName, String traditionalChineseName,
                  String vietnameseDescription, String simplifiedChineseDescription, String traditionalChineseDescription) {
        this.id = id;
        this.playlistId = playlistId;
        this.vietnameseName = vietnameseName;
        this.pinyinName = pinyinName;
        this.simplifiedChineseName = simplifiedChineseName;
        this.traditionalChineseName = traditionalChineseName;
        this.vietnameseDescription = vietnameseDescription;
        this.simplifiedChineseDescription = simplifiedChineseDescription;
        this.traditionalChineseDescription = traditionalChineseDescription;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public int getSongCount() {
        return songs == null ? 0 : songs.size();
    }

    public int getAlbumCount() {
        return albums == null ? 0 : albums.size();
    }

    public String getId() {
        return id;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }

    public String getPlaylistId() {
        return playlistId;
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

    public String getPinyinName() {
        return pinyinName;
    }

    public String getSimplifiedChineseName() {
        return simplifiedChineseName;
    }

    public String getTraditionalChineseName() {
        return traditionalChineseName;
    }
}