package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.helicopter_rush.constants;

public class ground {

    private TextureRegion texture;
    private Vector2 position;

    public ground(float x){
        texture = new TextureRegion(new Texture("ground_new.png"));
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

    public void dispose(){
        texture.getTexture().dispose();
    }

}
