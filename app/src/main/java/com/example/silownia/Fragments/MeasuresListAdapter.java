package com.example.silownia.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.silownia.DAO.WeightDAO;
import com.example.silownia.R;


public class MeasuresListAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private Context mContext = null;


    public MeasuresListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        CardView cdView = view.findViewById(R.id.CARDVIEW);
        final int position = cursor.getPosition();

        if (position % 2 == 0) {
            cdView.setBackgroundColor(context.getResources().getColor(R.color.background));
        } else {
            cdView.setBackgroundColor(context.getResources().getColor(R.color.background_even));
        }

        TextView text_date = view.findViewById(R.id.text_date);
        TextView text_weight = view.findViewById(R.id.text_weight);


        text_date.setText(cursor.getString(cursor.getColumnIndex(WeightDAO.DATE)));
        text_weight.setText(cursor.getString(cursor.getColumnIndex(WeightDAO.WEIGHT))+" KG");


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.bodymeasure_rowheader, parent, false);
    }


}
