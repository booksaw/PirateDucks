package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.college.Goodricke;

import java.util.Random;

/**
 * Used to calculate an angle and fire a random fruit in that direction
 */
public class Fruit extends GameObject {

    /**
     * Stores the number of unique fruit that exist in the game
     */
    public static final int UNIQUEFRUIT = 4;

    private final Texture texture;
    private final Goodricke manager;
    private final OrthographicCamera camera;
    private float angle;
    private float rotation = 0;
    private String fruitChoice = "apple.png";

    /**
     * Create a new fruit
     * @param x The starting x location of the fruit
     * @param y The starting y location of the fruit
     * @param size the size of the fruit
     * @param select which fruit to select
     * @param manager the LevelManager that the fruit are in (must be goodricke)
     * @param camera the camera for the level
     * @param angle The angle that the fruit will travel at
     */
    public Fruit(float x, float y, float size, int select, LevelManager manager, OrthographicCamera camera, float angle) {
        super(100, 100);

        this.camera = camera;

        if(!(manager instanceof  Goodricke)){
            throw new IllegalArgumentException("The fruit class can only be used during the goodricke battle");
        }
        this.manager = (Goodricke) manager;


        // Allows the fruit to be changed using an int value
        if (select == 0) {
            fruitChoice = "apple.png";
        }else if (select == 1) {
            fruitChoice = "banana.png";
        }else if (select == 2) {
            fruitChoice = "melon.png";
        }else if (select == 3) {
            fruitChoice = "bomb.png";
        }

        // converting degrees to radians as libgdx works in degrees but java Math.sin works in radians
        this.angle = (float)Math.toRadians(angle);
        // loads the texture
        texture = new Texture(Gdx.files.internal("goodricke/" + fruitChoice));

        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = ((float) texture.getWidth() / (float) camera.viewportWidth) * 135f;
        // increases the sprite based on the multiplier 'size' (default=1)
        setSize((texture.getWidth() / scaleRatio) * size, (texture.getHeight() / scaleRatio) * size);

        this.x = x;
        this.y = y;

        Random rnd = new Random();
        rotation = (float)rnd.nextDouble();
    }

    /**
     * Used to render the Fruit
     *
     * @param batch The batch that is rendering the Fruit
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height / 2, width, height, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    /**
     * Gets the hit box of the fruit
     * @return hit box rectangle
     */
    public Rectangle getCollision(){
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void update(float delta) {
        rotation += 1;
        float velocity = 80 * delta;
        x += Math.sin(angle) * velocity;
        y += -Math.cos(angle) * velocity;

        // limiting x
        if (x <= -width || x >= camera.viewportWidth + width) {
            dispose();
        }

        // limiting y
        if (y <= -height || y >= camera.viewportHeight + height) {
            dispose();
        }
    }

    public void dispose() {
        texture.dispose();
        // Remove fruit object from the list of objects to be rendered
        manager.removeFruit(this);
    }

    /**
     * Destroys the fruit object
     */
    public void explode() {
        manager.removeFruit(this);
        dispose();
    }

}

