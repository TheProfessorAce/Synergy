package com.cookies.synergy.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.cookies.synergy.game.main;
import com.cookies.synergy.game.utils.constants;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(constants.appWidth, constants.appHeight);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new main();
        }
}