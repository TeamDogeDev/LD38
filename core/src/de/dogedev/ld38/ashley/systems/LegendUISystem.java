package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
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

    private TextureRegion beigeBuilding;
    private TextureRegion tent;

    private TextureRegion skyscraperGlass;
    private TextureRegion skyscraperWide;

    private TextureRegion hangar;
    private TextureRegion militaryTent;

    private TextureRegion windmill;
    private TextureRegion tavern;

    private float xSpacing = 5;
    private float ySpacing = 10;
    public LegendUISystem() {
        font = Statics.asset.getBitmapFont(BitmapFonts.KENNEY_1);

        beigeBuilding = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_BEIGEBUILDING);
        tent = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_INDIANTENT_FRONT);

        skyscraperGlass = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_SKYSCRAPER_GLASS);
        skyscraperWide = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_SKYSCRAPER_WIDE);

        hangar = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_HANGAR);
        militaryTent = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_MILITARYTENT);

        windmill = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_WINDMILL_COMPLETE);
        tavern = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_TAVERN);

    }

    @Override
    public void update(float deltaTime) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.begin();

        batch.draw(Statics.asset.getTextureAtlasRegion(Key.ICONS_TEXTBG), 0, 0, Gdx.graphics.getWidth(), 2*ySpacing+ (skyscraperGlass.getRegionHeight()));

        font.setColor(Color.BLACK);
        float oldScaleX = font.getScaleX();
        float oldScaleY = font.getScaleY();
        font.getData().setScale(0.8f, 0.8f);

        float xPos = xSpacing*4;
        float fontWidth = 100;

        batch.draw(skyscraperGlass, xPos, (ySpacing + font.getLineHeight())*2, skyscraperGlass.getRegionWidth()>>1, skyscraperGlass.getRegionHeight()>>1);
        font.draw(batch, "tickrate\nx" + (1/Statics.settings.skyscraperGlass), xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += skyscraperGlass.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(skyscraperWide, xPos, (ySpacing + font.getLineHeight())*2, skyscraperWide.getRegionWidth()>>1, skyscraperWide.getRegionHeight()>>1);
        font.draw(batch, "tickrate\nx" + (1/Statics.settings.skyscraperWide), xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += skyscraperWide.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(hangar, xPos, (ySpacing + font.getLineHeight())*2, hangar.getRegionWidth()>>1, hangar.getRegionHeight()>>1);
        font.draw(batch, "movementspeed\n+" + Statics.settings.hangar, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += hangar.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(militaryTent, xPos, (ySpacing + font.getLineHeight())*2, militaryTent.getRegionWidth()>>1, militaryTent.getRegionHeight()>>1);
        font.draw(batch, "movementspeed\n+" + Statics.settings.militaryTent, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += militaryTent.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(beigeBuilding, xPos, (ySpacing + font.getLineHeight())*2, beigeBuilding.getRegionWidth()>>1, beigeBuilding.getRegionHeight()>>1);
        font.draw(batch, "spawnrate\n+" + Statics.settings.beigeBuilding, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += beigeBuilding.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(tent, xPos, (ySpacing + font.getLineHeight())*2, tent.getRegionWidth()>>1, tent.getRegionHeight()>>1);
        font.draw(batch, "spawnrate\n+" + Statics.settings.tent, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += tent.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(windmill, xPos, (ySpacing + font.getLineHeight())*2, windmill.getRegionWidth()>>1, windmill.getRegionHeight()>>1);
        font.draw(batch, "maxpopulation\n+" + Statics.settings.windmill, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);
        xPos += windmill.getRegionWidth() + xSpacing + fontWidth;

        batch.draw(tavern, xPos, (ySpacing + font.getLineHeight())*2, tavern.getRegionWidth()>>1, tavern.getRegionHeight()>>1);
        font.draw(batch, "maxpopulation\n+" + Statics.settings.tavern, xPos, ySpacing + (font.getLineHeight()*2), fontWidth, 1, false);


        batch.end();
        font.getData().setScale(oldScaleX, oldScaleY);

    }

    @Override
    public void dispose() {
        font.dispose();
    }

}
