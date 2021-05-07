package com.zitherharp.zhmusic.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zitherharp.zhmusic.ui.fragment.RecyclerViewFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    Fragment fragment;
    Bundle args;
    int itemType;

    public ViewPagerAdapter(@NonNull Fragment fragment, int itemType) {
        super(fragment);
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        fragment = new RecyclerViewFragment(itemType);
        args = new Bundle();
        args.putInt("State", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2; // online and offline state
    }

    public void onAttach(TabLayout tabLayout, ViewPager2 viewPager) {
        new TabLayoutMediator(tabLayout, viewPager, false, true, (tab, position) -> {
            if (position == 0) {
                tab.setText("Online");
            } else {
                tab.setText("Offline");
            }
        }).attach();
    }
}
