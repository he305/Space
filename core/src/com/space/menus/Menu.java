package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Menu
{
    protected Stage stage;

    protected Menu parentMenu = null;
    protected Menu childMenu = null;

    public Menu()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    public abstract void draw();

    public void update()
    {
        if (childMenu == null) {
            handleInput();
        }
        else
            childMenu.update();
    }

    public abstract void handleInput();

    public void setChildMenu(Menu menu)
    {
        this.childMenu = menu;
    }

    public Menu getChildMenu()
    {
        return childMenu;
    }

    public void setParentMenu(Menu menu)
    {
        this.parentMenu = menu;
    }

    public Menu getParentMenu()
    {
        return parentMenu;
    }
}
