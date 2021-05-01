package com.zitherharp.zhmusic.model;

import android.net.Uri;

public class Account {
    public String id;
    public Uri photoUri;
    public String displayName;
    public String userName;

    public Account() {}

    public Account(String userName) {
        this.userName = userName;
        this.displayName = userName.substring(0, userName.indexOf('@'));
    }

    public Account(String displayName, String userName) {
        this.displayName = displayName;
        this.userName = userName;
    }

    public Account(String id, String displayName, String userName, Uri photoUri) {
        this.id = id;
        this.displayName = displayName;
        this.userName = userName;
        this.photoUri = photoUri;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserName() {
        return userName;
    }
}
