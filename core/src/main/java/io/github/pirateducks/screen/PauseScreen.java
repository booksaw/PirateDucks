package io.github.pirateducks.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.LevelManager;
import io.github.pirateducks.level.MainLevel;

/**
 * This screen is displayed when the pause button is pressed,
 * The buttons continue, quit, and mute music are available on this screen
 */
public class PauseScreen implements Screen  {
    // Initialise array to store button sprites
    private final Array<Sprite> buttons = new Array<>();

    // Initialise textures and sprites
    private Texture pauseTextTexture;
    private Sprite pauseTextSprite;
    private Texture continueButtonTexture;
    private Sprite continueButtonSprite;
    private Texture quitButtonTexture;
    private Sprite quitButtonSprite;
    private PirateDucks mainClass;
    private Screen prevScreen;
    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Texture muteButtonTextureOn;
    private Texture muteButtonTextureOff;
    private Sprite muteButtonSprite;

    /**
     * Class constructor, called when this screen becomes active
     * @param mainClass Holds the main game and player data
     * @param prevScreen Screen to which to return to if the user wants to continue rather than quit
     */
    public PauseScreen(PirateDucks mainClass, Screen prevScreen){
        this.mainClass = mainClass;
        this.prevScreen = prevScreen;
    }

    /**
     * Called to draw the screen and graphics
     * @param batch Used to draw sprites
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        // set background as blurred map
        ScreenUtils.clear(0, 0, 0.2f, 1);
        backgroundTexture = new Texture("map_blurred.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        backgroundSprite.setPosition(0, 0);

        if (backgroundSprite != null && pauseTextSprite != null){
            backgroundSprite.draw(batch);
            pauseTextSprite.draw(batch);
        } else {
            throw new Error("Error drawing graphics, please restart game");
        }


        for (Sprite button : buttons){
            if (button != null){
                button.draw(batch);
            } else {
                throw new Error("Error drawing graphics, please restart game");
            }
        }
    }

    /**
     * Called to update the screen and check for key presses
     * @param delta The delta time since the last update
     */
    public void update(float delta){
        // Check if user has left-clicked
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){

            // User has left-clicked, so check each button to see if it has been clicked
            for (int i=0;i<buttons.size;i++){
                Sprite button = buttons.get(i);

                // getting the location of the mouse
                Vector2 scaledMouse = PirateDucks.getScaledMouseLocation(mainClass.getCamera());

                // Get button position information
                float buttonX = button.getX();
                float buttonY = button.getY();
                float buttonW = button.getWidth();
                float buttonH = button.getHeight();

                // Check if mouse within button boundary

                if (scaledMouse.x >= buttonX && scaledMouse.x <= (buttonX + buttonW) && scaledMouse.y >= buttonY && scaledMouse.y <= (buttonY + buttonH)){
                    if (i == 0){
                        // User has pressed continue, return to previous screen
                        mainClass.setCurrentScreen(prevScreen, false);

                    } else if (i == 1){
                        // User has pressed quit, so return to main menu
                        mainClass.setCurrentScreen(new MainMenuScreen(mainClass));
                    } else if (i == 2){
                        // User has pressed the music button, so switch music on or off.
                        if (mainClass.musicOn) {
                            mainClass.musicOn = false;
                            muteButtonSprite.setTexture(muteButtonTextureOff);
                        } else {
                            mainClass.musicOn = true;
                            muteButtonSprite.setTexture(muteButtonTextureOn);
                        }

                    }
                }
            }
        }
        // Return to game when escape key is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            mainClass.setCurrentScreen(prevScreen, false);
        }
    }

    /**
     * Called when this screen becomes the active screen to generate graphics
     */
    public void startDisplaying(OrthographicCamera camera){

        // Generate and scale header text
        pauseTextTexture = new Texture("pauseScreen/pausedText.png");
        pauseTextSprite = new Sprite(pauseTextTexture);

        float scaleRatio = (pauseTextSprite.getWidth() / camera.viewportWidth * 1.8f);
        pauseTextSprite.setSize(pauseTextSprite.getWidth() / scaleRatio, pauseTextSprite.getHeight()/scaleRatio);
        pauseTextSprite.setPosition(camera.viewportWidth/2 - pauseTextSprite.getWidth()/2,(camera.viewportHeight/2 - pauseTextSprite.getHeight()/2) * 1.5f);

        //Buttons

        // Generate and scale continue button
        continueButtonTexture = new Texture("pauseScreen/continue.png");
        continueButtonSprite = new Sprite(continueButtonTexture);
        scaleRatio = buttonScaleRatio(continueButtonSprite, camera);
        continueButtonSprite.setSize(continueButtonSprite.getWidth()/scaleRatio,continueButtonSprite.getHeight()/scaleRatio);

        int offset = -10; // Used to put but buttons below one another

        continueButtonSprite.setPosition(camera.viewportWidth/2 - continueButtonSprite.getWidth()/2,(camera.viewportHeight/2-continueButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(continueButtonSprite);


        // Generate and scale quit button
        quitButtonTexture = new Texture("pauseScreen/quit.png");
        quitButtonSprite = new Sprite(quitButtonTexture);
        scaleRatio = buttonScaleRatio(quitButtonSprite, camera);
        quitButtonSprite.setSize(quitButtonSprite.getWidth()/scaleRatio,quitButtonSprite.getHeight()/scaleRatio);

        offset -= 20; // Put quit button below continue button
        quitButtonSprite.setPosition(camera.viewportWidth/2 - quitButtonSprite.getWidth()/2,(camera.viewportHeight/2-quitButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(quitButtonSprite);

        // Mute music button
        // Creates two textures, one for music on, one for music off
        muteButtonTextureOn = new Texture("pauseScreen/music-on.png");
        muteButtonTextureOff = new Texture("pauseScreen/music-off.png");

        // Sets texture based on whether the music is on/off
        if (mainClass.musicOn){
            muteButtonSprite = new Sprite(muteButtonTextureOn);
        } else {
            muteButtonSprite = new Sprite(muteButtonTextureOff);
        }

        muteButtonSprite.setSize((muteButtonSprite.getWidth() / scaleRatio) / 3, (muteButtonSprite.getHeight() / scaleRatio) / 3);
        muteButtonSprite.setPosition(camera.viewportWidth / 2 - muteButtonSprite.getWidth() / 2 + 395, (camera.viewportHeight / 2 - muteButtonSprite.getHeight() / 2) * 2.2f - 45);
        buttons.add(muteButtonSprite);

    }

    /**
     * Used to calculate the scale ratio of a button
     * @param button The button to calculate the scale ratio of
     * @param camera Manages the screen, used to get screen size information
     * @return float of scale ratio
     */
    private float buttonScaleRatio(Sprite button, OrthographicCamera camera){
        float scaleRatio = (button.getWidth()/camera.viewportWidth) * 3.5f;
        return scaleRatio;
    }


    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    public void stopDisplaying(){
        continueButtonTexture.dispose();
        quitButtonTexture.dispose();
        muteButtonTextureOn.dispose();
        muteButtonTextureOff.dispose();
    }

}
