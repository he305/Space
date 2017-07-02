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
    protected int acceleration = 1;


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
            alpha += v * acceleration;

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

        Random random = new Random();
        object.setAlpha(random.nextInt(361));

        object.rotationTime = rotationTime;
        object.setParent(this);
    }

    public void addChildrenRelatively(SpaceObject object, double rotationTime) {
        children.add(object);
        object.setUpdating(true);

        Random random = new Random();
        object.setAlpha(random.nextInt(361));

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
    }

    public String getName() {
        return name;
    }

    public SpaceObject getChildrenByName(String name) {

        if (name == this.name) //May be, I guess
            return this;

        if (children.size() != 0) {
            for (SpaceObject child : children) {
                if (child.getName().equals(name)) {
                    return child;
                }
                if (child.getChildren().size() != 0)
                {
                    if (child.getChildrenByName(name) != null)
                        return child.getChildrenByName(name);
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
        this.acceleration = a;

        if (children.size() != 0) {
            for (SpaceObject child : children) {
                child.setAcceleration(a);
            }
        }
    }

    public int getAcceleration()
    {
        return acceleration;
    }

    public void increaseAcceleration(int inc)
    {
        if (acceleration == 0)
            acceleration = 1;
        else if (acceleration == 1)
            acceleration = 5;
        else
            acceleration += inc;

        setAcceleration(acceleration);
    }

    public void decreaseAcceleration(int dec)
    {
        if (acceleration == 1) {
            acceleration = 0;
        }
        else if (acceleration == 0)
            return;
        else if (acceleration == 5)
            acceleration = 1;
        else
            acceleration -= dec;

        setAcceleration(acceleration);
    }

    @Override
    public void setUpdating(boolean isUpdating) {
        super.setUpdating(isUpdating);
    }

    public float getDistanceFromParent()
    {
        return distanceFromParent;
    }

    public void setAlpha(float alpha)
    {
        this.alpha = alpha;
    }


    public double getRealRadius() {
        return realRadius;
    }

    public double getRealDistance() {
        return realDistance;
    }

    public Sun getSun()
    {
        if (this instanceof Sun)
            return (Sun) this;

        else if (parentObject instanceof Sun)
            return (Sun) parentObject;

        return parentObject.getSun();
    }

    public Color getColor() {
        return color;
    }
}
