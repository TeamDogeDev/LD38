package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class RenderComponent implements Component, Pool.Poolable {

    public TextureRegion region;

    @Override
    public void reset() {
        region = null;
    }
}
