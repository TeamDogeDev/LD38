package de.dogedev.ld38.map;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.github.czyzby.noise4j.map.Grid;
import com.github.czyzby.noise4j.map.generator.noise.NoiseGenerator;
import com.github.czyzby.noise4j.map.generator.util.Generators;
import de.dogedev.ld38.Statics;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class MapBuilder {

    public TiledMap buildMap(int tilesX, int tilesY) {
        TiledMap map = new TiledMap();
        map.getProperties().put("hexsidelength", 68);
        map.getProperties().put("staggeraxis", "y");
        MapLayers layers = map.getLayers();

        Grid mapGrid = new Grid(tilesX, tilesY);

        final NoiseGenerator noiseGenerator = NoiseGenerator.getInstance();
        noiseStage(mapGrid, noiseGenerator, 2, 1);

        TiledMapTileLayer groundLayer = createLayer(mapGrid, tilesX, tilesY);
        decorateLayer(groundLayer, tilesX, tilesY);
        layers.add(groundLayer);

        layers.add(createEdges(tilesX, tilesY));
        return map;
    }

    private TiledMapTileLayer createEdges(int tilesX, int tilesY) {
        TiledMapTileLayer layer = new TiledMapTileLayer(tilesX, tilesY, Statics.settings.tileWidth, Statics.settings.tileHeight);
        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(CMapTile.EDGE);
                layer.setCell(x, y, cell);
            }
        }
        return layer;
    }

    private TiledMapTileLayer createLayer(Grid mapGrid, int tilesX, int tilesY) {


        TiledMapTileLayer layer = new TiledMapTileLayer(tilesX, tilesY, Statics.settings.tileWidth, Statics.settings.tileHeight);
        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                CMapTile tile;
                float noise = mapGrid.get(x, y);

                if (noise < .4) {
                    tile = CMapTile.SAND;
                } else if (noise < .45) {
                    tile = CMapTile.DIRT;
                } else if (noise < .7) {
                    tile = CMapTile.GRASS;
                } else {
                    tile = CMapTile.STONE;
                }

                cell.setTile(tile);
                layer.setCell(x, y, cell);
            }
        }

        return layer;
    }

    private void decorateLayer(TiledMapTileLayer tileLayer, int tilesX, int tilesY) {
        for (int y = 1; y < tilesY-1; y++) {
            for (int x = 1; x < tilesX-1; x++) {
                boolean decorate = MathUtils.randomBoolean(.2f);
                if (decorate) {
                    TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                    cell.setTile(CMapTile.getTileVariation((CMapTile) cell.getTile()));
                }
            }
        }
    }

    private static void noiseStage(final Grid grid, final NoiseGenerator noiseGenerator, final int radius,
                                   final float modifier) {
        noiseGenerator.setRadius(radius);
        noiseGenerator.setModifier(modifier);
        // Seed ensures randomness, can be saved if you feel the need to
        // generate the same map in the future.
        noiseGenerator.setSeed(Generators.rollSeed());
        noiseGenerator.generate(grid);
    }
}
