package com.zitherharp.zhmusic.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.helper.SongHelper;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.viewholder.ItemViewHolder;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    Activity activity;
    View itemView;

    List<Song> songs;
    int itemType;

    public ItemAdapter(Activity activity, List<Song> songs, int itemType) {
        this.activity = activity;
        this.songs = songs;
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (itemType) {
            case ItemViewHolder.LIST:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
                break;
            case ItemViewHolder.GRID:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
                break;
        }
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String title = songs.get(position).getTitle();
        String artist = songs.get(position).getArtistName();

        holder.tvTitle.setText(title);
        holder.tvArtist.setText(artist);
        new SongHelper(activity, songs.get(position)).setImage(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return songs == null ? 0 : songs.size();
    }
}