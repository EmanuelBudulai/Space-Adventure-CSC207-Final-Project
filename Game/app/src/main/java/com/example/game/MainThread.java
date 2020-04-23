package com.example.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/* This class is modified from FishTank.MainThread*/

/**
 * the thread that the game runs on
 */
public class MainThread extends Thread {


    /**
     * Where the levels are drawn.
     */
    private GameView gameView;
    /**
     * The canvas container.
     */
    private final SurfaceHolder surfaceHolder;
    /**
     * Whether the thread is running.
     */
    private boolean running;
    /**
     * The canvas on which to draw the current level.
     */
    private Canvas canvas;

    private int targetFPS = 60;
    private double averageFPS;

    /**
     * Construct the thread.
     *
     * @param gameView          where the level is drawn.
     */
    public MainThread(GameView gameView) {
        this.gameView = gameView;
        this.surfaceHolder = gameView.getHolder();

    }
    /**
     * constantly causes the gameView to update and draw to screen while running is true
     */
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetFPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    gameView.update();
                    gameView.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (canvas != null)            {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if(waitTime > 0) {
                    Thread.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS)        {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }

    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}

