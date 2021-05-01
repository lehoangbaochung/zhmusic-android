package com.zitherharp.zhmusic.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zitherharp.zhmusic.ui.fragment.ItemFragment;

public class LibraryAdapter extends FragmentStateAdapter {
    Fragment fragment;
    Bundle args;

    public LibraryAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        fragment = new ItemFragment();
        args = new Bundle();
        args.putInt("Library", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
