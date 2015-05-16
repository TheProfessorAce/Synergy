package com.cookies.synergy.game.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.cookies.synergy.game.box2d.powerUpUserData;

public class PowerUp extends gameActor {

    public PowerUp(Body body) {
        super(body);
    }

    @Override
    public powerUpUserData getUserData() {
        return (powerUpUserData) UserData;
    }

    public Body getBody() {
        return this.body;
    }



}
