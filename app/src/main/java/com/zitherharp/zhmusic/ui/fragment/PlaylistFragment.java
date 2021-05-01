package com.zitherharp.zhmusic.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.PlaylistAdapter;

public class PlaylistFragment extends Fragment {
    ListView lvPlaylist;
    TextView tvPlaylistStatus;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playlist, container, false);
        lvPlaylist = root.findViewById(R.id.playlist_listview);
        tvPlaylistStatus = root.findViewById(R.id.playlist_status);

        PlaylistAdapter playlistAdapter = new PlaylistAdapter(getContext(), null);
        lvPlaylist.setAdapter(playlistAdapter);
        return root;
    }
}
