package com.example.silownia.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.silownia.models.Exercises;

import java.util.ArrayList;

public class ExercisesDAO extends DAO_database{

    public static final String TABLE_NAME = "Exercises";
    public static final String KEY = "_id";
    public static final String NAME = "Exercise_name";
    public static final String CATEGORY = "Exercise_category";

    private Cursor mCursor = null;

    public ExercisesDAO(Context context) {
        super(context);
    }


    public Cursor getExercisesList(int workout_id){
        ArrayList<Exercises> exercisesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select e._id, e.Exercise_name, w.Workout_name from Exercises e, Belongs_to b, Workouts w where w.Workout_id=b.Workout_id and e._id=b.Exercise_id and w.Workout_id="+workout_id;
        return getExercisesListCursor(query);
    }

    public Cursor getExercisesListCursor(String pRequest){
        ArrayList<Exercises> exercisesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(pRequest, null);
    }

    public Exercises getExercise(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        mCursor = null;

        mCursor = db.query(TABLE_NAME, new String[]{KEY,NAME,CATEGORY}, KEY +"=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(mCursor!=null)
            mCursor.moveToFirst();

        if(mCursor.getCount()==0)
            return null;

        Exercises cwiczenie = new Exercises(
                mCursor.getInt(0),
                mCursor.getString(1),
                mCursor.getString(2));

        cwiczenie.setExercise_id(mCursor.getInt(0));
        cwiczenie.setExercise_name(mCursor.getString(1));
        cwiczenie.setExercise_category(mCursor.getString(2));
        mCursor.close();
        close();
        return cwiczenie;
    }



}
