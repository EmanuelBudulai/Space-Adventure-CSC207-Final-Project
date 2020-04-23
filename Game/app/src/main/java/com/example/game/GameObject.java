package com.example.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


abstract class GameObject {
    /**
     * Collision box for the object.
     */
    private Rect hitBox;
    /**
     * The appearance of the object.
     */
    private Bitmap icon;
    /**
     * The (unscaled) horizontal location of the object.
     */
    private int x;
    /**
     * The (unscaled) vertical location of the object.
     */
    private int y;

    /**
     * Constructor for GameObject.
     *
     * @param x      the (unscaled) horizontal location of the object (left).
     * @param y      the (unscaled) vertical location of the object (top).
     * @param width  the (unscaled) width of the object.
     * @param height the (unscaled) height of the object.
     * @param icon   the appearance of the object
     */
    GameObject(int x, int y, int width, int height, Bitmap icon) {
        this.x = x;
        this.y = y;
        this.setHitBox(new Rect(x, y, x + width, y + height));
        this.icon = Bitmap.createScaledBitmap(icon,
                Level.scaleHorizontally(width),
                Level.scaleVertically(height), true);
    }

    /**
     * update this game object
     */
    abstract void update();

    /**
     * Draws this object on a canvas.
     *
     * @param c The canvas to draw this object on.
     */
    void draw(Canvas c) {
        c.drawBitmap(this.icon,
                Level.scaleHorizontally(x),
                Level.scaleVertically(y), null);
    }

    /**
     * Changes the location of the object.
     *
     * @param x (unscaled) horizontal location to move this object to.
     * @param y (unscaled) vertical location to move this object to.
     */
    void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitBox.offsetTo(x, y);
    }

    Rect getHitBox() {
        return hitBox;
    }

    int getXCoordinate() {
        return this.x;
    }

    int getYCoordinate() {
        return this.y;
    }

    int getWidth() {
        return this.getHitBox().width();
    }

    int getHeight() {
        return this.getHitBox().height();
    }

    private void setHitBox(Rect hitBox) {
        this.hitBox = hitBox;
    }
    /**
     * Checks if this GameObject collides with another GameObject.
     *
     * @param o The GameObject that might collide with this GameObject.
     * @return Whether the this object collides with the object in the parameter.
     */
    boolean collide(GameObject o) {
        return Rect.intersects(this.getHitBox(), o.getHitBox());
    }

}
