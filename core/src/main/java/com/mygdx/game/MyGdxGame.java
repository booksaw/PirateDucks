package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.level.LevelManager;

public class MyGdxGame extends ApplicationAdapter {

	LevelManager manager;

	/**
	 * Used to test junit functionality is working as intended
	 * TODO REMOVE THIS WHEN JUNIT TESTS ARE WRITTEN
	 */
	public static boolean getTrue() {
		return true;
	}

	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// jumping straight to the game, this will need to be changed in the future
		manager = new LevelManager();
	}

	@Override
	public void render () {

		batch.begin();
		manager.render(batch);
		batch.end();

		manager.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}


}
