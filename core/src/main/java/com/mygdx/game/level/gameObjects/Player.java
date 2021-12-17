package com.mygdx.game.level.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.GameObject;

public class Player extends GameObject {
    //Body is an object yet to be defined which will be defined as the main Player

    Texture texture;

    public Player() {
        super(100, 100);
        // loading the texture
        texture = new Texture(Gdx.files.internal("DuckBoat_TopView.png"));
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    private final double SPEED = 5;

    @Override
    public void update(float delta) {
        float vel_x = 0;   //Replace the x to find optimal moving speed
        float vel_y = 0;   //Replace the y to find optimal moving speed

        double deltaSpeed = SPEED + delta;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            vel_y += deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            vel_y -= deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            vel_x -= deltaSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            vel_x += deltaSpeed;
        }

        x += vel_x;
        y += vel_y;

    }

    public void dispose() {
        texture.dispose();
    }
}
