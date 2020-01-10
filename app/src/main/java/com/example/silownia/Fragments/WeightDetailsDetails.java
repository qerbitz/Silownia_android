package com.example.silownia.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.silownia.DAO.WeightDAO;
import com.example.silownia.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class WeightDetailsDetails extends Fragment {

    private LineChart mChart = null;
    private WeightDAO wazenie=null;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor = null, cursor2;
    ListView measures_list;

    TextView text_date,text_weight;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bodytracking_details_fragment, container, false);


        measures_list = view.findViewById(R.id.measures_list);
        mChart = view.findViewById(R.id.graphChart);

        text_date = view.findViewById(R.id.text_date);
        text_weight = view.findViewById(R.id.text_reps);

        measures_list.setDivider(null);


        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        wazenie=new WeightDAO(view.getContext());
        sqLiteDatabase=wazenie.getReadableDatabase();
        cursor = wazenie.getAllMeasures();
        cursor2 = wazenie.getAllMeasures();
        Cursor oldCursor = null;


        ArrayList<Entry> yValues = new ArrayList<>();
        int i = 0;
        ArrayList<String> values = new ArrayList<String>();
        values.add("Jan");
        values.add("Feb");
        values.add("Mar");
        values.add("Apr");
        values.add("Jan");
        values.add("Jul");


        if (cursor2.moveToFirst()) {
            do {
                Float waga = cursor2.getFloat(1);
                String data = cursor2.getString(2);
                yValues.add(new Entry(i, waga));
                i++;
            }
            while (cursor2.moveToNext());
        }

      //  if(cursor == null || cursor.getCount() == 0){
       //     measures_list.setAdapter(null);
       // }else{
          //  if(measures_list.getAdapter() == null){
                MeasuresListAdapter mTableAdapter = new MeasuresListAdapter(getContext(),cursor,0);
                measures_list.setAdapter(mTableAdapter);
            //}else{
             //   oldCursor = ((MeasuresListAdapter ) measures_list.getAdapter()).swapCursor(cursor);
             //   if(oldCursor != null)
               //     oldCursor.close();
           // }///
      //  }
        //cursor.close();


        LineDataSet set1 = new LineDataSet(yValues,"");
        set1.setFillAlpha(110);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new MyXAsisValueFormatter(values));


        return view;
    }

    public class MyXAsisValueFormatter implements IAxisValueFormatter{
        private ArrayList mValues;
        public MyXAsisValueFormatter(ArrayList values){
            this.mValues=values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return (String) mValues.get((int) value);
        }
    }

}
