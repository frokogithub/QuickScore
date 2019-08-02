package com.example.quickscore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button b_Single = findViewById(R.id.b_Single);
        Button b_Match = findViewById(R.id.b_Match);



        b_Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),SingleActivity.class);
                startActivity(in);
            }
        });


        b_Match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(),MatchActivity.class);
                startActivity(in);
            }
        });

    }
}
