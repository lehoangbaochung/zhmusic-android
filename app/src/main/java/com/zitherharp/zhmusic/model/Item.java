package com.zitherharp.zhmusic.model;

public class Item {
    public final String id;
    public final String title;
    public final String subtitle;
    public final String imageUrl;

    public Item(String id, String title, String subtitle, String imageUrl) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
