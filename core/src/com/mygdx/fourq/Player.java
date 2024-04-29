package com.mygdx.fourq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
    final GameScreen game;

    public Player(final GameScreen game) {
        this.game = game;
    }

    public void createPlayer() {
        game.player = new Rectangle();
        game.player.x = 480 / 2f - PLAYER_SIZE / 2f; // center the player horizontally
        game.player.y = 20; // bottom left corner of the player is 20 pixels above the bottom screen edge
        game.player.width = PLAYER_SIZE;
        game.player.height = PLAYER_SIZE;
    }

    public void render() {
        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);
            game.player.x = touchPos.x - PLAYER_SIZE / 2f;
        }
    }

    public void move() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            game.player.x -= speed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            game.player.x += speed * Gdx.graphics.getDeltaTime();
        }

        // make sure the player stays within the screen bounds
        if(game.player.x < 0) game.player.x = 0;
        if(game.player.x > game.GAME_SCREEN_X - PLAYER_SIZE) game.player.x = game.GAME_SCREEN_X - PLAYER_SIZE;
    }


    public void processSpeed() {
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            speed = PLAYER_SPEED_FAST;
        } else {
            speed = PLAYER_SPEED_DEFAULT;
        }
    }

    private static final int PLAYER_SIZE = 64;
    private static final int PLAYER_SPEED_FAST = 500;
    private static final int PLAYER_SPEED_DEFAULT = 200;
    private int speed = PLAYER_SPEED_DEFAULT;

}
