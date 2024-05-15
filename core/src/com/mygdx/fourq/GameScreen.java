package com.mygdx.fourq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final Game game;

    private Player chara;
    private Fruit fruit;
    private Bomb bomb;



    public GameScreen(final Game game) {
        this.game = game;
        chara = new Player(this);
        fruit = new Fruit(this);
        bomb = new Bomb(this);
        initComponents();

    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(1, 1, 1, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);


        // begin a new batch and draw the player and
        // all drops
        batch.begin();
        batch.draw(texture, 0, margin, GAME_SCREEN_X, GAME_SCREEN_Y);
        batch.draw(playerImage, player.x, player.y);
        fruit.draw();

        bomb.draw();

        batch.end();

        float remainingTime = timerDuration - timer;
        updateTimer(Gdx.graphics.getDeltaTime());

        // process user input
        chara.render();
        chara.processSpeed();
        chara.move();

        fruit.render();
        fruit.move();


        bomb.render();
        bomb.move();

        batch.begin();
        font.draw(batch, "" + points, GAME_SCREEN_X*0.5f, GAME_SCREEN_Y*0.95f);
        font.draw(batch, "" + (int) remainingTime, GAME_SCREEN_X*0.5f, GAME_SCREEN_Y*0.3f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        dropImage.dispose();
        playerImage.dispose();
        bombImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        batch.dispose();
        font.dispose();
        fruit.dispose();



    }

    public void updateTimer(float deltaTime) {
        if (stunned) {
            stunTimer += deltaTime;
            if (stunTimer >= chara.STUN_DURATION) {
                stunned = false;
                stunTimer = 0;
            }
        }

        timer += deltaTime;
        if (timer >= timerDuration) {
            game.setScreen(new MainMenuScreen(game));
        }
    }


    public void initComponents() {
        // load the images for the droplet and the player, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("melon.png"));
        bombImage = new Texture(Gdx.files.internal("bomb.png"));
        playerImage = new Texture(Gdx.files.internal("cheebi.png"));

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));

        // set up the screen (kind of self-explanatory rite)

        texture = new Texture(Gdx.files.internal("bg.png"));

        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        rainMusic.play();

        // init font
        font = new BitmapFont();
        font.getData().setScale(2f);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_SCREEN_X, GAME_SCREEN_Y);
        batch = new SpriteBatch();

        chara.createPlayer();
        fruit.create();
        bomb.create();
    }

    public void stunPlayer() {
        stunned=true;
    }

    public OrthographicCamera camera;
    public Texture dropImage;
    private Texture playerImage;
    public Texture bombImage;
    public Sound dropSound;
    private Music rainMusic;
    public SpriteBatch batch;

    public BitmapFont font;
    private Texture texture;
    public int points;

    public final int GAME_SCREEN_X = 480;
    public final int GAME_SCREEN_Y = 640;

    public Rectangle player;

    private float margin = 0;
    public float stunTimer = 0;
    public boolean stunned = false;

    private float timer = 0;
    private float timerDuration = 60;


}
