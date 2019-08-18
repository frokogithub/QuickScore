package com.example.quickscore;

/*
 BaseActivity sprawdza zapisany temat i inicjuje go
Pozostałe aktywnośći dziedziczą po Base activity
*/

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);

        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("theme")) {
                    if(pref.getString("theme", "").equals("DARK")) setTheme(R.style.QuickScoreDarkTheme);
                    if(pref.getString("theme", "").equals("LIGHT")) setTheme(R.style.QuickScoreLightTheme);

                    StartActivity.THEME_CHANGED_FLAG = true;
                    SingleActivity.THEME_CHANGED_FLAG = true;
                    MatchActivity.THEME_CHANGED_FLAG = true;
                }
            }
        });


    }
    public void updateTheme() {
        pref =  getSharedPreferences("QuickScorePreferences", MODE_PRIVATE);

        if(pref.getString("theme", "").equals("DARK")) setTheme(R.style.QuickScoreDarkTheme);
        if(pref.getString("theme", "").equals("LIGHT")) setTheme(R.style.QuickScoreLightTheme);



//        if (Utility.getTheme(getApplicationContext()) <= THEME_BLUE) {
//            setTheme(R.style.AppTheme_Blue);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                getWindow().setStatusBarColor(getResources().getColor(R.color.primaryColorDark_blue));
//            }
//        } else if (Utility.getTheme(getApplicationContext()) == THEME_RED) {
//            setTheme(R.style.AppTheme_Red);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                getWindow().setStatusBarColor(getResources().getColor(R.color.primaryColorDark_red));
//            }
//        }
    }
}