package com.me.helicopter_rush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.me.helicopter_rush.constants;
import com.me.helicopter_rush.helicopter_rush;
import com.me.helicopter_rush.sprites.hud.aboutHud;

public class titleScreen extends screen {

    private Texture backgroundtex, playbuttontex, titletex, abouttex, music_on, music_off, sound_on, sound_off;
    private helicopter_rush rush;
    private Stage uiStage;
    private BitmapFont font;
    private Music gameMusic;
    private Preferences preferences;
    private boolean isMusicOn = true, isSoundOn = true;

    private aboutHud hud_1;

    public titleScreen(helicopter_rush rush) {
        super(rush.getBatch(), true);
        this.rush = rush;
    }

    @Override
    public void show() {
        super.show();

        preferences = Gdx.app.getPreferences(constants.PREFERENCE);
        isMusicOn = preferences.getBoolean(constants.MAIN_MUSIC_PREFERENCE_KEY, true);
        isSoundOn = preferences.getBoolean(constants.SFX_PREFERENCE_KEY, true);

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        gameMusic.setLooping(true);
        if (isMusicOn)
            gameMusic.play();

        playbuttontex = new Texture("yellow_button00.png");
        backgroundtex = new Texture("game_background_1.png");
        titletex = new Texture("title.png");
        abouttex = new Texture("about.png");
        music_on = new Texture("music_on.png");
        music_off = new Texture("music_off.png");
        sound_on = new Texture("sound_on.png");
        sound_off = new Texture("sound_off.png");

        font = new BitmapFont(Gdx.files.internal("kenvector_future.fnt"));
        font.setColor(Color.BROWN);
        font.getData().setScale(0.6f);

        hud_1 = new aboutHud(rush.getBatch());

        getUiCamera().position.set(getUiViewPort().getWorldWidth()/2, getUiViewPort().getWorldHeight()/2, 0);

        playButton playButton = new playButton(playbuttontex, new Vector2(
                constants.GAME_WIDTH / 2 - playbuttontex.getWidth() / 2,
                (constants.GAME_HEIGHT / 2 - playbuttontex.getHeight() / 2)),
                playbuttontex.getWidth(), playbuttontex.getHeight());//104, 58);
        playButton.setTouchable(Touchable.enabled);
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!hud_1.isDrawing()) rush.setScreen(new gameScreen(rush));
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
                if (hud_1.isDrawing()) hud_1.hide();
                return true;
            }
        });

        background background = new background(backgroundtex,
                new Vector2(constants.ORIGIN.x, constants.ORIGIN.y), constants.GAME_WIDTH, constants.GAME_HEIGHT);
        background.setTouchable(Touchable.enabled);
        background.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (hud_1.isDrawing()) hud_1.hide();
                return true;
            }
        });

        aboutActor about = new aboutActor(abouttex, new Vector2(constants.GAME_WIDTH/2 - 130/2,
                constants.ORIGIN.y + 80), 130, 40);
        about.setTouchable(Touchable.enabled);
        about.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!hud_1.isDrawing()) hud_1.show();
                return true;
            }
        });

        final musicButtton musicButtton = new musicButtton(isMusicOn ? music_on : music_off,
                new Vector2(640, 15), 40,40);
        musicButtton.setTouchable(Touchable.enabled);
        musicButtton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                preferences.putBoolean(constants.MAIN_MUSIC_PREFERENCE_KEY, !isMusicOn);
                preferences.flush();

                isMusicOn = preferences.getBoolean(constants.MAIN_MUSIC_PREFERENCE_KEY, true);

                musicButtton.setTexture(isMusicOn ? music_on : music_off);

                if (!gameMusic.isPlaying())
                    gameMusic.play();
                else if (gameMusic.isPlaying())
                    gameMusic.stop();

                return true;
            }
        });
        final sfxButton sfxButton = new sfxButton(isSoundOn ? sound_on : sound_off,
                new Vector2(700,15), 40, 40);
        sfxButton.setTouchable(Touchable.enabled);
        sfxButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                preferences.putBoolean(constants.SFX_PREFERENCE_KEY, !isSoundOn);
                preferences.flush();

                isSoundOn = preferences.getBoolean(constants.SFX_PREFERENCE_KEY, true);

                sfxButton.setTexture(isSoundOn ? sound_on : sound_off);

                return true;
            }
        });
        this.uiStage = new Stage(getUiViewPort(), rush.getBatch());
        uiStage.addActor(background);
        uiStage.addActor(title);
        uiStage.addActor(playButton);
        uiStage.addActor(about);
        uiStage.addActor(musicButtton);
        uiStage.addActor(sfxButton);

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

        rush.getBatch().begin();
        font.draw(rush.getBatch(), "start"
                , constants.GAME_WIDTH/2-playbuttontex.getWidth()/4 - constants.PLAYBUTTON_XOFFSET
                , constants.GAME_HEIGHT/2-playbuttontex.getHeight()/2 + constants.PLAYBUTTON_YOFFSET);
        rush.getBatch().end();
        hud_1.draw();
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
        playbuttontex.dispose();
        titletex.dispose();
        abouttex.dispose();
        backgroundtex.dispose();
        font.dispose();
        hud_1.dispose();
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
    static class musicButtton extends actor{
        musicButtton(Texture tex, Vector2 pos, int width, int height) {
            super(tex, pos, width, height);
        }
    }
    static class sfxButton extends actor{
        sfxButton(Texture tex, Vector2 pos, int width, int height) {
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

        public void setTexture(Texture tex){
            this.tex = tex;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(tex, this.pos.x, this.pos.y, width, height);
        }
    }
}

