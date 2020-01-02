package com.example.silownia.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.silownia.DAO.Log_EntriesDAO;
import com.example.silownia.R;

public class RecordCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private Context mContext = null;


    public RecordCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final int position = cursor.getPosition();

        TextView text_set = view.findViewById(R.id.text_set);
        TextView text_reps = view.findViewById(R.id.text_reps);
        TextView text_weight = view.findViewById(R.id.text_weight);


        text_set.setText(cursor.getString(cursor.getColumnIndex(Log_EntriesDAO.SET_NUMBER)));
        text_reps.setText(cursor.getString(cursor.getColumnIndex(Log_EntriesDAO.REPS)));
        text_weight.setText(cursor.getString(cursor.getColumnIndex(Log_EntriesDAO.WEIGHT)));

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.row_exercise_test, parent, false);
    }


}
