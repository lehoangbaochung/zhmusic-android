package com.zitherharp.zhmusic.ui.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.SongAdapter;
import com.zitherharp.zhmusic.service.PlayerService;
import com.zitherharp.zhmusic.ui.activity.MainActivity;
import com.zitherharp.zhmusic.viewmodel.LibraryViewModel;

public class LibraryFragment extends Fragment {
    private LibraryViewModel libraryViewModel;
    private ListView listView;
    private SongAdapter songAdapter;
    private PlayerService PlayerService;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);

        SongAdapter songAdapter = new SongAdapter(getContext(), MainActivity.songList);
        listView = root.findViewById(R.id.lvLibrary);
        listView.setAdapter(songAdapter);
        registerForContextMenu(listView);
        return root;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvLibrary) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String obj = (String)lv.getItemAtPosition(acmi.position);

            menu.add("One");
            menu.add("Two");
            menu.add("Three");
            menu.add("Four");
        }
    }
}