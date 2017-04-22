package de.dogedev.ld38.input;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameDragProcessor extends GestureDetector.GestureAdapter {

    private OrthographicCamera camera;

    public GameDragProcessor(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.position.x -= (deltaX*camera.zoom);
        camera.position.y += (deltaY*camera.zoom);
        camera.position.x = MathUtils.round(camera.position.x);
        camera.position.y = MathUtils.round(camera.position.y);
        return super.pan(x, y, deltaX, deltaY);
    }
}
