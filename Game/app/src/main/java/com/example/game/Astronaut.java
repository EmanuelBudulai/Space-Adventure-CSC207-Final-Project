package com.example.game;


import android.graphics.Bitmap;
import android.graphics.Color;

public class Astronaut extends GameObject {
    // test test test
    /**
     * whether the astronaut is in a jumping state
     */
    private boolean isJumping;
    /**
     * the vertical velocity of this astronaut
     */
    private int yVelocity;

    /**
     * Creates a new Astronaut object.
     *
     * @param x   the horizontal coordinate of the Astronaut.
     * @param y   the vertical coordinate of the Astronaut.
     * @param width the width of the Astronaut
     * @param height the height of the Astronaut
     * @param icon the icon for the Astronaut
     */
    Astronaut(int x, int y, int width, int height, Bitmap icon) {
        super(x, y,  width, height, icon);
        //initially set astronaut to not jump and there is no change in y coordinate
        isJumping = false;
        yVelocity = 0;
    }


    /** update only changes Astronaut's Y coordinate by jumpSpeed. Initially jumpSpeed is 0
     * when player wants to jump, jumpSpeed changes to -1 and on each update, Astronaut goes
     * up by 1, until it reaches y=450. After that, on each update it comes down by 1 until it
     * it reaches 500. This implementation is only for testing and can be changed later
     */
    @Override
    void update() {
        // increases/decreases y coordinate based on whether astronaut jumping up or coming down
        setLocation(getXCoordinate(), getYCoordinate() + yVelocity);

        // if astronaut is now back on the ground, he should not change his coordinates and
        // should stop jumping
        if (isJumping) {
            yVelocity++;

            if (getYCoordinate() >= 1000){
            setLocation(getXCoordinate(), 1000);
            yVelocity = 0;
            isJumping = false;
            }
        }
    }

    /**
     * returns true if this astronaut is jumping, false otherwise
     */
    boolean isJumping() {

        return isJumping;
    }

    /**
     * makes this astronaut start jumping
     */
    void jump() {
        isJumping = true;
        yVelocity = - 25;
    }
}
