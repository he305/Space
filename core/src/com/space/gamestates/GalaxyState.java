package com.space.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.space.entities.System;
import com.space.managers.GameStateManager;
import com.space.menus.GalaxyContextMenu;
import com.space.menus.GalaxyContextMenuName;

import java.util.ArrayList;

public class GalaxyState extends GameState
{
    int systemCount;
    ArrayList<System> systems = new ArrayList<>();

    GalaxyContextMenu menu;

    private float translateSpeed = 10;
    private float zoomSpeed = 1f;

    String name;

    public GalaxyState(String name, int systemCount)
    {
        super();
        this.name = name;
        this.systemCount = systemCount;
        menu = new GalaxyContextMenu(this, GalaxyContextMenuName.TopMenu, null);
    }

    public void addSystems(ArrayList<System> systems)
    {
        this.systems = systems;
        init();
    }

    @Override
    protected void init()
    {
        systems.get(0).setCamera(camera);
    }

    public void draw(SpriteBatch batch)
    {
        camera.update();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(camera.combined);

        for (int i = 0; i < systems.size(); i++)
        {
            systems.get(i).draw(renderer);
        }

        if (menuEnabled) {
            menu.draw();
        }

        renderer.end();
    }
    private void handleInput() {

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            menuEnabled = !menuEnabled;
        }
    }

    public void getIntoSystem(String name)
    {
        for (System system : systems)
        {
            if (system.getName().equals(name))
                GameStateManager.getIntoSystem(system.getSystemState());
        }
    }

    @Override
    public void update() {
        handleInput();

        if (menuEnabled)
            menu.update();

        camSpeedUpdate();

    }

    private void camSpeedUpdate() {
        if (camera.zoom <= 5) {
            camera.zoom = 5;
        }

        zoomSpeed = camera.zoom / 15;
        translateSpeed = camera.zoom * 3f;
    }

    public ArrayList<String> getObjectNames()
    {
        ArrayList<String> objectNames = new ArrayList<>();

        for (System system : systems)
            objectNames.add(system.getName());

        return objectNames;
    }
}
