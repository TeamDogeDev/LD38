package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.SpawnComponent;
import de.dogedev.ld38.ashley.components.UnitComponent;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class TickSystem extends IntervalSystem {

    private ImmutableArray<Entity> entities;

    public TickSystem() {
        super(Statics.settings.tickInterval);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SpawnComponent.class, UnitComponent.class).get());
    }

    @Override
    protected void updateInterval() {
        for (Entity entity : entities) {
            UnitComponent unitComponent = ComponentMappers.unit.get(entity);
            SpawnComponent spawnComponent = ComponentMappers.spawn.get(entity);
            unitComponent.units = MathUtils.clamp(++unitComponent.units, 0, spawnComponent.maxUnits);
            System.out.println(unitComponent.units);
        }
    }
}
