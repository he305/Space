package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.space.gamestates.SystemState;

import java.util.ArrayList;

public class SystemContextMenu extends ContextMenu {
    private ArrayList<String> objectNames = new ArrayList<>();

    private Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
    private TextureRegion background = new TextureRegion(new Texture("background.png"), 0, 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
    private Image backgroundImage = new Image(background);
    private Table tableNames;

    private final ArrayList<String> topMenu;

    private SystemState state;

    public SystemContextMenu(SystemState state)
    {
        super();
        this.state = state;

        topMenu = new ArrayList<>();

        topMenu.add("[i] SystemInfo");

        setToDefault();
    }

    private void setToDefault()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        tableNames = new Table();
        tableNames.setDebug(true);
        tableNames.setVisible(true);
        tableNames.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
        tableNames.setScale(0.1f, 0.1f);
        tableNames.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);
        stage.addActor(backgroundImage);
        tableNames.align(Align.topLeft);

        backgroundImage.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);

        itemList = new List(skin);
        itemList.setItems(topMenu.toArray());

        tableNames.add(itemList);

        stage.addActor(tableNames);
    }

    private void showSystemInfo()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        tableNames = new Table();
        tableNames.setDebug(true);
        tableNames.setVisible(true);
        tableNames.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
        tableNames.setScale(0.1f, 0.1f);
        tableNames.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);
        stage.addActor(backgroundImage);

        tableNames.align(Align.topLeft);
        backgroundImage.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);

        objectNames.clear();
        objectNames.add(state.getSun().getName());
        state.getObjectNames(state.getSun(), objectNames, 0);

        itemList = new List(skin);

        itemList.setItems(objectNames.toArray());

        tableNames.add(itemList);
        stage.addActor(tableNames);

        currentIndex = 0;
    }

    public void handleInput()
    {
        super.handleInput();
        if (Gdx.input.isKeyJustPressed(Input.Keys.I))
        {
            showSystemInfo();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET))
        {
            setToDefault();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            act(itemList.getSelected().toString());
        }
    }

    protected void act(String command)
    {
        System.out.println(command);

        switch (command)
        {
            case "[i] SystemInfo":
                showSystemInfo();
                break;
            default:
                state.getSun().center(command);
                break;
        }
    }
}
