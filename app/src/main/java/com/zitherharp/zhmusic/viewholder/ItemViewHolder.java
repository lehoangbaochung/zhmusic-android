package com.zitherharp.zhmusic.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharp.zhmusic.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle, tvSubtitle;
    public ImageView ivImage;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        ivImage = itemView.findViewById(R.id.item_image);
        tvTitle = itemView.findViewById(R.id.item_title);
        tvSubtitle = itemView.findViewById(R.id.item_subtitle);
    }
}
