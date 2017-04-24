package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.*;
import de.dogedev.ld38.assets.enums.BitmapFonts;

/**
 * Created by Furuha on 28.01.2016.
 */
public class RenderSystem extends EntitySystem implements EntityListener {

    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private Array<Entity> sortedEntities;
    private BitmapFont font;
    ImmutableArray<Entity> entities;

    public RenderSystem(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.sortedEntities = new Array<>();
        this.font = Statics.asset.getBitmapFont(BitmapFonts.KENNEY_1);
    }

    @Override
    public void addedToEngine (Engine pengine) {
        entities = pengine.getEntitiesFor(Family.one(PositionComponent.class, TilePositionComponent.class).one(RenderComponent.class).exclude(HiddenComponent.class).get());

        PooledEngine engine = (PooledEngine) pengine;

//        for(int x = 0; x < Statics.settings.tilesX; x++){
//            for(int y = 0; y < Statics.settings.tilesY; y++){
//                Entity entity = engine.createEntity();
//                RenderComponent rc = engine.createComponent(RenderComponent.class);
//                rc.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_MINE);
//                rc.angle = 180;
//                entity.add(rc);
//
//                TilePositionComponent pc = engine.createComponent(TilePositionComponent.class);
//                pc.x = x;
//                pc.y = y;
//                entity.add(pc);
//
//                engine.addEntity(entity);
//            }
//        }
//
//        for(int x = 0; x < Statics.settings.tilesX; x++){
//            for(int y = 0; y < Statics.settings.tilesY; y++){
//                Entity entity = engine.createEntity();
//                RenderComponent rc = engine.createComponent(RenderComponent.class);
//                rc.region = Statics.asset.getTextureAtlasRegion(Key.CHARACTERS_CHAR_1);
//                rc.angle = 90;
//                entity.add(rc);
//
//                PositionComponent pc = engine.createComponent(PositionComponent.class);
//                pc.x = CoordinateMapper.getTilePosX(x, y);
//                pc.y = CoordinateMapper.getTilePosY(y);
//                entity.add(pc);
//
//                entity.add(engine.createComponent(LookComponent.class));
//
//                engine.addEntity(entity);
//            }
//        }


    }

    @Override
    public void removedFromEngine (Engine engine) {
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded (Entity entity) {
        sortedEntities.add(entity);
    }

    @Override
    public void entityRemoved (Entity entity) {
        sortedEntities.removeValue(entity, false);
    }



    @Override
    public void update (float deltaTime) {

        try {
            sortedEntities.sort();
        } catch (Exception e) {
            System.err.println("Render: Sort contract violation ~ Carry on");
        }

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            drawEntity(e, deltaTime);
        }

        batch.end();

    }

    private void drawEntity(Entity e, float deltaTime) {
        RenderComponent renderComponent = ComponentMappers.render.get(e);

        if(ComponentMappers.tilePos.has(e)){
            TilePositionComponent positionComponent = ComponentMappers.tilePos.get(e);

            Vector2 tilePos = CoordinateMapper.getTilePos(positionComponent.x, positionComponent.y);

            if(ComponentMappers.look.has(e)){
                LookComponent lookComponent = ComponentMappers.look.get(e);
                drawRotated(renderComponent.region, tilePos.x-renderComponent.region.getRegionWidth()/2, tilePos.y-renderComponent.region.getRegionHeight()/2, (int)lookComponent.x, (int)lookComponent.y, renderComponent.angle);
            } else {
                draw(renderComponent.region, tilePos.x-renderComponent.region.getRegionWidth()/2, tilePos.y-renderComponent.region.getRegionHeight()/2);
            }
        }
        if(ComponentMappers.position.has(e)){
            PositionComponent positionComponent = ComponentMappers.position.get(e);

            if(ComponentMappers.look.has(e)){
                LookComponent lookComponent = ComponentMappers.look.get(e);
                drawRotated(renderComponent.region, positionComponent.x-renderComponent.region.getRegionWidth()/2, positionComponent.y-renderComponent.region.getRegionHeight()/2, (int)lookComponent.x, (int)lookComponent.y, renderComponent.angle);
            } else {
                draw(renderComponent.region, positionComponent.x-renderComponent.region.getRegionWidth()/2, positionComponent.y-renderComponent.region.getRegionHeight()/2);
            }
        }
//        if(ComponentMappers.spawn.has(e) && ComponentMappers.unit.has(e) && ComponentMappers.tilePos.has(e)) {
//            TilePositionComponent tpc = ComponentMappers.tilePos.get(e);
//            UnitComponent units = ComponentMappers.unit.get(e);
//            font.draw(batch, "#" + units.units,
//                    CoordinateMapper.getTilePosX(tpc.x, tpc.y)-(Statics.settings.tileWidth>>1),
//                    CoordinateMapper.getTilePosY(tpc.y) + (Statics.settings.tileHeight>>2),
//                    Statics.settings.tileWidth, Align.center, false);
//        }

    }

    private void draw(TextureRegion region, float x, float y) {
        batch.draw(region.getTexture(), x, y, region.getRegionWidth()/2, region.getRegionHeight()/2, region.getRegionWidth(), region.getRegionHeight(), 1, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    private void drawRotated(TextureRegion region, float x, float y, int targetX, int targetY, int angleOffset) {
        Vector2.X.set(x, y);
        Vector2.Y.set(targetX, targetY);
        float angle = Vector2.Y.sub(Vector2.X).angle() - 90 + angleOffset;
        batch.draw(region.getTexture(), x, y, region.getRegionWidth()/2, region.getRegionHeight()/2, region.getRegionWidth(), region.getRegionHeight(), 1, 1, angle, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

}
