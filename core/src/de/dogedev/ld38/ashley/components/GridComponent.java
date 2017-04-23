package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GridComponent implements Component, Pool.Poolable {

    public int clickable = 0;
    @Override
    public void reset() {
        clickable = 0;
    }
}
