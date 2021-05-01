package com.zitherharp.zhmusic.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Song;

import java.util.ArrayList;

public class LibAdapter extends BaseAdapter {
    ArrayList<Song> songs;
    LayoutInflater layoutInflater;
    boolean isOnline;

    public LibAdapter(Context context, ArrayList<Song> songs, boolean isOnline) {
        this.songs = songs;
        this.isOnline = isOnline;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return songs == null ? 0 : songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return songs.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Ánh xạ đến layout mỗi bài
        RelativeLayout songLayout = (RelativeLayout)layoutInflater.inflate(R.layout.item_song, parent, false);
        TextView songView = songLayout.findViewById(R.id.song_title);
        TextView artistView = songLayout.findViewById(R.id.artist_name);

        // Lấy bài hát
        Song currentSong = songs.get(position);

        // Lấy tên tiêu đề và tác giả
        songView.setText(currentSong.getTitle());
        artistView.setText(currentSong.getArtistName());

        // Cài đặt tag cho mỗi bài
        songLayout.setTag(currentSong.getPathId());

        return songLayout;
    }
}