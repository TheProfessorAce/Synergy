package com.cookies.synergy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cookies.synergy.game.main;
import com.cookies.synergy.game.utils.constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = constants.appWidth;
		config.height = constants.appHeight;
		new LwjglApplication(new main(), config);
	}
}
