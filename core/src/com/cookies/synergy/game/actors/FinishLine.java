package com.cookies.synergy.game.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.cookies.synergy.game.box2d.finishLineUserData;

public class FinishLine extends gameActor {

    public FinishLine(Body body) {
        super(body);
    }

    @Override
    public finishLineUserData getUserData() {
        return (finishLineUserData) UserData;
    }



}
