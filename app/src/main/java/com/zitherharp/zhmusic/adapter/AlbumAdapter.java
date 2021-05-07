package com.zitherharp.zhmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Album;
import com.zitherharp.zhmusic.viewholder.ItemViewHolder;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    Context context;
    View itemView;

    List<Album> albums;

    public AlbumAdapter(Context context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String title = albums.get(position).getVietnameseTitle();
        String subtitle = String.format("%s\n%s songs", albums.get(position).getArtistId(), albums.get(position).getCount());

        holder.tvTitle.setText(title);
        holder.tvSubtitle.setText(subtitle);
    }

    @Override
    public int getItemCount() {
        return albums == null ? 0 : albums.size();
    }
}