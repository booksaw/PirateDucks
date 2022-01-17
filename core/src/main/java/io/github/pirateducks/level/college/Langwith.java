package io.github.pirateducks.level.college;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.level.gameObjects.LangwithCannon;
import io.github.pirateducks.level.gameObjects.Cannon;
import io.github.pirateducks.level.gameObjects.CannonBall;
import io.github.pirateducks.level.gameObjects.Fruit;
import io.github.pirateducks.level.gameObjects.Player;


public class Langwith extends College {

    private final OrthographicCamera camera;
    private final LevelManager manager;
    private final Array<LangwithCannon> cannons = new Array<>();
    private Sprite map;

    public Langwith(MainLevel mainLevel, OrthographicCamera camera, LevelManager manager) {
        super(mainLevel);
        this.manager = manager;
        this.camera = camera;
    }

    /**
     * @return The max health of the college building
     */
    @Override
    public int getMaxHealth() {
        // Each cannon will be worth 100 health
        return 400;
    }

    /**
     * Called to draw the screen
     *
     * @param batch
     * @param camera
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        map.draw(batch);
        super.draw(batch, camera);

        for (GameObject object : cannons) {
            object.render(batch);
        }
    }

    /**
     * Called to update the screen
     *
     * @param delta The delta time since the last update
     */
    @Override
    public void update(float delta) {
        // updating all game objects
        super.update(delta);

        // checking if any cannonballs are hitting any fruit
        for (GameObject object : getObjectsClone()) {
            if (object instanceof CannonBall) {
                // checking if the cannonball is colliding with fruit
                Rectangle collision = ((CannonBall) object).getCollision();

                // looping through cannons to check collision
                for (LangwithCannon c : cannons) {
                    // Check what fired cannonball to stop instant collision with itself
                    if (collision.overlaps(c.getCollision()) && ((CannonBall) object).getFiredBy() instanceof Player) {
                        // despawning the cannonball and lowering the cannons health
                        c.setHealth(c.getHealth()-1);
                        ((CannonBall) object).collide();
                    }
                }
            }
        }

        for (GameObject object : cannons) {
            object.update(delta);
        }

    }

    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    @Override
    public void stopDisplaying() {
        map.getTexture().dispose();
        for (GameObject object : cannons) {
            object.dispose();
        }
    }

    /**
     * @return the texture of the map for this level
     */
    @Override
    protected Texture getMapTexture() {
        return new Texture("Langwith/BulletHellBackground.png");
    }

    public void removeCannon(LangwithCannon cannon) {
        cannons.removeValue(cannon, false);
    }

    /**
     * called when the level is being setup to setup the default layout of the level
     *
     * @param camera
     */
    @Override
    protected void setup(OrthographicCamera camera) {
        // load the map
        Texture texture = new Texture("Langwith/BulletHellBackground.png");
        map = new Sprite(texture);
        // scales the sprite depending on window size multiplied by a constant
        map.setSize(camera.viewportWidth, camera.viewportHeight);

        // Add 4 cannons to the level, separated by an offset
        int offset = 0;
        for (int i = 0; i < 4; i++) {
            cannons.add(new LangwithCannon(130, 130, 50 + offset, camera.viewportHeight - 105, this));
            offset += 200;
        }
    }
}
