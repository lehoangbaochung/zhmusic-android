package com.zitherharp.zhmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Album;
import com.zitherharp.zhmusic.model.Artist;
import com.zitherharp.zhmusic.model.Song;
import com.zitherharp.zhmusic.provider.LibraryProvider;
import com.zitherharp.zhmusic.util.StaticList;
import com.zitherharp.zhmusic.viewholder.ItemViewHolder;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    Context context;
    View itemView;

    List<Song> songs;
    List<Artist> artists;
    List<Album> albums;
    int itemType;

    public ItemAdapter(Context context, int itemType) {
        this.context = context;
        this.itemType = itemType;

        switch (itemType) {
            case LibraryProvider.SONG:
                songs = StaticList.SONGS;
                break;
            case LibraryProvider.ARTIST:
                artists = StaticList.ARTISTS;
                break;
            case LibraryProvider.ALBUM:
                albums = StaticList.ALBUMS;
                break;
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (itemType) {
            case LibraryProvider.SONG:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
                break;
            case LibraryProvider.ARTIST:
            case LibraryProvider.ALBUM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
                break;
        }
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String title = null, subtitle = null, videoId = null;
        switch (itemType) {
            case LibraryProvider.SONG:
                title = songs.get(position).getVietnameseTitle();
                subtitle = songs.get(position).getArtistName();
                videoId = songs.get(position).getVideoId();
                break;
            case LibraryProvider.ARTIST:
                title = artists.get(position).getVietnameseName();
                subtitle = String.format("%s songs - %s albums",
                        artists.get(position).getSongCount(), artists.get(position).getAlbumCount());
                break;
            case LibraryProvider.ALBUM:
                title = albums.get(position).getVietnameseTitle();
                subtitle = String.format("%s\n%s songs", albums.get(position).getArtistId(), albums.get(position).getCount());
                break;
        }

        holder.tvTitle.setText(title);
        holder.tvSubtitle.setText(subtitle);
        holder.itemView.setTag(videoId);
       // new LibraryHelper(activity).setImage(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        switch (itemType) {
            case LibraryProvider.SONG:
                return songs.size();
            case LibraryProvider.ARTIST:
                return artists.size();
            case LibraryProvider.ALBUM:
                return albums.size();
        }
        return 0;
    }
}