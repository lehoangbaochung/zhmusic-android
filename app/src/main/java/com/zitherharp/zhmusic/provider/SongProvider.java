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
import android.provider.MediaStore.Audio.AudioColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongProvider extends ContentProvider {
    MainActivity mainActivity;
    public static ArrayList<Song> songs;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    private static final int ID = -1;
    private static final int TITLE = 0;
    private static final int ALBUM_ID = 1;
    private static final int ALBUM = 2;
    private static final int ARTIST_ID = 3;
    private static final int ARTIST = 4;
    private static final int PATH = 5;
    private static final int DURATION = 6;
    private static final int YEAR = 7;

    private static final String[] BASE_PROJECTION = new String[] {
        AudioColumns.TITLE_KEY,//-1
        AudioColumns.TITLE,// 0
        AudioColumns.ALBUM_ID,// 1
        AudioColumns.ALBUM,// 2
        AudioColumns.ARTIST_ID,// 3
        AudioColumns.ARTIST,// 4
        AudioColumns.DATA,// 5
        AudioColumns.DURATION,// 6
        AudioColumns.YEAR,// 7
    };

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

    @NonNull
    static void getSongs(@Nullable final Cursor cursor) {
        ArrayList<Song> songs = new ArrayList<>();
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
    }

    @NonNull
    static Song getSongFromCursorImplement(@NonNull Cursor cursor) {
        final int id = cursor.getInt(ID);
        final String title = cursor.getString(TITLE);
        final String albumId = cursor.getString(ALBUM_ID);
        final String albumName = cursor.getString(ALBUM);
        final String artistId = cursor.getString(ARTIST_ID);
        final String artistName = cursor.getString(ARTIST);
        final String path = cursor.getString(PATH);
        final int duration = cursor.getInt(DURATION);
        final int year = cursor.getInt(YEAR);

        return new Song(id, title, albumId, albumName, artistId, artistName, path, duration, year);
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
        Collections.sort(songs, (obj1, obj2) -> Integer.compare(obj1.duration, obj2.duration));
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
        qb.setTables(DatabaseHelper.SONGS_TABLE_NAME);

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
