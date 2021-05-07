package com.zitherharp.zhmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Artist;
import com.zitherharp.zhmusic.viewholder.ItemViewHolder;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    Context context;
    View itemView;

    List<Artist> artists;

    public ArtistAdapter(Context context, List<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String title = artists.get(position).getVietnameseName();
        String subtitle = String.format("%s songs - %s albums",
                artists.get(position).getSongCount(), artists.get(position).getAlbumCount());

        holder.tvTitle.setText(title);
        holder.tvSubtitle.setText(subtitle);
    }

    @Override
    public int getItemCount() {
        return artists == null ? 0 : artists.size();
    }
}