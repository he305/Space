package com.space.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.space.entities.Planet;
import com.space.entities.SpaceObject;

public class SystemState extends GameState
{

    protected void init()
    {
        entities.add(new SpaceObject(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), new Vector2(30, 30)));
        entities.add(new Planet(new Vector2(3*Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2), new Vector2(10, 10), entities.get(0)));
        entities.add(new Planet(new Vector2(Gdx.graphics.getWidth()/2+50, Gdx.graphics.getHeight()/2), new Vector2(5, 5), entities.get(0)));
    }
}
