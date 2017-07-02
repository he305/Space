package com.space.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.space.gamestates.SystemState;

public class System extends Entity
{
    Color color;
    float radius;

    float distance_from_center;

    SystemState systemState;

    private Camera camera;
    private String name;

    public System(Vector2 position, Color color, float radius, float distance_from_center, SystemState state) {
        super(position);
        this.color = color;
        this.position = position;
        this.distance_from_center = distance_from_center;
        this.radius = radius;
        this.systemState = state;
        this.name = state.getSun().getName();
    }

    @Override
    public void update() {

    }

    public void draw (ShapeRenderer renderer)
    {
        renderer.setColor(color);
        renderer.circle(position.x, position.y, radius);
    }

    public float getDistance_from_center() {
        return distance_from_center;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public float getRadius() {
        return radius;
    }

    public String getName()
    {
        return name;
    }

    public SystemState getSystemState() {
        return systemState;
    }
}
