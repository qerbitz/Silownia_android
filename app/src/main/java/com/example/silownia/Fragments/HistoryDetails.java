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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.silownia.DAO.Log_EntriesDAO;
import com.example.silownia.DAO.Log_performedDAO;
import com.example.silownia.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetails extends Fragment implements AdapterView.OnItemSelectedListener{

    TextView text_set;
    TextView text_reps;
    TextView text_weight;
    TableLayout tableLayout;
    TableRow tableRow;
    Cursor cursor;
    Cursor cursor_date;

    SQLiteDatabase sqLiteDatabase;
    Log_EntriesDAO historia=null;
    Log_performedDAO completed_training;


    Spinner spiner_date;



    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_details, container, false);

        text_set = view.findViewById(R.id.text_set);
        text_reps = view.findViewById(R.id.text_reps);
        text_weight = view.findViewById(R.id.text_weight);
        tableLayout = view.findViewById(R.id.tableLayout);
        spiner_date = view.findViewById(R.id.spiner_date);

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

    public void show_details_log(String data) {

        cursor=null;
        historia=null;
        historia=new Log_EntriesDAO(getContext());
        sqLiteDatabase=historia.getReadableDatabase();
        tableLayout.removeAllViews();

         Bundle b = this.getArguments();        //pobranie id z list view
         int ajdi = b.getInt("ajdi");       //przypisanie tego id do szukanego

        cursor = historia.showDetails(ajdi, data);

        if (cursor.moveToFirst()) {
            do {
                String set, reps, weight;
                set = cursor.getString(1);
                reps = cursor.getString(2);
                weight = cursor.getString(3);

                tableRow = new TableRow(getContext());

                text_set = new TextView(getContext());
                text_reps = new TextView(getContext());
                text_weight = new TextView(getContext());

                text_set.setText(set);
                text_reps.setText(reps);
                text_weight.setText(weight);

                tableRow.addView(text_set);
                tableRow.addView(text_reps);
                tableRow.addView(text_weight);
                tableLayout.addView(tableRow);

                String TAG = "MyActivity";
                Log.i(TAG, "Cursoser rozmiarek "+cursor.getCount());
            }
            while (cursor.moveToNext());
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String TAG = "MyActivity";
        Log.i(TAG, "Przejscie do funkcji ");
        String data = spiner_date.getSelectedItem().toString();
        if(position!=0)
        {
            tableLayout.removeAllViews();
            show_details_log(data);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}