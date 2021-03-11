package com.zitherharp.zhmusic.model;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Song {
    static final Song EMPTY_SONG
            = new Song("", "", "", "", "", "", -1, -1);

    public final String title;
    public final String albumId;
    public final String albumName;
    public final String artistId;
    public final String artistName;
    public final String path;
    public final int duration;
    final int year;

    public Song(String title, String albumId, String albumName, String artistId, String artistName,
                String path, int duration, int year) {
        this.title = title;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.path = path;
        this.duration = duration;
        this.year = year;
    }

    public static String formatDuration(int duration) {
        return String.format(Locale.getDefault(), "%02d:%02d",
             TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) -
             TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
