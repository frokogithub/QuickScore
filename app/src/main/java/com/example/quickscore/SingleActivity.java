package com.example.quickscore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import interfacesPackage.OnChangeIndexListener;
import interfacesPackage.OnEraseListener;
import interfacesPackage.OnMenuItemClickListener;
import scoresPackage.End;


public class SingleActivity extends BaseActivity {

    private final static int INDOOR_ARROWS_IN_END = 3;
    private final static int OUTDOOR_ARROWS_IN_END = 6;
    private final static int INDOOR_NUMBER_OF_ENDS = 10;
    private final static int OUTDOOR_NUMBER_OF_ENDS = 6;
    private int EventType = 2; //1 indoor, 2 outdoor,
    private static  int ARROWS_IN_END;
    private static  int NUMBER_OF_ENDS;
    private int activeRow = 0;
    private static End[] end;
    private ViewGroup endsDummy;
    private TextView totalSum;
    private ViewGroup insertDummy;
    private int scoringStatus = 0; // 0 przed rozpoczęciem, 1 w trakcie, 2 zakończone TODO: dorobić 0 do 1 i 1 do 0
    static boolean THEME_CHANGED_FLAG = false;
    static boolean CELLS_COLORING_CHANGED_FLAG;

    private int[] tempScoreArray;
    private Bundle bundleScores;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        setInitialState();
        initEnds();
//        activateFirstIncompleteEnd();
        initButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(THEME_CHANGED_FLAG){
            THEME_CHANGED_FLAG = false;
            recreate();
        }
        if(CELLS_COLORING_CHANGED_FLAG){
            CELLS_COLORING_CHANGED_FLAG = false;
            for (int i=0;i<NUMBER_OF_ENDS;i++) {
                end[i].updateCells();
            }
        }

        for (int i=0;i<NUMBER_OF_ENDS;i++) {
            String arrName = "scores"+i;
            String emptyCellsName = "emptyCells" +i;
            tempScoreArray = getIntent().getIntArrayExtra(arrName);
            int emptyCells = getIntent().getIntExtra(emptyCellsName, 6);
            if(end[i]!=null && tempScoreArray!=null) end[i].fillEnd(tempScoreArray, emptyCells);
        }
        activateFirstIncompleteEnd();
    }


    @Override
    protected void onPause() {
        super.onPause();
        for (int i=0;i<NUMBER_OF_ENDS;i++) {
            tempScoreArray = end[i].getScores();
            String arrName = "scores"+i;
            getIntent().putExtra(arrName,tempScoreArray);

            int emptyCells = end[i].getEmptyCellsAmount();
            String emptyCellsName = "emptyCells" +i;
            getIntent().putExtra(emptyCellsName,emptyCells);
        }

    }



    private void setInitialState(){
        insertDummy=findViewById(R.id.cl_insert_dummy);
        insertDummy.removeAllViews();
        switch (EventType){
            case 1:
                ARROWS_IN_END = INDOOR_ARROWS_IN_END;
                NUMBER_OF_ENDS = INDOOR_NUMBER_OF_ENDS;
                View ins3 = LayoutInflater.from(this).inflate(R.layout.cl_insert_3arr, null);
                insertDummy.addView(ins3);
                break;
            case 2:
                ARROWS_IN_END = OUTDOOR_ARROWS_IN_END;
                NUMBER_OF_ENDS = OUTDOOR_NUMBER_OF_ENDS;
                View ins6 = LayoutInflater.from(this).inflate(R.layout.cl_insert_6arr, null);
                insertDummy.addView(ins6);
                break;
            default:
                break;
        }
        activeRow = 0;
        totalSum = findViewById(R.id.tv_total);
        totalSum.setText("0");
    }


    void initEnds(){
        endsDummy = findViewById(R.id.ends_dummy);
        end = new End[NUMBER_OF_ENDS+1];

        int endHorizontalLineId=0, endViewId=0;
        switch (EventType){
            case 1:
                endHorizontalLineId = R.layout.end_horizontal_line;
                endViewId = R.layout.end_3arrows;
                break;
            case 2:
                endHorizontalLineId = R.layout.end_horizontal_line_6arr;
                endViewId = R.layout.end_6arrows;
                break;
            default:
                break;
        }

        for(int i=0; i<NUMBER_OF_ENDS+1; i++){
            if(i<NUMBER_OF_ENDS){

                View endHorizontalLine = LayoutInflater.from(this).inflate(endHorizontalLineId, null);
                View endView = LayoutInflater.from(this).inflate(endViewId, null);

                endsDummy.addView(endHorizontalLine);
                endsDummy.addView(endView);

                end[i] = new End(this, i, endView, endHorizontalLine, ARROWS_IN_END);
                addEndsListeners(i);
            }else{
                View endHorizontalLine = LayoutInflater.from(this).inflate(endHorizontalLineId, null);
                endsDummy.addView(endHorizontalLine);
                end[i] = new End(endHorizontalLine);
            }
        }
    }//initEnds()



    private void addEndsListeners(int i){
        end[i].setOnIndexListener(new OnChangeIndexListener() {
            @Override
            public void onChange() {
                doIfEndIsFull();
            }
        });
        end[i].setOnEraseListener(new OnEraseListener() {
            @Override
            public void onErase(int index) {
                doIfCellErased(index);
            }
        });
    }//addEndsListeners()


    private void doIfEndIsFull(){
        updateTotalSum();
        unmarkEnd();
        if(activeRow < NUMBER_OF_ENDS-1){
            activateFirstIncompleteEnd();
        }else{
            scoringStatus = 2;
        }

    }// doIfEndIsFull()

    private void doIfCellErased(int endIndex){
        if(scoringStatus < 2){
            unmarkEnd();
        }else{
            scoringStatus = 1;
        }

        activeRow = endIndex;
        markEnd();
    }//doIfCellErased()



    private void activateFirstIncompleteEnd(){
        for(int i=0;i<NUMBER_OF_ENDS;i++){
            if(end[i].getEmptyCellsAmount()>0){
                activeRow = i;
                markEnd();
                return;
            }
        }
    }




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
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ShowPopupWindow showPopupWindow = new ShowPopupWindow(arg0, SingleActivity.this);
                showPopupWindow.setOnMenuItemClick(new OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(String command) {
                        if(command.equals("NEW")){
                            clearEnds();
                        }
                    }
                });
            }
        });

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
                activateFirstIncompleteEnd();
            }
        });

    } // initButtons



    private void enterScore (int score){
        if(scoringStatus < 2)end[activeRow].addScore(score);
    }

    private void markEnd(){
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.inner_mark_color, outValue, true);
        int color = outValue.resourceId;
        end[activeRow].setFrameColor(true, color);
        if(activeRow <NUMBER_OF_ENDS) end[activeRow +1].setFrameColor(false, color);
    }

    private void unmarkEnd(){
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.cell_outline_color, outValue, true);
        int color = outValue.resourceId;
        end[activeRow].setFrameColor(true, color);
        if(activeRow<NUMBER_OF_ENDS) {
            end[activeRow + 1].setFrameColor(false, color);
        }
    }


    private void updateTotalSum(){
        int s=0;
        for(int i = 0; i< activeRow+1; i++){
            s += end[i].getSum();
        }
        totalSum.setText(String.valueOf(s));
    }

    private  void clearEnds(){
        for(int i=0;i<NUMBER_OF_ENDS;i++){
            end[i].clear();
            //unmarkEnd();
        }
        unmarkEnd();
        activeRow = 0;
        markEnd();
        totalSum.setText("0");
        scoringStatus = 0;
    }

//    private void fillEnds(){
//        //for (int i=0;i<NUMBER_OF_ENDS;i++) {
//            end[0].fillEnd(tempScoreArray);
//        //}
//
//    }


    private void printToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
    private void printLog(String s){
        Log.i("Kroko",s);
    }
}


