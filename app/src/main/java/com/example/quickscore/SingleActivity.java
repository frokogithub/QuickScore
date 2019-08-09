package com.example.quickscore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import interfacesPackage.OnChangeIndexListener;
import interfacesPackage.OnEraseListener;
import scoresPackage.End;


public class SingleActivity extends BaseActivity {

    private int shootingType = 2; //1 indoor, 2 outdoor
    private static   int ARROWS_IN_END;
    private static  int NUMBER_OF_ENDS;
    private int endIndex = 0;
    private int editedEndIndex;
    private static End[] end;
    private ViewGroup endsDummy;
    private TextView totalSum;
    private boolean editInProgressFlag = false;
    private ViewGroup insertDummy;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);






        insertDummy=findViewById(R.id.cl_insert_dummy);

        View ins3 = LayoutInflater.from(this).inflate(R.layout.cl_insert_3arr, null);



        setState();
        initEnds();
        initButtons();
        markEnd(0);
        end[0].setEditable(true);

        totalSum = findViewById(R.id.tv_total_text);
    }

    private void setState(){
        insertDummy.removeAllViews();
        switch (shootingType){
            case 1:
                ARROWS_IN_END = 3;
                NUMBER_OF_ENDS = 10;
                View ins3 = LayoutInflater.from(this).inflate(R.layout.cl_insert_3arr, null);
                insertDummy.addView(ins3);
                break;
            case 2:
                ARROWS_IN_END = 6;
                NUMBER_OF_ENDS = 6;
                View ins6 = LayoutInflater.from(this).inflate(R.layout.cl_insert_6arr, null);
                insertDummy.addView(ins6);
                break;
            default:
                break;
        }

    }

    private void chooseLayout(){

    }




    void initEnds(){
        endsDummy = findViewById(R.id.ends_dummy);
        end = new End[NUMBER_OF_ENDS+1];

        int endHorizontalLineId=0, endViewId=0;
        switch (shootingType){
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
//


                View endHorizontalLine = LayoutInflater.from(this).inflate(endHorizontalLineId, null);
                View endView = LayoutInflater.from(this).inflate(endViewId, null);

                endsDummy.addView(endHorizontalLine);
                endsDummy.addView(endView);

                end[i] = new End(this, i, endView, endHorizontalLine, ARROWS_IN_END);
                end[i].setOnIndexListener(new OnChangeIndexListener() {
                    @Override
                    public void onChange() {

                        if(editInProgressFlag){
                            editInProgressFlag = false;
                            unmarkEnd(editedEndIndex);
                            setEditableEnds();
                        }else{
                            endIndex++;
                            unmarkEnd(endIndex-1);
                        }

                        if(endIndex<NUMBER_OF_ENDS) markEnd(endIndex);
                        updateTotalSum();
                        end[endIndex].setEditable(true);
                    }
                });

                end[i].setOnEraseListener(new OnEraseListener() {
                    @Override
                    public void onErase(int index) {
                        if(index!=endIndex){
                            editInProgressFlag = true;
                            editedEndIndex = index;
                            unmarkEnd(endIndex);
                            markEnd(editedEndIndex);
                            updateTotalSum();
                            setEditableEnds();
                        }

                    }
                });
            }else{
                View endHorizontalLine = LayoutInflater.from(this).inflate(endHorizontalLineId, null);
                endsDummy.addView(endHorizontalLine);
                end[i] = new End(endHorizontalLine);
            }
        }
    }//initEnds()

    private  void setEditableEnds(){
        if(editInProgressFlag){
            for(int i=0; i<NUMBER_OF_ENDS; i++){
                if(i==editedEndIndex){
                    end[i].setEditable(true);
                }else{
                    end[i].setEditable(false);
                }
            }
        }else{
            for(int i=0; i<NUMBER_OF_ENDS; i++){
                if(i<endIndex){
                    end[i].setEditable(true);
                }else{
                    end[i].setEditable(false);
                }
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
                PopUpMenu popUpMenu = new PopUpMenu();
                popUpMenu.showPopupWindow(arg0, getApplicationContext());
            }
        });

        Button bInOut =findViewById(R.id.b_in_out_switch);
        bInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (shootingType){
                    case 1:
                        shootingType = 2;
                        break;
                    case 2:
                        shootingType = 1;
                        break;
                    default:
                        break;
                }
                setState();
                initEnds();
                markEnd(0);
                end[0].setEditable(true);
            }
        });

    } // initButtons


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if(event.getAction()==MotionEvent.ACTION_DOWN){
//            switch(v.getId()){
//                case R.id.bX:
//
//                    enterScore(11);
//                    break;
//                case R.id.b10:
//                    enterScore(10);
//                    break;
//                case R.id.b9:
//                    enterScore(9);
//                    break;
//                case R.id.b8:
//                    enterScore(8);
//                    break;
//            }
//            v.setPressed(true);
//        }else if(event.getAction()==MotionEvent.ACTION_UP){
//            v.setPressed(false);
//            v.performClick();
//        }
//
//        return true;
//    }



    private void enterScore (int score){
        int activeEndIndex;
        if(editInProgressFlag){
            activeEndIndex = editedEndIndex;
        }else{
            activeEndIndex = endIndex;
        }
        end[activeEndIndex].addScore(score);
    }



    private void markEnd(int index){
        int color = R.color.mark__frame__red;
        end[index].setFrameColor(true, color);
        if(index<NUMBER_OF_ENDS)
            end[index+1].setFrameColor(false, color);
    }

    private void unmarkEnd(int index){
        int color = R.color.black;
        end[index].setFrameColor(true, color);
        if(index<NUMBER_OF_ENDS)
            end[index+1].setFrameColor(false, color);

    }


    private void updateTotalSum(){
        int s=0;
        for(int i=0; i<endIndex ; i++){
            s += end[i].getSum();
        }
        totalSum.setText(String.valueOf(s));
        int x=0;
    }

    private void printToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
    private void printLog(String s){
        Log.i("Kroko",s);
    }
}


