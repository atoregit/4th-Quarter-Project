package com.mygdx.fourq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
        fruitLastDropTime = TimeUtils.nanoTime();

        font = new BitmapFont();
        font.getData().setScale(2f);

        fruits = new Array<>();
        spawnFruit();
    }

    public void spawnFruit() {
        Rectangle fruitBox = new FruitRectangle();
        fruitBox.x = MathUtils.random(0, game.GAME_SCREEN_X - 64);
        fruitBox.y = game.GAME_SCREEN_Y;
        fruitBox.width = 64;
        fruitBox.height = 64;
        generateFruitValue((FruitRectangle) fruitBox);
        fruits.add(fruitBox);
        fruitLastDropTime = TimeUtils.nanoTime();
    }

    public void render() {
        if (TimeUtils.nanoTime() - fruitLastDropTime > spawnFruitInterval) {
            spawnFruit();
        }
    }

    public void draw() {
        for (Rectangle fruit : fruits) {
            game.batch.draw(game.dropImage, fruit.x, fruit.y);
            game.font.draw(game.batch, String.valueOf(((FruitRectangle) fruit).fruitValue), fruit.x, fruit.y + 50);
        }
    }

    public void move() {
        for (Iterator<Rectangle> iter = fruits.iterator(); iter.hasNext(); ) {
            Rectangle fruit = iter.next();
            fruit.y -= FRUIT_SPEED * Gdx.graphics.getDeltaTime();
            if (fruit.y + FRUIT_SIZE < 0) iter.remove();
            if (fruit.overlaps(game.player)) {
                FruitRectangle collidedFruit = (FruitRectangle) fruit;
                int fruitValue = collidedFruit.fruitValue;
                System.out.println("Collided with fruit value: " + fruitValue);
                game.dropSound.play();
                collectLogic(fruitValue);
                iter.remove();
            }
        }
    }

    public void dispose() {

    }

    public void generateFruitValue(FruitRectangle fruit) {
        // Choose a factor of 180 as the fruit value
        int[] factors = { 20, 30, 45, 60, 90};
        fruit.fruitValue = factors[MathUtils.random(factors.length - 1)];
    }


    public void collectLogic(int value) {
        collected[collectIndex] = value;
        collectIndex = (collectIndex + 1) % 3;
        if ((collected[0] + collected[1] + collected[2]) == 180) {
            game.points++;
        }
        System.out.println(collected[0] + " " + collected[1] + " " + collected[2]);
        System.out.println(collected[0]  + collected[1] + collected[2]);
        lastFruitSum = (collected[0] + collected[1] + collected[2]);
        remainingFruitSum = (collected[1] + collected[2]);
    }

    private int[] collected = new int [3];
    private int collectIndex = 0;
    public int lastFruitSum;
    public int remainingFruitSum;
    private BitmapFont font;
    private long fruitLastDropTime;
    private Array<Rectangle> fruits;
    private static final int FRUIT_SIZE = 64;
    private static final int FRUIT_SPEED = 400;
    private long spawnFruitInterval = 400000000L;

    private static class FruitRectangle extends Rectangle {
        int fruitValue;
    }
}
