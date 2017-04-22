package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.media.jfxmediaimpl.MediaDisposer;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class OverlayRenderSystem extends EntitySystem implements MediaDisposer.Disposable{

    private SpriteBatch batch = new SpriteBatch();
    private OrthographicCamera camera;
    private BitmapFont font;

    public OverlayRenderSystem(OrthographicCamera camera) {
        this.camera = camera;
        font = new BitmapFont();
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        font.draw(batch, "20|10", 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
