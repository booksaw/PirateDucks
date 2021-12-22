package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.level.GameObject;

public class CannonBall extends GameObject {

    private final Texture texture;
    private float cannonBallX, cannonBallY;
    private int damage = 50;
    private double angle;

    public CannonBall(float playerX, float playerY, int targetX, int targetY) {
        super(100, 100);

        // loading the texture
        texture = new Texture("CannonBall.png");

        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = ((float) texture.getWidth() / (float) Gdx.graphics.getWidth()) * 135f;
        SetSize(texture.getWidth() / scaleRatio, texture.getHeight() / scaleRatio);
        cannonBallX = playerX;
        cannonBallY = playerY;

        // We use a triangle to calculate the new trajectory
        angle = Math.atan2(targetY - cannonBallY, targetX - cannonBallX);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, cannonBallX, cannonBallY, width / 2, height/2, width, height, 1, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    @Override
    public void update(float delta) {
        float velocity = 200 * delta;
        cannonBallX += Math.cos(angle) * velocity;
        cannonBallY += Math.sin(angle) * velocity;
    }

    public void dispose() {
        texture.dispose();
    }
}
