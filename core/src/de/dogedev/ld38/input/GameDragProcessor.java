package de.dogedev.ld38.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.systems.GridSystem;
import de.dogedev.ld38.ashley.systems.OverlayRenderSystem;
import de.dogedev.ld38.screens.GameScreen;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameDragProcessor extends GestureDetector.GestureAdapter {

    private GameScreen gameScreen;
    private OrthographicCamera camera;
    private Vector3 mouse = new Vector3();
    public GameDragProcessor(GameScreen gameScreen, OrthographicCamera camera) {
        this.gameScreen = gameScreen;
        this.camera = camera;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.position.x -= (deltaX*camera.zoom);
        camera.position.y += (deltaY*camera.zoom);
        return super.pan(x, y, deltaX, deltaY);
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(button == Input.Buttons.LEFT) {
            mouse.set(x, y, 0);
            Vector3 unproject = camera.unproject(mouse);
            Vector2 tileCoordinates = CoordinateMapper.getTile((int) unproject.x, (int) unproject.y);

            if(tileCoordinates != null) {
                if(Statics.ashley.getSystem(GridSystem.class).isClickable((int) tileCoordinates.x, (int) tileCoordinates.y, button)) {
                    Vector2 arrowTilePosition = Statics.ashley.getSystem(OverlayRenderSystem.class).getArrowTilePosition();

//                    gameScreen.spawnWarrior(new Vector2(0, Statics.settings.tilesY-1), tileCoordinates, 40);
                    gameScreen.spawnWarrior(arrowTilePosition, tileCoordinates, 40);
                } else {
                }
            }
        } else if(button == Input.Buttons.RIGHT) {
            mouse.set(x, y, 0);
            Vector3 unproject = camera.unproject(mouse);
            Vector2 tileCoordinates = CoordinateMapper.getTile((int) unproject.x, (int) unproject.y);

            if(tileCoordinates != null) {
                GridSystem gridSystem = Statics.ashley.getSystem(GridSystem.class);
                if(gridSystem.isClickable((int) tileCoordinates.x, (int) tileCoordinates.y, button)) {
                    Statics.ashley.getSystem(OverlayRenderSystem.class)
                            .setArrowTilePosition(tileCoordinates);
                } else {
                }
            }
        }
        return super.tap(x, y, count, button);
    }
}
