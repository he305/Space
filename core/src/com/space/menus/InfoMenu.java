package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.space.entities.SpaceObject;

public class InfoMenu extends Menu
{
    TextureRegion background;
    Skin skin;

    Label title;
    Label text;

    SpaceObject object;

    public InfoMenu(Menu parentMenu)
    {
        super();

        this.parentMenu = parentMenu;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new TextureRegion(new Texture("background.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Image actor = new Image(background);
        actor.setPosition(0, 0);
        stage.addActor(actor);

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
    }

    public void draw()
    {
        if (childMenu == null)
        {
            Gdx.input.setInputProcessor(stage);
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
        else
            childMenu.draw();
    }

    public InfoMenu(SpaceObject object, Menu parentMenu)
    {
        this(parentMenu);

        this.object = object;

        System.out.println(object.getName());

        title = new Label(object.getName(), skin);
        //title.setPosition(Gdx.graphics.getWidth()/2, 10);
        //title.setAlignment(Align.top);


        String objectInfo = new String();
        objectInfo += "Radius: " + object.getRealRadius() + '\n';
        objectInfo += "Mass: " + object.getMass() + '\n';
        if (object.getParent() != null)
            objectInfo += "Parent:" + object.getParent().getName() + '\n';

        text = new Label(objectInfo, skin);

        stage.addActor(title);
        stage.addActor(text);
        title.setAlignment(Align.top);
        text.setPosition(10, 20);


    }

    public void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            parentMenu.setChildMenu(null);
        }
    }

}
