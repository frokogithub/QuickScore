package com.example.quickscore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Arrays;

import scoresPackage.ScoreEnd;


public class SingleActivity extends BaseActivity {

    int ARROWS_IN_END = 3;
    int NUMBER_OF_ENDS = 10;
    TextView[] arrTextViews = new TextView[ARROWS_IN_END+1];
    int endIndex = 0;
    ScoreEnd[] scoreEnd = new ScoreEnd[NUMBER_OF_ENDS];
    private ViewGroup endsDummy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        initEnds();
        initButtons();
    }

    void initEnds(){
        endsDummy = findViewById(R.id.ends_dummy);

        for(int i=0; i<NUMBER_OF_ENDS; i++){


            if(i<NUMBER_OF_ENDS-1){
                View indoorEndView = new View(this);
                indoorEndView = LayoutInflater.from(this).inflate(R.layout.end_3arrows, null);
                endsDummy.addView(indoorEndView);

                scoreEnd[i] = new ScoreEnd(this, i,  indoorEndView, ARROWS_IN_END);
            }else{
                View indoorEndViewBttm = new View(this);
                indoorEndViewBttm = LayoutInflater.from(this).inflate(R.layout.end_3arrows_bttm, null);
                endsDummy.addView(indoorEndViewBttm);

                scoreEnd[i] = new ScoreEnd(this, i, indoorEndViewBttm, ARROWS_IN_END);
            }
        }
//
    }



    void initButtons(){
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
                scoreEnd[endIndex].enterScore(10);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        bM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(0);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(1);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(2);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(3);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(4);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(5);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(6);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(7);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(8);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(9);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(10);
                if(scoreEnd[endIndex].isFull()) endIndex++;
            }
        });
    } // initButtons


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


