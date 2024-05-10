package com.mygdx.fourq;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.fourq.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;

public class Bomb {
    final GameScreen game;
    public Bomb(final GameScreen game) {
        this.game = game;
    }

    public void create() {
        bomb = new Rectangle();
        bomb.x = MathUtils.random(0, game.GAME_SCREEN_X - 64);
        bomb.y = game.GAME_SCREEN_Y;
        bomb.width = BOMB_SIZE;
        bomb.height = BOMB_SIZE;
        bombLastDropTime = TimeUtils.nanoTime();

        bombs = new Array<Rectangle>();
        spawnBomb();

    }

    public void spawnBomb() {
        Rectangle bombBox = new Rectangle();
        bombBox.x = MathUtils.random(0, game.GAME_SCREEN_X - 64);
        bombBox.y = game.GAME_SCREEN_Y;
        bombBox.width = BOMB_SIZE;
        bombBox.height = BOMB_SIZE;
        bombs.add(bombBox);
        bombLastDropTime = TimeUtils.nanoTime();
    }

    public void render() {

        if(TimeUtils.nanoTime() - bombLastDropTime > spawnBombInterval) {
            spawnBomb();
        }
    }



    public void draw() {
        for(Rectangle bomb : bombs) {
            game.batch.draw(game.bombImage, bomb.x, bomb.y);
        }
    }

    public void move() {
        for (Iterator<Rectangle> iter = bombs.iterator(); iter.hasNext(); ) {
            Rectangle bomb = iter.next();
            bomb.y -= BOMB_SPEED * Gdx.graphics.getDeltaTime();
            if(bomb.y + BOMB_SIZE < 0) iter.remove();
            if(bomb.overlaps(game.player)) {
                game.dropSound.play();
                game.stunPlayer();
                iter.remove(); // Remove the bomb from the list when it overlaps with the player
            }
        }
    }

    public void dispose() {

    }

    private Rectangle bomb;
    private long bombLastDropTime;
    private Array<Rectangle> bombs;
    public final int BOMB_SIZE = 64;
    private static final int BOMB_SPEED = 250;
    private long spawnBombInterval = 5000000000L;
}
