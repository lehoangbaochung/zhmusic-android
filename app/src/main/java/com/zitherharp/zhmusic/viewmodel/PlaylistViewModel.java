package com.zitherharp.zhmusic.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaylistViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public PlaylistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("No playlist(s)\nClick Add on screen to add a new playlist");
    }

    public LiveData<String> getText() {
        return mText;
    }
}