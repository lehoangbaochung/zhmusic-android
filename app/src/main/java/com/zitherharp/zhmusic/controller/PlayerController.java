package com.zitherharp.zhmusic.controller;

import com.zitherharp.zhmusic.service.MusicService;
import com.zitherharp.zhmusic.ui.activity.MainActivity;

public class PlayerController {
    MainActivity mainActivity;
    MusicService musicService;
    MusicController musicController;
    boolean playbackPaused = false;

    public PlayerController(MainActivity mainActivity, MusicService musicService) {
        this.mainActivity = mainActivity;
        this.musicService = musicService;
    }

}
