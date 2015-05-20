package com.cookies.synergy.game;

import com.badlogic.gdx.Game;
import com.cookies.synergy.game.screens.loadingScreen;
import com.cookies.synergy.game.utils.assetManager;

public class main extends Game {

	@Override
	public void create () {
			this.setScreen(new loadingScreen(this));
	}


}
