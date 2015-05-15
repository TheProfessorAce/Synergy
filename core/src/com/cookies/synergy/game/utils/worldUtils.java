package com.cookies.synergy.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.cookies.synergy.game.actors.Runner;
import com.cookies.synergy.game.box2d.chargeFieldUserData;
import com.cookies.synergy.game.box2d.chargeUserData;
import com.cookies.synergy.game.box2d.powerUpUserData;
import com.cookies.synergy.game.box2d.runnerUserData;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public class worldUtils {

    public static World createWorld() {
        return new World(constants.WORLD_GRAVITY, true);
    }

    public static Body createPowerUps(World world, float x, float y, int mode) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        CircleShape shape = new CircleShape();
        shape.setRadius(constants.POWERUP_RADIUS);
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        Box2DSprite box2DSprite = new Box2DSprite();
        if(mode == 1) {
            box2DSprite = new Box2DSprite(assetManager.manager.get(constants.POSPOWERUP_SPRITE, Texture.class));
        }
        else if (mode == 2) {
            box2DSprite = new Box2DSprite(assetManager.manager.get(constants.NEGPOWERUP_SPRITE, Texture.class));
        }
        else {
            box2DSprite = new Box2DSprite(assetManager.manager.get(constants.NEUTPOWERUP_SPRITE, Texture.class));
        }
        box2DSprite.setAdjustSize(true);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setSensor(true);
        fixture.setUserData(box2DSprite);
        body.setUserData(new powerUpUserData(mode));
        shape.dispose();
        return body;

    }

    public static Body createRunner(World world, float x, float y, int mode) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(x, y));
        bodyDef.angularDamping = 2f;
        CircleShape shape = new CircleShape();
        shape.setRadius(constants.RUNNER_RADIUS);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(constants.RUNNER_GRAVITY_SCALE);
        Box2DSprite box2DSprite = new Box2DSprite(assetManager.manager.get(constants.NEUTRUNNER_SPRITE, Texture.class));
        box2DSprite.setAdjustSize(true);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = constants.RUNNER_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.5f;
        Fixture fixture = body.createFixture(fixtureDef);
        body.resetMassData();
        fixture.setUserData(box2DSprite);
        body.setUserData(new runnerUserData(mode));
        shape.dispose();
        return body;
    }

    public static Body createCharge(World world, float x, float y, boolean isPositive) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        CircleShape shape = new CircleShape();
        shape.setRadius(constants.CHARGE_RADIUS);
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        Box2DSprite box2DSprite = new Box2DSprite();
        if(isPositive) {
            box2DSprite = new Box2DSprite(assetManager.manager.get(constants.POSCHARGE_SPRITE, Texture.class));
        }
        else {
            box2DSprite = new Box2DSprite(assetManager.manager.get(constants.NEGCHARGE_SPRITE, Texture.class));
        }
        box2DSprite.setAdjustSize(true);
        fixtureDef.density = constants.CHARGE_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = constants.CHARGE_TRANSPARENT;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setSensor(true);
        fixture.setUserData(box2DSprite);
        body.setUserData(new chargeUserData());
        shape.dispose();
        return body;
    }

    public static void updateBall (Runner runner, int mode) {
        Box2DSprite box2DSprite = new Box2DSprite();
        if(mode == 1) {
            box2DSprite = new Box2DSprite(assetManager.manager.get(constants.POSRUNNER_SPRITE, Texture.class));
        }
        else if (mode == 2) {
            box2DSprite = new Box2DSprite(assetManager.manager.get(constants.NEGRUNNER_SPRITE, Texture.class));
        }
        else {
            box2DSprite = new Box2DSprite(assetManager.manager.get(constants.NEUTRUNNER_SPRITE, Texture.class));
        }
        runner.getBody().getFixtureList().first().setUserData(box2DSprite);
        runner.getUserData().setMode(mode);
    }

    public static Body chargeField(World world, float x, float y) {
        BodyDef fieldDef = new BodyDef();
        fieldDef.position.set(x, y);
        fieldDef.angle = 0;
        CircleShape fieldShape = new CircleShape();
        fieldShape.setRadius(constants.ELECTROSTATIC_FIELD_RADIUS);
        Body field = world.createBody(fieldDef);
        FixtureDef fixtureDef = new FixtureDef();
        Box2DSprite box2DSprite = new Box2DSprite(assetManager.manager.get(constants.FIELD_SPRITE, Texture.class));
        box2DSprite.setAdjustSize(true);
        fixtureDef.density = constants.CHARGE_DENSITY;
        fixtureDef.shape = fieldShape;
        fixtureDef.isSensor = true;
        Fixture fixture = field.createFixture(fixtureDef);
        fixture.setUserData(box2DSprite);
        field.setUserData(new chargeFieldUserData());
        fieldShape.dispose();
        return field;
    }

}
