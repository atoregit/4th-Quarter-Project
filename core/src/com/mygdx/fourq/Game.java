package com.mygdx.fourq;

import java.util.Iterator;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

// try

public class Game extends ApplicationAdapter {


	private Texture dropImage;
	private Texture playerImage;
	private Sound dropSound;
	private Music rainMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle player;
	private Array<Rectangle> fruits;
	private long lastDropTime;
	private BitmapFont font;
	private Texture texture;

	private int playerSpeed = PLAYER_SPEED_DEFAULT;
	private int points;
	private float timeSinceLastDrop;

	@Override
	public void create() {
		// load the images for the droplet and the player, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("melon.png"));
		playerImage = new Texture(Gdx.files.internal("cheebi.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));

		// set up the screen (kind of self-explanatory rite)
		setupScreen();
		texture = new Texture(Gdx.files.internal("bg.png"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// init font
		font = new BitmapFont();
		font.getData().setScale(1.5f);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME_SCREEN_X, GAME_SCREEN_Y);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the player
		player = new Rectangle();
		player.x = 480 / 2f - PLAYER_SIZE / 2f; // center the player horizontally
		player.y = 20; // bottom left corner of the player is 20 pixels above the bottom screen edge
		player.width = PLAYER_SIZE;
		player.height = PLAYER_SIZE;

		// create the raindrops array and spawn the first raindrop
		fruits = new Array<Rectangle>();
		spawnFruit();
	}

	private void spawnFruit() {
		Rectangle fruitBox = new Rectangle();
		fruitBox.x = MathUtils.random(0, GAME_SCREEN_X - FRUIT_SIZE);
		fruitBox.y = GAME_SCREEN_Y;
		fruitBox.width = FRUIT_SIZE;
		fruitBox.height = FRUIT_SPEED;
		fruits.add(fruitBox);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render() {

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
		for(Rectangle raindrop: fruits) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();

		// process user input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			player.x = touchPos.x - PLAYER_SIZE / 2f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			playerSpeed = PLAYER_SPEED_FAST;
		} else {
			playerSpeed = PLAYER_SPEED_DEFAULT;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.x -= playerSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.x += playerSpeed * Gdx.graphics.getDeltaTime();

		// make sure the player stays within the screen bounds
		if(player.x < 0) player.x = 0;
		if(player.x > GAME_SCREEN_X - PLAYER_SIZE) player.x = GAME_SCREEN_X - PLAYER_SIZE;

		// check if we need to create a new raindrop
		if(TimeUtils.nanoTime() - lastDropTime > SPAWN_FRUIT_INTERVAL) spawnFruit();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the player. In the latter case we play back
		// a sound effect as well.
		for (Iterator<Rectangle> iter = fruits.iterator(); iter.hasNext(); ) {
			Rectangle fruit = iter.next();
			fruit.y -= FRUIT_SPEED * Gdx.graphics.getDeltaTime();
			if(fruit.y + FRUIT_SIZE < 0) iter.remove();
			if(fruit.overlaps(player)) {
				System.out.println(points);
				points++;
				dropSound.play();
				iter.remove();
			}
		}

		batch.begin();
		font.draw(batch, "" + points, player.x+(player.width/2), player.y + player.height + 20);
		batch.end();
	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		dropImage.dispose();
		playerImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
		font.dispose();
	}

	public void setupScreen() {
//		float totalHeight = Gdx.graphics.getHeight();
//		margin = totalHeight * MARGIN_RATIO;
//		playingAreaHeight = totalHeight * PLAYING_AREA_RATIO - 2 * margin;
//		whiteAreaHeight = totalHeight * WHITE_AREA_RATIO - 2 * margin;
	}


	private static final int PLAYER_SIZE = 64;
	private static final int FRUIT_SIZE = 64;
	private static final int PLAYER_SPEED_DEFAULT = 200;
	private static final int PLAYER_SPEED_FAST = 500;
	private static final int FRUIT_SPEED = 500;
	private final int GAME_SCREEN_X = 480;
	private final int GAME_SCREEN_Y = 640;
	private static final float SPAWN_FRUIT_INTERVAL = 1000000000/3f;

	private static final float PLAYING_AREA_RATIO = 2f / 3f;
	private static final float WHITE_AREA_RATIO = 1f / 3f;
	private static final float MARGIN_RATIO = 0.05f;
	private float playingAreaHeight;
	private float whiteAreaHeight;
	private float margin = 0;




}
