package com.example.silownia.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.silownia.DAO.Log_EntriesDAO;
import com.example.silownia.DAO.Log_performedDAO;
import com.example.silownia.R;
import com.example.silownia.models.Log_Entries;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetails extends Fragment implements AdapterView.OnItemSelectedListener{


    Cursor cursor;
    Cursor cursor_date;

    SQLiteDatabase sqLiteDatabase;
    Log_EntriesDAO historia=null;
    Log_Entries details = null;
    Log_performedDAO completed_training;


    Spinner spiner_date;

    ListView listView;
    ListAdapter_details listAdapter_details;



    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_details, container, false);

        spiner_date = view.findViewById(R.id.spiner_date);

        listView=view.findViewById(R.id.list_details);
        listAdapter_details = new ListAdapter_details(getContext(), R.layout.row_exercise_test);

        spinner_date();
        return view;
    }


    public void spinner_date(){
        spiner_date.setOnItemSelectedListener(this);

        completed_training = new Log_performedDAO(getContext());
        sqLiteDatabase=completed_training.getReadableDatabase();

        List<String> list_date = new ArrayList<>();     //lista z datami treningów

        cursor_date = completed_training.getDateList();

        if (cursor_date.moveToFirst())
        {
                do{
                    String data = cursor_date.getString(0);
                    list_date.add(data);                                //dodawanie daty do naszej listy
                }
                while (cursor_date.moveToNext());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list_date);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_date.setAdapter(dataAdapter);
    }

    public void show_details_log() {

        String data = spiner_date.getSelectedItem().toString();
        cursor=null;
        historia=null;
        historia=new Log_EntriesDAO(getContext());
        sqLiteDatabase=historia.getReadableDatabase();

         Bundle b = this.getArguments();        //pobranie id z list view
         int ajdi = b.getInt("ajdi");       //przypisanie tego id do szukanego

        cursor = historia.showDetails(ajdi, data);
        listAdapter_details = new ListAdapter_details(getContext(), R.layout.row_exercise_test);
        listView.setAdapter(listAdapter_details);

        if(cursor==null || cursor.getCount()==0){
            listView.setAdapter(null);
        }
        else if (cursor.moveToFirst()) {
            do {
                Integer set, reps;
                Float weight;
                set = cursor.getInt(1);
                reps = cursor.getInt(2);
                weight = cursor.getFloat(3);
                Log_Entries details = new Log_Entries(set,reps,weight);
                listAdapter_details.add(details);

                String TAG = "MyActivity";
                Log.i(TAG, "Rozmiareeeeekkkk "+cursor.getCount());
            }
            while (cursor.moveToNext());
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        show_details_log();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}