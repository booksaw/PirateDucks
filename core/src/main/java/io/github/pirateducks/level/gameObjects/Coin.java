package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;

import java.util.Random;

public class Coin extends GameObject {
    private final Texture texture;
    private final LevelManager manager;
    private final OrthographicCamera camera;
    private final Sound collectCoin;
    private static final int UNIQUESIZES = 6;
    private int size;
    private float multiplier;

    /**
     * Create a new coin
     * @param x The starting x location of the Coin
     * @param y The starting y location of the Coin
     * @param manager the LevelManager that the Coins are in
     */
    public Coin(float x, float y, LevelManager manager) {
        super(10, 10);

        this.camera = manager.getCamera();
        this.manager = manager;
        collectCoin = Gdx.audio.newSound(Gdx.files.internal("collect-coin.mp3"));
        texture = new Texture(Gdx.files.internal("GoldCoin.png")); // Change to coin texture

        Random rnd = new Random();
        size = rnd.nextInt(UNIQUESIZES);
        multiplier = (float) 1;

        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = ((float) texture.getWidth() / (float) camera.viewportWidth) * 135f;
        // increases the sprite based on the multiplier 'size' (default=1)
        setSize((texture.getWidth() / scaleRatio) * size * multiplier, (texture.getHeight() / scaleRatio) * size * multiplier);

        this.x = x;
        this.y = y;
    }

    /**
     * Used to render the Coin
     *
     * @param batch The batch that is rendering the Coin
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height / 2, width, height, 1, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public Rectangle getCollision(){
        return new Rectangle(x, y, width, height);
    }

    public void update(float delta) {

    }

    public void dispose() {
        texture.dispose();
        // Remove coin object from the list of objects to be rendered
        manager.removeCoin(this);
    }

    public void collect() {
        // Display ding animation over the coin
        manager.removeCoin(this);
        collectCoin.play();
        dispose();
    }

    public int getSize() {
        return size;
    }
}
