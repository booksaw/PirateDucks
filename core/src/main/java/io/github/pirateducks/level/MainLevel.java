package io.github.pirateducks.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import io.github.pirateducks.PirateDucks;

public class MainLevel extends LevelManager {

    public Music music;
    public Music sfx_ocean;

    public MainLevel(PirateDucks mainClass) {
        super(mainClass);
    }

    @Override
    protected Texture getMapTexture() {
        return new Texture("map.png");
    }

    @Override
    protected void setup(OrthographicCamera camera) {
        //Sets the background music
        music = Gdx.audio.newMusic(Gdx.files.internal("Main_Theme.ogg"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        sfx_ocean = Gdx.audio.newMusic(Gdx.files.internal("Ocean.ogg"));
        sfx_ocean.setLooping(true);
        sfx_ocean.setVolume(0.2f);
        sfx_ocean.play();
    }

    @Override
    public void stopDisplaying() {
        music.dispose();
        sfx_ocean.dispose();
    }
}
