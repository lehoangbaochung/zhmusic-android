package com.zitherharp.zhmusic.model;

import com.zitherharp.zhmusic.provider.SongProvider;

import java.util.List;

public class Artist {
    public final String id, playlistId, description;
    public String name, pinyinName, simplifiedChineseName, traditionalChineseName;

    List<Song> songs;
    List<Album> albums;

    public Artist(String id, String name, String pinyinName, String simplifiedChineseName, String traditionalChineseName,
                  String playlistId, String description) {
        this.id = id;
        this.name = name;
        this.pinyinName = pinyinName;
        this.simplifiedChineseName = simplifiedChineseName;
        this.traditionalChineseName = traditionalChineseName;
        this.playlistId = playlistId;
        this.description = description;
    }

    public void getSongs() {
        for (Song song : SongProvider.ONLINE_SONGS) {
            if (song.getArtistId().contains(id)) {
                songs.add(song);
            }
        }
    }

}
