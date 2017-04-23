package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.PlayerComponent;
import de.dogedev.ld38.ashley.components.SpawnComponent;
import de.dogedev.ld38.ashley.components.TilePositionComponent;
import de.dogedev.ld38.screens.GameScreen;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class AiSystem extends IntervalSystem {

    private GameScreen gameScreen;
    private ImmutableArray<Entity> fields;
    private ImmutableArray<Entity> spawns;

    public AiSystem(GameScreen gameScreen) {
        super(0.5f);
        this.gameScreen = gameScreen;
    }

    @Override
    public void addedToEngine(Engine engine) {
        fields = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        spawns = engine.getEntitiesFor(Family.all(SpawnComponent.class).get());
    }

    @Override
    protected void updateInterval() {
        doAi(PlayerComponent.PLAYER.B);

    }

    private void doAi(PlayerComponent.PLAYER player) {
        //Get player spawn
//        Entity spawn = null;
//        for (Entity entity : spawns) {
//            if (ComponentMappers.player.get(entity).player.equals(player)) {
//                spawn = entity;
//                break;
//            }
//        }
//        if (spawn != null) {
            for (int i = 0; i < fields.size(); i++) {
                Entity entity = fields.get(i);
                if (ComponentMappers.player.get(entity).player.equals(player)) {
                    TilePositionComponent sourcePos = ComponentMappers.tilePos.get(entity);
                    //Send?


                    //Target field
                    Entity target = null;
                    int dist = Integer.MAX_VALUE;
                    for (Entity t : fields) {
                        if (!ComponentMappers.player.get(t).player.equals(player)) {
                            TilePositionComponent g1 = ComponentMappers.tilePos.get(t);

                            int dist2 = (g1.x-sourcePos.x) + (g1.y - sourcePos.y);
                            if(dist2 < 0) dist *= -1;
                            if(dist2 < dist){
                                dist = dist2;
                                target = t;
                            }
                        }
                    }
                    if(target != null) {
                        TilePositionComponent targetPos = ComponentMappers.tilePos.get(target);
                        int targetTileX = sourcePos.x;
                        int targetTileY = sourcePos.y;
                        if (targetTileX > targetPos.x && !(targetTileY < targetPos.y)) {
                            targetTileX -= 1;
                        } else if (targetTileX < targetPos.x) {
                            targetTileX += 1;
                        }
                        if (targetTileY > targetPos.y) {
                            targetTileY -= 1;
                        } else if (targetTileY < targetPos.y) {
                            targetTileY += 1;
                        }

                        gameScreen.spawnWarrior(new Vector2(sourcePos.x, sourcePos.y), new Vector2(targetTileX, targetTileY), PlayerComponent.PLAYER.B, 60);
                    }
                }
            }
//        }
    }
}
