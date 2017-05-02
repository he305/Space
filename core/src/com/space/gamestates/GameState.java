package com.space.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class GameState
{
    protected List<Entity> entities = new ArrayList<>();
    protected String gameStateName;

    public GameState()
    {
        this.gameStateName = this.getClass().getName();
        init();
    }

    protected abstract void init();

    public void draw(SpriteBatch batch)
    {
        for (Entity en : entities)
        {
            en.draw(batch);
        }
    }

    public void update()
    {
        for (Entity en : entities)
        {
            en.update();
        }
    }

}
