package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public abstract class ContextMenu extends Menu
{
    protected Stage stage;
    protected List itemList;
    protected int currentIndex = 0;

    protected Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
    protected TextureRegion background = new TextureRegion(new Texture("background.png"), 0, 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
    protected Image backgroundImage = new Image(background);
    protected Table tableNames;

    public ContextMenu(Menu parentMenu)
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        itemList = new List(skin);

        tableNames = new Table();
        tableNames.setDebug(true);
        tableNames.setVisible(true);
        tableNames.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
        tableNames.setScale(0.1f, 0.1f);
        tableNames.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);
        stage.addActor(backgroundImage);
        tableNames.align(Align.topLeft);

        backgroundImage.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);

        this.parentMenu = parentMenu;
    }

    public void draw()
    {
        if (childMenu == null)
        {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
        else
            childMenu.draw();
    }

    public void handleInput() {
        if (childMenu == null)
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                currentIndex--;
                if (currentIndex < 0)
                    currentIndex = itemList.getItems().size - 1;

                itemList.setSelectedIndex(currentIndex);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                currentIndex++;

                if (currentIndex > itemList.getItems().size - 1)
                    currentIndex = 0;

                itemList.setSelectedIndex(currentIndex);

            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)) {
                if (parentMenu == null)
                    return;
                parentMenu.setChildMenu(null);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                act(itemList.getSelected().toString());
            }

            //INFO
            if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
                showInfo(itemList.getSelected().toString());
            }
        }
        else
            childMenu.handleInput();
    }

    protected abstract void act(String command);

    protected abstract void showInfo(String command);
}
