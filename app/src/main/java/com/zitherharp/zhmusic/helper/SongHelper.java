package com.zitherharp.zhmusic.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.zitherharp.zhmusic.model.Song;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SongHelper {
    static final String IMAGE_CODE = "R300x300M000";
    static final String ARTIST_ID = "T001";
    static final String ALBUM_ID = "T002";
    static final String SERVER_URL = "https://y.gtimg.cn/music/photo_new/";
    static final String IMAGE_EXTENSION = ".jpg";

    Activity activity;
    Song song;
    Bitmap bitmap;

    public SongHelper(Activity activity, Song song) {
        this.activity = activity;
        this.song = song;
    }

    public void setImage(ImageView imageView) {
        new Thread(() -> {
            try {
                InputStream inputStream = new URL(getImageUrl()).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(() -> {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }).start();
    }

    String getImageUrl() {
        if (song.getAlbumId() != null) {
            return SERVER_URL + ALBUM_ID + IMAGE_CODE + song.getAlbumId() + IMAGE_EXTENSION;
        } else {
            String[] artistIds = song.getArtistId().split("&");
            if (artistIds.length == 0) {
                return SERVER_URL + ARTIST_ID + IMAGE_CODE + song.getArtistId() + IMAGE_EXTENSION;
            } else {
                return SERVER_URL + ARTIST_ID + IMAGE_CODE + artistIds[0] + IMAGE_EXTENSION;
            }
        }
    }
}
