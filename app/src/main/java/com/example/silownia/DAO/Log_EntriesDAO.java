package com.example.silownia.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Log_EntriesDAO extends DAO_database{


    public static final String TABLE_NAME = "Log_Entries";
    public static final String KEY = "Entries_id";
    public static final String LOG = "Log_id";
    public static final String SET_NUMBER = "Set_number";
    public static final String WEIGHT = "Weight";
    public static final String REPS = "Reps";
    public static final String EXERCISE_ID = "_id";


    public Log_EntriesDAO(Context context) {
        super(context);
    }

    public void addTrening(int log, int set_number, int reps, float weight, int exercise_id){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(LOG, log);
        contentValues.put(SET_NUMBER, set_number);
        contentValues.put(REPS, reps);
        contentValues.put(WEIGHT, weight);
        contentValues.put(EXERCISE_ID, exercise_id);


        db.insert(TABLE_NAME,null,contentValues);
    }

    public Cursor showDetails(int cwiczenie, String data){
        String query = "select distinct le._id, le.Set_number, le.Reps, le.Weight, lp.Date from Log_entries le, Log_performed lp where _id="+ cwiczenie +" and Date ='"+data+"' and lp.Log_id = le.Log_id";
        return getDetailsInfo(query);
    }

    public Cursor getDetailsInfo(String pRequest){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(pRequest, null);
    }


}
