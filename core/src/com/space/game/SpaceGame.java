package com.space.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.managers.GameStateManager;

public class SpaceGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		GameStateManager.init(batch);
		System.out.println(Gdx.graphics.getWidth());
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		GameStateManager.draw();
		batch.end();

		GameStateManager.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
