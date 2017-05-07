package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.space.entities.SpaceObject;
import com.space.entities.Sun;

import java.util.ArrayList;

public class SystemMenu {
    private Stage stage;
    private ArrayList<String> objectNames = new ArrayList<>();

    public SystemMenu(Sun sun)
    {
        Table tableNames;
        TextureRegion background;
        Skin skin;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        tableNames = new Table();
        tableNames.setDebug(true);
        tableNames.setVisible(true);
        tableNames.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
        tableNames.setScale(0.1f, 0.1f);
        tableNames.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);

        background = new TextureRegion(new Texture("background.png"), 0, 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
        Image actor = new Image(background);
        actor.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);
        stage.addActor(actor);

        skin = new Skin(Gdx.files.internal("vhs/skin/vhs-ui.json"));

        objectNames.add(sun.getName());
        getObjectNames(sun, objectNames, 0);

        for (String str : objectNames) {
            Label label = new Label(str, skin);
            label.setFontScale(0.5f);
            label.setAlignment(Align.left, Align.left);
            tableNames.add(label).pad(10).align(Align.left);
            tableNames.row();
        }

        stage.addActor(tableNames);
    }

    private void getObjectNames(SpaceObject parent, ArrayList<String> names, int depth)
    {
        for (SpaceObject object : parent.getChildren())
        {
            names.add(new String(new char[depth]).replace('\0', '-') + object.getName());
            if (object.getChildren().size() != 0)
            {
                getObjectNames(object, names, depth+1);
            }
        }
    }

    public void draw()
    {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
