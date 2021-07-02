package com.trabalho.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trabalho.game.PlataformGame;
import com.trabalho.game.level.LevelManager;


public class GameScreen implements Screen {

    private PlataformGame game;
    private OrthographicCamera gameCam;
    private Viewport viewport;
    private LevelManager levelManager;

    public GameScreen(PlataformGame game) {
        this.game = game;
        this.gameCam = new OrthographicCamera();
        this.viewport = new StretchViewport(403 / 100f, 208 / 100f, gameCam);
        this.gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        this.levelManager = new LevelManager(gameCam);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.levelManager.input();
        this.levelManager.update(delta);
        this.levelManager.draw();

    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.levelManager.resize(width, height);
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
