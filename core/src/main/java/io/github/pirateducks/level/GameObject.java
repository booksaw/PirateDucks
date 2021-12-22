package io.github.pirateducks.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/***
 * Class that all objects in the game world extend
 */
public abstract class GameObject {

    // the position information for the GameObject
    protected float x = 0;
    protected float y = 0;

    protected float width;
    protected float height;

    public GameObject(float width, float height){
        this.width = width;
        this.height = height;
    }

    /**
     * This method is used to render the gameObject to the screen
     *
     * @param batch The spriteBatch that the game object is rendered as a part of. The batch will already be begun when this is called
     */
    public abstract void render(SpriteBatch batch);

    /**
     * this method is called to update the positional information of the GameObject
     * @param delta The delta time since the last update
     */
    public abstract void update(float delta);

    /**
     * Use this method to dispose of any resources (ie Texture resources)
     */
    public abstract void dispose();

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
