package com.example.shubham11.ecraftindia.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import java.util.HashMap;

/**
 * Created by shubham11 on 25/5/17.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    Context context;

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String ACCESS_ROLE="access_role";
    private static final String PASSWORD="password";
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " STRING PRIMARY KEY,"
                + KEY_USERNAME + " TEXT UNIQUE,"
                + ACCESS_ROLE +" TEXT,"
                + PASSWORD+" TEXT"
                +")";
        db.execSQL(CREATE_LOGIN_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String username,String access_role,String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete * from "+ TABLE_USER);
        ContentValues values = new ContentValues();
       // values.put(KEY_NAME, name); // Name
        String imei= Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        values.put(KEY_USERNAME, username); // Email
        values.put(KEY_ID,imei);
        values.put(ACCESS_ROLE,access_role);
        values.put(PASSWORD,pass);
        /*values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); */// Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("username", cursor.getString(1));
            user.put("id",cursor.getString(0));
            user.put("access_role",cursor.getString(2));
            user.put("password",cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

    }

}
