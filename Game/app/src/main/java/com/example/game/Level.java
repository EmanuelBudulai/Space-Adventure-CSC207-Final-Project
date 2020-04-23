package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;


abstract class Level {

    public ArrayList<GameObject> gameObjects;
    /**
     * the width of the grid where the level is drawn
     */
    private static final int GRID_WIDTH = 1080;
    /**
     * the height of the grid where the level is drawn
     */
    private static final int GRID_HEIGHT = 1920;

    /**
     * the ratio between the screens width in pixels and the levels grid width used to scale coordinates
     */
    private static final float X_SCALE_FACTOR = GameView.getScreenWidth() / ((float)GRID_WIDTH);
    /**
     * the ratio between the screens height in pixels and the levels grid height used to scale coordinates
     */
    private static final float Y_SCALE_FACTOR = GameView.getScreenHeight() / ((float)GRID_HEIGHT);
    /**
     * the completed state of this level, either true or false
     */
    private boolean isComplete;
    /**
     * Starting time of the level.
     */
    private long startTime;

    /**
     * Amount of time elapsed during a pause.
     */
    private long pauseTime;
    /**
     * if this level has been passed
     */
    private boolean levelPassed = false;
    /**
     * the palladium collected by the player during this level
     */
    private int pCollected;
    /**
     * the name of this level
     */
    private String name;
    /**
     * the chosen background of this level
     */
    private Bitmap background;


    Level(String name){
        pCollected = 0;
        startTime = System.currentTimeMillis();
        pauseTime = 0;
        background = Icons.background.get(0); //default background.
        this.name = name;
    }



    /**
     * update the level
     */
    abstract void update();

    /**
     * draw this level
     *
     * @param canvas the canvas this level is drawn on
     */
    abstract void draw(Canvas canvas);

    /**
     * Handle this level's response to a touch event
     *
     * @param x the grid-scaled x coordinate of the touch
     * @param y the grid-scaled y coordinate of the touch
     */
    abstract void onTouch(int x, int y);

    /**
     * calculate and return the score deserved to the player based on the current state of the level
     */
    abstract int calculateScore();

    public String name(){
        return this.name;
    }


    /**
     * returns whether or not this level is completed
     */
    boolean isComplete() {
        return isComplete;
    }


    /**
     * sets the completed state of this level to true
     */
    void completeLevel() {
        isComplete = true;
    }

    /**
     * sets the completed state of this level to true
     */
    static int getGridWidth() {
        return GRID_WIDTH;
    }

    static int getGridHeight() {
        return GRID_HEIGHT;
    }

    /**
     * Calculates the amount of time passed in the game.
     *
     * @return how long the game has went on.
     */
    int elapsedTime() {
        return (int) (System.currentTimeMillis() - this.startTime) / 1000;
    }

    /**
     * Record the time when pause happens.
     */
    void startPause(){this.pauseTime = System.currentTimeMillis();}

    /**
     * At the end of a pause, update startTime so the time elapsed during the pause isn't counted
     * as in game time elapsed.
     */
    void endPause(){this.startTime += (System.currentTimeMillis() - this.pauseTime);
    pauseTime = 0;}

    boolean isPaused(){
        return (pauseTime != 0);
    }
    /**
     * Scales the given vertical position value according to the vertical scale factor.
     * @param i unscaled vertical position value.
     * @return scaled vertical position value.
     */
    static int scaleVertically(int i){
        float value = (float) i;
        return (int)(value * Y_SCALE_FACTOR);
    }

    /**
     * Scales the given horizontal position value according to the horizontal scale factor.
     * @param i unscaled horizontal position value.
     * @return scaled horizontal position value.
     */
    static int scaleHorizontally(int i){
        float value = (float) i;
        return (int)(value * X_SCALE_FACTOR);
    }

    /**
     * Increases the amount of palladium collected by 1.
     */
    void collectPalladium(){pCollected ++;}

    /**
     * @return the amount of palladium collected in this level.
     */
    int getPalladium(){
        return pCollected;
    }

    static float getXScaleFactor() {
        return X_SCALE_FACTOR;
    }

    static float getYScaleFactor() {
        return Y_SCALE_FACTOR;
    }

    /**
     * Changes the variable indicating whether or not this level has been passed.
     */
    void passLevel(){
        this.levelPassed = true;
    }

    /**
     * @return boolean value indicating whether this level was passed or failed.
     */
    boolean getLevelPassStatus(){
        return this.levelPassed;
    }

    void setBackground(Bitmap background){this.background = background;}

    Bitmap getBackground(){return this.background;}
}
