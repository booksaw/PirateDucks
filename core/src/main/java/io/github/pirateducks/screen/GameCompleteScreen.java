package io.github.pirateducks.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.pirateducks.PirateDucks;

/**
 * This screen is displayed when all three colleges are defeated and the player has won
 */
public class GameCompleteScreen implements Screen{

    // Initialise Textures and Sprites for graphics
    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Texture headerTexture;
    private Sprite headerSprite;
    private Texture menuButtonTexture;
    private Sprite menuButtonSprite;


    private OrthographicCamera camera;
    private PirateDucks mainClass;

    /**
     * Class constructor, called when this screen is to be displayed
     * @param mainClass MainClass which holds the main game and player data
     * @param camera Manages screen of the game
     */
    public GameCompleteScreen(PirateDucks mainClass, OrthographicCamera camera) {
        this.camera = camera;
        this.mainClass = mainClass;

    }


    /**
     * Called to draw the screen and display graphics
     * @param batch Used to draw sprites
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {

        // Set background as blurred map
        ScreenUtils.clear(0, 0, 0.2f, 1);
        backgroundTexture = new Texture("map_blurred.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        backgroundSprite.setPosition(0, 0);

        // Draw all graphics
        if (backgroundSprite != null && headerSprite != null && menuButtonSprite != null){
            backgroundSprite.draw(batch);
            headerSprite.draw(batch);
            menuButtonSprite.draw(batch);
        } else {
            throw new Error("Error displaying graphics, please restart game");
        }

        // Generate and draw text which displays number of points user obtained
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.5f);
        String points = "You scored " + mainClass.getPoints() + " points!";
        font.draw(batch,points,(camera.viewportWidth/2 - font.getRegion().getRegionWidth() / 2) + 25,camera.viewportHeight/2);

    }

    /**
     * Called to update the screen and check if buttons are pressed
     * @param delta The delta time since the last update
     */
    @Override
    public void update(float delta) {
        // User has left-clicked, check if they clicked on one of the buttons
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // Get scaled mouse position
            Vector2 scaledMouse = PirateDucks.getScaledMouseLocation(camera);

            // Set button as menu button
            Sprite button = menuButtonSprite;

            // Find button positions
            float buttonX = button.getX();
            float buttonY = button.getY();
            float buttonW = button.getWidth();
            float buttonH = button.getHeight();

            // Check if mouse is within the button positions
            if (scaledMouse.x >= buttonX && scaledMouse.x <= (buttonX + buttonW) && scaledMouse.y >= buttonY && scaledMouse.y <= (buttonY + buttonH)) {
                // User has pressed return to main menu button
                mainClass.setCurrentScreen(new MainMenuScreen(mainClass));
            }
        }


    }

    /**
     * Called when this screen becomes the active screen, this generates the graphics for the screen
     */
    @Override
    public void startDisplaying(OrthographicCamera camera) {
        // Generate header texture and sprite
        headerTexture = new Texture("gameCompletedScreen/header.png");
        headerSprite = new Sprite(headerTexture);

        // Scale and set position of header
        float scaleRatio = (headerSprite.getWidth() / camera.viewportWidth * 1.2f);
        headerSprite.setSize(headerSprite.getWidth() / scaleRatio, headerSprite.getHeight()/scaleRatio);
        headerSprite.setPosition(camera.viewportWidth/2 - headerSprite.getWidth()/2,(camera.viewportHeight/2 - headerSprite.getHeight()/2) * 1.9f);

        // Generate menu button texture and sprite
        menuButtonTexture = new Texture("gameCompletedScreen/menu.png");
        menuButtonSprite = new Sprite(menuButtonTexture);

        // Scale and set position of menu button
        scaleRatio = (menuButtonSprite.getWidth() / camera.viewportWidth * 2.0f);
        menuButtonSprite.setSize(menuButtonSprite.getWidth() / scaleRatio, menuButtonSprite.getHeight() / scaleRatio);
        menuButtonSprite.setPosition(camera.viewportWidth/2 - menuButtonSprite.getWidth()/2,(camera.viewportHeight/2 - menuButtonSprite.getHeight()/2) * 0.5f);


    }

    /**
     * Called when this screen is no longer the active screen
     * This disposes of all graphics objects which are not needed
     */
    @Override
    public void stopDisplaying() {
        backgroundTexture.dispose();
        headerTexture.dispose();
        menuButtonTexture.dispose();
    }

    @Override
    public void resume() {
        // nothing to do here
    }
}
