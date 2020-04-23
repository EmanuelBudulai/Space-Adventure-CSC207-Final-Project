package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    /**
     * the AccountManager tool used to interact with the database
     */
    private AccountManager accountManager;
    /**
     * the message displayed at the bottom of the screen
     */
    private TextView signInMsgText;
    /**
     * the text box that takes a username
     */
    private EditText usernameText;
    /**
     * the text box that takes a password
     */
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountManager = new AccountManager(this);

        signInMsgText = findViewById(R.id.signInMsgText);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);

        signInMsgText.setVisibility(View.INVISIBLE);

    }

    /**
     * Checks validity of credentials and signs in valid users
     *
     * @param view the button view that called this method.
     */
    public void signIn(View view) {
        EditText usernameText = findViewById(R.id.usernameText);
        EditText passwordText = findViewById(R.id.passwordText);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (accountManager.credentialsMatch(username, password)) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("USERNAME_EXTRA", username);
            startActivity(intent);
        } else {
            passwordText.setText("");
            signInMsgText.setText(R.string.invalid_username_password);
            signInMsgText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * switches to CreateNewAccountActivity
     *
     * @param view the button view that called this method .
     */
    public void newAccount(View view) {
        signInMsgText.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, CreateNewAccountActivity.class);
        startActivity(intent);
    }
}
