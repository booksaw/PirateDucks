package com.mygdx.game.screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenuScreen implements Screen  {

    private final Array<Sprite> buttons = new Array<>();

    private Texture gameLogoTexture;
    private Sprite gameLogoSprite;

    private Texture startGameButtonTexture;
    private Sprite startGameButtonSprite;

    private Texture settingsButtonTexture;
    private Sprite settingsButtonSprite;

    private Texture quitButtonTexture;
    private Sprite quitButtonSprite;


    /**
     * Called to draw the screen
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        // Plain white background for now
        ScreenUtils.clear(1, 1, 1, 1);

        if (gameLogoSprite != null){
            gameLogoSprite.draw(batch);
        }

        for (Sprite button : buttons){
            if (button != null){
                button.draw(batch);
            }
        }
    }

    /**
     * Called to update the screen
     * @param delta The delta time since the last update
     */
    public void update(float delta){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            // We need to ensure multiple buttons are not pressed at once
            boolean buttonPressed = false;

            for (int i=0;i<buttons.size;i++){
                Sprite button = buttons.get(i);

                // As mouse position coordinates start in top left whereas game coordinates start in bottom left
                // we need to inverse them
                int x = Gdx.graphics.getWidth() - Gdx.input.getX();
                int y = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Check if mouse position is inside button when clicked
                float buttonX = button.getX();
                float buttonY = button.getY();
                float buttonW = button.getWidth();
                float buttonH = button.getHeight();

                if (x >= buttonX && x<= (buttonX + buttonW) && y >= buttonY && y <= (buttonY + buttonH)){
                    if (i == 0){
                        System.out.println("Start game button pressed");
                        buttonPressed = true;
                        //MyGdxGame.setCurrentScreen(new LevelManager());

                    } else if (i==1) {
                        System.out.println("Settings button pressed");
                        buttonPressed = true;
                    } else if (i == 2){
                        System.out.println("Quit button pressed");
                        Gdx.app.exit();
                    }
                }
            }
        }
    }

    /**
     * Called when this screen becomes the active screen
     */
    public void startDisplaying(){
        // Create game logo sprite
        gameLogoTexture = new Texture("logo.png");
        gameLogoSprite = new Sprite(gameLogoTexture);

        // Scale logo depending on window size
        float scaleRatio = (gameLogoSprite .getWidth() / Gdx.graphics.getWidth()) * 1.2f;
        gameLogoSprite.setSize(gameLogoSprite.getWidth() / scaleRatio, gameLogoSprite.getHeight()/scaleRatio);

        //Centre the logo
        gameLogoSprite.setPosition(Gdx.graphics.getWidth()/2 - gameLogoSprite.getWidth()/2,(Gdx.graphics.getHeight()/2 - gameLogoSprite.getHeight()/2) * 2.2f);

        //Buttons

        // Start Game Button
        startGameButtonTexture = new Texture("mainMenuScreen/start.png");
        startGameButtonSprite = new Sprite(startGameButtonTexture);
        scaleRatio = buttonScaleRatio(startGameButtonSprite);
        startGameButtonSprite.setSize(startGameButtonSprite.getWidth()/scaleRatio,startGameButtonSprite.getHeight()/scaleRatio);

        int offset = 0;

        startGameButtonSprite.setPosition(Gdx.graphics.getWidth()/2 - startGameButtonSprite.getWidth()/2,(Gdx.graphics.getHeight()/2-startGameButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(startGameButtonSprite);

        // Settings Button
        settingsButtonTexture = new Texture("mainMenuScreen/settings.png");
        settingsButtonSprite = new Sprite(settingsButtonTexture);
        scaleRatio = buttonScaleRatio(settingsButtonSprite);
        settingsButtonSprite.setSize(settingsButtonSprite.getWidth()/scaleRatio,settingsButtonSprite.getHeight()/scaleRatio);

        offset -= 320;

        settingsButtonSprite.setPosition(Gdx.graphics.getWidth()/2 - settingsButtonSprite.getWidth()/2,(Gdx.graphics.getHeight()/2-settingsButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(settingsButtonSprite);

        // Quit button

        quitButtonTexture = new Texture("mainMenuScreen/quit.png");
        quitButtonSprite = new Sprite(quitButtonTexture);
        scaleRatio = buttonScaleRatio(quitButtonSprite);
        quitButtonSprite.setSize(quitButtonSprite.getWidth()/scaleRatio,quitButtonSprite.getHeight()/scaleRatio);

        offset -= 600;
        quitButtonSprite.setPosition(Gdx.graphics.getWidth()/2 - quitButtonSprite.getWidth()/2,(Gdx.graphics.getHeight()/2-quitButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(quitButtonSprite);
    }

    private float buttonScaleRatio(Sprite button){
        float scaleRatio = (button.getWidth()/Gdx.graphics.getWidth()) * 3.5f;
        return scaleRatio;
    }


    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    public void stopDisplaying(){
        gameLogoTexture.dispose();
        startGameButtonTexture.dispose();
        settingsButtonTexture.dispose();
        quitButtonTexture.dispose();
    }

}
