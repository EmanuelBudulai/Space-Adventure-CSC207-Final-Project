package com.example.game;

import android.graphics.Bitmap;


class FallingObject extends GameObject {

    /**
     * Rate at which the object moves downward.
     */
    private int fallingSpeed;
    /**
     * A string indicating whether if this object is an asteroid or a palladium.
     */
    private String objectType;


    /**
     * A builder for an asteroid object, makes constructing rocket easier and simpler.
     */
    static class Builder{
        private Bitmap icon;
        private int xCoordinate;
        private int yCoordinate;
        private int width;
        private int height;
        private int speed;
        private String objectType;

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

        Builder setSpeed(int speed){
            this.speed = speed;
            return this;
        }

        Builder setObjectType(String objectType){
            this.objectType = objectType;
            return this;
        }
        /**
         * builds the FallingObject
         */
        FallingObject build(){
            return new FallingObject(this.xCoordinate,
                    this.yCoordinate,
                    this.width,
                    this.height,
                    this.icon, this.speed, this.objectType);
        }
    }


    /**
     * Constructs a new asteroid.
     *
     * @param xCoordinate (unscaled) horizontal coordinate of asteroid.
     * @param yCoordinate (unscaled) vertical coordinate of asteroid.
     * @param width       (unscaled) width of the asteroid.
     * @param height      (unscaled) height of the asteroid.
     * @param icon        the appearance of the asteroid.
     * @param speed       the distance travelled by the asteroid every update.
     * @param objectType  a string indicating whether this object is a palladium or an asteroid.
     */
    private FallingObject(int xCoordinate,
                          int yCoordinate,
                          int width, int height, Bitmap icon, int speed, String objectType) {
        super(xCoordinate, yCoordinate, width, height, icon);
        this.fallingSpeed = speed;
        this.objectType = objectType;
    }

    /**
     * Moves the asteroid downward according to asteroidSpeed.
     * If the asteroid moves below the screen, move it back on top.
     */
    @Override
    void update() {
        this.setLocation(this.getXCoordinate(), this.getYCoordinate() + this.fallingSpeed);
    }

    String getObjectType(){
        return this.objectType;
    }

    void speedUp(int increment){
        this.fallingSpeed += increment;
    }
}
