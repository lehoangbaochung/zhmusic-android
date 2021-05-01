package com.zitherharp.zhmusic.viewmodel;

import androidx.lifecycle.ViewModel;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryViewModel extends ViewModel {

    //private MutableLiveData<Song> mSong;
    private List<Song> lSong;
    private List<HashMap<String,String>> aList;

    public LibraryViewModel() {
        aList = new ArrayList<>();

        String[] mNames = { "Fabian", "Carlos", "Alex", "Andrea", "Karla",
                "Freddy", "Lazaro", "Hector", "Carolina", "Edwin", "Jhon",
                "Edelmira", "Andres" };
        String[] mAnimals = { "Perro", "Gato", "Oveja", "Elefante", "Pez",
                "Nicuro", "Bocachico", "Chucha", "Curie", "Raton", "Aguila",
                "Leon", "Jirafa" };
        int[] flags = new int[]{
                R.drawable.download_icon,
                R.drawable.download_icon,
                R.drawable.download_icon,
                R.drawable.download_icon,
                R.drawable.download_icon,
                R.drawable.download_icon,
                R.drawable.download_icon,
                R.drawable.download_icon,
                R.drawable.download_icon,
                R.drawable.download_icon,
        };

//        List<Song> songs = new LibAdapter().getSongList();
//        for (Song song : songs) {
//            HashMap<String, String> hm = new HashMap<>();
//            hm.put("txt", song.title);
//            hm.put("cur", song.artistName);
//            aList.add(hm);
//        }
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("txt", "App Name : " + mNames[i]);
            hm.put("cur","creator : " + mAnimals[i]);
            hm.put("flag", Integer.toString(flags[i]) );

            aList.add(hm);
        }

    }

    public List<Song> getSongs() {
        return lSong;
    }
}