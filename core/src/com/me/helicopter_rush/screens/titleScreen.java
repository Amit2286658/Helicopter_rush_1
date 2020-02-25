package com.me.helicopter_rush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.me.helicopter_rush.constants;
import com.me.helicopter_rush.helicopter_rush;
import com.me.helicopter_rush.sprites.hud.about;

public class titleScreen extends screen {

    private Texture backgroundtex, playbuttontex, titletex, abouttex;
    private helicopter_rush rush;
    Stage uiStage;
    private about aboutHud;

    public titleScreen(helicopter_rush rush) {
        super(rush.getBatch(), true);
        this.rush = rush;
    }

    @Override
    public void show() {
        super.show();
        playbuttontex = new Texture("playbutton.png");
        backgroundtex = new Texture("bg_forest_edited.png");
        titletex = new Texture("title.png");
        abouttex = new Texture("about.png");

        aboutHud = new about(rush.getBatch(), getUiViewPort());

        //getUiCamera().setToOrtho(false, constants.GAME_WIDTH, constants.GAME_HEIGHT);
        getUiCamera().position.set(getUiViewPort().getWorldWidth()/2, getUiViewPort().getWorldHeight()/2, 0);

        playButton playButton = new playButton(playbuttontex, new Vector2(
                constants.GAME_WIDTH / 2 - playbuttontex.getWidth() / 2,
                (constants.GAME_HEIGHT / 2 - playbuttontex.getHeight() / 2)), 104, 58);
        playButton.setTouchable(Touchable.enabled);
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (aboutHud.isShowing()) aboutHud.hide();
                else
                    rush.setScreen(new gameScreen(rush));
                return true;
            }
        });

        title title = new title(titletex, new Vector2(
                constants.GAME_WIDTH/2-600/2, constants.GAME_HEIGHT-160),
                600, 100);
        title.setTouchable(Touchable.enabled);
        title.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (aboutHud.isShowing()) aboutHud.hide();
                return true;
            }
        });

        background background = new background(backgroundtex,
                new Vector2(constants.ORIGIN.x, constants.ORIGIN.y), constants.GAME_WIDTH, constants.GAME_HEIGHT);
        background.setTouchable(Touchable.enabled);
        background.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (aboutHud.isShowing()) aboutHud.hide();
                return true;
            }
        });

        aboutActor about = new aboutActor(abouttex, new Vector2(constants.GAME_WIDTH/2 - 130/2,
                constants.ORIGIN.y + 80), 130, 40);
        about.setTouchable(Touchable.enabled);
        about.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (aboutHud.isShowing())
                    aboutHud.hide();
                else aboutHud.show();
                return true;
            }
        });

        this.uiStage = new Stage(getUiViewPort(), rush.getBatch());
        uiStage.addActor(background);
        uiStage.addActor(title);
        uiStage.addActor(playButton);
        uiStage.addActor(about);

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
        aboutHud.draw();
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
        aboutHud.dispose();
        playbuttontex.dispose();
        titletex.dispose();
        abouttex.dispose();
        backgroundtex.dispose();
    }

    static class playButton extends actor{
        playButton(Texture tex, Vector2 pos, int width, int height) {
            super(tex, pos, width, height);
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
    static class aboutActor extends actor{
        aboutActor(Texture tex, Vector2 pos, int width, int height) {
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

