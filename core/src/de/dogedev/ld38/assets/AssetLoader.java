package de.dogedev.ld38.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import de.dogedev.ld38.assets.enums.BitmapFonts;
import de.dogedev.ld38.assets.enums.Musics;
import de.dogedev.ld38.assets.enums.Sounds;
import de.dogedev.ld38.assets.enums.Textures;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class AssetLoader implements Disposable{

    private AssetManager manager = new AssetManager();
    private TextureAtlas atlas;

    public AssetLoader() {
        loadTextures();
//        loadMusics();
//        loadSounds();
//        loadBitmapFonts();
    }

    public boolean load() {
        return manager.update();
    }

    public float progress() {
        return manager.getProgress();
    }

    private void loadBitmapFonts() {
        for(BitmapFonts font : BitmapFonts.values()) {
            manager.load(font.name, BitmapFont.class);
        }
    }

    private void loadSounds() {
        for(Sounds sound : Sounds.values()) {
            manager.load(sound.name, Sound.class);
        }
    }

    private void loadMusics() {
        for(Musics music : Musics.values()) {
            manager.load(music.name, Music.class);
        }
    }

    private void loadTextures() {
        manager.load("sprites/packed.atlas", TextureAtlas.class);
        for(Textures texture : Textures.values()) {
            manager.load(texture.name, Texture.class);
        }
    }

    public Sound getSound(Sounds sound) {
        return manager.get(sound.name, Sound.class);
    }

    public Music getMusic(Musics music) {
        return manager.get(music.name, Music.class);
    }

    public BitmapFont getBitmapFont(BitmapFonts font) {
        return manager.get(font.name, BitmapFont.class);
    }

    public TextureRegion getTexture(Textures texture) {
        return manager.get(texture.name, TextureRegion.class);
    }

    public TextureRegion getTextureAtlasRegion(String texture) {
        return manager.get("sprites/packed.atlas", TextureAtlas.class).findRegion(texture);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
