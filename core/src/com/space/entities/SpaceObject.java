package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

import java.util.ArrayList;
import java.util.Random;

public abstract class SpaceObject extends Entity
{
    protected Vector2 startPosition;

    protected float radius;
    protected Color color;
    protected float mass;

    protected String name;

    protected SpaceObject parentObject;
    protected float distanceFromParent;
    protected float alpha;
    protected ArrayList<SpaceObject> children = new ArrayList<>();
    protected double v;
    protected double rotationTime = 0;
    protected double realRadius;
    protected double realDistance;

    //ускорение времени
    protected double a = 1;


    public SpaceObject(Vector2 position, float radius, String name) {
        super(position);

        realRadius = radius * Constants.earthRadius;
        startPosition = this.position;
        this.radius = radius * Constants.radiusScale;
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
            alpha += v * a;
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

    public void addChildren(SpaceObject object, double rotationTime) {
        children.add(object);
        object.setUpdating(true);
        object.rotationTime = rotationTime;
        object.setParent(this);
    }

    public void addChildrenRelatively(SpaceObject object, double rotationTime) {
        children.add(object);
        object.setUpdating(true);
        object.setPosition(new Vector2(this.position.x + this.getRadius() * 2 + object.position.x + object.radius, this.position.y + object.position.y));
        object.setStartPosition(object.getPosition());
        object.rotationTime = rotationTime;
        object.setParent(this);
    }

    public SpaceObject getParent() {
        return parentObject;
    }

    public void setParent(SpaceObject object) {
        parentObject = object;
        distanceFromParent = Math.abs(parentObject.position.x + parentObject.radius - position.x);

        v = 360 / rotationTime / 60;

        /*if (mass / object.getMass() > 0.01 && mass / object.getMass() < 100) {
            v = (mass + object.getMass()) / distanceFromParent  * 60;
        } else {
            v = Constants.G * object.getMass() / distanceFromParent * 60;
        }*/

        realDistance = distanceFromParent * Constants.earthRadius;
        System.out.println(this.name + " " + rotationTime + " " + realRadius + " " + distanceFromParent);

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

    public SpaceObject getLastChild() {
        return children.get(children.size() - 1);
    }

    public ArrayList<SpaceObject> getChildren() {
        return children;
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

    public float getRadius() {
        return radius;
    }

    public void setAcceleration(int a) {
        this.a = a;

        if (children.size() != 0) {
            for (SpaceObject child : children) {
                child.setAcceleration(a);
            }
        }
    }

    @Override
    public void setUpdating(boolean isUpdating) {
        super.setUpdating(isUpdating);
        if (isUpdating) {
            Random random = new Random();
            alpha = random.nextInt(361);
        }
    }

    public float getDistanceFromParent()
    {
        return distanceFromParent;
    }
}
