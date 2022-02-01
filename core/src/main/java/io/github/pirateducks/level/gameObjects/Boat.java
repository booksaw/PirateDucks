package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.pathfinding.Checkpoint;

import java.util.List;
import java.util.Random;

/**
 * This class gives boats pathfinding on the main level
 */
public class Boat extends GameObject {

    private final double SPEED = 100;
    private final Sprite sprite;
    private final MainLevel level;
    List<Checkpoint> path;

    /**
     * Class constructor, called when creating a boat
     * @param width width of the boat
     * @param height height of the boat
     * @param level MainLevel class which holds the main game and player data
     */
    public Boat(float width, float height, MainLevel level) {
        super(width, height);

        this.level = level;
        Vector2 startLoc = generateDestination();

        this.x = startLoc.x;
        this.y = startLoc.y;

        Texture texture = new Texture(Gdx.files.internal("BrownBoat.png"));

        sprite = new Sprite(texture);
        sprite.setSize(width, height);

        generateNewPath();
    }

    /**
     * Used to generate a new path from the current location to a random point on the map
     */
    public void generateNewPath() {

        Vector2 destination = generateDestination();

        path = level.getPathFinder().getPath(x + width / 2, y + height / 2, destination.x + width / 2, destination.y + height / 2);
        if (path != null) {
            // removing the start node from the path
            path.remove(0);
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.setX(x);
        sprite.setY(y);

        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {
        float deltaSpeed = (float) (delta * SPEED);

        if (path == null || path.size() < 1) {
            return;
        }

        Checkpoint cp = path.get(0);

        Vector2 v = new Vector2(cp.x - x - sprite.getWidth() / 2, cp.y - y - sprite.getHeight()/ 2);
        v = v.limit(deltaSpeed);
        x += v.x;
        y += v.y;

        if (x + sprite.getWidth() / 2 == cp.x && y + sprite.getHeight() / 2 == cp.y) {
            path.remove(cp);
            if(path.size() == 0){
                generateNewPath();
            }
        }

        sprite.setRotation((float) Math.toDegrees(Math.atan2(-(double) v.x, v.y)));

    }

    /**
     * @return a random point on the map that the boat can travel too
     */
    private Vector2 generateDestination(){
        Random rnd = new Random();

        while(true){
            int x = rnd.nextInt((int) level.getMap().getWidth());
            int y = rnd.nextInt((int) level.getMap().getHeight());

            if(level.getPathFinder().isTraversable(x ,y)){
                return new Vector2(x - width / 2, y - height / 2);
            }
        }
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
