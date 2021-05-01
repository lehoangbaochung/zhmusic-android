package com.zitherharp.zhmusic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Account;
import com.zitherharp.zhmusic.credential.GoogleSignIn;

import org.jetbrains.annotations.NotNull;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    public static Account GOOGLE_ACCOUNT = new Account();

    GoogleSignIn googleSignIn;
    SignInButton bSignIn;
    Button bSignOut;
    ImageView ivUserAvatar;
    TextView tvUserName, tvUserInfo, tvStatus;
    boolean isSignIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        findViewById();
        setOnClickListener();

        // khởi tạo phương thức đăng nhập bằng Google
        googleSignIn = new GoogleSignIn(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleSignIn.isSignIn()) {
            isSignIn = true;
            bSignIn.setVisibility(View.GONE);
        } else {
            isSignIn = false;
            bSignOut.setVisibility(View.GONE);
        }
        displayAccount();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleSignIn.onResult(requestCode, data);
        displayAccount();
        sendIntent();
    }

    @Override
    public void onClick(@NotNull View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                googleSignIn.signIn();
                break;
            case R.id.sign_out_button:
                googleSignIn.signOut();
                break;
        }
    }

    void findViewById() {
        ivUserAvatar = findViewById(R.id.user_avatar);
        tvUserName = findViewById(R.id.user_name);
        tvUserInfo = findViewById(R.id.user_information);
        tvStatus = findViewById(R.id.status);
        bSignIn = findViewById(R.id.sign_in_button);
        bSignIn.setSize(SignInButton.SIZE_STANDARD);
        bSignOut = findViewById(R.id.sign_out_button);
    }

    void setOnClickListener() {
        bSignIn.setOnClickListener(this);
        bSignOut.setOnClickListener(this);
    }

    void displayAccount() {
        if (!isSignIn) {
            ivUserAvatar.setImageResource(R.drawable.ring_btnplay);
            tvUserName.setText("Guest");
            tvUserInfo.setText("You are not signed in");
            tvStatus.setText("Signed out");
        } else {
            ivUserAvatar.setImageURI(GOOGLE_ACCOUNT.photoUri);
            tvUserName.setText(GOOGLE_ACCOUNT.displayName);
            tvUserInfo.setText(GOOGLE_ACCOUNT.userName);
            tvStatus.setText("Signed in");
        }
    }

    public void sendIntent() {
        Intent signInIntent = new Intent(this, MainActivity.class);
        signInIntent.putExtra("account_name", tvUserName.getText().toString());
        signInIntent.putExtra("account_info", tvUserInfo.getText().toString());
        setResult(RESULT_OK);
        finish();
    }
}