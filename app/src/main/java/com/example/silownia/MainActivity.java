package com.example.silownia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.silownia.Fragments.HistoryFragment;
import com.example.silownia.Fragments.TrainingFragment;
import com.example.silownia.Others.CustomAdapter;
import com.example.silownia.Others.MyData;
import com.example.silownia.models.DataModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    public static View.OnClickListener myOnClickListener;

    int ajdi_workout = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i]
            ));
        }


        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

            Bundle args = new Bundle();
            ajdi_workout = recyclerView.getChildPosition(v)+1;
            args.putInt("ajdi_training", ajdi_workout);


            TrainingFragment fragment = new TrainingFragment();
            fragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            recyclerView.setVisibility(View.GONE);

        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_training:
                            selectedFragment = new TrainingFragment();
                            Bundle args = new Bundle();
                            args.putInt("ajdi_training", ajdi_workout);
                            selectedFragment.setArguments(args);
                            recyclerView.setVisibility(View.GONE);
                            break;
                        //case R.id.nav_favorites:
                           // selectedFragment = new FavoritesFragment();
                           // break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            recyclerView.setVisibility(View.GONE);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };








}
