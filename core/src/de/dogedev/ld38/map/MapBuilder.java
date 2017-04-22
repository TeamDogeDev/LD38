package de.dogedev.ld38.map;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.github.czyzby.noise4j.map.Grid;
import com.github.czyzby.noise4j.map.generator.noise.NoiseGenerator;
import com.github.czyzby.noise4j.map.generator.util.Generators;
import de.dogedev.ld38.Key;
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

        TiledMapTile[] tiles = new TiledMapTile[5];

        tiles[0] = new StaticTiledMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_07));
        tiles[1] = new StaticTiledMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_06));
        tiles[2] = new StaticTiledMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_05));
        tiles[3] = new StaticTiledMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_STONE_STONE_07));
        tiles[4] = new StaticTiledMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_17));

        tiles[0].setId(0);
        tiles[1].setId(1);
        tiles[2].setId(2);
        tiles[3].setId(3);
        tiles[4].setId(4);

        for (int l = 0; l < 1; l++) {
            Grid mapGrid = new Grid(tilesX, tilesY);

            final NoiseGenerator noiseGenerator = NoiseGenerator.getInstance();
            noiseGenerator.setSeed(4711);
            noiseStage(mapGrid, noiseGenerator, 2, 1);

            TiledMapTileLayer layer = createLayer(mapGrid, tilesX, tilesY, tiles);
            decorateLayer(layer, tilesX, tilesY, tiles);
            layers.add(layer);
        }
        return map;
    }

    private TiledMapTileLayer createLayer(Grid mapGrid, int tilesX, int tilesY, TiledMapTile[] tiles) {


        TiledMapTileLayer layer = new TiledMapTileLayer(tilesX, tilesY, Statics.settings.tileWidth, Statics.settings.tileHeight);
        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                int tileIdx = 0;
                float noise = mapGrid.get(x, y);
                if (noise < .4) {
                    tileIdx = 0;
                } else if (noise < .45) {
                    tileIdx = 1;
                } else if (noise < .7) {
                    tileIdx = 2;
                } else {
                    tileIdx = 3;
                }

                cell.setTile(tiles[tileIdx]);
                layer.setCell(x, y, cell);
            }
        }

        return layer;
    }

    private void decorateLayer(TiledMapTileLayer tileLayer, int tilesX, int tilesY, TiledMapTile[] tiles) {
        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                int id = tileLayer.getCell(x, y).getTile().getId();
                boolean b = MathUtils.randomBoolean(.5f);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(tiles[4]);
                if(b) {
                    switch (id) {
                        case 0:
                            tileLayer.setCell(x, y, cell);
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                    }
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
