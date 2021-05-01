package com.zitherharp.zhmusic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.LibraryAdapter;
import com.zitherharp.zhmusic.credential.google.LoginCredential;

import org.jetbrains.annotations.NotNull;

public class LibraryFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    LibraryAdapter libraryAdapter;

    LoginCredential loginCredential;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        libraryAdapter = new LibraryAdapter(this);
        viewPager = view.findViewById(R.id.library_viewpager);
        viewPager.setAdapter(libraryAdapter);

        tabLayout = view.findViewById(R.id.library_tablayout);
        new TabLayoutMediator(tabLayout, viewPager, false, true, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Song");
                    break;
                case 1:
                    tab.setText("Artist");
                    break;
                case 2:
                    tab.setText("Album");
                    break;
                default:
                    tab.setText("");
            }
        }).attach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}