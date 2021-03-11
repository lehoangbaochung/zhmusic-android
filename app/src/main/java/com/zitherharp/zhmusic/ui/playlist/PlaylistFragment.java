package com.zitherharp.zhmusic.ui.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import com.zitherharp.zhmusic.R;

public class PlaylistFragment extends Fragment {

    private PlaylistViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(PlaylistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_playlist, container, false);
        final TextView textView = root.findViewById(R.id.text_playlist);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}