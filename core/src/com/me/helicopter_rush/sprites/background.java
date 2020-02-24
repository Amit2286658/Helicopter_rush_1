package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.me.helicopter_rush.constants;

public class background {

    private Texture bg;
    private Vector2 position;

    public background(float x){
        this.bg = new Texture("bg_forest_edited.png");
        this.position = new Vector2(x, constants.ORIGIN.y);
    }

    public void update(float x){
        this.position.x = x;
        this.position.y = constants.ORIGIN.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return bg;
    }

    public void dispose(){
        this.bg.dispose();
    }
}
