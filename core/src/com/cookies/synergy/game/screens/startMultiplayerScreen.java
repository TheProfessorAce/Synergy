package com.cookies.synergy.game.screens;

import com.appwarp.WarpController;
import com.appwarp.WarpListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.cookies.synergy.game.stages.multiplayerStage;
import com.cookies.synergy.game.utils.assetManager;

public class startMultiplayerScreen implements Screen, WarpListener {

    private multiplayerStage stage;
    private Game game;

    public startMultiplayerScreen(Game game) {
        this.game = game;
        assetManager.manager.update();
        stage = new multiplayerStage(game);
        WarpController.getInstance().setListener(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void onWaitingStarted(String message) {

    }

    @Override
    public void onError(String message) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new gameScreen(game));
            }
        });
    }

    @Override
    public void onGameStarted(String message) {

    }

    @Override
    public void onGameFinished(int code, boolean isRemote) {

    }

    @Override
    public void onGameUpdateReceived(String message) {

    }
}
