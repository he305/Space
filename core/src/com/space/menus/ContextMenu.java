package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public abstract class ContextMenu
{
    protected Stage stage;
    protected List itemList;
    protected int currentIndex = 0;

    public ContextMenu()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    public void draw()
    {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            currentIndex--;
            if (currentIndex < 0)
                currentIndex = itemList.getItems().size-1;

            itemList.setSelectedIndex(currentIndex);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
        {
            currentIndex++;

            if (currentIndex > itemList.getItems().size-1)
                currentIndex = 0;

            itemList.setSelectedIndex(currentIndex);

        }
    }

    protected abstract void act(String command);
}
