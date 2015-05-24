package com.cookies.synergy.game.stages;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TabbedList;
import com.badlogic.gdx.utils.TimeUtils;
import com.cookies.synergy.game.utils.assetManager;
import com.cookies.synergy.game.utils.constants;

import appwarp.WarpController;

public class multiplayerStage extends Stage {

    //TODO: SET GAME STATE CONSTANTS

    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private TabbedList list;
    private TabbedList.TabbedListStyle listStyle;

    private long startTime = 0;
    private long delay = 10000000000l;

    private boolean tempbool = false;

    public multiplayerStage(Game game) {
        Gdx.input.setInputProcessor(this);
        WarpController.getInstance().getAllRooms();
        generator = assetManager.manager.get(constants.FONT, FreeTypeFontGenerator.class);
        parameter.size =  20* (int)constants.scale;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        System.out.println(WarpController.rooms);

        listStyle = new TabbedList.TabbedListStyle(font, Color.WHITE, Color.WHITE, new Image(assetManager.manager.get(constants.BACKGROUND, Texture.class)).getDrawable());

        startTime = TimeUtils.nanoTime();
    }

    private void updateList() {
        if(TimeUtils.timeSinceNanos(startTime) > delay) {
            WarpController.getInstance().getAllRooms();
            log(WarpController.rooms.size() + " " + WarpController.roomName.size() + " " + WarpController.roomCreator.size() + " " + WarpController.maxPlayer.size());
            System.out.println("Updated");
            list.setSelectedIndex(1);
            startTime = TimeUtils.nanoTime();
        }
    }

    //TODO: ROOM LIST

    @Override
    public void act(float delta) {
        updateList();
        if(WarpController.rooms.size() > 0 && !tempbool) {
            list = new TabbedList(WarpController.RoomList.toArray(), listStyle);
            list.setColumnGap(20f);
            list.setHeader("Room ID\tName\tCreator\tPlayers");
            addActor(list);

            tempbool = true;
        }
        list.setItems(WarpController.RoomList.toArray());
        list.setPosition(Gdx.graphics.getWidth() / 2 - list.getPrefWidth() / 2, Gdx.graphics.getHeight() - 50f);
    }

    @Override
    public void dispose() {
        super.dispose();
        WarpController.getInstance().stopApp();
        constants.prefs.remove("sessionid");
        System.out.println("disposing");
    }

    private void log(String message){
        if(constants.showLog) {
            System.out.println(message);
        }
    }

}
