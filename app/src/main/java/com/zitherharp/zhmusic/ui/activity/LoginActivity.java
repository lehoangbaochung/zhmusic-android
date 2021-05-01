package com.zitherharp.zhmusic.ui.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.helper.SheetHelper;
import com.zitherharp.zhmusic.model.Account;
import com.zitherharp.zhmusic.util.ConstantString;
import com.zitherharp.zhmusic.util.RequestCode;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends Activity implements EasyPermissions.PermissionCallbacks {
    public static Account ACCOUNT;
    Button bFacebookLogin, bGoogleLogin;
    TextView tvAccountName, tvUserHelper;

    GoogleAccountCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById();
        setOnClickListener();
        initialize();
        getResultsFromApi();
    }

    void findViewById() {
        // buttons
        bFacebookLogin = findViewById(R.id.facebook_login_button);
        bGoogleLogin = findViewById(R.id.google_login_button);
        // textviews
        tvAccountName = findViewById(R.id.account_name);
        tvUserHelper = findViewById(R.id.user_helper);
    }

    void setOnClickListener() {
        bGoogleLogin.setOnClickListener(v -> getResultsFromApi());
    }

    void initialize() {
        credential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(SheetHelper.SHEET_SCOPES))
                .setBackOff(new ExponentialBackOff());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {}

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {}

    void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            Toast.makeText(this, ConstantString.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        } else {
            tvAccountName.setText(ACCOUNT.getDisplayName());
            tvUserHelper.setText(ACCOUNT.getUserName());
            bGoogleLogin.setText(R.string.sign_out);

            
        }
    }

    @AfterPermissionGranted(RequestCode.GET_ACCOUNTS_PERMISSION)
    void chooseAccount() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE).getString("accountName", null);
            if (accountName != null) {
                credential.setSelectedAccountName(accountName);
                ACCOUNT = new Account(credential.getSelectedAccountName());
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(credential.newChooseAccountIntent(), RequestCode.ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(this, ConstantString.REQUEST_ACCESS_GOOGLE_ACCOUNT,
                    RequestCode.GET_ACCOUNTS_PERMISSION, Manifest.permission.GET_ACCOUNTS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(this, ConstantString.REQUIRE_GOOGLE_PLAY_SERVICES, Toast.LENGTH_LONG).show();
                } else {
                    getResultsFromApi();
                }
                break;
            case RequestCode.ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                        editor.putString("accountName", accountName);
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

    boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    public void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        apiAvailability.getErrorDialog(LoginActivity.this, connectionStatusCode, RequestCode.GOOGLE_PLAY_SERVICES).show();
    }

    boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}