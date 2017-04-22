package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Furuha on 28.01.2016.
 */
public class RenderSystem extends EntitySystem implements EntityListener {

    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private Array<Entity> sortedEntities;
    private BitmapFont font;


    public RenderSystem(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.sortedEntities = new Array<>();
        this.font = new BitmapFont();
    }

    @Override
    public void addedToEngine (Engine engine) {
//        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(PositionComponent.class).one(RenderComponent.class).exclude(HiddenComponent.class, SubEntityComponent.class).get());
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

        for (int i = 0; i < sortedEntities.size; i++) {
            Entity e = sortedEntities.get(i);

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

    }

}
