package com.space.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameState
{
    protected String gameStateName;

    protected ShapeRenderer renderer;
    protected OrthographicCamera camera;

    protected boolean menuEnabled = false;

    public GameState()
    {
        this.gameStateName = this.getClass().getName();

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.zoom = 50;
    }

    protected abstract void init();

    public abstract void draw(SpriteBatch batch);

    public abstract void update();

}
