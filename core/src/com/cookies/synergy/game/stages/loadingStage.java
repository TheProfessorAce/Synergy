package com.cookies.synergy.game.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.cookies.synergy.game.screens.gameScreen;
import com.cookies.synergy.game.utils.assetManager;
import com.cookies.synergy.game.utils.constants;

public class loadingStage extends Stage{

    private Game main;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private Label text;
    private Label.LabelStyle textStyle;

    public loadingStage(Game game) {
        main = game;

        assetManager.load();

        generator = assetManager.manager.get(constants.FONT, FreeTypeFontGenerator.class);
        parameter.size =  35* (int)constants.scale;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        text = new Label("Loading...", textStyle);
        text.setPosition(Gdx.graphics.getWidth() / 2 - text.getWidth() / 2, Gdx.graphics.getHeight() / 2 - text.getHeight() / 2);

        addActor(text);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(assetManager.isLoaded()) {
            main.setScreen(new gameScreen());
        }

    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}