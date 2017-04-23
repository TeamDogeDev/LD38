package de.dogedev.ld38.screens;

import com.badlogic.ashley.core.Entity;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Key;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.ComponentMappers;
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

    public GameScreen() {
        cloudBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.zoom = 2f;
        camera.setToOrtho(false, 1280, 720);

        clouds = Statics.asset.getTexture(Textures.CLOUD);
        initShader();

        TiledMap map = mapBuilder.buildMap(settings.tilesX, settings.tilesY);

        mapRenderSystem = new MapRenderSystem(camera, map);
        renderSystem = new RenderSystem(camera);

        ashley.addSystem(new InputSystem(camera, this));
        ashley.addSystem(new CameraSystem(camera));
        ashley.addSystem(new MovementSystem());
        ashley.addSystem(new AiSystem(this));
        ashley.addSystem(mapRenderSystem);
        ashley.addSystem(renderSystem);
        ashley.addSystem(new DebugUISystem(camera));
        ashley.addSystem(new TickSystem());
        ashley.addSystem(new OverlayRenderSystem(camera));
        ashley.addSystem(new GridSystem(camera));


        dirtyEntities = ashley.getEntitiesFor(Family.all(DirtyComponent.class).get());

        createSpawnEntity(0, Statics.settings.tilesY-1, PlayerComponent.PLAYER.A);
        createSpawnEntity(Statics.settings.tilesX-1, 0, PlayerComponent.PLAYER.B);        createGridEntites(Statics.settings.tilesX, Statics.settings.tilesY);


    }

    private void createSpawnEntity(int tileX, int tileY, PlayerComponent.PLAYER player) {
        Entity spawnEntity = ashley.createEntity();
        RenderComponent renderComponent = ashley.createComponent(RenderComponent.class);
        renderComponent.region = Statics.asset.getTextureAtlasRegion(Key.OBJECTS_CASTLE_OPEN);

        SpawnComponent spawnComponent = ashley.createComponent(SpawnComponent.class);
        UnitComponent unitComponent = ashley.createComponent(UnitComponent.class);

        PlayerComponent playerComponent = ashley.createComponent(PlayerComponent.class);
        playerComponent.player = player;
        spawnEntity.add(playerComponent);

        TilePositionComponent tilePositionComponent = ashley.createComponent(TilePositionComponent.class);

        tilePositionComponent.x = tileX; //
        tilePositionComponent.y = tileY;

        GridComponent gridComponent = ashley.createComponent(GridComponent.class);
        if(PlayerComponent.PLAYER.A == player) gridComponent.clickable = 1;

        spawnEntity.add(tilePositionComponent);
        spawnEntity.add(renderComponent);
        spawnEntity.add(gridComponent);
        spawnEntity.add(unitComponent);
        spawnEntity.add(spawnComponent);

        ashley.addEntity(spawnEntity);
    }

//    public void spawnWarrior(Vector2 spawnTile, Vector2 targetTile, PlayerComponent.PLAYER player, float speed, int num) {
//        for (int i = 0; i < num; i++) {
//            spawnWarrior(spawnTile, targetTile, speed);
//        }
//    }

    public void spawnWarrior(Vector2 spawnTile, Vector2 targetTile, PlayerComponent.PLAYER player, float speed) {

        ImmutableArray<Entity> entitiesFor = Statics.ashley.getEntitiesFor(
                Family.all(TilePositionComponent.class, UnitComponent.class).get());

        TilePositionComponent tilePositionComponent;
        UnitComponent uc;

        for(Entity entity : entitiesFor) {
            tilePositionComponent = ComponentMappers.tilePos.get(entity);
            if(tilePositionComponent.x == spawnTile.x && tilePositionComponent.y == spawnTile.y) {
                uc = ComponentMappers.unit.get(entity);
                if(uc.units > Statics.settings.minPeeps) {
                    uc.units--;
                    Entity warrior = Statics.ashley.createEntity();
                    RenderComponent rc = Statics.ashley.createComponent(RenderComponent.class);
                    rc.region = Statics.asset.getTextureAtlasRegion(player == PlayerComponent.PLAYER.A ? Key.CHARACTERS_CHAR_0 : Key.CHARACTERS_CHAR_1) ;
                    rc.angle = 90;
                    warrior.add(rc);

                    PositionComponent tpc = Statics.ashley.createComponent(PositionComponent.class);
                    // w/4 & h/4
                    int spawnOffsetX = MathUtils.random(-Statics.settings.tileWidth >> 2, Statics.settings.tileWidth >> 2);
                    int spawnOffsetY = MathUtils.random(-Statics.settings.tileHeight >> 2, Statics.settings.tileHeight >> 2);
                    tpc.x = CoordinateMapper.getTilePosX((int) spawnTile.x, (int) spawnTile.y) + spawnOffsetX;
                    tpc.y = CoordinateMapper.getTilePosY((int) spawnTile.y) + spawnOffsetY;

                    warrior.add(tpc);

                    PeepComponent peepComponent = Statics.ashley.createComponent(PeepComponent.class);
                    peepComponent.player = player;
                    warrior.add(peepComponent);

                    MovementComponent mvc = Statics.ashley.createComponent(MovementComponent.class);
                    Vector2 tilePos = CoordinateMapper.getTilePos((int) targetTile.x, (int) targetTile.y);
                    mvc.x = (int) tilePos.x;
                    mvc.y = (int) tilePos.y;
                    mvc.speed = speed;
                    warrior.add(mvc);

                    LookComponent lookComponent = Statics.ashley.createComponent(LookComponent.class);
                    lookComponent.x = targetTile.x;
                    lookComponent.y = targetTile.y;

                    warrior.add(lookComponent);

                    Statics.ashley.addEntity(warrior);
                }
                break;
            }
        }
    }

    private void createGridEntites(int tilesX, int tilesY) {
        for (int x = 0; x < tilesX; x++) {
            for (int y = 0; y < tilesY; y++) {
                if ((x == 0 && y == tilesY - 1) || (x == tilesX - 1 && y == 0)) { // spawn tiles
                    continue;
                }
                Entity gridEntity = ashley.createEntity();
                TilePositionComponent gridTpc = ashley.createComponent(TilePositionComponent.class);
                gridTpc.x = x;
                gridTpc.y = y;

                GridComponent gridComponent = ashley.createComponent(GridComponent.class);
                // TODO
                if (x == 0 && y == Statics.settings.tilesY - 2) { //(0, N-1) / TL
                    gridComponent.clickable = 1;
                }
                if (x == 1 && y == Statics.settings.tilesY - 1) { //(1, N-1) / TL
                    gridComponent.clickable = 1;
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
        Gdx.gl.glClearColor(29 / 255.f, 128 / 255.f, 71 / 255.f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ashley.update(delta);

        windData.add(windVelocity);
        cloudShader.begin();
        cloudShader.setUniformf("scroll", windData);
        cloudShader.setUniformf("camPosition", camera.position);
        cloudShader.end();

        cloudBatch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (camera.zoom == 1) {
            cloudBatch.begin();
            cloudBatch.draw(clouds, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            cloudBatch.end();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            mapRenderSystem.setMap(mapBuilder.buildMap(settings.tilesX, settings.tilesY));
        }

        // remove dirty entities
        if (dirtyEntities.size() > 0) {
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
