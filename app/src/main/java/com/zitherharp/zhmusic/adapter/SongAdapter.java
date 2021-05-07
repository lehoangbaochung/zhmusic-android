package com.zitherharp.zhmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.viewholder.ItemViewHolder;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    Context context;
    View itemView;

    List<Song> songs;
    int state;

    public SongAdapter(Context context, List<Song> songs, int state) {
        this.context = context;
        this.songs = songs;
        this.state = state;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String title = songs.get(position).getVietnameseTitle();
        String subtitle = songs.get(position).getArtistName();
        String videoId = songs.get(position).getVideoId();

        holder.tvTitle.setText(title);
        holder.tvSubtitle.setText(subtitle);
        holder.itemView.setTag(videoId);
    }

    @Override
    public int getItemCount() {
        return songs == null ? 0 : songs.size();
    }
}