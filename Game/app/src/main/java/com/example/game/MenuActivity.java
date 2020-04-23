package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private String username;
    private List<String> savedGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar ac = getSupportActionBar();
        if(ac != null){
            ac.hide();
        }
        setContentView(R.layout.activity_menu);

        AccountManager acc = new AccountManager(this);

        username = this.getIntent().getStringExtra("USERNAME_EXTRA");
        if (username != null){
            savedGame = acc.retrieveSavedGame(username);
        }

        resumeButtonDisplay(Integer.parseInt(savedGame.get(4)) == -1);

    }


    /**
     * Hide the resume button if the user didn't have any previously saved game.
     * @param b boolean indicating whether the user saved a game or not.
     */
    private void resumeButtonDisplay(boolean b){
        Button resumeButton = findViewById(R.id.resumeBtn);
        if (b){
            resumeButton.setVisibility(View.INVISIBLE);
        } else {
            resumeButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Start a new game and allow the user to choose their customization.
     * @param view the view in which this button was clicked.
     */
    public void customizationSelection(View view){
        Intent startGameIntent = new Intent(this, StartGameActivity.class);
        startGameIntent.putExtra("USERNAME_EXTRA", username);
        AccountManager ac = new AccountManager(this);
        //ac.removeSavedGame(username);
        startActivity(startGameIntent);
        finish();
    }


    /**
     * Resume the game that was saved in the previous session.
     * @param view the view in which this button was clicked.
     */
    public void resumePreviousSession(View view){
        Intent resumeGameIntent = new Intent(this, GameActivity.class);
        resumeGameIntent.putExtra("USERNAME_EXTRA", username);
        resumeGameIntent.putExtra("SELECTED_LEVEL", resumeAtNextLevel(savedGame.get(2)));
        resumeGameIntent.putExtra("AVATAR_EXTRA", Integer.parseInt(savedGame.get(3)));
        resumeGameIntent.putExtra("BACKGROUND_EXTRA", Integer.parseInt(savedGame.get(4)));
        startActivity(resumeGameIntent);
        finish();
    }

    public void goToScoreboard(View view) {
        Intent intent = new Intent(this, ScoreBoardActivity.class);
        intent.putExtra("USERNAME_EXTRA", username);
        startActivity(intent);
    }

    private String resumeAtNextLevel(String level){
        if ("RocketLevel".equals(level)){
            return "MashLevel";
        } else{
            return "JumpLevel";
        }
    }
}
