package com.example.game;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

public class LevelMenuActivity extends AppCompatActivity {
    /**
     * the intent to go to the main menu
     */
    Intent mainMenuIntent;
    /**
     * whether or not this level is completed
     */
    boolean levelCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar ac = getSupportActionBar();
        if(ac != null){
            ac.hide();
        }
        setContentView(R.layout.activity_level_menu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.5), (int) (dm.heightPixels * 0.5));

        mainMenuIntent = new Intent(this, MenuActivity.class);
        mainMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainMenuIntent.putExtra("USERNAME_EXTRA",
                this.getIntent().getStringExtra("USERNAME_EXTRA"));
    }

    /**
     * resumes the level
     *
     * @param view the view that called this method
     */
    public void resumeLevel(View view) {
        finish();
    }


    /**
     * Save the user's progress and return to the main menu.
     *
     * @param view the view that called this method
     */
    public void returnToMainMenu(View view) {
        startActivity(mainMenuIntent);
        finish();

    }




}
