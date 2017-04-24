package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.PlayerComponent;
import de.dogedev.ld38.ashley.components.SpawnComponent;
import de.dogedev.ld38.ashley.components.TilePositionComponent;
import de.dogedev.ld38.assets.GameSettings;
import de.dogedev.ld38.screens.GameScreen;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class AiSystem extends IntervalSystem {

    private GameScreen gameScreen;
    private ImmutableArray<Entity> fields;
    private ImmutableArray<Entity> spawns;

    public AiSystem(GameScreen gameScreen, int priority) {
        super(Statics.settings.aiTickRate, priority);
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


        int numPlayer = 0;

        for (Entity fieldEntity : fields) {
            PlayerComponent playerComponent = ComponentMappers.player.get(fieldEntity);
            if (playerComponent.player == PlayerComponent.PLAYER.A) {
                numPlayer++;
            }
        }
        float owningPercentagePlayer = ((float) numPlayer) / (Statics.settings.tilesX * Statics.settings.tilesY);

        float skipChance = 1 - Statics.settings.difficulty; // difficulty = 1 = hard -> skipchance = 0
        skipChance -= .5 * MathUtils.clamp(owningPercentagePlayer, 0.0, Math.min(owningPercentagePlayer, (1 - Statics.settings.difficulty)));
        skipChance = MathUtils.clamp(skipChance, 0.1f, 0.8f);

        Vector2 tmpVector = new Vector2();
        Vector2 tmpVector2 = new Vector2();
        for (int i = 0; i < fields.size(); i++) {
            // smaller = harder
            if (MathUtils.randomBoolean(skipChance)) continue;
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

                        tmpVector.set(g1.x, g1.y);
                        tmpVector2.set(sourcePos.x, sourcePos.y);
                        int dist2 = (int) tmpVector.dst(Vector2.Y);
                        if (dist2 < 0) dist *= -1;
                        if (dist2 <= dist) {
                            dist = dist2;
                            target = t;
                        }
                    }
                }

                if (target != null) {
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

                    gameScreen.spawnWarrior(new Vector2(sourcePos.x, sourcePos.y), new Vector2(targetTileX, targetTileY), PlayerComponent.PLAYER.B, 40);
                }
            }
        }
//        }
    }
}
