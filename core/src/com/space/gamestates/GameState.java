package com.space.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState
{
    protected String gameStateName;

    public GameState()
    {
        this.gameStateName = this.getClass().getName();
    }

    protected abstract void init();

    public abstract void draw(SpriteBatch batch);

    public abstract void update();

}
