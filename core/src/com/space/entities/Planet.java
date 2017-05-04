package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

public class Planet extends SpaceObject{


    public Planet(Vector2 position, float radius, String name) {
        super(position, radius, name);

        if (radius <= 5)
            color = Color.BROWN;
        else
            color = Color.GREEN;
        this.mass = radius;
        this.radius *= Constants.planetRadiusScale;
        this.mass *= 20;
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
