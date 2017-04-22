package de.dogedev.ld38.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class CMapTile extends StaticTiledMapTile {

    public static CMapTile EDGE = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_EDGE));

    public static CMapTile GRASS = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_05));
    public static CMapTile GRASS_TREE_1 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_10));
    public static CMapTile GRASS_TREE_2 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_11));
    public static CMapTile GRASS_TREE_3 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_12));
    public static CMapTile GRASS_TREE_4 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_13));
    public static CMapTile GRASS_TREE_ROCK_1 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_14));
    public static CMapTile GRASS_TREE_ROCK_2 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_GRASS_GRASS_15));

    public static CMapTile SAND = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_07));
    public static CMapTile SAND_CACTUS_1 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_12));
    public static CMapTile SAND_CACTUS_2 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_13));
    public static CMapTile SAND_CACTUS_3 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_14));
    public static CMapTile SAND_CACTUS_4 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_15));
    public static CMapTile SAND_ROCK_1 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_16));
    public static CMapTile SAND_ROCK_2 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_17));
    public static CMapTile SAND_ROCK_3 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_SAND_SAND_18));


    public static CMapTile DIRT = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_06));
    public static CMapTile DIRT_TREE_1 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_11));
    public static CMapTile DIRT_TREE_2 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_12));
    public static CMapTile DIRT_TREE_3 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_13));
    public static CMapTile DIRT_TREE_4 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_14));
    public static CMapTile DIRT_TREE_ROCK_1 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_17));
    public static CMapTile DIRT_TREE_ROCK_2 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_18));
    public static CMapTile DIRT_ROCK_1 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_15));
    public static CMapTile DIRT_ROCK_2 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_DIRT_DIRT_16));


    public static CMapTile STONE = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_STONE_STONE_07));
    public static CMapTile STONE_TREE_1 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_STONE_STONE_12));
    public static CMapTile STONE_TREE_2 = new CMapTile(Statics.asset.getTextureAtlasRegion(Key.TILES_TERRAIN_STONE_STONE_13));

    public static CMapTile getTileVariation(CMapTile tile) {
        if (tile == CMapTile.SAND) {
            int random = MathUtils.random(6);
            switch (random) {
                case 0: return SAND_CACTUS_1;
                case 1: return SAND_CACTUS_2;
                case 2: return SAND_CACTUS_3;
                case 3: return SAND_CACTUS_4;
                case 4: return SAND_ROCK_1;
                case 5: return SAND_ROCK_2;
                case 6: return SAND_ROCK_3;
            }
        } else if(tile == CMapTile.DIRT) {
            int random = MathUtils.random(7);
            switch (random) {
                case 0: return DIRT_TREE_1;
                case 1: return DIRT_TREE_2;
                case 2: return DIRT_TREE_3;
                case 3: return DIRT_TREE_4;
                case 4: return DIRT_TREE_ROCK_1;
                case 5: return DIRT_TREE_ROCK_2;
                case 6: return DIRT_ROCK_1;
                case 7: return DIRT_ROCK_2;
            }
        } else if(tile == CMapTile.GRASS) {
            int random = MathUtils.random(5);
            switch (random) {
                case 0: return GRASS_TREE_1;
                case 1: return GRASS_TREE_2;
                case 2: return GRASS_TREE_3;
                case 3: return GRASS_TREE_4;
                case 4: return GRASS_TREE_ROCK_1;
                case 5: return GRASS_TREE_ROCK_2;
            }
        } else if(tile == CMapTile.STONE) {
            int random = MathUtils.random(1);
            switch (random) {
                case 0 : return STONE_TREE_1;
                case 1 : return STONE_TREE_2;
            }
        }
        return tile;
    }

    public CMapTile(TextureRegion textureRegion) {
        super(textureRegion);
    }

}
