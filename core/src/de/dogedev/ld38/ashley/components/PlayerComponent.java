package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class PlayerComponent implements Component, Pool.Poolable {

    public enum PLAYER {
        A,B, NONE
    }

    public PLAYER player = PLAYER.NONE;

    @Override
    public void reset() {
        player = PLAYER.NONE;
    }


}
