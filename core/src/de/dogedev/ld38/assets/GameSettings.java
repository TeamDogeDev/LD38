package de.dogedev.ld38.assets;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameSettings {
    public float soundVolume = .5f;
    public float musicVolume = .2f;
    public int tileWidth = 120;
    public int tileHeight = 140;
    public int tilesX = 2;
    public int tilesY = 2;
    public float tickInterval = 0.5f;
    public int minPeeps = 5;
    public int maxPeeps = 10;

    public int numBuildings = (int) ((tilesX*tilesY)*.4);
    public float difficulty = 1f; // 0 = easy peasy; 1 = "super hard"
    public float finishCheckInterval = 1;

    // effects
    public float movementSpeed = 20;
    public float spawnrate = 1f;
    public float maxPopulation = 2;
    public float tickrate = 0.5f;
}
