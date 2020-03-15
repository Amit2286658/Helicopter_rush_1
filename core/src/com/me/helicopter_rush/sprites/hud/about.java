package com.me.helicopter_rush.sprites.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.helicopter_rush.constants;

@Deprecated
public class about {

    private boolean isShowing;
    private Label project_url, creator, title, project_description, version, contact;
    private Label.LabelStyle style;
    private BitmapFont font;
    private Table table;
    private Pixmap bg;
    private TextureRegionDrawable regionDrawable;
    private Stage stage;

    public about(SpriteBatch batch, Viewport viewport){
        this.stage = new Stage(viewport, batch);
        init();
        tableBackgroundColor();
    }

    private void init(){
        font = new BitmapFont(Gdx.files.internal("roboto_light_bmf.fnt"));
        font.getData().setScale(0.3f);
        style = new Label.LabelStyle(font, Color.WHITE);
        table = new Table();
        table.setBounds(constants.GAME_WIDTH/2 - constants.GAME_WIDTH/4,
                constants.GAME_HEIGHT/2 - constants.GAME_HEIGHT/4,
                constants.GAME_WIDTH/2, constants.GAME_HEIGHT/2);
        project_url = new Label("github.com/Amit2286658/Helicopter_rush_1.git", style);
        creator = new Label("Created by me", style);
        title = new Label("Helicopter rush", style);
        project_description = new Label("find the project here on: ", style);
        version = new Label("v1.0", style);
        contact = new Label("contact me directly at: amitkumar13234353@gmail.com", style);

        table.add(title).pad(15).expandX();
        table.row();
        table.add(creator).pad(10).expandX();
        table.row();
        table.add(contact).pad(10).expandX();
        table.row();
        table.add(project_description).pad(10).expandX();
        table.row();
        table.add(project_url).pad(10).expandX();
        table.row();
        table.add(version).pad(10).expandX();

        stage.addActor(table);

        isShowing = false;
    }

    public void show(){
        this.isShowing = true;
    }

    public void hide(){
        this.isShowing = false;
    }

    public void draw(){
        if (isShowing)
            stage.draw();
    }

    public boolean isShowing() {
        return isShowing;
    }

    private void tableBackgroundColor(){
        bg = new Pixmap(constants.GAME_WIDTH/2, constants.GAME_HEIGHT/2, Pixmap.Format.RGBA8888);
        bg.setColor(Color.BROWN);
        bg.fill();
        bg.setBlending(Pixmap.Blending.None);
        regionDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(bg)));
        this.table.setBackground(regionDrawable);
    }

    public void dispose(){
        stage.dispose();
        bg.dispose();
        regionDrawable.getRegion().getTexture().dispose();
        font.dispose();
    }
}
