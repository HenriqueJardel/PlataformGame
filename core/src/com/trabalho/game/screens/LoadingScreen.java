package com.trabalho.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.trabalho.game.PlataformGame;
import com.trabalho.game.tools.Assets;

public class LoadingScreen implements Screen {
    private PlataformGame game;
    private Texture bar;
    private Texture [] background;
    private float time;

    public LoadingScreen(PlataformGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.time = 0;
        this.bar = new Texture("bar.png");
        this.background = new Texture[2];
        this.background[0] = new Texture("background-0.png");
        this.background[1] = new Texture("background-1.png");

        for (int i = 0; i < 11; i++) {
            Assets.load("player/parado-" + i + ".png");
        }

        for (int i = 0; i < 12; i++) {
            Assets.load("player/run-" + i + ".png");
        }

        for (int i = 0 ; i < 7; i++) {
            Assets.load("player/morto-" + i + ".png");
        }

        Assets.load("player/caindo.png");
        Assets.load("player/pulando.png");

        for(int i = 0; i < 14; i++) {
            Assets.load("inimigos/cogumelo/parado-"+i+".png");
        }

        for (int i = 0; i < 16; i++) {
            Assets.load("inimigos/cogumelo/run-" + i + ".png");
        }

        Assets.load("inimigos/cogumelo/morto.png");

        for (int i = 0; i < 9; i++) {
            Assets.load("inimigos/passaro/voando-" + i + ".png");
        }

        for (int i = 0 ; i < 14; i++) {
            Assets.load("inimigos/tartaruga/andando-" + i + ".png");
        }

        for (int i = 0 ; i < 18; i++) {
            Assets.load("inimigos/arvore/parado-" + i + ".png");
        }

        for (int i = 0 ; i < 11; i++) {
            Assets.load("inimigos/arvore/atacando-" + i + ".png");
        }

        Assets.load("inimigos/arvore/morto.png");
        Assets.load("inimigos/arvore/bala.png");

        for (int i = 0; i < 10 ;i++) {
            Assets.loadMap("cenarios/level-" + i + ".tmx");
        }

        for (int i = 0; i < 5 ;i++) {
            Assets.load("objetos/moeda-" + i + ".png");
        }

        Assets.load("objetos/plataforma.png");

        for (int i = 0; i < 10; i++) {
            Assets.loadMusic("sons/cenario-" + i + ".mp3");
        }

        Assets.loadSounds("sons/coin.ogg");
        Assets.loadSounds("sons/jump.ogg");
        Assets.loadSounds("sons/pisar.wav");
        Assets.loadMusic("sons/win.mp3");
        Assets.loadMusic("sons/lose.ogg");
    }

    @Override
    public void render(float delta) {
        time += delta;

        if (Assets.manager.update() && time >= 3) {
            game.setScreen(new GameScreen(game));
        }

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background[(int) time % 2], 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(bar, 0.05f * Gdx.graphics.getWidth(), 0.15f * Gdx.graphics.getHeight(),
                0.8f*Gdx.graphics.getWidth()*Math.min(Assets.manager.getProgress(), time/2),
                0.1f*Gdx.graphics.getHeight());
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
        bar.dispose();
        background[0].dispose();
        background[1].dispose();
    }
}
