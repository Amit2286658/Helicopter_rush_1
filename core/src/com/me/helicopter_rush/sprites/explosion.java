package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class explosion {

    private Animation<Texture> explosion;
    private Vector2 position;
    private float delta;
    private boolean isAnimationLoaded = false;

    public void show(float x, float y){
        if (!isAnimationLoaded) {
            position = new Vector2(x, y);
            explosion = new Animation<Texture>(
                    0.05f,
                    new Texture("exp1.png"),
                    new Texture("exp2.png"),
                    new Texture("exp3.png"),
                    new Texture("exp4.png"),
                    new Texture("exp5.png"),
                    new Texture("exp6.png"),
                    new Texture("exp7.png"),
                    new Texture("exp8.png")
            );
            explosion.setPlayMode(Animation.PlayMode.NORMAL);
            isAnimationLoaded = true;
        }
    }

    public void update(float delta){
        this.delta += delta;
    }

    public Texture getTexture(){
        return explosion.getKeyFrame(this.delta);
    }

    public Vector2 getPosition(){
        return position;
    }

    public void dispose(){
        for (Texture tex : explosion.getKeyFrames()){
            tex.dispose();
        }
    }
}
