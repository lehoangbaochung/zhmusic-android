package com.zitherharp.zhmusic.credential;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.zitherharp.zhmusic.ui.activity.SigninActivity;

import org.jetbrains.annotations.NotNull;

public class GoogleSignIn implements OnConnectionFailedListener {
    static final int RC_SIGN_IN = 9001;

    SigninActivity signinActivity;
    ProgressDialog progressDialog;
    GoogleApiClient googleApiClient;

    public GoogleSignIn(SigninActivity signinActivity) {
        this.signinActivity = signinActivity;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(signinActivity)
                .enableAutoManage(signinActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(signinActivity, "Failed: " + connectionResult, Toast.LENGTH_LONG).show();
    }

    // Kiểm tra trạng thái đăng nhập
    public boolean isSignIn() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            // Nếu dữ liệu của người dùng trong bộ nhớ đệm hợp lệ, OptionalPendingResult sẽ ở trạng thái "done"
            // và GoogleSignInResult sẽ có ngay mà không cần thực hiện đăng nhập lại.
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
            return true;
        } else {
            // Nếu người dùng chưa từng đăng nhập trước đó, hoặc phiên làm việc đã hết hạn,
            // thao tác bất đồng bộ này sẽ ngầm đăng nhập người dùng, và thực hiện thao tác cross sign-on.
            showProgressDialog();
            opr.setResultCallback(googleSignInResult -> {
                hideProgressDialog();
                handleSignInResult(googleSignInResult);
            });
            return false;
        }
    }

    public void onResult(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    void handleSignInResult(@NotNull GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Đã đăng nhập thành công, hiển thị trạng thái đăng nhập.
            GoogleSignInAccount acc = result.getSignInAccount();
            signinActivity.GOOGLE_ACCOUNT.id = acc.getId();
            signinActivity.GOOGLE_ACCOUNT.displayName = acc.getDisplayName();
            signinActivity.GOOGLE_ACCOUNT.userName = acc.getEmail();
            signinActivity.GOOGLE_ACCOUNT.photoUri = acc.getPhotoUrl();
        } else {
            // Đã đăng xuất, hiển thị trạng thái đăng xuất.
            signinActivity.GOOGLE_ACCOUNT.id = null;
        }
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        signinActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(status -> {
            Toast.makeText(signinActivity, "Signed out", Toast.LENGTH_SHORT).show();
            signinActivity.sendIntent();
        });
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(signinActivity);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}
