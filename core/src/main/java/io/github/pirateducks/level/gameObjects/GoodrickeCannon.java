package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.college.Goodricke;

import java.util.Random;

public class GoodrickeCannon extends Cannon {

    private final Goodricke goodricke;
    private final OrthographicCamera camera;
    private double rotationSpeed = 0.3;

    public GoodrickeCannon(float width, float height, float x, float y, LevelManager manager, OrthographicCamera camera) {
        super(width, height, x, y, manager);

        if (!(manager instanceof Goodricke)) {
            throw new IllegalArgumentException("Goodricke cannon must be used along with goodricke college");
        }

        goodricke = (Goodricke) manager;
        this.camera = camera;

    }

    // this class needs to:
    // shoot fruit in random directions at random intervals

    int untilNextShot = 50;

    @Override
    public void update(float delta) {
        super.update(delta);
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
    }

    @Override
    public void dispose() {
        super.dispose();
        goodricke.removeObject(this);
    }
}
