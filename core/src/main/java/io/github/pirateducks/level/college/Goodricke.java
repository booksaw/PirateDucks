package io.github.pirateducks.level.college;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.level.gameObjects.CannonBall;
import io.github.pirateducks.level.gameObjects.Fruit;
import io.github.pirateducks.level.gameObjects.GoodrickeCannon;

public class Goodricke extends College { // Projectiles
    private final OrthographicCamera camera;
    private final Array<Fruit> fruit = new Array<>();
//    private final Texture texture;

    private float fruitSize = 10; // Size of fruit
    private final float sizeMultiplier = (float) 1;
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
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public void update(float delta) {
        // updating all game objects
        super.update(delta);

        // checking if all the cannons are dead
        boolean dead = true;
        for(GoodrickeCannon cannon : cannons){
            if(cannon.getHealth() > 0){
                dead = false;
                break;
            }
        }
        if(dead){
            // all cannons are dead, the college has been defeated setting its health to 0
            setHealth(0);
        }

        // checking if any cannonballs are hitting any fruit or cannons
        for (GameObject object : getObjectsClone()) {
            if (object instanceof CannonBall) {
                // checking if the cannonball is colliding with fruit
                Rectangle collision = ((CannonBall) object).getCollision();

                for (Fruit f : getFruitClone()) {
                    // looping through fruit to check collision
                    if (collision.overlaps(f.getCollision())) {
                        // despawning the cannonball and the fruit
                        f.explode();
                        ((CannonBall) object).collide();
                        continue; // cannot both collide with fruit and cannon in the same collision
                    }
                }

                for(GoodrickeCannon cannon : cannons){
                    // looping through the cannons to check collision
                    if(collision.overlaps(cannon.getCollision())){
                        cannon.setHealth(cannon.getHealth() - 2);
                        ((CannonBall)object).collide();
                        continue;
                        // no need to check other cannons
                    }
                }
            }
        }
        // checking if the player has hit any fruit
        Rectangle collision = getPlayer().getCollision();
        for (Fruit f : getFruitClone()) {
            if (f.getCollision().overlaps(collision)) {
                // despawning the fruit
                f.explode();
                // damaging the player
                getPlayer().setHealth(getPlayer().getHealth() - 2);
            }
        }

        // temp code to test fruit

    }

    /**
     * Used to spawn a new fruit at the specified location
     *
     * @param x The x coord of the fruit
     * @param y the y coord of the fruit
     */
    public void spawnFruit(float x, float y, float angle) {
        // To make the game more difficult, the size can be decreased
        fruitSize = fruitSize * sizeMultiplier;
        Random rnd = new Random();
        Fruit f = new Fruit(x - fruitSize / 2, y - fruitSize / 2, fruitSize, rnd.nextInt(Fruit.UNIQUEFRUIT), this, camera, angle);
        fruit.add(f);
        addObject(f);
    }

    GoodrickeCannon[] cannons;

    @Override
    public void setup(OrthographicCamera camera) {

        // init the cannons
        cannons = new GoodrickeCannon[3];
        cannons[0] = new GoodrickeCannon(130, 130, 200, 300, this, camera);
        addObject(cannons[0]);

        cannons[1] = new GoodrickeCannon(130, 130, 350, 240, this, camera);
        addObject(cannons[1]);

        cannons[2] = new GoodrickeCannon(130, 130, 500, 300, this, camera);
        addObject(cannons[2]);
    }

    @Override
    public void stopDisplaying() {
        super.stopDisplaying();

        for (Fruit f : fruit) {
            f.dispose();
        }

    }

    /**
     * @return the texture of the map for this level
     */
    @Override
    protected Texture getMapTexture() {
        return new Texture("goodricke/goodrickemap.png");
    }

    /**
     * @return a clone of the fruit array to avoid GdxRuntimeException
     */
    public Array<Fruit> getFruitClone() {
        return new Array<>(fruit);
    }

    /**
     * Used to remove a fruit from the level
     * @param toRemove The fruit that should be removed
     */
    public void removeFruit(Fruit toRemove){
        fruit.removeValue(toRemove, true);
        removeObject(toRemove);
    }
}
