package de.dogedev.ld38.screens.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by elektropapst on 23.04.2017.
 */
public class ImageActor extends Actor {
    private final Texture texture;

    public ImageActor(Texture texture) {
        super();
        this.texture = texture;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), 1280, 720);
    }

}
