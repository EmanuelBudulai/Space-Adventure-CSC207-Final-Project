package com.example.game;

import android.graphics.Bitmap;

import java.util.Random;

class MashCoin extends GameObject {

    private Random rand = new Random();
    private int gridHeight;
    private int gridWidth;

    /**
     * Create a coin that appears in random parts of the screen.
     * Player earns coins for tapping on it.
     * @param x horizontal coordinate of the coin
     * @param y vertical coordinate of the coin
     */
    MashCoin(int x, int y, int width, int height, Bitmap icon) {
        super(x, y, width, height, icon);
        gridWidth = Level.getGridWidth();
        gridHeight = Level.getGridHeight();
    }

    /**
     * Moves the coin to a random location on screen.
     */
    private void forceMove() {
        int newX = rand.nextInt(gridWidth - 2 * this.getHitBox().width()) + this.getHitBox().width();
        int newY = rand.nextInt(gridHeight - 2 * this.getHitBox().height()) + this.getHitBox().height();
        this.setLocation(newX, newY);
    }

    /**
     * Decides whether the coin will be randomly moved to a new location each time step of the game.
     */
    @Override
    void update() {
        if (rand.nextDouble() < 0.05) {
            this.forceMove();
        }
    }
}
