package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.me.helicopter_rush.constants;

import java.util.Random;

public class obstacle {

    private Vector2 position = new Vector2();
    private TextureRegion image;
    private boolean counted;
    private Random rand = new Random();
    private boolean isDown = false, isReveresed = false;
    private Rectangle bounds;
    private Rectangle bounds2;
    private Rectangle bounds3;
    private Polygon triangle1,triangle2;
    private float[] triangle1VerticesDown, triangle1Vertices, triangle2VerticesDown, triangle2Vertices;

    public obstacle(float x) {
        this.position.x = x;
        this.image = new TextureRegion(new Texture("r.png"));
        isDown = rand.nextBoolean();
        isReveresed = rand.nextBoolean();

        triangle1VerticesDown = new float[]{
                this.position.x + constants.TRIANGLE1_POINT1_X_OFFSET,
                constants.VIEWPORT_HEIGHT - constants.TRIANGLE1_POINT1_Y_OFFSET,
                this.position.x + constants.TRIANGLE1_POINT2_X_OFFSET,
                constants.VIEWPORT_HEIGHT - constants.TRIANGLE1_POINT2_Y_OFFSET,
                this.position.x + constants.TRIANGLE1_POINT3_X_OFFSET,
                constants.VIEWPORT_HEIGHT - constants.TRIANGLE1_POINT3_Y_OFFSET - 15
        };
        triangle1Vertices = new float[]{
                this.position.x + constants.TRIANGLE1_POINT1_X_OFFSET,
                constants.ORIGIN.y + constants.TRIANGLE1_POINT1_Y_OFFSET,
                this.position.x + constants.TRIANGLE1_POINT2_X_OFFSET,
                constants.ORIGIN.y + constants.TRIANGLE1_POINT2_Y_OFFSET,
                this.position.x + constants.TRIANGLE1_POINT3_X_OFFSET,
                constants.ORIGIN.y + constants.TRIANGLE1_POINT3_Y_OFFSET + 15
        };
        triangle2VerticesDown = new float[]{
                this.position.x + constants.TRIANGLE2_POINT1_X_OFFSET,
                constants.VIEWPORT_HEIGHT,
                this.position.x + constants.TRIANGLE2_POINT2_X_OFFSET,
                constants.VIEWPORT_HEIGHT,
                this.position.x + constants.TRIANGLE2_POINT3_X_OFFSET,
                constants.VIEWPORT_HEIGHT - constants.TRIANGLE2_POINT_Y_OFFSET
        };
        triangle2Vertices = new float[]{
                this.position.x + constants.TRIANGLE2_POINT1_X_OFFSET,
                constants.ORIGIN.y,
                this.position.x + constants.TRIANGLE2_POINT2_X_OFFSET,
                constants.ORIGIN.y,
                this.position.x + constants.TRIANGLE2_POINT3_X_OFFSET,
                constants.ORIGIN.y + constants.TRIANGLE2_POINT_Y_OFFSET
        };
        /*the position is kept to suit better the texture drawing rather than maintaining the bounds
        * for example, we could set the y axis to the viewport height, but then to draw the
        * sprite back on the screen we would need to subtract a fixed amount of height to get
        * to the position where we can draw the sprite, this approach would save us time and effort to maintain
        * the bounds ,but it's just that i prefer it this way more, we want to keep the code in the main game
        * screen as minimal as possible, another approach is to create a new method to return the exact height
        * we want for the sprite, while also keeping the y axis at the viewport height position to better maintain
        * the bounds, and i'm definitely thinking into it*/
        if (isDown){
            this.position.y = constants.VIEWPORT_HEIGHT - constants.OBSTACLE_HEIGHT;
            bounds = new Rectangle(this.position.x + 20, this.position.y + 60 + constants.OBSTACLE_HEIGHT_OFFSET
                    , 100, 60);
            bounds2 = new Rectangle(this.position.x + 40, this.position.y + 60 - 15 + constants.OBSTACLE_HEIGHT_OFFSET,
                    100, 15);
            bounds3 = new Rectangle(this.position.x + 18 + 85, constants.VIEWPORT_HEIGHT - 100,
                    60, 100);
            triangle1 = new Polygon();
            triangle1.setVertices(triangle1VerticesDown);
            triangle2 = new Polygon();
            triangle2.setVertices(triangle2VerticesDown);
        }else{
            this.position.y = constants.ORIGIN.y;
            bounds = new Rectangle(this.position.x + 20, this.position.y, 100,
                    60);
            bounds2 = new Rectangle(this.position.x + 40, this.position.y + 60, 100, 15);
            bounds3 = new Rectangle(this.position.x + 18 + 85, this.position.y,
                    60, 100);
            triangle1 = new Polygon();
            triangle1.setVertices(triangle1Vertices);
            triangle2 = new Polygon();
            triangle2.setVertices(triangle2Vertices);
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

    public void drawShape(OrthographicCamera cam, ShapeRenderer renderer, SpriteBatch batch){
        batch.end();
        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        renderer.rect(bounds2.x, bounds2.y, bounds2.width, bounds2.height);
        renderer.rect(bounds3.x, bounds3.y, bounds3.width, bounds3.height);
        renderer.polygon(triangle1.getVertices());
        renderer.polygon(triangle2.getVertices());
        renderer.end();
        batch.begin();
    }

    public void drawCollisionDebugLines(OrthographicCamera cam, ShapeRenderer renderer, SpriteBatch batch) {
        batch.end();
        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.rect(this.position.x, this.position.y, constants.OBSTACLE_WIDTH, constants.OBSTACLE_HEIGHT);
        renderer.end();
        batch.begin();
    }

    public void dispose(){
        this.image.getTexture().dispose();
    }

    public void update(float x){
        this.position.x = x;
        //this.image = new TextureRegion(new Texture("new_rock.png"));
        isDown = rand.nextBoolean();
        if (isDown){
            this.position.y = constants.VIEWPORT_HEIGHT - constants.OBSTACLE_HEIGHT;
            bounds.setPosition(this.position.x + 20, this.position.y + constants.OBSTACLE_HEIGHT_OFFSET + 60);
            bounds2.setPosition(this.position.x + 40,
                    this.position.y + constants.OBSTACLE_HEIGHT_OFFSET + 60 - 15);
            bounds3.setPosition(this.position.x + 18 + 85, constants.VIEWPORT_HEIGHT - 100);

            triangle1VerticesDown[0] = this.position.x + constants.TRIANGLE1_POINT1_X_OFFSET;
            triangle1VerticesDown[2] = this.position.x + constants.TRIANGLE1_POINT2_X_OFFSET;
            triangle1VerticesDown[4] = this.position.x + constants.TRIANGLE1_POINT3_X_OFFSET;
            triangle1.setVertices(triangle1VerticesDown);

            triangle2VerticesDown[0] = this.position.x + constants.TRIANGLE2_POINT1_X_OFFSET;
            triangle2VerticesDown[2] = this.position.x + constants.TRIANGLE2_POINT2_X_OFFSET;
            triangle2VerticesDown[4] = this.position.x + constants.TRIANGLE2_POINT3_X_OFFSET;
            triangle2.setVertices(triangle2VerticesDown);

        }else{
            this.position.y = constants.ORIGIN.y;
            bounds.setPosition(this.position.x + 20, this.position.y);
            bounds2.setPosition(this.position.x + 40, this.position.y + 60);
            bounds3.setPosition(this.position.x + 18 + 85, this.position.y);

            triangle1Vertices[0] = this.position.x + constants.TRIANGLE1_POINT1_X_OFFSET;
            triangle1Vertices[2] = this.position.x + constants.TRIANGLE1_POINT2_X_OFFSET;
            triangle1Vertices[4] = this.position.x + constants.TRIANGLE1_POINT3_X_OFFSET;
            triangle1.setVertices(triangle1Vertices);

            triangle2Vertices[0] = this.position.x + constants.TRIANGLE2_POINT1_X_OFFSET;
            triangle2Vertices[2] = this.position.x + constants.TRIANGLE2_POINT2_X_OFFSET;
            triangle2Vertices[4] = this.position.x + constants.TRIANGLE2_POINT3_X_OFFSET;
            triangle2.setVertices(triangle2Vertices);
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

    public boolean collision(Rectangle[] helicopter){
        return overLaps(helicopter)
                || polygonRectCollisionDetection(triangle1, helicopter)
                || polygonRectCollisionDetection(triangle2, helicopter);
        //return false;
    }

    private boolean overLaps(Rectangle[] rectangles){
        for (Rectangle rect : rectangles) {
            if (rect.overlaps(bounds) || rect.overlaps(bounds2) || rect.overlaps(bounds3)) {
                return true;
            }
        }
        return false;
    }

    public boolean polygonRectCollisionDetection(Polygon poly, Rectangle[] rect){
        for (Rectangle rectangle : rect) {
            float[] rectPolyVertices = new float[]{
                    rectangle.getX(),
                    rectangle.getY(),
                    rectangle.getX() + rectangle.getWidth(),
                    rectangle.getY(),
                    rectangle.getX() + rectangle.getWidth(),
                    rectangle.getY() + rectangle.getHeight(),
                    rectangle.getX(),
                    rectangle.getY() + rectangle.getHeight()
            };
            Polygon p = new Polygon();
            p.setVertices(rectPolyVertices);

            if (Intersector.overlapConvexPolygons(p, poly)) return true;
        }
        return false;

        /*boolean collision = false;

        Array<Vector2> triangle1Down = new Array<Vector2>();
        triangle1Down.add(new Vector2(triangle1VerticesDown[0], triangle1VerticesDown[1]));
        triangle1Down.add(new Vector2(triangle1VerticesDown[2], triangle1VerticesDown[3]));
        triangle1Down.add(new Vector2(triangle1VerticesDown[4], triangle1VerticesDown[5]));

        Array<Vector2> triangle1 = new Array<Vector2>();
        triangle1.add(new Vector2(triangle1Vertices[0], triangle1Vertices[1]));
        triangle1.add(new Vector2(triangle1Vertices[2], triangle1Vertices[3]));
        triangle1.add(new Vector2(triangle1Vertices[4], triangle1Vertices[5]));

        Array<Vector2> triangle2Down = new Array<Vector2>();
        triangle2Down.add(new Vector2(triangle2VerticesDown[0], triangle2VerticesDown[1]));
        triangle2Down.add(new Vector2(triangle2VerticesDown[2], triangle2VerticesDown[3]));
        triangle2Down.add(new Vector2(triangle2VerticesDown[4], triangle2VerticesDown[5]));

        Array<Vector2> triangle2 = new Array<Vector2>();
        triangle2.add(new Vector2(triangle2Vertices[0], triangle2Vertices[1]));
        triangle2.add(new Vector2(triangle2Vertices[2], triangle2Vertices[3]));
        triangle2.add(new Vector2(triangle2Vertices[4], triangle2Vertices[5]));

        Array<Vector2> rectangleVertices = new Array<Vector2>();
        rectangleVertices.add(new Vector2(rect.getX(), rect.getY()));
        rectangleVertices.add(new Vector2(rect.getX() + rect.getWidth(), rect.getY()));
        rectangleVertices.add(new Vector2(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight()));
        rectangleVertices.add(new Vector2(rect.getX(), rect.getY() + rect.getHeight()));

        for (int i = 0; i < rectangleVertices.size; i++){
            if (Intersector.isPointInTriangle(rectangleVertices.get(i),
                    triangle1.get(0), triangle1.get(1), triangle1.get(2))){
                collision = true;
                break;
            }
        }
        *//*if (!collision){
            for (int i = 0; i < rectangleVertices.size; i++){
                if (Intersector.isPointInTriangle(rectangleVertices.get(i),
                        triangle2.get(0), triangle2.get(1), triangle2.get(2))){
                    collision = true;
                    break;
                }
            }
        }*//*
        return collision;*/
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