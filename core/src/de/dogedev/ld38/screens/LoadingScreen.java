package de.dogedev.ld38.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.dogedev.ld38.LDGame;
import de.dogedev.ld38.Statics;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class LoadingScreen implements Screen {


    private ShapeRenderer shapeRenderer;

    private Batch batch;
    private Texture tex;

    public LoadingScreen() {
        init();
        Statics.initCat();
    }

    private void update(float delta) {
        if (Statics.asset.load()) {
                LDGame.game.setScreen( new GameScreen());
//                LDGame.game.setScreen( new FinishScreen("You won"));
        }
    }

    private void init() {
        batch = new SpriteBatch();
        tex = new Texture(Gdx.files.internal("titlescreen.png"));
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);
        float progress = Statics.asset.progress();
        float val = 191 * progress;
        Gdx.gl.glClearColor(val / 255f, val / 255f, val / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(tex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
        float width = 300;
        float height = 20;
        float x = (Gdx.graphics.getWidth() - width) / 2;
        float y = (Gdx.graphics.getHeight() - height) / 2;
        shapeRenderer.rect(x, y, width * progress, height);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        tex.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
