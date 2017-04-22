package de.dogedev.ld38.input;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;

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
        return super.pan(x, y, deltaX, deltaY);
    }
}
