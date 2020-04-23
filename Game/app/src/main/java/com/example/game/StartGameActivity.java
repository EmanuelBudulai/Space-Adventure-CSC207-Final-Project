package com.example.game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class StartGameActivity extends AppCompatActivity {
    private int avatar;
    private int background;
    private String username;
    private ImageButton[] avatarButtons;
    private ImageButton[] backgroundButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar ac = getSupportActionBar();
        if(ac != null){
            ac.hide();
        }
        setContentView(R.layout.activity_start_game);
        username = this.getIntent().getStringExtra("USERNAME_EXTRA");
        avatar = 0;
        background = 0;

        avatarButtons = new ImageButton[4];
        avatarButtons[0] = findViewById(R.id.avatarBtn0);
        avatarButtons[1] = findViewById(R.id.avatarBtn1);
        avatarButtons[2] = findViewById(R.id.avatarBtn2);
        avatarButtons[3] = findViewById(R.id.avatarBtn3);

        backgroundButtons = new ImageButton[4];
        backgroundButtons[0] = findViewById(R.id.backgroundBtn0);
        backgroundButtons[1] = findViewById(R.id.backgroundBtn1);
        backgroundButtons[2] = findViewById(R.id.backgroundBtn2);
        backgroundButtons[3] = findViewById(R.id.backgroundBtn3);

    }

    public void setAvatar(View view){

        for(int i = 0; i<=3; i++){
            avatarButtons[i].getBackground().setAlpha(150);
        }
        view.getBackground().setAlpha(255);

        if(view.getId() == R.id.avatarBtn0){
            avatar = 0;
        } else if(view.getId() == R.id.avatarBtn1){
            avatar = 1;
        }else if(view.getId() == R.id.avatarBtn2){
            avatar = 2;
        }else if(view.getId() == R.id.avatarBtn3){
            avatar = 3;
        }
    }

    public void setBackground(View view){
        for(int i = 0; i<=3; i++){
            backgroundButtons[i].getBackground().setAlpha(150);
        }
        view.getBackground().setAlpha(255);


        if(view.getId() == R.id.backgroundBtn0){
            background = 0;
        }
        if(view.getId() == R.id.backgroundBtn1){
            background = 1;
        }
        if(view.getId() == R.id.backgroundBtn2){
            background = 2;
        }
        if(view.getId() == R.id.backgroundBtn3){
            background = 3;
        }
    }

    public void startGame(View view){
        Intent startGameIntent = new Intent(this, GameActivity.class);
        startGameIntent.putExtra("USERNAME_EXTRA", username);
        startGameIntent.putExtra("AVATAR_EXTRA", avatar);
        startGameIntent.putExtra("BACKGROUND_EXTRA", background);
        startGameIntent.putExtra("SELECTED_LEVEL", "RocketLevel");
        startActivity(startGameIntent);
        finish();
    }

}
