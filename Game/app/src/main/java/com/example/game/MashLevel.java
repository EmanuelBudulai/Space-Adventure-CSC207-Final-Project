package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class MashLevel extends Level {

    private final int GAME_DURATION = 30; // game duration in seconds
    private final int SCORE_PER_TAP = 10;
    private GameObject[] gameObjects;
    private int numberTaps;
    private long elapsedTime;
    private long startTime = 0;
    private long endTime = 0;
    private boolean rearrange = false;

    /**
     * Constructer for MashLevel object for the Button MAshing Game (Game 2).
     */
    private MashLevel(GameObject[] gameObjects, Bitmap background) {
        super("MashLevel");
        this.elapsedTime = 0;
        this.gameObjects = gameObjects;
        this.setBackground(background);
    }

    /**
     * Helper method responsible for swapping the positions of the two buttons
     * every 5 seconds.
     */
    private void rearrangeButtons() {
        Builder builder = new Builder();
        int astronautX = builder.randomX(100);
        int astronautY = builder.randomY(100);
        int xAlien1 = builder.randomX(100);
        int yAlien1 = builder.randomY(100);
        int xAlien2 = builder.randomX(100);
        int yAlien2 = builder.randomY(100);
        gameObjects[0].setLocation(astronautX, astronautY);
        gameObjects[1].setLocation(xAlien1, yAlien1);
        gameObjects[2].setLocation(xAlien2, yAlien2);
        rearrangeCoin();
    }

    /**
     * Helper method that takes x and y coordinates and returns true if the coordinates are
     * contained within a Alien object in game.
     *
     * @param x horizontal coordinate of point
     * @param y vertical coordinate of point
     * @return true if input coordinates intersect a Alien object.
     */
    private boolean checkAlienCollision(int x, int y) {
        GameObject alien1 = gameObjects[1];
        GameObject alien2 = gameObjects[2];
        return alien1.getHitBox().contains(x, y) || alien2.getHitBox().contains(x, y);

    }

    /**
     * Helper method that assigns the coin a new random location on screen.
     */
    private void rearrangeCoin() {
        Builder b = new Builder();
        int newX = b.randomX(100);
        int newY = b.randomY(100);
        gameObjects[3].setLocation(newX, newY);

    }

    /**
     * Calulate and return the player's final score for this game
     *
     * @return The score.
     */
    @Override
    int calculateScore() {
        return (SCORE_PER_TAP * numberTaps) + (100 * this.getPalladium());
    }

    /**
     * Denotes a time step in the game.
     * updates the current elapsed time in the game.
     * Randomly rearranges the objects every 3 seconds.
     */
    @Override
    void update() {
        endTime = elapsedTime - startTime;
        this.elapsedTime = this.elapsedTime();


        if (elapsedTime > GAME_DURATION) {
            this.passLevel();
            this.completeLevel();
        } else if (elapsedTime % 3 == 0) {
            rearrange = true;
            startTime = endTime;
        } else if (rearrange) {
            rearrangeButtons();
            rearrange = false;
        }
    }

    /**
     * Draws the buttons on the game screen.
     *
     * @param canvas the canvas this level is drawn on
     */
    @Override
    void draw(Canvas canvas) {
        for (GameObject item : this.gameObjects) {
            item.draw(canvas);
        }
    }

    /**
     * Increases player's points on this level upon tapping on the Alien button.
     * Decrements the player's number of lives upon tapping on a Alien button.
     * Ends the game if player runs out of lives or runs out of time.
     *
     * @param x float value denoting the horizontal coordinate of the player's tap.
     * @param y float vlaue denoting the vertical coordinate of the player's tap.
     */
    @Override
    void onTouch(int x, int y) {
        boolean touchedAlien = false;
        boolean touchedAstronaut = false;

        Log.i("COORDINATES OF TAP:", Integer.toString(x) + " , " + Integer.toString(y));
        Log.i("TOUCHED BUTTON:", String.valueOf(gameObjects[0].getHitBox().contains(x, y)));
        if (gameObjects[0].getHitBox().contains(x, y)) {
            touchedAstronaut = true;

        } else if (gameObjects[3].getHitBox().contains(x, y)) {
            rearrangeCoin();
            this.collectPalladium();
//
        }
        // Check if player touched Astronaut
        else if (checkAlienCollision(x, y)) {
            touchedAlien = true;
        }

        if (touchedAstronaut) {
            rearrangeButtons();
            numberTaps++;
        } else if (touchedAlien) {
            this.completeLevel();
        }

    }

    /**
     * A nested builder for construction of a MashLevel
     */
    static class Builder {

        private GameObject[] buttons;
        private Bitmap background;

        public MashLevel buildLevel() {
            return new MashLevel(buttons, background);
        }

        Builder buildBackground(int background) {
            this.background = Icons.background.get(background);
            return this;
        }

        /**
         * Creates and returns an array of TeleportingButton objects required for MashLevel.
         *
         * @return Array of TeleportingButton objects to be used in MashLevel.
         */
        public Builder buildButtons(int avatar) {


            //instantiate button list
            buttons = new GameObject[4];

            // Create the two buttons and coin.
            int xAstronaut = randomX(100);
            int yAstronaut = randomY(100);
            int xAlien1 = randomX(100);
            int yAlien1 = randomY(100);
            int xAlien2 = randomX(100);
            int yAlien2 = randomY(100);

            TeleportingButton astronautButton = new TeleportingButton(xAstronaut, yAstronaut,
                    100, 100, Icons.astronautAvatars.get(avatar));

            TeleportingButton alienButton1 = new TeleportingButton(xAlien1, yAlien1,
                    100, 100, Icons.alienIcon);

            TeleportingButton alienButton2 = new TeleportingButton(xAlien2, yAlien2, 100,
                    100, Icons.alienIcon);

            MashCoin coin = new MashCoin(100, 100, 100, 100,
                    Icons.palladium);


            // Add the thee buttons to the list of gameObjects.
            buttons[0] = astronautButton;
            buttons[1] = alienButton1;
            buttons[2] = alienButton2;
            buttons[3] = coin;

            return this;


        }

        /**
         * Computes and returns a randomly generated horizontal coordinate within the screen.
         *
         * @param width width of the object that the coordinate is being generated for.
         * @return randomly generated horizontal coordinate within screen bounds.
         */
        int randomX(int width) {
            return (int) (Math.random() * (Level.getGridWidth() - width));

        }

        /**
         * Computes and returns a randomly generated vertical coordinate within the screen.
         *
         * @param height height of the object that the coordinate is being generated for.
         * @return randomly generated vertical coordinate within screen bounds.
         */
        int randomY(int height) {
            return (int) (Math.random() * (Level.getGridHeight() - height));
        }


    }
}
