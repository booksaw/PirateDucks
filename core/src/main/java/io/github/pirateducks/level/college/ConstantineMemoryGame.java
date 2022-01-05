package io.github.pirateducks.level.college;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.screen.PauseScreen;

import java.util.Arrays;
import java.util.Random;


public class ConstantineMemoryGame extends College {
    private final OrthographicCamera camera;
    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Texture headerTexture;
    private Sprite headerSprite;


    private Array<Texture> cardTextures = new Array<>();
    private Array<Sprite> cardSprites = new Array<>();

    private int[] digitsToMemorise;


    public ConstantineMemoryGame(MainLevel level, OrthographicCamera camera) {
        super(level);
        this.camera = camera;
    }

    @Override
    public int getMaxHealth() {
        return 0;
    }

    @Override
    protected Texture getMapTexture() {
        // Constantine map not yet created
        return new Texture("map_blurred.png");
    }

    @Override
    public void setup(OrthographicCamera camera) {
        // Display game header
        headerTexture = new Texture("memoryGame/header.png");
        headerSprite = new Sprite(headerTexture);

        float scaleRatio = (headerSprite.getWidth() / camera.viewportWidth * 1.5f);
        headerSprite.setSize(headerSprite.getWidth() / scaleRatio, headerSprite.getHeight()/scaleRatio);
        headerSprite.setPosition(camera.viewportWidth/2 - headerSprite.getWidth()/2,(camera.viewportHeight/2 - headerSprite.getHeight()/2) * 1.9f);

        // Generate number cards
        int currentxpos = 5;
        for (int x = 0;x<8;x++){
            Texture cardTexture = new Texture("memoryGame/question-mark.png");
            Sprite cardSprite = new Sprite(cardTexture);
            scaleRatio = (cardSprite.getWidth() / camera.viewportWidth * 8.4f);
            cardSprite.setSize(cardSprite.getWidth() / scaleRatio, cardSprite.getHeight() / scaleRatio);
            cardSprite.setPosition(currentxpos,(camera.viewportHeight/2-cardSprite.getHeight()/2));
            cardTextures.add(cardTexture);
            cardSprites.add(cardSprite);
            currentxpos += 105;
        }
    }


    @Override
    public void stopDisplaying() {

        backgroundTexture.dispose();

        for (int x = 0; x<8;x++){
            cardTextures.get(x).dispose();
        }

        super.stopDisplaying();
    }

    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera) {

        // set background as blurred map
        backgroundTexture = new Texture("map_blurred.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        backgroundSprite.setPosition(0, 0);

        if (backgroundSprite != null) {
            backgroundSprite.draw(batch);
        }

        if (headerSprite != null) {

            headerSprite.draw(batch);
        }

        if (!cardTextures.isEmpty() && !cardSprites.isEmpty()){
            for (int x=0;x<8;x++){
                cardSprites.get(x).draw(batch);
            }
        }
    }


    @Override
    public void update(float delta) {
        // updating all game objects
        super.update(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            getLevelManager().getMainClass().setCurrentScreen(new PauseScreen(getLevelManager().getMainClass(),this));
        }

        // Generate 8 digits
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            digitsToMemorise = new int[8];
            Random rand = new Random();
            for (int x = 0;x<8;x++){
                // Min value 0, max value 9
                digitsToMemorise[x] = rand.nextInt(10);

                String filepath = "memoryGame/card-" + String.valueOf(digitsToMemorise[x]) + ".png";
                cardTextures.set(x,new Texture(filepath));
                cardSprites.get(x).setTexture(cardTextures.get(x));

            }
            System.out.println(Arrays.toString(digitsToMemorise));

        }
    }


}