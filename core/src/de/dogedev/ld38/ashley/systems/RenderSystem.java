package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.HiddenComponent;
import de.dogedev.ld38.ashley.components.PositionComponent;
import de.dogedev.ld38.ashley.components.RenderComponent;

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
        this.font = new BitmapFont();


    }

    public int getTilePosX(int x, int y) {
        int x2 =  x*120+30;
        if(y%2 == 0) {
            x2 += 60;
        }
        return x2;
    }
    public int getTilePosY(int y) {
        return y*104+35;
    }

    @Override
    public void addedToEngine (Engine pengine) {
        entities = pengine.getEntitiesFor(Family.all(PositionComponent.class).one(RenderComponent.class).exclude(HiddenComponent.class).get());

        PooledEngine engine = (PooledEngine) pengine;

        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 15; y++){
                Entity entity = engine.createEntity();
                RenderComponent rc = engine.createComponent(RenderComponent.class);
                rc.region = Statics.asset.getTextureAtlasRegion(Key.CHARACTERS_CHAR_1);
                entity.add(rc);

                PositionComponent pc = engine.createComponent(PositionComponent.class);
                pc.x = getTilePosX(x, y);
                pc.y = getTilePosY(y);
                entity.add(pc);

                engine.addEntity(entity);
            }
        }
//        engine.addEntityListener(Family.all(PositionComponent.class).one(RenderComponent.class).exclude(HiddenComponent.class, SubEntityComponent.class).get(), this);
//        for(Entity e: entities){
//            sortedEntities.add(e);
//        }
//        sortedEntities.sort();
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

//            PositionComponent position = ComponentMappers.position.get(e);
//            NameComponent nc = ComponentMappers.name.get(e);

//            if (nc != null) {
//                font.draw(batch, nc.name, position.x + xOffset - 90, position.y + position.z + 40, 200, Align.center, false);
//            }
        }

        batch.end();

    }

    private void drawEntity(Entity e, float deltaTime) {
        PositionComponent positionComponent = ComponentMappers.position.get(e);
        RenderComponent renderComponent = ComponentMappers.render.get(e);

        batch.draw(renderComponent.region, positionComponent.x, positionComponent.y);

    }

}
