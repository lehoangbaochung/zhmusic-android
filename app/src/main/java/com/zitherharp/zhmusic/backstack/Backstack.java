package com.zitherharp.zhmusic.backstack;

import android.content.Intent;

import java.util.Dictionary;
import java.util.Enumeration;

public class Backstack {
    public static Dictionary<Integer, Intent> Intent = new Dictionary<Integer, Intent>() {
        @Override
        public int size() {
            return Intent.size();
        }

        @Override
        public boolean isEmpty() {
            return Intent.size() == 0;
        }

        @Override
        public Enumeration<Integer> keys() {
            return null;
        }

        @Override
        public Enumeration<Intent> elements() {
            return null;
        }

        @Override
        public Intent get(Object key) {
            return Intent.get(key);
        }

        @Override
        public Intent put(Integer key, Intent value) {
            return Intent.put(key, value);
        }

        @Override
        public Intent remove(Object key) {
            return Intent.remove(key);
        }
    };
}