package io.github.pirateducks.level.college;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.level.gameObjects.Fruit;
import io.github.pirateducks.level.gameObjects.Player;

public class Goodricke extends College { // Projectiles
    private final LevelManager manager;
    private final OrthographicCamera camera;
    private final Texture texture;
    private final Array<GameObject> objects = new Array<GameObject>();
    private Player player = null;

    private int health;
    private int time; // How long the game has been going on
    private int quantityLeft = 10; // Number of fruit left
    private float timeFired = 0; // Time since fruit was fired
    private int fruitSize = 1; // Size of fruit
    private int fruitSelect = 0; // Select which fruit
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

    public Goodricke(MainLevel level, LevelManager manager, OrthographicCamera camera) {
        super(level);

        this.camera = camera;
        this.manager = manager;

        texture = new Texture(Gdx.files.internal("sand.png"));
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

    }

    @Override
    public void update(float delta) {
        if (quantityLeft > 0 && health > 0){
            // Find players current location
            playerX = player.getX();
            playerY = player.getY();

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
        setHealth(health - 1);
    }

    @Override
    public void startDisplaying(OrthographicCamera camera) {
        setPlayer(new Player(this, camera));
    }

    @Override
    public void stopDisplaying() {

    }

    /**
     * @return the texture of the map for this level
     */
    @Override
    protected Texture getMapTexture() {
        return null;
    }

    /**
     * called when the level is being setup to setup the default layout of the level
     */
    @Override
    protected void setup() {

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        // closing the current player
        if (this.player != null) {
            objects.removeValue(this.player, true);
            this.player.dispose();
        }

        // declaring the new player
        this.player = player;
        objects.add(this.player);
    }

    /**
     * Used to select the next fruit for the game, not using the same one as before
     */
    public void nextFruit() {
        lastFruit = fruitSelect; // Sets the last fruit used as the current fruit selected
        while (fruitSelect != lastFruit) {

        }
        // To make the game more difficult, the size can be increased
        fruitSize += 1;
    }
}
