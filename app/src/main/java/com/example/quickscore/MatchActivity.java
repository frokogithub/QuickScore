package com.example.quickscore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import interfacesPackage.OnChangeIndexListener;
import interfacesPackage.OnEraseListener;
import interfacesPackage.OnMenuItemClickListener;
import scoresPackage.End;

public class MatchActivity extends BaseActivity {


    private int EventType = 2; //1 indoor, 2 outdoor,
    private static  int ARROWS_IN_END = 3;
    private static  int NUMBER_OF_ENDS = 5;
    private int endIndex = 0;
    private int editedEndIndex;
    private static End[] endA;
    private static End[] endB;
    private ViewGroup AendsDummy;
    private ViewGroup BendsDummy;
    private TextView totalSum;
    private boolean editInProgressFlag = false;
    private ViewGroup insertDummy;
    private TextView AtvTotalScore;
    private TextView BtvTotalScore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);


        setInitialState();
        initEnds();
        initButtons();


    }

    private void setInitialState(){
      AtvTotalScore = findViewById(R.id.A_tv_total_score);
      BtvTotalScore = findViewById(R.id.B_tv_total_score);
      AtvTotalScore.setText("0");
      BtvTotalScore.setText("0");

      endIndex = 0;

    }


    void initEnds(){
        AendsDummy = findViewById(R.id.A_ends_dummy);
        BendsDummy = findViewById(R.id.B_ends_dummy);
        endA = new End[NUMBER_OF_ENDS+1];
        endB = new End[NUMBER_OF_ENDS+1];

//        int endHorizontalLineId=0, endViewId=0;
//        switch (EventType){
//            case 1:
//                endHorizontalLineId = R.layout.end_horizontal_line;
//                endViewId = R.layout.end_3arrows;
//                break;
//            case 2:
//                endHorizontalLineId = R.layout.end_horizontal_line_6arr;
//                endViewId = R.layout.end_6arrows;
//                break;
//            default:
//                break;
//        }
         int AendHorizontalLineId, BendHorizontalLineId;
         int AendViewId, BendViewId;
         AendHorizontalLineId = R.layout.a_end_horizontal_line;
         BendHorizontalLineId = R.layout.b_end_horizontal_line;
         AendViewId = R.layout.a_end_3arrows;
         BendViewId = R.layout.b_end_3arrows;

        for(int i=0; i<NUMBER_OF_ENDS+1; i++){
            if(i<NUMBER_OF_ENDS){

                View AendHorizontalLine = LayoutInflater.from(this).inflate(AendHorizontalLineId, null);
                View BendHorizontalLine = LayoutInflater.from(this).inflate(BendHorizontalLineId, null);
                View AendView = LayoutInflater.from(this).inflate(AendViewId, null);
                View BendView = LayoutInflater.from(this).inflate(BendViewId, null);

                AendsDummy.addView(AendHorizontalLine);
                AendsDummy.addView(AendView);
                BendsDummy.addView(BendHorizontalLine);
                BendsDummy.addView(BendView);

                endA[i] = new End(this, i, AendView, AendHorizontalLine, ARROWS_IN_END);
                endB[i] = new End(this, i, BendView, BendHorizontalLine, ARROWS_IN_END);
//                endB[i].setOnIndexListener(new OnChangeIndexListener() {
//                    @Override
//                    public void onChange() {
//
//                        if(editInProgressFlag){
//                            editInProgressFlag = false;
//                            unmarkEnd(editedEndIndex);
//                            end[endIndex].setEditable(true);
//                        }else{
//                            if (endIndex < NUMBER_OF_ENDS) endIndex++;
//                            unmarkEnd(endIndex-1);
//                        }
//
//                        if(endIndex<NUMBER_OF_ENDS) markEnd(endIndex);
//                        updateTotalSum();
//                    }
//                });
//
//                end[i].setOnEraseListener(new OnEraseListener() {
//                    @Override
//                    public void onErase(int index) {
//                        if(index!=endIndex){
//                            editInProgressFlag = true;
//                            editedEndIndex = index;
//                            if(endIndex<NUMBER_OF_ENDS)unmarkEnd(endIndex);
//                            markEnd(editedEndIndex);
//                            end[endIndex].setEditable(false);
//                            updateTotalSum();
//                        }
//                    }
//                });
            }else{
                View AendHorizontalLine = LayoutInflater.from(this).inflate(AendHorizontalLineId, null);
                View BendHorizontalLine = LayoutInflater.from(this).inflate(BendHorizontalLineId, null);
                AendsDummy.addView(AendHorizontalLine);
                BendsDummy.addView(BendHorizontalLine);
                endA[i] = new End(AendHorizontalLine);
                endB[i] = new End(BendHorizontalLine);
            }
        }
        markEnd(0);
    }//initEnds()



    void initButtons(){

        Button bX=findViewById(R.id.bX);
        Button bM=findViewById(R.id.bM);
        Button b1=findViewById(R.id.b1);
        Button b2=findViewById(R.id.b2);
        Button b3=findViewById(R.id.b3);
        Button b4=findViewById(R.id.b4);
        Button b5=findViewById(R.id.b5);
        Button b6=findViewById(R.id.b6);
        Button b7=findViewById(R.id.b7);
        Button b8=findViewById(R.id.b8);
        Button b9=findViewById(R.id.b9);
        Button b10=findViewById(R.id.b10);

        bX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterScore(11);
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
                enterScore(3);
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



        ImageView ivMenu = findViewById(R.id.iv_menu);
//        ivMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                PopUpMenu popUpMenu = new PopUpMenu();
//                popUpMenu.showPopupWindow(arg0, MatchActivity.this, getApplicationContext());
//                popUpMenu.setOnMenuItemClick(new OnMenuItemClickListener() {
//                    @Override
//                    public void onMenuItemClick(String command) {
//                        if(command.equals("NEW")){
//                            clearEnds();
//                        }
//                    }
//                });
//            }
//        });

        Button bInOut =findViewById(R.id.b_in_out_switch);
        bInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (EventType){
                    case 1:
                        EventType = 2;
                        break;
                    case 2:
                        EventType = 1;
                        break;
                    default:
                        break;
                }
                setInitialState();
                initEnds();
            }
        });

    } // initButtons




    private void enterScore (int score){
//        int activeEndIndex;
//        if(editInProgressFlag){
//            activeEndIndex = editedEndIndex;
//        }else{
//            activeEndIndex = endIndex;
//        }
//        if(activeEndIndex<NUMBER_OF_ENDS)end[activeEndIndex].addScore(score);
    }


    private void markEnd(int index){
        int color = R.color.mark__frame__red;
        endA[index].setFrameColor(true, color);
        endB[index].setFrameColor(true, color);
        if(index<NUMBER_OF_ENDS){
            endA[index+1].setFrameColor(false, color);
            endB[index+1].setFrameColor(false, color);
        }

    }
//
//    private void unmarkEnd(int index){
//        int color = R.color.black;
//        end[index].setFrameColor(true, color);
//        if(index<NUMBER_OF_ENDS)
//            end[index+1].setFrameColor(false, color);
//
//    }
//
//
//    private void updateTotalSum(){
//        int s=0;
//        for(int i=0; i<endIndex ; i++){
//            s += end[i].getSum();
//        }
//        totalSum.setText(String.valueOf(s));
//    }
//
//    private  void clearEnds(){
//        for(int i=0;i<NUMBER_OF_ENDS;i++){
//            end[i].clear();
//            unmarkEnd(i);
//        }
//        endIndex = 0;
//        markEnd(0);
//        totalSum.setText("0");
//    }



    private void printToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
    private void printLog(String s){
        Log.i("Kroko",s);
    }
}

