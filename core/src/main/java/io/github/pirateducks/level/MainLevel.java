package io.github.pirateducks.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.college.ConstantineMemoryGame;
import io.github.pirateducks.level.college.Goodricke;
import io.github.pirateducks.level.college.Langwith;
import io.github.pirateducks.screen.GameCompleteScreen;

public class MainLevel extends LevelManager {

    public Music music;
    public Music sfx_ocean;
    public boolean constantineDefeated = false, langwithDefeated = false, goodrikeDefeated = false;

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

        // Sets the background music
        music = Gdx.audio.newMusic(Gdx.files.internal("Main_Theme.ogg"));
        music.setLooping(true);
        if (getMainClass().musicOn) {
            music.setVolume(0.15f);
        } else {
            music.setVolume(0);
        }
        music.play();

        // Sets the ocean sounds
        sfx_ocean = Gdx.audio.newMusic(Gdx.files.internal("Ocean.ogg"));
        sfx_ocean.setLooping(true);
        sfx_ocean.setVolume(0.005f);
        sfx_ocean.play();
    }

    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        super.draw(batch, camera);

        Rectangle playerCollision = getPlayer().getCollision();

        if (playerCollision.overlaps(constantine) && !constantineDefeated) {
            font.draw(batch, "Press \"E\" to fight Constantine College", 250, camera.viewportHeight - 10);
        } else if (playerCollision.overlaps(goodricke) && !goodrikeDefeated) {
            font.draw(batch, "Press \"E\" to fight Goodricke College", 250, camera.viewportHeight - 10);
        } else if (playerCollision.overlaps(langwith) && !langwithDefeated) {
            font.draw(batch, "Press \"E\" to fight Langwith College", 250, camera.viewportHeight - 10);
        }

    }

    private final Rectangle langwith = new Rectangle(25, 40, 200, 250);
    private final Rectangle goodricke = new Rectangle(470, 35, 160, 150);
    private final Rectangle constantine = new Rectangle(310, 240, 250, 170);

    @Override
    public void update(float delta) {
        super.update(delta);
        // checking constantine
        Rectangle playerCollision = getPlayer().getCollision();

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (playerCollision.overlaps(constantine) && !constantineDefeated) {
                getMainClass().setCurrentScreen(new ConstantineMemoryGame(this, getCamera(), this));
            } else if (playerCollision.overlaps(goodricke) && !goodrikeDefeated) {
                getMainClass().setCurrentScreen(new Goodricke(this, getCamera()));
            } else if (playerCollision.overlaps(langwith) && !langwithDefeated) {
                getMainClass().setCurrentScreen(new Langwith(this, getCamera()));
            }
        }

        // Check if left mouse button is clicked
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // Scale mouse position
            Vector2 scaledMouse = PirateDucks.getScaledMouseLocation(getCamera());

            // Set button as mute button
            Sprite button = muteButtonSprite;

            // Find button positions
            float buttonX = button.getX();
            float buttonY = button.getY();
            float buttonW = button.getWidth();
            float buttonH = button.getHeight();

            // Check if mouse is within the button positions
            if (scaledMouse.x >= buttonX && scaledMouse.x <= (buttonX + buttonW) && scaledMouse.y >= buttonY && scaledMouse.y <= (buttonY + buttonH)) {
                if (musicVolume) {
                    music.setVolume(0);
                    musicVolume = false;
                } else {
                    music.setVolume(0.15f);
                    musicVolume = true;
                }
            }
        }

        if (goodrikeDefeated && constantineDefeated && langwithDefeated){
          getMainClass().setCurrentScreen(new GameCompleteScreen(getMainClass(),getCamera()));
        }
    }

    @Override
    public void stopDisplaying() {
        music.dispose();
        sfx_ocean.dispose();
    }

    public void setConstantineDefeated(boolean constantineDefeated) {
        this.constantineDefeated = constantineDefeated;
    }

    public boolean isConstantineDefeated() {
        return constantineDefeated;
    }

    public void setGoodrikeDefeated(boolean goodrikeDefeated) {
        this.goodrikeDefeated = goodrikeDefeated;
    }

    public boolean isGoodrikeDefeated() {
        return goodrikeDefeated;
    }

    public void setLangwithDefeated(boolean langwithDefeated) {
        this.langwithDefeated = langwithDefeated;
    }

    public boolean isLangwithDefeated() {
        return langwithDefeated;
    }

}
