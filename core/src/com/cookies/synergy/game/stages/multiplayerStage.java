package com.cookies.synergy.game.stages;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TabbedList;
import com.cookies.synergy.game.utils.assetManager;
import com.cookies.synergy.game.utils.constants;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

public class multiplayerStage extends Stage {

    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private TabbedList list;
    private TabbedList.TabbedListStyle listStyle;

    public multiplayerStage(Game game) {
        generator = assetManager.manager.get(constants.FONT, FreeTypeFontGenerator.class);
        parameter.size =  35* (int)constants.scale;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);

        listStyle = new TabbedList.TabbedListStyle(font, Color.WHITE, Color.WHITE, new Image(assetManager.manager.get(constants.BACKGROUND, Texture.class)).getDrawable());
        list = new TabbedList(constants.roomDataList, listStyle);

        addActor(list);

    }

    //TODO: ROOM LIST

    @Override
    public void act(float delta) {
        try {
            WarpClient.getInstance().getAllRooms();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(constants.roomDataList.length > 0)
            list.setItems(constants.roomDataList);
    }

}
