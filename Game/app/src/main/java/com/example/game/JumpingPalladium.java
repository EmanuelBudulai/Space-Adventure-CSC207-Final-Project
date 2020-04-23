package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Color;

public class JumpingPalladium extends GameObject {
    /**
     * the x velocity of the palladium
     */
    private int PALLADIUM_SPEED = 10;

    JumpingPalladium(int x, int y, int width, int height, Bitmap icon) {

        super(x, y, width, height, icon);
    }

    /**
     * updates this JumpingPalladium by moving it by its xvelocity PALLADIUM_SPEED
     */
    @Override
    void update() {
        // get obstacles coordinate and reduce x by obstacleSpeed.
        this.setLocation(this.getXCoordinate() - PALLADIUM_SPEED, this.getYCoordinate());
        PALLADIUM_SPEED += 0.1;
    }
}
