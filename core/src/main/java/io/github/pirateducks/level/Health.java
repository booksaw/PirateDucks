package io.github.pirateducks.level;

/**
 * Implemented by any object that has health
 */
public interface Health {

    /**
     * @return The health of that object
     */
    public int getHealth();

    /**
     * Change the health of the object
     */
    public void setHealth(int health);

}
