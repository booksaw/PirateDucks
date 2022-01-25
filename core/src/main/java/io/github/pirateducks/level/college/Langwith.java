package io.github.pirateducks.level.college;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.level.gameObjects.*;
import io.github.pirateducks.screen.PauseScreen;


public class Langwith extends College {

    private final OrthographicCamera camera;
    private final MainLevel mainLevel;
    private final Array<LangwithCannon> cannons = new Array<>();
    private boolean save = false;
    private float playerX, playerY = 0;
    public Music sfx_ocean;
    private Sound explode;

    public Langwith(MainLevel mainLevel, OrthographicCamera camera) {
        super(mainLevel);
        this.mainLevel = mainLevel;
        this.camera = camera;

        setHealth(4);

        // load and loops ocean sounds
        sfx_ocean = Gdx.audio.newMusic(Gdx.files.internal("Ocean.ogg"));
        sfx_ocean.setLooping(true);
        sfx_ocean.setVolume(0.005f);
        sfx_ocean.play();

        explode = Gdx.audio.newSound(Gdx.files.internal("explode.mp3"));
    }

    /**
     * @return The max health of the college building
     */
    @Override
    public int getMaxHealth() {
        // Each cannon will be worth 100 health
        return 4;
    }

    /**
     * Called to draw the screen
     *
     * @param batch
     * @param camera
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
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

        // If no cannons are alive, the college is defeated
        if (cannons.isEmpty()) {
            setHealth(0);
        }

        // Pause game when escape key is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            save = true;
            // Save players position for unpause
            playerX = getPlayer().getX();
            playerY = getPlayer().getY();
            this.stopDisplaying();
            // Load pause screen
            getLevelManager().getMainClass().setCurrentScreen(new PauseScreen(getLevelManager().getMainClass(),this));
        }

        // Return to main level if college is defeated
        if (isDefeated()) {
            save = false;
        }

        // Loop through objects and check for collision
        for (GameObject object : getObjectsClone()) {
            if (object instanceof CannonBall) {
                // checking if the players cannonball is colliding with a cannon
                Rectangle collision = ((CannonBall) object).getCollision();

                for (LangwithCannon c : cannons) {
                    // Check what fired the cannonball to stop instant collision with itself
                    if (collision.overlaps(c.getCollision()) && c.getHealth() != 0) {
                        // despawning the cannonball and lowering the cannons health
                        c.setHealth(c.getHealth()-1);
                        ((CannonBall) object).collide();
                    }
                }
            }

            // check if a cannonball is colliding with player
            if (object instanceof LangwithCannonball) {
                // Get hit box of cannonball
                Rectangle collision = ((LangwithCannonball) object).getCollision();

                if (collision.overlaps(getPlayer().getCollision())) {
                    // despawning the cannonball and lowering the players health
                    getPlayer().setHealth(getPlayer().getHealth()-1);
                    object.dispose();
                    explode.play(0.15f);
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
        // Only destroy cannons if not pausing
        if (!save) {
            for (GameObject object : cannons) {
                object.dispose();
            }
        }
        sfx_ocean.dispose();
    }

    /**
     * Spawns a cannonball from the cannons
     */
    public void spawnCannonball(float x, float y, float angle) {
        float size = 4;
        LangwithCannonball c = new LangwithCannonball(x - size / 2, y - size / 2, size, this, camera, angle);
        addObject(c);
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
     * called when the level is being set up to set up the default layout of the level
     *
     * @param camera
     */
    @Override
    protected void setup(OrthographicCamera camera) {
        if (!save) {
            // Add 4 cannons to the level, separated by an offset
            int offset = 0;
            for (int i = 0; i < 4; i++) {
                cannons.add(new LangwithCannon(130, 130, 50 + offset, camera.viewportHeight - 105, this));
                offset += 200;
            }
        }
        getPlayer().setX(playerX);
        getPlayer().setY(playerY);
    }
}
