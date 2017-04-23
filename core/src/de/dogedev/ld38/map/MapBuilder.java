package de.dogedev.ld38.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.github.czyzby.noise4j.map.Grid;
import com.github.czyzby.noise4j.map.generator.noise.NoiseGenerator;
import com.github.czyzby.noise4j.map.generator.util.Generators;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.BuildingComponent;
import de.dogedev.ld38.ashley.components.DirtyComponent;
import de.dogedev.ld38.ashley.components.RenderComponent;
import de.dogedev.ld38.ashley.components.TilePositionComponent;

import static de.dogedev.ld38.Statics.settings;

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
        buildBuildings(settings.numBuildings, map);
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
        for (int y = 1; y < tilesY - 1; y++) {
            for (int x = 1; x < tilesX - 1; x++) {
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

    public void buildBuildings(int numBuildings, TiledMap map) {

        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) map.getLayers().get(0);

        ImmutableArray<Entity> buildings = Statics.ashley.getEntitiesFor(Family.all(BuildingComponent.class, TilePositionComponent.class).get());
        for(Entity entity : buildings) {
            entity.add(Statics.ashley.createComponent(DirtyComponent.class));
        }
        for (int numBuilding = 0; numBuilding < numBuildings; numBuilding++) {
            boolean buildingBuild = false;

            do {
                int buildingTileX = MathUtils.random(tiledMapTileLayer.getWidth()- 1);
                int buildingTileY = MathUtils.random(tiledMapTileLayer.getHeight()- 1);
                if((buildingTileX == 0 && buildingTileY == tiledMapTileLayer.getHeight()-1)||(buildingTileX == tiledMapTileLayer.getWidth()-1 && buildingTileY == 0)) {
                } else {
                    CMapTile tile = (CMapTile) tiledMapTileLayer.getCell(buildingTileX, buildingTileY).getTile();
                    boolean canHaveBuilding = tile.canHaveBuilding();
                    boolean hasBuilding = false;
                    for (Entity building : buildings) {
                        TilePositionComponent tilePositionComponent = ComponentMappers.tilePos.get(building);
                        if (tilePositionComponent.x == buildingTileX && tilePositionComponent.y == buildingTileY) {
                            hasBuilding = true;
                        }
                    }

                    if (canHaveBuilding && !hasBuilding) {
                        Entity building = Statics.ashley.createEntity();
                        TilePositionComponent tilePositionComponent = Statics.ashley.createComponent(TilePositionComponent.class);
                        tilePositionComponent.x = buildingTileX;
                        tilePositionComponent.y = buildingTileY;

                        BuildingComponent buildingComponent = Statics.ashley.createComponent(BuildingComponent.class);

                        RenderComponent renderComponent = Statics.ashley.createComponent(RenderComponent.class);

                        switch (tile.getBasicType()) {
                            case DIRT:
                                renderComponent.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_HANGAR);
                                buildingComponent.movementSpeed = Statics.settings.movementSpeed;
                                break;
                            case SAND:
                                buildingComponent.spawnate = Statics.settings.spawnrate;
                                renderComponent.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_BEIGEBUILDING);
                                break;
                            case GRASS:
                                buildingComponent.maxPopulation = Statics.settings.maxPopulation;
                                renderComponent.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_WINDMILL_COMPLETE);
                                break;
                            case STONE:
                                buildingComponent.tickrate = Statics.settings.tickrate;
                                renderComponent.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_SKYSCRAPER_GLASS);
                                break;
                        }

                        building.add(tilePositionComponent);
                        building.add(renderComponent);
                        building.add(buildingComponent);

                        Statics.ashley.addEntity(building);
                        buildingBuild = true;

                    }
                }
            } while (!buildingBuild);

        }
    }
}
