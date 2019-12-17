package com.example.silownia.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.silownia.DAO.ExercisesDAO;
import com.example.silownia.DAO.Log_EntriesDAO;
import com.example.silownia.DAO.Log_performedDAO;
import com.example.silownia.MainActivity;
import com.example.silownia.R;
import com.example.silownia.models.Exercises;

import java.util.Date;


public class TrainingFragment extends Fragment {
    public int exercise_ID;
    EditText editSerie, editRepetition, editWeight;

    SQLiteDatabase sqLiteDatabase;
    Log_EntriesDAO trening_dane;
    Log_performedDAO trening_data;
    ExercisesDAO spis_cwiczen;

    ImageButton btn_exercises_list = null;
    Button add_set = null;
    Button add_training = null;
    AlertDialog exercisesListDialog;

    Cursor c;
    Cursor oldCursor;

    CheckBox restTimeCheck;
    EditText restTimeEdit;


    TextView machineEdit;
    TextView text_set;
    TextView text_reps;
    TextView text_weight;
    TableLayout tableLayout;
    TableRow tableRow;



    int i = 1;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_training, container, false);

        editSerie = view.findViewById(R.id.editSerie);                                              //
        editRepetition = view.findViewById(R.id.editRepetition);                                    //
        editWeight = view.findViewById(R.id.editWeight);                                            //

        btn_exercises_list = view.findViewById(R.id.btn_exercises_list);
        btn_exercises_list.setOnClickListener(onClickMachineListWithIcons);

        add_set = view.findViewById(R.id.add_set);
        add_set.setOnClickListener(onClickAddSet);                                                  //obsluga przycisku dodania serii

        add_training = view.findViewById(R.id.add_training);
        add_training.setOnClickListener(onClickAddTraining);                                                                                //obsluga przycisku dodania cwiczenia

        machineEdit = view.findViewById(R.id.editMachine);


        text_set= view. findViewById(R.id.text_set);
        text_reps= view.findViewById(R.id.text_reps);
        text_weight= view. findViewById(R.id.text_weight);
        tableLayout = view. findViewById(R.id.tableLayout);

        trening_dane = new Log_EntriesDAO(getContext());
        trening_data = new Log_performedDAO(getContext());

        restTimeCheck = view.findViewById(R.id.restTimecheckBox);
        restTimeEdit = view.findViewById(R.id.editRestTime);

        return view;
    }

    private View.OnClickListener onClickAddSet = new View.OnClickListener(){                        //Dodanie serii po przycisku
        @Override
        public void onClick(View v) {
            tableRow = new TableRow(getContext());

            text_set = new TextView(getContext());
            text_reps = new TextView(getContext());
            text_weight = new TextView(getContext());

            text_set.setText(editSerie.getText());
            text_reps.setText(editRepetition.getText());
            text_weight.setText(editWeight.getText());

            tableRow.addView(text_set);
            tableRow.addView(text_reps);
            tableRow.addView(text_weight);
            tableLayout.addView(tableRow);

            editSerie.setText(String.valueOf(i + 1));
            i++;


            if (restTimeCheck.isChecked()) {
                boolean bLaunchRest = restTimeCheck.isChecked();
                int restTime = 60;
                try {
                    restTime = Integer.valueOf(restTimeEdit.getText().toString());
                } catch (NumberFormatException e) {
                    restTime = 60;
                    restTimeEdit.setText("60");
                }


                CountdownDialogbox cdd = new CountdownDialogbox(getActivity(), restTime);
                cdd.show();
            }
        }
        };

    private View.OnClickListener onClickAddTraining = new View.OnClickListener(){                        //Dodanie cwiczenia po przycisku
        @Override
        public void onClick(View v) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
            a_builder.setMessage("Save this exercise ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            trening_dane.addTrening(5, Integer.parseInt(editSerie.getText().toString()), Integer.parseInt(editRepetition.getText().toString()), Integer.parseInt(editWeight.getText().toString()),getExercise_ID());
                            tableLayout.removeAllViews();
                            editSerie.setText("1");
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }) ;
            AlertDialog alert = a_builder.create();
            alert.setTitle("Alert!");
            alert.show();
        }
    };



    private View.OnClickListener onClickMachineListWithIcons = new View.OnClickListener() {         //Wybor cwiczen z listy
        @Override
        public void onClick(View v) {


            ListView exercisesList = new ListView(v.getContext());

            spis_cwiczen = new ExercisesDAO(v.getContext());
            sqLiteDatabase = spis_cwiczen.getReadableDatabase();
            c = spis_cwiczen.getAllData();


            if (exercisesList.getAdapter() == null) {
                ExercisesCursorAdapter mTableAdapter = new ExercisesCursorAdapter(v.getContext(), c, 0, spis_cwiczen);
                exercisesList.setAdapter(mTableAdapter);
            } else {
                ExercisesCursorAdapter mTableAdapter = ((ExercisesCursorAdapter) exercisesList.getAdapter());
                oldCursor = mTableAdapter.swapCursor(c);
                if (oldCursor != null) oldCursor.close();
            }
            exercisesList.setOnItemClickListener((parent, view, position, id) -> {
                TextView textView = view.findViewById(R.id.LIST_MACHINE_ID);
                exercise_ID = Integer.parseInt(textView.getText().toString());
                setExercise_ID(exercise_ID);

                Exercises nazwa = spis_cwiczen.getExercise(exercise_ID);


                if (exercisesListDialog.isShowing()) {
                    machineEdit.setText(nazwa.getExercise_name());
                    exercisesListDialog.dismiss();
                }


            });


            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Wybierz cwiczenie");
            builder.setView(exercisesList);
            exercisesListDialog = builder.create();
            exercisesListDialog.show();
        }

    };



    public int getExercise_ID() {
        return exercise_ID;
    }

    public void setExercise_ID(int exercise_ID) {
        this.exercise_ID = exercise_ID;
    }



}




