package de.dogedev.ld38.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.ashley.systems.*;
import de.dogedev.ld38.map.MapBuilder;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class GameScreen implements Screen {


    private OrthographicCamera camera;
    MapRenderSystem mapRenderSystem;
    MapBuilder mapBuilder = new MapBuilder();

    public GameScreen(){
        camera = new OrthographicCamera();
        camera.zoom = 2f;
        camera.setToOrtho(false, 1280, 720);

        TiledMap map = mapBuilder.buildMap(Statics.settings.tilesX, Statics.settings.tilesY);

        mapRenderSystem = new MapRenderSystem(camera, map);

        Statics.ashley.addSystem(new InputSystem(camera));
        Statics.ashley.addSystem(new CameraSystem(camera));
        Statics.ashley.addSystem(mapRenderSystem);
        Statics.ashley.addSystem(new RenderSystem(camera));
        Statics.ashley.addSystem(new DebugUISystem(camera));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(39/255.f, 174/255.f, 96/255.f, 1f);
        Gdx.gl.glClearColor(39/255.f, 0/255.f, 96/255.f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Statics.ashley.update(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            mapRenderSystem.setMap(mapBuilder.buildMap(Statics.settings.tilesX, Statics.settings.tilesY));
        }
    }

    @Override
    public void resize(int width, int height) {

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
