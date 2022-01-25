package io.github.pirateducks.level.college;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.screen.PauseScreen;
import io.github.pirateducks.screen.Screen;

import javax.swing.*;

import java.util.Random;
import java.util.concurrent.*;


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
    private Music backgroundMusic;


    private final Array<Texture> cardTextures = new Array<>();
    private final Array<Sprite> cardSprites = new Array<>();

    private int[] digitsToMemorise;

    private boolean inGame = false;
    private boolean save = false;

    private final Array<Sprite> buttons = new Array<>();

    private final Screen prevScreen;

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

        // Display game header
        headerTexture = new Texture("memoryGame/header.png");
        headerSprite = new Sprite(headerTexture);

        float scaleRatio = (headerSprite.getWidth() / camera.viewportWidth * 1.5f);
        headerSprite.setSize(headerSprite.getWidth() / scaleRatio, headerSprite.getHeight() / scaleRatio);
        headerSprite.setPosition(camera.viewportWidth / 2 - headerSprite.getWidth() / 2, (camera.viewportHeight / 2 - headerSprite.getHeight() / 2) * 1.9f);

        // Generate number cards
        int currentxpos = 5;
        for (int x = 0; x < 8; x++) {
            Texture cardTexture = new Texture("memoryGame/question-mark.png");
            Sprite cardSprite = new Sprite(cardTexture);
            scaleRatio = (cardSprite.getWidth() / camera.viewportWidth * 8.4f);
            cardSprite.setSize(cardSprite.getWidth() / scaleRatio, cardSprite.getHeight() / scaleRatio);
            cardSprite.setPosition(currentxpos, (camera.viewportHeight / 2 - cardSprite.getHeight() / 2));
            cardTextures.add(cardTexture);
            cardSprites.add(cardSprite);
            currentxpos += 105;
        }

        countdownTexture = new Texture("memoryGame/countdown-10.png");
        countdownSprite = new Sprite(countdownTexture);

        scaleRatio = (countdownSprite.getWidth() / camera.viewportWidth * 6.0f);
        countdownSprite.setSize(countdownSprite.getWidth() / scaleRatio, countdownSprite.getHeight() / scaleRatio);
        countdownSprite.setPosition(camera.viewportWidth / 2 - countdownSprite.getWidth() / 2, (camera.viewportHeight / 2 - countdownSprite.getHeight() / 2 - 100));

        startGameTexture = new Texture("memoryGame/start-game.png");
        startGameSprite = new Sprite(startGameTexture);

        scaleRatio = (startGameSprite.getWidth() / camera.viewportWidth * 3.0f);
        startGameSprite.setSize(startGameSprite.getWidth() / scaleRatio, startGameSprite.getHeight() / scaleRatio);
        startGameSprite.setPosition(camera.viewportWidth / 2 - startGameSprite.getWidth() / 2, (camera.viewportHeight / 2 - startGameSprite.getHeight() / 2 - 200));

        buttons.add(startGameSprite);


        closeTexture = new Texture("memoryGame/close.png");
        closeSprite = new Sprite(closeTexture);

        scaleRatio = (closeSprite.getWidth() / camera.viewportWidth * 3.0f);
        closeSprite.setSize(closeSprite.getWidth() / scaleRatio, closeSprite.getHeight() / scaleRatio);
        closeSprite.setPosition(camera.viewportWidth / 2 - closeSprite.getWidth() / 2, (camera.viewportHeight / 2 - closeSprite.getHeight() / 2 - 200));

        buttons.add(closeSprite);
        closeSprite.setAlpha(0);


        //Beach by MusicbyAden & Jurgance | https://soundcloud.com/musicbyaden
        //https://soundcloud.com/jurgance
        //Music promoted by https://www.free-stock-music.com
        //Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)
        //https://creativecommons.org/licenses/by-nd/4.0/
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("memoryGame/beach.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.15f);
        backgroundMusic.play();


    }


    @Override
    public void stopDisplaying() {

        if (!save) {
            backgroundTexture.dispose();
            startGameTexture.dispose();
            closeTexture.dispose();
            backgroundMusic.dispose();

            for (int x = 0; x < 8; x++) {
                cardTextures.get(x).dispose();
            }
        }
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

        if (startGameSprite != null) {
            startGameSprite.draw(batch);
        }

        if (closeSprite != null) {
            closeSprite.draw(batch);
        }

        if (countdownSprite != null) {
            countdownSprite.draw(batch);
        }

        if (!cardTextures.isEmpty() && !cardSprites.isEmpty()) {
            for (int x = 0; x < 8; x++) {
                cardSprites.get(x).draw(batch);
            }
        }
    }

    private final int countdownLength = 10;

    private final Timer.Task countdown = new Timer.Task() {
        int numSeconds = countdownLength;

        @Override
        public void run() {
            numSeconds -= 1;
            String filepath = "memoryGame/countdown-" + numSeconds + ".png";
            countdownTexture = new Texture(filepath);
            countdownSprite.setTexture(countdownTexture);
            float scaleRatio = (countdownSprite.getWidth() / camera.viewportWidth * 6.0f);
            countdownSprite.setSize(countdownSprite.getWidth() / scaleRatio, countdownSprite.getHeight() / scaleRatio);
            countdownSprite.setPosition(camera.viewportWidth / 2 - countdownSprite.getWidth() / 2, (camera.viewportHeight / 2 - countdownSprite.getHeight() / 2 - 100));

            if (numSeconds < 4 && numSeconds != 0) {
                // https://mixkit.co/free-sound-effects/beep/ "System beep buzzer fail"
                Sound bleep = Gdx.audio.newSound(Gdx.files.internal("memoryGame/timer.wav"));
                bleep.play();
            }
            if (numSeconds == 0) {
                //https://www.freesfx.co.uk/sfx/air-horn "Air Horn Blast"
                Sound horn = Gdx.audio.newSound(Gdx.files.internal("memoryGame/horn.mp3"));
                horn.play();
                hideCards();
                countdown.cancel();
                numSeconds = countdownLength;
                inGame = false;
                setHealth(0);

                // Delay asking user for digits, as we need to make sure cards are hidden first.
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                Callable<Boolean> task = new Callable<Boolean>() {
                    public Boolean call() {
                        return checkDigits();
                    }
                };

                Future<Boolean> result = scheduler.schedule(task, 500, TimeUnit.MILLISECONDS);
            }
        }
    };

    private void hideCards() {
        for (int x = 0; x < 8; x++) {
            String filepath = "memoryGame/question-mark.png";
            cardTextures.set(x, new Texture(filepath));
            cardSprites.get(x).setTexture(cardTextures.get(x));
        }
    }


    private boolean checkDigits() {

        // Convert int array to single string
        StringBuilder builder = new StringBuilder();
        for (int i : digitsToMemorise) {
            builder.append(i);
        }
        String correctDigits = builder.toString();

        JFrame frame = new JFrame();

        String digits = JOptionPane.showInputDialog("Enter the digits you have memorised:");

        Boolean win = false;

        if (digits != null && digits.equals(correctDigits)) {
            // https://mixkit.co/free-sound-effects/win/
            Sound winSound = Gdx.audio.newSound(Gdx.files.internal("memoryGame/win.wav"));
            winSound.play();

            JOptionPane.showMessageDialog(frame, "Correct! You have defeated Constantine College!");
            win = true;
            winSound.dispose();

            // Code to process correct input here

        } else {
            // https://mixkit.co/free-sound-effects/lose/
            Sound loseSound = Gdx.audio.newSound(Gdx.files.internal("memoryGame/lose.wav"));
            loseSound.play();

            getPlayer().setHealth(getPlayer().getHealth() - 1);

            String resultMsg = "Incorrect!";
            if (digits.isEmpty()) {
                resultMsg = "Incorrect! You didn't enter anything. The correct answer was " + correctDigits + "! Lose a heart";
            } else {
                resultMsg = "Incorrect! You entered " + digits + " but should have got " + correctDigits + "! Lose a heart";
            }
            JOptionPane.showMessageDialog(frame, resultMsg);
            win = false;
            loseSound.dispose();

            // Code to process incorrect input here
        }


        inGame = false;
        // Show close button
        closeSprite.setAlpha(1);

        // Show cards again
        for (int x = 0; x < 8; x++) {
            String filepath = "memoryGame/card-" + digitsToMemorise[x] + ".png";
            cardTextures.set(x, new Texture(filepath));
            cardSprites.get(x).setTexture(cardTextures.get(x));
        }

        return win;


    }

    @Override
    public void update(float delta) {
        // updating all game objects
        super.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && inGame == false) {
            save = true;
            this.stopDisplaying();
            getLevelManager().getMainClass().setCurrentScreen(new PauseScreen(getLevelManager().getMainClass(), this));
        }

        // Check if buttons clicked
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (int i = 0; i < buttons.size; i++) {
                int mouseX = Gdx.graphics.getWidth() - Gdx.input.getX();
                int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

                Vector2 scaledMouse = PirateDucks.getScaledMouseLocation(getMainClass().getCamera());

                float buttonX = buttons.get(i).getX();
                float buttonY = buttons.get(i).getY();
                float buttonW = buttons.get(i).getWidth();
                float buttonH = buttons.get(i).getHeight();

                // Start game button clicked, so start the game
                if (scaledMouse.x >= buttonX && scaledMouse.x <= (buttonX + buttonW) && scaledMouse.y >= buttonY && scaledMouse.y <= (buttonY + buttonH) && inGame == false) {


                    //Start game button pressed

                    Sound start = Gdx.audio.newSound(Gdx.files.internal("memoryGame/go.wav"));
                    start.play();

                    // Hide start game button whilst game is running
                    startGameSprite.setAlpha(0);

                    inGame = true;
                    digitsToMemorise = new int[8];
                    Random rand = new Random();
                    for (int x = 0; x < 8; x++) {
                        // Min value 0, max value 9
                        digitsToMemorise[x] = rand.nextInt(10);

                        String filepath = "memoryGame/card-" + digitsToMemorise[x] + ".png";
                        cardTextures.set(x, new Texture(filepath));
                        cardSprites.get(x).setTexture(cardTextures.get(x));

                    }
                    Timer.schedule(countdown, 1f, 1f);

                }
            }
        }
    }
}