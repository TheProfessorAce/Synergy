package com.cookies.synergy.game.stages;

import com.appwarp.WarpController;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.cookies.synergy.game.screens.startMultiplayerScreen;
import com.cookies.synergy.game.utils.assetManager;
import com.cookies.synergy.game.utils.constants;

public class initMultiplayerStage extends Stage implements Input.TextInputListener {

    private Game main;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private Label text;
    private Label.LabelStyle textStyle;
    private String user;


    public initMultiplayerStage(Game game) {
        main = game;
        constants.prefs.remove("username");

        generator = assetManager.manager.get(constants.FONT, FreeTypeFontGenerator.class);
        parameter.size =  35* (int)constants.scale;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        text = new Label("Connecting...", textStyle);
        text.setPosition(Gdx.graphics.getWidth() / 2 - text.getWidth() / 2, Gdx.graphics.getHeight() / 2 - text.getHeight() / 2);

        addActor(text);

    }

    @Override
    public void act(float delta) {
        if(constants.prefs.getString("username").isEmpty()) {
            text.setText("Please touch anywhere to enter your desired username.");
            text.setPosition(Gdx.graphics.getWidth() / 2 - text.getPrefWidth() / 2, Gdx.graphics.getHeight() / 2 - text.getPrefHeight() / 2);
            if(Gdx.input.justTouched()) {
                Gdx.input.getTextInput(this, "Username", "", "Enter your desired username for the game");
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
    }


    @Override
    public void input(String text) {
        user=text;
        constants.prefs.putString("username", user);
        WarpController.getInstance().startApp(constants.prefs.getString("username"));
        constants.prefs.flush();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                main.setScreen(new startMultiplayerScreen(main));
            }
        });
    }

    @Override
    public void canceled() {
        constants.prefs.putString("username", "");
    }
}
