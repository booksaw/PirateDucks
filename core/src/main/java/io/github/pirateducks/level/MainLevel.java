package io.github.pirateducks.level;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.college.ConstantineMemoryGame;
import io.github.pirateducks.level.college.Goodricke;
import io.github.pirateducks.level.college.Langwith;

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

    BitmapFont font;

    @Override
    protected void setup(OrthographicCamera camera) {

        font = new BitmapFont();

    }

    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        super.draw(batch, camera);

        Rectangle playerCollision = getPlayer().getCollision();

        if (playerCollision.overlaps(constantine)){
            font.draw(batch, "Press \"E\" to fight Constantine College", 250, camera.viewportHeight - 10);
        } else if(playerCollision.overlaps(goodricke)){
            font.draw(batch, "Press \"E\" to fight Goodricke College", 250, camera.viewportHeight - 10);
        } else if(playerCollision.overlaps(langwith)){
            font.draw(batch, "Press \"E\" to fight Langwith College", 250, camera.viewportHeight - 10);
        }

    }

    private final Rectangle constantine = new Rectangle(25, 40, 200, 250);
    private final Rectangle goodricke = new Rectangle(470, 35, 160, 150);
    private final Rectangle langwith = new Rectangle(310, 240, 250, 170);

    @Override
    public void update(float delta) {
        super.update(delta);
        // checking constantine
        Rectangle playerCollision = getPlayer().getCollision();

        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (playerCollision.overlaps(constantine)) {
                    getMainClass().setCurrentScreen(new ConstantineMemoryGame(this, getCamera(), this));
            } else if(playerCollision.overlaps(goodricke)){
                getMainClass().setCurrentScreen(new Goodricke(this, getCamera()));
            } else if(playerCollision.overlaps(langwith)){
                getMainClass().setCurrentScreen(new Langwith(this, getCamera(), this));
            }

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
