package com.me.helicopter_rush;

import com.badlogic.gdx.math.Vector2;

public class constants {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 480;
    public static final int VIEWPORT_WIDTH = GAME_WIDTH / 2;
    public static final int VIEWPORT_HEIGHT = GAME_HEIGHT / 2;
    public static final Vector2 ORIGIN = new Vector2(0,0);
    public static final int GRAVITY = -15;
    public static final int JUMP_IMPULSE = 250;
    public static final int OBSTACLE_WIDTH = 63;
    public static final int OBSTACLE_HEIGHT = 140;
    public static final int OBSTACLE_HEIGHT_OFFSET = 5;
    public static final int HELICOPTER_WIDTH = 40;//35;
    public static final int HELICOPTER_HEIGHT = 32;//29;
    public static final int HELICOPTER_INITIAL_X = 40;
    public static final int HELICOPTER_INITIAL_Y = 120;
    public static final int TEXT_WIDTH = 350;
    public static final int TEXT_HEIGHT = 60;
    public static final int CAMERA_OFFSET = 160;
    public static final int GROUND_WIDTH = 400;
    public static final int GROUND_HEIGHT = 35;
    public static final int CEILING_WIDTH = GROUND_WIDTH;
    public static final int CEILING_HEIGHT = GROUND_HEIGHT;
    public static final int GROUND_COLLISION_OFFSET = GROUND_HEIGHT - 25;
    public static float movement = 100;
    private static final float ACCELERATION = 0.5f;
    public static final int SPACING = 140;
    public static final int OBSTACLE_COUNT = 5;
    public static final int GROUND_COUNT = 3;
    public static final int CEILING_COUNT = GROUND_COUNT;
    public static final int BACKGROUND_COUNT = GROUND_COUNT;
    public static final int BACKGROUND_WIDTH = GAME_WIDTH;
    public static final int BACKGROUND_HEIGHT = GAME_HEIGHT;
    public static final String PREFERENCE = "score_preference";
    public static final String SCORE_PREFERENCE_KEY = "high_score";
    public static final int TABLE_WIDTH = 240;
    public static final int TABLE_HEIGHT = 100;
    public static final int PLAYBUTTON_XOFFSET = 10;
    public static final int PLAYBUTTON_YOFFSET = 35;
    private static float time_counter = 0;
    public static float synchronizeCameraPosition;
    public static void accelerateSpeed(float delta){
        time_counter += delta;
        if (time_counter % 10 == 0){
            movement += ACCELERATION;
            System.out.println(movement);
        }
    }

}
