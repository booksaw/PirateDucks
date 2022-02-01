package io.github.pirateducks.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pirateducks.level.GameObject;
import io.github.pirateducks.level.GameObjectHealth;

public class HealthIndicator extends GameObject {

    private static final int HEARTSIZE = 20;
    private static final int PADDING = 5;

    private final GameObjectHealth object;
    private final Texture fullHeart, halfHeart, emptyHeart;
    private final Texture fullHeartWhite, halfHeartWhite, emptyHeartWhite;
    private int currentHealth;
    private final int flashDuration = 20;
    private int currentDuration = -1;
    private final OrthographicCamera camera;

    public HealthIndicator(GameObjectHealth parent, float x, float y, OrthographicCamera camera) {
        // giving dimensions of 0 as it does not have collision
        super(0, 0);
        this.camera = camera;
        this.object = parent;
        this.x = x;
        this.y = y;
        currentHealth = object.getHealth();
        fullHeart = new Texture(Gdx.files.internal("Heart.png"));
        halfHeart = new Texture(Gdx.files.internal("Half_Heart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Empty_Heart.png"));

        fullHeartWhite = new Texture(Gdx.files.internal("HeartWhite.png"));
        halfHeartWhite = new Texture(Gdx.files.internal("Half_HeartWhite.png"));
        emptyHeartWhite = new Texture(Gdx.files.internal("Empty_HeartWhite.png"));
    }

    @Override
    public void render(SpriteBatch batch) {

        float heartX = x;
        float heartY = y;
        int health = object.getHealth();

        float cameraAdditionX = camera.position.x - camera.viewportWidth / 2;
        float cameraAdditionY = camera.position.y - camera.viewportHeight / 2;

        for (int rendered = 0; rendered < object.getMaxHealth(); rendered += 2) {
            int diff = health - rendered;
            if (diff <= 0) {
                batch.draw((currentDuration == -1) ? emptyHeart : emptyHeartWhite, heartX + cameraAdditionX, y + cameraAdditionY, HEARTSIZE, HEARTSIZE);
            } else if (diff == 1) {
                batch.draw((currentDuration == -1) ? halfHeart : halfHeartWhite, heartX + cameraAdditionX, y + cameraAdditionY, HEARTSIZE, HEARTSIZE);
            } else {
                batch.draw((currentDuration == -1) ? fullHeart : fullHeartWhite, heartX + cameraAdditionX, y + cameraAdditionY, HEARTSIZE, HEARTSIZE);
            }

            heartX += HEARTSIZE + PADDING;
        }
    }

    @Override
    public void update(float delta) {
        int health = object.getHealth();

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
        fullHeartWhite.dispose();
        halfHeart.dispose();
        halfHeartWhite.dispose();
        emptyHeart.dispose();
        emptyHeart.dispose();
    }
}
