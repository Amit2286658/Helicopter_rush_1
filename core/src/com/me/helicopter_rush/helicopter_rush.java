package com.me.helicopter_rush;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.helicopter_rush.screens.gameScreen;
import com.me.helicopter_rush.screens.titleScreen;

public class helicopter_rush extends Game {
	private SpriteBatch batch;
	private Music gameMusic;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		gameMusic.setLooping(true);
		gameMusic.play();

		setScreen(new titleScreen(this));
		//img = new Texture("badlogic.jpg");
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	/*@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		*//*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*//*
	}*/
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
