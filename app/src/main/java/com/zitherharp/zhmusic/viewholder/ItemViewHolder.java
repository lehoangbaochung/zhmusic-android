package com.zitherharp.zhmusic.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharp.zhmusic.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public static final int LIST = 0;
    public static final int GRID = 1;

    public TextView tvTitle, tvArtist;
    public ImageView ivImage;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        ivImage = itemView.findViewById(R.id.item_image);
        tvTitle = itemView.findViewById(R.id.item_title);
        tvArtist = itemView.findViewById(R.id.item_subtitle);
    }
}
