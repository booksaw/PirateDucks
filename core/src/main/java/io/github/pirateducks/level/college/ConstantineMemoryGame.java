package io.github.pirateducks.level.college;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.screen.PauseScreen;
import io.github.pirateducks.screen.Screen;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class ConstantineMemoryGame extends College {
    private final OrthographicCamera camera;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Texture headerTexture;
    private Sprite headerSprite;
    private Texture startGameTexture;
    private Sprite startGameSprite;
    private Texture closeTexture;
    private Sprite closeSprite;
    private Texture countdownTexture;
    private Sprite countdownSprite;


    private Array<Texture> cardTextures = new Array<>();
    private Array<Sprite> cardSprites = new Array<>();

    private int[] digitsToMemorise;

    private boolean inGame = false;
    private boolean gameFinished = false;

    private final Array<Sprite> buttons = new Array<>();

    private Screen prevScreen;

    public ConstantineMemoryGame(MainLevel level, OrthographicCamera camera, Screen prevScreen) {
        super(level);
        this.camera = camera;
        this.prevScreen = prevScreen;

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
        inGame = false;
        gameFinished = false;

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

        countdownTexture = new Texture("memoryGame/countdown-10.png");
        countdownSprite = new Sprite(countdownTexture);

        scaleRatio = (countdownSprite.getWidth()/camera.viewportWidth * 6.0f);
        countdownSprite.setSize(countdownSprite.getWidth()/scaleRatio,countdownSprite.getHeight()/scaleRatio);
        countdownSprite.setPosition(camera.viewportWidth/2 - countdownSprite.getWidth()/2,(camera.viewportHeight/2 - countdownSprite.getHeight()/2 - 100));

        startGameTexture = new Texture("memoryGame/start-game.png");
        startGameSprite = new Sprite(startGameTexture);

        scaleRatio = (startGameSprite.getWidth()/camera.viewportWidth * 3.0f);
        startGameSprite.setSize(startGameSprite.getWidth()/scaleRatio,startGameSprite.getHeight()/scaleRatio);
        startGameSprite.setPosition(camera.viewportWidth/2 - startGameSprite.getWidth()/2,(camera.viewportHeight/2 - startGameSprite.getHeight()/2 - 200));

        buttons.add(startGameSprite);


        closeTexture = new Texture("memoryGame/close.png");
        closeSprite = new Sprite(closeTexture);

        scaleRatio = (closeSprite.getWidth()/camera.viewportWidth * 3.0f);
        closeSprite.setSize(closeSprite.getWidth()/scaleRatio,closeSprite.getHeight()/scaleRatio);
        closeSprite.setPosition(camera.viewportWidth/2 - closeSprite.getWidth()/2,(camera.viewportHeight/2 - closeSprite.getHeight()/2 - 200));

        buttons.add(closeSprite);
        closeSprite.setAlpha(0);

        //Stage stage = new Stage();
       //Skin skin = new Skin(Gdx.files.internal("memoryGame/skin.json"));
        //TextField input = new TextField("Enter the digits here",skin);
        //input.setWidth(100f);
        //input.setHeight(100f);
        //input.setPosition(camera.viewportWidth/2 - input.getWidth()/2,camera.viewportHeight/2 - input.getHeight()/2);
        //stage.addActor(input);


    }


    @Override
    public void stopDisplaying() {

        backgroundTexture.dispose();
        startGameTexture.dispose();
        closeTexture.dispose();

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

        if (startGameSprite != null){
            startGameSprite.draw(batch);
        }

        if (closeSprite !=null){
            closeSprite.draw(batch);
        }

        if (countdownSprite != null){
            countdownSprite.draw(batch);
        }

        if (!cardTextures.isEmpty() && !cardSprites.isEmpty()){
            for (int x=0;x<8;x++){
                cardSprites.get(x).draw(batch);
            }
        }
    }

    private int countdownLength = 10;

    private Timer.Task countdown = new Timer.Task(){
        int numSeconds = countdownLength;
        @Override
        public void run(){
            System.out.println(numSeconds);
            numSeconds -= 1;
            String filepath = "memoryGame/countdown-" + numSeconds + ".png";
            countdownTexture = new Texture(filepath);
            countdownSprite.setTexture(countdownTexture);
            float scaleRatio = (countdownSprite.getWidth()/camera.viewportWidth * 6.0f);
            countdownSprite.setSize(countdownSprite.getWidth()/scaleRatio,countdownSprite.getHeight()/scaleRatio);
            countdownSprite.setPosition(camera.viewportWidth/2 - countdownSprite.getWidth()/2,(camera.viewportHeight/2 - countdownSprite.getHeight()/2 - 100));

            if (numSeconds == 0){
                hideCards();
                countdown.cancel();
                numSeconds = countdownLength;
                inGame = false;
                gameFinished = true;
                checkDigits();
            }
        }
    };

    private void hideCards(){
        for (int x =0;x<8;x++){
            System.out.println(x);
            String filepath = "memoryGame/question-mark.png";
            cardTextures.set(x,new Texture(filepath));
            cardSprites.get(x).setTexture(cardTextures.get(x));
        }
    }


    private void checkDigits(){

        // Convert int array to single string
        StringBuilder builder = new StringBuilder();
        for (int i: digitsToMemorise){
            builder.append(i);
        }
        String correctDigits = builder.toString();

        JFrame frame = new JFrame();

        System.out.println("now asking");
        //String digits = "";

        String digits = JOptionPane.showInputDialog("Enter the digits you have memorised:");


        if (digits != null && digits.equals(correctDigits)){
            JOptionPane.showMessageDialog(frame,"Correct");
            // Code to process correct input here
        } else {
            JOptionPane.showMessageDialog(frame,"Incorrect");
            // Code to process incorrect input here
        }

        inGame = false;
        // Show close button
        closeSprite.setAlpha(1);

        // Show cards again
        //for (int x =0;x<8;x++){
            //System.out.println(x);
            //String filepath = "memoryGame/card-" + digitsToMemorise[x] + ".png";
            //cardTextures.set(x,new Texture(filepath));
            //cardSprites.get(x).setTexture(cardTextures.get(x));
        //}

    }

    @Override
    public void update(float delta) {
        // updating all game objects
        super.update(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && inGame == false){
            this.stopDisplaying();
            getLevelManager().getMainClass().setCurrentScreen(new PauseScreen(getLevelManager().getMainClass(),this));
        }

        // Check if buttons clicked
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                for (int i = 0;i<buttons.size;i++) {
                    int mouseX = Gdx.graphics.getWidth() - Gdx.input.getX();
                    int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

                    float buttonX = buttons.get(i).getX();
                    float buttonY = buttons.get(i).getY();
                    float buttonW = buttons.get(i).getWidth();
                    float buttonH = buttons.get(i).getHeight();

                    // Start game button clicked, so start the game
                    if (mouseX >= buttonX && mouseX <= (buttonX + buttonW) && mouseY >= buttonY && mouseY <= (buttonY + buttonH) && inGame == false) {

                        if (gameFinished) {
                            // Close button pressed
                            this.stopDisplaying();
                            getLevelManager().getMainClass().setCurrentScreen(prevScreen);
                        } else {
                            //Start game button pressed

                            // Hide start game button whilst game is running
                            startGameSprite.setAlpha(0);

                            inGame = true;
                            digitsToMemorise = new int[8];
                            Random rand = new Random();
                            for (int x = 0; x < 8; x++) {
                                // Min value 0, max value 9
                                digitsToMemorise[x] = rand.nextInt(10);

                                String filepath = "memoryGame/card-" + String.valueOf(digitsToMemorise[x]) + ".png";
                                cardTextures.set(x, new Texture(filepath));
                                cardSprites.get(x).setTexture(cardTextures.get(x));

                            }
                            Timer.schedule(countdown, 1f, 1f);
                            System.out.println(Arrays.toString(digitsToMemorise));
                        }
                    }
            }
        }
    }


}