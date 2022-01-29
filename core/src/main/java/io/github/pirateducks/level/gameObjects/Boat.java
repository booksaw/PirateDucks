package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.level.GameObject;

public class Boat extends GameObject {

    private Sprite sprite;

    public Boat(float width, float height) {
        super(width, height);

        Texture texture = new Texture(Gdx.files.internal("DuckBoat_TopView.png"));

        sprite = new Sprite(texture);
        sprite.setSize(width, height);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.setX(x);
        sprite.setY(y);

        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
