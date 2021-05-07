package com.zitherharp.zhmusic.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.zitherharp.zhmusic.credential.google.LoginCredential;
import com.zitherharp.zhmusic.model.Account;
import com.zitherharp.zhmusic.model.Artist;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.ui.activity.MainActivity;
import com.zitherharp.zhmusic.util.ConstantString;
import com.zitherharp.zhmusic.util.StaticList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SheetHelper {
    public static final String[] SHEET_SCOPES = { SheetsScopes.SPREADSHEETS_READONLY };
    static final String SHEET_ID = "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI";

    static final String SONG_TABLE = "Song";
    static final String ARTIST_TABLE = "Artist";
    static final String ALBUM_TABLE = "Album";

    static final String SONG_CELL = "A2:Q";
    static final String ARTIST_CELL = "A2:I";
    static final String ALBUM_CELL = "A2:I";

    static final String SONG_TABLE_RANGE = String.format("%s!%s", SONG_TABLE, SONG_CELL);
    static final String ARTIST_TABLE_RANGE = String.format("%s!%s", ARTIST_TABLE, ARTIST_CELL);
    static final String ALBUM_TABLE_RANGE = String.format("%s!%s", ALBUM_TABLE, ALBUM_CELL);

    Activity activity;
    Sheets sheets;
    Exception exception;

    List<List<Object>> values;
    LoginCredential loginCredential;

    public SheetHelper(@NotNull Activity activity, @NotNull LoginCredential loginCredential) {
        this.activity = activity;
        this.loginCredential = loginCredential;

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        sheets = new Sheets.Builder(transport, jsonFactory, loginCredential.getCredential())
                .setApplicationName(ConstantString.APPLICATION_NAME)
                .build();
    }

    void retrieveSongs() {
        try {
            values = sheets.spreadsheets().values().get(SHEET_ID, SONG_TABLE_RANGE).execute().getValues();
            if (values != null) {
                StaticList.SONGS = new ArrayList<>();
                for (List<Object> row : values) {
                    StaticList.SONGS.add(new Song(
                            row.get(0).toString(), // id
                            row.get(1).toString(), // artist id
                            row.get(2).toString(), // album id
                            row.get(3).toString(), // video id
                            row.get(5).toString(), // vn title
                            row.get(6).toString(), // py title
                            row.get(7).toString(), // sc title
                            row.get(8).toString(), // tc title
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                    ));
                }
            }
        } catch (Exception e) {
            exception = e;
        }
    }

    void retrieveArtists() {
        try {
            values = sheets.spreadsheets().values().get(SHEET_ID, ARTIST_TABLE_RANGE).execute().getValues();
            if (values != null) {
                StaticList.ARTISTS = new ArrayList<>();
                for (List<Object> row : values) {
                    StaticList.ARTISTS.add(new Artist(
                        row.get(0).toString(), // id
                        row.get(1).toString(), // playlist id
                        row.get(2).toString(), // vn name
                        row.get(3).toString(), // py name
                        row.get(4).toString(), // sc name
                        row.get(5).toString(), // tc name
                        "", // vn des
                        "", // sc des
                        "" // tc des
                    ));
                }
            }
        } catch (Exception e) {
            exception = e;
        }
    }

    void retrieveAlbums() {
        try {
            ValueRange response = sheets.spreadsheets().values().get(SHEET_ID, ALBUM_TABLE_RANGE).execute();
            values = response.getValues();
            if (values != null) {
                StaticList.ALBUMS = new ArrayList<>();
                for (List<Object> row : values) {
                    String id = row.get(0).toString();
                    String artistId = row.get(1).toString();
                    String vietnameseName = row.get(2).toString();

                }
            }
        } catch (Exception e) {
            exception = e;
        }
    }

    public void retrieveValue() {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        new Thread(() -> {
            retrieveSongs();
            retrieveAlbums();
            retrieveArtists();
            activity.runOnUiThread(() -> {
                if (values == null || values.size() == 0) {
                    Toast.makeText(activity, "No data returned: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Get data successfully", Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();
                goMainActivity();
            });
        }).start();
    }

    public void goMainActivity() {
        Account account = loginCredential.getAccount();
        if (account != null) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("loginName", account.getDisplayName());
            intent.putExtra("loginEmail", account.getUserName());
            activity.startActivity(intent);
        }
    }
}