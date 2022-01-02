package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.level.GameObject;

public class Cannon extends GameObject {

    public Cannon(float width, float height) {
        super(200, 200);
    }

    /**
     * This method is used to render the gameObject to the screen
     *
     * @param batch The spriteBatch that the game object is rendered as a part of. The batch will already be begun when this is called
     */
    @Override
    public void render(SpriteBatch batch) {

    }

    /**
     * this method is called to update the positional information of the GameObject
     *
     * @param delta The delta time since the last update
     */
    @Override
    public void update(float delta) {

    }

    /**
     * Use this method to dispose of any resources (ie Texture resources)
     */
    @Override
    public void dispose() {

    }
}
