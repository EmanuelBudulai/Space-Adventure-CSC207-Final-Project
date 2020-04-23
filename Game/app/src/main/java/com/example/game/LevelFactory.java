package com.example.game;


import android.graphics.Color;
/**
 * A Factory that produces fully constructed levels upon request
 */
public class LevelFactory {
    /**
     * Builds level using its nested Builder
     */
    public static Level buildLevel(String level, int avatar, int background) {
        switch (level) {
            case "JumpLevel":

                return new JumpLevel.Builder(avatar)
                        .buildLives(3)
                        .buildEndTime(30)
                        .buildObstacle()
                        .buildPalladium()
                        .buildBackground(background)
                        .buildGround()
                        .buildPaint(Color.GRAY)
                        .buildLevel();

            case "RocketLevel":

                return new RocketLevel
                        .Builder()
                        .buildFallingObjects()
                        .buildRocket(avatar)
                        .buildBackground(background)
                        .buildLevel();

            case "MashLevel":

                return new MashLevel.Builder()
                        .buildButtons(avatar)
                        .buildBackground(background)
                        .buildLevel();
        }

        return null;
    }
}
