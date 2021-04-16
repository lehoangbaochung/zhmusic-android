package com.zitherharp.zhmusic.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Music";
    static final int DATABASE_VERSION = 1;

    public static final String SONGS_TABLE_NAME = "songs";
    public static final String PLAYLISTS_TABLE_NAME = "playlists";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String SONG_ID = "song_id";

    // create table queries
    public static final String CREATE_SONG_TABLE = "CREATE TABLE " + SONGS_TABLE_NAME +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + " title TEXT NOT NULL, " +
            " artist_name TEXT NOT NULL);";
    public static final String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + PLAYLISTS_TABLE_NAME +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + " title TEXT NOT NULL, " +
            " song_id TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SONG_TABLE);
        db.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SONGS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYLISTS_TABLE_NAME);
        onCreate(db);
    }
}
