package com.example.game;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class Icons {

    public static Bitmap asteroid;
    public static Bitmap alienIcon;
    public static Bitmap palladium;
    public static List<Bitmap> astronautAvatars;
    public static List<Bitmap> rocketSprites;
    public static List<Bitmap> background;

    public static void loadIcons(Resources res){
        palladium = BitmapFactory.decodeResource(res, R.drawable.coin);
        alienIcon = BitmapFactory.decodeResource(res, R.drawable.skull_in_a_ufo_spacecraft);
        asteroid = BitmapFactory.decodeResource(res, R.drawable.asteroid);

        //=================AVATARS==================================//
        astronautAvatars = new ArrayList<>();
        astronautAvatars.add(BitmapFactory.decodeResource(res, R.drawable.astronaut));
        astronautAvatars.add(BitmapFactory.decodeResource(res, R.drawable.astronaut1));
        astronautAvatars.add(BitmapFactory.decodeResource(res, R.drawable.astronaut2));
        astronautAvatars.add(BitmapFactory.decodeResource(res, R.drawable.astronaut3));

        //=================ROCKETS==================================//
        rocketSprites = new ArrayList<>();
        rocketSprites.add(BitmapFactory.decodeResource(res, R.drawable.rocket));
        rocketSprites.add(BitmapFactory.decodeResource(res, R.drawable.rocket1));
        rocketSprites.add(BitmapFactory.decodeResource(res, R.drawable.rocket2));
        rocketSprites.add(BitmapFactory.decodeResource(res, R.drawable.rocket3));


        //=================BACKGROUNDS==================================//
        int h = GameView.getScreenHeight();
        int w = GameView.getScreenWidth();

        Bitmap b0 = BitmapFactory.decodeResource(res, R.drawable.background);
        Bitmap b1 = BitmapFactory.decodeResource(res, R.drawable.background1);
        Bitmap b2 = BitmapFactory.decodeResource(res, R.drawable.background2);
        Bitmap b3 = BitmapFactory.decodeResource(res, R.drawable.background3);

        background = new ArrayList<>();
        background.add(Bitmap.createScaledBitmap(b0, w,h, true));
        background.add(Bitmap.createScaledBitmap(b1, w,h, true));
        background.add(Bitmap.createScaledBitmap(b2, w,h, true));
        background.add(Bitmap.createScaledBitmap(b3, w,h, true));
    }

}
