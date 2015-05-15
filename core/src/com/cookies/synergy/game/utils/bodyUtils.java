package com.cookies.synergy.game.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.cookies.synergy.game.box2d.userData;
import com.cookies.synergy.game.enums.userDataType;

public class bodyUtils {

    public static boolean bodyIsPowerUp(Body body) {
        userData UserData = (userData) body.getUserData();

        return UserData != null && UserData.getUserDataType() == userDataType.POWERUP;
    }

    public static boolean bodyIsCharge(Body body) {
        userData UserData = (userData) body.getUserData();

        return UserData != null && UserData.getUserDataType() == userDataType.CHARGE;
    }

    public static boolean bodyIsRunner(Body body) {
        userData UserData = (userData) body.getUserData();

        return UserData != null && UserData.getUserDataType() == userDataType.RUNNER;
    }

}
