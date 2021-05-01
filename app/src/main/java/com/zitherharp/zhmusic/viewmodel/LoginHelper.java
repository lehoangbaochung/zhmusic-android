package com.zitherharp.zhmusic.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.zitherharp.zhmusic.helper.SheetHelper;
import com.zitherharp.zhmusic.model.Account;
import com.zitherharp.zhmusic.provider.LibraryProvider;
import com.zitherharp.zhmusic.util.ConstantString;
import com.zitherharp.zhmusic.util.RequestCode;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginHelper {
    Activity activity;

    Account account;
    GoogleAccountCredential credential;

    public LoginHelper(@NotNull Activity activity) {
        this.activity = activity;
        credential = GoogleAccountCredential.usingOAuth2(activity.getApplicationContext(),
                Arrays.asList(SheetHelper.SHEET_SCOPES)).setBackOff(new ExponentialBackOff());
    }

    void getResultsFromApi() {
        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            LibraryProvider.SONGS = new SheetHelper(activity, credential).getSongs();
        }
    }

    @AfterPermissionGranted(RequestCode.GET_ACCOUNTS_PERMISSION)
    void chooseAccount() {
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = activity.getPreferences(Context.MODE_PRIVATE).getString("accountName", null);
            if (accountName != null) {
                credential.setSelectedAccountName(accountName);
                account = new Account(credential.getSelectedAccountName());
                getResultsFromApi();
            } else {
                activity.startActivityForResult(credential.newChooseAccountIntent(), RequestCode.ACCOUNT_PICKER);
            }
        } else {
            EasyPermissions.requestPermissions(activity, ConstantString.REQUEST_ACCESS_GOOGLE_ACCOUNT,
                    RequestCode.GET_ACCOUNTS_PERMISSION, Manifest.permission.GET_ACCOUNTS);
        }
    }
}