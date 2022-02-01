package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.level.GameObjectHealth;
import io.github.pirateducks.level.LevelManager;

/**
 * Used as a base for other cannon classes
 */
public abstract class Cannon extends GameObjectHealth {

    private final Texture texture;
    private final Sprite sprite;
    private final HealthIndicator healthIndicator;
    private int health;
    private int maxHealth;
    protected float angle = 0;
    private Sound explode;
    private LevelManager manager;

    /**
     * Class constructor, called when creating a cannon
     * @param width width of the boat
     * @param height height of the boat
     * @param x x coordinate of the cannon
     * @param y y coordinate of the cannon
     */
    public Cannon(float width, float height, float x, float y, LevelManager manager) {

        super(width, height);
        this.x = x;
        this.y = y;
        maxHealth = 6;
        health = 6;
        this.manager = manager;

        texture = new Texture("Langwith/Cannon.png");
        sprite = new Sprite(texture);
        sprite.setSize(width, height);

        healthIndicator = new HealthIndicator(this, x + 30, y + 85, manager.getCamera());
        explode = Gdx.audio.newSound(Gdx.files.internal("explode.mp3"));
    }

    /**
     * Sets the angle of the cannon
     * @param angle
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Gets the angle of the cannon
     * @return angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * This method is used to render the gameObject to the screen
     *
     * @param batch The spriteBatch that the game object is rendered as a part of. The batch will already be begun when this is called
     */
    @Override
    public void render(SpriteBatch batch) {
        // if the cannon has only not been disposed as the explosion sound is playing, do not render it
        if(disposeTimer >= 0){
            return;
        }

        batch.draw(texture, x, y, width/2, height/2, width, height, 1, 1, angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        healthIndicator.render(batch);

    }

    /**
     * timer until the cannon should be disposed
     */
    protected int disposeTimer = -1;

    /**
     * this method is called to update the positional information of the GameObject
     *
     * @param delta The delta time since the last update
     */
    @Override
    public void update(float delta) {

        healthIndicator.update(delta);
        if(disposeTimer > 0){
            disposeTimer--;
            return;
        } else if(disposeTimer == 0){
            dispose();
            return;
        }

    }

    /**
     * Gets the cannons sprite
     * @return sprite
     */
    public Sprite getSprite() {return sprite;}

    /**
     * @return The health of that object
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * Change the health of the object
     *
     * @param health
     */
    @Override
    public void setHealth(int health) {
        this.health = health;
        if (health <= 0) {
            explode.play(0.4f);
            disposeTimer = 100;
        } else{
            explode.play(0.2f);
        }
    }

    /**
     * Gets the max health of the cannon
     * @return max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Set the max health of the player, NOTE: This number must be even
     *
     * @param maxHealth The max health of the player
     */
    public void setMaxHealth(int maxHealth) {
        if (maxHealth % 2 != 0) {
            throw new IllegalArgumentException("The game currently cannot render a max health which is odd.");
        }
        this.maxHealth = maxHealth;
    }

    /**
     * Gets the hit box of the cannon
     * @return hit box rectangle
     */
    public Rectangle getCollision(){
        return new Rectangle(x, y, width, height);
    }

    /**
     * Use this method to dispose of any resources (ie Texture resources)
     */
    @Override
    public void dispose() {
        explode.dispose();
        texture.dispose();
    }

}
