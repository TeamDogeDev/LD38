package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class UnitComponent implements Component, Pool.Poolable{
    public int units = 0;

    @Override
    public void reset() {
        units = 0;
    }
}
