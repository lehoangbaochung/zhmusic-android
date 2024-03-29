package com.zitherharp.zhmusic.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.zitherharp.zhmusic.adapter.ArtistAdapter;
import com.zitherharp.zhmusic.adapter.SongAdapter;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(SongAdapter.getInstance(new ArtistAdapter()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}