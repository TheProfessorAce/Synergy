package com.cookies.synergy.game.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cookies.synergy.game.box2d.userData;

public abstract class gameActor extends Actor {

    protected Body body;
    protected userData UserData;
    protected Rectangle screenRectangle;

    public gameActor(Body body) {
        this.body = body;
        this.UserData = (userData) body.getUserData();
        screenRectangle = new Rectangle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public abstract userData getUserData();

}
