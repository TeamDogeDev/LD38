package de.dogedev.ld38.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.dogedev.ld38.LDGame;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.assets.enums.Musics;
import de.dogedev.ld38.screens.actors.ImageActor;
import de.dogedev.ld38.screens.actors.TextActor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by elektropapst on 23.04.2017.
 */
public class FinishScreen implements Screen {
    private Batch batch;
    private Texture tex;
    private Stage stage;
    private TextActor text;
    private Music music;
    private String firstLine;

    public FinishScreen(String firstLine) {
        this.firstLine = firstLine;
        init();
    }

    private void init() {
        stage = new Stage();
        batch = new SpriteBatch();
        music = Statics.asset.getMusic(Musics.VICTORY);//.play();
        music.setVolume(Statics.settings.musicVolume);

        tex = new Texture(Gdx.files.internal("finishscreen.png"));

        stage.setViewport(new FitViewport(1280, 720));

        Actor background = new ImageActor(tex);
        background.setPosition(0,0);
        stage.addActor(background);

        TextActor continueText = new TextActor("Press <Space> or <ESC> to restart", 1270, Align.left);
        continueText.setPosition(10, 700);
        continueText.setColor(Color.DARK_GRAY);
        stage.addActor(continueText);

        TextActor text;

        text = new TextActor(firstLine, 1280, Align.center);
        text.setPosition(0, -300);
        text.setColor(Color.BLACK);
        text.setScale(3);
        text.addAction(sequence(delay(0), moveTo(0,700, 2),delay(2), moveBy(0, 800, 2)));
        stage.addActor(text);

        text = new TextActor("Made for Ludum Dare 38 Jam", 1280, Align.center);
        text.setPosition(0, -300);
        text.setColor(Color.BLACK);
        text.setScale(1);
        text.addAction(sequence(delay(0), moveTo(0, 650, 2),delay(2), moveBy(0, 800, 2)));
        stage.addActor(text);

        text = new TextActor("By Elektropapst, Meisterfuu", 1280, Align.center);
        text.setPosition(0, -300);
        text.setColor(Color.BLACK);
        text.setScale(2f);
        text.addAction(sequence(delay(0), moveTo(0, 670, 2),delay(2), moveBy(0, 800, 2)));
        stage.addActor(text);

        text = new TextActor("Made with LibGDX and Ashley", 1280, Align.center);
        text.setPosition(0, -300);
        text.setColor(Color.BLACK);
        text.setScale(3);
        text.addAction(sequence(delay(4), moveTo(0, 700, 2),delay(2), moveBy(0, 800, 2)));
        stage.addActor(text);

        text = new TextActor("Assets from Kenny.nl", 1280, Align.center);
        text.setPosition(0, -300);
        text.setColor(Color.BLACK);
        text.setScale(2);
        text.addAction(sequence(delay(8), moveTo(0, 700, 2),delay(2), moveBy(0, 800, 2)));
        stage.addActor(text);

        text = new TextActor("Music: www.bensound.com", 1280, Align.center);
        text.setPosition(0, -300);
        text.setColor(Color.BLACK);
        text.setScale(2);
        text.addAction(sequence(delay(12), moveTo(0, 700, 2),delay(2), moveBy(0, 800, 2)));
        stage.addActor(text);
//
//        text = new TextActor("Grass sound from duckduckpony (CC BY 3.0)", 1280, Align.center);
//        text.setPosition(0, -300);
//        text.setColor(Color.BLACK);
//        text.setScale(5);
//        text.addAction(sequence(delay(16), moveTo(0, 500, 2),delay(2), moveBy(0, 800, 2)));
//        stage.addActor(text);

        text = new TextActor("The End!", 1280, Align.center);
        text.setPosition(0, -300);
        text.setColor(Color.BLACK);
        text.setScale(5);
        text.addAction(sequence(delay(21), moveTo(0, 700, 2),delay(10), moveBy(0, 800, 2)));
        stage.addActor(text);

        text = new TextActor("TeamDogeDev", 1280, Align.center);
        text.setPosition(0, -300);
        text.setColor(Color.BLACK);
        text.setScale(5);
        text.addAction(sequence(delay(35), moveTo(0, 700, 2)));


        stage.addActor(text);
        text.setScale(1);
    }

    @Override
    public void show() {
        music.stop();
        music.play();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        if(
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)
        ) {
            LDGame.game.setScreen(new GameScreen());
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void hide() {
        music.pause();
    }

    @Override
    public void dispose() {
        music.stop();
    }
}
