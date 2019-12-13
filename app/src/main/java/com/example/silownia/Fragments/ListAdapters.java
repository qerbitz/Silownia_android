package com.example.silownia.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.silownia.R;
import com.example.silownia.models.Belongs_to;

import java.util.ArrayList;
import java.util.List;

public class ListAdapters extends ArrayAdapter {

    List list = new ArrayList();
    public int i =0;
    public ListAdapters(Context context, int resource){
        super(context,resource);
    }

    static class LayoutHandler{
        TextView NAME,CATEGORY;
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
            layoutHandler.NAME=(TextView)row.findViewById(R.id.text_set);
            layoutHandler.CATEGORY=(TextView)row.findViewById(R.id.text_reps);
            row.setTag(layoutHandler);
        }else{
            layoutHandler=(LayoutHandler)row.getTag();
        }

        //Exercises exercise =(Exercises)this.getItem(position);
        Belongs_to nalezy =(Belongs_to)this.getItem(position);
        layoutHandler.NAME.setText(nalezy.getCwiczenie().getExercise_name());
        layoutHandler.CATEGORY.setText(nalezy.getTrening().getWorkout_name());

        i++;
        return row;
    }
}
