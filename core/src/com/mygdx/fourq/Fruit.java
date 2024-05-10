package com.mygdx.fourq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Fruit {
    final GameScreen game;

    public Fruit(final GameScreen game) {
        this.game = game;
    }

    public void create() {
        fruit = new Rectangle();
        fruit.x = MathUtils.random(0, game.GAME_SCREEN_X - 64);
        fruit.y = game.GAME_SCREEN_Y;
        fruit.width = 64;
        fruit.height = 64;
        fruitLastDropTime = TimeUtils.nanoTime();

        fruits = new Array<Rectangle>();
        spawnFruit();

    }

    public void spawnFruit() {
        Rectangle fruitBox = new Rectangle();
        fruitBox.x = MathUtils.random(0, game.GAME_SCREEN_X - 64);
        fruitBox.y = game.GAME_SCREEN_Y;
        fruitBox.width = 64;
        fruitBox.height = 64;
        fruits.add(fruitBox);
        fruitLastDropTime = TimeUtils.nanoTime();
    }

    public void render() {

        if(TimeUtils.nanoTime() - fruitLastDropTime > spawnFruitInterval) {
            spawnFruit();
        }
    }

    public void draw() {
        for(Rectangle fruit : fruits) {
            game.batch.draw(game.dropImage, fruit.x, fruit.y);
        }
    }

    public void move() {
        for (Iterator<Rectangle> iter = fruits.iterator(); iter.hasNext(); ) {
            Rectangle fruit = iter.next();
            fruit.y -= FRUIT_SPEED * Gdx.graphics.getDeltaTime();
            if(fruit.y + FRUIT_SIZE < 0) iter.remove();
            if(fruit.overlaps(game.player)) {
                System.out.println(game.points);
                game.points++;
                game.dropSound.play();
                if(iter.hasNext()) {
                    iter.remove();
                }
            }
        }
    }

    public void dispose() {

    }

    private Rectangle fruit;
    private long fruitLastDropTime;
    private Array<Rectangle> fruits;
    private static final int FRUIT_SIZE = 64;
    private static final int FRUIT_SPEED = 500;
    private long spawnFruitInterval = 500000000L;
}
