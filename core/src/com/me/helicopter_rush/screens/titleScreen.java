package com.me.helicopter_rush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.helicopter_rush.constants;
import com.me.helicopter_rush.helicopter_rush;

public class titleScreen extends screen {

    private Texture backgroundtex, playbuttontex, titletex;
    private helicopter_rush rush;
    Stage uiStage;

    public titleScreen(helicopter_rush rush) {
        super(rush.getBatch(), true);
        this.rush = rush;
    }

    @Override
    public void show() {
        super.show();
        playbuttontex = new Texture("playbutton.png");
        backgroundtex = new Texture("bg_new.png");
        titletex = new Texture("title.png");

        //getUiCamera().setToOrtho(false, constants.GAME_WIDTH, constants.GAME_HEIGHT);
        getUiCamera().position.set(getUiViewPort().getWorldWidth()/2, getUiViewPort().getWorldHeight()/2, 0);

        playButton playButton = new playButton(playbuttontex, new Vector2(
                constants.GAME_WIDTH / 2 - playbuttontex.getWidth() / 2,
                (constants.GAME_HEIGHT / 2 - playbuttontex.getHeight() / 2)), 104, 58, rush);
        title title = new title(titletex, new Vector2(
                constants.GAME_WIDTH/2-600/2, constants.GAME_HEIGHT-160),
                600, 100);
        background background = new background(backgroundtex,
                new Vector2(constants.ORIGIN.x, constants.ORIGIN.y), constants.GAME_WIDTH, constants.GAME_HEIGHT);

        this.uiStage = new Stage(getUiViewPort(), rush.getBatch());
        uiStage.addActor(background);
        uiStage.addActor(title);
        uiStage.addActor(playButton);

        Gdx.input.setInputProcessor(uiStage);
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
        uiStage.draw();
    }

    @Override
    void hidden() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        getUiViewPort().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        uiStage.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        /*if (screenX >= (constants.GAME_WIDTH/2-playbutton.getWidth()/2)
                && screenX <= (constants.GAME_WIDTH/2+playbutton.getWidth()/2)
                && screenY >= ((constants.GAME_HEIGHT/2-playbutton.getHeight()/2))
                && screenY <= (constants.GAME_HEIGHT/2+playbutton.getHeight()/2)){
        }*/
        return true;
    }

    static class playButton extends actor{
        playButton(Texture tex, Vector2 pos, int width, int height, final helicopter_rush rush) {
            super(tex, pos, width, height);

            setTouchable(Touchable.enabled);
            addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    rush.setScreen(
                            new gameScreen(
                                    rush
                            )
                    );
                    return true;
                }
            });
        }
    }
    static class title extends actor{
        title(Texture tex, Vector2 pos, int width, int height) {
            super(tex, pos, width, height);
        }
    }
    static class background extends actor{
        background(Texture tex, Vector2 pos, int width, int height) {
            super(tex, pos, width, height);
        }
    }

    static abstract class actor extends Actor{
        Texture tex;
        Vector2 pos;
        int width, height;

        actor(Texture tex, Vector2 pos, int width, int height){
            this.tex = tex;
            this.pos = pos;
            this.width = width;
            this.height = height;
            setBounds(this.pos.x, this.pos.y, width, height);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(tex, this.pos.x, this.pos.y, width, height);
        }
    }
}

