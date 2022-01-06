package io.github.pirateducks.level;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.college.ConstantineMemoryGame;
import io.github.pirateducks.level.college.Goodricke;
import io.github.pirateducks.level.gameObjects.CannonBall;
import io.github.pirateducks.level.gameObjects.HealthIndicator;
import io.github.pirateducks.level.gameObjects.Player;
import io.github.pirateducks.screen.PauseScreen;
import io.github.pirateducks.screen.Screen;

import java.util.ArrayList;

/**
 * This class is the manager when the actual game is being displayed
 */
public abstract class LevelManager implements Screen {

    private final PirateDucks mainClass;

    public LevelManager(PirateDucks mainClass) {
        this.mainClass = mainClass;
    }

    private final Array<GameObject> objects = new Array<GameObject>();
    private Player player = null;
    private Sprite map;
    private OrthographicCamera camera;
    private MainLevel mainLevel;

    @Override
    public final void startDisplaying(OrthographicCamera camera) {

        this.mainLevel = new MainLevel(mainClass);

        // loading the game
        this.camera = camera;
        setPlayer(new Player(this, camera));
        addOverlay();


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
        objects.add(new HealthIndicator(getPlayer()));
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

    int delay = 1000;

    @Override
    public void update(float delta) {

        for (int i = 0; i < objects.size; i++) {
            objects.get(i).update(delta);
        }

        // adding delay to the temp buttons so things dont break
        if (delay < 1000) {
            delay++;
        } else {

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                mainClass.setCurrentScreen(new PauseScreen(mainClass, this));
                delay = 0;
            }

            // Temporary method to launch Memory Game
            if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
                mainClass.setCurrentScreen(new ConstantineMemoryGame(mainLevel, camera));
                delay = 0;
            }

            // Temporary method to launch Goodricke college game
            if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
                mainClass.setCurrentScreen(new Goodricke(mainLevel, camera));
                delay = 0;
            }
        }
    }

    @Override
    public void stopDisplaying() {
        map.getTexture().dispose();
        for (GameObject object : objects) {
            object.dispose();
        }
    }

    public PirateDucks getMainClass() {
        return mainClass;
    }

    /**
     * Used to add a game object to the level
     *
     * @param object
     */
    public void addObject(GameObject object) {
        objects.add(object);
    }

    /**
     * Used to remove a game object to the level
     *
     * @param object
     */
    public void removeObject(GameObject object) {
        objects.removeValue(object, false);
    }

    public boolean isOnLand(float x, float y) {
        Color color = getColorOfMap(x, y);
        // this needs improving
        return !(color.r > 0.2) || !(color.r < 0.3) || !(color.g >= 0.7) || !(color.g <= 0.8) || !(color.b > 0.8) || !(color.b < 0.9);
    }

    public Color getColorOfMap(float x, float y) {
        Texture texture = map.getTexture();
        x -= map.getX();
        y -= map.getY();
        // inverting y as pixmap goes frop top left not bottom left
        y = camera.viewportHeight - y;

        // scaling to the image size as it is not its real size
        x = x * (texture.getWidth() / map.getWidth());
        y = y * (texture.getHeight() / map.getHeight());

        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }

        Pixmap pixmap = texture.getTextureData().consumePixmap();

        Color color = new Color(pixmap.getPixel((int) x, (int) y));
        return color;
    }

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
}
