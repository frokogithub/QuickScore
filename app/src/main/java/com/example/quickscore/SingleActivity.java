package com.example.quickscore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import interfacesPackage.OnChangeIndexListener;
import scoresPackage.ScoreEnd;


public class SingleActivity extends BaseActivity {

    static int ARROWS_IN_END = 3;
    static  int NUMBER_OF_ENDS = 10;
    TextView[] arrTextViews = new TextView[ARROWS_IN_END+1];
    int endIndex = 0;
    public static ScoreEnd[] scoreEnd = new ScoreEnd[NUMBER_OF_ENDS+1];
    private ViewGroup endsDummy;
    private ArrayList<LinearLayout> arrMarkFrameLines = new ArrayList<LinearLayout>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        initEnds();
        initButtons();
        updateMarkFrame(0);
    }






    void initEnds(){
        endsDummy = findViewById(R.id.ends_dummy);



        for(int i=0; i<NUMBER_OF_ENDS+1; i++){


            if(i<NUMBER_OF_ENDS){

                View endHorizontalLine = LayoutInflater.from(this).inflate(R.layout.end_horizontal_line, null);
                View indoorEndView = LayoutInflater.from(this).inflate(R.layout.end_3arrows, null);
                endsDummy.addView(endHorizontalLine);
                endsDummy.addView(indoorEndView);

                scoreEnd[i] = new ScoreEnd(this, i,  indoorEndView, endHorizontalLine, ARROWS_IN_END);
                scoreEnd[i].setOnIndexListener(new OnChangeIndexListener() {
                    @Override
                    public void onChange() {
                        endIndex++;
                        if(endIndex<NUMBER_OF_ENDS) updateMarkFrame(endIndex);
                    }
                });

            }else{
                View endHorizontalLine = LayoutInflater.from(this).inflate(R.layout.end_horizontal_line, null);
                endsDummy.addView(endHorizontalLine);
                scoreEnd[i] = new ScoreEnd(this, endHorizontalLine);
            }
        }
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
            }
        });

        bM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(0);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(2);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(3);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(4);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(5);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(6);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(7);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(8);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(9);
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreEnd[endIndex].enterScore(10);
            }
        });
    } // initButtons



    private void updateMarkFrame(int markIndex){
        if(markIndex>0)              scoreEnd[markIndex-1].setFrameColor(1, R.color.black);
                                     scoreEnd[markIndex].setFrameColor(1,R.color.mark__frame__red);
        if(markIndex<NUMBER_OF_ENDS)  scoreEnd[markIndex+1].setFrameColor(0,R.color.mark__frame__red);
    }



    private void printToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
}


