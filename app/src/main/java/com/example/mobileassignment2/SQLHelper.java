package com.example.mobileassignment2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locationDatabase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_LOCATIONS = "locations";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";


    private static final String CREATE_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + " (" +
            COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ADDRESS +" TEXT," +
            COLUMN_LATITUDE +" REAL," +
            COLUMN_LONGITUDE + " REAL" +
            ");";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOCATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades if needed
    }
}
