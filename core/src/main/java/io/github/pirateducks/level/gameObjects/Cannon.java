package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.GameObjectHealth;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.college.Langwith;

public abstract class Cannon extends GameObjectHealth {

    private final Texture texture;
    private final Sprite sprite;
    private final HealthIndicator healthIndicator;
    private int health;
    private int maxHealth;
    protected float angle = 0;
    private final LevelManager manager;

    private Sound explode;

    public Cannon(float width, float height, float x, float y, LevelManager manager) {

        super(width, height);
        this.x = x;
        this.y = y;
        this.manager = manager;
        maxHealth = 6;
        health = 6;
        texture = new Texture("Langwith/Cannon.png");
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        healthIndicator = new HealthIndicator(this, x + 30, y + 85);

        explode = Gdx.audio.newSound(Gdx.files.internal("explode.mp3"));
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

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
            explode.play(0.3f);
            disposeTimer = 100;
        }
    }

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
