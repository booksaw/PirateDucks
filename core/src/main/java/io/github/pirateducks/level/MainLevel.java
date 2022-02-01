package io.github.pirateducks.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.college.ConstantineMemoryGame;
import io.github.pirateducks.level.college.Goodricke;
import io.github.pirateducks.level.college.Langwith;
import io.github.pirateducks.level.gameObjects.Boat;
import io.github.pirateducks.pathfinding.PathFinder;
import io.github.pirateducks.screen.GameCompleteScreen;
import io.github.pirateducks.screen.PauseScreen;

/**
 * This class is the main level of the game
 */
public class MainLevel extends LevelManager {

    public Music music;
    public Music sfx_ocean;
    public boolean constantineDefeated = false, langwithDefeated = false, goodrickeDefeated = false;
    private int tutorialStage = 0;
    private Texture[] tutorialTexture;
    private Sprite[] tutorials;
    private Texture fire;

    /**
     * Class constructor, called when starting the main level
     * @param mainClass main game class
     */
    public MainLevel(PirateDucks mainClass) {

        super(mainClass);

        // Stores tutorial textures & sprites in arrays
        tutorialTexture = new Texture[]{new Texture("controls.png"), new Texture("gameTutorial.png")};
        tutorials = new Sprite[]{new Sprite(tutorialTexture[0]), new Sprite(tutorialTexture[1])};
        fire = new Texture("fire.png");
    }

    @Override
    protected Texture getMapTexture() {
        return new Texture("map.png");
    }

    BitmapFont font;

    private PathFinder pathFinder;

    @Override
    protected void setup(OrthographicCamera camera) {
        System.out.println("setup is being called");
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

        pathFinder = new PathFinder(this, 10);

        // generating AI boats
        for(int i = 0; i < 5; i++) {
            addObject(new Boat(35, 55, this));
        }
    }

    int fireFrame = 0;
    int updateCount = 0;

    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        super.draw(batch, camera);

        Rectangle playerCollision = getPlayer().getCollision();

        if (playerCollision.overlaps(constantine) && !constantineDefeated) {
            font.draw(batch, "Press \"E\" to fight Constantine College", camera.position.x - 100, camera.position.y + 200);
        } else if (playerCollision.overlaps(goodricke) && !goodrickeDefeated) {
            font.draw(batch, "Press \"E\" to fight Goodricke College", camera.position.x - 100, camera.position.y + 200);
        } else if (playerCollision.overlaps(langwith) && !langwithDefeated) {
            font.draw(batch, "Press \"E\" to fight Langwith College", camera.position.x - 100,  camera.position.y + 200);
        }

        // Shows 1 of 2 possible tutorial screens
        if (tutorialStage < 2) {
            tutorials[tutorialStage].draw(batch);
        }

        // displaying fire if each college is defeated
        int frameWidth = fire.getWidth() / 3;
        if(constantineDefeated){
            batch.draw(fire, 800, 680, 150, 240, frameWidth * fireFrame, 0, frameWidth, fire.getHeight(), false,  false);
        }
        if(goodrickeDefeated){
            batch.draw(fire, 1230, 200, 150, 240, frameWidth * fireFrame, 0, frameWidth, fire.getHeight(), false,  false);
        }
        if(langwithDefeated){
            batch.draw(fire, 260, 260, 190, 290, frameWidth * fireFrame, 0, frameWidth, fire.getHeight(), false,  false);
        }
    }

    // Creates a hit box for each college island
    private final Rectangle langwith = new Rectangle(150, 150, 400, 450);
    private final Rectangle goodricke = new Rectangle(1140, 150, 260, 270);
    private final Rectangle constantine = new Rectangle(650, 580, 500, 320);

    @Override
    public void update(float delta) {
        super.update(delta);

        updateCount++;
        if(updateCount > 20){
            updateCount = 0;
            fireFrame = (++fireFrame) % 3;
        }

        // gets players hit box
        Rectangle playerCollision = getPlayer().getCollision();

        // Space bar removes changes tutorial screen
        if (tutorialStage < 2) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                tutorialTexture[tutorialStage].dispose();
                tutorialStage++;
            }
        }

        // Center tutorial in center of screen
        if (tutorialStage < 2) {
            tutorials[0].setPosition(getCamera().position.x - (tutorials[0].getWidth() / 2), getCamera().position.y - (tutorials[0].getHeight() / 2));
            // scales the sprite depending on window size divided by a constant
            tutorials[0].setSize(getCamera().viewportWidth / 1.7f, getCamera().viewportHeight / 1.7f);

            tutorials[1].setPosition(getCamera().position.x - (tutorials[1].getWidth() / 2), getCamera().position.y - (tutorials[1].getHeight() / 2));
            tutorials[1].setSize(getCamera().viewportWidth / 1.7f, getCamera().viewportHeight / 1.7f);
        }

        // Press E to fight a college when player is next to a college island
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (playerCollision.overlaps(constantine) && !constantineDefeated) {
                getMainClass().setCurrentScreen(new ConstantineMemoryGame(this, getCamera()));
                return;
            } else if (playerCollision.overlaps(goodricke) && !goodrickeDefeated) {
                getMainClass().setCurrentScreen(new Goodricke(this, getCamera()));
                return;
            } else if (playerCollision.overlaps(langwith) && !langwithDefeated) {
                getMainClass().setCurrentScreen(new Langwith(this, getCamera()));
                return;
            }
        }

        // Game complete if all colleges defeated
        if (goodrickeDefeated && constantineDefeated && langwithDefeated) {
            getMainClass().setCurrentScreen(new GameCompleteScreen(getMainClass(), getCamera()));
            return;
        }
        // Pause game when escape key is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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

    public PathFinder getPathFinder() {
        return pathFinder;
    }
}
