package de.dogedev.ld38.assets;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameSettings {
    public float soundVolume = .5f;
    public float musicVolume = .2f;
    public int tileWidth = 120;
    public int tileHeight = 140;
    public int tilesX = 8;
    public int tilesY = 8;
    public float tickInterval = 0.5f;
    public int minPeeps = 5;
    public int maxPeeps = 10;

    public int numBuildings = (int) ((tilesX*tilesY)*.4);
    public float difficulty = .6f; // 0 = easy peasy; 1 = "super hard"
    public float finishCheckInterval = 1;

    // effects
    //movementspeed
    public float hangar = 20;
    public float militaryTent = hangar*2;
    //spawnrate
    public float beigeBuilding = 1f;
    public float tent = beigeBuilding*2;
    //maxpopulation
    public float windmill = 2;
    public float tavern = windmill*2;
    //tickrate
    public float skyscraperGlass = 0.5f;
    public float skyscraperWide = skyscraperGlass/2f;
}
