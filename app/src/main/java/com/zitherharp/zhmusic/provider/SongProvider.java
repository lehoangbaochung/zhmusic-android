package com.zitherharp.zhmusic.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.ui.activity.MainActivity;
import com.zitherharp.zhmusic.util.DatabaseHelper;

import java.util.ArrayList;

public class SongProvider extends ContentProvider {
    MainActivity mainActivity;
    public static ArrayList<Song> songs;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    public SongProvider() {}

    public SongProvider(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        songs = new ArrayList<>();
    }

    public ArrayList getSongList() {
        // Query external audio resources
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = mainActivity.getContentResolver()
                .query(musicUri, null, null, null, null);
        // Iterate over results if valid
        if (musicCursor != null && musicCursor.moveToFirst()) {
            // Get columns
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.RELATIVE_PATH);
            int displayColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);

            do {
                String path = musicCursor.getString(pathColumn);
                String displayName = musicCursor.getString(displayColumn);
                String pathUri = Environment.getExternalStorageDirectory().getPath() + "/" + path + displayName;

                int thisId = musicCursor.getInt(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songs.add(new Song(thisId, thisTitle, thisArtist, pathUri));
            }
            while (musicCursor.moveToNext());
        }
        return songs;
    }



    @Override
    public boolean onCreate() {
        Context context = getContext();
        databaseHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = databaseHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DatabaseHelper.PLAYLISTS_TABLE_NAME);

//        switch (DatabaseHelper.uriMatcher.match(uri)) {
//            case DatabaseHelper.SONGS:
//                qb.setProjectionMap(DatabaseHelper.SONGS_PROJECTION_MAP);
//                break;
//
//            case DatabaseHelper.SONG_ID:
//                qb.appendWhere( DatabaseHelper.ID + "=" + uri.getPathSegments().get(1));
//                break;
//
//            default:
//        }
//
//        if (sortOrder == null || sortOrder == ""){
//            /**
//             * By default sort on student names
//             */
//            sortOrder = DatabaseHelper.TITLE;
//        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
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
        long rowID = db.insert(databaseHelper.SONGS_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(DatabaseProvider.CONTENT_URI, rowID);
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
