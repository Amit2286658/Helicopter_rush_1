package com.me.helicopter_rush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.helicopter_rush.constants;
import com.me.helicopter_rush.helicopter_rush;

public class titleScreen extends screen {

    private Texture /*startGame, about, */background, playbutton;
    private helicopter_rush rush;

    public titleScreen(helicopter_rush rush) {
        super(rush.getBatch(), true);
        this.rush = rush;
    }

    @Override
    public void show() {
        super.show();
        /*startGame = new Texture("start_game.png");
        about = new Texture("about.png");*/
        playbutton = new Texture("playbutton.png");
        background = new Texture("bg_new.png");

        Gdx.input.setInputProcessor(this);
        //getUiCamera().setToOrtho(false, constants.GAME_WIDTH, constants.GAME_HEIGHT);
        getUiCamera().position.set(getUiViewPort().getWorldWidth()/2, getUiViewPort().getWorldHeight()/2, 0);
    }

    @Override
    void update(float delta) {
        getUiCamera().update();
    }

    @Override
    void render(SpriteBatch batch) {
        batch.setProjectionMatrix(getUiCamera().combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, constants.ORIGIN.x, constants.ORIGIN.y, constants.GAME_WIDTH, constants.GAME_HEIGHT);
        batch.draw(playbutton, constants.GAME_WIDTH / 2 - playbutton.getWidth() / 2,
                (constants.GAME_HEIGHT / 2 - playbutton.getHeight() / 2), 104, 58);
        /*batch.draw(startGame, constants.GAME_WIDTH / 2 - startGame.getWidth() / 2,
                (constants.GAME_HEIGHT / 2 - startGame.getHeight() / 2) + 60, 412, 78);
        batch.draw(about, constants.GAME_WIDTH / 2 - about.getWidth() / 2,
                (constants.GAME_HEIGHT/ 2 - about.getHeight() / 2) - 60, 412, 78);*/
        batch.end();
    }

    @Override
    void hidden() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        getGameViewPort().update(width, height);
        getUiViewPort().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        /*startGame.dispose();
        about.dispose();*/
        playbutton.dispose();
        background.dispose();
        //this.batch.dispose();
        //this.rush.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        /*if (screenX >= (constants.GAME_WIDTH/2-playbutton.getWidth()/2)
                && screenX <= (constants.GAME_WIDTH/2+playbutton.getWidth()/2)
                && screenY >= ((constants.GAME_HEIGHT/2-playbutton.getHeight()/2))
                && screenY <= (constants.GAME_HEIGHT/2+playbutton.getHeight()/2)){
        }*/
        rush.setScreen(new gameScreen(
                rush
        ));

        return true;
    }
}

