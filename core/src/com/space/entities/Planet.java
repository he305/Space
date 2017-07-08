package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

import java.util.concurrent.ThreadLocalRandom;

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

        if (name.equals("Earth"))
        {
            isHabit = true;
            peopleCount = 1000;
            minerals = 100000;
        }

        double rand = ThreadLocalRandom.current().nextDouble(1);
        if (rand > 0.5)
        {
            pressure = ThreadLocalRandom.current().nextDouble(0, 1);
        }
        else if (rand > 0.2)
        {
            pressure = ThreadLocalRandom.current().nextDouble(1, 10);
        }
        else
        {
            pressure = ThreadLocalRandom.current().nextDouble(10, 100);
        }
    }

    public void update()
    {
        super.update();

        if (alpha >= 360)
                alpha = 0;

            position.x = parentObject.getPosition().x + (float) (distanceFromParent * Math.cos(Math.toRadians(alpha)));
            position.y = parentObject.getPosition().y + (float) (distanceFromParent * Math.sin(Math.toRadians(alpha)));
            alpha += v * acceleration;

            if (isHabit())
            {
                int r = ThreadLocalRandom.current().nextInt(0, 10);
                int d = ThreadLocalRandom.current().nextInt(0, 10);

                peopleCount += r - d;
            }

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
