package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.assets.enums.BitmapFonts;

/**
 * Created by Furuha on 09.04.2016.
 */
public class LegendUISystem extends EntitySystem implements Disposable {

    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font;

    private TextureRegion beigeTexture;
    private TextureRegion skyscraper;
    private TextureRegion hangar;
    private TextureRegion windmill;

    private float xSpacing = 10;
    private float ySpacing = 10;
    public LegendUISystem() {
        font = Statics.asset.getBitmapFont(BitmapFonts.KENNEY_1);
        beigeTexture = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_BEIGEBUILDING);
        skyscraper = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_SKYSCRAPER_GLASS);
        hangar = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_HANGAR);
        windmill = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_WINDMILL_COMPLETE);
    }

    @Override
    public void update(float deltaTime) {

        batch.begin();
        font.setColor(Color.BLACK);
        float xPos = xSpacing*3;
        float fontWidth = 100;

        batch.draw(skyscraper, xPos, (ySpacing + font.getLineHeight())*2, skyscraper.getRegionWidth(), skyscraper.getRegionHeight());
        font.draw(batch, "tickrate\nx" + (1/Statics.settings.tickrate), xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += skyscraper.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(hangar, xPos, (ySpacing + font.getLineHeight())*2, hangar.getRegionWidth(), hangar.getRegionHeight());
        font.draw(batch, "movementspeed\n+" + Statics.settings.movementSpeed, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += hangar.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(beigeTexture, xPos, (ySpacing + font.getLineHeight())*2, beigeTexture.getRegionWidth(), beigeTexture.getRegionHeight());
        font.draw(batch, "spawnrate\nx" + Statics.settings.spawnrate, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += beigeTexture.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(windmill, xPos, (ySpacing + font.getLineHeight())*2, windmill.getRegionWidth(), windmill.getRegionHeight());
        font.draw(batch, "maxpopulation\n+" + Statics.settings.maxPopulation, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        batch.end();

    }

    @Override
    public void dispose() {
        font.dispose();
    }

}
