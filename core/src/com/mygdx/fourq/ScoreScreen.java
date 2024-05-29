package com.mygdx.fourq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.logging.FileHandler;


public class ScoreScreen implements Screen {
    final Game game;
    private final Stage stage;

    OrthographicCamera camera;
    FileHandler scorefile;

    public ScoreScreen(final Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }


    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));

        ImageTextButton newGame = new ImageTextButton("Back to game...", skin);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        loadScores();


        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);

    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0, 0, 0.2f, 1);
//        game.font.getData().setScale(1.5f);
//
//        camera.update();
//        game.batch.setProjectionMatrix(camera.combined);
//
//        game.batch.begin();
//        game.font.draw(game.batch, "Welcome to THE fourth quarter project!!!", 100, 150);
//        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
//        game.batch.end();
//
//        if (Gdx.input.isTouched()) {
//            game.setScreen(new GameScreen(game));
//            dispose();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            game.setScreen(new Automations());
//            dispose();
//        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();



    }


    public void loadScores() {
        try {
            FileHandle file = Gdx.files.local("scores.txt");
            if (file.exists()) {
                String text = file.readString().trim(); // read and trim string
                if (!text.isEmpty()) {
                    String[] scoreStrings = text.split(" ");
                    int[] scores = new int[scoreStrings.length];

                    for (int i = 0; i < scoreStrings.length; i++) {
                        try {
                            scores[i] = Integer.parseInt(scoreStrings[i]);
                            Gdx.app.log("ScoreManager", "Loaded score: " + scores[i]);
                        } catch (NumberFormatException e) {
                            Gdx.app.error("ScoreManager", "Invalid score format: " + scoreStrings[i], e);
                        }
                    }

                    // print da scores
                    for (int score : scores) {
                        System.out.println(score);
                    }
                } else {
                    Gdx.app.log("ScoreManager", "No scores found in the file.");
                }
            } else {
                Gdx.app.log("ScoreManager", "File does not exist.");
            }
        } catch (Exception e) {
            Gdx.app.error("ScoreManager", "Error loading data", e);
        }
    }


    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
