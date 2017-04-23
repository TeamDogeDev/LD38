package de.dogedev.ld38.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
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
    private ImmutableArray<Entity> dirtyEntities;

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
        ashley.addSystem(new OverlayRenderSystem(camera));

        dirtyEntities = ashley.getEntitiesFor(Family.all(DirtyComponent.class).get());

        createSpawnEntity(0, Statics.settings.tilesY-1);
        createSpawnEntity(Statics.settings.tilesX-1, 0);
        createGridEntites(Statics.settings.tilesX, Statics.settings.tilesY);


    }

    private void createSpawnEntity(int tileX, int tileY) {
        Entity spawnEntity = ashley.createEntity();
        RenderComponent renderComponent = ashley.createComponent(RenderComponent.class);
        renderComponent.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_CASTLE_OPEN);

        SpawnComponent spawnComponent = ashley.createComponent(SpawnComponent.class);
        UnitComponent unitComponent = ashley.createComponent(UnitComponent.class);

        TilePositionComponent tilePositionComponent = ashley.createComponent(TilePositionComponent.class);

        tilePositionComponent.x = tileX; //
        tilePositionComponent.y = tileY;

        spawnEntity.add(tilePositionComponent);
        spawnEntity.add(renderComponent);
        spawnEntity.add(unitComponent);
        spawnEntity.add(spawnComponent);

        ashley.addEntity(spawnEntity);
    }

    private void createGridEntites(int tilesX, int tilesY) {
        for (int x = 0; x < tilesX; x++) {
            for (int y = 0; y < tilesY; y++) {
                if((x == 0 && y == tilesY-1) || (x == tilesX-1 && y == 0)) { // spawn tiles
                    continue;
                }
                Entity gridEntity = ashley.createEntity();
                TilePositionComponent gridTpc = ashley.createComponent(TilePositionComponent.class);
                gridTpc.x = x;
                gridTpc.y = y;

                GridComponent gridComponent = ashley.createComponent(GridComponent.class);
                // TODO
                if(x == 0 && y == Statics.settings.tilesY-2) { //(0, N-1) / TL
                    gridComponent.clickable = true;
                }
                if(x == 1 && y == Statics.settings.tilesY-1) { //(1, N-1) / TL
                    gridComponent.clickable = true;
                }
                UnitComponent gridUnitComponent = ashley.createComponent(UnitComponent.class);

                gridEntity.add(gridComponent);
                gridEntity.add(gridTpc);
                gridEntity.add(gridUnitComponent);
                ashley.addEntity(gridEntity);
            }
        }

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

        // remove entities
        if(dirtyEntities.size() > 0) {
            System.out.println("Remove " + dirtyEntities.size());
            for (Entity entity : dirtyEntities) {
                ashley.removeEntity(entity);
            }
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
