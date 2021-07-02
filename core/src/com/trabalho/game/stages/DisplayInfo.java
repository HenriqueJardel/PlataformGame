package com.trabalho.game.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class DisplayInfo {

    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera cam;

    private Integer score;
    private Integer time;

    private Label textoScore;
    private Label scoreLabel;
    private Label textoTempo;
    private Label tempoLabel;

    public DisplayInfo(Batch batch) {
        this.cam = new OrthographicCamera();
        this.viewport = new StretchViewport(800, 480, cam);
        this.stage = new Stage(viewport, batch);

        this.score = 0;
        this.time = 0;

        this.textoScore = new Label("MELHOR SCORE", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        this.textoTempo = new Label("MELHOR TEMPO", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        this.scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        this.tempoLabel = new Label(String.format("%03d",time), new Label.LabelStyle(new BitmapFont(),Color.DARK_GRAY));
        this.textoScore.setFontScale(1.20f);
        this.textoTempo.setFontScale(1.20f);
        this.scoreLabel.setFontScale(1.20f);
        this.tempoLabel.setFontScale(1.20f);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(textoScore).expandX().pad(10);
        table.row();
        table.add(scoreLabel);
        table.row();
        table.add(textoTempo).expandX().pad(10);
        table.row();
        table.add(tempoLabel);

        stage.addActor(table);
    }

    public void draw() {
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
    }

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(String.format("%06d PONTOS",score));
    }

    public void setWorldTime(Integer time) {
        this.time = time;
        this.tempoLabel.setText(String.format("%03d SEG",time));
    }

}
