package com.cookies.synergy.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class assetManager {

    public static AssetManager manager = new AssetManager();
    public static boolean initLoaded = false;
    public static boolean allLoaded = false;

    public static void loadBefore() {
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(new InternalFileHandleResolver()));
        manager.load(constants.FONT, FreeTypeFontGenerator.class);

        manager.finishLoading();
        initLoaded = true;
    }

    public static void load() {

        if(!initLoaded) {
            loadBefore();
        }

        manager.load(constants.POSRUNNER_SPRITE, Texture.class);
        manager.load(constants.NEGRUNNER_SPRITE, Texture.class);
        manager.load(constants.NEUTRUNNER_SPRITE, Texture.class);
        manager.load(constants.NEGCHARGE_SPRITE, Texture.class);
        manager.load(constants.POSCHARGE_SPRITE, Texture.class);
        manager.load(constants.FIELD_SPRITE, Texture.class);
        manager.load(constants.POSITIVE_BUTTON, Texture.class);
        manager.load(constants.DOWN_BUTTON, Texture.class);
        manager.load(constants.NEGATIVE_BUTTON, Texture.class);
        manager.load(constants.POSPOWERUP_SPRITE, Texture.class);
        manager.load(constants.NEGPOWERUP_SPRITE, Texture.class);
        manager.load(constants.NEUTPOWERUP_SPRITE, Texture.class);
        manager.load(constants.BGM, Music.class);
        manager.load(constants.BLOOP, Sound.class);
        manager.load(constants.CHIME, Sound.class);
        manager.load(constants.BLIP, Sound.class);
        manager.load(constants.BLURP, Sound.class);
        manager.load(constants.STARTUP, Texture.class);
        manager.load(constants.STARTDOWN, Texture.class);
        manager.load(constants.STARTING, Texture.class);
        manager.load(constants.HELPUP, Texture.class);
        manager.load(constants.HELPDOWN, Texture.class);
        manager.load(constants.HELP, Texture.class);

        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(constants.LEVEL1_MAP, TiledMap.class);
        manager.load(constants.LEVEL2_MAP, TiledMap.class);

        allLoaded = true;

    }
    public static Boolean isLoaded() {
        manager.update();
        if(manager.getProgress() >= 1 && allLoaded) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void dispose() {
        manager.clear();
    }
}
