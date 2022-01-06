package io.github.pirateducks.level.college;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.level.gameObjects.CannonBall;
import io.github.pirateducks.level.gameObjects.Fruit;
import io.github.pirateducks.level.gameObjects.Player;

public class Goodricke extends College { // Projectiles
    private final OrthographicCamera camera;
    private final Array<Fruit> fruit = new Array<>();
//    private final Texture texture;

    private int health;
    private int time; // How long the game has been going on
    private int quantityLeft = 10; // Number of fruit left
    private float timeFired = 0; // Time since fruit was fired
    private float fruitSize = 10; // Size of fruit
    private final float sizeMultiplier = (float) 0.95;
    private int fruitSelect = 0; // Select which fruit
    private final int[] fruitList = {0, 1, 2, 3};
    private final int blastPower = 10; // Amount of damage fruit does
    private float playerX = 0; // Default player co-ordinates
    private float playerY = 0;

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

    public Goodricke(MainLevel level, OrthographicCamera camera) {
        super(level);

        this.camera = camera;
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public void update(float delta) {
        // updating all game objects
        super.update(delta);

        if (quantityLeft > 0 && health > 0) {
            // Find players current location
            playerX = getPlayer().getX();
            playerY = getPlayer().getY();

            // Projects the object at the target for the player to dodge
//            addObject(new Fruit(playerX, playerY, fruitSize, fruitSelect, this, camera));
            timeFired = 0;
        }

        // checking if any cannonballs are hitting any fruit
        for (GameObject object : getObjectsClone()) {
            if (object instanceof CannonBall) {
                // checking if the cannonball is colliding with fruit
                Rectangle collision = ((CannonBall) object).getCollision();

                for (Fruit f : getFruitClone()) {
                    // looping through fruit to check collision
                    if (collision.overlaps(f.getCollision())) {
                        // despawning the cannonball and the fruit
                        f.explode();
                        ((CannonBall) object).collide();
                    }
                }
            }
        }
        // checking if the player has hit any fruit
        Rectangle collision = getPlayer().getCollision();
        for (Fruit f : getFruitClone()) {
            if (f.getCollision().overlaps(collision)) {
                // despawning the fruit
                f.explode();
                // damaging the player
                getPlayer().setHealth(getPlayer().getHealth() - 2);
            }
        }

        // temp code to test fruit

    }

    /**
     * Used to spawn a new fruit at the specified location
     *
     * @param x The x coord of the fruit
     * @param y the y coord of the fruit
     */
    public void spawnFruit(float x, float y) {
        // To make the game more difficult, the size can be decreased
        fruitSize = fruitSize * sizeMultiplier;
        // Decreases the number of fruit left to spawn
        quantityLeft -= 1;
        Random rnd = new Random();
        Fruit f = new Fruit(x, y, 5, rnd.nextInt(Fruit.UNIQUEFRUIT), this, camera);
        fruit.add(f);
        addObject(f);
    }

    @Override
    public void setup(OrthographicCamera camera) {
    }

    @Override
    public void stopDisplaying() {
        super.stopDisplaying();

        for (Fruit f : fruit) {
            f.dispose();
        }

    }

    /**
     * @return the texture of the map for this level
     */
    @Override
    protected Texture getMapTexture() {
        return new Texture("goodricke/goodrickemap.png");
    }

    /**
     * Used to get a clone of the fruit array to avoid GdxRuntimeException
     *
     * @return
     */
    public Array<Fruit> getFruitClone() {
        return new Array<>(fruit);
    }
}
