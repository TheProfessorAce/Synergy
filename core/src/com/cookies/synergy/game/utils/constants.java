package com.cookies.synergy.game.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.cookies.synergy.game.screens.gameScreen;
import com.cookies.synergy.game.stages.loadingStage;

public class constants {


    public static Game thisGame;
    public static int numberOfLaps = 3;
    public static float COOLDOWN_TIME = 1f;

    public static Preferences prefs = Gdx.app.getPreferences("SynergyPrefs");

    public static final int appWidth = 960;
    public static final int appHeight = 544;
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

    public static final String LOGO = "logo.png";

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

    public static final float MAINBUTTON_SIZE = 100f;
    public static final float BUTTON_SIZE = 50f;
    public static final float BUTTON2_SIZE = 75f;

    //TODO: STEP 1 MAP INSERT HERE
    public static final String LEVEL1_MAP = "maps/level1.tmx";
    public static final String LEVEL2_MAP = "maps/level2.tmx";
    public static final String LEVEL3_MAP = "maps/level3.tmx";

    //ADDLEVEL

    public static String MAP_USED = LEVEL1_MAP; //default start

    public static final int NUMBER_OF_POWERUPS_1 = 5;
    public static final int NUMBER_OF_POWERUPS_2 = 20;
    public static final int NUMBER_OF_POWERUPS_3 = 8;

    //ADDNUMBER

    public static boolean introDone = false;

    public static final String FONT = "fonts/Reckoner_Bold.ttf";

    public static final String BGM = "bgm/bgm.ogg";
    public static final String BLOOP = "sfx/bloop.mp3";
    public static final String CHIME = "sfx/chime.mp3";
    public static final String BLIP = "sfx/blip.mp3";
    public static final String BLURP = "sfx/blurp.mp3";

    public static final String STARTUP = "buttons/startup.png";
    public static final String STARTDOWN = "buttons/startdown.png";
    public static final String STARTING = "buttons/starting.png";

    public static final String HELPUP = "buttons/helpup.png";
    public static final String HELPDOWN = "buttons/helpdown.png";
    public static final String HELP = "buttons/tutorial.png";

    public static final String LEVELUP = "buttons/levelup.png";
    public static final String LEVELDOWN = "buttons/leveldown.png";

    public static final String BACKGROUND = "tilemap/bg.png";
    public static final String WHITE = "white.png";
    public static final String OPACITY = "opacity.png";

    public static boolean lightningActivate = false;
    public static final short IGNORE = 0x1;
    public static final short NOTHING = 0x1 << 1;
    public static final short LIGHT = 0x1 << 2;

}
