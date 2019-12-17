package com.example.silownia.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.silownia.DAO.ExercisesDAO;
import com.example.silownia.R;



public class ExercisesCursorAdapter extends CursorAdapter implements Filterable {

    ExercisesDAO cwiczenie = null;
    private LayoutInflater mInflater;

    public ExercisesCursorAdapter(Context context, Cursor c, int flags, ExercisesDAO spis_cwiczen) {
        super(context, c, flags);
        cwiczenie = spis_cwiczen;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView t0 = view.findViewById(R.id.LIST_MACHINE_ID);
        t0.setText(cursor.getString(cursor.getColumnIndex(ExercisesDAO.KEY)));

        TextView t1 = view.findViewById(R.id.LIST_MACHINE_NAME);
        t1.setText(cursor.getString(cursor.getColumnIndex(ExercisesDAO.NAME)));


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.exerciseslist_row, parent, false);

    }
}
