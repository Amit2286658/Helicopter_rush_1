package com.me.helicopter_rush.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

import java.util.Locale;

public class high_score {

    private Stage stage;
    private Table table;
    private Label highScoreText, highScoreValue, currentScoreText, currentScoreValue;
    private Label.LabelStyle style1;
    private int high_score = 0;
    private static int current_score = 0;
    private Pixmap bg;
    private TextureRegionDrawable regionDrawable;
    private Preferences preferences;
    private boolean scoreCheckHasRun = false;

    public high_score(SpriteBatch batch, Viewport port){
        stage = new Stage(port, batch);
        init();
        tableBackgroundColor();
        preferences = Gdx.app.getPreferences(constants.PREFERENCE);
        high_score = preferences.getInteger(constants.SCORE_PREFERENCE_KEY);
    }

    private void init(){
        table = new Table();
        table.setBounds(stage.getViewport().getWorldWidth()/2 - constants.TABLE_WIDTH/2,
                (stage.getViewport().getWorldHeight()/2 - constants.TABLE_HEIGHT/2) - 100,
                constants.TABLE_WIDTH, constants.TABLE_HEIGHT);
        style1 = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        highScoreText = new Label("HIGH SCORE", style1);
        highScoreValue = new Label(String.format(Locale.getDefault(), "%03d", high_score), style1);
        currentScoreText = new Label("CURRENT SCORE", style1);
        currentScoreValue = new Label(String.format(Locale.getDefault(), "%03d", current_score), style1);

        table.add(highScoreText).pad(12);
        table.add(highScoreValue).pad(12);
        table.row();
        table.add(currentScoreText).pad(12);
        table.add(currentScoreValue).pad(12);

        stage.addActor(table);

    }

    private void tableBackgroundColor(){
        bg = new Pixmap(constants.TABLE_WIDTH, constants.TABLE_HEIGHT, Pixmap.Format.RGB565);
        bg.setColor(Color.BROWN);
        bg.fill();
        regionDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(bg)));
        this.table.setBackground(regionDrawable);
    }

    public void draw(OrthographicCamera cam, int score){
        stage.getBatch().setProjectionMatrix(cam.combined);
        runScoreCheck(score);
        stage.draw();
    }

    public void resetScore(){
        scoreCheckHasRun = false;
    }

    private void runScoreCheck(int score){
        if (!scoreCheckHasRun){
            high_score = preferences.getInteger(constants.SCORE_PREFERENCE_KEY);
            current_score = score;
            if (high_score < current_score){
                preferences.putInteger(constants.SCORE_PREFERENCE_KEY, current_score);
                preferences.flush();
                highScoreText.setText("NEW HIGH SCORE");
                highScoreValue.setText(String.format(Locale.getDefault(), "%03d", current_score));
            }else {
                highScoreText.setText("HIGH SCORE");
                highScoreValue.setText(String.format(Locale.getDefault(), "%03d", high_score));
            }

            currentScoreValue.setText(String.format(Locale.getDefault(), "%03d", current_score));

            scoreCheckHasRun = true;
        }
    }

    public void dispose(){
        stage.dispose();

        bg.dispose();
        regionDrawable.getRegion().getTexture().dispose();
    }

}
