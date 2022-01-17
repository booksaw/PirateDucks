package io.github.pirateducks.level.gameObjects;

import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.college.Langwith;

public class LangwithCannon extends Cannon {

    private final Langwith manager;
    private int direction = 1;

    public LangwithCannon(float width, float height, float x, float y, LevelManager manager) {
        super(width, height, x, y, manager);

        if(!(manager instanceof  Langwith)){
            throw new IllegalArgumentException("The level manager must be an instance of Langwith");
        }
        this.manager = (Langwith) manager;

    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(angle==90f || angle==-90f){
            direction = -direction;
        }
        angle += 45f * direction;

    }

    @Override
    public void dispose() {
        super.dispose();
        // Remove cannon object from the list of objects to be rendered
        manager.removeCannon(this);
    }
}
