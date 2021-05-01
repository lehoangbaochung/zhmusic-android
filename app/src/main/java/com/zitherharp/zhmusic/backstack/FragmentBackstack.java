package com.zitherharp.zhmusic.backstack;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.ui.activity.MainActivity;

import org.jetbrains.annotations.NotNull;

public class FragmentBackstack {
    MainActivity mainActivity;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public FragmentBackstack(@NotNull MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        fragmentManager = mainActivity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    public void add(Fragment fragment) {
        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void replace(Fragment fragment) {
        fragmentTransaction.replace(R.id.main_layout, fragment);
        fragmentTransaction.commit();
    }

    public void hide(Fragment fragment) {
        fragmentTransaction.hide(fragment);
    }
}
