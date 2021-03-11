package com.zitherharp.zhmusic.ui.library;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.SongAdapter;
import com.zitherharp.zhmusic.ui.main.MainViewModel;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    private ListView listView;
    private SongAdapter songAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        listView = root.findViewById(R.id.lvLibrary);

        String[] from = { "flag", "txt", "cur" };
        int[] to = { R.id.imageView, R.id.tvTitle, R.id.tvArtist };

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), libraryViewModel.getList(),
                R.layout.listview_item_grid_layout, from, to);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener((parent, view, position, id)
//                -> Toast.makeText(libraryViewModel.this, "", Toast.LENGTH_SHORT).show());
        return root;
    }
}