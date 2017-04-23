package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import de.dogedev.ld38.Statics;

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

        int maxCameraX = (Statics.settings.tilesX-1) * Statics.settings.tileWidth;
        int maxCameraY = (Statics.settings.tilesY-1) * Statics.settings.tileHeight;

        camera.position.x = MathUtils.round(camera.position.x);
        camera.position.y = MathUtils.round(camera.position.y);
        camera.position.x = MathUtils.clamp(camera.position.x, 0, maxCameraX);
        camera.position.y = MathUtils.clamp(camera.position.y, 0, maxCameraY);

        camera.zoom = MathUtils.clamp(camera.zoom, .5f, 2f);
        camera.update();
    }


}
