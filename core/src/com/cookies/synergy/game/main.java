package com.cookies.synergy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.cookies.synergy.game.screens.loadingScreen;
import com.cookies.synergy.game.utils.assetManager;

public class main extends Game {

    FPSLogger fps;

	@Override
	public void create () {
        this.setScreen(new loadingScreen(this));
        fps = new FPSLogger();
	}

    @Override
    public void render() {
        super.render();
        //fps.log();
    }

}
