package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.GameObject;

public class GoldIndicator extends GameObject {

    private int gold;
    private int initialGold;
    private final PirateDucks mainClass;
    private final OrthographicCamera camera;

    BitmapFont font;

    public GoldIndicator(float x, float y, PirateDucks mainClass, OrthographicCamera camera) {
        super(0,0);

        this.mainClass = mainClass;
        this.camera = camera;

        this.x = x;
        this.y = y;

        gold = mainClass.getGold();

        font = new BitmapFont();
        font.setColor(Color.ORANGE);
    }

    @Override
    public void render(SpriteBatch batch) {
        gold = mainClass.getGold();

        float cameraAdditionX = camera.position.x - camera.viewportWidth / 2;
        float cameraAdditionY = camera.position.y - camera.viewportHeight / 2;
        font.draw(batch, String.valueOf(gold), x + cameraAdditionX, y + cameraAdditionY);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
