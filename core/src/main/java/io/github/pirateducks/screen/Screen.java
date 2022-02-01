package io.github.pirateducks.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This interface is used to create a new screen in the game
 * Contains all the methods needed for a screen
 */
public interface Screen {

    /**
     * Called to draw the screen
     * @param batch
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera);

    /**
     * Called to update the screen
     * @param delta The delta time since the last update
     */
    public void update(float delta);

    /**
     * Called when this screen becomes the active screen
     */
    public void startDisplaying(OrthographicCamera camera);

    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    public void stopDisplaying();

    /**
     * Resumes displaying, this is called after the pause menus stops displaying
     */
    public abstract void resume();

}
