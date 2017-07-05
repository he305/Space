package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

import java.util.concurrent.ThreadLocalRandom;

public class Planet extends SpaceObject{

    boolean hasAtmospere = false;
    int peopleCount = 0;

    public Planet(Vector2 position, float radius, String name) {
        super(position, radius, name);

        if (radius <= 5)
            color = Color.BROWN;
        else
            color = Color.GREEN;
        this.mass = radius;
        this.radius *= Constants.planetRadiusScale;
        this.mass *= 20;

        if (ThreadLocalRandom.current().nextFloat() > 0.8 && radius <= 5)
            hasAtmospere = true;

        if (hasAtmospere)
            averageTemperature += ThreadLocalRandom.current().nextInt(30, 80);

        if (name.equals("Earth"))
        {
            isHabit = true;
            peopleCount = 1000;
            minerals = 1000;
        }

    }

    public void update()
    {
        super.update();

        if (isHabit())
        {
            int r = ThreadLocalRandom.current().nextInt(0, 10);
            int d = ThreadLocalRandom.current().nextInt(0, 10);

            peopleCount += r-d;
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

    public int getPeopleCount() {
        return peopleCount;
    }

}
