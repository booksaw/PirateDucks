package io.github.pirateducks.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;

/**
 * This screen displays the main menu,
 * The buttons start game button and a quit game button
 */
public class MainMenuScreen implements Screen {

    // Initialise array to store buttons
    private final Array<Sprite> buttons = new Array<>();

    // Initialise Textures and Sprites
    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Texture gameLogoTexture;
    private Sprite gameLogoSprite;
    private Texture startGameButtonTexture;
    private Sprite startGameButtonSprite;
    private Texture quitButtonTexture;
    private Sprite quitButtonSprite;

    private final PirateDucks mainClass;

    /**
     * Constructor called to create the main menu screen
     * @param mainClass the main class which holds the main game and player data
     */
    public MainMenuScreen(PirateDucks mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * Called to draw the screen and display all graphics
     *
     * @param batch
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera) {

        // set Background
        ScreenUtils.clear(0, 0, 0.2f, 1);
        backgroundSprite.draw(batch);

        // Draw game logo header
        if (gameLogoSprite != null) {
            gameLogoSprite.draw(batch);
        }

        // Draw all buttons
        for (Sprite button : buttons) {
            if (button != null) {
                button.draw(batch);
            }
        }
    }

    /**
     * Called to update the screen and check if keys or buttons pressed
     *
     * @param delta The delta time since the last update
     */
    public void update(float delta) {

        // Check if user has left-clicked
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // Go through each button and see if it is clicked
            for (int i = 0; i < buttons.size; i++) {
                Sprite button = buttons.get(i);

                // Get scaled mouse position
                Vector2 scaledMouse = PirateDucks.getScaledMouseLocation(mainClass.getCamera());

                // Get button position
                float buttonX = button.getX();
                float buttonY = button.getY();
                float buttonW = button.getWidth();
                float buttonH = button.getHeight();

                // Check if user clicked within area of button
                if (scaledMouse.x >= buttonX && scaledMouse.x <= (buttonX + buttonW) && scaledMouse.y >= buttonY && scaledMouse.y <= (buttonY + buttonH)) {
                    if (i == 0) {
                        // User has pressed start game, so start a new game
                        mainClass.setCurrentScreen(new MainLevel(mainClass));
                        this.stopDisplaying();
                    } else if (i == 1) {
                        // User has pressed the quit button, so close the game
                        Gdx.app.exit();
                    }
                }
            }
        }
    }

    /**
     * Called when this screen becomes the active screen to generate graphics
     */
    public void startDisplaying(OrthographicCamera camera) {

        // Generate background texture and sprite
        backgroundTexture = new Texture("map_blurred.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        backgroundSprite.setPosition(0, 0);

        // Generate game logo texture and sprite
        gameLogoTexture = new Texture("logo.png");
        gameLogoSprite = new Sprite(gameLogoTexture);
        // Scale and centre logo depending on window size
        float scaleRatio = (gameLogoSprite.getWidth() / camera.viewportWidth) * 1.2f;
        gameLogoSprite.setSize(gameLogoSprite.getWidth() / scaleRatio, gameLogoSprite.getHeight() / scaleRatio);
        gameLogoSprite.setPosition(camera.viewportWidth / 2 - gameLogoSprite.getWidth() / 2, (camera.viewportHeight / 2 - gameLogoSprite.getHeight() / 2) * 2.2f);

        //Buttons

        // Generate and scale start game button
        startGameButtonTexture = new Texture("mainMenuScreen/start.png");
        startGameButtonSprite = new Sprite(startGameButtonTexture);
        scaleRatio = buttonScaleRatio(startGameButtonSprite, camera);
        startGameButtonSprite.setSize(startGameButtonSprite.getWidth() / scaleRatio, startGameButtonSprite.getHeight() / scaleRatio);

        int offset = 0; // Used to position buttons underneath one another
        startGameButtonSprite.setPosition(camera.viewportWidth / 2 - startGameButtonSprite.getWidth() / 2, (camera.viewportHeight / 2 - startGameButtonSprite.getHeight() / 2) + (offset / scaleRatio));
        buttons.add(startGameButtonSprite);

        // Generate and scale quit button
        quitButtonTexture = new Texture("mainMenuScreen/quit.png");
        quitButtonSprite = new Sprite(quitButtonTexture);
        scaleRatio = buttonScaleRatio(quitButtonSprite, camera);
        quitButtonSprite.setSize(quitButtonSprite.getWidth() / scaleRatio, quitButtonSprite.getHeight() / scaleRatio);
        offset -= 20; // Put quit button below start game button
        quitButtonSprite.setPosition(camera.viewportWidth / 2 - quitButtonSprite.getWidth() / 2, (camera.viewportHeight / 2 - quitButtonSprite.getHeight() / 2) + (offset / scaleRatio));
        buttons.add(quitButtonSprite);
    }

    /**
     * Used to calculate the scale ratio of a button
     * @param button The button to calculate the scale ratio of
     * @param camera Manages the screen, used to get screen size information
     * @return float of scale ratio
     */
    private float buttonScaleRatio(Sprite button, OrthographicCamera camera) {
        float scaleRatio = (button.getWidth() / camera.viewportWidth) * 3.5f;
        return scaleRatio;
    }


    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything as they are no longer needed
     */
    public void stopDisplaying() {
        gameLogoTexture.dispose();
        startGameButtonTexture.dispose();
        quitButtonTexture.dispose();
        backgroundTexture.dispose();
    }

}
