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
import com.space.buildings.Building;
import com.space.buildings.BuildingController;
import com.space.entities.Planet;
import com.space.entities.SpaceObject;
import com.space.tools.Constants;

import java.util.ArrayList;

public class InfoMenu extends Menu
{
    final TextureRegion background;
    final Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));

    final Label title;

    final List list;
    final ArrayList<Label> texts = new ArrayList<>();

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

        title = new Label("", skin);
        title.setAlignment(Align.top);


        list = new List(skin);

        list.setPosition(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10);
        list.setBounds(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10, 8 * Gdx.graphics.getWidth()/10, 8*Gdx.graphics.getHeight()/10);
        list.setCullingArea(new Rectangle(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10, 8 * Gdx.graphics.getWidth()/10, 8*Gdx.graphics.getHeight()/10));

        stage.addActor(title);
        stage.addActor(list);

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

        title.setText(object.getName());

        ArrayList<String> objectData = new ArrayList<>();

        texts.add(new Label("Radius: " + object.getRealRadius(), skin));
        texts.add(new Label("Mass: " + object.getMass(), skin));
        if (object instanceof Planet)
        {
            texts.add(new Label("People: " + object.getPeopleCount(), skin));
            texts.add(new Label("Distance from parent: " + String.valueOf(object.getRealDistance()/Constants.earthToSunDistance) + " a.e", skin));
            texts.add(new Label("Pressure: "  + String.valueOf(((Planet) object).getPressure()), skin));
        }
        texts.add(new Label("Minerals: " + object.getMinerals(), skin));
        texts.add(new Label("Temperature: " + object.getTemperature(), skin));

        objectData.add("Radius: " + object.getRealRadius());
        objectData.add("Mass: " + object.getMass());
        if (object.getParent() != null)
            objectData.add("Parent: " + object.getParent().getName());
        if (object instanceof Planet)
            objectData.add("Distance from parent: " + String.valueOf(object.getRealDistance()/Constants.earthToSunDistance) + " a.e");
        objectData.add("Average temperature: " + object.getTemperature());
        if (object instanceof Planet && object.isHabit())
            objectData.add("People: " + object.getPeopleCount());
            //data.add("Has atmosphere");

        list.setItems(texts.toArray());
    }

    //For buildings on space objects
    public InfoMenu(BuildingController buildingController, Menu parentMenu)
    {
        this(parentMenu);
        SpaceObject object = buildingController.getObject();
        title.setText(object.getName());

        ArrayList<String> data = new ArrayList<>();
        for (Building building : buildingController.getBuildings())
            data.add(building.toString());

        list.setItems(data.toArray());
    }

    public void update()
    {
        super.update();

        for (Label l : texts)
        {
            if (l.getText().substring(0, 6).equals("People"))
                l.setText("People: " + object.getPeopleCount());
            if (l.getText().substring(0, 6).equals("Minera"))
                l.setText("Minerals: " + object.getMinerals());
        }
    }

    public void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            parentMenu.setChildMenu(null);
        }
    }

}
