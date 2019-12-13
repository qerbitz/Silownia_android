package com.example.silownia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.silownia.DAO.DatabaseHelper;
import com.example.silownia.DAO.ExercisesDAO;
import com.example.silownia.Fragments.HistoryFragment;
import com.example.silownia.Fragments.TrainingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    ListView filterList = null;
    Button btnView;

    private static final ExercisesDAO cwiczenia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_training:
                            selectedFragment = new TrainingFragment();
                            break;
                        //case R.id.nav_favorites:
                           // selectedFragment = new FavoritesFragment();
                           // break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };


    public void showData(View view){
        Intent intent = new Intent(getApplicationContext(), HistoryFragment.class);
        startActivity(intent);
    }

    public void addData(View view){
        Intent intent = new Intent(getApplicationContext(), TrainingFragment.class);
        startActivity(intent);
    }






}
