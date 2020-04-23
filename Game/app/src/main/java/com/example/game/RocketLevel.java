package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

class RocketLevel extends Level {
    /**
     * The rocket object that the player controls
     */
    private Rocket rocket;
    /**
     * A list of falling objects(including asteroids and palladium).
     */
    private ArrayList<FallingObject> fallingObjects;

    /**
     * The center of the screen.
     */
    private final float centre = Level.getGridWidth() / 2;


    /**
     * Constructs a rocket level with 5 asteroids, 5 palladium and a rocket.
     */
    static class Builder {

        private ArrayList<FallingObject> fallingObjects;
        private Rocket rocket;
        private Bitmap background;


        Builder() {

        }

        RocketLevel buildLevel() {
            return new RocketLevel(fallingObjects, rocket, background);
        }


        Builder buildFallingObjects() {


            fallingObjects = new ArrayList<>();
            FallingObject asteroid, palladium;

            for (int i = 1; i <= 5; i++) {
                asteroid = new FallingObject.Builder(Icons.asteroid)
                        .setHeight(100).setWidth(100).setXCoordinate(0)
                        .setYCoordinate(0).setSpeed(10).setObjectType("asteroid").build();

                fallingObjects.add(asteroid);

                palladium = new FallingObject.Builder(Icons.palladium)
                        .setHeight(60).setWidth(60).setXCoordinate(0)
                        .setYCoordinate(0).setSpeed(5).setObjectType("palladium").build();

                fallingObjects.add(palladium);
            }

            return this;
        }

        Builder buildRocket(int rocket) {
            this.rocket = new Rocket.Builder(Icons.rocketSprites.get(rocket))
                    .setXCoordinate((Level.getGridWidth() / 2) - 25)
                    .setYCoordinate(Level.getGridHeight() - 200)
                    .setHeight(150)
                    .setWidth(75)
                    .setInterval(1)
                    .setMovementSpeed(10)
                    .build();

            return this;
        }

        Builder buildBackground(int background){
            this.background = Icons.background.get(background);
            return this;
        }

    }

    /**
     * Constructs a rocketLevel.
     *
     * @param fallingObjects Asteroids and palladium in this level.
     * @param rocket         The rocket controlled by the player.
     */
    private RocketLevel(ArrayList<FallingObject> fallingObjects, Rocket rocket, Bitmap background) {
        super("RocketLevel");
        this.fallingObjects = fallingObjects;
        this.rocket = rocket;
        this.setBackground(background);

        for (FallingObject o : this.fallingObjects) {
            relocate(o);
        }

    }

    /**
     * Calculate the score of this RocketLevel based on the player's statistics.
     *
     * @return Score of the level.
     */
    @Override
    int calculateScore() {
        return (100 * getPalladium() + 20 * this.elapsedTime());
    }

    /**
     * Updates the location of every asteroid and checks for collision.
     * If the rocket collides with an asteroid, end the game.
     */
    void update() {
        if (!isComplete()) {
            for (FallingObject o : fallingObjects) {
                o.update();
                if (o.collide(rocket)) {
                    if (o.getObjectType().equals("asteroid")) {
                        this.passLevel();
                        this.completeLevel();
                        return;
                    } else {
                        collectPalladium();
                        relocate(o);
                    }
                }
                if (o.getHitBox().top >= Level.getGridHeight()) {
                    relocate(o);
                }
            }
            if ((this.fallingObjects.size() - 10) < (this.elapsedTime() / 30)) {
                this.escalateDifficulty();
            }
        }
    }

    /**
     * Displays the current state of the game on the canvas.
     *
     * @param canvas the canvas this level is drawn on
     */
    @Override
    void draw(Canvas canvas) {
        for (FallingObject fallingObject : fallingObjects) {
            fallingObject.draw(canvas);
        }
        rocket.draw(canvas);
    }

    /**
     * Calls rocket.update() to inform rocket of the user's touch.
     *
     * @param x the (scaled) horizontal location of the screen where the user touched.
     * @param y the (scaled) vertical location of the screen where the user touched.
     */
    @Override
    void onTouch(int x, int y) {
        rocket.setMovingRight(x > centre);
        rocket.update();
    }

    /**
     * Relocate the object to a random location above the screen.
     * (used for FallingObject, which are asteroids and palladium.)
     *
     * @param o GameObject to relocate.
     */
    private void relocate(GameObject o) {
        Random r = new Random();
        int x = o.getWidth() + r.nextInt(Level.getGridWidth() - 2 * o.getWidth());
        int y = -r.nextInt(Level.getGridHeight()) - o.getHeight();
        o.setLocation(x, y);
    }

    /**
     * Increases the difficulty of the game by increasing the number and speed of asteroids.
     */
    private void escalateDifficulty() {
        FallingObject asteroid = new FallingObject.Builder(Icons.asteroid)
                .setHeight(100).setWidth(100).setXCoordinate(0)
                .setYCoordinate(0).setSpeed(10).setObjectType("asteroid").build();
        this.relocate(asteroid);
        this.fallingObjects.add(asteroid);

        Random x = new Random();
        for (FallingObject o : fallingObjects) {
            if (o.getObjectType().equals("asteroid")) {
                o.speedUp(x.nextInt(2) + 1);
            }
        }

    }


}
