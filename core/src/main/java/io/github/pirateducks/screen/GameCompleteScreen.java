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

public class GameCompleteScreen implements Screen{

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    private Texture headerTexture;
    private Sprite headerSprite;

    private Texture menuButtonTexture;
    private Sprite menuButtonSprite;

    private OrthographicCamera camera;
    private PirateDucks mainClass;

    public GameCompleteScreen(PirateDucks mainClass, OrthographicCamera camera) {

        this.camera = camera;
        this.mainClass = mainClass;

    }



    /**
     * Called to draw the screen
     *
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {

        // set background as blurred map
        ScreenUtils.clear(0, 0, 0.2f, 1);

        backgroundTexture = new Texture("map_blurred.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        backgroundSprite.setPosition(0, 0);

        if (backgroundSprite != null){
            backgroundSprite.draw(batch);
        }

        if (headerSprite != null){
            headerSprite.draw(batch);
        }

        if (menuButtonSprite != null){
            menuButtonSprite.draw(batch);
        }

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.5f);
        String points = "You scored " + mainClass.points + " points!";
        font.draw(batch,points,(camera.viewportWidth/2 - font.getRegion().getRegionWidth() / 2) + 25,camera.viewportHeight/2);

    }

    /**
     * Called to update the screen
     *
     * @param delta The delta time since the last update
     */
    @Override
    public void update(float delta) {

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // Scale mouse position
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
                mainClass.setCurrentScreen(new MainMenuScreen(mainClass));
            }
        }


    }

    /**
     * Called when this screen becomes the active screen
     */
    @Override
    public void startDisplaying(OrthographicCamera camera) {
        headerTexture = new Texture("gameCompletedScreen/header.png");
        headerSprite = new Sprite(headerTexture);

        float scaleRatio = (headerSprite.getWidth() / camera.viewportWidth * 1.2f);
        headerSprite.setSize(headerSprite.getWidth() / scaleRatio, headerSprite.getHeight()/scaleRatio);
        headerSprite.setPosition(camera.viewportWidth/2 - headerSprite.getWidth()/2,(camera.viewportHeight/2 - headerSprite.getHeight()/2) * 1.9f);

        menuButtonTexture = new Texture("gameCompletedScreen/menu.png");
        menuButtonSprite = new Sprite(menuButtonTexture);

        scaleRatio = (menuButtonSprite.getWidth() / camera.viewportWidth * 2.0f);
        menuButtonSprite.setSize(menuButtonSprite.getWidth() / scaleRatio, menuButtonSprite.getHeight() / scaleRatio);
        menuButtonSprite.setPosition(camera.viewportWidth/2 - menuButtonSprite.getWidth()/2,(camera.viewportHeight/2 - menuButtonSprite.getHeight()/2) * 0.5f);


    }

    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    @Override
    public void stopDisplaying() {
        backgroundTexture.dispose();
        headerTexture.dispose();
        menuButtonTexture.dispose();
    }
}
