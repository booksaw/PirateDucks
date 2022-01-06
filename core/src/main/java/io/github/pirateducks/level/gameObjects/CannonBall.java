package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;

public class CannonBall extends GameObject {

    private final Texture texture;
    private int damage = 50;
    private double angle;
    private final LevelManager manager;
    private final OrthographicCamera camera;

    public CannonBall(float playerX, float playerY, int targetX, int targetY, LevelManager manager, OrthographicCamera camera) {
        super(100, 100);

        this.camera = camera;
        this.manager = manager;

        // loading the texture
        texture = new Texture("CannonBall.png");

        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = ((float) texture.getWidth() / (float) Gdx.graphics.getWidth()) * 135f;
        SetSize(texture.getWidth() / scaleRatio, texture.getHeight() / scaleRatio);
        x = playerX;
        y = playerY;

        // We use a triangle to calculate the new trajectory
        angle = Math.atan2(targetY - y, targetX - x);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height/2, width, height, 1, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    @Override
    public void update(float delta) {
        float velocity = 200 * delta;
        x += Math.cos(angle) * velocity;
        y += Math.sin(angle) * velocity;

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

    public Rectangle getCollision(){
        return new Rectangle(x, y, width, height);
    }

    /**
     * Called when the cannonball hits something so it can be despawned
     */
    public void collide(){
        manager.removeObject(this);
    }
}