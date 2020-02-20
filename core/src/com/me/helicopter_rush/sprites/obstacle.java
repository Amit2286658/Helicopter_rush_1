package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.helicopter_rush.constants;

import java.util.Random;

public class obstacle {

    private Vector2 position = new Vector2();
    private TextureRegion image;
    boolean counted;
    private Random rand = new Random();
    private boolean isDown = false;
    private Rectangle bounds;

    public obstacle(float x) {
        this.position.x = x;
        this.image = new TextureRegion(new Texture("new_rock.png"));
        isDown = rand.nextBoolean();
        if (isDown){
            this.position.y = constants.VIEWPORT_HEIGHT - constants.OBSTACLE_HEIGHT;
            bounds = new Rectangle(this.position.x + 16, this.position.y + constants.OBSTACLE_HEIGHT_OFFSET
                    , 20, constants.OBSTACLE_HEIGHT - constants.OBSTACLE_HEIGHT_OFFSET);
        }else{
            this.position.y = constants.ORIGIN.y;
            bounds = new Rectangle(this.position.x + 18, this.position.y, 20,
                    constants.OBSTACLE_HEIGHT - constants.OBSTACLE_HEIGHT_OFFSET);
        }



    }

    public TextureRegion getTexture() {
        if (isDown){
            TextureRegion rockDown = new TextureRegion(this.image);
            rockDown.flip(false, true);
            return rockDown;
        }else {
            return image;
        }
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

    public void dispose(){
        this.image.getTexture().dispose();
    }

    public void update(float x){
        this.position.x = x;
        //this.image = new TextureRegion(new Texture("new_rock.png"));
        isDown = rand.nextBoolean();
        if (isDown){
            this.position.y = constants.VIEWPORT_HEIGHT - constants.OBSTACLE_HEIGHT;
            bounds.setPosition(this.position.x + 18, this.position.y + constants.OBSTACLE_HEIGHT_OFFSET);
        }else{
            this.position.y = constants.ORIGIN.y;
            bounds.setPosition(this.position.x + 18, this.position.y);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean getCounted(){
        return counted;
    }

    public void setCounted(boolean value){
        this.counted = value;
    }

    public boolean collision(Rectangle helicopter){
        return helicopter.overlaps(bounds);
        //return false;
    }

    /*private TextureRegion topObstacle, bottomObstacle;
    private Vector2 topPos, botPos;
    private Random rand;

    public obstacle(float x){
        topObstacle = new TextureRegion(new Texture("rock.png"));
        bottomObstacle = new TextureRegion(topObstacle);
        bottomObstacle.flip(true, true);
        rand = new Random();
    }*/

}