package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Planet extends SpaceObject{
    protected Entity sun;
    protected float distanceFromSun;

    double v;

    public Planet(Vector2 position, Vector2 size, Entity sun) {
        super(position, size);

        isUpdating = true;
        this.sun = sun;
        distanceFromSun = Math.abs(sun.position.x - position.x);
        v = 1000/(distanceFromSun*distanceFromSun);
    }

    @Override
    public void update() {
        if (isUpdating)
        {
            if (alpha >= 360)
                alpha = 0;

            position.x = sun.getPosition().x + (float)(distanceFromSun * Math.cos(Math.toRadians(alpha)));
            position.y = sun.getPosition().y + (float)(distanceFromSun * Math.sin(Math.toRadians(alpha)));
            System.out.println(position.x + " " + position.y);
            alpha += v;
        }
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        renderer.setColor(Color.CYAN);
        renderer.begin();
        renderer.circle(sun.position.x, sun.position.y, distanceFromSun);
        renderer.end();
        renderer.setColor(Color.GOLD);

        super.draw(batch);

    }
}
