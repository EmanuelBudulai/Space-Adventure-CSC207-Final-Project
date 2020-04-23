package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * A collection of accounts and the operations that modify and extract information from it.
 */
class AccountManager extends SQLiteOpenHelper {

    private static final String NOTHING = "NONE";
    private static final String DATABASE_NAME = "AccountManager.db";
    private static final String TABLE_NAME = "account_table";
    private static final String COL_0 = "ID";
    private static final String COL_1 = "USERNAME";
    private static final String COL_2 = "PASSWORD";
    private static final String COL_3 = "PALLADIUM";
    private static final String COL_4 = "HIGHSCORE";
    private static final String COL_5 = "SCORE_SAVED";
    private static final String COL_6 = "PALLADIUM_SAVED";
    private static final String COL_7 = "SAVED_GAME_LEVEL";
    private static final String COL_8 = "SAVED_GAME_PLAYER_CUSTOM";
    private static final String COL_9 = "SAVED_GAME_BG_CUSTOM";

    /**
     * Creates an SQL database, with the columns as such:
     * Column 0 - unique account id.
     * Column 1 - the account username.
     * Column 2 - the account password.
     * Column 3 - the account's total palladium.
     * Column 4 - the account's highscore.
     * Column 5 - the account's saved game's score.
     * Column 6 - the account's saved game's palladium.
     * Column 7 - the account's saved game's level. Must be "ROCKET", "JUMP", "MASHABLE", or "NONE".
     * Column 8 - the account's saved game's player customization setting.
     * Column 9 - the account's saved game's background customization setting.
     */
    AccountManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override

    //TODO Data types should not just be TEXT.
    //TODO "Schema".
    //TODO RUI.
    //TODO "Anonomyze".
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_1 + " TEXT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " +
                COL_5 + " TEXT, " + COL_6 + " TEXT, " + COL_7 + " TEXT, " + COL_8 + " TEXT, " +
                COL_9 + " TEXT)");
    }

    //TODO Look up proper process for upgrading the database.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /**
     * Checks to see if the input username and password match that of an account in the database.
     *
     * @param inputUsername An input Username.
     * @param inputPassword An input Password.
     * @return Returns true if the given Username and Password match an account.
     */
    //TODO Look into encryption.
    boolean credentialsMatch(String inputUsername, String inputPassword) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { COL_0 };
        String selection = COL_1 + "=?" + " and " + COL_2 + "=?";
        String[] selectionArgs = { inputUsername, inputPassword };
        Cursor queriedDB = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        int count = queriedDB.getCount();

        queriedDB.close();
        db.close();

        return count > 0;
    }

    /**
     * Checks to see if the entered username is already taken in the list of accounts.
     *
     * @param newUsername The input username.
     * @return Returns true if the given username is already taken.
     */
    boolean accountTaken(String newUsername) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { COL_0 };
        String selection = COL_1 + "=?";
        String[] selectionArgs = { newUsername };
        Cursor queriedDB = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        int count = queriedDB.getCount();

        queriedDB.close();
        db.close();

        return count > 0;
    }

    /**
     * Adds an account, with default value of 0 for Palladium, Highscore, savedGameScore,
     * savedGamePalladium, "NONE" for saved level, and -1 for the player and background
     * customization settings.
     *
     * @param newUsername A new account's username.
     * @param newPassword A new account's password.
     * @return Returns true if the account could be added
     */
    boolean addAccount(String newUsername, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, newUsername);
        contentValues.put(COL_2, newPassword);
        contentValues.put(COL_3, "0");
        contentValues.put(COL_4, "0");
        contentValues.put(COL_5, "0");
        contentValues.put(COL_6, "0");
        contentValues.put(COL_7, NOTHING);
        contentValues.put(COL_8, "-1");
        contentValues.put(COL_9, "-1");

        long result = db.insert(TABLE_NAME, null, contentValues);

        db.close();
        return result != -1;
    }

    /**
     * Updates all of a given user's information regarding their highscore, palladium, their saved
     * level, their saved level's palladium, their saved level's player customization, and their
     * saved level's background customization.
     *
     * @param user           The user who's account needs to be updated.
     * @param palladium      The new palladium count for the user.
     * @param highscore      The new highscore for the user.
     * @param savedScore     The new score for the saved game for the user.
     * @param savedPalladium The new saved palladium count for saved game for the user.
     * @param savedLevel     The level of the saved game for the user.
     * @param pCustomization The saved level's player customization.
     * @param bCustomization The saved level's background customization.
     * @return Returns true if the user's account could be updated.
     */
    //TODO Account class model.
    //TODO Repository pattern.
    //TODO Database Transactions.
    private boolean updateAccount(String user, int palladium, int highscore, int savedScore,
                                  int savedPalladium, String savedLevel, int pCustomization,
                                  int bCustomization) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = { COL_0, COL_2 };
        String selection = COL_1 + "=?";
        String[] selectionArgs = { user };
        Cursor queriedDB = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null,null);

        if (queriedDB.getCount()>0){
            queriedDB.moveToFirst();
        }
        String identity = queriedDB.getString(0);
        String password = queriedDB.getString(1);

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_0, identity);
        contentValues.put(COL_1, user);
        contentValues.put(COL_2, password);
        contentValues.put(COL_3, Integer.toString(palladium));
        contentValues.put(COL_4, Integer.toString(highscore));
        contentValues.put(COL_5, Integer.toString(savedScore));
        contentValues.put(COL_6, Integer.toString(savedPalladium));
        contentValues.put(COL_7, savedLevel);
        contentValues.put(COL_8, Integer.toString(pCustomization));
        contentValues.put(COL_9, Integer.toString(bCustomization));

        String whereClause = "ID = ?";
        String[] whereArgs = { identity };

        db.update(TABLE_NAME, contentValues, whereClause, whereArgs);

        queriedDB.close();
        db.close();
        return true;
    }

    /**
     * Adds a saved game to the user's account. A saved game consists of a score, palladium,
     * the level that the game was saved on, and the player and background customization.
     *
     * @param user           The user who saved their game.
     * @param score          The score that the user accumulated up to and including the last
     *                       completed level.
     * @param palladium      The palladium that the user accumulated up to and including the last
     *                       completed level.
     * @param level          The level that the user saved at.
     * @param pCustomization The customization setting for the player character that the user had
     *                       when they saved their game.
     * @param bCustomization The customization setting for the background color that the user had
     *                       when they saved their game.
     */
    void addSavedGame(String user, int score, int palladium, String level,
                              int pCustomization, int bCustomization) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = { COL_3, COL_4 };
        String selection = COL_1 + "=?";
        String[] selectionArgs = { user };
        Cursor queriedDB = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null,null);

        queriedDB.moveToFirst();
        int totalPalladium = Integer.parseInt(queriedDB.getString(0));
        int highscore = Integer.parseInt(queriedDB.getString(1));

        this.updateAccount(user, totalPalladium, highscore, score, palladium, level, pCustomization,
                bCustomization);
        queriedDB.close();
        db.close();
    }

    /**
     * Retrieves a saved game from the user's account.
     *
     * @param user The user who's saved game you want to retrieve.
     * @return A list containing the information about the saved game with the folloing information
     * at the respective indices:
     *
     * Index 0 - The score of the saved game.
     * Index 1 - The palladium accumulated on the saved game.
     * Index 2 - The level that the game was saved at.
     * Index 3 - The player customization setting.
     * Index 4 - The background customization setting.
     */
    List<String> retrieveSavedGame(String user) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { COL_5, COL_6, COL_7, COL_8, COL_9 };
        String selection = COL_1 + "=?";
        String[] selectionArgs = { user };
        Cursor queriedDB = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null,null);

        List<String> result = new ArrayList<>();

        queriedDB.moveToFirst();

        for (int i = 0; i < 5; i++) {
            result.add(queriedDB.getString(i));
        }

        queriedDB.close();
        db.close();
        return result;
    }

    /**
     * Retrieves the palladium count from the saved game of a particular user.
     *
     * @param user The user who's palladium count you would like ot retreive.
     * @return The palladium count as an int from the saved game.
     */
    int retrieveSavedGamePalladium(String user) {
        return Integer.parseInt(retrieveSavedGame(user).get(0));
    }

    /**
     * Retrieves the score from the saved game of a particular user.
     *
     * @param user The user who's palladium count you would like ot retreive.
     * @return The score as an int from the saved game.
     */
    int retrieveSavedGameScore(String user) {
        return Integer.parseInt(retrieveSavedGame(user).get(1));
    }

    /**
     * Removes the saved game from the user by setting the columns to their defaults:
     * Saved level will be set to 0
     * Saved score will be set to 0
     * Saved palladium will be set to 0
     * Saved player customization will be set to -1
     * Saved background customization will be set to -1
     *
     * @param user The user who's saved game will be removed.
     */
    void removeSavedGame(String user) {
        addSavedGame(user, 0, 0, NOTHING, -1, -1);
    }

    /**
     * Returns whether or not the given score is greater than the current highscore of the user.
     *
     * @param user  The user who's score you want to compare.
     * @param score The score that you want to compare with the highscore.
     */
    private boolean isHigherScore(String user, int score) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { COL_4 };
        String selection = COL_1 + "=?";
        String[] selectionArgs = { user };
        Cursor queriedDB = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null,null);

        queriedDB.moveToFirst();
        int highscore = Integer.parseInt(queriedDB.getString(0));
        queriedDB.close();
        return score > highscore;
    }

    /**
     * Adds a new score to the user if it is a high score.
     *
     * @param user  The user who scored their new score.
     * @param score The user's new score.
     * @return returns true if the highscore was updated.
     */
    public boolean updateHighScore(String user, int score) {
        if (this.isHigherScore(user, score)) {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = { COL_3, COL_5, COL_6, COL_7, COL_8, COL_9 };
            String selection = COL_1 + "=?";
            String[] selectionArgs = { user };
            Cursor queriedDB = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null,null);

            queriedDB.moveToFirst();
            int totalPalladium = Integer.parseInt(queriedDB.getString(0));
            int savedScore = Integer.parseInt(queriedDB.getString(1));
            int savedPalladium = Integer.parseInt(queriedDB.getString(2));
            String savedLevel = queriedDB.getString(3);
            int pCustomization = Integer.parseInt(queriedDB.getString(4));
            int bCustomization = Integer.parseInt(queriedDB.getString(5));

            this.updateAccount(user, totalPalladium, score, savedScore, savedPalladium, savedLevel,
                    pCustomization,
                    bCustomization);

            queriedDB.close();
            db.close();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Appends palladium to the user. Value can be negative, indicating spent palladium.
     *
     * @param user      The user who needs their palladium updated.
     * @param palladium The ammount of palladium to be added or removed from the user's total
     *                  palladium count.
     * @return true if the palladium could be added to the current balance.
     */
    public boolean updatePalladium(String user, int palladium) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = { COL_3, COL_4, COL_5, COL_6, COL_7, COL_8, COL_9 };
        String selection = COL_1 + "=?";
        String[] selectionArgs = { user };
        Cursor queriedDB = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null,null);

        queriedDB.moveToFirst();
        int totalPalladium = Integer.parseInt(queriedDB.getString(0));
        int highScore = Integer.parseInt(queriedDB.getString(1));
        int savedScore = Integer.parseInt(queriedDB.getString(2));
        int savedPalladium = Integer.parseInt(queriedDB.getString(3));
        String savedLevel = queriedDB.getString(4);
        int pCustomization = Integer.parseInt(queriedDB.getString(5));
        int bCustomization = Integer.parseInt(queriedDB.getString(6));

        queriedDB.close();
        db.close();

        if (totalPalladium + palladium >= 0) {
            totalPalladium = totalPalladium + palladium;

            this.updateAccount(user, totalPalladium, highScore, savedScore, savedPalladium, savedLevel,
                    pCustomization, bCustomization);

            queriedDB.close();
            db.close();
            return true;
        } else {
            queriedDB.close();
            db.close();
            return false;
        }
    }

    /**
     * Returns a List of lists, where each sublist contains 2 strings, a Username and a Highscore
     * that corresponds to that user. It returns the top 5 highscores.
     *
     * @return array list of lists that describe the score board.
     */
    List<List<String>> getScoreboard() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { COL_1, COL_4 };
        String limit = "5";
        Cursor queriedDB = db.query(TABLE_NAME, columns, null, null,null,null, COL_4, limit);

        List<List<String>> scoreBoard = new ArrayList<>();

        while (queriedDB.moveToNext()) {
            List<String> sublist = new ArrayList<>();
            sublist.add(queriedDB.getString(0));
            sublist.add(queriedDB.getString(1));
            scoreBoard.add(sublist);
        }

        queriedDB.close();
        db.close();
        return scoreBoard;
    }
}
