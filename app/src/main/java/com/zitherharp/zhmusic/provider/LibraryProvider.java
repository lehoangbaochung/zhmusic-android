package com.zitherharp.zhmusic.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zitherharp.zhmusic.helper.DatabaseHelper;
import com.zitherharp.zhmusic.helper.ProviderHelper;

public class LibraryProvider extends ContentProvider {
    public static final int SONG = 1;
    public static final int ARTIST = 2;
    public static final int ALBUM = 3;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, DatabaseHelper.SONG_TABLE_NAME, SONG);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, DatabaseHelper.ARTIST_TABLE_NAME, ARTIST);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, DatabaseHelper.ALBUM_TABLE_NAME, ALBUM);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, DatabaseHelper.ALBUM_TABLE_NAME, ALBUM);
    }

    DatabaseHelper databaseHelper;
    SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();
        return database != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case SONG:
                queryBuilder.setTables(DatabaseHelper.SONG_TABLE_NAME);
                selection = "";
                break;
            case ARTIST:
                queryBuilder.setTables(DatabaseHelper.ARTIST_TABLE_NAME);
                break;
            case ALBUM:
                queryBuilder.setTables(DatabaseHelper.ALBUM_TABLE_NAME);
                break;
        }

        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs,null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = null;
        Uri contentUri = null, insertUri = null;

        switch (uriMatcher.match(uri)) {
            case SONG:
                tableName = DatabaseHelper.SONG_TABLE_NAME;
                contentUri = ProviderHelper.SONG_CONTENT_URI;
                break;
            case ARTIST:
                tableName = DatabaseHelper.ARTIST_TABLE_NAME;
                contentUri = ProviderHelper.ARTIST_CONTENT_URI;
                break;
            case ALBUM:
                tableName = DatabaseHelper.ALBUM_TABLE_NAME;
                contentUri = ProviderHelper.ALBUM_CONTENT_URI;
                break;
        }

        long rowID = database.insert(tableName, null, values);
        if (rowID > 0) {
            insertUri = ContentUris.withAppendedId(contentUri, rowID);
            getContext().getContentResolver().notifyChange(insertUri, null);
        }
        return insertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = null;
        int count;

        switch (uriMatcher.match(uri)) {
            case SONG:
                tableName = DatabaseHelper.SONG_TABLE_NAME;
                break;
            case ARTIST:
                tableName = DatabaseHelper.ARTIST_TABLE_NAME;
                break;
            case ALBUM:
                tableName = DatabaseHelper.ALBUM_TABLE_NAME;
                break;
        }

        count = database.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = null;
        int count;

        switch (uriMatcher.match(uri)) {
            case SONG:
                tableName = DatabaseHelper.SONG_TABLE_NAME;
                break;
            case ARTIST:
                tableName = DatabaseHelper.ARTIST_TABLE_NAME;
                break;
            case ALBUM:
                tableName = DatabaseHelper.ALBUM_TABLE_NAME;
                break;
        }

        count = database.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SONG:
                return "vnd.android.cursor.dir/vnd.zitherharp.zhmusic.song";
            case ARTIST:
                return "vnd.android.cursor.dir/vnd.zitherharp.zhmusic.artist";
            case ALBUM:
                return "vnd.android.cursor.dir/vnd.zitherharp.zhmusic.album";
        }
        return null;
    }
}