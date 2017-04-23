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
    public float tickInterval = 0.25f;
    public int minPeeps = 5;
    public int numBuildings = (int) ((tilesX*tilesY)*.4);
    public float difficulty = 1f; // 0 = easy peasy; 1 = super hard
}
