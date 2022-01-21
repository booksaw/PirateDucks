package io.github.pirateducks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
		camera.update();
		viewport = new FitViewport(848, 480);
		batch = new SpriteBatch();

		// Start game on main menu
		setCurrentScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		camera.update();
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

	/**
	 * Used to get which screen is currently being displayed
	 */
	public Screen getCurrentScreen() { return currentScreen; }

	public OrthographicCamera getCamera() {
		return camera;
	}
}