package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.college.Langwith;

import java.util.Random;

/**
 * Used to carry out all processing required for cannonballs shot by LangwithCannon class
 */
public class LangwithCannonball extends GameObject {

    private final Texture texture;
    private final Langwith manager;
    private final OrthographicCamera camera;
    private float angle;
    private float rotation = 0;
    private String fruitChoice = "apple.png";

    /**
     * Create a new cannonball
     * @param x The starting x location of the fruit
     * @param y The starting y location of the fruit
     * @param size the size of the fruit
     * @param manager the LevelManager that the fruit are in (must be goodricke)
     * @param camera the camera for the level
     * @param angle The angle that the fruit will travel at
     */
    public LangwithCannonball(float x, float y, float size, LevelManager manager, OrthographicCamera camera, float angle) {
        super(100, 100);

        this.camera = camera;

        if(!(manager instanceof  Langwith)){
            throw new IllegalArgumentException("The LangwithCannonball class can only be used during the Langwith battle");
        }
        this.manager = (Langwith) manager;


        // converting degrees to radians as libgdx works in degrees but java Math.sin works in radians
        this.angle = (float)Math.toRadians(angle);
        // loads the texture
        texture = new Texture(Gdx.files.internal("CannonBall.png"));

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
     * Used to render the Cannonball
     *
     * @param batch The batch that is rendering the Cannonball
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height / 2, width, height, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    /**
     * @return The bounding box surrounding this cannon
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

    /**
     * Used to dispose of all the textures in this class
     */
    public void dispose() {
        texture.dispose();
        // Remove fruit object from the list of objects to be rendered
        manager.removeObject(this);
    }
}
