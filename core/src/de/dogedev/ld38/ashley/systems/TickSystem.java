package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.BuildingComponent;
import de.dogedev.ld38.ashley.components.PlayerComponent;
import de.dogedev.ld38.ashley.components.SpawnComponent;
import de.dogedev.ld38.ashley.components.UnitComponent;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class TickSystem extends EntitySystem {

    private ImmutableArray<Entity> spawns;
    private ImmutableArray<Entity> buildings;

    public TickSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        spawns = engine.getEntitiesFor(Family.all(SpawnComponent.class, UnitComponent.class).get());
        buildings = engine.getEntitiesFor(Family.all(BuildingComponent.class, PlayerComponent.class).get());
    }

    @Override
    public void update(float delta) {
        for (Entity entity : spawns) {
            PlayerComponent playerComponent = ComponentMappers.player.get(entity);
            if(playerComponent == null){
                continue;
            }

            float tickrate = 1;
            float spawnate = 1;
            float maxPopulation = 10;
            float movementSpeed = 0;

            for(Entity building: buildings){
                if(playerComponent.player == ComponentMappers.player.get(building).player){
                    BuildingComponent bc = ComponentMappers.building.get(building);
                    if(bc.tickrate > 0) tickrate *= bc.tickrate;
                    spawnate += bc.spawnate;
                    maxPopulation += bc.maxPopulation;
                    movementSpeed += bc.movementSpeed;
                }
            }

            SpawnComponent spawnComponent = ComponentMappers.spawn.get(entity);
            spawnComponent.movementSpeed = movementSpeed;
            if(spawnComponent.spawnTimer >= tickrate*Statics.settings.tickInterval){
                spawnComponent.spawnTimer = 0;
                UnitComponent unitComponent = ComponentMappers.unit.get(entity);
                unitComponent.units += spawnate;
                unitComponent.units = MathUtils.clamp(unitComponent.units, 0, (int)maxPopulation);
            } else {
                spawnComponent.spawnTimer += delta;
            }


        }
    }
}
