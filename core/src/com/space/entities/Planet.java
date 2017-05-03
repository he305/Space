package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Planet extends SpaceObject{


    public Planet(Vector2 position, float radius, float mass, String name) {
        super(position, radius, mass, name);
        color = Color.BROWN;
        this.radius *= 10;
        this.mass *= 10;
    }

    @Override
    public void draw(ShapeRenderer renderer)
    {
        renderer.end();

        renderer.begin();
        renderer.setColor(Color.CYAN);
        renderer.circle(parentObject.position.x, parentObject.position.y, distanceFromParent);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        super.draw(renderer);

    }

}
