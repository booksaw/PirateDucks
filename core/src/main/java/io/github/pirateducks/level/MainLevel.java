package io.github.pirateducks.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.college.ConstantineMemoryGame;
import io.github.pirateducks.level.college.Goodricke;
import io.github.pirateducks.level.college.Langwith;
import io.github.pirateducks.screen.GameCompleteScreen;
import io.github.pirateducks.screen.PauseScreen;

public class MainLevel extends LevelManager {

    public Music music;
    public Music sfx_ocean;
    public boolean constantineDefeated = false, langwithDefeated = false, goodrickeDefeated = false;

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

        getPlayer().setX(800);
        getPlayer().setY(400);

        getMap().setSize(camera.viewportWidth * 2, camera.viewportHeight * 2);
    }

    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        super.draw(batch, camera);

//        getMap().draw(batch);

        Rectangle playerCollision = getPlayer().getCollision();

        if (playerCollision.overlaps(constantine) && !constantineDefeated) {
            font.draw(batch, "Press \"E\" to fight Constantine College", camera.position.x - 100, camera.position.y + 200);
        } else if (playerCollision.overlaps(goodricke) && !goodrickeDefeated) {
            font.draw(batch, "Press \"E\" to fight Goodricke College", camera.position.x - 100, camera.position.y + 200);
        } else if (playerCollision.overlaps(langwith) && !langwithDefeated) {
            font.draw(batch, "Press \"E\" to fight Langwith College", camera.position.x - 100,  camera.position.y + 200);
        }


        // code used to display a coordinated area on the screen, this was used to modify the hitboxes of locations on the map
        // leaving it here in case the map gets modified in the future
/*        batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rectLine(650, 580, 1150, 900, 5);
        shapeRenderer.end();
        batch.begin();*/

    }

    private final Rectangle langwith = new Rectangle(150, 150, 400, 450);
    private final Rectangle goodricke = new Rectangle(1140, 150, 260, 270);
    private final Rectangle constantine = new Rectangle(650, 580, 500, 320);

    @Override
    public void update(float delta) {
        super.update(delta);
        // checking constantine
        Rectangle playerCollision = getPlayer().getCollision();

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (playerCollision.overlaps(constantine) && !constantineDefeated) {
                getMainClass().setCurrentScreen(new ConstantineMemoryGame(this, getCamera(), this));
                return;
            } else if (playerCollision.overlaps(goodricke) && !goodrickeDefeated) {
                getMainClass().setCurrentScreen(new Goodricke(this, getCamera()));
                return;
            } else if (playerCollision.overlaps(langwith) && !langwithDefeated) {
                getMainClass().setCurrentScreen(new Langwith(this, getCamera()));
                return;
            }
        }

        if (goodrickeDefeated && constantineDefeated && langwithDefeated) {
            getMainClass().setCurrentScreen(new GameCompleteScreen(getMainClass(), getCamera()));
            return;
        }
        // Pause game when escape key is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.stopDisplaying();
            // Load pause screen
            getMainClass().setCurrentScreen(new PauseScreen(getMainClass(), this));
            return;
        }

        final float XLIMIT = 120;
        // updating the camera location
        float diffX = getPlayer().x - getCamera().position.x;
        if (diffX < -XLIMIT) {
            float calc = getPlayer().x + XLIMIT;
            if (calc < getCamera().viewportWidth / 2) {
                getCamera().position.x = getCamera().viewportWidth / 2;
            } else {
                getCamera().position.x = calc;
            }
        } else if (diffX > XLIMIT) {

            float calc = getPlayer().x - XLIMIT;
            if (calc > getMap().getWidth() - getCamera().viewportWidth / 2) {
                getCamera().position.x = getMap().getWidth() - getCamera().viewportWidth / 2;
            } else {
                getCamera().position.x = calc;
            }
        }

        final float YLIMIT = 80;

        float diffY = getPlayer().y - getCamera().position.y;
        if (diffY < -YLIMIT) {
            float calc = getPlayer().y + YLIMIT;
            if (calc < getCamera().viewportHeight / 2) {
                getCamera().position.y = getCamera().viewportHeight / 2;
            } else {
                getCamera().position.y = calc;
            }
        } else if (diffY > YLIMIT) {
            float calc = getPlayer().y - YLIMIT;
            if (calc > getMap().getHeight() - getCamera().viewportHeight / 2) {
                getCamera().position.y = getMap().getHeight() - getCamera().viewportHeight / 2;
            } else {
                getCamera().position.y = calc;
            }
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

    public void setGoodrickeDefeated(boolean goodrickeDefeated) {
        this.goodrickeDefeated = goodrickeDefeated;
    }

    public boolean isGoodrickeDefeated() {
        return goodrickeDefeated;
    }

    public void setLangwithDefeated(boolean langwithDefeated) {
        this.langwithDefeated = langwithDefeated;
    }

    public boolean isLangwithDefeated() {
        return langwithDefeated;
    }

}
