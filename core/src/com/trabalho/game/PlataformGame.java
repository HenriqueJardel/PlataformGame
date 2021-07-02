package com.trabalho.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trabalho.game.screens.LoadingScreen;

public class PlataformGame extends Game {

	public static final short PLAYER_BIT = 2;
	public static final short ENEMY_BIT = 62;
	public static final short ENEMY_HEAD = 130;
	public static final short COIN = 34;
	public static final short SHOT = 5;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
