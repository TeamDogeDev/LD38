package de.dogedev.ld38.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameInputProcessor extends InputAdapter {

    private OrthographicCamera camera;

    public GameInputProcessor(OrthographicCamera camera) {
        this.camera = camera;
    }


    @Override
    public boolean scrolled(int amount) {
        if (amount > 0) {
            camera.zoom *= 2;
        } else {
            camera.zoom *= .5;
        }

        return super.scrolled(amount);
    }



}
