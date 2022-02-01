package io.github.pirateducks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pirateducks.screen.MainMenuScreen;
import io.github.pirateducks.screen.Screen;

/**
 * Main class for setting up the game
 */
public class PirateDucks extends ApplicationAdapter {

    /**
     * Used to scale a location on the screen to a location within game coordinates
     *
     * @param point  The point on the screen
     * @param camera The camera that is in charge of scaling
     * @return The point translated into in-game coordinates
     */
    public static Vector2 getScaledLocation(Vector2 point, OrthographicCamera camera) {
        return new Vector2(point.x * (camera.viewportWidth / Gdx.graphics.getWidth()), point.y * (camera.viewportHeight / Gdx.graphics.getHeight()));
    }

    /**
     * Used to get the in game coordinates of the mouse
     *
     * @param camera The camera that is in charge of scaling
     * @return The location of the mouse pointer in in-game coordinates
     */
    public static Vector2 getScaledMouseLocation(OrthographicCamera camera) {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        return getScaledLocation(new Vector2(mouseX, mouseY), camera);
    }

    private Screen currentScreen;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    public Boolean musicOn = true;
    private int playerHealth = 6;
    private int points = 0;
    private int gold = 0;

    /**
     * Setup the game by creating a camera and creating new instance of main menu
     */
    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 848, 480);
        camera.update();

        viewport = new FitViewport(848, 480);
        batch = new SpriteBatch();

        // Start game on main menu
        setCurrentScreen(new MainMenuScreen(this));
    }

    /**
     * Render current screen
     */
    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        currentScreen.draw(batch, camera);
        batch.end();

        currentScreen.update(Gdx.graphics.getDeltaTime());
    }

    /**
     * Dispose of resources as no longer being displayed
     */
    @Override
    public void dispose() {
        batch.dispose();
        // telling the current screen to dispose of resources
        currentScreen.stopDisplaying();
    }

    /**
     * Resize the screen
     * @param width New width of the screen
     * @param height New height of the screen
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Used to change which screen is currently being displayed
     *
     * @param screen The screen to display
     */
    public void setCurrentScreen(Screen screen) {
        setCurrentScreen(screen, true);
    }

    /**
     * Change which screen is currently being displayed
     * @param screen The new screen to display
     * @param setup Value of whether the screen is being resumed (false) rather than starting brand new (true)
     */
    public void setCurrentScreen(Screen screen, boolean setup) {
        if (currentScreen != null) {
            currentScreen.stopDisplaying();
        }
        // resetting the camera position
        camera.position.x = camera.viewportWidth / 2;
        camera.position.y = camera.viewportHeight / 2;

        currentScreen = screen;
        if (setup) {
            currentScreen.startDisplaying(camera);
        } else{
            currentScreen.resume();
        }
    }


    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public int getPlayerHealth() { return playerHealth; }

    public void setPlayerHealth(int newHealth) { playerHealth = newHealth; }

    public int getPoints () {
        return points;
    }

    public void setPoints (int n) {
        this.points = n;
    }

    public void addPoints (int n) {
        this.points += n;
    }

    public int getGold () {
        return gold;
    }

    public void setGold (int n) {
        this.gold = n;
    }

    public void addGold (int n) {
        this.gold += n;
    }
}
