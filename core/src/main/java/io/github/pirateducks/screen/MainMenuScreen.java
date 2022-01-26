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


public class MainMenuScreen implements Screen {

    private final Array<Sprite> buttons = new Array<>();

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    private Texture gameLogoTexture;
    private Sprite gameLogoSprite;

    private Texture startGameButtonTexture;
    private Sprite startGameButtonSprite;


    private Texture quitButtonTexture;
    private Sprite quitButtonSprite;

    private final PirateDucks mainClass;

    public MainMenuScreen(PirateDucks mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * Called to draw the screen
     *
     * @param batch
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        // set background as blurred map
        backgroundSprite.draw(batch);

        if (gameLogoSprite != null) {
            gameLogoSprite.draw(batch);
        }

        for (Sprite button : buttons) {
            if (button != null) {
                button.draw(batch);
            }
        }
    }

    /**
     * Called to update the screen
     *
     * @param delta The delta time since the last update
     */
    public void update(float delta) {

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // We need to ensure multiple buttons are not pressed at once
            boolean buttonPressed = false;

            for (int i = 0; i < buttons.size; i++) {
                Sprite button = buttons.get(i);

                // Get scaled mouse position
                Vector2 scaledMouse = PirateDucks.getScaledMouseLocation(mainClass.getCamera());


                // Check if mouse position is inside button when clicked
                float buttonX = button.getX();
                float buttonY = button.getY();
                float buttonW = button.getWidth();
                float buttonH = button.getHeight();

                if (scaledMouse.x >= buttonX && scaledMouse.x <= (buttonX + buttonW) && scaledMouse.y >= buttonY && scaledMouse.y <= (buttonY + buttonH)) {
                    if (i == 0) {
                        buttonPressed = true;
                        mainClass.setCurrentScreen(new MainLevel(mainClass));
                        this.stopDisplaying();

                    } else if (i == 1) {
                        Gdx.app.exit();
                    }
                }
            }
        }
    }

    /**
     * Called when this screen becomes the active screen
     */
    public void startDisplaying(OrthographicCamera camera) {
        // Create game logo sprite
        gameLogoTexture = new Texture("logo.png");
        gameLogoSprite = new Sprite(gameLogoTexture);

        backgroundTexture = new Texture("map_blurred.png");
        backgroundSprite = new Sprite(backgroundTexture);
        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = (gameLogoTexture.getWidth() / camera.viewportWidth) * 1.2f;
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        // Centers the sprite
        backgroundSprite.setPosition(0, 0);


        // Scale logo depending on window size
        scaleRatio = (gameLogoSprite.getWidth() / camera.viewportWidth) * 1.2f;
        gameLogoSprite.setSize(gameLogoSprite.getWidth() / scaleRatio, gameLogoSprite.getHeight() / scaleRatio);

        //Centre the logo
        gameLogoSprite.setPosition(camera.viewportWidth / 2 - gameLogoSprite.getWidth() / 2, (camera.viewportHeight / 2 - gameLogoSprite.getHeight() / 2) * 2.2f);

        //Buttons

        // Start Game Button
        startGameButtonTexture = new Texture("mainMenuScreen/start.png");
        startGameButtonSprite = new Sprite(startGameButtonTexture);
        scaleRatio = buttonScaleRatio(startGameButtonSprite, camera);
        startGameButtonSprite.setSize(startGameButtonSprite.getWidth() / scaleRatio, startGameButtonSprite.getHeight() / scaleRatio);

        int offset = 0;

        startGameButtonSprite.setPosition(camera.viewportWidth / 2 - startGameButtonSprite.getWidth() / 2, (camera.viewportHeight / 2 - startGameButtonSprite.getHeight() / 2) + (offset / scaleRatio));
        buttons.add(startGameButtonSprite);

        // Settings Button
        /*
        settingsButtonTexture = new Texture("mainMenuScreen/settings.png");
        settingsButtonSprite = new Sprite(settingsButtonTexture);
        scaleRatio = buttonScaleRatio(settingsButtonSprite, camera);
        settingsButtonSprite.setSize(settingsButtonSprite.getWidth()/scaleRatio,settingsButtonSprite.getHeight()/scaleRatio);

        offset -= 20;

        settingsButtonSprite.setPosition(camera.viewportWidth/2 - settingsButtonSprite.getWidth()/2,(camera.viewportHeight/2-settingsButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(settingsButtonSprite);
        */

        // Quit button
        quitButtonTexture = new Texture("mainMenuScreen/quit.png");
        quitButtonSprite = new Sprite(quitButtonTexture);
        scaleRatio = buttonScaleRatio(quitButtonSprite, camera);
        quitButtonSprite.setSize(quitButtonSprite.getWidth() / scaleRatio, quitButtonSprite.getHeight() / scaleRatio);

        offset -= 20;
        quitButtonSprite.setPosition(camera.viewportWidth / 2 - quitButtonSprite.getWidth() / 2, (camera.viewportHeight / 2 - quitButtonSprite.getHeight() / 2) + (offset / scaleRatio));
        buttons.add(quitButtonSprite);
    }

    private float buttonScaleRatio(Sprite button, OrthographicCamera camera) {
        float scaleRatio = (button.getWidth() / camera.viewportWidth) * 3.5f;
        return scaleRatio;
    }


    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    public void stopDisplaying() {
        gameLogoTexture.dispose();
        startGameButtonTexture.dispose();
        // settingsButtonTexture.dispose();
        quitButtonTexture.dispose();
        backgroundTexture.dispose();
    }

}
