package io.github.pirateducks.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import io.github.pirateducks.PirateDucks;

public class MainLevel extends LevelManager {

    public MainLevel(PirateDucks mainClass) {
        super(mainClass);
    }

    @Override
    protected Texture getMapTexture() {
        return new Texture("map.png");
    }

    @Override
    protected void setup(OrthographicCamera camera) {
        // nothing to do
    }


}
