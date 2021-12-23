package io.github.pirateducks.level;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.gameObjects.CannonBall;
import io.github.pirateducks.level.gameObjects.HealthIndicator;
import io.github.pirateducks.level.gameObjects.Player;
import io.github.pirateducks.screen.Screen;

/**
 * This class is the manager when the actual game is being displayed
 */
public class LevelManager implements Screen {

    private final PirateDucks mainClass;

    public LevelManager(PirateDucks mainClass){
        this.mainClass = mainClass;
    }

    private final Array<GameObject> objects = new Array<GameObject>();
    private Player player = null;
    private Sprite map;

    @Override
    public void startDisplaying(OrthographicCamera camera) {
        // loading the game
        setPlayer(new Player(this, camera));
        addOverlay();


        // load the map
        Texture texture = new Texture("map.png");
        map = new Sprite(texture);
        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = map.getWidth() / camera.viewportWidth;
        map.setSize(map.getWidth() / scaleRatio, map.getHeight() / scaleRatio);
        // Centers the map sprite
        map.setPosition(0, 0);
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
        if(this.player != null) {
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
     * @param batch The batch that is rendering the level
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera){
        // adding a plain color background as we do not have a map yet
        ScreenUtils.clear(0, 0, 0.2f, 1);

        map.draw(batch);

        for (GameObject object : objects) {
            object.render(batch);
        }
    }

    @Override
    public void update(float delta){

        for (GameObject object : objects) {
            object.update(delta);
        }
    }

    @Override
    public void stopDisplaying() {
        for (GameObject object : objects) {
            object.dispose();
        }
    }

    public PirateDucks getMainClass() {
        return mainClass;
    }

    /**
     * Used to add a game object to the level
     * @param object
     */
    public void addObject(GameObject object){
        objects.add(object);
    }
}
