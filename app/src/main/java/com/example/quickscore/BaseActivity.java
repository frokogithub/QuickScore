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