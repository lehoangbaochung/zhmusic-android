package com.zitherharp.zhmusic.helper;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.ui.activity.MainActivity;
import com.zitherharp.zhmusic.util.ConstantString;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SheetHelper {
    public static final String[] SHEET_SCOPES = { SheetsScopes.SPREADSHEETS_READONLY };
    static final String SHEET_ID = "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI";

    static final String SONGS_TABLE = "Song";
    static final String ARTISTS_TABLE = "Artist";
    static final String ALBUMS_TABLE = "Album";

    static final String SONGS_CELL = "A2:E";
    static final String ARTISTS_CELL = "A2:G";
    static final String ALBUMS_CELL = "A2:D";

    static final String SONGS_TABLE_RANGE = String.format("%s!%s", SONGS_TABLE, SONGS_CELL);
    static final String ARTISTS_TABLE_RANGE = String.format("%s!%s", ARTISTS_TABLE, ARTISTS_CELL);
    static final String ALBUMS_TABLE_RANGE = String.format("%s!%s", ALBUMS_TABLE, ALBUMS_CELL);

    MainActivity mainActivity;
    Sheets sheets;
    Exception exception;

    List<List<Object>> values;
    List<Song> songs;

    public SheetHelper(@NotNull MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        sheets = new Sheets.Builder(transport, jsonFactory, mainActivity.loginCredential.getCredential())
                .setApplicationName(ConstantString.APPLICATION_NAME)
                .build();
    }

    public List<Song> getSongs() {
        songs = new ArrayList<>();
        retrieveValue(SONGS_TABLE_RANGE, "songs");
        return songs;
    }

    void retrieveValue(String tableRange, String object) {
        ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setMessage(String.format("Loading %s...", object));
        progressDialog.show();

        new Thread(() -> {
            try {
                ValueRange response = sheets.spreadsheets().values().get(SHEET_ID, tableRange).execute();
                values = response.getValues();
                if (values != null) {
                    for (List<Object> row : values) {
                        int id = Integer.parseInt(row.get(0).toString());
                        String title = row.get(1).toString();
                        String artistName = row.get(2).toString();
                        String albumId = row.get(3).toString();
                        String videoId = row.get(4).toString();
                        songs.add(new Song(id, title, artistName, videoId, albumId));
                    }
                }
            } catch (IOException e) {
                exception = e;
            }
            mainActivity.runOnUiThread(() -> {
                progressDialog.hide();
                if (values == null || values.size() == 0) {
                    Toast.makeText(mainActivity, String.format("No %s returned: %s", object, exception.getMessage()),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mainActivity, "Get " + object + " successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}