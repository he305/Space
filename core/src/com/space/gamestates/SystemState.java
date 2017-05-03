package com.space.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.space.entities.Planet;
import com.space.entities.Sun;

public class SystemState extends GameState
{
    final int translateSpeed = 10;
    final float zoomSpeed = 5f;
    protected ShapeRenderer renderer;
    protected Sun sun;
    protected OrthographicCamera camera;

    protected void init()
    {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        sun = new Sun(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 1, 1, "Sun");

        sun.addChildrenRelatively(new Planet(new Vector2(122180, 0), 11.21f, 100, "Jupiter"));
        sun.addChildrenRelatively(new Planet(new Vector2(23518, 0), 1, 1, "Earth"));
        sun.getChildrenByName("Earth").addChildrenRelatively(new Planet(new Vector2(60, 0), 0.272f, 0.0123f, "Moon"));
        sun.addChildrenRelatively(new Planet(new Vector2(9079, 0), 0.3f, 0.5f, "Mercury"));
        sun.addChildrenRelatively(new Planet(new Vector2(16933, 0), 0.5f, 0.7f, "Venus"));
    }

    @Override
    public void draw(SpriteBatch batch) {
        camera.update();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(camera.combined);
        sun.draw(renderer);
        renderer.end();
    }

    @Override
    public void update() {
        handleInput();
        sun.update();
    }


    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            camera.position.set(sun.getChildrenByName("Earth").getPosition(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            camera.position.set(sun.getChildrenByName("Mercury").getPosition(), 0);
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
}
