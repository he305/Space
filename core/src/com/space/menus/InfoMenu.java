package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class InfoMenu extends Menu
{
    public InfoMenu(String title, String text)
    {
        super();

        TextureRegion background;
        Skin skin;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new TextureRegion(new Texture("background.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Image actor = new Image(background);
        actor.setPosition(0, 0);
        stage.addActor(actor);

        skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));


    }


}
