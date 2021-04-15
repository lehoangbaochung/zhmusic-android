package com.zitherharp.zhmusic.provider;

import android.content.UriMatcher;
import android.net.Uri;

import java.util.HashMap;

public class DatabaseProvider {
    static final String PROVIDER_NAME = "com.zitherharp.zhmusic.provider.Music";
    static final String URL = "content://" + PROVIDER_NAME + "/songs";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
//        uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
    }

    public DatabaseProvider() {

    }

    public void onCreate(String query) {

    }
}
