package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.Health;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.screen.GameOverScreen;

public class Player extends GameObject implements Health {
    //Body is an object yet to be defined which will be defined as the main Player

    private final Texture texture;
    private float rotation;
    private final LevelManager manager;

    public Player(LevelManager manager) {
        super(100, 100);
        this.manager = manager;
        // loading the texture
        texture = new Texture(Gdx.files.internal("DuckBoat_TopView.png"));
        rotation = 0;
        maxHealth = 6;
        health = 6;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height/2, width, height, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    private final double SPEED = 5;

    @Override
    public void update(float delta) {
        float vel_x = 0;   //Replace the x to find optimal moving speed
        float vel_y = 0;   //Replace the y to find optimal moving speed

        double deltaSpeed = SPEED + delta;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            vel_y += deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            vel_y -= deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            vel_x -= deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            vel_x += deltaSpeed;
        }

        if(vel_x != 0 || vel_y != 0){
           rotation = (float) Math.toDegrees(-Math.atan2(vel_x, vel_y));
        }

        x += vel_x;
        y += vel_y;

    }

    public void dispose() {
        texture.dispose();
    }

    private int health;
    private int maxHealth;

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
        if(health <= 0){
            manager.getMainClass().setCurrentScreen(new GameOverScreen());
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Set the max health of the player, NOTE: This number must be even
     * @param maxHealth The max health of the player
     */
    public void setMaxHealth(int maxHealth) {
        if(maxHealth % 2 != 0){
            throw new IllegalArgumentException("The game currently cannot render a max health which is odd.");
        }
        this.maxHealth = maxHealth;
    }
}
