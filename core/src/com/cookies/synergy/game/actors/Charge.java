package com.cookies.synergy.game.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.cookies.synergy.game.box2d.chargeUserData;

public class Charge extends gameActor {

    public Charge(Body body) { super(body); }

    @Override
    public chargeUserData getUserData() {
        return (chargeUserData) UserData;
    }


}
