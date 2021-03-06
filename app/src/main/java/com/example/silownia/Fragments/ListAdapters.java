package com.example.silownia.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.silownia.R;
import com.example.silownia.models.Belongs_to;

import java.util.ArrayList;
import java.util.List;

public class ListAdapters extends ArrayAdapter {

    List list = new ArrayList();
    CardView cdView;
    public int i = 0;
    public ListAdapters(Context context, int resource){
        super(context,resource);
    }

    static class LayoutHandler{
        TextView AJDI,NAME;
    }

    @Override
    public void add(Object object){
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {




        View row = convertView;
        LayoutHandler layoutHandler;

        if(row==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.AJDI=(TextView)row.findViewById(R.id.text_set);
            layoutHandler.NAME=(TextView)row.findViewById(R.id.text_reps);

            cdView = row.findViewById(R.id.CARDVIEW);

            row.setTag(layoutHandler);
        }else{
            layoutHandler=(LayoutHandler)row.getTag();
        }

        if (position % 2 == 0) {
            cdView.setBackgroundColor(getContext().getResources().getColor(R.color.background));
        } else {
            cdView.setBackgroundColor(getContext().getResources().getColor(R.color.background_even));
        }

        Belongs_to nalezy =(Belongs_to)this.getItem(position);
        layoutHandler.AJDI.setText(nalezy.getCwiczenie().getExercise_name());
        layoutHandler.NAME.setText(nalezy.getTrening().getWorkout_name());

        i++;
        return row;
    }
}
