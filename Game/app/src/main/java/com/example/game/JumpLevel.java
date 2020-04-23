package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JumpLevel extends Level {

    /**
     * The astronaut object the player controls and  a list of objects astronaut interacts with
     * such as Palladiums and Obstacles
     */
    private Astronaut astronaut;
    private List<GameObject> gameObjects;

    /**
     * Stats such as the number of lives player has and how long does the game last
     */
    private int lives;
    private int endTime;

    /**
     * A Rect to represent the platform the astronaut is running on and its corresponding Paint
     */
    private Rect ground;
    private Paint paint;

    /**
     * Builder class for JumpLevel to help us build immutable JumpLevel instances without having
     * to define several different constructors while also making it easier to extend upon
     */
    static class Builder {

        private Astronaut astronaut;
        private ArrayList<GameObject> gameObjects;
        private Bitmap background;
        private int lives;
        private int endTime;
        private Rect ground;
        private Paint paint;

        Builder(int avatar) {
            astronaut = new Astronaut(300, 1000, 100, 100,
                    Icons.astronautAvatars.get(avatar));
            gameObjects = new ArrayList<>();
        }

        Builder buildLives(int lives) {
            this.lives = lives;
            return this;
        }

        Builder buildObstacle() {
            gameObjects.add(new Obstacle(1500, 1000, 100, 100, Icons.asteroid));

            return this;
        }

        Builder buildPalladium() {
            gameObjects.add(new JumpingPalladium(1000, 700, 100, 100, Icons.palladium));

            return this;
        }

        Builder buildEndTime(int endTime) {
            this.endTime = endTime;
            return this;
        }

        Builder buildBackground(int background){
            this.background = Icons.background.get(background);
            return this;
        }

        Builder buildGround(){
            this.ground = new Rect(0, Level.scaleVertically(this.astronaut.getYCoordinate()+this.astronaut.getHeight()), 1100,
                    Level.scaleVertically(this.astronaut.getYCoordinate()+this.astronaut.getHeight()+100));

            return this;
        }

        Builder buildPaint(int color) {
            paint = new Paint();
            paint.setColor(color);

            return this;
        }

        JumpLevel buildLevel() {
            return new JumpLevel(this);
        }
    }

    private JumpLevel(Builder builder) {

        super("JumpLevel");

        this.astronaut = builder.astronaut;
        this.gameObjects = builder.gameObjects;
        this.lives = builder.lives;
        this.endTime = builder.endTime;
        this.setBackground(builder.background);
        this.ground = builder.ground;
        this.paint = builder.paint;

    }

    /**
     * Update modifies obstacles and palladiums location, and detects collision.
     * If there is any collision, relocate obstacle by calling resetGameObject.
     */

    @Override
    void update() {
        astronaut.update();
        for (GameObject o : gameObjects) {
            // if obstacle collides with astro, reset location and reduce life.
            // if obstacle out of screen, reset location.
            o.update();
            if (Rect.intersects(o.getHitBox(), astronaut.getHitBox())) {
                if (o instanceof Obstacle)
                    lives -= 1;
                if (o instanceof JumpingPalladium)
                    collectPalladium();
                resetGameObject(o);

            }

            if (o.getHitBox().left < 0)
                resetGameObject(o);
        }

        if (lives < 1) {
            completeLevel();
        } else if (elapsedTime() >= endTime) {
            completeLevel();
            passLevel();
        }
    }

    /**
     * Draws the platform and all game objects at their current position on the canvas
     *
     * @param canvas:  the canvas this level is drawn on
     */
    @Override
    void draw(Canvas canvas) {
        canvas.drawRect(ground, paint);
        astronaut.draw(canvas);
        for (GameObject o : gameObjects) {
            o.draw(canvas);
        }
    }

    /**
     * Makes the astronaut jump if he isnt already jumping
     *
     * @param x  X coordinate of point tapped on the screen
     * @param y Y coordinate of point tapped on the screen
     */
    public void onTouch(int x, int y) {
        // jump only if astronaut is NOT jumping
        if (!astronaut.isJumping())
            astronaut.jump();
    }


    /**
     * Player is given 50 points for each palladium collected, 10 points for each second survived,
     * and 100 points for each life left.
     *
     * @return score for JumpLevel
     */
    public int calculateScore() {
        return (50 * getPalladium() + 10 * elapsedTime() + 100 * lives);
    }

    /**
     * Resets the position of o depending on what instance it is of
     *
     * @param o  GameObject whose position needs to be reset
     */
    public void resetGameObject(GameObject o) {
        Random rand = new Random();
        int randomGap = rand.nextInt(500);
        int randomHeight = rand.nextInt(200);
        if (o instanceof Obstacle)
            o.setLocation(1000 + randomGap, 1000);
        if (o instanceof JumpingPalladium)

            o.setLocation(1000 + randomGap, 700 + randomHeight);
    }

}


