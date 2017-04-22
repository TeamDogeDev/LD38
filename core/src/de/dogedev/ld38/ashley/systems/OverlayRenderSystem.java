package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.sun.media.jfxmediaimpl.MediaDisposer;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.assets.enums.BitmapFonts;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class OverlayRenderSystem extends EntitySystem implements MediaDisposer.Disposable{

    private SpriteBatch batch = new SpriteBatch();
    private OrthographicCamera camera;
    private BitmapFont font;

    public OverlayRenderSystem(OrthographicCamera camera) {
        this.camera = camera;
        font = Statics.asset.getBitmapFont(BitmapFonts.KENNEY_1);
    }

    @Override
    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.BLACK);
        for (int x = 0; x < Statics.settings.tilesX; x++) {
            for (int y = 0; y < Statics.settings.tilesY; y++) {
                font.draw(batch, "(" + x + ", " + y + ")",
                        CoordinateMapper.getTilePosX(x, y)-(Statics.settings.tileWidth>>1),
                        CoordinateMapper.getTilePosY(y) + (Statics.settings.tileHeight>>2),
                        Statics.settings.tileWidth, Align.center, false);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
