package com.example.quickscore;

/*
 BaseActivity sprawdza zapisany temat i inicjuje go
Pozostałe aktywnośći dziedziczą po Base activity
*/


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class BaseActivity extends AppCompatActivity {

    SharedPreferences pref;
    public static boolean COLORED_CELLS_FLAG;
    //static boolean THEME_CHANGED_FLAG = false;

    @Override
    protected void onResume() {
        super.onResume();
        COLORED_CELLS_FLAG = pref.getBoolean("coloredCells", false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    public void updateTheme() {
            //pref =  getSharedPreferences("Settings", MODE_PRIVATE);
            pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if(pref.getString("theme", "").equals("DARK")) setTheme(R.style.QuickScoreDarkTheme);
            if(pref.getString("theme", "").equals("LIGHT")) setTheme(R.style.QuickScoreLightTheme);
    }
}