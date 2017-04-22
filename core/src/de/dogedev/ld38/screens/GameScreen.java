package de.dogedev.ld38.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.components.RenderComponent;
import de.dogedev.ld38.ashley.components.SpawnComponent;
import de.dogedev.ld38.ashley.components.TilePositionComponent;
import de.dogedev.ld38.ashley.components.UnitComponent;
import de.dogedev.ld38.ashley.systems.*;
import de.dogedev.ld38.map.MapBuilder;

import static de.dogedev.ld38.Statics.ashley;
import static de.dogedev.ld38.Statics.settings;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameScreen implements Screen {


    private OrthographicCamera camera;
    MapRenderSystem mapRenderSystem;
    RenderSystem renderSystem;
    MapBuilder mapBuilder = new MapBuilder();

    public GameScreen(){
        camera = new OrthographicCamera();
        camera.zoom = 2f;
        camera.setToOrtho(false, 1280, 720);

        TiledMap map = mapBuilder.buildMap(settings.tilesX, settings.tilesY);

        mapRenderSystem = new MapRenderSystem(camera, map);
        renderSystem = new RenderSystem(camera);

        ashley.addSystem(new InputSystem(camera));
        ashley.addSystem(new CameraSystem(camera));
        ashley.addSystem(new MovementSystem());
        ashley.addSystem(mapRenderSystem);
        ashley.addSystem(renderSystem);
        ashley.addSystem(new DebugUISystem(camera));
        ashley.addSystem(new TickSystem());
//        ashley.addSystem(new OverlayRenderSystem(camera));

        Entity castleOpen = ashley.createEntity();
        RenderComponent rc = ashley.createComponent(RenderComponent.class);
        rc.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_CASTLE_OPEN);

        SpawnComponent spawnComponent = ashley.createComponent(SpawnComponent.class);
        UnitComponent unitComponent = ashley.createComponent(UnitComponent.class);

        TilePositionComponent tpc = ashley.createComponent(TilePositionComponent.class);

        tpc.x = 0; //
        tpc.y = Statics.settings.tilesY-1;

        castleOpen.add(tpc);
        castleOpen.add(rc);
        castleOpen.add(unitComponent);
        castleOpen.add(spawnComponent);

        ashley.addEntity(castleOpen);

//        Entity castleSmall = ashley.createEntity();
//        RenderComponent rc1 = ashley.createComponent(RenderComponent.class);
//        rc1.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_CASTLE_SMALL);
//        PositionComponent pc1 = ashley.createComponent(PositionComponent.class);
//        pc1.x = CoordinateMapper.getTilePosX(Statics.settings.tilesX-1, 0); //
//        pc1.y = CoordinateMapper.getTilePosY(0);
//        castleSmall.add(pc1);
//        castleSmall.add(rc1);
//        ashley.addEntity(castleSmall);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(39/255.f, 174/255.f, 96/255.f, 1f);
        Gdx.gl.glClearColor(29/255.f, 128/255.f, 71/255.f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ashley.update(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            mapRenderSystem.setMap(mapBuilder.buildMap(settings.tilesX, settings.tilesY));
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
