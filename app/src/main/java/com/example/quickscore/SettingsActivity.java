package com.example.quickscore;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;


public class SettingsActivity extends BaseActivity {

    //SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //settings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();// settings.edit();

        Button bDark = findViewById(R.id.b_dark);
        Button bLight = findViewById(R.id.b_light);


        bDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("theme", "DARK");
                editor.apply();
                recreateActivities();
            }
        });

        bLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("theme", "LIGHT");
                editor.apply();
                recreateActivities();
            }
        });


    }

    public void recreateActivities() {
        StartActivity.THEME_CHANGED_FLAG = true;
        SingleActivity.THEME_CHANGED_FLAG = true;
        MatchActivity.THEME_CHANGED_FLAG = true;
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

