package com.zitherharp.zhmusic.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.SongAdapter;
import com.zitherharp.zhmusic.ui.activity.MainActivity;

public class LibraryFragment extends Fragment {
    ListView lvLibrary;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        SongAdapter songAdapter = new SongAdapter(getContext(), MainActivity.songList);
        lvLibrary = root.findViewById(R.id.lvLibrary);
        lvLibrary.setAdapter(songAdapter);
        return root;
    }

    public void onClick(View v) {

    }
}