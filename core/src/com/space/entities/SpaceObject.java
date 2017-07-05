package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;

public abstract class SpaceObject extends Entity
{
    protected Vector2 startPosition;

    protected float radius;

    protected Color color;
    protected float mass;

    protected ArrayList<Building> buildings = new ArrayList<>();

    protected double minerals;

    protected String name;

    protected SpaceObject parentObject;
    protected float distanceFromParent;
    protected float alpha;
    protected ArrayList<SpaceObject> children = new ArrayList<>();
    protected double v;
    protected double rotationTime = 0;

    protected double realRadius;
    protected double realDistance;

    protected double averageTemperature;

    //ускорение времени
    protected int acceleration = 1;

    protected boolean isHabit = false;

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

            //TODO: change this
            for (Building building : buildings)
                for (int i = 0; i < acceleration; i++)
                    building.update();

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
        distanceFromParent = Math.abs(position.x - radius/Constants.radiusScale - parentObject.position.x + parentObject.radius);

        out.println(Constants.earthRadius);
        realDistance = distanceFromParent * 1000;
        out.println(this.name + " " + realDistance + " " + distanceFromParent);
        if (object instanceof Sun)
        {
            //TODO: think should i use it or not
            double albedo = ThreadLocalRandom.current().nextDouble(0, 1);

            averageTemperature = Math.pow((((Sun) object).getLuminosity() * 0.5)/(16 * Math.PI * Math.pow(realDistance, 2) * Constants.STEFAN_BOLTZMANN_CONST), 1.0/4) - 273;
        }
        else
            //TODO: should stay like this?
            averageTemperature = object.getAverageTemperature() + ThreadLocalRandom.current().nextInt(-20, 21);

        v = 360 / rotationTime / 60;

        /*if (mass / object.getMass() > 0.01 && mass / object.getMass() < 100) {
            v = (mass + object.getMass()) / distanceFromParent  * 60;
        } else {
            v = Constants.G * object.getMass() / distanceFromParent * 60;
        }*/

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

    public void addBuilding(BuildingType buildingType)
    {
        buildings.add(new Building(buildingType, this));
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

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public double getMinerals()
    {
        return minerals;
    }

    public void decreaseMinerals(double delta)
    {
        minerals -= delta;
    }

    public boolean isHabit() {
        return isHabit;
    }

    public ArrayList<Building> getBuildings()
    {
        return buildings;
    }
}
