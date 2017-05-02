package com.space.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position;
    protected Texture texture;
    protected Sprite sprite;
    protected boolean isUpdating;

    public Entity(Vector2 position)
    {
        this.position = position;
    }

    public abstract void draw(SpriteBatch batch);

    public abstract void update();


    public void setUpdating(boolean isUpdating)
    {
        this.isUpdating = isUpdating;
    }

    public Vector2 getPosition()
    {
        return position;
    }
}
