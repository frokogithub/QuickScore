package com.example.quickscore;

/*
 BaseActivity sprawdza zapisany temat i inicjuje go
Pozostałe aktywnośći dziedziczą po Base activity
*/


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

    SharedPreferences pref;
    public static boolean COLORED_CELLS_FLAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        checkFirstRun();
        updateTheme();
        getSupportActionBar().hide();

    }

    @Override
    protected void onResume() {
        super.onResume();
        COLORED_CELLS_FLAG = pref.getBoolean("coloredCells", false);
    }

    public void updateTheme() {
            //pref =  getSharedPreferences("Settings", MODE_PRIVATE);
//            pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if(pref.getString("theme", "").equals("DARK")) setTheme(R.style.QuickScoreDarkTheme);
            if(pref.getString("theme", "").equals("LIGHT")) setTheme(R.style.QuickScoreLightTheme);
    }

    private void checkFirstRun() {
        int currentVersion = BuildConfig.VERSION_CODE;
        int savedVersion = pref.getInt("version_code", -1);
        if (savedVersion == -1) firstRunPreferencesSetup();//Toast.makeText(getApplicationContext(), String.valueOf(BuildConfig.VERSION_CODE), Toast.LENGTH_SHORT).show();
        if (currentVersion > savedVersion) pref.edit().putInt("version_code", currentVersion).apply();
    }

    private void firstRunPreferencesSetup(){
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString("theme", "LIGHT");
        prefEditor.putInt("outdoor_record", 0);
        prefEditor.putInt("indoor_record", 0);
        prefEditor.putBoolean("coloredCells", false);
        prefEditor.putInt("prepare_time", 10);
        prefEditor.putInt("indoor_time", 120);
        prefEditor.putInt("outdoor_time", 240);
        prefEditor.apply();
    }
}