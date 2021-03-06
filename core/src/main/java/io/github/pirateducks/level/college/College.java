package io.github.pirateducks.level.college;

import io.github.pirateducks.level.Health;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.screen.CollegeDefeatedScreen;

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
     * Called when a college is defeated
     */
    private void setDefeated() {
        getMainClass().addPoints(1000);
        defeated = true;
        getMainClass().setCurrentScreen(new CollegeDefeatedScreen(mainLevel, getCamera()));

        // Add an extra heart when a college is defeated
        mainLevel.getMainClass().setPlayerHealth(mainLevel.getMainClass().getPlayerHealth() + 2);
        getLevelManager().getPlayer().setHealth(getPlayer().getHealth() + 2);

        if(this instanceof Goodricke){
            getLevelManager().setGoodrickeDefeated(true);
        } else if(this instanceof ConstantineMemoryGame){
            getLevelManager().setConstantineDefeated(true);
        } else if(this instanceof Langwith){
            getLevelManager().setLangwithDefeated(true);
        }
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
