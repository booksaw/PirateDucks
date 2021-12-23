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
    private final Texture fullHeartWhite, halfHeartWhite, emptyHeartWhite;
    private int currentHealth;
    private final int flashDuration = 20;
    private int currentDuration = -1;

    public HealthIndicator(Player player) {
        // giving dimensions of 0 as it does not have collision
        super(0, 0);
        this.player = player;
        currentHealth = player.getHealth();
        fullHeart = new Texture(Gdx.files.internal("Heart.png"));
        halfHeart = new Texture(Gdx.files.internal("Half_Heart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Empty_Heart.png"));

        fullHeartWhite = new Texture(Gdx.files.internal("HeartWhite.png"));
        halfHeartWhite = new Texture(Gdx.files.internal("Half_HeartWhite.png"));
        emptyHeartWhite = new Texture(Gdx.files.internal("Empty_HeartWhite.png"));
    }

    @Override
    public void render(SpriteBatch batch) {

        int x = PADDING;
        int health = player.getHealth();

        for (int rendered = 0; rendered < player.getMaxHealth(); rendered += 2) {
            int diff = health - rendered;
            if (diff <= 0) {
                batch.draw((currentDuration == -1) ? emptyHeart : emptyHeartWhite, x, PADDING, HEARTSIZE, HEARTSIZE);
            } else if (diff == 1) {
                batch.draw((currentDuration == -1) ? halfHeart : halfHeartWhite, x, PADDING, HEARTSIZE, HEARTSIZE);
            } else {
                batch.draw((currentDuration == -1) ? fullHeart : fullHeartWhite, x, PADDING, HEARTSIZE, HEARTSIZE);
            }

            x += HEARTSIZE + PADDING;
        }

    }

    @Override
    public void update(float delta) {
        int health = player.getHealth();

        if (health < currentHealth) {
            currentDuration = 0;
            currentHealth = health;
        } else if (currentDuration != -1) {
            currentDuration++;
            if (currentDuration >= flashDuration) {
                currentDuration = -1;
            }
        }
    }

    @Override
    public void dispose() {
        fullHeart.dispose();
        halfHeart.dispose();
        emptyHeart.dispose();
    }
}
