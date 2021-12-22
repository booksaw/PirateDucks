package io.github.pirateducks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.screen.MainMenuScreen;
import io.github.pirateducks.screen.Screen;

public class PirateDucks extends ApplicationAdapter {

	private Screen currentScreen;

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
		setCurrentScreen(new LevelManager());
	}

	@Override
	public void render () {
		batch.begin();
		currentScreen.draw(batch);
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

	/**
	 * Used to change which screen is currently being displayed
	 * @param screen The screen to display
	 */
	public void setCurrentScreen(Screen screen) {
		if(currentScreen != null) {
			currentScreen.stopDisplaying();
		}
		currentScreen = screen;
		currentScreen.startDisplaying();
	}


}
