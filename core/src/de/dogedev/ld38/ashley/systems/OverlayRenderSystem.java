package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    private Vector3 mouse = Vector3.Zero;
    private Vector2 arrowTilePosition;

    public OverlayRenderSystem(OrthographicCamera camera, int priority) {
        super(priority);
        this.camera = camera;
        spriteBatch = new SpriteBatch();
        arrowTilePosition = new Vector2(0, Statics.settings.tilesY-1);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }


    private float arrowOffset = 0;
    private float deltaSum = 0;
    private float maxOffset = 20;
    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        deltaSum += deltaTime*2;
        arrowOffset = MathUtils.sin(deltaSum)*maxOffset;

        mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 unproject = camera.unproject(mouse);
        Vector2 tile = CoordinateMapper.getTile((int) unproject.x, (int) unproject.y);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        if(tile != null) {
            float tileScreenX = CoordinateMapper.getTilePosX(tile);
            float tileScreenY = CoordinateMapper.getTilePosY((int) tile.y);

            boolean clickable = getEngine().getSystem(GridSystem.class).isClickable((int) tile.x, (int) tile.y, Input.Buttons.LEFT);
            spriteBatch.draw(
                    clickable ? Statics.asset.getTextureAtlasRegion(Key.TILES_TILEOVERLAYGREEN) : Statics.asset.getTextureAtlasRegion(Key.TILES_TILEOVERLAYRED),
                    tileScreenX- (Statics.settings.tileWidth >> 1), tileScreenY - (Statics.settings.tileHeight >> 1));
        }
        float arrowTileScreenX = CoordinateMapper.getTilePosX(arrowTilePosition);
        float arrowTileScreenY = CoordinateMapper.getTilePosY((int) arrowTilePosition.y);
        spriteBatch.draw(
                Statics.asset.getTextureAtlasRegion(Key.ICONS_ARROWDOWN),
                arrowTileScreenX - (Statics.settings.tileWidth >> 1), arrowTileScreenY - (Statics.settings.tileHeight >> 1) + arrowOffset + maxOffset);
        spriteBatch.end();
    }

    public void setArrowTilePosition(Vector2 arrowTilePosition) {
        this.arrowTilePosition.set(arrowTilePosition);
    }

    public Vector2 getArrowTilePosition() {
        return arrowTilePosition;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
