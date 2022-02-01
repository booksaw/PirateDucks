package io.github.pirateducks.level;

public abstract class GameObjectHealth extends GameObject implements Health {

    /**
     * Game object with health constructor
     * @param width Width of the game object
     * @param height Heigh of the game object
     */
    public GameObjectHealth(float width, float height) {
        super(width, height);
    }

    /**
     * Abstract method signature to get the object's maximum health
     * @return the object's maximum health
     */
    public abstract int getMaxHealth();
}
