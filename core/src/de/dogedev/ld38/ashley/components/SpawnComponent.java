package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class SpawnComponent implements Component, Pool.Poolable {

    public int maxUnits = 10;

    @Override
    public void reset() {
        maxUnits = 10;
    }
}
