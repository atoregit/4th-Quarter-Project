package com.mygdx.fourq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.logging.FileHandler;


public class GameEndScreen implements Screen {
    final Game game;
    private int finalPoints;
    private final Stage stage;
    private String playerName;

    OrthographicCamera camera;
    FileHandler scorefile;

    public GameEndScreen(final Game game, int points) {
        this.game = game;
        finalPoints = points;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

        System.out.println(finalPoints);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));

        Label affirmation = new Label("Good job!", skin);
        Label pointsLabel = new Label("You have a score of:", skin);
        Label points = new Label("" + finalPoints, skin);

        Label nameLabel = new Label("Enter your name here:", skin);
        TextField enterName = new TextField("", skin);

        ImageTextButton saveScore = new ImageTextButton("Save", skin);
        saveScore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerName = enterName.getText();
                saveData(finalPoints, playerName);
                System.out.println("Player name: " + playerName);
                // Add additional logic to save the player's name if needed
            }
        });

        ImageTextButton newGame = new ImageTextButton("Back to menu", skin);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        table.defaults().fillX().uniformX().center();
        table.row().pad(10, 0, 10, 0);
        table.add(affirmation).center();
        table.row().pad(10, 0, 10, 0);
        table.add(pointsLabel).center();
        table.row().pad(10, 0, 10, 0);
        table.add(points).center();
        table.row().pad(20, 0, 10, 0);
        table.add(nameLabel).right().padRight(10);
        table.add(enterName).left();
        table.row().pad(10, 0, 10, 0);
        table.add(saveScore).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(newGame).colspan(2).center();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void saveData(int points, String name) {
        try {
            FileHandle scorefile = Gdx.files.local("scores.txt");
            scorefile.writeString(name + " " + points + "\n" , true);
            Gdx.app.log("ScoreManager", "Data saved successfully.");
        } catch (Exception e) {
            Gdx.app.error("ScoreManager", "Error saving data", e);
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
        stage.dispose();
    }
}
