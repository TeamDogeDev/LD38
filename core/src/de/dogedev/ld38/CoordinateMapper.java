package de.dogedev.ld38;

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

}
