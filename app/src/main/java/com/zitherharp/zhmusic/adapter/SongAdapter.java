package com.zitherharp.zhmusic.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
    // Song list và layout
    private ArrayList<Song> songs;
    private LayoutInflater songInf;

    // Constructor
    public SongAdapter(Context c, ArrayList<Song> theSongs) {
        songs = theSongs;
        songInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Ánh xạ đến layout mỗi bài
        LinearLayout songLayout = (LinearLayout)songInf.inflate(R.layout.listview_item_grid_layout, parent, false);
        TextView songView = songLayout.findViewById(R.id.song_title);
        TextView artistView = songLayout.findViewById(R.id.artist_name);

        // Lấy bài hát
        Song currentSong = songs.get(position);

        // Lấy tên tiêu đề và tác giả
        songView.setText(currentSong.getTitle());
        artistView.setText(currentSong.getArtistName());

        // Cài đặt tag cho mỗi bài
        songLayout.setTag(position);
        return songLayout;
    }
}