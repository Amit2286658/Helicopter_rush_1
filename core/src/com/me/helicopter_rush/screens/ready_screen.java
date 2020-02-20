package com.me.helicopter_rush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.me.helicopter_rush.constants;
import com.me.helicopter_rush.helicopter_rush;
import com.me.helicopter_rush.sprites.background;
import com.me.helicopter_rush.sprites.ceiling;
import com.me.helicopter_rush.sprites.ground;
import com.me.helicopter_rush.sprites.helicopter;

public class ready_screen extends screen {

    private helicopter_rush rush;
    private Texture readyTex, bg;
    private helicopter helicop;
    private Array<ground> grounds = new Array<ground>();
    private Array<ceiling> ceilings = new Array<ceiling>();
    private Array<background> backgrounds = new Array<background>();
    private OrthographicCamera newCam;

    public ready_screen(helicopter_rush rush){
        super(rush.getBatch(), true);
        this.rush = rush;
    }

    @Override
    public void show() {
        super.show();
        readyTex = new Texture("new get ready.png");
        //bg = new Texture("background.png");
        helicop = new helicopter(40, 120);
        helicop.moveHorizontalOnly();
        for (int i = 0; i < constants.BACKGROUND_COUNT; i++){
            backgrounds.add(new background(i * constants.BACKGROUND_WIDTH - constants.CAMERA_OFFSET));
        }
        for (int i = 0; i < constants.GROUND_COUNT; i++){
            grounds.add(new ground((i * constants.GROUND_WIDTH) - constants.CAMERA_OFFSET));
        }
        for (int i = 0; i < constants.CEILING_COUNT; i++){
            ceilings.add(new ceiling((i * constants.CEILING_WIDTH) - constants.CAMERA_OFFSET));
        }

        getGameCamera().setToOrtho(false, constants.VIEWPORT_WIDTH, constants.VIEWPORT_HEIGHT);
        getGameCamera().update();
        getUiCamera().setToOrtho(false, constants.GAME_WIDTH, constants.GAME_HEIGHT);
        getUiCamera().update();
        newCam = new OrthographicCamera();
        newCam.setToOrtho(false, constants.GAME_WIDTH, constants.GAME_HEIGHT);
        newCam.update();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    void update(float delta) {

        for (background b : backgrounds){
            if ((newCam.position.x - newCam.viewportWidth/2) >
                    b.getPosition().x + constants.BACKGROUND_WIDTH){
                b.update(b.getPosition().x + constants.BACKGROUND_WIDTH * constants.BACKGROUND_COUNT);
            }
        }
        for (int i = 0; i < grounds.size; i++){
            if ((getGameCamera().position.x - getGameCamera().viewportWidth/2) >
                    grounds.get(i).getPosition().x + constants.GROUND_WIDTH){
                grounds.get(i).update(grounds.get(i).getPosition().x + (constants.GROUND_WIDTH) * constants.GROUND_COUNT);
            }
        }
        for (ceiling c : ceilings){
            if ((getGameCamera().position.x - getGameCamera().viewportWidth/2) >
                    c.getPosition().x + constants.CEILING_WIDTH){
                c.update(c.getPosition().x + (constants.CEILING_WIDTH) * constants.CEILING_COUNT);
            }
        }

        helicop.update(delta);

        getGameCamera().position.x = helicop.getPosition().x + constants.CAMERA_OFFSET;
        getGameCamera().update();
        newCam.position.x = getGameCamera().position.x/6  + constants.CAMERA_OFFSET * 2;
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

        batch.setProjectionMatrix(getUiCamera().combined);
        batch.begin();
        //batch.draw(bg, 0, 0);
        batch.draw(readyTex, constants.GAME_WIDTH/2-constants.TEXT_WIDTH/2,
                constants.GAME_HEIGHT/2-constants.TEXT_HEIGHT/2, constants.TEXT_WIDTH, constants.TEXT_HEIGHT);
        batch.end();

        batch.setProjectionMatrix(getGameCamera().combined);
        batch.begin();
        batch.draw(helicop.getTexture(), helicop.getPosition().x, helicop.getPosition().y,
                constants.HELICOPTER_WIDTH, constants.HELICOPTER_HEIGHT);
        for (int i = 0; i < grounds.size; i++){
            batch.draw(grounds.get(i).getTexture(), grounds.get(i).getPosition().x, grounds.get(i).getPosition().y,
                    constants.GROUND_WIDTH, constants.GROUND_HEIGHT);
        }
        for (int i = 0; i < grounds.size; i++){
            batch.draw(ceilings.get(i).getTexture(), ceilings.get(i).getPosition().x, ceilings.get(i).getPosition().y,
                    constants.CEILING_WIDTH, constants.CEILING_HEIGHT);
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
        readyTex.dispose();
        //bg.dispose();
        constants.synchronizeCameraPosition = helicop.getPosition().x;
        helicop.dispose();
        for (int i = 0; i < grounds.size; i++){
            grounds.get(i).dispose();
        }
        for (int i = 0; i < ceilings.size; i++){
            ceilings.get(i).dispose();
        }
        for (int i = 0; i < backgrounds.size; i++){
            backgrounds.get(i).dispose();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        rush.setScreen(new gameScreen(rush));
        return true;
    }
}
