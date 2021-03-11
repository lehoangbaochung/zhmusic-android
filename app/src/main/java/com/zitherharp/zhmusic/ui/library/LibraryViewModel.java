package com.zitherharp.zhmusic.ui.library;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.ListView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.adapter.SongAdapter;
import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryViewModel extends ViewModel {

    //private MutableLiveData<Song> mSong;
    private List<Song> lSong;
    private List<HashMap<String,String>> aList;

    public LibraryViewModel() {
        // mSong = new MutableLiveData<>();
//        lSong = new ArrayList<>();
//        lSong.add(new Song("Hello", "1", "2", "3", "Hary", "4", 0, 0));
//        lSong.add(new Song("Hell", "2", "2", "3", "Hay", "4", 0, 0));
//        lSong.add(new Song("Heo", "3", "12", "5", "Ha", "1", 0, 0));
//        lSong.add(new Song("He", "4", "4", "1", "Har", "2", 0, 0));
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

        for (int i=0; i<10; i++) {
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

    public List<HashMap<String,String>> getList() {
        return aList;
    }
}