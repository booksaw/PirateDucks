package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;

public class CannonBall extends GameObject {

    private final Texture texture;
    private final Sprite sprite;
    private double angle;
    private final LevelManager manager;
    private final OrthographicCamera camera;
    private final Vector2 direction;

    public CannonBall(float sourceX, float sourceY, LevelManager manager, Vector2 direction) {
        super(100, 100);

        this.camera = manager.getCamera();
        this.manager = manager;

        // loading the texture
        this.texture = new Texture("CannonBall.png");
        this.sprite = new Sprite(texture);

        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = ((float) texture.getWidth() / (float) Gdx.graphics.getWidth()) * 50f;
        SetSize(texture.getWidth() / scaleRatio, texture.getHeight() / scaleRatio);
        this.sprite.setSize(texture.getWidth() / scaleRatio, texture.getHeight() / scaleRatio);

        this.x = sourceX;
        this.y = sourceY;

        // We use a triangle to calculate the new trajectory
        //this.angle = Math.atan2(targetY - y, targetX - x);
        this.direction = direction;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height/2, width, height, 1, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public Sprite getSprite() {return sprite;}

    @Override
    public void update(float delta) {
        float velocity = 200 * delta;
        //x += Math.cos(angle) * velocity;
        //y += Math.sin(angle) * velocity;
        x += direction.x * velocity;
        y += direction.y * velocity;

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