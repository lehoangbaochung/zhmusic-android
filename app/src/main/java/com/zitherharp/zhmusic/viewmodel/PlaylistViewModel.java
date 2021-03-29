package com.zitherharp.zhmusic.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class PlaylistViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlaylistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is playlist fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}