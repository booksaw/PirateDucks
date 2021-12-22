package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.level.GameObject;

public class HealthIndicator extends GameObject {

    private static final int HEARTSIZE = 20;
    private static final int PADDING = 5;

    private final Player player;
    private final Texture fullHeart, halfHeart, emptyHeart;

    public HealthIndicator(Player player) {
        // giving dimensions of 0 as it does not have collision
        super(0, 0);
        this.player = player;

        fullHeart = new Texture(Gdx.files.internal("Heart.png"));
        halfHeart = new Texture(Gdx.files.internal("Half_Heart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Empty_Heart.png"));
    }

    @Override
    public void render(SpriteBatch batch) {

        int x = PADDING;
        int health = player.getHealth();
        for (int rendered = 0; rendered < player.getMaxHealth(); rendered += 2) {
            int diff = health - rendered;
            if (diff <= 0) {
                batch.draw(emptyHeart, x, PADDING, HEARTSIZE, HEARTSIZE);
            } else if (diff == 1) {
                batch.draw(halfHeart, x, PADDING, HEARTSIZE, HEARTSIZE);
            } else {
                batch.draw(fullHeart, x, PADDING, HEARTSIZE, HEARTSIZE);
            }

            x += HEARTSIZE + PADDING;
        }

    }

    @Override
    public void update(float delta) {
        // nothing to do here
    }

    @Override
    public void dispose() {
        fullHeart.dispose();
        halfHeart.dispose();
        emptyHeart.dispose();
    }
}
