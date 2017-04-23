package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 23.04.2017.
 */
public class DirtyComponent implements Component, Pool.Poolable {

    @Override
    public void reset() {}
}
