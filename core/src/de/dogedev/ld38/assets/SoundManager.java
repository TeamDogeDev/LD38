package de.dogedev.ld38.assets;

import com.badlogic.gdx.math.MathUtils;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.assets.enums.Sounds;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class SoundManager {
    public void stopSound(Sounds sound, long id) {
        Statics.asset.getSound(sound).stop(id);
    }

    public long loopSound(Sounds sound) {
        return loopSound(sound, Statics.settings.soundVolume);
    }

    public long loopSound(Sounds sound, float volume) {
        return Statics.asset.getSound(sound).loop(volume);
    }

    public long playSound(Sounds sound) {
        return playSound(sound, Statics.settings.soundVolume);
    }

    public long playSoundPitched(Sounds sound) {
        return playSoundPitched(sound, Statics.settings.soundVolume, MathUtils.random(0.7f, 1.3f));
    }

    public long playSoundPitched(Sounds sound, float volume) {
        return playSoundPitched(sound, volume, MathUtils.random(0.7f, 1.3f));
    }

    public long playSoundPitchedHigh(Sounds sound, float volume) {
        return playSoundPitched(sound, volume, MathUtils.random(1.5f, 2));
    }

    public long playSound(Sounds sound, float volume) {
        return Statics.asset.getSound(sound).play(volume);
    }

    public long playSoundPitched(Sounds sound, float volume, float pitch) {
        return Statics.asset.getSound(sound).play(volume, pitch, 1);
    }
}
