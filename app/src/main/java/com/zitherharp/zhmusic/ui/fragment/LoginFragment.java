package com.zitherharp.zhmusic.ui.fragment;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.util.RequestCode;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class LoginFragment extends Fragment
        implements EasyPermissions.PermissionCallbacks, View.OnClickListener {
    AppCompatActivity fragmentActivity;

    Button bGoogleLogin;
    ProgressDialog progressDialog;

    GoogleAccountCredential credential;
    static final String[] SCOPES = { SheetsScopes.SPREADSHEETS_READONLY };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bGoogleLogin = view.findViewById(R.id.google_login_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentActivity = (AppCompatActivity)getActivity();
        fragmentActivity.getSupportActionBar().hide();

        credential = GoogleAccountCredential.usingOAuth2(fragmentActivity.getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(fragmentActivity, "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app", Toast.LENGTH_LONG).show();
                } else {
                    getResultsFromApi();
                }
                break;
            case RequestCode.ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings = fragmentActivity.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("PREF_ACCOUNT_NAME", accountName);
                        editor.apply();
                        credential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case RequestCode.AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, fragmentActivity);
    }

    boolean isDeviceOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)fragmentActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(fragmentActivity);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(fragmentActivity);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        apiAvailability.getErrorDialog(fragmentActivity, connectionStatusCode,
                RequestCode.GOOGLE_PLAY_SERVICES).show();
    }

    @AfterPermissionGranted(RequestCode.GET_ACCOUNTS_PERMISSION)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(fragmentActivity, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = fragmentActivity.getPreferences(Context.MODE_PRIVATE)
                    .getString("PREF_ACCOUNT_NAME", null);
            if (accountName != null) {
                credential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(credential.newChooseAccountIntent(), RequestCode.ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(this,
                    "This app needs to access your Google account (via Contacts)",
                    RequestCode.GET_ACCOUNTS_PERMISSION, Manifest.permission.GET_ACCOUNTS);
        }
    }

    void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            Toast.makeText(fragmentActivity, "No network connection available", Toast.LENGTH_SHORT).show();
        } else {
            // đăngnhập gg
            //new SheetActivity.MakeRequestTask(mCredential).execute();
        }
    }


    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.google_login_button:
                bGoogleLogin.setEnabled(false);
                getResultsFromApi();
                bGoogleLogin.setEnabled(true);
                break;
        }
    }
}