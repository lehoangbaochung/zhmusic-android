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

public class RecyclerViewFragment extends Fragment {
    RecyclerView recyclerView;
    int itemType;

    public RecyclerViewFragment(int itemType) {
        this.itemType = itemType;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        if (requireArguments().getInt("State") == 0) { // online state
            if (itemType == LibraryProvider.SONG) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }
            recyclerView.setAdapter(new ItemAdapter(getContext(), itemType));
        }
    }
}
