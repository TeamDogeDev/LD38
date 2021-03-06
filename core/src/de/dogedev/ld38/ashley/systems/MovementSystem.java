package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.*;

/**
 * Created by Furuha on 28.01.2016.
 */
public class MovementSystem extends EntitySystem {


    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> spawns;

    public MovementSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, MovementComponent.class, PeepComponent.class).get());
        spawns = engine.getEntitiesFor(Family.all(SpawnComponent.class, PlayerComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    Vector2 current = new Vector2();
    Vector2 target = new Vector2();

    @Override
    public void update(float deltaTime) {
        for (Entity e : entities) {
            MovementComponent mvc = ComponentMappers.movement.get(e);
            PositionComponent pvc = ComponentMappers.position.get(e);
            PeepComponent peep = ComponentMappers.peep.get(e);
            current.set(pvc.x, pvc.y);
            target.set(mvc.x, mvc.y);
            target.sub(current);

            if (target.len() < 40) {
                // Einheit ist angekommen
                e.add(((PooledEngine) getEngine()).createComponent(HiddenComponent.class));
                e.remove(MovementComponent.class);
                Vector2 tilePos = CoordinateMapper.getTile((int) pvc.x, (int) pvc.y);
                getEngine().getSystem(GridSystem.class).incAt(
                        (int) tilePos.x, (int) tilePos.y, peep.player
                );
                e.add(new DirtyComponent());
                continue;
            }

            float speed = 50;

            for (Entity spawn : spawns) {
                PlayerComponent playerComponent = ComponentMappers.player.get(spawn);
                if (playerComponent != null && playerComponent.player == peep.player) {
                    speed += ComponentMappers.spawn.get(spawn).movementSpeed;
                }

            }

            target.nor();
            target.scl(speed * deltaTime);
            pvc.x += target.x;
            pvc.y += target.y;
        }
    }


}
