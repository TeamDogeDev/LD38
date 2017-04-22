package de.dogedev.ld38.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.components.*;
import de.dogedev.ld38.ashley.systems.*;
import de.dogedev.ld38.assets.enums.ShaderPrograms;
import de.dogedev.ld38.assets.enums.Textures;
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
    private ShaderProgram cloudShader;
    private SpriteBatch cloudBatch;
    private Texture clouds;

    public GameScreen(){
        cloudBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.zoom = 2f;
        camera.setToOrtho(false, 1280, 720);

        clouds = Statics.asset.getTexture(Textures.CLOUD);
        initShader();

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
        ashley.addSystem(new GridSystem(camera));
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


        for (int x = 0; x < Statics.settings.tilesX; x++) {
            for (int y = 0; y < Statics.settings.tilesY; y++) {
                Entity gridEntity = ashley.createEntity();
                TilePositionComponent gridTpc = ashley.createComponent(TilePositionComponent.class);
                gridTpc.x = x;
                gridTpc.y = y;

                GridComponent gridComponent = ashley.createComponent(GridComponent.class);
                UnitComponent gridUnitComponent = ashley.createComponent(UnitComponent.class);

                gridEntity.add(gridComponent);
                gridEntity.add(gridTpc);
                gridEntity.add(gridUnitComponent);
                ashley.addEntity(gridEntity);
            }
        }


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

    private void initShader() {
        cloudShader = Statics.asset.getShader(ShaderPrograms.CLOUD_SHADER);
        cloudBatch.setShader(cloudShader);
        cloudShader.begin();
        cloudShader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cloudShader.setUniformf("cloudsize", .4f);
        cloudShader.end();
    }

    @Override
    public void show() {

    }

    private Vector2 windVelocity = new Vector2(0.0005f, 0f);
    private Vector2 windData = new Vector2(0, 0);

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(39/255.f, 174/255.f, 96/255.f, 1f);
        Gdx.gl.glClearColor(29/255.f, 128/255.f, 71/255.f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ashley.update(delta);


        windData.add(windVelocity);
        cloudShader.begin();
        cloudShader.setUniformf("scroll", windData);
        cloudShader.setUniformf("camPosition", camera.position);
        cloudShader.end();

        cloudBatch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if(camera.zoom == 1) {
            cloudBatch.begin();
            cloudBatch.draw(clouds, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            cloudBatch.end();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            mapRenderSystem.setMap(mapBuilder.buildMap(settings.tilesX, settings.tilesY));
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        cloudShader.begin();
        cloudShader.setUniformf("resolution", width, height);
        cloudShader.end();

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
        cloudBatch.dispose();
    }
}
