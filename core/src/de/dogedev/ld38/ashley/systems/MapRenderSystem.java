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
    private TiledMap map;
    private HexagonalTiledMapRenderer renderer;

    public MapRenderSystem(OrthographicCamera camera) {
        this.camera = camera;
        init();
    }

    private void init() {
        map = new TiledMap();
        map.getProperties().put("hexsidelength", 68);
        map.getProperties().put("staggeraxis", "y");
        MapLayers layers = map.getLayers();
        TiledMapTile[] tiles = new TiledMapTile[1];

        tiles[0] = new StaticTiledMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_05));

        for (int l = 0; l < 1; l++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(Statics.settings.tilesX, Statics.settings.tilesY, Statics.settings.tileWidth, Statics.settings.tileHeight);
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(tiles[0]);
                    layer.setCell(x, y, cell);
                }
            }
            layers.add(layer);
        }

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
        renderer.render();
    }


}
