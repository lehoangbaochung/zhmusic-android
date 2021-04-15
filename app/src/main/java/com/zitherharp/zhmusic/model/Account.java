package com.zitherharp.zhmusic.model;

import android.net.Uri;

public class Account {
    public String id;
    public Uri photoUri;
    public String displayName;
    public String userName;

    public Account() {}

    public Account(String id, String displayName, String userName, Uri photoUri) {
        this.id = id;
        this.displayName = displayName;
        this.userName = userName;
        this.photoUri = photoUri;
    }
}
