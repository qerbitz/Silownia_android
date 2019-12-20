package com.example.silownia.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;


public class Log_performedDAO extends DAO_database {



    public static final String TABLE_NAME = "Log_performed";
    public static final String KEY = "Log_id";
    public static final String TRENING = "Workout_id";
    public static final String DATA = "Date";

    public Log_performedDAO(Context context) {
        super(context);
    }

    public void addLog(int trening_id){

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());         //pobranie obecnej daty

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(TRENING, trening_id);
        contentValues.put(DATA, String.valueOf(date));

        db.insert(TABLE_NAME,null,contentValues);
    }

    public Cursor getDateList(){
        String query = "select distinct Date from Log_performed ORDER BY Date DESC;";
        return getDateListCursor(query);
    }

    public Cursor getDateListCursor(String pRequest){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(pRequest, null);
    }


}
