package com.zitherharp.zhmusic.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zitherharp.zhmusic.util.DatabaseHelper;
import com.zitherharp.zhmusic.util.ProviderHelper;

import java.util.HashMap;
import java.util.Objects;

public class PlaylistProvider extends ContentProvider {
    static final int PLAYLISTS = 1;
    static final int PLAYLIST_ID = 2;

    static HashMap<String, String> PLAYLISTS_MAP;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, "playlists", PLAYLISTS);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, "playlists/#", PLAYLIST_ID);
    }

    SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return database != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DatabaseHelper.PLAYLISTS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case PLAYLISTS:
                qb.setProjectionMap(PLAYLISTS_MAP);
                break;
            case PLAYLIST_ID:
                qb.appendWhere( DatabaseHelper.ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || Objects.equals(sortOrder, "")) {
            // sort playlists by title
            sortOrder = DatabaseHelper.TITLE;
        }
        Cursor cursor = qb.query(database,	projection,	selection, selectionArgs,null, null, sortOrder);
        // register to watch a content URI for changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            // get all playlists record
            case PLAYLISTS:
                return "vnd.android.cursor.dir/";
            // get a particular playlist
            case PLAYLIST_ID:
                return "vnd.android.cursor.item/";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Add a new playlist record
        long rowID = database.insert(DatabaseHelper.PLAYLISTS_TABLE_NAME, "", values);
        // If add successfully
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(ProviderHelper.PLAYLIST_CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a new playlist into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
