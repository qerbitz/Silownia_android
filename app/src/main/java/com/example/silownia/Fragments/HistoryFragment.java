package com.example.silownia.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.silownia.DAO.ExercisesDAO;
import com.example.silownia.R;
import com.example.silownia.models.Belongs_to;
import com.example.silownia.models.Exercises;
import com.example.silownia.models.Workouts;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment  extends Fragment implements AdapterView.OnItemSelectedListener{

    SQLiteDatabase sqLiteDatabase;
    ExercisesDAO cwiczenie;
    Cursor cursor;
    ListView listView;
    ListAdapters listAdapter;



    public Spinner spiner;
    public int exercise_ID;
    public final static String ID_EXTRA = "";

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_history, container, false);

        listView=view.findViewById(R.id.listFilterRecord);

        cwiczenie=new ExercisesDAO(getContext());
        sqLiteDatabase=cwiczenie.getReadableDatabase();
        listAdapter = new ListAdapters(getContext(), R.layout.row_layout);
        spiner = view.findViewById(R.id.spiner);

        spinner();

        return view;
    }


    public void spinner(){
        spiner.setOnItemSelectedListener(this);

        List<String> list = new ArrayList<String>();
        list.add("FBW A");
        list.add("FBW B");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = view.findViewById(R.id.text_set);
                exercise_ID = Integer.parseInt(textView.getText().toString());

                Bundle b = new Bundle();
                b.putString("Key", "YourValue");
                b.putInt("YourKey", 1);

                HistoryDetails fragB = new HistoryDetails();
                fragB.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragB);

                HistoryDetails nextFrag= new HistoryDetails();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position==1){

        cwiczenie=new ExercisesDAO(getContext());
        sqLiteDatabase=cwiczenie.getReadableDatabase();

        cursor=cwiczenie.getFilteredRecords();
        listAdapter = new ListAdapters(getContext(), R.layout.row_layout);
        listView.setAdapter(listAdapter);

        if(cursor.moveToFirst())
        {

          do {
              String name, trening;
              name = cursor.getString(0);
              trening = cursor.getString(1);

              Exercises exercise = new Exercises(name);
              Workouts workout = new Workouts(trening);
              Belongs_to nalezy = new Belongs_to(exercise, workout);
              listAdapter.add(nalezy);
          }
          while(cursor.moveToNext());
        }}
        else if(position==0){
            cwiczenie=new ExercisesDAO(getContext());
            sqLiteDatabase=cwiczenie.getReadableDatabase();

            cursor=cwiczenie.getAllData();
            listAdapter = new ListAdapters(getContext(), R.layout.row_layout);
            listView.setAdapter(listAdapter);

            if(cursor.moveToFirst())
            {

                do {
                    String name, trening;

                    name = cursor.getString(0);
                    trening = cursor.getString(1);

                    Exercises exercise = new Exercises(name);
                    Workouts workout = new Workouts(trening);
                    Belongs_to nalezy = new Belongs_to(exercise, workout);
                    listAdapter.add(nalezy);
                }
                while(cursor.moveToNext());
            }}
        }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
