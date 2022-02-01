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


public class VictoryScreen implements Screen {

    private final Array<Sprite> buttons = new Array<>();
    private Sprite gameOverSprite;
    private final OrthographicCamera camera;


    public VictoryScreen(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Called to draw the screen
     *
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        if (gameOverSprite != null) {
            gameOverSprite.draw(batch);
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
    @Override
    public void update(float delta) {
        // If user left-clicks the screen
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
        {
            // Stops multiple buttons being pressed in the loop
            boolean buttonPressed = false;
            // Loop through all buttons and see if one was clicked
            for (int i = 0; i < buttons.size; i++) {
                Sprite button = buttons.get(i);

                // getting the locaiton of the mouse in game coords
                Vector2 scaledMouse = PirateDucks.getScaledMouseLocation(camera);

                // Check if mouse position is inside button when clicked
                if (scaledMouse.x >= button.getX() && scaledMouse.x <= button.getX() + button.getWidth() &&
                        scaledMouse.y >= button.getY() && scaledMouse.y <= button.getY() + button.getHeight())
                {
                    if (i == 0) {
                        System.out.println("Menu Button Pressed");
                        buttonPressed = true;
                    }
                    else if (i == 1 && !buttonPressed) {
                        // Exit the application
                        Gdx.app.exit();
                        System.exit(0);
                    }

                }
            }
        }
    }

    /**
     * Called when this screen becomes the active screen
     */
    @Override
    public void startDisplaying(OrthographicCamera camera) {
        // Creates a sprite with the Game over title texture
        Texture texture = new Texture("victoryScreen/YouWinScreen.png");
        gameOverSprite = new Sprite(texture);

        // scales the sprite depending on window size multiplied by a constant
        float scaleRatio = (gameOverSprite.getWidth() / Gdx.graphics.getWidth()) * 1.5f;
        gameOverSprite.setSize(gameOverSprite.getWidth() / scaleRatio, gameOverSprite.getHeight() / scaleRatio);

        // Centers the Game Over sprite
        gameOverSprite.setPosition(camera.viewportWidth/2 - gameOverSprite.getWidth()/2, (camera.viewportHeight/2 - gameOverSprite.getHeight()/2) * 1.6f);

        addButtons(camera);
    }

    /**
     * Called to add return to menu button and quit button to screen
     * @param camera
     */
    private void addButtons(OrthographicCamera camera) {
        // Add a return to menu button
        Texture texture = new Texture("victoryScreen/buttons/VictoryScreenMenuButton.png");
        // Create sprite from texture
        Sprite button = new Sprite(texture);
        // scales the sprite based on window size multiplied by a constant since textures will be different size images
        float scaleRatio = (button.getWidth() / Gdx.graphics.getWidth()) * 3.5f;
        button.setSize(button.getWidth() / scaleRatio, button.getHeight() / scaleRatio);
        // Position the buttons with their x centered and under each other using an offset that's scaled with the window size
        int offset = -10;
        button.setPosition(camera.viewportWidth/2 - button.getWidth()/2, (camera.viewportHeight/2 - button.getHeight()/2) + (offset / scaleRatio));
        buttons.add(button);

        // Add a quit button
        texture = new Texture("victoryScreen/buttons/VictoryScreenQuitButton.png");
        button = new Sprite(texture);
        scaleRatio = (button.getWidth() / Gdx.graphics.getWidth()) * 9f;
        offset -= 20;
        button.setSize(button.getWidth() / scaleRatio, button.getHeight() / scaleRatio);
        button.setPosition(camera.viewportWidth/2 - button.getWidth()/2, (camera.viewportHeight/2 - button.getHeight()/2) + (offset / scaleRatio));
        buttons.add(button);
    }

    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    @Override
    public void stopDisplaying() {
        buttons.clear();
    }
}
