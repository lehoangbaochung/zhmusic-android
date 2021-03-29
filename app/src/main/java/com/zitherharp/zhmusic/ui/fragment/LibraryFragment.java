package com.zitherharp.zhmusic.ui.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.SongAdapter;
import com.zitherharp.zhmusic.viewmodel.LibraryViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class LibraryFragment extends Fragment {
    private LibraryViewModel libraryViewModel;
    private ListView listView;
    private SongAdapter songAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        listView = root.findViewById(R.id.lvLibrary);

        String[] from = { "file_path", "file_name" };
        int[] to = { R.id.tvTitle, R.id.tvArtist };

        ArrayList<HashMap<String,String>> songList = libraryViewModel.getPlayList("/storage/sdcard1/");
        if(songList!=null){
            for(int i=0;i<songList.size();i++){
                String fileName=songList.get(i).get("file_name");
                String filePath=songList.get(i).get("file_path");
                //here you will get list of file name and file path that present in your device
                Log.e("file details "," name ="+fileName +" path = "+filePath);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), songList, R.layout.fragment_library, from, to);
        listView.setAdapter(adapter);

        return root;
    }
}