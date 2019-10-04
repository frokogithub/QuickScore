package com.example.quickscore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class StartActivity extends BaseActivity {

    static boolean THEME_CHANGED_FLAG = false;
    @Override
    protected void onResume() {
        super.onResume();
        if(THEME_CHANGED_FLAG){
            THEME_CHANGED_FLAG = false;
            recreate();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        Button b_Single = findViewById(R.id.b_Single);
        Button b_Match = findViewById(R.id.b_Match);


        b_Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in = new Intent(getApplicationContext(),SingleActivity.class);
//                startActivity(in);
                SingleActivity.start(StartActivity.this, false, "");
            }
        });


        b_Match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent in = new Intent(getApplicationContext(),MatchActivity.class);
//                startActivity(in);
                MatchActivity.start(StartActivity.this, false, "");
            }
        });

        ImageView ivMenu = findViewById(R.id.iv_menu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new ShowPopupWindow(arg0, StartActivity.this);
            }
        });

    }

}
