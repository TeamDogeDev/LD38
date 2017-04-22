package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Furuha on 28.01.2016.
 */
public class CameraSystem extends EntitySystem  {


    private final OrthographicCamera camera;


    public CameraSystem(OrthographicCamera camera) {
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
        camera.position.x = MathUtils.round(camera.position.x);
        camera.position.y = MathUtils.round(camera.position.y);
        camera.update();
    }


}
