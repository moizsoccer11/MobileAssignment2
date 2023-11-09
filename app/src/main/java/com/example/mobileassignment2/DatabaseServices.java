package com.example.mobileassignment2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseServices {

    private SQLiteDatabase database;
    private SQLHelper dbHelper;

    public DatabaseServices(Context context) {
        dbHelper = new SQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //Functions to interact with database

    //Function to insert data into database
    public long insertData(DataContainer address) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_ADDRESS, address.getAddress());
        values.put(SQLHelper.COLUMN_LATITUDE, address.getLatitude());
        values.put(SQLHelper.COLUMN_LONGITUDE, address.getLongitude());
        return database.insert(SQLHelper.TABLE_LOCATIONS, null, values);
    }

    //Function to get lat and long values given the address
    public String[] getLatitudeAndLongitude(String address) {
        String[] values =  new String[2];

        String query = "SELECT latitude, longitude FROM " + dbHelper.TABLE_LOCATIONS +
                " WHERE address = ?";
        Cursor cursor = database.rawQuery(query, new String[]{address});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            @SuppressLint("Range") double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));

            // Convert the values to strings
            values[0] = String.valueOf(latitude);
            values[1] = String.valueOf(longitude);
        }
        else {
            // Address not found in the database, return null
            values = null;
        }

        cursor.close();

        return values;
    }

    //Function to delete given address in database
    public void deleteAddress(String address){
        String whereClause = "address = ?";
        String[] whereArgs = {address};
        database.delete(SQLHelper.TABLE_LOCATIONS, whereClause, whereArgs);
    }
}
