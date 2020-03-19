package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class explosion {

    private Animation<Texture> explosion;
    private Vector2 position;
    private float delta;
    private boolean isAnimationLoaded = false, isTextureLoaded = false;
    private Texture tex1, tex2, tex3, tex4, tex5, tex6, tex7, tex8;

    public void show(float x, float y){
        if (!isAnimationLoaded) {
            if (!isTextureLoaded) {
                tex1 = new Texture("exp1.png");
                tex2 = new Texture("exp2.png");
                tex3 = new Texture("exp3.png");
                tex4 = new Texture("exp4.png");
                tex5 = new Texture("exp5.png");
                tex6 = new Texture("exp6.png");
                tex7 = new Texture("exp7.png");
                tex8 = new Texture("exp8.png");
                isTextureLoaded = true;
            }
            position = new Vector2(x, y);
            explosion = new Animation<Texture>(0.05f,
                    tex1, tex2, tex3, tex4, tex5, tex6, tex7, tex8);
            isAnimationLoaded = true;
        }
    }

    public void reset(){
        isAnimationLoaded = false;
        this.delta = 0;
    }

    public void update(float delta){
        this.delta += delta;
    }

    public Texture getTexture(){
        return explosion.getKeyFrame(this.delta, false);
    }

    public Vector2 getPosition(){
        return position;
    }

    public void dispose(){
        for (Texture tex : explosion.getKeyFrames()){
            tex.dispose();
        }
        tex1.dispose();
        tex2.dispose();
        tex3.dispose();
        tex4.dispose();
        tex5.dispose();
        tex6.dispose();
        tex7.dispose();
        tex8.dispose();
    }
}
