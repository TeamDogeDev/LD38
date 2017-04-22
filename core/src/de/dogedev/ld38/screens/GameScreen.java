package de.dogedev.ld38.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.input.GameDragProcessor;
import de.dogedev.ld38.input.GameInputProcessor;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameScreen implements Screen {

    TextureRegion texture;
    SpriteBatch batch;

    private TiledMap map;
    private OrthographicCamera camera;
    private HexagonalTiledMapRenderer renderer;


    @Override
    public void show() {
        batch = new SpriteBatch();
        texture = Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_01);

        camera = new OrthographicCamera();
        camera.zoom = 2f;
        camera.setToOrtho(false, 1280, 720);

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

        InputMultiplexer inputMultiplexer = new InputMultiplexer(
                new GameInputProcessor(camera),
                new GestureDetector(new GameDragProcessor(camera))
                );

        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(39/255.f, 174/255.f, 96/255.f, 1f);
        Gdx.gl.glClearColor(39/255.f, 0/255.f, 96/255.f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
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
