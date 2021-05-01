package com.zitherharp.zhmusic.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.ItemAdapter;
import com.zitherharp.zhmusic.provider.LibraryProvider;
import com.zitherharp.zhmusic.provider.SongProvider;
import com.zitherharp.zhmusic.viewholder.ItemViewHolder;

public class ItemFragment extends Fragment {
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        switch (requireArguments().getInt("Library")) {
            case 0:
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new ItemAdapter(getActivity(), LibraryProvider.SONGS, ItemViewHolder.LIST));
                break;
            case 1: case 2:
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setAdapter(new ItemAdapter(getActivity(), SongProvider.ONLINE_SONGS, ItemViewHolder.GRID));
                break;
            default:
                recyclerView.setAdapter(null);
        }
    }
}