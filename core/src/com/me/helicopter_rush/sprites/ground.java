package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.me.helicopter_rush.constants;

public class ground {

    private TextureRegion texture;
    private Vector2 position;

    public ground(float x){
        texture = new TextureRegion(new Texture("Ground.png"));
        position = new Vector2(x, constants.ORIGIN.y);
    }

    public void update(float x){
        this.position.x = x;
        this.position.y = constants.ORIGIN.y;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void drawGroundDebugLines(OrthographicCamera cam, ShapeRenderer renderer, SpriteBatch batch){
        batch.end();
        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.line(new Vector2(this.position.x, constants.ORIGIN.y + constants.GROUND_COLLISION_OFFSET),
                new Vector2(this.position.x + constants.GROUND_WIDTH,
                        constants.ORIGIN.y + constants.GROUND_COLLISION_OFFSET));
        renderer.end();
        batch.begin();
    }

    public void dispose(){
        texture.getTexture().dispose();
    }

}
