package io.github.pirateducks.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.gameObjects.Coin;
import io.github.pirateducks.level.gameObjects.Fruit;
import io.github.pirateducks.level.gameObjects.HealthIndicator;
import io.github.pirateducks.level.gameObjects.Player;
import io.github.pirateducks.screen.Screen;

import java.util.Random;

/**
 * This class is the manager when the actual game is being displayed
 */
public abstract class LevelManager implements Screen {

    private final PirateDucks mainClass;
    private final Array<GameObject> objects = new Array<>();
    private final Array<Coin> coins = new Array<>();
    private Player player = null;
    private Sprite map;
    private OrthographicCamera camera;
    private MainLevel mainLevel;
    private int[] possible_x;
    private int[] possible_y;
    private int i_x;
    private int i_y;

    public LevelManager(PirateDucks mainClass) { this.mainClass = mainClass; }

    @Override
    public final void startDisplaying(OrthographicCamera camera) {

        this.mainLevel = new MainLevel(mainClass);

        // loading the game
        this.camera = camera;

        // Setup player and health only once at the start of game
        if (player == null) {
            System.out.println(true);
            setPlayer(new Player(this, camera));
            addOverlay();
        }

        // load the map
        Texture texture = getMapTexture();
        map = new Sprite(texture);
        // scales the sprite depending on window size multiplied by a constant
        map.setSize(camera.viewportWidth, camera.viewportHeight);
        // Centers the map sprite
        map.setPosition(0, 0);

        setup(camera);
    }

    /**
     * Used to add all elements in the overlay
     */
    private void addOverlay() {
        objects.add(new HealthIndicator(getPlayer(), 5, 5));
    }

    /**
     * Used to change the game object that is acting as the player
     *
     * @param player The new player
     */
    public void setPlayer(Player player) {
        // closing the current player
        if (this.player != null) {
            objects.removeValue(this.player, true);
            this.player.dispose();
        }

        // declaring the new player
        this.player = player;
        // increases players health if they just defeated a college
        this.player.setMaxHealth(mainClass.getPlayerHealth());
        // restores player to full health
        this.player.setHealth(this.player.getMaxHealth());
        objects.add(this.player);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Used to render the level
     *
     * @param batch The batch that is rendering the level
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        // adding a plain color background as we do not have a map yet
        ScreenUtils.clear(0, 0, 0.2f, 1);
        map.draw(batch);
        for (GameObject object : objects) {
            object.render(batch);
        }
    }


    @Override
    public void update(float delta) {

        for (int i = 0; i < objects.size; i++) {
            objects.get(i).update(delta);
        }
    }

    @Override
    public void stopDisplaying() {
        if(pixmap != null) {
            pixmap.dispose();
        }

        map.getTexture().dispose();
        for (GameObject object : objects) {
            object.dispose();
        }
    }

    public PirateDucks getMainClass() { return mainClass; }

    /**
     * Used to add a game object to the level
     *
     * @param object
     */
    public void addObject(GameObject object) {
        objects.add(object);
        System.out.println("adding object " + object);
    }

    /**
     * Used to remove a game object from the level
     *
     * @param object the object to remove
     */
    public void removeObject(GameObject object) {
        objects.removeValue(object, false);
    }

    public boolean isOnLand(float x, float y) {
        Color color = getColorOfMap(x, y);

        return !(color.r > 0.2) || !(color.r < 0.3) || !(color.g >= 0.7) || !(color.g <= 0.8) || !(color.b > 0.8) || !(color.b < 0.9);
    }

    Pixmap pixmap;

    public Color getColorOfMap(float x, float y) {
        Texture texture = map.getTexture();
        x -= map.getX();
        y -= map.getY();
        // inverting y as pixmap goes from top left not bottom left
        y = map.getHeight() - y;

        // scaling to the image size as it is not its real size
        x = x * (texture.getWidth() / map.getWidth());
        y = y * (texture.getHeight() / map.getHeight());

        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }

        if(pixmap == null || pixmap.isDisposed()) {
            pixmap = texture.getTextureData().consumePixmap();
        }

        Color color = new Color(pixmap.getPixel((int) x, (int) y));

        return color;
    }

    /**
     * @return the camera of the level
     */
    public OrthographicCamera getCamera() { return camera; }

    /**
     * @return the texture of the map for this level
     */
    protected abstract Texture getMapTexture();

    /**
     * called when the level is being setup to setup the default layout of the level
     */
    protected abstract void setup(OrthographicCamera camera);

    public Array<GameObject> getObjectsClone() {
        return new Array<GameObject>(objects);
    }

    public Sprite getMap() {
        return map;
    }

    public Array<Coin> getCoins() {
        return coins;
    }

    /**
     * Used to remove a coin from the level
     * @param toRemove The coin that should be removed
     */
    public void removeCoin(Coin toRemove){
        coins.removeValue(toRemove, true);
        removeObject(toRemove);
    }

    public Array<Coin> getCoinsClone() {
        return new Array<>(getCoins());
    }

    public void spawnCoins(int no_coins) {
        // Creates an array of all possible x and y positions so that they will always be spaced out
        possible_x = new int[41];
        possible_y = new int[41];
        // Length of possible x and y should be higher than number of coins required
        if((no_coins > possible_x.length) || (no_coins > possible_y.length)) {
            throw new IllegalArgumentException("Length of possible x and y should be higher than number of coins required");
        }
        for (int i = 0; i < possible_x.length; i++) {
            possible_x[i] = i * 20;
        }
        for (int i = 0; i < possible_y.length; i++) {
            possible_y[i] = i * 10;
        }
        // Add coins, repeats for the number of coins required
        for (int n = 0; n < no_coins; n++) {
            // Randomly selects from the possible positions
            Random rnd_x = new Random();
            i_x = rnd_x.nextInt(possible_x.length);
            while (possible_x[i_x] == -1) {
                rnd_x = new Random();
                i_x = rnd_x.nextInt(possible_x.length);
            }
            // Randomly selects from the possible positions
            Random rnd_y = new Random();
            i_y = rnd_y.nextInt(possible_y.length);
            while (possible_y[i_y] == -1) {
                rnd_y = new Random();
                i_y = rnd_y.nextInt(possible_y.length);
            }
            // Checks that the coin would be on land and then adds it
            if (!isOnLand(possible_x[i_x], possible_y[i_y])) {
                getCoins().add(new Coin(possible_x[i_x], possible_y[i_y], this));
            }
            // Replaces the position with -1 so that it cannot be chosen again
            possible_x[i_x] = -1;
            possible_y[i_y] = -1;
        }
    }
}
