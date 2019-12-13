package com.example.silownia.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DAO_database  {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DAO_database(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public SQLiteDatabase open() {
        return database = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return database = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        return database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
