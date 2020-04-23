package com.example.game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewAccountActivity extends AppCompatActivity {
    /**
     * the AccountManager tool used to interact with database
     */
    AccountManager accountManager;
    /**
     * the message at the bottom of the screen
     */
    TextView newAccountMsgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        accountManager = new AccountManager(this);

        newAccountMsgText = findViewById(R.id.newAccountMsgText);
    }
    /**
     * checks inputs for validity and creates an account for valid inputs
     *
     * @param view the view that called this method
     */
    public void createNewAccount(View view) {
        EditText usernameText = findViewById(R.id.newAccountUsernameText);
        EditText passwordText = findViewById(R.id.newAccountPasswordText);
        EditText rPasswordText = findViewById(R.id.newAccountRPasswordText);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String rpassword = rPasswordText.getText().toString();

        newAccountMsgText.setVisibility(View.INVISIBLE);

        if (username.isEmpty() || password.isEmpty() || rpassword.isEmpty()) {
            newAccountMsgText.setTextColor(Color.RED);
            newAccountMsgText.setText(R.string.invalid_username_password);
            newAccountMsgText.setVisibility(View.VISIBLE);
        } else {

            if (password.equals(rpassword)) {
                if (!accountManager.accountTaken(username)) {
                    boolean isInserted = accountManager.addAccount(username, password);
                    if (isInserted) {
                        newAccountMsgText.setTextColor(Color.GREEN);
                        newAccountMsgText.setText(R.string.account_succesfully_created);
                        newAccountMsgText.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(this, MenuActivity.class);
                        intent.putExtra("USERNAME_EXTRA", username);
                        startActivity(intent);
                        finish();
                    } else {
                        usernameText.setText("");
                        passwordText.setText("");
                        rPasswordText.setText("");
                        newAccountMsgText.setTextColor(Color.RED);
                        newAccountMsgText.setText(R.string.account_creation_unsuccessful);
                        newAccountMsgText.setVisibility(View.VISIBLE);
                    }
                } else {
                    usernameText.setText("");
                    newAccountMsgText.setTextColor(Color.RED);
                    newAccountMsgText.setText(R.string.username_taken);
                    newAccountMsgText.setVisibility(View.VISIBLE);
                }
            } else {
                passwordText.setText("");
                rPasswordText.setText("");
                newAccountMsgText.setTextColor(Color.RED);
                newAccountMsgText.setText(R.string.passwords_no_match);
                newAccountMsgText.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * returns to the MainActivity sign in screen
     */
    public void backToMainActivity(View view) {
        newAccountMsgText.setVisibility(View.INVISIBLE);
        startActivity(new Intent(this, MainActivity.class));
    }

}
