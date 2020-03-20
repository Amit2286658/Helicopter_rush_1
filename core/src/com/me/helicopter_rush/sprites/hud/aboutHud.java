package com.me.helicopter_rush.sprites.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.helicopter_rush.constants;

public class aboutHud {

    private Texture backgroundGroup, about; //title;
    private BitmapFont font;
    private BitmapFont boldFont;
    private SpriteBatch batch;
    private boolean isDrawing;
    private String title = "Helicopter rush",
            creater = "Created by me",
            contact = "contact me directly at:",
            email = "amitkumar13234353@gmail.com",
            project_description = "find the project here on:",
            project_url = "github.com/Amit2286658/Helicopter_rush_1.git",
            version = "v3.0";

    public aboutHud(SpriteBatch batch){
        this.batch = batch;
        init();
    }

    private void init(){
        backgroundGroup = new Texture("about_panel.png");
        about = new Texture("about.png");
        font = new BitmapFont(Gdx.files.internal("kenvector_future_thin.fnt"));
        font.setColor(Color.BROWN);
        font.getData().setScale(0.3f);
        boldFont = new BitmapFont(Gdx.files.internal("kenvector_future.fnt"));
        boldFont.setColor(Color.BROWN);
        boldFont.getData().setScale(0.3f);

        isDrawing = false;
    }

    public void show() {
        isDrawing = true;
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    public void hide(){
        this.isDrawing = false;
    }

    public void draw(){
        if (isDrawing) {
            batch.begin();
            batch.draw(backgroundGroup, constants.GAME_WIDTH / 2 - 240
                    , constants.GAME_HEIGHT / 2 - 170, 480, 340);
            batch.draw(about, constants.GAME_WIDTH/2 - 50,
                    constants.GAME_HEIGHT/2 + 120, 100, 30);
            boldFont.draw(batch, title, constants.GAME_WIDTH / 2 - 85
                    , constants.GAME_HEIGHT / 2 + 50);
            font.draw(batch, creater, constants.GAME_WIDTH/2 - 60
                    , constants.GAME_HEIGHT/2 + 10);
            font.draw(batch, contact, constants.GAME_WIDTH/2 - 100
                    , constants.GAME_HEIGHT/2 - 10);
            font.draw(batch, email, constants.GAME_WIDTH/2 - 135
                    , constants.GAME_HEIGHT/2 - 30);
            font.draw(batch, project_description, constants.GAME_WIDTH/2 -105
                    , constants.GAME_HEIGHT/2 - 50);
            font.draw(batch, project_url, constants.GAME_WIDTH/2 - 215
                    , constants.GAME_HEIGHT/2 - 70);
            font.draw(batch, version, constants.GAME_WIDTH/2 - 20
                    , constants.GAME_HEIGHT/2 - 90);
            batch.end();
        }
    }

    public void dispose(){
        backgroundGroup.dispose();
        about.dispose();
        font.dispose();
        boldFont.dispose();
        isDrawing = false;
    }

}
