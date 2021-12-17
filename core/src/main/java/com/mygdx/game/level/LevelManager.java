package com.mygdx.game.level;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.level.gameObjects.Player;
import com.mygdx.game.screen.Screen;

/**
 * This class is the manager when the actual game is being displayed
 */
public class LevelManager implements Screen {


    private final Array<GameObject> objects = new Array<GameObject>();
    private Player player = null;

    @Override
    public void startDisplaying() {
        // loading the game
        setPlayer(new Player());
    }


    /**
     * Used to change the game object that is acting as the player
     *
     * @param player The new player
     */
    public void setPlayer(Player player) {
        // closing the current player
        if(this.player != null) {
            objects.removeValue(this.player, true);
            this.player.dispose();
        }

        // declaring the new player
        this.player = player;
        objects.add(this.player);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Used to render the level
     * @param batch The batch that is rendering the level
     */
    public void draw(SpriteBatch batch){
        // adding a plain color background as we do not have a map yet
        ScreenUtils.clear(0, 0, 0.2f, 1);

        for (GameObject object : objects) {
            object.render(batch);
        }
    }

    @Override
    public void update(float delta){
        for (GameObject object : objects) {
            object.update(delta);
        }
    }

    @Override
    public void stopDisplaying() {
        for (GameObject object : objects) {
            object.dispose();
        }
    }

}