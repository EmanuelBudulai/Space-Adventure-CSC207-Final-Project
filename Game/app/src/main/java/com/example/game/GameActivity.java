package com.example.game;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    /**
     * The currently active level on screen
     */
    Level level;
    /**
     * the updating view that displays the level
     */
    private GameView gameView;
    /**
     * the pause button
     */
    ImageButton pauseButton;
    /**
     * intent to go the the levelmenu
     */
    Intent levelMenuIntent;
    /**
     * intent to go to the end of level screen
     */
    Intent endGameIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Get rid of the action bar if there exists one.
        ActionBar ac = getSupportActionBar();
        if(ac != null){
            ac.hide();
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);


        levelMenuIntent = new Intent(this, LevelMenuActivity.class);
        endGameIntent = new Intent(this, EndGameActivity.class);

        Icons.loadIcons(getResources());

        int background  = this.getIntent().getIntExtra("BACKGROUND_EXTRA", 0);
        int avatar  = this.getIntent().getIntExtra("AVATAR_EXTRA", 0);

        String selectedLevel = getIntent().getStringExtra("SELECTED_LEVEL");
        if (selectedLevel != null) {
            level = LevelFactory.buildLevel(selectedLevel, avatar, background);
        }else{
            System.out.println("ERROR");
            //error
        }

        gameView = findViewById(R.id.gameView);
        gameView.setLevel(level);
        gameView.setListener(new GameViewListener());

        pauseButton = findViewById(R.id.pauseLevelBtn);


    }

    /**
     * End the level by calling the end game screen. Pass in all the necessary statistics.
     */
    public void completeLevel() {
        gameView.killCurrentThread();

        endGameIntent.putExtra("PASSED_EXTRA", level.getLevelPassStatus());
        endGameIntent.putExtra("PALLADIUM_EXTRA", level.getPalladium());
        endGameIntent.putExtra("SCORE_EXTRA", level.calculateScore());
        endGameIntent.putExtra("SELECTED_LEVEL", level.name());
        endGameIntent.putExtra("AVATAR_EXTRA", this.getIntent()
                .getIntExtra("AVATAR_EXTRA", 0));
        endGameIntent.putExtra("BACKGROUND_EXTRA", this.getIntent()
                .getIntExtra("BACKGROUND_EXTRA", 0));
        endGameIntent.putExtra("USERNAME_EXTRA",
                this.getIntent().getStringExtra("USERNAME_EXTRA"));


        SystemClock.sleep(500);
        startActivity(endGameIntent);
        this.finish();
    }

    /**
     * Pauses the level and switches to paused level menu
     *
     * @param view the view that called this method
     */

    public void pauseLevel(View view) {
        super.onPause();


        levelMenuIntent.putExtra("USERNAME_EXTRA",
                this.getIntent().getStringExtra("USERNAME_EXTRA"));

        gameView.killCurrentThread();
        startActivity(levelMenuIntent);
    }

    /**
     * Restart the level.
     * @param view the view that contains the restart button.
     */
    public void restartLevel(View view) {
        gameView.killCurrentThread();
        //level = LevelFactory.buildLevel(getIntent().getStringExtra("SELECTED_LEVEL"));
        //or level.reset(); this is better
        //gameView.setLevel(level);
        //gameView.startNewThread();
        this.recreate();
    }


    @Override
    protected void onResume() {
        super.onResume();
        gameView.startNewThread();

    }

    class GameViewListener implements CompleteLevelListener{
        @Override
        public void onCompleteLevel() {
            completeLevel();
        }
    }

//============MAY NEED TO USE THESE=================================

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
