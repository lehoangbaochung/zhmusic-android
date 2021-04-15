package com.zitherharp.zhmusic.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.SongAdapter;
import com.zitherharp.zhmusic.ui.activity.MainActivity;

public class OnlineFragment extends Fragment {
    SwipeRefreshLayout refreshLayout;
    ListView lvSong;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_online, container, false);
        refreshLayout = root.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
        });

        SongAdapter songAdapter = new SongAdapter(getContext(), MainActivity.onlineSongList, true);
        lvSong = root.findViewById(R.id.online_song_listview);
        lvSong.setAdapter(songAdapter);
        return root;
    }
}