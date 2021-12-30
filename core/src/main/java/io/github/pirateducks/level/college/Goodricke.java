package io.github.pirateducks.level.college;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.gameObjects.Fruit;

public class Goodricke extends College { // Projectiles
    private final LevelManager manager;
    private final OrthographicCamera camera;
    private final Texture texture;

    private int health;
    private int time; // How long the game has been going on
    private int quantityLeft = 10; // Number of fruit left
    private float timeFired = 0; // Time since fruit was fired
    private int fruitSize = 1; // Size of fruit
    private int fruitSelect = 0; // Select which fruit

    private int playerX = 0;
    private int playerY = 0;

    /**
     * Goodricke
     * Be able to set a new background (maybe sand): Yes
     * Be able to have multiple fruit at once: Not sure
     * Be able to aim fruit to player: Yes
     * Be able to shoot at the fruit: No
     * Be able to calculate time since fired: No
     * Be able to count the time since game started: No
     * Be able to count how many fruit are left to fire: Yes
     * Be able to adjust player health: No
     */

    public Goodricke(LevelManager manager, OrthographicCamera camera) {
        super(); // ? not sure

        this.camera = camera;
        this.manager = manager;

        texture = new Texture(Gdx.files.internal("sand.png"));
    }

    @Override
    public int getMaxHeath() {
        return 0;
    }

    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {

    }

    @Override
    public void update(float delta) {
        if (quantityLeft > 0 && health > 0){
            // Find players current location
            // NOT SURE HOW

            // Projects the object at the target for the player to dodge
            manager.addObject(new Fruit(playerX, playerY, fruitSize, fruitSelect, manager, camera));
            timeFired = 0;
            quantityLeft -= 1;

        }
    }

    public void fruitDestroyed() {
        // When the fruit is destroyed by the player show explosion
        // When fruit is hit by the bullet (when bullet touches): destroy fruit
    }

    public void playerDamaged() {
        // When the fruit hits the player
        // When player is hit by fruit: lose health
    }

    @Override
    public void startDisplaying(OrthographicCamera camera) {

    }

    @Override
    public void stopDisplaying() {

    }

    @Override
    protected Texture getMapTexture() {
        return null;
    }

    @Override
    protected void setup() {

    }

}
