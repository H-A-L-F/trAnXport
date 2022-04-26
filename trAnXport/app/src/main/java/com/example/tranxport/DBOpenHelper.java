package com.example.tranxport;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "trAnXport.db";
    public final static int DB_VERSION = 1;
    public final static String ACCOUNT = "Account";
    public final static String ID = "Id";
    public final static String USERNAME = "Username";
    public final static String EMAIL = "Email";
    public final static String PHONENUMBER = "PhoneNumber";
    public final static String PASSWORD = "Password";

    private static DBOpenHelper db = null;

    private DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBOpenHelper getInstance(Context context) {
        return db = (db == null) ? new DBOpenHelper(context) : db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ACCOUNT + "(" +
                ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " TEXT NOT NULL," +
                EMAIL + " TEXT NOT NULL," +
                PHONENUMBER + " TEXT NOT NULL," +
                PASSWORD + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
