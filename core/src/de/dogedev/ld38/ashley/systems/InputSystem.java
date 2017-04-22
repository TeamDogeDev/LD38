package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import de.dogedev.ld38.input.GameDragProcessor;
import de.dogedev.ld38.input.GameInputProcessor;

/**
 * Created by Furuha on 28.01.2016.
 */
public class InputSystem extends EntitySystem  {


    private final OrthographicCamera camera;
    private final InputMultiplexer inputMultiplexer;
    public InputSystem(OrthographicCamera camera) {
        this.camera = camera;
        inputMultiplexer = new InputMultiplexer(
                new GameInputProcessor(camera),
                new GestureDetector(new GameDragProcessor(camera))
        );
    }

    @Override
    public void addedToEngine (Engine engine) {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void removedFromEngine (Engine engine) {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void update (float deltaTime) {

    }


}
