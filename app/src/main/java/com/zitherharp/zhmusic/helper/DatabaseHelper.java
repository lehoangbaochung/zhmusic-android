package com.zitherharp.zhmusic.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NotNull;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Music";
    static final int DATABASE_VERSION = 1;

    public static final String SONG_TABLE_NAME = "song";
    public static final String ARTIST_TABLE_NAME = "artist";
    public static final String ALBUM_TABLE_NAME = "album";
    public static final String SONG_VIDEO_TABLE_NAME = "song_video";
    public static final String SONG_ARTIST_TABLE_NAME = "song_artist";
    public static final String ALBUM_ARTIST_TABLE_NAME = "album_artist";
    public static final String PLAYLIST_TABLE_NAME = "playlist";

    static final String ID = "id";
    static final String TITLE = "title";
    static final String SONG_ID = "song_id";
    static final String ARTIST_ID = "artist_id";
    static final String ALBUM_ID = "album_id";
    static final String VIDEO_ID = "video_id";
    static final String PLAYLIST_ID = "playlist_id";
    static final String PINYIN_NAME = "pinyin_name";
    static final String VIETNAMESE_NAME = "vietnamese_name";
    static final String SIMPLIFIED_CHINESE_NAME = "simplified_chinese_name";
    static final String TRADITIONAL_CHINESE_NAME = "traditional_chinese_name";
    static final String PINYIN_LYRIC = "pinyin_lyric";
    static final String VIETNAMESE_LYRIC = "vietnamese_lyric";
    static final String SIMPLIFIED_CHINESE_LYRIC = "simplified_chinese_lyric";
    static final String TRADITIONAL_CHINESE_LYRIC = "traditional_chinese_lyric";
    static final String VIETNAMESE_DESCRIPTION = "vietnamese_description";
    static final String SIMPLIFIED_CHINESE_DESCRIPTION = "simplified_chinese_description";
    static final String TRADITIONAL_CHINESE_DESCRIPTION = "traditional_chinese_description";
    static final String GENRE = "genre";

    static final String CREATE_SONG_TABLE = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, " +
                    "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL," +
                    "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
            SONG_TABLE_NAME, ID, ALBUM_ID, VIETNAMESE_NAME, PINYIN_NAME, SIMPLIFIED_CHINESE_NAME, TRADITIONAL_CHINESE_NAME,
            VIETNAMESE_LYRIC, PINYIN_LYRIC, SIMPLIFIED_CHINESE_LYRIC, TRADITIONAL_CHINESE_LYRIC,
            VIETNAMESE_DESCRIPTION, SIMPLIFIED_CHINESE_DESCRIPTION, TRADITIONAL_CHINESE_DESCRIPTION, GENRE);

    static final String CREATE_ARTIST_TABLE = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, " +
                    "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL," +
                    "%s TEXT, %s TEXT, %s TEXT);",
            ARTIST_TABLE_NAME, ID, PLAYLIST_ID, VIETNAMESE_NAME, PINYIN_NAME, SIMPLIFIED_CHINESE_NAME, TRADITIONAL_CHINESE_NAME,
            VIETNAMESE_DESCRIPTION, SIMPLIFIED_CHINESE_DESCRIPTION, TRADITIONAL_CHINESE_DESCRIPTION);

    static final String CREATE_ALBUM_TABLE = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, " +
                    "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL," +
                    "%s TEXT, %s TEXT, %s TEXT);",
            ALBUM_TABLE_NAME, ID, ARTIST_ID, VIETNAMESE_NAME, PINYIN_NAME, SIMPLIFIED_CHINESE_NAME, TRADITIONAL_CHINESE_NAME,
            VIETNAMESE_DESCRIPTION, SIMPLIFIED_CHINESE_DESCRIPTION, TRADITIONAL_CHINESE_DESCRIPTION);

    static final String CREATE_SONG_VIDEO_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                            "FOREIGN KEY %s REFERENCES %s(%s));",
                    SONG_VIDEO_TABLE_NAME, ID, SONG_ID, VIDEO_ID, SONG_ID, SONG_TABLE_NAME, ID);

    static final String CREATE_SONG_ARTIST_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                            "FOREIGN KEY %s REFERENCES %s(%s), FOREIGN KEY %s REFERENCES %s(%s));",
                    SONG_ARTIST_TABLE_NAME, ID, SONG_ID, ARTIST_ID,
                    SONG_ID, SONG_TABLE_NAME, ID, ARTIST_ID, ARTIST_TABLE_NAME, ID);

    static final String CREATE_ALBUM_ARTIST_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                            "FOREIGN KEY %s REFERENCES %s(%s), FOREIGN KEY %s REFERENCES %s(%s));",
                    ALBUM_ARTIST_TABLE_NAME, ID, ALBUM_ID, ARTIST_ID,
                    ALBUM_ID, ALBUM_TABLE_NAME, ID, ARTIST_ID, ARTIST_TABLE_NAME, ID);

    static final String CREATE_PLAYLIST_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                            "FOREIGN KEY %s REFERENCES %s(%s));",
                    PLAYLIST_TABLE_NAME, ID, TITLE, SONG_ID, SONG_ID, SONG_TABLE_NAME, ID);

    static final String JOIN_QUERY = String.format("%s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
            SONG_TABLE_NAME, ARTIST_TABLE_NAME, SONG_TABLE_NAME, ARTIST_ID, ARTIST_TABLE_NAME, ID);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase database) {
        database.execSQL(CREATE_SONG_TABLE);
        database.execSQL(CREATE_ALBUM_TABLE);
        database.execSQL(CREATE_ARTIST_TABLE);
        database.execSQL(CREATE_PLAYLIST_TABLE);
        database.execSQL(CREATE_SONG_VIDEO_TABLE);
        database.execSQL(CREATE_SONG_ARTIST_TABLE);
        database.execSQL(CREATE_ALBUM_ARTIST_TABLE);
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase database, int oldVersion, int newVersion) {
        if (database.needUpgrade(newVersion)) {
            database.execSQL("DROP TABLE IF EXISTS " + SONG_TABLE_NAME);
            database.execSQL("DROP TABLE IF EXISTS " + ALBUM_TABLE_NAME);
            database.execSQL("DROP TABLE IF EXISTS " + ARTIST_TABLE_NAME);
            database.execSQL("DROP TABLE IF EXISTS " + PLAYLIST_TABLE_NAME);
            onCreate(database);
        }
    }
}