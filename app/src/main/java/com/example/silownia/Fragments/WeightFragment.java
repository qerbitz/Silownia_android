package com.example.silownia.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.silownia.DAO.WeightDAO;
import com.example.silownia.EditableInputView.EditableInputView;
import com.example.silownia.EditableInputView.EditableInputViewWithDate;
import com.example.silownia.MainActivity;
import com.example.silownia.R;


public class WeightFragment extends Fragment {

    private WeightDAO wazenie=null;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor = null;
    Cursor cursor2 = null;
    TextView bmiValue;
    TextView bmiStats;

        MainActivity mActivity = null;
        private EditableInputViewWithDate weightEdit = null;
        private EditableInputViewWithDate heightEdit = null;
        private AdapterView.OnClickListener showDetailsFragment = v -> {
            int bodyPartID = 19;
            switch (v.getId()) {
                case R.id.weightDetailsButton:
                   bodyPartID = 19;
                    break;
            }


           WeightDetailsDetails nextFrag= new WeightDetailsDetails();

           getActivity().getSupportFragmentManager().beginTransaction()
                   .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                   .addToBackStack(null)
                   .commit();

        };
        private Spinner.OnItemSelectedListener itemOnItemSelectedChange = new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  refreshData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        private EditableInputView.OnTextChangedListener itemOnTextChange = view -> {
            EditableInputViewWithDate v = (EditableInputViewWithDate) view;
            try {
                switch (view.getId()) {
                    case R.id.weightInput:
                        float weightValue = Float.parseFloat(v.getText());
                        weightEdit.setText(String.valueOf(weightValue));
                        wazenie.addMeasureWeight(weightValue);
                        break;
                    case R.id.heightInput:
                        float heightValue = Float.parseFloat(v.getText());
                        heightEdit.setText(String.valueOf(heightValue));
                        wazenie.addMeasureHeight(heightValue);

                }
            } catch (NumberFormatException e) {
            }
        };
        /**
         * Create a new instance of DetailsFragment, initialized to
         * show the text at 'index'.
         */
        public static WeightFragment newInstance(String name, int id) {
            WeightFragment f = new WeightFragment();

            Bundle args = new Bundle();
            args.putString("name", name);
            args.putInt("id", id);
            f.setArguments(args);

            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.tab_weight, container, false);
            Float masa = null, wzrost = null;

            weightEdit = view.findViewById(R.id.weightInput);
            heightEdit = view.findViewById(R.id.heightInput);

            wazenie=new WeightDAO(view.getContext());
            sqLiteDatabase=wazenie.getReadableDatabase();
            cursor = wazenie.getAllMeasures();

            cursor2 = wazenie.getHeightMeasures();

            if(cursor.moveToFirst()){
                weightEdit.setText(cursor.getString(1));
            }
            if(cursor2.moveToFirst()){
                heightEdit.setText(cursor2.getString(0));
            }

            ImageButton weightDetailsButton = view.findViewById(R.id.weightDetailsButton);


            weightEdit.setOnTextChangeListener(itemOnTextChange);
            heightEdit.setOnTextChangeListener(itemOnTextChange);

            weightDetailsButton.setOnClickListener(showDetailsFragment);

            bmiValue = view.findViewById(R.id.bmiValue);

            masa = Float.valueOf(weightEdit.getText());
            wzrost = Float.valueOf(heightEdit.getText());
            Float bmi = (masa/(wzrost*wzrost))*10000;
            bmiValue.setText(String.valueOf(bmi));

            bmiStats = view.findViewById(R.id.bmiStats);
            if (bmi < 18.5) {
                bmiStats.setText("Underweight");
            } else if (bmi < 25) {
                bmiStats.setText("Normal");
            } else if (bmi < 30) {
                bmiStats.setText("Overweight");
            } else {
                bmiStats.setText("Obese");
            }
            return view;
        }


        @Override
        public void onStart() {
            super.onStart();

           //refreshData();
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            this.mActivity = (MainActivity) activity;
        }

        public String getName() {
            return getArguments().getString("name");
        }


        }

       /* private void refreshData() {
            View fragmentView = getView();
            if (fragmentView != null) {
                if (getProfil() != null) {
                    BodyMeasure lastWeightValue = null;
                    BodyMeasure lastWaterValue = null;
                    BodyMeasure lastFatValue = null;
                    BodyMeasure lastMusclesValue = null;

                        lastWeightValue = mDbBodyMeasure.getLastBodyMeasures(BodyPart.WEIGHT, getProfil());

                    if (lastWeightValue != null) {
                        weightEdit.setText(String.valueOf(lastWeightValue.getBodyMeasure()));
                        // update IMC
                        int size = getProfil().getSize();
                        if (size == 0) {
                            imcText.setText("-");
                            imcRank.setText(R.string.no_size_available);
                            ffmiText.setText("-");
                            ffmiRank.setText(R.string.no_size_available);
                        } else {
                            float imcValue = calculateImc(lastWeightValue.getBodyMeasure(), size);
                            imcText.setText(String.format("%.1f", imcValue));
                            imcRank.setText(getImcText(imcValue));
                            if (lastFatValue!=null) {
                                double ffmiValue = calculateFfmi(lastWeightValue.getBodyMeasure(), size, lastFatValue.getBodyMeasure());
                                ffmiText.setText(String.format("%.1f", ffmiValue));
                                if(getProfil().getGender()== Gender.FEMALE)
                                    ffmiRank.setText(getFfmiTextForWomen(ffmiValue));
                                else if(getProfil().getGender()== Gender.MALE)
                                    ffmiRank.setText(getFfmiTextForMen(ffmiValue));
                                else if(getProfil().getGender()== Gender.OTHER)
                                    ffmiRank.setText(getFfmiTextForMen(ffmiValue));
                                else
                                    ffmiRank.setText("no gender defined");
                            } else {
                                ffmiText.setText("-");
                                ffmiRank.setText(R.string.no_fat_available);
                            }

                        }
                    } else {
                        weightEdit.setText("-");
                        imcText.setText("-");
                        imcRank.setText(R.string.no_weight_available);
                        ffmiText.setText("-");
                        ffmiRank.setText(R.string.no_weight_available);
                    }
                        musclesEdit.setText("-");
                }
            }
        }


        public Fragment getFragment() {
            return this;
        }

        @Override
        public void onHiddenChanged(boolean hidden) {
            if (!hidden) refreshData();
        }*/
    //}