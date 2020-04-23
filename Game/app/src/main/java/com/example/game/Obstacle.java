package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Color;

class Obstacle extends GameObject {

    private int OBSTACLE_SPEED = 10;

    Obstacle(int x, int y, int width, int height, Bitmap icon) {

        super(x, y, width, height, icon);
    }

    @Override
    void update() {
        // get obstacles coordinate and reduce x by obstacleSpeed.
        this.setLocation(this.getXCoordinate() - OBSTACLE_SPEED, this.getYCoordinate());
        OBSTACLE_SPEED += 0.1;

    }
}
