package com.trabalho.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {

    private Viewport viewport;
    private Stage stage;
    private boolean upPressed,leftPressed, rightPressed;
    private OrthographicCamera cam;

    private Integer score;
    private Integer level;
    private Integer worldTime;

    private Label scoreLabel;
    private Label worldTimeLabel;
    private Label playerLabel;

    private Label timeLabel;

    private float timer;

    public Hud(Batch batch){
        this.cam = new OrthographicCamera();
        this.viewport = new StretchViewport(800, 480, cam);
        this.stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);

        this.score = 0;
        this.level = 0;
        this.worldTime = 180;
        this.timer = 0;

        this.scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        this.worldTimeLabel = new Label(String.format("%03d",worldTime), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        this.timeLabel = new Label("TEMPO", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        this.playerLabel = new Label("SAPO", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        this.scoreLabel.setFontScale(1.20f);
        this.worldTimeLabel.setFontScale(1.20f);
        this.timeLabel.setFontScale(1.20f);
        this.playerLabel.setFontScale(1.20f);

        Table tableEsquerda = new Table();
        tableEsquerda.left().bottom();

        Table tableDireita = new Table();
        tableDireita.right().bottom();
        tableDireita.setFillParent(true);

        Table tableInfo = new Table();
        tableInfo.top();
        tableInfo.setFillParent(true);

        Image imageCima = new Image(new Texture("cima.png"));
        imageCima.setSize(50, 50);
        imageCima.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image imageDireita = new Image(new Texture("direita.png"));
        imageDireita.setSize(50, 50);
        imageDireita.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image imageEsquerda = new Image(new Texture("esquerda.png"));
        imageEsquerda.setSize(50, 50);
        imageEsquerda.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        tableInfo.add(playerLabel).top().expandX().pad(10);
        tableInfo.add().top().expandX().pad(10);
        tableInfo.add(timeLabel).top().expandX().pad(10);
        tableInfo.row();
        tableInfo.add(scoreLabel).top().expandX();
        tableInfo.add().top().expandX();
        tableInfo.add(worldTimeLabel).top().expandX();

        tableEsquerda.add(imageEsquerda).size(imageEsquerda.getWidth(), imageEsquerda.getHeight()).pad(10).bottom().left();
        tableEsquerda.add(imageDireita).size(imageDireita.getWidth(), imageDireita.getHeight()).pad(10).bottom().left();

        tableDireita.add(imageCima).size(imageCima.getWidth(), imageCima.getHeight()).pad(10);
        tableDireita.row().pad(0,0,0,10);

        stage.addActor(tableInfo);
        stage.addActor(tableEsquerda);
        stage.addActor(tableDireita);
    }

    public void draw(){
        stage.draw();
    }

    public void update(float delta , int level) {
        if (level != 0){
            timer += delta;
            if (timer >= 1) {
                if (worldTime > 0) {
                    worldTime--;
                    timer = 0;
                }
                worldTimeLabel.setText(String.format("%03d",worldTime));
            }
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public OrthographicCamera getCam() { return cam; }

    public void resize(int width, int height){
        viewport.update(width, height, true);
    }

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(String.format("%06d",score));
    }

    public void setWorldTime(Integer time) {
        this.worldTime = time;
        this.worldTimeLabel.setText(String.format("%03d",worldTime));
    }

    public Integer getScore() {
        return score;
    }

    public Integer getTime() {
        return worldTime;
    }



}
