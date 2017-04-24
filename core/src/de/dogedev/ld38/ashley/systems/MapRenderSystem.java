package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;

/**
 * Created by Furuha on 28.01.2016.
 */
public class MapRenderSystem extends EntitySystem {

    private final OrthographicCamera camera;
    private HexagonalTiledMapRenderer renderer;

    public MapRenderSystem(OrthographicCamera camera, TiledMap map, int priority) {
        super(priority);
        this.camera = camera;
        renderer = new HexagonalTiledMapRenderer(map);
    }

    @Override
    public void addedToEngine (Engine engine) {
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }


    @Override
    public void update (float deltaTime) {
        renderer.setView(camera);
        renderer.render();
    }


    public void setMap(TiledMap map) {
        renderer.setMap(map);
    }
}
