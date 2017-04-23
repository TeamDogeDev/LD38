package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class SpawnComponent implements Component, Pool.Poolable {

    public float spawnTimer = 0;
    public float maxPopulation = 0;
    public float movementSpeed = 0;


    @Override
    public void reset() {
        spawnTimer = 10;
        maxPopulation = 0;
        movementSpeed = 0;
    }
}
