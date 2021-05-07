package com.zitherharp.zhmusic.credential.google;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.zitherharp.zhmusic.helper.SheetHelper;
import com.zitherharp.zhmusic.model.Account;
import com.zitherharp.zhmusic.util.ConstantString;
import com.zitherharp.zhmusic.util.RequestCode;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginCredential implements EasyPermissions.PermissionCallbacks {
    GoogleAccountCredential credential;
    Activity activity;
    Account account;

    public LoginCredential(@NotNull Activity activity) {
        this.activity = activity;
        credential = GoogleAccountCredential.usingOAuth2(activity.getApplicationContext(),
                Arrays.asList(SheetHelper.SHEET_SCOPES)).setBackOff(new ExponentialBackOff());
    }

    public void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            Toast.makeText(activity, ConstantString.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        } else {
            account = new Account(credential.getSelectedAccountName());
        }
    }

    @AfterPermissionGranted(RequestCode.GET_ACCOUNTS_PERMISSION)
    void chooseAccount() {
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = activity.getPreferences(Context.MODE_PRIVATE).getString("accountName", null);
            if (accountName != null) {
                credential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                activity.startActivityForResult(credential.newChooseAccountIntent(), RequestCode.ACCOUNT_PICKER);
            }
        } else {
            EasyPermissions.requestPermissions(activity, ConstantString.REQUEST_ACCESS_GOOGLE_ACCOUNT,
                    RequestCode.GET_ACCOUNTS_PERMISSION, Manifest.permission.GET_ACCOUNTS);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCode.GOOGLE_PLAY_SERVICES:
                if (resultCode != Activity.RESULT_OK) {
                    Toast.makeText(activity, ConstantString.REQUIRE_GOOGLE_PLAY_SERVICES, Toast.LENGTH_LONG).show();
                } else {
                    getResultsFromApi();
                }
                break;
            case RequestCode.ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
                        editor.putString("accountName", accountName);
                        editor.apply();
                        credential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case RequestCode.AUTHORIZATION:
                if (resultCode == Activity.RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        apiAvailability.getErrorDialog(activity, connectionStatusCode, RequestCode.GOOGLE_PLAY_SERVICES).show();
    }

    boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        activity.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void signOut() {
        SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public GoogleAccountCredential getCredential() {
        return credential;
    }

    public Account getAccount() {
        return account;
    }
}