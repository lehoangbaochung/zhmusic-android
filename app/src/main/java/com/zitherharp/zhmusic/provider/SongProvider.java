package com.zitherharp.zhmusic.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongProvider {
    private static final int TITLE = 0;
    private static final int ALBUM_ID = 1;
    private static final int ALBUM = 2;
    private static final int ARTIST_ID = 3;
    private static final int ARTIST = 4;
    private static final int PATH = 5;
    private static final int DURATION = 6;
    private static final int YEAR = 7;

    private static final String[] BASE_PROJECTION = new String[] {
        AudioColumns.TITLE,// 0
        AudioColumns.ALBUM_ID,// 1
        AudioColumns.ALBUM,// 2
        AudioColumns.ARTIST_ID,// 3
        AudioColumns.ARTIST,// 4
        AudioColumns.DATA,// 5
        AudioColumns.DURATION,// 6
        AudioColumns.YEAR,// 7
    };

    @NonNull
    static List<Song> getSongs(@Nullable final Cursor cursor) {
        List<Song> songs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                if (getSongFromCursorImplement(cursor).duration >= 5000) {
                    songs.add(getSongFromCursorImplement(cursor));
                }
            }
            while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        if (songs.size() > 1) {
            sortSongsByDuration(songs);
        }

        return songs;
    }

    @NonNull
    static Song getSongFromCursorImplement(@NonNull Cursor cursor) {
        final String title = cursor.getString(TITLE);
        final String albumId = cursor.getString(ALBUM_ID);
        final String albumName = cursor.getString(ALBUM);
        final String artistId = cursor.getString(ARTIST_ID);
        final String artistName = cursor.getString(ARTIST);
        final String path = cursor.getString(PATH);
        final int duration = cursor.getInt(DURATION);
        final int year = cursor.getInt(YEAR);

        return new Song(title, albumId, albumName, artistId, artistName, path, duration, year);
    }

    @Nullable
    static Cursor makeSongCursor(@NonNull final Context context, final String sortOrder) {
        try {
            return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                BASE_PROJECTION, null, null, sortOrder);
        }
        catch (SecurityException e) {
            return null;
        }
    }

    private static void sortSongsByDuration(List<Song> songs) {
        Collections.sort(songs, new Comparator<Song>() {
            public int compare(Song obj1, Song obj2) {
                return Integer.compare(obj1.duration, obj2.duration);
            }
        });
    }
}
