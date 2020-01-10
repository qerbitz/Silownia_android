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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.silownia.DAO.ExercisesDAO;
import com.example.silownia.DAO.Log_EntriesDAO;
import com.example.silownia.DAO.Log_performedDAO;
import com.example.silownia.R;
import com.example.silownia.models.Exercises;

import java.util.ArrayList;
import java.util.List;


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
    TextView maxText;
    TextView last_label;

    List list_set = new ArrayList();
    List list_reps = new ArrayList();
    List list_weight = new ArrayList();


    ImageButton subButton1, subButton2,subButton3, downButton1,downButton2, downButton3;
    int i = 1;
    int ajdi_workout=-1;

    String value = null;
    String value2= null;
    int value1=0;
    double value11=0;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_training, container, false);


        Bundle args = this.getArguments();              //pobranie id z list view
        ajdi_workout = args.getInt("ajdi_training");

        editSerie = view.findViewById(R.id.editSerie);                                              //
        editRepetition = view.findViewById(R.id.editRepetition);                                    //
        editWeight = view.findViewById(R.id.editWeight);


        btn_exercises_list = view.findViewById(R.id.btn_exercises_list);
        btn_exercises_list.setOnClickListener(onClickMachineListWithIcons);

        add_set = view.findViewById(R.id.add_set);
        add_set.setOnClickListener(onClickAddSet);                                                  //obsluga przycisku dodania serii

        add_training = view.findViewById(R.id.add_training);
        add_training.setOnClickListener(onClickAddTraining);                                                                                //obsluga przycisku dodania cwiczenia

        machineEdit = view.findViewById(R.id.editMachine);


        text_set= view. findViewById(R.id.text_set);
        text_reps= view.findViewById(R.id.text_reps);
        text_weight= view.findViewById(R.id.text_reps);
        tableLayout = view.findViewById(R.id.tableLayout);

        trening_dane = new Log_EntriesDAO(getContext());
        trening_data = new Log_performedDAO(getContext());

        restTimeCheck = view.findViewById(R.id.restTimecheckBox);
        restTimeEdit = view.findViewById(R.id.editRestTime);

        last_label = view.findViewById(R.id.last_label);

        subButton1 = view.findViewById(R.id.subButton1);
        subButton2 = view.findViewById(R.id.subButton2);
        subButton3 = view.findViewById(R.id.subButton3);

        downButton1 = view.findViewById(R.id.downButton1);
        downButton2 = view.findViewById(R.id.downButton2);
        downButton3 = view.findViewById(R.id.downButton3);

        subButton1.setOnClickListener(buttonsListener);
        subButton2.setOnClickListener(buttonsListener);
        subButton3.setOnClickListener(buttonsListener);
        downButton1.setOnClickListener(buttonsListener);
        downButton2.setOnClickListener(buttonsListener);
        downButton3.setOnClickListener(buttonsListener);

        maxText = view.findViewById(R.id.maxText);
        return view;
    }

    private View.OnClickListener buttonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.subButton1:
                    value = editSerie.getText().toString();
                    value1 = Integer.parseInt(value);
                    value1++;
                    value2 = String.valueOf(value1);
                    editSerie.setText(value2);
                    break;
                case R.id.subButton2:
                    value = editRepetition.getText().toString();
                    value1 = Integer.parseInt(value);
                    value1++;
                    value2 = String.valueOf(value1);
                    editRepetition.setText(value2);
                    break;
                case R.id.subButton3:
                    value = editWeight.getText().toString();
                    value11 = Float.parseFloat(value);
                    value11=value11+2.5;
                    value2 = String.valueOf(value11);
                    editWeight.setText(value2);
                    break;
                case R.id.downButton1:
                    value = editSerie.getText().toString();
                    value1 = Integer.parseInt(value);
                    value1--;
                    value2 = String.valueOf(value1);
                    editSerie.setText(value2);
                    break;
                case R.id.downButton2:
                    value = editRepetition.getText().toString();
                    value1 = Integer.parseInt(value);
                    value1--;
                    value2 = String.valueOf(value1);
                    editRepetition.setText(value2);
                    break;
                case R.id.downButton3:
                    value = editWeight.getText().toString();
                    value11 = Float.parseFloat(value);
                    value11=value11-2.5;
                    value2 = String.valueOf(value11);
                    editWeight.setText(value2);
                    break;

            }
        }
    };

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

            list_set.add(text_set.getText().toString());
            list_reps.add(text_reps.getText().toString());
            list_weight.add(text_weight.getText().toString());

            editSerie.setText(String.valueOf(i + 1));
            i++;


        }
        };

    int return_log(){
        Cursor c = trening_data.getLastLog();
        int last_log = 0;
        if(c.moveToFirst()){
            last_log = c.getInt(0);
        }
        return last_log;
    }

    private View.OnClickListener onClickAddTraining = new View.OnClickListener(){                        //Dodanie cwiczenia po przycisku
        @Override
        public void onClick(View v) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
            a_builder.setMessage("Save this exercise ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            trening_data.addLog(1);
                            int help = list_set.size();
                            for(int i=0; i<help;i++) {

                                String seria = list_set.get(i).toString();
                                String reps = list_reps.get(i).toString();
                                String weight = list_weight.get(i).toString();

                                int seria1 = Integer.parseInt(seria.trim());
                                int reps1 = Integer.parseInt(reps.trim());
                                float weight1 = Float.parseFloat(weight.trim());
                                trening_dane.addTrening(return_log(),seria1,reps1,weight1,getExercise_ID());

                            }

                            tableLayout.removeAllViews();
                            editSerie.setText("1");
                            list_set.clear();           //czyszczenie list
                            list_reps.clear();          //czyszczenie list
                            list_weight.clear();        //czyszczenie list
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


            c = spis_cwiczen.getExercisesList(ajdi_workout);


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
                    maxText.setText(last_weight().toString());
                    Double next_weight = last_weight()+2.5;
                    editWeight.setText(next_weight.toString());
                    last_label.setText("Last training: ");
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

    private Float last_weight(){
        Cursor c =  spis_cwiczen.getLastWeight(getExercise_ID());
        Float ostatni = Float.valueOf(0);
        if (c.moveToFirst()) {
           ostatni = c.getFloat(0);
        }
        return ostatni;
    }

    public int getExercise_ID() {
        return exercise_ID;
    }

    public void setExercise_ID(int exercise_ID) {
        this.exercise_ID = exercise_ID;
    }



}




