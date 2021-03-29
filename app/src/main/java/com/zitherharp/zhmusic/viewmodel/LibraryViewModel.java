package com.zitherharp.zhmusic.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Song;

import java.io.File;
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

//        List<Song> songs = new SongAdapter().getSongList();
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

    public List<HashMap<String,String>> getList() {
        return aList;
    }

    public ArrayList<HashMap<String,String>> getPlayList(String rootPath) {
        ArrayList<HashMap<String,String>> fileList = new ArrayList<>();
        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles(); //here you will get NPE if directory doesn't contains  any file,handle it like this.
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    HashMap<String, String> song = new HashMap<>();
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                }
            }
            return fileList;
        } catch (Exception e) {
            return null;
        }
    }
}