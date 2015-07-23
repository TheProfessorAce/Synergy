package com.cookies.synergy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.cookies.synergy.game.screens.gameScreen;
import com.cookies.synergy.game.screens.loadingScreen;
import com.cookies.synergy.game.utils.assetManager;
import com.cookies.synergy.game.utils.constants;

public class main extends Game {

    FPSLogger fps;

	@Override
	public void create () {
        this.setScreen(new loadingScreen(this));
        constants.thisGame = this;
        fps = new FPSLogger();
	}

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        constants.prefs.remove("sessionid");
        constants.prefs.flush();
    }
}
