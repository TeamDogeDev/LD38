package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.GridComponent;
import de.dogedev.ld38.ashley.components.TilePositionComponent;
import de.dogedev.ld38.ashley.components.UnitComponent;
import de.dogedev.ld38.assets.enums.BitmapFonts;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GridSystem extends EntitySystem implements Disposable {

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(GridComponent.class, TilePositionComponent.class, UnitComponent.class).get());
    }

    private SpriteBatch batch = new SpriteBatch();
    private OrthographicCamera camera;
    private BitmapFont font;

    public GridSystem(OrthographicCamera camera) {
        this.camera = camera;
        font = Statics.asset.getBitmapFont(BitmapFonts.KENNEY_1);
    }

    @Override
    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.BLACK);
        for(Entity entity : entities) {
            TilePositionComponent tpc = ComponentMappers.tilePos.get(entity);
            UnitComponent units = ComponentMappers.unit.get(entity);
            font.draw(batch, "#" + units.units,
                    CoordinateMapper.getTilePosX(tpc.x, tpc.y)-(Statics.settings.tileWidth>>1),
                    CoordinateMapper.getTilePosY(tpc.y) + (Statics.settings.tileHeight>>2),
                    Statics.settings.tileWidth, Align.center, false);
        }
        batch.end();
    }

    public void incAt(int x, int y) {
        for(Entity entity : entities) {
            TilePositionComponent tpc = ComponentMappers.tilePos.get(entity);
            if(tpc.x == x && tpc.y == y) {
                ComponentMappers.unit.get(entity).units++;
                break;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
