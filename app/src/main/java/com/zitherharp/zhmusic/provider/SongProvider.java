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

import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.ui.activity.MainActivity;
import com.zitherharp.zhmusic.util.DatabaseHelper;
import com.zitherharp.zhmusic.util.ProviderHelper;

import java.util.ArrayList;

public class SongProvider extends ContentProvider {
    MainActivity mainActivity;
    public static ArrayList<Song> songs;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    static final int SONGS = 1;
    static final int SONG_ID = 2;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, "songs", SONGS);
        uriMatcher.addURI(ProviderHelper.PROVIDER_NAME, "songs/#", SONG_ID);
    }


    public SongProvider() {}

    public SongProvider(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        songs = new ArrayList<>();
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DatabaseHelper.SONGS_TABLE_NAME);
        Cursor cursor = mainActivity.getContentResolver()
                .query(ProviderHelper.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
//        switch (DatabaseHelper.uriMatcher.match(uri)){
//            /**
//             * Get all student records
//             */
//            case DatabaseHelper.SONGS:
//                return "vnd.android.cursor.dir/vnd.example.students";
//            /**
//             * Get a particular student
//             */
//            case DatabaseHelper.SONG_ID:
//                return "vnd.android.cursor.item/vnd.example.students";
//            default:
//                throw new IllegalArgumentException("Unsupported URI: " + uri);
//        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(DatabaseHelper.SONGS_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(ProviderHelper.SONG_CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
//        switch (databaseHelper.uriMatcher.match(uri)){
//            case DatabaseHelper.SONGS:
//                count = db.delete(databaseHelper.SONGS_TABLE_NAME, selection, selectionArgs);
//                break;
//
//            case DatabaseHelper.SONG_ID:
//                String id = uri.getPathSegments().get(1);
//                count = db.delete(databaseHelper.SONGS_TABLE_NAME, databaseHelper.ID +  " = " + id +
//                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
            int count = 0;
//            switch (DatabaseHelper.uriMatcher.match(uri)) {
//                case DatabaseHelper.SONGS:
//                    count = db.update(databaseHelper.SONGS_TABLE_NAME, values, selection, selectionArgs);
//                    break;
//
//                case DatabaseHelper.SONG_ID:
//                    count = db.update(databaseHelper.SONGS_TABLE_NAME, values,
//                            databaseHelper.ID + " = " + uri.getPathSegments().get(1) +
//                                    (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unknown URI " + uri );
//            }

            getContext().getContentResolver().notifyChange(uri, null);
            return count;
    }
}
