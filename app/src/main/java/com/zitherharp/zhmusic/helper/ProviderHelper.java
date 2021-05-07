package com.zitherharp.zhmusic.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zitherharp.zhmusic.model.Album;
import com.zitherharp.zhmusic.model.Artist;
import com.zitherharp.zhmusic.model.Song;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProviderHelper {
    public static final String PROVIDER_NAME = "com.zitherharp.zhmusic.provider";
    static final String CONTENT_URL = "content://" + PROVIDER_NAME;

    static final String SONG_URL = "/song";
    static final String ARTIST_URL = "/artist";
    static final String ALBUM_URL = "/album";
    static final String PLAYLIST_URL = "/playlist";

    public static final Uri SONG_CONTENT_URI = Uri.parse(CONTENT_URL + SONG_URL);
    public static final Uri ARTIST_CONTENT_URI = Uri.parse(CONTENT_URL + ARTIST_URL);
    public static final Uri ALBUM_CONTENT_URI = Uri.parse(CONTENT_URL + ALBUM_URL);
    public static final Uri PLAYLIST_CONTENT_URI = Uri.parse(CONTENT_URL + PLAYLIST_URL);

    public static final Uri EXTERNAL_CONTENT_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri INTERNAL_CONTENT_URI = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

    Context context;

    public ProviderHelper(Context context) {
        this.context = context;
    }

    public List<?> select(@NotNull String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (tableName) {
            case DatabaseHelper.SONG_TABLE_NAME:
                cursor = context.getContentResolver().query(SONG_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
                List<Song> songs = new ArrayList<>();
                while (cursor.moveToNext()) {
                    songs.add(new Song(
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.ARTIST_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.ALBUM_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.VIDEO_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.VIETNAMESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.PINYIN_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.SIMPLIFIED_CHINESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRADITIONAL_CHINESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.VIETNAMESE_LYRIC)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.PINYIN_LYRIC)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.SIMPLIFIED_CHINESE_LYRIC)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRADITIONAL_CHINESE_LYRIC)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.VIETNAMESE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.SIMPLIFIED_CHINESE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRADITIONAL_CHINESE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.GENRE))
                    ));
                }
                cursor.close();
                return songs;
            case DatabaseHelper.ARTIST_TABLE_NAME:
                List<Artist> artists = new ArrayList<>();
                cursor = context.getContentResolver().query(ARTIST_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
                while (cursor.moveToNext()) {
                    artists.add(new Artist(
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYLIST_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.VIETNAMESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.PINYIN_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.SIMPLIFIED_CHINESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRADITIONAL_CHINESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.VIETNAMESE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.SIMPLIFIED_CHINESE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRADITIONAL_CHINESE_DESCRIPTION))
                    ));
                }
                cursor.close();
                return artists;
            case DatabaseHelper.ALBUM_TABLE_NAME:
                List<Album> albums = new ArrayList<>();
                cursor = context.getContentResolver().query(ALBUM_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
                while (cursor.moveToNext()) {
                    albums.add(new Album(
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.ARTIST_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.VIETNAMESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.PINYIN_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.SIMPLIFIED_CHINESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRADITIONAL_CHINESE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.VIETNAMESE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.SIMPLIFIED_CHINESE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRADITIONAL_CHINESE_DESCRIPTION))
                    ));
                }
                cursor.close();
                return albums;
        }
        return null;
    }

    public void delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        context.getContentResolver().delete(uri, selection, selectionArgs);
    }
}
