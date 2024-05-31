package com.mygdx.fourq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final Game game;

    private Player chara;
    private Fruit fruit;
    private Bomb bomb;
    private GameEndScreen endScreen;

    public GameScreen(final Game game) {
        this.game = game;
        chara = new Player(this);
        fruit = new Fruit(this);
        bomb = new Bomb(this);
        initComponents();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(new PauseMenuScreen(game, this));
            gameMusic.pause();
            game.pause();
            return;
        }

        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        batch.draw(texture, 0, margin, GAME_SCREEN_X, GAME_SCREEN_Y);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && !stunned) {
            batch.draw(playerImageLeft, player.x, player.y);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !stunned) {
            batch.draw(playerImageRight, player.x, player.y);
        }
        else if(stunned) {
            batch.draw(playerImageStun, player.x, player.y);
        }
        else {
            batch.draw(playerImageIdle, player.x, player.y);
        }

        fruit.draw();
        bomb.draw(batch);
        batch.end();

        float remainingTime = timerDuration - timer;
        updateTimer(Gdx.graphics.getDeltaTime());

        chara.render();
        chara.processSpeed();
        chara.move();
        fruit.render();
        fruit.move();
        bomb.render();
        bomb.move();

        batch.begin();
        batch.draw(timerTexture, 10, GAME_SCREEN_Y - timerTexture.getHeight() - 10);
        batch.draw(scoresTexture, GAME_SCREEN_X*0.46f, GAME_SCREEN_Y - timerTexture.getHeight() - 18);
        fruit.drawCollectedFruits();
        batch.end();

        batch.begin();
        font.draw(batch, "" + points, GAME_SCREEN_X*0.5f, GAME_SCREEN_Y*0.95f);
        font.draw(batch, "" + (int) remainingTime, GAME_SCREEN_X*0.15f, GAME_SCREEN_Y*0.96f);

        batch.end();



    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        dropImage.dispose();
        playerImageIdle.dispose();
        playerImageLeft.dispose();
        playerImageRight.dispose();
        bombImage.dispose();
        dropSound.dispose();
        gameMusic.dispose();
        batch.dispose();
        font.dispose();
        fruit.dispose();
        bomb.dispose();
        timerTexture.dispose();
        fruitsCollectedTexture.dispose();
    }

    public void updateTimer(float deltaTime) {
        if (stunned) {
            stunTimer += deltaTime;
            if (stunTimer >= chara.STUN_DURATION) {
                stunned = false;
                stunTimer = 0;
            }
        }

        if (slowed) {
            slowTimer += deltaTime;
            if (stunTimer >= chara.SLOW_DURATION) {
                slowed = false;
                slowTimer = 0;
            }
        }

        timer += deltaTime;

        if (timer >= timerDuration) {
            gameMusic.stop();
            game.setScreen(new GameEndScreen(game,points));
        }
    }



    public void initComponents() {
        dropImage = new Texture(Gdx.files.internal("melon.png"));
        bombImage = new Texture(Gdx.files.internal("bomb.png"));
        playerImageIdle = new Texture(Gdx.files.internal("idle.png"));
        playerImageRight = new Texture(Gdx.files.internal("runright.png"));
        playerImageLeft = new Texture(Gdx.files.internal("runleft.png"));
        playerImageStun = new Texture(Gdx.files.internal("stun.png"));
        timerTexture = new Texture(Gdx.files.internal("timer.png"));
        fruitsCollectedTexture = new Texture(Gdx.files.internal("fruitscollected.png"));
        scoresTexture = new Texture(Gdx.files.internal("scores.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        texture = new Texture(Gdx.files.internal("maingamebg.png"));

        gameMusic.setLooping(true);
        gameMusic.play();

        font = new BitmapFont();
        font.getData().setScale(2f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_SCREEN_X, GAME_SCREEN_Y);
        batch = new SpriteBatch();

        chara.createPlayer();
        fruit.create();
        bomb.create();
    }

    public void stunPlayer() {
        stunned = true;
    }


    public OrthographicCamera camera;
    public Texture dropImage;
    private Texture playerImageIdle;
    private Texture playerImageRight;
    private Texture playerImageLeft;
    private Texture playerImageStun;
    private Texture timerTexture;
    private Texture fruitsCollectedTexture;
    private Texture scoresTexture;
    public Texture bombImage;
    public Sound dropSound;
    private Music gameMusic;
    public SpriteBatch batch;

    public BitmapFont font;
    private Texture texture;
    public int points;

    public final int GAME_SCREEN_X = 480;
    public final int GAME_SCREEN_Y = 640;

    public Rectangle player;


    private float margin = 0;
    public float stunTimer = 0;
    public float slowTimer = 0;
    public boolean stunned = false;
    public boolean slowed = false;

    private float timer = 0;
    private float timerDuration = 60;
}
