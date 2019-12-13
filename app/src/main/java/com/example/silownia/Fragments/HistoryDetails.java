package com.example.silownia.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.silownia.DAO.ExercisesDAO;
import com.example.silownia.DAO.Log_EntriesDAO;
import com.example.silownia.R;

public class HistoryDetails extends Fragment {

    TextView text_set;
    TextView text_reps;
    TextView text_weight;
    TableLayout tableLayout;
    TableRow tableRow;
    Cursor cursor;

    SQLiteDatabase sqLiteDatabase;
    Log_EntriesDAO historia;

    int help = 1;

    String passedVar=null;



    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_details, container, false);


        text_set = view.findViewById(R.id.text_set);
        text_reps = view.findViewById(R.id.text_reps);
        text_weight = view.findViewById(R.id.text_weight);
        tableLayout = view.findViewById(R.id.tableLayout);
        show_details_log();

        return view;
    }

    public void show_details_log() {

        historia=new Log_EntriesDAO(getContext());
        sqLiteDatabase=historia.getReadableDatabase();

        Bundle b = this.getArguments();
        if(b != null){
            int i = b.getInt("YourKey");
            String s =b.getString("Key");
        }


        cursor=historia.showDetails("5");

        Toast toast=Toast.makeText(getContext(),"Hello "+passedVar,Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();

        if (cursor.moveToFirst()) {

            do {
                String set, reps, weight;
                set = cursor.getString(0);
                reps = cursor.getString(1);
                weight = cursor.getString(2);

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

            }
            while (cursor.moveToNext());
        }


    }

    public int getHelp() {
        return help;
    }

    public void setHelp(int help) {
        this.help = help;
    }
}