package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SpaceObject extends Entity
{

    ShapeRenderer renderer;
    Vector2 startPosition;
    int r;

    float alpha = 0;

    public SpaceObject(Vector2 position, Vector2 size) {
        super(position);

        startPosition = position;
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.setColor(Color.GOLD);
        r = (int)size.x;
        /*
        pixmap = new Pixmap((int)size.x, (int)size.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GOLD);
        pixmap.fillCircle((int)position.x, (int)position.y, 10);
        texture = new Texture(pixmap);
        sprite = new Sprite(texture);
        sprite.setPosition(position.x, position.y);*/
    }

    @Override
    public void update() {
        if (isUpdating)
        {
            if (alpha >= 360)
                alpha = 0;

            position.x = startPosition.x + (float)(10 * Math.cos(Math.toRadians(alpha)));
            position.y = startPosition.y + (float)(10 * Math.sin(Math.toRadians(alpha)));
            System.out.println(position.x + " " + position.y);
            alpha += 0.000001;
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch)
    {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, r);
        renderer.end();
    }
}
