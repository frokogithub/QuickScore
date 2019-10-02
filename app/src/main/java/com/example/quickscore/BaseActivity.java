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
    public static final String KEY_PREF_THEME = "theme";
    public static final String KEY_PREF_COLORED_CELLS = "colored_cells";
    public static final String KEY_PREF_APP_VERSION = "app_version";
    public static final String KEY_PREF_OUTDOOR_RECORD = "outdoor_record";
    public static final String KEY_PREF_INDOOR_RECORD = "indoor_record";
    public static final String KEY_PREF_PREPARE_TIME = "prepare_time";
    public static final String KEY_PREF_INDOOR_TIME = "indoor_time";
    public static final String KEY_PREF_OUTDOOR_TIME = "outdoor_time";
    public static final String KEY_PREF_CLOSED_BY_USER_Single = "closed_by_user_single";
    public static final String KEY_PREF_CLOSED_BY_USER_Match = "closed_by_user_match";


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
        COLORED_CELLS_FLAG = pref.getBoolean(KEY_PREF_COLORED_CELLS, false);
    }

    public void updateTheme() {
            if(pref.getString(KEY_PREF_THEME, "").equals("DARK")) setTheme(R.style.QuickScoreDarkTheme);
            if(pref.getString(KEY_PREF_THEME, "").equals("LIGHT")) setTheme(R.style.QuickScoreLightTheme);
    }

    private void checkFirstRun() {
        int currentVersion = BuildConfig.VERSION_CODE;
        int savedVersion = pref.getInt(KEY_PREF_APP_VERSION, -1);
        if (savedVersion == -1) firstRunPreferencesSetup();
        if (currentVersion > savedVersion) pref.edit().putInt(KEY_PREF_APP_VERSION, currentVersion).apply();
    }

    private void firstRunPreferencesSetup(){
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(KEY_PREF_THEME, "LIGHT");
        prefEditor.putInt(KEY_PREF_OUTDOOR_RECORD, 0);
        prefEditor.putInt(KEY_PREF_INDOOR_RECORD, 0);
        prefEditor.putBoolean(KEY_PREF_COLORED_CELLS, false);
        prefEditor.putInt(KEY_PREF_PREPARE_TIME, 10);
        prefEditor.putInt(KEY_PREF_INDOOR_TIME, 120);
        prefEditor.putInt(KEY_PREF_OUTDOOR_TIME, 240);
        prefEditor.putBoolean(KEY_PREF_CLOSED_BY_USER_Single, false);
        prefEditor.putBoolean(KEY_PREF_CLOSED_BY_USER_Match, false);
        prefEditor.apply();
    }
}