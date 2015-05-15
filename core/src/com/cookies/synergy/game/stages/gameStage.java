package com.cookies.synergy.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.cookies.synergy.game.actors.Charge;
import com.cookies.synergy.game.actors.ChargeField;
import com.cookies.synergy.game.actors.PowerUp;
import com.cookies.synergy.game.actors.Runner;
import com.cookies.synergy.game.utils.assetManager;
import com.cookies.synergy.game.utils.bodyUtils;
import com.cookies.synergy.game.utils.constants;
import com.cookies.synergy.game.utils.worldUtils;
import com.sun.jmx.snmp.tasks.Task;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

import java.util.Random;

public class gameStage extends Stage implements ContactListener {

    private static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth() / constants.pixelsPerMeter; //40
    private static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight() / constants.pixelsPerMeter; //26
    private World world;
    private Runner runner;
    private Charge charge;
    private ChargeField chargefield;
    private PowerUp powerUp;

    private PowerUp compareUp;

    private SpriteBatch batch;
    private SpriteBatch hudBatch;

    private ImageButton.ImageButtonStyle chargeButtonStyle;
    private ImageButton chargeButton;
    private ImageButton.ImageButtonStyle startButtonStyle;
    private ImageButton startButton;

    private Stage hudStage;

    private Array<Body> chargeFields = new Array<Body>();
    private Array<Boolean> chargeCharges = new Array<Boolean>();

    private Array<Body> posCharges = new Array<Body>();
    private Array<Body> negCharges = new Array<Body>();

    private Array<Body> toBeTransferred = new Array<Body>();

    private Body removeCharge;

    private boolean isPositive = true;

    private final float TIME_STEP = 1/300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private Box2DDebugRenderer renderer;

    private boolean positiveAttract, negativeAttract;
    private int RunnerMode = 0;

    private Vector3 press;

    private InputMultiplexer multiplexer = new InputMultiplexer();

    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private Box2DMapObjectParser parser = new Box2DMapObjectParser((1/8f));
    private boolean debug;

    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private Label countdown;
    private Label text;
    private Label text2;
    private Label text3;
    private Label.LabelStyle textStyle;

    private int lap = 1;
    private float playTime = 0.0f;
    private boolean finished = false;
    private float finishTime;
    private float[] cooldownTime = new float[3];
    private boolean chargeAllowed = true;
    private boolean timeRecorded = false;

    private boolean gameStarted = false;

    private Music bgm;
    private Sound placeCharge;
    private Sound finish;
    private Sound click;
    private Sound charged;

    private int numOfCharges;
    private float timer = 10.999999999f;
    private boolean gameStarting = false;
    private boolean initDone = false;
    private boolean tempBool = false;

    public gameStage() {
        setupCamera();

        hudStage = new Stage(new ExtendViewport(constants.appWidth, constants.appHeight, hudCamera));

        setupWorld();

        setupTouchControlAreas();

        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        setupHUD();

        for(int x=0;x<constants.CHARGE_MAXIMUM;x++) {
            cooldownTime[x] = 0;
        }

    }

    private void setupHUD() {
        generator = assetManager.manager.get(constants.FONT, FreeTypeFontGenerator.class);
        parameter.size =  35* (int)constants.scale;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        countdown = new Label("Starting in..." + timer, textStyle);
        countdown.setPosition(hudCamera.viewportWidth/2 - countdown.getWidth()/2, hudCamera.viewportHeight/2 - countdown.getHeight()/2);

        text = new Label("Lap: " + lap + "/" + constants.numberOfLaps, textStyle);
        text.setBounds(20f*constants.scale, hudCamera.viewportHeight - (20f*constants.scale), hudCamera.viewportWidth, 2);

        text2 = new Label("Time: " + playTime, textStyle);
        text2.setBounds(hudCamera.viewportWidth - (250f * constants.scale), hudCamera.viewportHeight - (20f * constants.scale), hudCamera.viewportWidth, 2);

        text3 = new Label("Charges left: " + (constants.CHARGE_MAXIMUM-(posCharges.size+negCharges.size)) ,textStyle);
        text3.setBounds(hudCamera.viewportWidth - (200f * constants.scale), (25f * constants.scale), hudCamera.viewportWidth, 2);

        drawButtons();
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        hudCamera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        hudCamera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        hudCamera.update();
    }

    private void setupTouchControlAreas() {
        press = new Vector3();
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void setupWorld() {
        debug = constants.DEBUG_ACTIVATE;

        world = worldUtils.createWorld();
        world.setContactListener(this);

        setupMap();
        setupRunner();

        bgm = assetManager.manager.get(constants.BGM, Music.class);
        bgm.play();
        bgm.setLooping(true);

        placeCharge = assetManager.manager.get(constants.BLOOP, Sound.class);
        finish = assetManager.manager.get(constants.CHIME, Sound.class);
        click = assetManager.manager.get(constants.BLIP, Sound.class);
        charged = assetManager.manager.get(constants.BLURP, Sound.class);
    }

    private void setupMap() {
        map = assetManager.manager.get(constants.LEVEL2_MAP);
        renderer = new Box2DDebugRenderer();
        parser.load(world, map);
        mapRenderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());

        Random generator = new Random();

        for(int x=0;x<constants.NUMBER_OF_POWERUPS_2;x++) {
            setupPowerUps(parser.getBodies().get(x+"").getPosition().x, parser.getBodies().get(x+"").getPosition().y, generator.nextInt(3));
        }
    }

    private void setupPowerUps(float x, float y, int mode) {
        powerUp = new PowerUp(worldUtils.createPowerUps(world, x, y, mode));
        addActor(powerUp);
    }

    private void setupRunner() {
        runner = new Runner(worldUtils.createRunner(world, parser.getBodies().get("spawn").getPosition().x, parser.getBodies().get("spawn").getPosition().y, RunnerMode));
        addActor(runner);
    }

    private void setupCharge(float x, float y, boolean isPositive) {
        placeCharge.play();
        Body initCharge = worldUtils.createCharge(world, x, y, isPositive);
        Body chargeField = worldUtils.chargeField(world, x, y);
        if(bodyUtils.bodyIsCharge(initCharge)) {
            charge = new Charge(initCharge);
            chargefield = new ChargeField(chargeField);
            if(isPositive) {
                chargeCharges.add(true);
                posCharges.add(initCharge);
            }
            else {
                chargeCharges.add(false);
                negCharges.add(initCharge);
            }
            chargeFields.add(chargeField);
            addActor(charge);
            addActor(chargefield);
        }
    }

    private void chargeActivate(Vector2 chargePosition, boolean attract) {
        if(attract) {
            runner.attract(chargePosition);
        }
        else {
            runner.repulse(chargePosition);
        }
    }

    private void runnerMode() {
        if(runner.getUserData().getMode() == 1) {
            positiveAttract = false;
            negativeAttract = true;
        }
        else if(runner.getUserData().getMode() == 2) {
            positiveAttract = true;
            negativeAttract = false;
        }
        else {
            positiveAttract = true;
            negativeAttract = true;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        mapRenderer.setView(camera);
        mapRenderer.render();

        posCharges.ensureCapacity(world.getBodyCount() - (posCharges.size + negCharges.size));
        negCharges.ensureCapacity(world.getBodyCount() - (posCharges.size + negCharges.size));
        chargeFields.ensureCapacity(world.getBodyCount() - (posCharges.size + negCharges.size));

        runnerMode();
        updateCharges();
        followPlayer();
        drawSprites();

        finish();


        if(gameStarting) {
            gameStarted = false;
            timer-= delta;
        }

        if(gameStarted) {
            gameStarting = false;
            if(!initDone) {
                addStuffStart();
            }
            playTime += delta;
            updateText();
            checkLaps();
        }

        if(timer<=0.0f) {
            gameStarting = false;
            gameStarted = true;
            timer = 10.999999f;
            lap = 1;
            playTime = 0.0f;
            timeRecorded = false;
        }

        countdown.setText("Starting in..." + (int) timer);

        checkChargeTime();

    }

    private void startGame() {
        initDone = false;
        finished = false;
        gameStarting = true;
        startButton.setDisabled(true);
        text.remove();
        text2.remove();
        text3.remove();
        hudStage.addActor(countdown);
    }

    private void addStuffStart() {
        initDone = true;
        countdown.remove();
        startButton.remove();
        runner.getBody().setTransform(parser.getBodies().get("spawn").getPosition().x, parser.getBodies().get("spawn").getPosition().y, runner.getBody().getAngle());
        hudStage.addActor(text);
        hudStage.addActor(text2);
        hudStage.addActor(text3);
    }

    private void updateText() {
        if(!finished) {
            text.setText("Lap: " + lap + "/" + constants.numberOfLaps);
            text2.setText("Time: " + (int) playTime + " sec");
            text3.setText("Charges left: " + (constants.CHARGE_MAXIMUM - (posCharges.size + negCharges.size)));
            tempBool = false;
        }
        else {
            text.setText("FINISHED!");
            if(!timeRecorded) {
                finishTime = playTime;
                timeRecorded = true;
            }
            text2.setBounds(hudCamera.viewportWidth - (300f * constants.scale), hudCamera.viewportHeight - (20f * constants.scale), hudCamera.viewportWidth, 2);
            text2.setText("Time Finished: " + (int) finishTime + " sec");
            text3.setText("");
            if(!tempBool) {
                hudStage.addActor(startButton);
                startButton.setDisabled(false);
                tempBool = true;
            }
        }
    }

    private void checkChargeTime() {
        numOfCharges = posCharges.size+negCharges.size;

        if(numOfCharges >= 1) {
            for(int x=0;x<numOfCharges;x++) {
                cooldownTime[x] += Gdx.graphics.getDeltaTime();
                if(cooldownTime[x] >= constants.COOLDOWN_TIME) {
                    removeCharges();
                    cooldownTime[x] = 0;
                }
            }
        }

        if(numOfCharges == constants.CHARGE_MAXIMUM) {
            chargeAllowed = false;
        }
        else if (numOfCharges < constants.CHARGE_MAXIMUM) {
            chargeAllowed = true;
        }
    }

    private void checkLaps() {
        if(lap>constants.numberOfLaps) {
            finished = true;
        }
    }

    private void removeCharges() {
        if(chargeCharges.first()) {
            removeCharge = posCharges.first();
            posCharges.removeIndex(posCharges.indexOf(removeCharge, true));
            world.destroyBody(removeCharge);
        }

        else if(!chargeCharges.first()) {
            removeCharge = negCharges.first();
            negCharges.removeIndex(negCharges.indexOf(removeCharge, true));
            world.destroyBody(removeCharge);
        }

        removeCharge = chargeFields.first();
        chargeFields.removeIndex(chargeFields.indexOf(removeCharge, true));
        world.destroyBody(removeCharge);

        chargeCharges.removeIndex(chargeCharges.indexOf(chargeCharges.first(), true));
    }

    private void updateCharges() {

        for (int x = 0; x < posCharges.size; x++) {
            chargeActivate(posCharges.get(x).getPosition(), positiveAttract);
        }

        for (int x = 0; x < negCharges.size; x++) {
            chargeActivate(negCharges.get(x).getPosition(), negativeAttract);
        }

    }

    private void updateRunner() {
        worldUtils.updateBall(runner, RunnerMode);
    }

    private void followPlayer() {
        camera.position.set(runner.getPosition().x, runner.getPosition().y, 0f);
        camera.update();
    }

    private void drawSprites() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Box2DSprite.draw(batch, world);
        batch.end();

        hudBatch.setProjectionMatrix(hudCamera.combined);
        hudBatch.begin();
        hudStage.draw();
        hudBatch.end();
    }

    private void drawButtons() {

        chargeButtonStyle = new ImageButton.ImageButtonStyle();
        chargeButtonStyle.up = new Image(assetManager.manager.get(constants.POSITIVE_BUTTON, Texture.class)).getDrawable();
        chargeButtonStyle.down = new Image(assetManager.manager.get(constants.DOWN_BUTTON, Texture.class)).getDrawable();
        chargeButtonStyle.checked = new Image(assetManager.manager.get(constants.NEGATIVE_BUTTON, Texture.class)).getDrawable();

        startButtonStyle = new ImageButton.ImageButtonStyle();
        startButtonStyle.up = new Image(assetManager.manager.get(constants.STARTUP, Texture.class)).getDrawable();
        startButtonStyle.down = new Image(assetManager.manager.get(constants.STARTDOWN, Texture.class)).getDrawable();
        startButtonStyle.disabled = new Image(assetManager.manager.get(constants.STARTDOWN, Texture.class)).getDrawable();

        chargeButton = new ImageButton(chargeButtonStyle);
        startButton = new ImageButton(startButtonStyle);

        chargeButton.setWidth(constants.BUTTON_SIZE * constants.scale);
        chargeButton.setHeight(constants.BUTTON_SIZE * constants.scale);
        chargeButton.setChecked(false);

        startButton.setWidth(constants.BUTTON_SIZE * constants.scale);
        startButton.setHeight(constants.BUTTON_SIZE * constants.scale);

        chargeButton.setPosition(20f, 20f);
        startButton.setPosition(hudCamera.viewportWidth - startButton.getWidth() - 20f, 20f);

        chargeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play();
                if (isPositive) {
                    chargeButton.setChecked(true);
                    isPositive = false;
                } else {
                    chargeButton.setChecked(false);
                    isPositive = true;
                }
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!startButton.isDisabled()) {
                    click.play();
                    startGame();
                }
            }
        });

        hudStage.addActor(startButton);
        hudStage.addActor(chargeButton);

    }

    @Override
    public void draw() {
        super.draw();
        if(debug) {
            renderer.render(world, camera.combined);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        hudBatch.dispose();
        hudStage.dispose();
        this.dispose();
        renderer.dispose();
        mapRenderer.dispose();
        map.dispose();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        translateScreen(x, y);
        if(chargeAllowed) {
            setupCharge(press.x, press.y, isPositive);
        }

        return super.touchDown(x, y, pointer, button);

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return super.touchUp(screenX, screenY, pointer, button);

    }

    private void translateScreen(int x, int y) {
        camera.unproject(press.set(x, y, 0));
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(bodyUtils.bodyIsPowerUp(a) && bodyUtils.bodyIsRunner(b)) {
            charged.play();
            compareUp = new PowerUp(a);
            comparePowerUp(compareUp);
        }
        else if(bodyUtils.bodyIsPowerUp(b) && bodyUtils.bodyIsRunner(a)) {
            charged.play();
            compareUp = new PowerUp(b);
            comparePowerUp(compareUp);
        }

        if((a == parser.getBodies().get("finish")) && bodyUtils.bodyIsRunner(b)) {
            finish.play();
            toBeTransferred.add(b);
        }
        else if((b == parser.getBodies().get("finish")) && bodyUtils.bodyIsRunner(a)) {
            finish.play();
            toBeTransferred.add(a);
        }

    }

    private void finish() {
        if(toBeTransferred.size>=1) {
            toBeTransferred.first().setTransform(parser.getBodies().get("spawn").getPosition().x, parser.getBodies().get("spawn").getPosition().y, toBeTransferred.first().getAngle());
            toBeTransferred.clear();
            System.out.println("Finish!");
            lap++;
        }
    }

    private void comparePowerUp(PowerUp compare) {
        switch(compare.getUserData().getMode()) {
            case 0:
                RunnerMode = 0;
                break;
            case 1:
                RunnerMode = 1;
                break;
            case 2:
                RunnerMode = 2;
                break;
        }
        updateRunner();
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
