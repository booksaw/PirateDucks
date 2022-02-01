package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;

/**
 * Calculates angle between player and mouse location and fires a cannonball in that direction
 */
public class CannonBall extends GameObject {

    private final Texture texture;
    private final Sprite sprite;
    private double angle;
    private final LevelManager manager;
    private final OrthographicCamera camera;

    /**
     * Class constructor, called when firing a cannonball
     * @param sourceX starting x coordinate
     * @param sourceY starting y coordinate
     * @param targetX target x coordinate
     * @param targetY target y coordinate
     * @param manager the level manager
     */
    public CannonBall(float sourceX, float sourceY, float targetX, float targetY, LevelManager manager) {
        super(100, 100);

        this.camera = manager.getCamera();
        this.manager = manager;

        // loading the texture
        this.texture = new Texture("CannonBall.png");
        this.sprite = new Sprite(texture);

        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = ((float) texture.getWidth() / (float) manager.getCamera().viewportWidth) * 50f;
        setSize(texture.getWidth() / scaleRatio, texture.getHeight() / scaleRatio);
        sprite.setSize(texture.getWidth() / scaleRatio, texture.getHeight() / scaleRatio);

        // sets center of sprite at source coordinates
        this.x = sourceX - (width / 2);
        this.y = sourceY - (height / 2);

        // Uses a triangle to calculate the new trajectory
        this.angle = Math.atan2(targetY - y, targetX - x);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height/2, width, height, 1, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    /**
     * Gets the cannonballs sprite
     * @return sprite
     */
    public Sprite getSprite() {return sprite;}

    @Override
    public void update(float delta) {
        float velocity = 200 * delta;
        x += Math.cos(angle) * velocity;
        y += Math.sin(angle) * velocity;

        // destroy cannonball if it goes off the screen
        // limiting x
        if (x <= -width / 2 || x >= camera.viewportWidth - width / 2) {
            dispose();
        }

        // limiting y
        if (y <= -height / 2 || y >= camera.viewportHeight - height / 2) {
            dispose();
        }
    }

    public void dispose() {
        texture.dispose();
        // Remove cannonball object from the list of objects to be rendered
        manager.removeObject(this);
    }

    /**
     * Gets the hit box of the cannonball
     * @return hit box rectangle
     */
    public Rectangle getCollision() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Called when the cannonball hits something so it can be despawned
     */
    public void collide(){
        manager.removeObject(this);
    }
}