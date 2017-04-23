package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
import de.dogedev.ld38.ashley.components.GridComponent;
import de.dogedev.ld38.ashley.components.PlayerComponent;
import de.dogedev.ld38.ashley.components.TilePositionComponent;
import de.dogedev.ld38.ashley.components.UnitComponent;
import de.dogedev.ld38.assets.enums.BitmapFonts;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GridSystem extends EntitySystem implements Disposable {

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(GridComponent.class,
                        TilePositionComponent.class,
                        UnitComponent.class).get());
    }

    private SpriteBatch batch = new SpriteBatch();
    private OrthographicCamera camera;
    private BitmapFont font;

    public GridSystem(OrthographicCamera camera) {
        this.camera = camera;
        font = Statics.asset.getBitmapFont(BitmapFonts.KENNEY_1);
    }

    @Override
    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.BLACK);
        for (Entity entity : entities) {
            TilePositionComponent tpc = ComponentMappers.tilePos.get(entity);
            UnitComponent units = ComponentMappers.unit.get(entity);
            GridComponent grid = ComponentMappers.grid.get(entity);
            float tileScreenX = CoordinateMapper.getTilePosX(tpc.x, tpc.y) - (Statics.settings.tileWidth >> 1);
            float tileScreenY = CoordinateMapper.getTilePosY(tpc.y) + (Statics.settings.tileHeight >> 2);
            if(units.units > 0 && ComponentMappers.player.has(entity)) {
                PlayerComponent playerComponent = ComponentMappers.player.get(entity);
                if(playerComponent.player == PlayerComponent.PLAYER.A) {
                    font.setColor(Color.BLUE);
                } else {
                    font.setColor(Color.RED);
                }
                font.draw(batch, "#" + units.units,
                        tileScreenX, tileScreenY,
                        Statics.settings.tileWidth, Align.center, false);
            }
            if(grid.clickable <= 0) {
                batch.draw(Statics.asset.getTextureAtlasRegion(Key.TILES_TILEOVERLAY),
                        tileScreenX, tileScreenY-105);
            }
        }
        batch.end();
    }

    private Entity getEntityAt(int gridX, int gridY) throws EntityNotFoundException {
        TilePositionComponent tpc;
        for (Entity entity : entities) {
            tpc = ComponentMappers.tilePos.get(entity);
            if (tpc.x == gridX && tpc.y == gridY) {
                return entity;
            }
        }
        throw new EntityNotFoundException();
    }

    public boolean isClickable(int x, int y, int button) {
        try {
            Entity entity = getEntityAt(x, y);

            if(button == Input.Buttons.LEFT) {
                GridComponent gridComponent = ComponentMappers.grid.get(entity);
                return gridComponent.clickable > 0;
            } else if(button == Input.Buttons.RIGHT) {
                UnitComponent unitComponent= ComponentMappers.unit.get(entity);
                return unitComponent.units > Statics.settings.minPeeps;
            }
        } catch (EntityNotFoundException e) {
        }
        return false;
    }

    public void incAt(int x, int y, PlayerComponent.PLAYER player) {
        Entity entity;
        try {
            entity = getEntityAt(x, y);
        } catch (EntityNotFoundException e) {
            return;
        }

        int gridChange = 0;

        if(ComponentMappers.player.has(entity)) {
            PlayerComponent playerComponent = ComponentMappers.player.get(entity);
            if(playerComponent.player == player) {
                // player's tile
                ComponentMappers.unit.get(entity).units++;
            } else {
                int newUnits = --ComponentMappers.unit.get(entity).units;
                if(newUnits <= 0) {
                    entity.remove(PlayerComponent.class);
                    if(PlayerComponent.PLAYER.A != player) gridChange = -1;
                }
            }
        } else {
            PlayerComponent newPlayerComponent = ((PooledEngine) getEngine()).createComponent(PlayerComponent.class);
            if(PlayerComponent.PLAYER.A == player) gridChange = 1;
            newPlayerComponent.player = player;
            entity.add(newPlayerComponent);
            ComponentMappers.unit.get(entity).units++;
        }

//        int newUnits = ++ComponentMappers.unit.get(entity).units;

        if(y%2 == 0) { // red -> right
            for (int xOffset = 0; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    try {
                        Entity entityOff = getEntityAt(x+xOffset, y +yOffset);
                        ComponentMappers.grid.get(entityOff).clickable += gridChange;
                    } catch (EntityNotFoundException e) {
                    }
                }
            }
            try {
                Entity entityOff = getEntityAt(x-1, y);
                ComponentMappers.grid.get(entityOff).clickable += gridChange;
            } catch (EntityNotFoundException e) {
            }
        } else { // blue -> left
            for (int xOffset = -1; xOffset <= 0; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    try {
                        Entity entityOff = getEntityAt(x+xOffset, y +yOffset);
                        ComponentMappers.grid.get(entityOff).clickable += gridChange;
                    } catch (EntityNotFoundException e) {
                    }
                }
            }
            try {
                Entity entityOff = getEntityAt(x+1, y);
                ComponentMappers.grid.get(entityOff).clickable += gridChange;
            } catch (EntityNotFoundException e) {
            }
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
