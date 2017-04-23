package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 23.04.2017.
 */
public class PeepComponent implements Component, Pool.Poolable {

    public PlayerComponent.PLAYER player = PlayerComponent.PLAYER.NONE;
    @Override
    public void reset() {
        player = PlayerComponent.PLAYER.NONE;
    }
}
