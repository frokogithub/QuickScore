package com.example.quickscore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Arrays;


public class SingleActivity extends BaseActivity {

    int ARROWS_IN_END = 6;
    TextView[] arrTextViews = new TextView[ARROWS_IN_END];
    int[] arrScores = new int[ARROWS_IN_END];
    int cellIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        initViews();
    }



    void initViews(){
        Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, bX, bM;

        bX=findViewById(R.id.bX);
        bM=findViewById(R.id.bM);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        b5=findViewById(R.id.b5);
        b6=findViewById(R.id.b6);
        b7=findViewById(R.id.b7);
        b8=findViewById(R.id.b8);
        b9=findViewById(R.id.b9);
        b10=findViewById(R.id.b10);

        bX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(10); //getApplicationContext().deleteDatabase("mojaDB");
            }
        });

        bM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(0);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(2);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(3);;
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(4);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(5);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(6);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(7);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(8);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(9);
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(10);
            }
        });


        //-----------------------------------------------------------
        // wpisuję wszystkie 6 TextView do tablicy
        String viewId;
        int resId;
        for(int i=0; i<ARROWS_IN_END;i++){
            viewId = "tv"+"0"+i;
            resId = getResources().getIdentifier(viewId,"id", getPackageName());
            arrTextViews[i] = findViewById(resId);
        }

    }


    void enterScore(int score){
        if(cellIndex<6){
            arrScores[cellIndex] = score;
            if (cellIndex > 0) prepareArray(arrScores);
            for(int i=0; i<cellIndex+1; i++){
                arrTextViews[i].setText(String.valueOf(arrScores[i]));
            }
            cellIndex++;
        }
    }

    void prepareArray(int[] array){
        if (array == null) return;
        int i = 0;
        int j = array.length - 1;
        int tmp;
        Arrays.sort(array);
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }
}


