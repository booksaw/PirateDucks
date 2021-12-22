package io.github.pirateducks.level.college;

import io.github.pirateducks.level.Health;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.screen.Screen;

/**
 * Used to store all the methods required between all colleges
 */
public abstract class College implements Screen, Health {

    private int health;
    private final LevelManager levelManager;


    public College(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
        if (health <= 0) {
            setDefeated();
        }
    }

    private boolean defeated = false;

    /**
     * Called when the college is defeated
     */
    private void setDefeated() {
        defeated = true;
    }

    /**
     * Used to check if this college has been defeated
     *
     * @return If the college has been defeated
     */
    public boolean isDefeated() {
        return defeated;
    }

    /**
     * @return The max health of the college building
     */
    public abstract int getMaxHeath();


}
