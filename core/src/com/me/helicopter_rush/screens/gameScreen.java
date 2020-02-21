package com.me.helicopter_rush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.helicopter_rush.constants;
import com.me.helicopter_rush.helicopter_rush;
import com.me.helicopter_rush.sprites.*;

public class gameScreen extends screen {

    private helicopter_rush rush;
    private helicopter helicop;
    private explosion exp;
    private Array<obstacle> rocks = new Array<obstacle>();
    private Array<ground> grounds = new Array<ground>();
    private Array<ceiling> ceilings = new Array<ceiling>();
    private Array<background> backgrounds = new Array<background>();
    private Sound explosion;
    private boolean isGameOver;
    private Texture gameover;
    private BitmapFont font;
    private static int score = 0;

    private OrthographicCamera newCam;

    private ShapeRenderer shapeRenderer;

    public gameScreen(helicopter_rush rush) {
        super(rush.getBatch(), true);
        this.rush = rush;
    }

    @Deprecated
    /*
    * never used
    */
    enum gameState{
        READY_STATE, OVER_STATE, PLAYING_STATE
    }

    @Override
    public void show() {
        super.show();
        helicop = new helicopter(constants.HELICOPTER_INITIAL_X, constants.HELICOPTER_INITIAL_Y);
        helicop.setPosition(new Vector2(constants.synchronizeCameraPosition, constants.HELICOPTER_INITIAL_Y));
        exp = new explosion();
        //bg = new Texture("background.png");
        gameover = new Texture("new game over.png");
        for (int i = 0; i < constants.BACKGROUND_COUNT; i++){
            backgrounds.add(new background(i * constants.BACKGROUND_WIDTH - constants.CAMERA_OFFSET));
        }
        for (int i = 0; i < constants.GROUND_COUNT; i++){
            grounds.add(new ground((i * constants.GROUND_WIDTH) - constants.CAMERA_OFFSET));
        }
        for (int i = 0; i < constants.CEILING_COUNT; i++){
            ceilings.add(new ceiling((i * constants.CEILING_WIDTH) - constants.CAMERA_OFFSET));
        }
        for (int i = 0; i < constants.OBSTACLE_COUNT; i++){
            rocks.add(new obstacle(500 + constants.synchronizeCameraPosition + i * constants.SPACING));
        }
        font = new BitmapFont(Gdx.files.internal("arial.fnt"));
        font.setColor(Color.BROWN);

        explosion = Gdx.audio.newSound(Gdx.files.internal("explode.wav"));

        getGameCamera().setToOrtho(false, constants.VIEWPORT_WIDTH, constants.VIEWPORT_HEIGHT);
        getGameCamera().update();
        getUiCamera().setToOrtho(false, constants.GAME_WIDTH, constants.GAME_HEIGHT);
        getUiCamera().update();
        newCam = new OrthographicCamera();
        newCam.setToOrtho(false, constants.GAME_WIDTH, constants.GAME_HEIGHT);
        newCam.update();
        Gdx.input.setInputProcessor(this);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    void update(float delta) {
        //constants.accelerateSpeed(delta);
        for (background b : backgrounds){
            if ((newCam.position.x - newCam.viewportWidth/2) >
                    b.getPosition().x + constants.BACKGROUND_WIDTH){
                b.update(b.getPosition().x + constants.BACKGROUND_WIDTH * constants.BACKGROUND_COUNT);
            }
        }
        for (ground g : grounds){
            if ((getGameCamera().position.x - getGameCamera().viewportWidth/2) >
                    g.getPosition().x + constants.GROUND_WIDTH){
                g.update(g.getPosition().x + (constants.GROUND_WIDTH) * constants.GROUND_COUNT);
            }
        }
        for (ceiling c : ceilings){
            if ((getGameCamera().position.x - getGameCamera().viewportWidth/2) >
                    c.getPosition().x + constants.CEILING_WIDTH){
                c.update(c.getPosition().x + (constants.CEILING_WIDTH) * constants.CEILING_COUNT);
            }
        }
        for (obstacle item : rocks){
            if (getGameCamera().position.x - getGameCamera().viewportWidth/2 >
                    item.getPosition().x + constants.OBSTACLE_WIDTH){
                item.update((item.getPosition().x + (/*constants.OBSTACLE_WIDTH + */constants.SPACING) * constants.OBSTACLE_COUNT));
                item.setCounted(false);
            }
            if (item.getPosition().x < helicop.getPosition().x && !item.getCounted()){
                score++;
                item.setCounted(true);
            }
            if (!isGameOver) {
                if (item.collision(helicop.getBounds())) {
                    explosion.play();
                    isGameOver = true;
                /*rush.setScreen(new titleScreen(
                        batch, rush
                ));*/
                }
            }
        }
        if (!isGameOver) {
            if (helicop.collision()) {
                explosion.play();
                isGameOver = true;
            /*rush.setScreen(
                    new titleScreen(batch, rush)
            );*/
            }
        }
        if (!isGameOver)
            helicop.update(delta);
        else {
            exp.show(helicop.getPosition().x, helicop.getPosition().y);
            exp.update(delta);
        }
        getGameCamera().position.x = helicop.getPosition().x + constants.CAMERA_OFFSET;
        getGameCamera().update();
        newCam.position.x = getGameCamera().position.x/6 + constants.CAMERA_OFFSET * 2;
        newCam.update();

    }

    @Override
    void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(newCam.combined);
        batch.begin();
        for (int i = 0; i < backgrounds.size; i++){
            batch.draw(backgrounds.get(i).getTexture(), backgrounds.get(i).getPosition().x,
                    backgrounds.get(i).getPosition().y, constants.BACKGROUND_WIDTH, constants.BACKGROUND_HEIGHT);
        }
        //batch.draw(bg, constants.ORIGIN.x/*getGameCamera().position.x - getGameCamera().viewportWidth/2*/,
        //        constants.ORIGIN.y);
        batch.end();

        batch.setProjectionMatrix(getGameCamera().combined);
        batch.begin();
        for (int i = 0; i < rocks.size; i++){
            batch.draw(rocks.get(i).getTexture(), rocks.get(i).getPosition().x, rocks.get(i).getPosition().y,
                    constants.OBSTACLE_WIDTH, constants.OBSTACLE_HEIGHT);
            //rocks.get(i).drawShape(getGameCamera(), shapeRenderer, batch);
        }
        for (int i = 0; i < grounds.size; i++){
            batch.draw(grounds.get(i).getTexture(), grounds.get(i).getPosition().x, grounds.get(i).getPosition().y,
                    constants.GROUND_WIDTH, constants.GROUND_HEIGHT);
        }
        for (int i = 0; i < ceilings.size; i++){
            batch.draw(ceilings.get(i).getTexture(), ceilings.get(i).getPosition().x, ceilings.get(i).getPosition().y,
                    constants.GROUND_WIDTH, constants.GROUND_HEIGHT);
        }
        if (!isGameOver) {
            batch.draw(helicop.getTexture(), helicop.getPosition().x, helicop.getPosition().y,
                    constants.HELICOPTER_WIDTH, constants.HELICOPTER_HEIGHT);
            //helicop.drawShape(getGameCamera(), shapeRenderer, batch);
        }else {
            batch.draw(exp.getTexture(), exp.getPosition().x, exp.getPosition().y,
                    constants.HELICOPTER_WIDTH, constants.HELICOPTER_HEIGHT);
        }
        batch.end();

        batch.setProjectionMatrix(getUiCamera().combined);
        batch.begin();
        font.draw(batch, "" + score, constants.GAME_WIDTH/2, constants.GAME_HEIGHT - 60);
        if (isGameOver) {
            batch.draw(gameover, constants.GAME_WIDTH / 2 - constants.TEXT_WIDTH / 2,
                    constants.GAME_HEIGHT / 2 - constants.TEXT_HEIGHT / 2, constants.TEXT_WIDTH, constants.TEXT_HEIGHT);
        }
        batch.end();
    }

    @Override
    void hidden() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        super.dispose();
        helicop.dispose();
        explosion.dispose();
        shapeRenderer.dispose();
        for (int i = 0; i < rocks.size; i++){
            rocks.get(i).dispose();
        }
        for (int i = 0; i < grounds.size; i++){
            grounds.get(i).dispose();
        }
        for (int i = 0; i < ceilings.size; i++){
            ceilings.get(i).dispose();
        }
        for (int i = 0; i < backgrounds.size; i++){
            backgrounds.get(i).dispose();
        }
        rocks.clear();
        grounds.clear();
        ceilings.clear();
        if (isGameOver){
            exp.dispose();
        }
        font.dispose();
        gameover.dispose();
        score = 0;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!isGameOver)
            helicop.jump();
        else {
            rush.setScreen(
                    new ready_screen(
                            rush
                    )
            );
        }
        return true;
    }
}
