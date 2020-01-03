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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.silownia.DAO.Log_EntriesDAO;
import com.example.silownia.DAO.Log_performedDAO;
import com.example.silownia.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetails extends Fragment implements AdapterView.OnItemSelectedListener{


    Cursor cursor_date;


    TextView text_set,text_reps,text_weight;
    Spinner dateList = null;

    ListView listView;
    List<String> mDateArray = null;

    ArrayAdapter<String> mAdapterDate = null;
    private Log_EntriesDAO historia=null;
    private Log_performedDAO completed_training=null;

    int ajdi_exercise=-1;



    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_details, container, false);

        Bundle args = this.getArguments();              //pobranie id z list view
        ajdi_exercise = args.getInt("ajdi");       //przypisanie tego id do szukanego

        dateList = view.findViewById(R.id.spiner_date);
        listView=view.findViewById(R.id.list_details);

        text_set = view.findViewById(R.id.text_set);
        text_reps = view.findViewById(R.id.text_reps);
        text_weight = view.findViewById(R.id.text_weight);



        completed_training = new Log_performedDAO(view.getContext());
        historia = new Log_EntriesDAO(view.getContext());

        mDateArray = new ArrayList<>();
        mAdapterDate = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                mDateArray);
        mAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateList.setAdapter(mAdapterDate);
        spinner_date();
        return view;
    }


    public void spinner_date(){

        dateList.setOnItemSelectedListener(this);
        completed_training = new Log_performedDAO(getContext());
        cursor_date = completed_training.getDateList(ajdi_exercise);

        if (cursor_date.moveToFirst())
        {
                do{
                    String data = cursor_date.getString(0);
                    mDateArray.add(data);                                //dodawanie daty do naszej listy
                }
                while (cursor_date.moveToNext());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, mDateArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateList.setAdapter(dataAdapter);
    }

    private void show_details_log(Integer ajdi, String data) {
        Cursor oldCursor = null;


        Cursor c =  historia.showDetails(ajdi, data);
        if(c == null || c.getCount() == 0){
            listView.setAdapter(null);
        }else{
            if(listView.getAdapter() == null){
                RecordCursorAdapter mTableAdapter = new RecordCursorAdapter(this.getView().getContext(),c,0);
                listView.setAdapter(mTableAdapter);
            }else{
                oldCursor = ((RecordCursorAdapter) listView.getAdapter()).swapCursor(c);
                if(oldCursor != null)
                    oldCursor.close();
            }
        }
    }

    private void refreshDates(){
        View fragmentView = getView();
        if(fragmentView != null){
            mDateArray.clear();
            cursor_date = completed_training.getDateList(ajdi_exercise);

            if (cursor_date.moveToFirst())
            {
                do{
                    String data = cursor_date.getString(0);
                    mDateArray.add(data);                                //dodawanie daty do naszej listy
                }
                while (cursor_date.moveToNext());
            }
           // if(mDateArray.size() > 1){
           //     dateList.setSelection(1);
            //}
            mAdapterDate.notifyDataSetChanged();
            cursor_date.close();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        refreshDates();
        //if(dateList.getCount()>1){
         //   dateList.setSelection(1);
       // }
       // else {
       //     dateList.setSelection(0);
        //}
        if(dateList.getCount()>=1) {
            show_details_log(ajdi_exercise, dateList.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}