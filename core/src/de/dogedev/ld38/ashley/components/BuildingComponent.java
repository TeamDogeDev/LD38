package de.dogedev.ld38.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by elektropapst on 23.04.2017.
 */
public class BuildingComponent implements Pool.Poolable, Component {

    public float tickrate = 0;
    public float spawnate = 0;
    public float maxPopulation = 0;
    public float movementSpeed = 0;

    @Override
    public void reset() {
        tickrate = 0;
        spawnate = 0;
        maxPopulation = 0;
        movementSpeed = 0;
    }
}
