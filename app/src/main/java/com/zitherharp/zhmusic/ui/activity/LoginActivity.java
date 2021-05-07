package com.zitherharp.zhmusic.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.credential.google.LoginCredential;
import com.zitherharp.zhmusic.helper.SheetHelper;
import com.zitherharp.zhmusic.model.Account;

public class LoginActivity extends Activity {
    Button bLogin;
    TextView tvAccountName, tvSubtitle;

    LoginCredential loginCredential;
    SheetHelper sheetHelper;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById();
        setOnClickListener();
        logIn();
    }

    void findViewById() {
        bLogin = findViewById(R.id.login_button);

        tvAccountName = findViewById(R.id.account_name);
        tvSubtitle = findViewById(R.id.login_subtitle);
    }

    void setOnClickListener() {
        if (bLogin.getText().equals("Sign in")) {
            bLogin.setOnClickListener(v -> logIn());
        } else {
            bLogin.setOnClickListener(v -> logIn());
        }

        tvSubtitle.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    void logIn() {
        if (loginCredential == null) {
            loginCredential = new LoginCredential(this);
        }

        loginCredential.getResultsFromApi();
        account = loginCredential.getAccount();

        if (account != null) {
            tvAccountName.setText(account.getDisplayName());
            tvSubtitle.setText(account.getUserName());
            bLogin.setText("Sign out");
        }

        if (sheetHelper == null) {
            sheetHelper = new SheetHelper(this, loginCredential);
            sheetHelper.retrieveValue();
        }
    }

    void logOut() {
        loginCredential.signOut();
        tvAccountName.setText("Guest");
        tvSubtitle.setText("Don't want to sign in?");
        bLogin.setText("Sign in");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginCredential.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sheetHelper.goMainActivity();
    }
}