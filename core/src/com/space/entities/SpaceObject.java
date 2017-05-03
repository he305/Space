package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

import java.util.ArrayList;

public abstract class SpaceObject extends Entity
{
    protected Vector2 startPosition;
    protected float radius;
    protected Color color;
    protected float mass;

    protected String name;

    protected SpaceObject parentObject;
    protected float distanceFromParent;
    protected float alpha = 0;
    protected ArrayList<SpaceObject> children = new ArrayList<>();
    double v;


    public SpaceObject(Vector2 position, float radius, float mass, String name) {
        super(position);

        startPosition = position;
        this.radius = radius;
        this.mass = mass;
        this.name = name;
    }

    @Override
    public void update() {
        //Spinning
        if (isUpdating)
        {
            if (alpha >= 360)
                alpha = 0;

            position.x = parentObject.getPosition().x + (float) (distanceFromParent * Math.cos(Math.toRadians(alpha)));
            position.y = parentObject.getPosition().y + (float) (distanceFromParent * Math.sin(Math.toRadians(alpha)));
            alpha += v;
        }

        if (children.size() != 0) {
            for (SpaceObject object : children) {
                object.update();
            }
        }
    }

    public void draw(ShapeRenderer renderer)
    {
        renderer.setColor(color);
        renderer.circle(position.x, position.y, radius);

        if (children.size() != 0) {
            for (SpaceObject object : children) {
                object.draw(renderer);
            }
        }
    }

    public void addChildren(SpaceObject object) {
        children.add(object);
        object.setUpdating(true);
        object.setParent(this);
    }

    public void addChildrenRelatively(SpaceObject object) {
        children.add(object);
        object.setUpdating(true);
        object.setPosition(new Vector2(this.position.x + this.radius + object.position.x, this.position.y + object.position.y));
        object.setStartPosition(new Vector2(this.startPosition.x + this.radius + object.startPosition.x, this.startPosition.y + object.startPosition.y));
        object.setParent(this);
    }

    public SpaceObject getParent() {
        return parentObject;
    }

    public void setParent(SpaceObject object) {
        parentObject = object;
        distanceFromParent = Math.abs(parentObject.position.x + parentObject.radius - position.x);

        if (mass / object.getMass() > 0.01 && mass / object.getMass() < 100) {
            v = Math.sqrt(Constants.G * (mass + object.getMass()) / distanceFromParent) * 60 * 36 * 24;
        } else {
            v = Math.sqrt(Constants.G * object.getMass() / distanceFromParent) * 60 * 24;
        }
        System.out.println(getName() + " " + v);
        //v = 1000/(distanceFromParent * distanceFromParent);

    }

    public String getName() {
        return name;
    }

    public SpaceObject getChildrenByName(String name) {
        if (children.size() != 0) {
            for (SpaceObject child : children) {
                if (child.getName() == name) {
                    return child;
                }
            }
        }
        return null;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setStartPosition(Vector2 startPosition) {
        this.startPosition = position;
    }

    public float getMass() {
        return mass;
    }
}
