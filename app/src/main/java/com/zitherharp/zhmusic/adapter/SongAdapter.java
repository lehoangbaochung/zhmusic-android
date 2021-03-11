package com.zitherharp.zhmusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zitherharp.zhmusic.R;
import com.zitherharp.zhmusic.model.Album;
import com.zitherharp.zhmusic.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SimpleViewHolder> {
    //private final SongSelectedListener mSongSelectedListener;
    private Context context;
    private Activity mActivity;
    private List<Song> mSongs;
    private Album mAlbum;
    private Fragment fragment;

    public SongAdapter(@NonNull Activity activity, Album album) {
        mActivity = activity;
        mAlbum = album;
        mSongs = mAlbum.songs;
        // mSongSelectedListener = (SongSelectedListener) activity;
    }

    public View getView(View view, ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(position, null);

        TextView txt1 = view.findViewById(R.id.tvTitle);
        TextView txt2 = view.findViewById(R.id.tvArtist);

        txt1.setText(mSongs.get(position).title);
        txt2.setText(mSongs.get(position).artistName);

        return view;
    }

    public void swapSongs(Album album) {
        mAlbum = album;
        mSongs = mAlbum.songs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongAdapter.SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        View itemView = null;
        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SimpleViewHolder simpleViewHolder, int i) {
//        Song song = mSongs.get(holder.getAdapterPosition());
//        String songTitle = song.title;
//
//        int songTrack = Song.formatTrack(song.trackNumber);
//        Spanned spanned = Utils.buildSpanned(mActivity.getString(R.string.track_title, songTrack, songTitle));
//        holder.trackTitle.setText(spanned);
//        holder.duration.setText(Song.formatDuration(song.duration));
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //final TextView trackTitle, duration;

        SimpleViewHolder(View itemView) {
            super(itemView);

            //trackTitle = itemView.findViewById(R.id.track_title);
            //duration = itemView.findViewById(R.id.duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Song song = mSongs.get(getAdapterPosition());
            //mSongSelectedListener.onSongSelected(song, mAlbum);
        }
    }
}
