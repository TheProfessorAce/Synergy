package com.cookies.synergy.game;

import appwarp.WarpController;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.cookies.synergy.game.screens.loadingScreen;
import com.cookies.synergy.game.utils.constants;

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
    }

    @Override
    public void dispose() {
        super.dispose();
        WarpController.getInstance().stopApp();
        constants.prefs.remove("sessionid");
        constants.prefs.flush();
    }
}
