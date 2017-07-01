package com.space.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.space.entities.SpaceObject;
import com.space.entities.Sun;
import com.space.managers.GameStateManager;
import com.space.menus.SystemContextMenu;

import java.util.ArrayList;

public class SystemState extends GameState
{
    private ShapeRenderer renderer;
    private OrthographicCamera camera;
    private SystemContextMenu menu;


    private boolean guiEnabled = false;
    private boolean menuEnabled = false;


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
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        menu = new SystemContextMenu(this);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.zoom = 50;

        sun.setCamera(camera);
    }


    public void getObjectNames(SpaceObject parent, ArrayList<String> names, int depth)
    {
        for (SpaceObject object : parent.getChildren())
        {
            names.add(new String(new char[depth]).replace('\0', '-') + object.getName());
            if (object.getChildren().size() != 0)
            {
                getObjectNames(object, names, depth+1);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        camera.update();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(camera.combined);
        sun.draw(renderer);

        if (menuEnabled) {
            menu.draw();
        }

        renderer.end();

    }

    @Override
    public void update() {
        handleInput();

        if (menuEnabled)
        {
            menu.handleInput();
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
        {
            sun.setPause();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N))
            sun.setAcceleration(30);
        if (Gdx.input.isKeyJustPressed(Input.Keys.B))
            sun.setAcceleration(1);
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            camera.position.set(sun.getPosition(), 0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            GameStateManager.resetLevel();
        }

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
