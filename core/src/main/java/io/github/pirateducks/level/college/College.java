package io.github.pirateducks.level.college;

import io.github.pirateducks.level.Health;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.screen.CollegeDefeatedScreen;
import io.github.pirateducks.screen.GameOverScreen;
import io.github.pirateducks.screen.Screen;

/**
 * Used to store all the methods required between all colleges
 */
public abstract class College extends LevelManager implements Health {

    private int health;
    private final MainLevel mainLevel;


    public College(MainLevel mainLevel) {
        super(mainLevel.getMainClass());
        this.mainLevel = mainLevel;
    }

    public MainLevel getLevelManager() {
        return mainLevel;
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
        getMainClass().points += 1000;
        defeated = true;
        getMainClass().setCurrentScreen(new CollegeDefeatedScreen(mainLevel, getCamera()));
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
    public abstract int getMaxHealth();


}
