package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Screen {

    /**
     * Called to draw the screen
     * @param batch
     */
    public void draw(SpriteBatch batch);

    /**
     * Called to update the screen
     * @param delta The delta time since the last update
     */
    public void update(float delta);

    /**
     * Called when this screen becomes the active screen
     */
    public void startDisplaying();

    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    public void stopDisplaying();

}
