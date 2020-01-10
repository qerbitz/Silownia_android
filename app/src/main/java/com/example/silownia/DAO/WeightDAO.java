package com.example.silownia.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;


public class WeightDAO extends DAO_database {

    public static final String TABLE_NAME = "Weight";
    public static final String DATE = "date";
    public static final String WEIGHT = "weight";
    public static final String HEIGHT= "height";

    public WeightDAO(Context context) {
        super(context);
    }

    public Cursor getAllMeasures(){
        String query = "select * from Weight ORDER BY Date DESC";
        return getMeasureListCursor(query);
    }

    public Cursor getHeightMeasures(){
        String query = "select * from Height;";
        return getMeasureListCursor(query);
    }

    public Cursor getMeasureListCursor(String pRequest){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(pRequest, null);
    }


    public void addMeasureWeight(Float weight){
        SQLiteDatabase db=this.getWritableDatabase();

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        ContentValues contentValues=new ContentValues();
        contentValues.put(WEIGHT, weight);
        contentValues.put(DATE, String.valueOf(date));


        db.insert(TABLE_NAME,null,contentValues);
    }

    public void addMeasureHeight(Float height){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(HEIGHT, height);


        db.insert("Height",null,contentValues);
    }


}
