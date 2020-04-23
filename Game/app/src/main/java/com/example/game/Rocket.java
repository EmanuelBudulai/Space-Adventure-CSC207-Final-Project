package com.example.game;

import android.graphics.Bitmap;

class Rocket extends GameObject {
    /**
     * Distance the rocket travels each time.
     */
    private final int movementSpeed;
    /**
     * Time restriction between each movement.
     */
    private final int interval;
    /**
     * Time remaining until next movement is allowed.
     */
    private int timeRemain;
    /**
     * Direction of movement.
     */
    private boolean movingRight;


    /**
     * A builder for a rocket object, makes constructing rocket easier and simpler.
     */
     static class Builder{
        private Bitmap icon;
        private int xCoordinate;
        private int yCoordinate;
        private int width;
        private int height;
        private int interval;
        private int movementSpeed;

        Builder(Bitmap icon){
            this.icon=icon;
        }

        Builder setXCoordinate(int x){
            this.xCoordinate = x;
            return this;
        }

        Builder setYCoordinate(int y){
            this.yCoordinate = y;
            return this;
        }

        Builder setWidth(int width){
            this.width = width;
            return this;
        }

        Builder setHeight(int height){
            this.height = height;
            return this;
        }

        Builder setInterval(int interval){
            this.interval = interval;
            return this;
        }

        Builder setMovementSpeed(int movementSpeed){
            this.movementSpeed = movementSpeed;
            return this;
        }

        Rocket build(){
            Rocket rocket = new Rocket(this.xCoordinate,
                    this.yCoordinate,
                    this.width,
                    this.height,
                    this.icon,
                    this.movementSpeed,
                    this.interval);
            rocket.setMovingRight(true); //Default Value.
            return rocket;
        }
    }

    /**
     * Creates a new Rocket object.
     *
     * @param xCoordinate (unscaled) horizontal location of the rocket (left).
     * @param yCoordinate (unscaled) vertical location of the rocket (top).
     * @param width       (unscaled) width of the rocket.
     * @param height      (unscaled) height of the rocket.
     * @param icon        appearance of the rocket.
     * @param speed       distance travelled by the rocket per tap.
     * @param interval    time between each movement.
     */
    private Rocket(int xCoordinate,
                   int yCoordinate,
                   int width, int height, Bitmap icon, int speed, int interval) {
        super(xCoordinate, yCoordinate, width, height, icon);
        this.movementSpeed = speed;
        this.interval = interval;
        this.timeRemain = 0;
    }

    /**
     * Moves the rocket a set distance to the left or right.
     * The rocket can only move once every half a second (30 updates).
     * If the rocket moves outside of the screen, make the rocket appear on the other side of the screen.
     */
    @Override
    void update() {
        if (timeRemain == 0) {
            if (movingRight) {
                if (this.getXCoordinate() + movementSpeed <= Level.getGridWidth()- this.getWidth()) {
                    this.setLocation(this.getXCoordinate() + movementSpeed, this.getYCoordinate());
                } else {
                    this.setLocation(0, this.getYCoordinate());
                }
            } else {
                if (this.getXCoordinate() - movementSpeed >= this.getWidth()) {
                    this.setLocation(this.getXCoordinate() - movementSpeed, this.getYCoordinate());
                } else {
                    this.setLocation(Level.getGridWidth() - this.getWidth(), this.getYCoordinate());
                }
            }
            timeRemain = interval;
        } else {
            timeRemain--;
        }
    }

    /**
     * Sets the direction of the rocket according to the input parameter.
     *
     * @param b Which direction the rocket will travel, true = right, false = left.
     */
    void setMovingRight(boolean b) {
        movingRight = b;
    }
}
