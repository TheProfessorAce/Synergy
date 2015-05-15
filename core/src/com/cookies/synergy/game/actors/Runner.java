package com.cookies.synergy.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.cookies.synergy.game.box2d.runnerUserData;
import com.cookies.synergy.game.utils.constants;

import java.util.Random;
import java.util.Vector;

public class Runner extends gameActor {

    private float xDiff, yDiff, rad2, distance;

    public Runner(Body body) {
        super(body);
    }

    @Override
    public runnerUserData getUserData() {
        return (runnerUserData) UserData;
    }

    public void attract(Vector2 chargePosition) {

        xDiff = chargePosition.x - body.getPosition().x;
        yDiff = chargePosition.y - body.getPosition().y;
        rad2 = ((float) Math.pow(xDiff, 2)) + ((float) Math.pow(yDiff, 2));
        distance = (float) Math.sqrt(rad2);

        if(distance <= constants.ELECTROSTATIC_FIELD_RADIUS) {
            body.applyLinearImpulse(new Vector2((constants.ELECTROSTATIC_CONSTANT*xDiff)/rad2, (constants.ELECTROSTATIC_CONSTANT*yDiff)/rad2).rotate(180), body.getWorldCenter(), true);
        }

    }

    public void repulse(Vector2 chargePosition) {
        xDiff = chargePosition.x - body.getPosition().x;
        yDiff = chargePosition.y - body.getPosition().y;
        rad2 = ((float) Math.pow(xDiff, 2)) + ((float) Math.pow(yDiff, 2));
        distance = (float) Math.sqrt(rad2);

        if(distance <= constants.ELECTROSTATIC_FIELD_RADIUS) {
            body.applyLinearImpulse(new Vector2((constants.ELECTROSTATIC_CONSTANT*xDiff)/rad2, (constants.ELECTROSTATIC_CONSTANT*yDiff)/rad2), body.getWorldCenter(), true);
        }
    }

    public void randomMove() {
        Random generator = new Random();
        Vector2 chargePosition = new Vector2();
        chargePosition.set(generator.nextInt(Gdx.graphics.getWidth()), generator.nextInt(Gdx.graphics.getHeight()));
        xDiff = chargePosition.x - body.getPosition().x;
        yDiff = chargePosition.y - body.getPosition().y;
        rad2 = ((float) Math.pow(xDiff, 2)) + ((float) Math.pow(yDiff, 2));
        distance = (float) Math.sqrt(rad2);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Body getBody() {
        return body;
    }


}
