package de.dogedev.ld38.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.components.LookComponent;
import de.dogedev.ld38.ashley.components.MovementComponent;
import de.dogedev.ld38.ashley.components.PositionComponent;
import de.dogedev.ld38.ashley.components.RenderComponent;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameDragProcessor extends GestureDetector.GestureAdapter {

    private OrthographicCamera camera;
    private Vector3 mouse = new Vector3();
    public GameDragProcessor(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.position.x -= (deltaX*camera.zoom);
        camera.position.y += (deltaY*camera.zoom);
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        mouse.set(x, y, 0);
        Vector3 unproject = camera.unproject(mouse);
        Vector2 tile = CoordinateMapper.getTile((int) unproject.x, (int) unproject.y);
        if(tile != null) spawnEntity(tile);
        return super.tap(x, y, count, button);
    }

    @Deprecated
    private void spawnEntity(Vector2 tile) {
        Entity entity = Statics.ashley.createEntity();
        RenderComponent rc = Statics.ashley.createComponent(RenderComponent.class);
        rc.region = Statics.asset.getTextureAtlasRegion(Key.CHARACTERS_CHAR_1);
        rc.angle = 90;
        entity.add(rc);

        PositionComponent tpc = Statics.ashley.createComponent(PositionComponent.class);
        tpc.x = CoordinateMapper.getTilePosX(0, 7);
        tpc.y = CoordinateMapper.getTilePosY(7);
        entity.add(tpc);


        MovementComponent mvc = Statics.ashley.createComponent(MovementComponent.class);
        Vector2 tilePos = CoordinateMapper.getTilePos((int) tile.x, (int) tile.y);
        mvc.x = (int) tilePos.x;
        mvc.y = (int) tilePos.y;
        mvc.speed = 40;
        entity.add(mvc);

        entity.add(Statics.ashley.createComponent(LookComponent.class));

        Statics.ashley.addEntity(entity);
    }
}
