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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import io.github.pirateducks.PirateDucks;
import io.github.pirateducks.level.MainLevel;
import io.github.pirateducks.screen.GameOverScreen;
import io.github.pirateducks.screen.PauseScreen;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.*;

/**
 * This class is the Constantine memory game
 */
public class ConstantineMemoryGame extends College{

    private final OrthographicCamera camera;
    private final MainLevel mainLevel;

    // Initialise textures and sprites for graphics
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

    // Initialise array to store textures and sprites of number cards
    private final Array<Texture> cardTextures = new Array<>();
    private final Array<Sprite> cardSprites = new Array<>();

    // Initialise array to store digits on number cards
    private int[] digitsToMemorise;

    // Initialise game status variables
    private boolean inGame = false;
    private boolean gameFinished = false;
    private boolean save = false;
    private boolean win = false;
    private boolean asking = false; // Stores status of whether currently asking for user input

    // Initialise array to store on-screen buttons
    private final Array<Sprite> buttons = new Array<>();

    /**
     * Class constructor, called to start the Constantine Memory Game
     * @param level MainLevel class which holds the main game and player data
     * @param camera Manages screen of the game
     */
    public ConstantineMemoryGame(MainLevel level, OrthographicCamera camera) {
        super(level);
        this.camera = camera;
        this.mainLevel = level;

        /*
         * Background music credit
         * Beach by MusicbyAden & Jurgance | https://soundcloud.com/musicbyaden
         * https://soundcloud.com/jurgance
         * Music promoted by https://www.free-stock-music.com
         * Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)
         * https://creativecommons.org/licenses/by-nd/4.0/
         */

        // Configure background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("memoryGame/beach.mp3"));
        backgroundMusic.setLooping(true);

        // Set music volume
        backgroundMusic.play();
        if (getMainClass().musicOn) {
            backgroundMusic.setVolume(0.15f);
        } else {
            backgroundMusic.setVolume(0);
        }
    }

    /**
     * Get college max health
     * @return the college's maximum health as an int
     */
    @Override
    public int getMaxHealth() {
        return 1;
    }

    /**
     * Get the map texture
     * @return Texture of the map/background of the college
     */
    @Override
    protected Texture getMapTexture() {
        return new Texture("map_blurred.png");
    }

    /**
     * Method to setup the screen and graphics of Constantine college
     * @param camera Manages screen of the game
     */
    @Override
    public void setup(OrthographicCamera camera) {
        inGame = false;
        gameFinished = false;

        // Display game text header
        headerTexture = new Texture("memoryGame/header.png");
        headerSprite = new Sprite(headerTexture);

        // Set size and position of game header
        float scaleRatio = (headerSprite.getWidth() / camera.viewportWidth * 1.5f);
        headerSprite.setSize(headerSprite.getWidth() / scaleRatio, headerSprite.getHeight() / scaleRatio);
        headerSprite.setPosition(camera.viewportWidth / 2 - headerSprite.getWidth() / 2, (camera.viewportHeight / 2 - headerSprite.getHeight() / 2) * 1.9f);

        // Generate number cards
        int currentxpos = 5; // Store current x position of number card
        for (int x = 0; x < 8; x++) {
            // Create number card texture and sprite
            Texture cardTexture = new Texture("memoryGame/question-mark.png");
            Sprite cardSprite = new Sprite(cardTexture);

            // Scale and set size of number card so it fits on the screen
            scaleRatio = (cardSprite.getWidth() / camera.viewportWidth * 8.4f);
            cardSprite.setSize(cardSprite.getWidth() / scaleRatio, cardSprite.getHeight() / scaleRatio);
            cardSprite.setPosition(currentxpos, (camera.viewportHeight / 2 - cardSprite.getHeight() / 2));

            // Store number card objects in array so it can be accessed and modified later
            cardTextures.add(cardTexture);
            cardSprites.add(cardSprite);

            // Increase x position for the next number card
            currentxpos += 105;
        }

        // Generate countdown timer graphics
        countdownTexture = new Texture("memoryGame/countdown-10.png");
        countdownSprite = new Sprite(countdownTexture);

        // Scale countdown timer and set position
        scaleRatio = (countdownSprite.getWidth() / camera.viewportWidth * 6.0f);
        countdownSprite.setSize(countdownSprite.getWidth() / scaleRatio, countdownSprite.getHeight() / scaleRatio);
        countdownSprite.setPosition(camera.viewportWidth / 2 - countdownSprite.getWidth() / 2, (camera.viewportHeight / 2 - countdownSprite.getHeight() / 2 - 100));

        // Generate start button graphics
        startGameTexture = new Texture("memoryGame/start-game.png");
        startGameSprite = new Sprite(startGameTexture);

        // Scale start button and set position
        scaleRatio = (startGameSprite.getWidth() / camera.viewportWidth * 3.0f);
        startGameSprite.setSize(startGameSprite.getWidth() / scaleRatio, startGameSprite.getHeight() / scaleRatio);
        startGameSprite.setPosition(camera.viewportWidth / 2 - startGameSprite.getWidth() / 2, (camera.viewportHeight / 2 - startGameSprite.getHeight() / 2 - 200));

        buttons.add(startGameSprite);

        // Generate close button graphics
        closeTexture = new Texture("memoryGame/close.png");
        closeSprite = new Sprite(closeTexture);

        // Scale close button and set position
        scaleRatio = (closeSprite.getWidth() / camera.viewportWidth * 3.0f);
        closeSprite.setSize(closeSprite.getWidth() / scaleRatio, closeSprite.getHeight() / scaleRatio);
        closeSprite.setPosition(camera.viewportWidth / 2 - closeSprite.getWidth() / 2, (camera.viewportHeight / 2 - closeSprite.getHeight() / 2 - 200));

        buttons.add(closeSprite);

        // Set close button as hidden (close button will show when the game is finished)
        closeSprite.setAlpha(0);

        // Change music volume
        backgroundMusic.play();
        if (getMainClass().musicOn) {
            backgroundMusic.setVolume(0.15f);
        } else {
            backgroundMusic.setVolume(0);
        }
    }

    /**
     * When this college game is finished, dispose of all the objects as we no longer need them
     */
    @Override
    public void stopDisplaying() {

        backgroundMusic.setVolume(0);

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
    public void resume() {
        if (getMainClass().musicOn) {
            backgroundMusic.setVolume(0.15f);
        } else {
            backgroundMusic.setVolume(0);
        }
    }

    /**
     * Draw all graphics
     * @param batch The batch that is rendering the level
     * @param camera Manages screen of the game
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera camera){

        ScreenUtils.clear(0, 0, 0.2f, 1);
        // set background as blurred map
        backgroundTexture = getMapTexture();
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        backgroundSprite.setPosition(0, 0);


        // Draw all graphics

        if (backgroundSprite != null && headerSprite != null && startGameSprite != null && closeSprite != null && countdownSprite != null){
            backgroundSprite.draw(batch);
            headerSprite.draw(batch);
            startGameSprite.draw(batch);
            closeSprite.draw(batch);
            countdownSprite.draw(batch);
        } else {
            throw new Error("Error drawing graphics, please restart game");
        }


        if (!cardTextures.isEmpty() && !cardSprites.isEmpty()) {
            for (int x = 0; x < 8; x++) {
                cardSprites.get(x).draw(batch);
            }
        } else {
            throw new Error("Error drawing number cards, please restart game");
        }
    }

    // Set length of timer of game (in seconds)
    private final int countdownLength = 10;

    private boolean gameOver = false;

    /**
     * Timer task which performs countdown
     * This task
     */
    private final Timer.Task countdown = new Timer.Task() {
        int numSeconds = countdownLength;

        @Override
        public void run() {
            // Decrement countdown by 1
            numSeconds -= 1;

            // Update countdown timer graphic
            String filepath = "memoryGame/countdown-" + numSeconds + ".png";
            countdownTexture = new Texture(filepath);
            countdownSprite.setTexture(countdownTexture);
            float scaleRatio = (countdownSprite.getWidth() / camera.viewportWidth * 6.0f);
            countdownSprite.setSize(countdownSprite.getWidth() / scaleRatio, countdownSprite.getHeight() / scaleRatio);
            countdownSprite.setPosition(camera.viewportWidth / 2 - countdownSprite.getWidth() / 2, (camera.viewportHeight / 2 - countdownSprite.getHeight() / 2 - 100));

            // Play buzzer sound on last three seconds of game
            if (numSeconds < 4 && numSeconds != 0) {
                // https://mixkit.co/free-sound-effects/beep/ "System beep buzzer fail"
                Sound bleep = Gdx.audio.newSound(Gdx.files.internal("memoryGame/timer.wav"));
                bleep.play();
            }

            // Check if countdown is 0 (i.e. game has now finished)
            if (numSeconds == 0) {
                // Play horn sound to signify end of game
                //https://www.freesfx.co.uk/sfx/air-horn "Air Horn Blast"
                Sound horn = Gdx.audio.newSound(Gdx.files.internal("memoryGame/horn.mp3"));
                horn.play();

                hideCards();  // Hide number cards as user has to input the digits they have memorised

                // Stop countdown and set game status
                countdown.cancel();
                numSeconds = countdownLength;
                inGame = false;
                gameFinished = true;

                // Create task so we can delay asking user for digits they have memorised, as we need to make sure cards are hidden first.
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                Callable<Boolean> task = new Callable<Boolean>() {
                    public Boolean call() {
                        return checkDigits();
                    }
                };
                // Schedule task to ask user for digits they have memorised in 500ms
                Future<Boolean> result = scheduler.schedule(task, 500, TimeUnit.MILLISECONDS);

            }
        }
    };

    /**
     * Sets the number cards to display question-marks, so the user cannot see the numbers
     */
    private void hideCards() {
        for (int x = 0; x < 8; x++) {
            String filepath = "memoryGame/question-mark.png";
            cardTextures.set(x, new Texture(filepath));
            cardSprites.get(x).setTexture(cardTextures.get(x));
        }
    }

    /**
     * Function which asks the user to enter the digits they have memorised and compares them
     * to the stored digits to determine if the user has defeated the college or not
     * @return Boolean indicating win (true) or loss (false)
     */
    private boolean checkDigits() {
        // User has to enter in digits as a single string, so convert our int array to single string
        StringBuilder builder = new StringBuilder();
        for (int i : digitsToMemorise) {
            builder.append(i);
        }
        String correctDigits = builder.toString();

        // Using JOptionPane to ask for user input

        // creating a never displayed jframe so we get more control over jOptionPanes
        JFrame frame = new JFrame();
        // ensuring all jOptionPanes stay on the top layer
        frame.setAlwaysOnTop(true);
        // centering the jframe
        frame.setLocationRelativeTo(null);

        asking = true;
        String digits = JOptionPane.showInputDialog(frame, "Enter the digits you have memorised:");

        win = false;

        if (digits != null && digits.equals(correctDigits)) {
            // User has entered correct digits and defeated the college

            // https://mixkit.co/free-sound-effects/win/
            Sound winSound = Gdx.audio.newSound(Gdx.files.internal("memoryGame/win.wav"));
            winSound.play();

            JOptionPane.showMessageDialog(frame, "Correct! You have defeated Constantine College!");
            win = true;
            winSound.dispose();

        } else {
            // User has not entered the correct digits and has not defeated the college

            // https://mixkit.co/free-sound-effects/lose/
            Sound loseSound = Gdx.audio.newSound(Gdx.files.internal("memoryGame/lose.wav"));
            loseSound.play();

            mainLevel.getPlayer().setHealth(mainLevel.getPlayer().getHealth() - 2, false);

            if(mainLevel.getPlayer().getHealth() <= 0){
                gameOver = true;
            }

            String resultMsg;
            if (digits.isEmpty()) {
                resultMsg = "Incorrect! You didn't enter anything. The correct answer was " + correctDigits + "! Lose a heart";
            } else {
                resultMsg = "Incorrect! You entered " + digits + " but should have got " + correctDigits + "! Lose a heart";
            }
            JOptionPane.showMessageDialog(frame, resultMsg);
            win = false;
            loseSound.dispose();
        }


        inGame = false;
        asking = false;
        // Show close button so user can exit game
        closeSprite.setAlpha(1);

        return win;
    }

    /**
     * Updates game status and checks for button and key presses
     * @param delta The delta time since last update
     */
    @Override
    public void update(float delta) {
        // updating all game objects
        super.update(delta);

        // Check if user has defeated the college
        if (gameFinished && win) {
            setHealth(0);
            return;
        }


        if(gameOver){
            getMainClass().setCurrentScreen(new GameOverScreen(getMainClass(), camera));
            return;
        }

        // Check if escape key pressed to pause the game
        // The user can "pause" so long as they are not playing the game (i.e. the countdown isn't going)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && inGame == false) {
            save = true;
            this.stopDisplaying();
            getLevelManager().getMainClass().setCurrentScreen(new PauseScreen(getLevelManager().getMainClass(), this));
        }

        // Check if buttons clicked
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // Go through each button to see if it is clicked
            for (int i = 0; i < buttons.size; i++) {

                // Get mouse position
                Vector2 scaledMouse = PirateDucks.getScaledMouseLocation(getMainClass().getCamera());

                // Get button position
                float buttonX = buttons.get(i).getX();
                float buttonY = buttons.get(i).getY();
                float buttonW = buttons.get(i).getWidth();
                float buttonH = buttons.get(i).getHeight();

                if (scaledMouse.x >= buttonX && scaledMouse.x <= (buttonX + buttonW) && scaledMouse.y >= buttonY && scaledMouse.y <= (buttonY + buttonH) && inGame == false) {

                    if (gameFinished && win) {
                        setHealth(0);
                    } else if (i ==0 && !gameFinished) {
                        //Start game button pressed
                        startGame();
                    } else if (i== 1 && !asking){
                        // Close button pressed
                        this.stopDisplaying();
                        getLevelManager().getMainClass().setCurrentScreen(mainLevel);
                    }
                }
            }
        }
    }

    /**
     * This method is called to start the game
     */
    private void startGame(){
        // Play sound to indicate start of game
        Sound start = Gdx.audio.newSound(Gdx.files.internal("memoryGame/go.wav"));
        start.play();

        // Hide start game button whilst game is running
        startGameSprite.setAlpha(0);

        inGame = true;

        //Generate digits user has to memorise
        digitsToMemorise = new int[8];
        Random rand = new Random();
        for (int x = 0; x < 8; x++) {
            // Min value 0, max value 9
            digitsToMemorise[x] = rand.nextInt(10);

            String filepath = "memoryGame/card-" + digitsToMemorise[x] + ".png";
            // Set each number card to correct digit
            cardTextures.set(x, new Texture(filepath));
            cardSprites.get(x).setTexture(cardTextures.get(x));

        }
        // Start countdown (task is scheduled to occur every 1 second)
        Timer.schedule(countdown, 1f, 1f);
    }
}