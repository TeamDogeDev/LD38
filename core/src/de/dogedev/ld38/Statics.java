package de.dogedev.ld38;

import com.badlogic.ashley.core.PooledEngine;
import de.dogedev.ld38.assets.AssetLoader;
import de.dogedev.ld38.assets.GameSettings;
import de.dogedev.ld38.assets.SoundManager;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class Statics {

    public static PooledEngine ashley;
    public static AssetLoader asset;
    public static GameSettings settings;
    public static SoundManager sound;

    public static void initCat() {
        settings = new GameSettings();
        asset = new AssetLoader();
        ashley = new PooledEngine();
        sound = new SoundManager();
    }
}
