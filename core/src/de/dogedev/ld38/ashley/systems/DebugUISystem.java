package de.dogedev.ld38.ashley.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import de.dogedev.ld38.CoordinateMapper;
import de.dogedev.ld38.Statics;
import de.dogedev.ld38.assets.enums.BitmapFonts;

import java.text.DecimalFormat;

/**
 * Created by Furuha on 09.04.2016.
 */
public class DebugUISystem extends EntitySystem implements Disposable {

    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font;
    private OrthographicCamera camera;
    private DecimalFormat floatFormat = new DecimalFormat("#.##");

    public DebugUISystem(OrthographicCamera camera) {
        this.camera = camera;
        font = Statics.asset.getBitmapFont(BitmapFonts.KENNEY_1);
        GLProfiler.enable();
    }

    @Override
    public void update(float deltaTime) {

        batch.begin();
        font.setColor(Color.WHITE);
        Vector3 pos = getMousePosInGameWorld();
        Vector2 tile = CoordinateMapper.getTile((int) pos.x, (int) pos.y);
        if(camera != null)debugLine(260, "mouse x=" + (int) (pos.x) );
        if(camera != null)debugLine(240, "mouse y=" + (int) (pos.y) );
        if(camera != null)debugLine(220, "cam x=" + floatFormat.format(camera.position.x));
        if(camera != null)debugLine(200, "cam y=" + floatFormat.format(camera.position.y));
        debugLine(180, "entities=" + getEngine().getEntities().size());
        if(camera != null)debugLine(160, "zoom=" + camera.zoom);
        if(tile != null)debugLine(140, "x=" + tile.x);
        if(tile != null)debugLine(240 - 120, "y=" + tile.y);
        debugLine(100, "DC=" + GLProfiler.drawCalls);
        debugLine(80, "TXB=" + GLProfiler.textureBindings);
        debugLine(60, "FPS=" + Gdx.graphics.getFramesPerSecond());
        batch.end();

        GLProfiler.reset();

    }

    private Vector3 mouse = new Vector3();
    public Vector3 getMousePosInGameWorld() {
        return camera.unproject(mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    public void debugLine(float pos, String text){
        font.draw(batch, text, 1070, pos, 200, Align.right, false);
    }

    @Override
    public void dispose() {
        font.dispose();
        GLProfiler.disable();
    }


}
