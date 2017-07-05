package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.space.entities.Planet;
import com.space.entities.SpaceObject;

import java.util.ArrayList;

public class InfoMenu extends Menu
{
    TextureRegion background;
    Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));

    Label title;
    List text;

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
        text = new List(skin);

        Gdx.input.setInputProcessor(stage);
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

        ArrayList<String> data = new ArrayList<>();
        data.add("Radius: " + object.getRealRadius());
        data.add("Mass: " + object.getMass());
        if (object.getParent() != null)
            data.add("Parent:" + object.getParent().getName());
        data.add("Average temperature: " + object.getAverageTemperature());
        if (object instanceof Planet && ((Planet) object).isHabit())
            data.add("People: " + ((Planet) object).getPeopleCount());
            //data.add("Has atmosphere");



        text.setPosition(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10);
        text.setBounds(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10, 8 * Gdx.graphics.getWidth()/10, 8*Gdx.graphics.getHeight()/10);
        text.setCullingArea(new Rectangle(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10, 8 * Gdx.graphics.getWidth()/10, 8*Gdx.graphics.getHeight()/10));

        text.setItems(data.toArray());

        stage.addActor(title);
        stage.addActor(text);
        title.setAlignment(Align.top);
    }

    public void update()
    {
        super.update();
        title.setText(String.valueOf((int)object.getMinerals()));

        //text.
        //text.getItems().get(text.getItems().size-1) = ((Planet) object).getPeopleCount();
    }

    public void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            parentMenu.setChildMenu(null);
        }
    }

}
