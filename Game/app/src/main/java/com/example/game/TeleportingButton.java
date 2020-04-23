package com.example.game;

import android.graphics.Bitmap;

class TeleportingButton extends GameObject {

    /**
     * Creates a new Teleporting Button object.
     *
     * @param x   the horizontal coordinate of the Astronaut.
     * @param y   the vertical coordinate of the Astronaut.
     * @param width the width of the Astronaut
     * @param height the height of the Astronaut
     * @param icon the icon for the Astronaut
     */
    TeleportingButton(int x, int y, int width, int height, Bitmap icon) {
        super(x,y,width,height,icon);
    }

    @Override
    void update() {
        // intentionally left empty. Teleporting button takes no action upon update command.
    }
}
