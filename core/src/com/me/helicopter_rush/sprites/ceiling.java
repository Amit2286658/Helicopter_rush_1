package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.helicopter_rush.constants;

public class ceiling {

    private TextureRegion texture;
    private Vector2 position;

    public ceiling(float x){
        texture = new TextureRegion(new Texture("ground_new.png"));
        texture.flip(true, true);
        position = new Vector2(x, constants.VIEWPORT_HEIGHT - constants.GROUND_HEIGHT);
    }

    public void update(float x){
        this.position.x = x;
        this.position.y = constants.VIEWPORT_HEIGHT - constants.GROUND_HEIGHT;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose(){
        texture.getTexture().dispose();
    }
}
