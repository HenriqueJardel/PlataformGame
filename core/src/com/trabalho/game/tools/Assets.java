package com.trabalho.game.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {

    public static final AssetManager manager = new AssetManager();

    public static void load(String file) {
        manager.load(file, Texture.class);
    }

    public static void loadMap(String file) {
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load(file, TiledMap.class);
    }

    public static void loadSounds(String file) {
        manager.load(file, Sound.class);
    }

    public static void loadMusic(String file) {
        manager.load(file, Music.class);
    }
}
