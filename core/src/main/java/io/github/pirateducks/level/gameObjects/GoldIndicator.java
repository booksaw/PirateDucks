package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.PirateDucks;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import javax.swing.plaf.ColorUIResource;

public class GoldIndicator extends GameObject {

    private int gold;
    private int initialGold;
    private final PirateDucks mainClass;

    BitmapFont font;

    public GoldIndicator(float x, float y, PirateDucks mainClass) {
        super(0,0);

        this.mainClass = mainClass;

        this.x = x;
        this.y = y;

        gold = mainClass.getGold();

        font = new BitmapFont();
        font.setColor(Color.ORANGE);
    }

    @Override
    public void render(SpriteBatch batch) {
        gold = mainClass.getGold();
        font.draw(batch, String.valueOf(gold), x, y);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
