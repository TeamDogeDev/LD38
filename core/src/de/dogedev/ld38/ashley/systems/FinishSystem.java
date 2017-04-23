package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.PlayerComponent;

/**
 * Created by elektropapst on 23.04.2017.
 */
public class FinishSystem extends IntervalSystem {
    private ImmutableArray<Entity> entities;

    public FinishSystem() {
        super(Statics.settings.finishCheckInterval);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void updateInterval() {
        int ai = 0;
        int player = 0;

        for(Entity entity : entities) {
            PlayerComponent playerComponent = ComponentMappers.player.get(entity);
            if(playerComponent.player == PlayerComponent.PLAYER.A) {
                player++;
            } else if(playerComponent.player == PlayerComponent.PLAYER.B) {
                ai++;
            }
            if(player > 0 && ai > 0) {
                return;
            }
        }
        if(player == 0 && ai > 0) {
            // AI WINS
            System.out.println("AI WINS");
        } else if(player > 0 && ai == 0) {
            // PlayerWins
            System.out.println("PLAYER WINS");
        }

    }
}
