package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.college.Langwith;

import java.util.Random;

/**
 * This class is the cannon used in the Langwith college fight
 */
public class LangwithCannon extends Cannon {

    private final Langwith langwith;
    private int phase = 1;
    private int shotsFired = 0;
    private double rotationSpeed = 0.5;
    private boolean startFiring = false;
    private final Sound cannonFireSound = Gdx.audio.newSound(Gdx.files.internal("cannon-shot.mp3"));

    /**
     * Creates a cannon for the langwith college fight with the following parameters
     *
     * @param width   The width of the cannon
     * @param height  The height of the cannon
     * @param x       the x coord of the cannon
     * @param y       the y coord of the cannon
     * @param manager The Levelmanager that controls the cannon
     */
    public LangwithCannon(float width, float height, float x, float y, Langwith langwith) {
        super(width, height, x, y, langwith);

        this.langwith = langwith;
    }

    int untilNextShot = 50;

    @Override
    public void update(float delta) {
        super.update(delta);

        // if the cannon is about to be disposed but has not been so sound can play, skip update
        if (disposeTimer >= 0) {
            return;
        }

        if (startFiring) {
            // Every 5 cannonballs fired will swap the phase between a pattern and rotating randomly
            angle += rotationSpeed;
            if (phase == 1) {
                shootPattern(delta);
            } else {
                shootRandomly(delta);
            }
        } else {
            // Rotate cannons to -45 at the start
            angle -= rotationSpeed;
            if (angle == -45) {
                startFiring = true;
            }
        }
        untilNextShot -= delta;
    }

    /**
     * Used to shoot a cannonball from the cannon
     *
     * @param delta The deta for the update period
     */
    private void shootCannonball(float delta) {
        shotsFired++;
        langwith.spawnCannonball(x + width / 2, y, getAngle());
        long id = cannonFireSound.play();
        cannonFireSound.setVolume(id, 0.15f);
    }

    /**
     * Sweep left to right firing cannonballs
     */
    private void shootPattern(float delta) {
        if ((untilNextShot <= 0)) {
            if (angle >= 50 || angle <= -50) {
                rotationSpeed = -rotationSpeed;
            }
            if (shotsFired == 0) {
                angle = -45f;
                rotationSpeed = Math.abs(rotationSpeed);
                shootCannonball(delta);
            } else if (shotsFired == 5) {
                phase = 2;
                shotsFired = 0;
            } else {
                shootCannonball(delta);
            }
            untilNextShot = 70;
        }
    }

    /**
     * Randomly rotate left and right firing cannonballs
     */
    private void shootRandomly(float delta) {
        if (untilNextShot <= 0) {
            Random rnd = new Random();
            if (angle >= 40 || angle <= -40 || rnd.nextDouble() < 0.5) {
                rotationSpeed = -rotationSpeed;
            }
            shootCannonball(delta);
            if (shotsFired == 5) {
                phase = 1;
                shotsFired = 0;
            }
            untilNextShot = 70;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        // Remove cannon object from the list of objects to be rendered
        langwith.removeCannon(this);
        cannonFireSound.dispose();
    }
}
