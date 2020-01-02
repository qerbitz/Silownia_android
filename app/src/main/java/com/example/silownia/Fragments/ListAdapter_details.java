package com.example.silownia.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.silownia.R;
import com.example.silownia.models.Belongs_to;
import com.example.silownia.models.Log_Entries;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter_details extends ArrayAdapter {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder{
        TextView SET,REPS,WEIGHT;
    }


    public ListAdapter_details(Context context, int resource, ArrayList<Log_Entries> objects){
        super(context,resource,objects);
        mContext=context;
        mResource=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Integer set = getItem(position).getSet_number();
        //Integer reps = getItem(position).getReps();
        //Float weight = getItem(position).getWeight();

        //Log_Entries details = new Log_Entries(set,reps,weight);

        final View result;

        ViewHolder holder;

        if(convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(mContext);
            convertView=layoutInflater.inflate(mResource,parent,false);

            holder = new ViewHolder();
            holder.SET=(TextView)convertView.findViewById(R.id.text_set);
            holder.REPS=(TextView)convertView.findViewById(R.id.text_reps);
            holder.WEIGHT=(TextView)convertView.findViewById(R.id.text_weight);

            result = convertView;
            convertView.setTag(holder);
        }else{
            holder =(ViewHolder)convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        //holder.SET.setText(Integer.toString(details.getSet_number()));
        //holder.REPS.setText(Integer.toString(details.getReps()));
        //holder.WEIGHT.setText(Float.toString(details.getWeight()));

        return convertView;
    }
}
