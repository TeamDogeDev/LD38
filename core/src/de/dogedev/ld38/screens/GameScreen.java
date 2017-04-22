package de.dogedev.ld38.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.systems.DebugUISystem;
import de.dogedev.ld38.ashley.systems.InputSystem;
import de.dogedev.ld38.ashley.systems.MapRenderSystem;
import de.dogedev.ld38.ashley.systems.RenderSystem;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameScreen implements Screen {

    private TiledMap map;
    private OrthographicCamera camera;
    private HexagonalTiledMapRenderer renderer;


    public GameScreen(){
        camera = new OrthographicCamera();
        camera.zoom = 2f;
        camera.setToOrtho(false, 1280, 720);

        Statics.ashley.addSystem(new InputSystem(camera));
        Statics.ashley.addSystem(new MapRenderSystem(camera));
        Statics.ashley.addSystem(new RenderSystem(camera));
        Statics.ashley.addSystem(new DebugUISystem(camera));

    }

    @Override
    public void show() {
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
    public void render(float delta) {
//        Gdx.gl.glClearColor(39/255.f, 174/255.f, 96/255.f, 1f);
        Gdx.gl.glClearColor(39/255.f, 0/255.f, 96/255.f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        Statics.ashley.update(delta);
//        batch.begin();
//        batch.draw(texture, 100, 100);
//        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
