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
import com.example.silownia.models.Log_Entries;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter_details extends ArrayAdapter {

    List list = new ArrayList();
    public int i =0;
    public ListAdapter_details(Context context, int resource){
        super(context,resource);
    }

    static class LayoutHandler{
        TextView SET,REPS,WEIGHT;
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
            row=layoutInflater.inflate(R.layout.row_exercise_test,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.SET=(TextView)row.findViewById(R.id.text_set);
            layoutHandler.REPS=(TextView)row.findViewById(R.id.text_reps);
            layoutHandler.WEIGHT=(TextView)row.findViewById(R.id.text_weight);
            row.setTag(layoutHandler);
        }else{
            layoutHandler=(LayoutHandler)row.getTag();
        }

        Log_Entries historia =(Log_Entries)this.getItem(position);
        layoutHandler.SET.setText(Integer.toString(historia.getSet_number()));
        layoutHandler.REPS.setText(Integer.toString(historia.getReps()));
        layoutHandler.WEIGHT.setText(Float.toString(historia.getWeight()));
        i++;
        return row;
    }
}
