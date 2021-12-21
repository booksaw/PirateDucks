package com.mygdx.game.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.GameObject;

public class Player extends GameObject {
    //Body is an object yet to be defined which will be defined as the main Player

    private final Texture texture;
    private float rotation;

    public Player() {
        super(100, 100);
        // loading the texture
        texture = new Texture(Gdx.files.internal("DuckBoat_TopView.png"));
        rotation = 0;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height/2, width, height, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    private final double SPEED = 5;

    @Override
    public void update(float delta) {
        float vel_x = 0;   //Replace the x to find optimal moving speed
        float vel_y = 0;   //Replace the y to find optimal moving speed

        double deltaSpeed = SPEED + delta;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            vel_y += deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            vel_y -= deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            vel_x -= deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            vel_x += deltaSpeed;
        }

        if(vel_x != 0 || vel_y != 0){
           rotation = (float) Math.toDegrees(-Math.atan2(vel_x, vel_y));
        }

        x += vel_x;
        y += vel_y;

    }

    public void dispose() {
        texture.dispose();
    }
}
