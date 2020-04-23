package com.example.game;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EndGameActivity extends AppCompatActivity {

    private boolean passed;
    private int score;
    private int palladium;
    private int avatar;
    private int background;
    private String username;
    private String currLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.hide();
        }
        setContentView(R.layout.activity_end_game);

        username = this.getIntent().getStringExtra("USERNAME_EXTRA");
        passed = this.getIntent().getBooleanExtra("PASSED_EXTRA", false);

        if (passed) {
            score = this.getIntent().getIntExtra("SCORE_EXTRA", 0);
            palladium = this.getIntent().getIntExtra("PALLADIUM_EXTRA", 0);

        } else {
            score = 0;
            palladium = 0;
        }

        //Initialize all the intents.
        currLevel = this.getIntent().getStringExtra("SELECTED_LEVEL");
        avatar = this.getIntent().getIntExtra("AVATAR_EXTRA", 0);
        background = this.getIntent().getIntExtra("BACKGROUND_EXTRA", 0);

        passFailDisplay(passed);
        scoreDisplay(score);
        palladiumDisplay(palladium);

        //if the user passed the last level, hide restart and next level button.
        if ("JumpLevel".equals(currLevel)){
            nextLevelDisplay(false);
            restartDisplay(false);
            if (passed){
                restartDisplay(true);
            }
        } else {
            nextLevelDisplay(passed);
            restartDisplay(false);
        }



    }

    /**
     * Make the next level button invisible if the user did not pass the level.
     *
     * @param passed boolean indicating whether the user passed or failed the level.
     */
    private void nextLevelDisplay(boolean passed){
        Button b = findViewById(R.id.nextLevelBtn);
        if (passed){
            b.setVisibility(View.VISIBLE);
        } else {
            b.setVisibility(View.INVISIBLE);
        }
    }


    private void restartDisplay(boolean end){
        Button b = findViewById(R.id.restartLevelBtn);
        if (end){
            b.setVisibility(View.INVISIBLE);
        } else{
            b.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Tells the game what the next level is.
     * @param selectedLevel the current level.
     * @return the next level.
     */
    private String getNextLevel(String selectedLevel){
        if("RocketLevel".equals(selectedLevel)){
            return "MashLevel";
        }else{
            return "JumpLevel";
        }
    }

    /**
     * Display "Victory Achieved" if player passed level.
     * Display "You Died" if player failed level.
     *
     * @param passed boolean indicating whether the user passed or failed the level.
     */
    private void passFailDisplay(boolean passed){
        ImageView img = findViewById(R.id.endGameImg);
        if (passed){
            img.setImageResource(R.drawable.level_win);
        } else {
            img.setImageResource(R.drawable.level_end);
        }
    }

    /**
     * Display the player's score in this level.
     * @param score Player's score in this level, if they failed the level, the score is 0.
     */
    private void scoreDisplay(int score){
        TextView txt = findViewById(R.id.scoreTxt);
        String s = "Score: " + score;
        txt.setText(s);
    }

    /**
     * Display the amount of palladium collected in this level.
     * @param palladium  amount of palladium collected, if level was failed, this value is 0.
     */
    private void palladiumDisplay(int palladium){
        TextView txt = findViewById(R.id.palladiumCollectedTxt);
        String s = "palladium Collected: " + palladium;
        txt.setText(s);
    }

    /**
     * Go back to the main menu. If the user passed the level, save the game.
     * @param view the view which this button was clicked.
     */
    public void returnToMainMenu(View view){
        Intent mainMenuIntent = new Intent(this, MenuActivity.class);
        mainMenuIntent.putExtra("USERNAME_EXTRA", username);
        if (passed) {
            if (!"JumpLevel".equals(currLevel)){
                this.saveGame();
            } else {
                AccountManager ac = new AccountManager(this);
                ac.updateHighScore(username,score);
                ac.updatePalladium(username,palladium);
                if (passed) {
                    ac.removeSavedGame(username);
                }
            }
        }
        startActivity(mainMenuIntent);
        finish();
    }

    /**
     * Restart this level. The previous level(s) should've been saved already.
     *
     * @param view the view which this button was clicked.
     */
    public void restartLevel(View view){
        Intent restartLevelIntent = new Intent(this, GameActivity.class);
        restartLevelIntent.putExtra("SELECTED_LEVEL", currLevel);
        restartLevelIntent.putExtra("AVATAR_EXTRA", avatar);
        restartLevelIntent.putExtra("BACKGROUND_EXTRA", background);
        restartLevelIntent.putExtra("USERNAME_EXTRA", username);
        startActivity(restartLevelIntent);
        finish();
    }

    /**
     * Go to the next level. Record score and palladium gained in this level. The user can only
     * go to next level if they passed the level, otherwise this button should be invisible.
     *
     * @param view the view which this button was clicked.
     */
    public void nextLevel(View view){
        this.saveGame();
        Intent nextLevelIntent = new Intent(this, GameActivity.class);
        String nextLevel = getNextLevel(currLevel);
        nextLevelIntent.putExtra("SELECTED_LEVEL", nextLevel);
        nextLevelIntent.putExtra("AVATAR_EXTRA", avatar);
        nextLevelIntent.putExtra("BACKGROUND_EXTRA", background);
        nextLevelIntent.putExtra("USERNAME_EXTRA", username);
        startActivity(nextLevelIntent);
        finish();
    }

    /**
     * Saved the current session.
     */
    private void saveGame(){
        AccountManager ac = new AccountManager(this);
        String user = this.username;
        int score = this.score + ac.retrieveSavedGameScore(this.username);
        int palladium = this.palladium + ac.retrieveSavedGamePalladium(this.username);
        String level = this.currLevel;
        int avatar = this.avatar;
        int background = this.background;

        ac.addSavedGame(user, score, palladium, level, avatar, background);
    }

    /**
     * Remove the games saved by this user.
     */
    private void deleteSavedGames(){
        AccountManager ac = new AccountManager(this);
        ac.removeSavedGame(this.username);
    }
}
