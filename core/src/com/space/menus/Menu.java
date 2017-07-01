package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Menu
{
    protected Stage stage;

    public Menu()
    {
        stage = new Stage();
    }

    public void draw()
    {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
