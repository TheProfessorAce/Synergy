package com.cookies.synergy.game.stages;

import appwarp.WarpListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.cookies.synergy.game.screens.gameScreen;
import com.cookies.synergy.game.screens.startMultiplayerScreen;
import com.cookies.synergy.game.utils.assetManager;
import com.cookies.synergy.game.utils.constants;

import appwarp.WarpController;

public class initMultiplayerStage extends Stage implements WarpListener, Input.TextInputListener{

    private Game main;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private Label text;
    private Label text2;
    private Label.LabelStyle textStyle;
    private String username;

    private boolean online = true;
    private boolean passed = false;


    public initMultiplayerStage(Game game) {

        WarpController.getInstance().setListener(this);
        System.out.println(WarpController.getInstance().getConnectionState());

        constants.prefs.remove("username");

        main = game;

        generator = assetManager.manager.get(constants.FONT, FreeTypeFontGenerator.class);
        parameter.size =  35* (int)constants.scale;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        text = new Label("Connecting...", textStyle);
        text.setPosition(Gdx.graphics.getWidth() / 2 - text.getPrefWidth() / 2, Gdx.graphics.getHeight() / 2 - text.getPrefHeight() / 2);

        text2 = new Label("Press anywhere to continue offline.", textStyle);
        text2.setPosition(Gdx.graphics.getWidth() / 2 - text2.getPrefWidth() / 2, Gdx.graphics.getHeight() / 2 - text2.getPrefHeight() / 2 - 30f);

        addActor(text);

        if(constants.prefs.getString("username").isEmpty()) {
            Gdx.input.getTextInput(this, "Username", "", "Enter your desired username for the game");
        }

        else {
            this.text.setText("Connecting...");
            this.text.setPosition(Gdx.graphics.getWidth() / 2 - this.text.getWidth() / 2, Gdx.graphics.getHeight() / 2 - this.text.getHeight() / 2);
            WarpController.getInstance().startApp(constants.prefs.getString("username"));
        }
    }

    @Override
    public void act(float delta) {

        if(WarpController.CONNECTED) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    passed = true;
                    main.setScreen(new startMultiplayerScreen(main));
                }
            });
        }

        if(!online) {
            if(Gdx.input.justTouched()) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        main.setScreen(new gameScreen(main));
                    }
                });
            }
        }
        super.act(delta);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        if(!passed) {
            WarpController.getInstance().stopApp();
            constants.prefs.remove("sessionid");
            System.out.println("disposing");
        }
    }


    @Override
    public void input(String text) {
        username=text;
        constants.prefs.putString("username", username);
        this.text.setText("Connecting...");
        this.text.setPosition(Gdx.graphics.getWidth() / 2 - this.text.getWidth() / 2, Gdx.graphics.getHeight() / 2 - this.text.getHeight() / 2);

        WarpController.getInstance().startApp(constants.prefs.getString("username"));
    }

    @Override
    public void canceled() {
        constants.prefs.putString("username", "");
    }


    @Override
    public void onWaitingStarted(String s) {

    }

    @Override
    public void onError(String s) {
        this.text.setText("Cannot connect to server!");
        this.text.setPosition(Gdx.graphics.getWidth() / 2 - this.text.getPrefWidth() / 2, Gdx.graphics.getHeight() / 2 - this.text.getPrefHeight() / 2);
        addActor(text2);
        online = false;
    }

    @Override
    public void onGameStarted(String s) {

    }

    @Override
    public void onGameFinished(int i, boolean b) {

    }

    @Override
    public void onGameUpdateReceived(String s) {

    }

}
