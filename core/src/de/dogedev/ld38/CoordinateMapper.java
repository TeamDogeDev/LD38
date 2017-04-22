package de.dogedev.ld38;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class CoordinateMapper {

    public static int getTilePosX(int x, int y) {
        int x2 = x * 120 + 60;
        if (y % 2 == 0) {
            x2 += 60;
        }
        return x2;
    }

    public static int getTilePosY(int y) {
        return y * 104 + 70;
    }

    public static Vector2 getTilePos(int x, int y) {
        return new Vector2(getTilePosX(x, y), getTilePosY(y));
    }

    private static Vector2 click = new Vector2();

    public static Vector2 getTile(int x, int y) {
        int tilesX = Statics.settings.tilesX;
        int tilesY = Statics.settings.tilesY;

        int xAprox = x/120;
        int yAprox = y/100;



        for(int xT = (xAprox - 3); xT < (xAprox+3); xT++){
            if(xT < 0) continue;
            if(xT >= tilesX) continue;
            for(int yT = (yAprox - 3); yT < (yAprox+3); yT++){
                if(yT < 0) continue;
                if(yT >= tilesY) continue;
                click.set(x, y);
                click.sub(getTilePos(xT, yT));
                if(click.len() <= 60){
                    return new Vector2(xT, yT);
                }
            }
        }
        return null;
    }
}
