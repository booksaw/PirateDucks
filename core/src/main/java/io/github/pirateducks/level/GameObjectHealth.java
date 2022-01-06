package io.github.pirateducks.level;

public abstract class GameObjectHealth extends GameObject implements Health {

    public GameObjectHealth(float width, float height) {
        super(width, height);
    }

    public abstract int getMaxHealth();
}
