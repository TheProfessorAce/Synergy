package com.cookies.synergy.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class constants {


    public static int gameState = 0;
    public static int numberOfLaps = 3;
    public static float COOLDOWN_TIME = 1f;

    public static String appKey = "dbc066469b6caaf9f748d71ef066274fcd2a19df84c4851f827949bf93d944b2";
    public static String secretKey="68095c60cc1fa72d4326be4675097765c6ba16ff752a7d5ac2cbf075d8530d9";

    public static final int appWidth = 800;
    public static final int appHeight = 640;
    public static final float scale = Gdx.graphics.getWidth()/appWidth;
    public static final float pixelsPerMeter = (float) 16*scale;

    public static final boolean DEBUG_ACTIVATE = false;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    public static final float RUNNER_RADIUS = 3f;
    public static float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_GRAVITY_SCALE = 6f;

    public static final float CHARGE_RADIUS = 2f;
    public static final float ELECTROSTATIC_CONSTANT = -600f;
    public static final float ELECTROSTATIC_FIELD_RADIUS = 15f;
    public static float CHARGE_DENSITY = 1f;
    public static final int CHARGE_MAXIMUM = 3;
    public static final boolean CHARGE_TRANSPARENT = true;

    public static final String POSRUNNER_SPRITE = "ball/posBall.png";
    public static final String NEGRUNNER_SPRITE = "ball/negBall.png";
    public static final String NEUTRUNNER_SPRITE = "ball/neutBall.png";
    public static final String NEGCHARGE_SPRITE = "charges/negCharge.png";
    public static final String POSCHARGE_SPRITE = "charges/posCharge.png";
    public static final String FIELD_SPRITE = "charges/circle.png";

    public static final String POSITIVE_BUTTON = "buttons/Positive.png";
    public static final String DOWN_BUTTON = "buttons/down.png";
    public static final String NEGATIVE_BUTTON = "buttons/Negative.png";

    public static final float POWERUP_RADIUS = 1f;
    public static final String POSPOWERUP_SPRITE = "powerups/posPowerUp.png";
    public static final String NEGPOWERUP_SPRITE = "powerups/negPowerUp.png";
    public static final String NEUTPOWERUP_SPRITE = "powerups/neutPowerUp.png";

    public static final float BUTTON_SIZE = 100f;

    public static final String LEVEL1_MAP = "maps/level1.tmx";
    public static final String LEVEL2_MAP = "maps/level2.tmx";
    public static final int NUMBER_OF_POWERUPS_1 = 5;
    public static final int NUMBER_OF_POWERUPS_2 = 20;

    public static final String FONT = "fonts/Reckoner.ttf";

    public static final String BGM = "bgm/bgm.mp3";
    public static final String BLOOP = "sfx/bloop.mp3";
    public static final String CHIME = "sfx/chime.mp3";
    public static final String BLIP = "sfx/blip.mp3";
    public static final String BLURP = "sfx/blurp.mp3";

    public static final String STARTUP = "buttons/startup.png";
    public static final String STARTDOWN = "buttons/startdown.png";
}
