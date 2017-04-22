package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Furuha on 28.01.2016.
 */
public class MapRenderSystem extends EntitySystem {

    private final OrthographicCamera camera;


    public MapRenderSystem(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public void addedToEngine (Engine engine) {
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }


    @Override
    public void update (float deltaTime) {



    }


}
