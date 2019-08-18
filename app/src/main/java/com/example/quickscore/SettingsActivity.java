package com.example.quickscore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;

public class SettingsActivity extends BaseActivity {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button bDark = findViewById(R.id.b_dark);
        Button bLight = findViewById(R.id.b_light);
        sharedpreferences = getSharedPreferences("QuickScorePreferences", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        bDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("theme", "DARK");
                editor.apply();
                recreateActivity();
            }
        });

        bLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("theme", "LIGHT");
                editor.apply();
                recreateActivity();
            }
        });


    }

    public void recreateActivity() {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

