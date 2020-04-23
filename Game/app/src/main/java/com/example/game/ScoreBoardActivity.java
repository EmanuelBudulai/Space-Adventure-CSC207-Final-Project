package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ScoreBoardActivity extends AppCompatActivity {
    /**
     * The accountmanager from which the scoreboard will be generated.
     */
    AccountManager accountManager;

    /**
     * The user who is looking at the scoreboard.
     */
    String username;

    /**
     * The user with the best highscore.
     */
    private TextView user1;

    /**
     * The user with the second best highscore.
     */
    private TextView user2;

    /**
     * The user with the third best highscore.
     */
    private TextView user3;

    /**
     * The user with the fourth best highscore.
     */
    private TextView user4;

    /**
     * The user with the fifth best highscore.
     */
    private TextView user5;

    /**
     * The best highscore.
     */
    private TextView score1;

    /**
     * The second best highscore.
     */
    private TextView score2;

    /**
     * The third best highscore.
     */
    private TextView score3;

    /**
     * The fourth best highscore.
     */
    private TextView score4;

    /**
     * The fifth best highscore.
     */
    private TextView score5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        username = this.getIntent().getStringExtra("USERNAME_EXTRA");

        accountManager = new AccountManager(this);

        List<List<String>> scoreboard = accountManager.getScoreboard();

        TextView user1 = findViewById(R.id.textView13);
        TextView user2 = findViewById(R.id.textView14);
        TextView user3 = findViewById(R.id.textView15);
        TextView user4 = findViewById(R.id.textView16);
        TextView user5 = findViewById(R.id.textView17);
        TextView score1 = findViewById(R.id.textView18);
        TextView score2 = findViewById(R.id.textView19);
        TextView score3 = findViewById(R.id.textView20);
        TextView score4 = findViewById(R.id.textView21);
        TextView score5 = findViewById(R.id.textView22);

        int size = scoreboard.size();

        for(int i = 0; i < 5 - size; i++) {
            List<String> sublist= new ArrayList<>();
            sublist.add("");
            sublist.add("");
            scoreboard.add(sublist);
        }

        user1.setText(scoreboard.get(0).get(0));
        user2.setText(scoreboard.get(1).get(0));
        user3.setText(scoreboard.get(2).get(0));
        user4.setText(scoreboard.get(3).get(0));
        user5.setText(scoreboard.get(4).get(0));
        score1.setText(scoreboard.get(0).get(1));
        score2.setText(scoreboard.get(1).get(1));
        score3.setText(scoreboard.get(2).get(1));
        score4.setText(scoreboard.get(3).get(1));
        score5.setText(scoreboard.get(4).get(1));
    }

    /**
     * returns to the MainActivity sign in screen
     */
    public void backToMenuActivity(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("USERNAME_EXTRA", username);
        startActivity(intent);
    }
}
