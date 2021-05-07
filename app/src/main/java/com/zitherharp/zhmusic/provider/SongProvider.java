package com.zitherharp.zhmusic.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zitherharp.zhmusic.helper.DatabaseHelper;
import com.zitherharp.zhmusic.helper.ProviderHelper;
import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;

public class SongProvider extends ContentProvider {
    public ArrayList<Song> songs;
    Context context;

    DatabaseHelper databaseHelper;
    SQLiteDatabase database;

    static final int ONLINE_SONGS_ID = 1;
    static final int OFFLINE_SONGS_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, "songs/online", ONLINE_SONGS_ID);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, "songs/offline", OFFLINE_SONGS_ID);
    }

    public SongProvider(Context context) {
        this.context = context;
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
        return database != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DatabaseHelper.SONG_TABLE_NAME);
        Cursor cursor = context.getContentResolver()
                .query(ProviderHelper.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ONLINE_SONGS_ID:
                return "vnd.android.cursor.dir/vnd.example.students";
            case OFFLINE_SONGS_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void retrieveOfflineSongs(Uri contentUri) {
        songs = new ArrayList<>();
        Cursor cursor = new SongProvider(context).query(contentUri, null, null, null, null);

        assert cursor != null;
        if (cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathColumn = cursor.getColumnIndex(MediaStore.Audio.Media.RELATIVE_PATH);
            int fileNameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);

            do {
                String path = cursor.getString(pathColumn);
                String displayName = cursor.getString(fileNameColumn);
                String pathUri = "/storage/2225-11F8/" + path + displayName;

                int thisId = cursor.getInt(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(artistColumn);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
    }

    public ArrayList getSongs() {
        return songs;
    }
}
