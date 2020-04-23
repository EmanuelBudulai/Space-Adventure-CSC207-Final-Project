package com.example.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * The game view.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Height of navigation bar at bottom of screen.
     */
    private static int navBarHeight;
    /**
     * Screen width.
     */
    private static int screenWidth;
    /**
     * Screen height.
     */
    private static int screenHeight;
    /**
     * Whether the screen is pressed
     */
    private boolean isPressed;
    /**
     * x coordinate of last screen touch
     */
    private float touchX;
    /**
     * y coordinate of last screen touch
     */
    private float touchY;
    /**
     * The level on screen
     */
    Level level;
    /**
     * The thread responsible for continuously updating the gameView
     */
    private MainThread thread;
    /**
     * The observer object that notifies GameActivity when the game is completed.
     */
    private GameActivity.GameViewListener listener;

    /**
     * Create a new gameView in the context environment.
     *
     * @param context the environment.
     */
    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * Initialize the view.
     * @param context The context in which this view is constructed upon.
     */
    public void init(Context context) {

        getHolder().addCallback(this);
        setFocusable(true);

        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels - navBarHeight;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        killCurrentThread();
    }

    public void update() {
        if (!level.isComplete()) {
            if (level.isPaused()){
                level.endPause();
            }
            level.update();

            if (isPressed) {
                level.onTouch(
                        (int) (touchX / Level.getXScaleFactor()),
                        (int) (touchY / Level.getYScaleFactor()));
            }
        }else{
            listener.onCompleteLevel();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            isPressed = false;
        } else {
            isPressed = true;
            touchX = event.getX();
            touchY = event.getY();
        }

        super.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
    /**
     * draws the level on the screen
     *
     * @param canvas the canvas being drawn on
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawBitmap(level.getBackground(),0,0, null);
            level.draw(canvas);
        }
    }
    /**
     * attempts to join this thread so the level can be paused
     */
    public void killCurrentThread() {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                level.startPause();
                //thread.join();

            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            } finally {
                System.out.println("Attempting to kill game loop.");
            }
            retry = false;
        }
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public void setListener(GameActivity.GameViewListener listener){this.listener = listener;}
    /**
     * starts up a new thread for the level to run on
     */
    public void startNewThread() {
        thread = new MainThread(this);
        thread.setRunning(true);
        thread.start();
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }


}
