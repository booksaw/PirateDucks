package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.college.Goodricke;
import java.util.Random;

/**
 * This class is the cannon used in the Goodricke college fight
 */
public class GoodrickeCannon extends Cannon {

    private final Goodricke goodricke;
    private final OrthographicCamera camera;
    private double rotationSpeed = 0.3;
    private Sound cannonFireSound = Gdx.audio.newSound(Gdx.files.internal("cannon-shot.mp3"));

    /**
     * Class constructor, called when creating a cannon for the Goodricke college fight
     * @param width width of cannon
     * @param height height of cannon
     * @param x x coordinate of cannon
     * @param y y coordinate of cannon
     * @param manager level manager
     * @param camera
     */
    public GoodrickeCannon(float width, float height, float x, float y, LevelManager manager, OrthographicCamera camera) {
        super(width, height, x, y, manager);

        if (!(manager instanceof Goodricke)) {
            throw new IllegalArgumentException("Goodricke cannon must be used along with goodricke college");
        }

        goodricke = (Goodricke) manager;
        this.camera = camera;
    }

    // shoot fruit in random directions at random intervals
    int untilNextShot = 50;

    @Override
    public void update(float delta) {
        super.update(delta);
        // if the cannon is about to be disposed but has not been so sound can play, skip update
        if(disposeTimer >= 0){
            return;
        }
        angle += rotationSpeed;
        untilNextShot -= delta;
        Random rnd = new Random();
        // flipping the direction of spin if it is out of bounds, or giving it a random chance of flipping mid rotation
        if(angle >= 60 || angle <= -60 || rnd.nextDouble() < 0.005){
            rotationSpeed = - rotationSpeed;
        }

        if (untilNextShot <= 0) {
            shootFruit();
            untilNextShot = rnd.nextInt(100) + 50;
        }
    }

    /**
     * Used to shoot a fruit in the direction the cannon is facing
     */
    private void shootFruit() {
        // launch a fruit
        goodricke.spawnFruit(x + width / 2, y, getAngle());
        long id = cannonFireSound.play();
        cannonFireSound.setVolume(id, 0.03f);
    }

    @Override
    public void dispose() {
        super.dispose();
        goodricke.removeCannon(this);
        cannonFireSound.dispose();
    }
}
