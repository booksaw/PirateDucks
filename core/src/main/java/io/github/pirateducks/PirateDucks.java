package io.github.pirateducks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.level.college.Langwith;
import io.github.pirateducks.level.college.Goodricke;

import io.github.pirateducks.screen.*;

public class PirateDucks extends ApplicationAdapter {

	private Screen currentScreen;

	/**
	 * Used to test junit functionality is working as intended
	 * TODO REMOVE THIS WHEN JUNIT TESTS ARE WRITTEN
	 */
	public static boolean getTrue() {
		return true;
	}

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,848, 480);
//		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		camera.update();
		viewport = new FitViewport(848, 480);
		batch = new SpriteBatch();

		// jumping straight to the game, this will need to be changed in the future
		MainLevel level = new MainLevel(this);
		//setCurrentScreen(new Goodricke(level, camera));
		setCurrentScreen(new MainMenuScreen(this));
		//setCurrentScreen(new Langwith(new MainLevel(this)));
	}

	@Override
	public void render () {
		camera.update();
//		batch.setTransformMatrix(camera.view);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		currentScreen.draw(batch, camera);
		batch.end();

		currentScreen.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		batch.dispose();
		// telling the current screen to dispose of resources
		currentScreen.stopDisplaying();
	}

	public Screen getCurrentScreen(){
		return currentScreen;
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	/**
	 * Used to change which screen is currently being displayed
	 * @param screen The screen to display
	 */
	public void setCurrentScreen(Screen screen) {
		if(currentScreen != null) {
			currentScreen.stopDisplaying();
		}
		currentScreen = screen;
		currentScreen.startDisplaying(camera);
	}
}
