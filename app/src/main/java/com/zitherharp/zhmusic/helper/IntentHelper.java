package com.zitherharp.zhmusic.helper;

import android.content.Context;
import android.content.Intent;

public class IntentHelper {
    Intent intent;
    Context context;

    public IntentHelper(Context context) {
        this.context = context;
        intent = new Intent();
    }

    public void putExtra(String key, String value) {
        intent.putExtra(key, value);
    }

    public void startActivity(Class activityClass) {
        intent.setClass(context, activityClass);
        context.startActivity(intent);
    }
}