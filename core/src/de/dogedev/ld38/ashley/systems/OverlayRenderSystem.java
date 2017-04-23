package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;

/**
 * Created by elektropapst on 23.04.2017.
 */
public class OverlayRenderSystem extends EntitySystem implements Disposable {

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    public OverlayRenderSystem(OrthographicCamera camera) {
        this.camera = camera;
        spriteBatch = new SpriteBatch();
    }

    private Vector3 mouse = Vector3.Zero;

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

//        mouse.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
        mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 unproject = camera.unproject(mouse);
        Vector2 tile = CoordinateMapper.getTile((int) unproject.x, (int) unproject.y);

        if(tile != null) {
            float tileScreenX = CoordinateMapper.getTilePosX(tile);
            float tileScreenY = CoordinateMapper.getTilePosY((int) tile.y);

            boolean clickable = getEngine().getSystem(GridSystem.class).isClickable((int) tile.x, (int) tile.y);
            spriteBatch.setProjectionMatrix(camera.combined);
            spriteBatch.begin();
            spriteBatch.draw(
                    clickable ? Statics.asset.getTextureAtlasRegion(Key.TILES_TILEOVERLAYGREEN) : Statics.asset.getTextureAtlasRegion(Key.TILES_TILEOVERLAYRED),
                    tileScreenX- (Statics.settings.tileWidth >> 1), tileScreenY - (Statics.settings.tileHeight >> 1));
            spriteBatch.end();
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
