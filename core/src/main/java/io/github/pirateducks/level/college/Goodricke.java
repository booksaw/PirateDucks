package io.github.pirateducks.level.college;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import java.util.Random;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.level.gameObjects.Fruit;
import io.github.pirateducks.level.gameObjects.Player;

public class Goodricke extends College { // Projectiles
    private final OrthographicCamera camera;
//    private final Texture texture;

    private int health;
    private int time; // How long the game has been going on
    private int quantityLeft = 10; // Number of fruit left
    private float timeFired = 0; // Time since fruit was fired
    private int fruitSize = 1; // Size of fruit
    private int fruitSelect = 0; // Select which fruit
    private int[] fruitList = {0, 1, 2, 3};
    private int lastFruit; // The last fruit used
    private int blastPower = 10; // Amount of damage fruit does
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
        // im not sure what this is, sand.png does not exist
        // texture = new Texture(Gdx.files.internal("sand.png"));
    }

    @Override
    public int getMaxHeath() {
        return 0;
    }

    /**
     * Used to render the level
     *
     * @param batch The batch that is rendering the level
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        super.draw(batch, camera);
    }

    @Override
    public void update(float delta) {
        // updating all game objects
        super.update(delta);

        if (quantityLeft > 0 && health > 0){
            // Find players current location
            playerX = getPlayer().getX();
            playerY = getPlayer().getY();

            // Projects the object at the target for the player to dodge
            addObject(new Fruit(playerX, playerY, fruitSize, fruitSelect, this, camera));
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
        setHealth(health - 1);
    }

    @Override
    public void setup(OrthographicCamera camera) {
    }

    @Override
    public void stopDisplaying() {
        super.stopDisplaying();
    }

    /**
     * @return the texture of the map for this level
     */
    @Override
    protected Texture getMapTexture() {
        return new Texture("goodricke/goodrickemap.png");
    }

    /**
     * Used to select the next fruit for the game, not using the same one as before
     */
    public void nextFruit() {
        lastFruit = fruitSelect; // Sets the last fruit used as the current fruit selected
        while (fruitSelect == lastFruit) { // Ensures that the same fruit will not be selected
            Random generator = new Random(); // Randomly selects the next fruit
            int ranIndex = generator.nextInt(fruitList.length);
            fruitSelect = fruitList[ranIndex];
        }
        // To make the game more difficult, the size can be increased
        fruitSize += 1;
    }
}
