package com.space.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.space.entities.SpaceObject;
import com.space.entities.Sun;
import com.space.managers.GameStateManager;
import com.space.menus.SystemContextMenu;
import com.space.menus.SystemContextMenuName;

import java.util.ArrayList;

public class SystemState extends GameState
{
    private SystemContextMenu menu;

    private Sun sun;

    private float translateSpeed = 10;
    private float zoomSpeed = 1f;

    private String name;

    public SystemState(String name, Sun sun) {
        super();
        this.name = name;
        this.sun = sun;
        init();
    }

    protected void init()
    {
        menu = new SystemContextMenu(this, SystemContextMenuName.TopMenu, null);
        sun.setCamera(camera);
    }


    public ArrayList<String> getObjectNames()
    {
        ArrayList<String> names = new ArrayList<>();
        names.add(sun.getName());
        for (SpaceObject object : sun.getChildren())
        {
            String objectName = " ";
            objectName += object.getName();
            names.add(objectName);

            if (object.getChildren().size() != 0)
                for (SpaceObject childOfObject : object.getChildren())
                {
                    String childName = "  ";
                    childName += childOfObject.getName();
                    names.add(childName);
                }
        }

        return names;
        /*for (SpaceObject object : parent.getChildren())
        {
            String objectName = new String();

            for (int i = 0; i < depth; i++)
                objectName += new String(" ");
            objectName += object.getName();

            names.add(objectName);
            if (object.getChildren().size() != 0)
            {
                getObjectNames(object, names, depth+1);
            }
        }*/


    }

    public ArrayList<String> getHabitalObjects()
    {
        ArrayList<String> names = new ArrayList<>();
        if (sun.isHabit())
            names.add(sun.getName());

        for (SpaceObject object : sun.getChildren())
        {
            if (object.isHabit())
                names.add(object.getName());

            if (object.getChildren().size() != 0)
                for (SpaceObject childOfObject : object.getChildren())
                    if (childOfObject.isHabit())
                        names.add(childOfObject.getName());
        }
        return names;
    }

    @Override
    public void draw(SpriteBatch batch) {
        camera.update();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(camera.combined);
        sun.draw(renderer);

        renderer.end();

        if (menuEnabled) {
            menu.draw();
        }

    }

    @Override
    public void update() {
        handleInput();

        if (menuEnabled)
        {
            menu.update();
        }

        sun.update();

        camSpeedUpdate();

    }

    private void camSpeedUpdate() {
        if (camera.zoom <= 5) {
            camera.zoom = 5;
        }

        zoomSpeed = camera.zoom / 15;
        translateSpeed = camera.zoom * 3f;
    }


    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
        {
            sun.setPause();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD))
            sun.increaseAcceleration(5);
        if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA))
            sun.decreaseAcceleration(5);
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            camera.position.set(sun.getPosition(), 0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            GameStateManager.getIntoGalaxy();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            menuEnabled = !menuEnabled;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            camera.zoom -= zoomSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            camera.zoom += zoomSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-translateSpeed, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(translateSpeed, 0, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, translateSpeed, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -translateSpeed, 0);
        }
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public String getName() {
        return name;
    }
}
