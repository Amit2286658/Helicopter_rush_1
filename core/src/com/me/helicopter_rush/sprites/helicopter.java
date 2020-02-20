package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.helicopter_rush.constants;

public class helicopter {

    private Vector2 position;
    private Vector2 velocity;
    //private Texture texture;
    private Animation<Texture> helicopters;
    private float delta;
    private Rectangle bounds;
    private boolean moveHorizontalOnly = false;

    public helicopter(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
         //texture = new Texture("plane1.png");
         helicopters = new Animation<Texture>(0.05f,
                 new Texture("plane1.png"),
                 new Texture("plane2.png"),
                 new Texture("plane3.png"),
                 new Texture("plane2.png")
         );
         helicopters.setPlayMode(Animation.PlayMode.LOOP);
         bounds = new Rectangle(
                 this.position.x,
                 this.position.y,
                 constants.HELICOPTER_WIDTH,
                 constants.HELICOPTER_HEIGHT
         );
    }

    public void jump(){
        velocity.y = constants.JUMP_IMPULSE;

    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Texture getTexture() {
        return helicopters.getKeyFrame(delta);
    }

    /*public void drawShape(OrthographicCamera cam, SpriteBatch batch){
        batch.end();
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        shapeRenderer.end();
        batch.begin();
    }*/

    public Texture getTexture(float delta){
        this.delta += delta;
        return helicopters.getKeyFrame(this.delta);
    }

    public void moveHorizontalOnly(){
        this.moveHorizontalOnly = true;
    }

    public void update(float delta){
        this.delta += delta;
        if (!moveHorizontalOnly) {
            if (position.y > 0)
                velocity.add(0, constants.GRAVITY);
        }
        velocity.scl(delta);
        position.add(constants.movement * delta, velocity.y);
        velocity.scl(1/delta);
        if (position.y < 0)
            position.y = 0;

        bounds.setPosition(this.position.x, this.position.y);
    }

    public void dispose(){
        for (Texture tex : helicopters.getKeyFrames()){
            tex.dispose();
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean collision(){
        return this.position.y <= constants.ORIGIN.y + constants.GROUND_COLLISION_OFFSET ||
                this.position.y >= constants.VIEWPORT_HEIGHT - constants.GROUND_HEIGHT;
        //return false;
    }
    public Animation<Texture> getHelicopters() {
        return helicopters;
    }
}
