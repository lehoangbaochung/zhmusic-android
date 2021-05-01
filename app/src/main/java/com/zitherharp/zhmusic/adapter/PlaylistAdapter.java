package com.zitherharp.zhmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Playlist;

import java.util.ArrayList;

public class PlaylistAdapter extends BaseAdapter {
    ArrayList<Playlist> playlists;
    LayoutInflater layoutInflater;

    public PlaylistAdapter(Context context, ArrayList playlists) {
        this.playlists = playlists;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return playlists == null ? 0 : playlists.size();
    }

    @Override
    public Object getItem(int position) {
        return playlists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return playlists.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout playlistLayout = (LinearLayout)layoutInflater.inflate(R.layout.item_song, parent, false);
        TextView songView = playlistLayout.findViewById(R.id.song_title);
        TextView artistView = playlistLayout.findViewById(R.id.artist_name);

        Playlist playlist = playlists.get(position);
        songView.setText(playlist.getTitle());
        artistView.setText(playlist.getCount());
        playlistLayout.setTag(playlist.getId());

        return playlistLayout;
    }
}
