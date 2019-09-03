package com.example.quickscore;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import interfacesPackage.OnChangeIndexListener;
import interfacesPackage.OnEraseListener;
import interfacesPackage.OnMenuItemClickListener;
import interfacesPackage.OnSaveAlertItemClik;
import interfacesPackage.OnScoreBoardClickListener;
import scoresPackage.End;
import scoresPackage.JsonFileUtility;

public class MatchActivity extends BaseActivity {


    private String division = "recurve";
    private static  int ARROWS_IN_END = 3;
    private static  int NUMBER_OF_ENDS = 5;
    private int activeRow = 0;
    private int activeArcher;
    private  End[] endA;
    private  End[] endB;
    private ViewGroup endsDummyA;
    private ViewGroup endsDummyB;
    private ViewGroup outerMarkA;
    private ViewGroup outerMarkB;
    private ViewGroup parent;
    private TextView tvTotalScoreA;
    private TextView tvTotalScoreB;
    private TextView tvArcherA;
    private TextView tvArcherB;
    private int scoringStatus = 0;
    private String archerAName = "Archer A";
    private String archerBName = "Archer B";
    boolean isSaved = true;
    ConstraintLayout rootView;

    static boolean RECREATE_FLAG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
//
//        setInitialState();
//        initEnds();
//        initButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(RECREATE_FLAG){
            RECREATE_FLAG = false;
            recreate();
        }



        Intent intent = getIntent();
        if(intent.hasExtra("division")) division = intent.getStringExtra("division");

        setInitialState();
        initEnds();

        fillScores();

        initButtons();
        activateFirstIncompleteEnd(true, true);
    }

    private void fillScores(){

        Intent intent = getIntent();
        JSONObject jsonObject = null;
        if(intent.hasExtra("loadedJsonObject")){
            try {
                jsonObject = new JSONObject(intent.getStringExtra("loadedJsonObject"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        for (int i=0;i<NUMBER_OF_ENDS;i++) {
            String arrayKeyA = "endScoresA"+i;
            String arrayKeyB = "endScoresB"+i;
            String emptyCellsKeyA = "emptyCellsA" +i;
            String emptyCellsKeyB = "emptyCellsB" +i;
            int[] tempScoreArrayA = null;
            int[] tempScoreArrayB = null;
            int emptyCellsA = 0;
            int emptyCellsB = 0;

            if(intent.hasExtra("loadedJsonObject")){
                tempScoreArrayA = new int[ARROWS_IN_END];
                tempScoreArrayB = new int[ARROWS_IN_END];
                try {
                    JSONArray jsonArrayA = jsonObject.getJSONArray(arrayKeyA);
                    JSONArray jsonArrayB = jsonObject.getJSONArray(arrayKeyB);
                    for (int a=0; a<ARROWS_IN_END; a++) {
                        tempScoreArrayA[a] = jsonArrayA.getInt(a);
                        tempScoreArrayB[a] = jsonArrayB.getInt(a);
                    }
                    emptyCellsA = jsonObject.getInt(emptyCellsKeyA);
                    emptyCellsB = jsonObject.getInt(emptyCellsKeyB);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }else{
                if(intent.hasExtra(arrayKeyA)) tempScoreArrayA = intent.getIntArrayExtra(arrayKeyA);
                if(intent.hasExtra(arrayKeyB)) tempScoreArrayB = intent.getIntArrayExtra(arrayKeyB);
                if(intent.hasExtra(emptyCellsKeyA)) emptyCellsA = intent.getIntExtra(emptyCellsKeyA, 6);
                if(intent.hasExtra(emptyCellsKeyB)) emptyCellsB = intent.getIntExtra(emptyCellsKeyB, 6);
            }
            if(endA[i]!=null && tempScoreArrayA!=null) endA[i].fillEnd(tempScoreArrayA, emptyCellsA);
            if(endB[i]!=null && tempScoreArrayB!=null) endB[i].fillEnd(tempScoreArrayB, emptyCellsB);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = getIntent();
        int [] tempScoreArrayA, tempScoreArrayB;
        for (int i=0;i<NUMBER_OF_ENDS;i++) {
            tempScoreArrayA = endA[i].getScores();
            tempScoreArrayB = endB[i].getScores();
            String arrNameA = "endScoresA"+i;
            String arrNameB = "endScoresB"+i;
            intent.putExtra(arrNameA,tempScoreArrayA);
            intent.putExtra(arrNameB,tempScoreArrayB);

            int emptyCellsA = endA[i].getEmptyCellsAmount();
            int emptyCellsB = endB[i].getEmptyCellsAmount();
            String emptyCellsNameA = "emptyCellsA"+i;
            String emptyCellsNameB = "emptyCellsB"+i;
            intent.putExtra(emptyCellsNameA,emptyCellsA);
            intent.putExtra(emptyCellsNameB,emptyCellsB);
        }
    }
    @Override
    public void onBackPressed() {
        if(!isSaved){
            showSaveAlert("BACK");
        }else{
            finish();
        }
    }




    private void setInitialState(){

        rootView = findViewById(R.id.bckgd);

        endsDummyA = findViewById(R.id.A_ends_dummy);
        endsDummyB = findViewById(R.id.B_ends_dummy);
        endsDummyA.removeAllViews();
        endsDummyB.removeAllViews();

        tvTotalScoreA = findViewById(R.id.A_tv_total_score);
        tvTotalScoreB = findViewById(R.id.B_tv_total_score);
        tvTotalScoreA.setText("0");
        tvTotalScoreB.setText("0");

        tvArcherA = findViewById(R.id.tv_archer_A);
        tvArcherB = findViewById(R.id.tv_archer_B);

        outerMarkA = findViewById(R.id.A_outer_mark);
        outerMarkB = findViewById(R.id.B_outer_mark);
        outerMarkB.setVisibility(View.INVISIBLE);
    }//setInitialState()


    void initEnds(){
        endA = new End[NUMBER_OF_ENDS+1];
        endB = new End[NUMBER_OF_ENDS+1];

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

                endsDummyA.addView(AendHorizontalLine);
                endsDummyA.addView(AendView);
                endsDummyB.addView(BendHorizontalLine);
                endsDummyB.addView(BendView);

                endA[i] = new End(this, i, AendView, AendHorizontalLine, ARROWS_IN_END);
                endB[i] = new End(this, i, BendView, BendHorizontalLine, ARROWS_IN_END);

                addEndsListeners(i);
            }else{
                View AendHorizontalLine = LayoutInflater.from(this).inflate(AendHorizontalLineId, null);
                View BendHorizontalLine = LayoutInflater.from(this).inflate(BendHorizontalLineId, null);
                endsDummyA.addView(AendHorizontalLine);
                endsDummyB.addView(BendHorizontalLine);
                endA[i] = new End(AendHorizontalLine);
                endB[i] = new End(BendHorizontalLine);
            }
        }
    }//initEnds()

    private void addEndsListeners(int i){
        endA[i].setOnIndexListener(new OnChangeIndexListener() {
            @Override
            public void onChange() {
                doIfEndIsFull();
            }
        });
        endB[i].setOnIndexListener(new OnChangeIndexListener() {
            @Override
            public void onChange() {
                doIfEndIsFull();
            }
        });

        endA[i].setOnEraseListener(new OnEraseListener() {
            @Override
            public void onErase(int index) {
                doIfCellErased(0, index);
            }
        });

        endB[i].setOnEraseListener(new OnEraseListener() {
            @Override
            public void onErase(int index) {
                doIfCellErased(1, index);
            }
        });
        endA[i].setOnScoreBoardClickListener(new OnScoreBoardClickListener() {
            @Override
            public void onScoreBoardClick() {
                unmarkEnd();
                activateFirstIncompleteEnd(true, false);
            }
        });

        endB[i].setOnScoreBoardClickListener(new OnScoreBoardClickListener() {
            @Override
            public void onScoreBoardClick() {
                unmarkEnd();
                activateFirstIncompleteEnd(false, true);
            }
        });
    }//addEndsListeners()

    private void doIfEndIsFull(){
        if(endA[activeRow].isFull() && endB[activeRow].isFull()){
            updateTotalScore();
        }
        unmarkEnd();
        if(scoringStatus<2)activateFirstIncompleteEnd(true, true);
    }// doIfEndIsFull()

    private void doIfCellErased(int archerIndex, int endIndex){
        if(scoringStatus < 2){
            unmarkEnd();
        }else{
            scoringStatus = 1;
        }
        setActiveArcher(archerIndex);
        activeRow = endIndex;
        markEnd();
    }//doIfCellErased()


    private void activateFirstIncompleteEnd(boolean searchA, boolean searchB){
        for(int i=0;i<NUMBER_OF_ENDS;i++){
            if(searchA && endA[i].getEmptyCellsAmount()>0){
                setActiveArcher(0);
                activeRow = i;
                markEnd();
                return;
            }
            if(searchB && endB[i].getEmptyCellsAmount()>0){
                setActiveArcher(1);
                activeRow = i;
                markEnd();
                return;
            }
        }
    }


    private  void setActiveArcher(int archer){
        switch (archer){
            case 0:
                activeArcher = 0;
                break;
            case 1:
                activeArcher = 1;
                break;
            default:
                break;
        }
    }

    void initButtons(){

        Button bX=findViewById(R.id.bX);
        if(division.equals("recurve")) bX.setText("");
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

        if(division.equals("compound")) bX.setOnClickListener(new View.OnClickListener() {
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
                final ShowPopupWindow showPopupWindow = new ShowPopupWindow(arg0, MatchActivity.this);
                showPopupWindow.setOnMenuItemClick(new OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(String command) {
                        switch (command){
                            case "NEW":
                                if(!isSaved) showSaveAlert(command);
//                                clearEnds();
                                break;
                            case "TIMER":

                                break;
                            case "SAVE":
                                printToast("Save start");
                                break;
                            case "SETTINGS":
                                printToast("Settings start");
                                break;
                        }
//                        if(command.equals("NEW")){
//                            clearEnds();
//                        }
                    }
                });
            }
        });
    } // initButtons

    private void enterScore (int score){
        if(scoringStatus<2){
            switch (activeArcher){
                case 0:
                    endA[activeRow].addScore(score);
                    break;
                case 1:
                    endB[activeRow].addScore(score);
                    break;
                default:
                    break;
            }
            isSaved = false;
        }
    }


    private void markEnd(){
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.inner_mark_color, outValue, true);
        int color = outValue.resourceId;

        switch (activeArcher){
            case 0:
                outerMarkA.setVisibility(View.VISIBLE);
                endA[activeRow].setFrameColor(true, color);
                if(activeRow <NUMBER_OF_ENDS) endA[activeRow +1].setFrameColor(false, color);
                break;
            case 1:
                outerMarkB.setVisibility(View.VISIBLE);
                endB[activeRow].setFrameColor(true, color);
                if(activeRow <NUMBER_OF_ENDS) endB[activeRow +1].setFrameColor(false, color);
                break;
            default:
                break;
        }
    }

    private void unmarkEnd(){

        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.cell_outline_color, outValue, true);
        int color = outValue.resourceId;

        outerMarkA.setVisibility(View.INVISIBLE);
        outerMarkB.setVisibility(View.INVISIBLE);
        endA[activeRow].setFrameColor(true, color);
        endB[activeRow].setFrameColor(true, color);
        if(activeRow<NUMBER_OF_ENDS) {
            endA[activeRow + 1].setFrameColor(false, color);
            endB[activeRow + 1].setFrameColor(false, color);
        }
    }


    private void updateTotalScore(){
//        int s=0;
//        for(int i=0; i<activeRow ; i++){
//            s += end[i].getSum();
//        }
//        totalSum.setText(String.valueOf(s));
        int scoreA = 0, scoreB = 0;
        for(int i=0;i<NUMBER_OF_ENDS;i++){
            if(endA[i].isFull() && endB[i].isFull()){
                if(endA[i].getSum() > endB[i].getSum()){
                    scoreA += 2;
                }else if (endB[i].getSum() > endA[i].getSum()){
                    scoreB += 2;
                }else{
                    scoreA++;
                    scoreB++;
                }
            }
        }
        tvTotalScoreA.setText(String.valueOf(scoreA));
        tvTotalScoreB.setText(String.valueOf(scoreB));

        if(scoreA==5 && scoreB==5){
            scoringStatus = 2;
            unmarkEnd();
            printToast("shoot OFF");
        }
        if(scoreA>=6 || scoreB>=6){
            scoringStatus = 2;
            unmarkEnd();
            printToast("Match End");
        }


    }

    private  void clearEnds(){
        for(int i=0;i<NUMBER_OF_ENDS;i++){
            endA[i].clear();
            endB[i].clear();
            unmarkEnd();
        }
        activateFirstIncompleteEnd(true, true);
        tvTotalScoreA.setText("0");
        tvTotalScoreB.setText("0");
        outerMarkB.setVisibility(View.INVISIBLE);
        scoringStatus = 0;
    }

    private void showSaveAlert(final String command){
        final String filename = "match\n"+dateString();
        final SaveAlert saveAlert = new SaveAlert(this, filename);
        saveAlert.setOnSaveAlertItemClickListener(new OnSaveAlertItemClik() {
            @Override
            public void onItemClick(boolean choice) {
                if(choice){
                    String eventTypePrefix = "";
                    if(division.equals("recurve")) eventTypePrefix = "r";
                    if(division.equals("compound")) eventTypePrefix = "c";
                    String fullFileName = "m" + eventTypePrefix + saveAlert.getFilename();
                    makeJsonFile(fullFileName);
                    postSaveAlert(command);
                }else{
                    printToast("nie sejwuj");
                    postSaveAlert(command);
                }
            }
        });
    }//showSaveAlert()

    private String dateString(){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd_HH.mm");
        Date dt = new Date();
        return sd.format(dt);
    }//dateString()

    private void postSaveAlert(String command){
        switch (command){
            case "NEW":
                isSaved = true;
                clearEnds();
                break;
            case "BACK":
                finish();
                break;

            default:
                break;
        }
    }

    void makeJsonFile(String filename){

        JSONObject jsonObject = new JSONObject();
        try {
            for (int endIndex=0;endIndex<NUMBER_OF_ENDS;endIndex++) {
                String jEndScoresKeyA = "endScoresA"+endIndex;
                String jEndScoresKeyB = "endScores"+endIndex;
                String jEmptyCellsKeyA = "emptyCellsA"+endIndex;
                String jEmptyCellsKeyB = "emptyCells"+endIndex;

                int[] tempArrayA = endA[endIndex].getScores();
                int[] tempArrayB = endB[endIndex].getScores();

                JSONArray jsonEndScoresA = new JSONArray();
                JSONArray jsonEndScoresB = new JSONArray();
                for (int arrowIndex=0;arrowIndex<ARROWS_IN_END;arrowIndex++) {
                    jsonEndScoresA.put(tempArrayA[arrowIndex]);
                    jsonEndScoresB.put(tempArrayB[arrowIndex]);
                }
                jsonObject.put(jEndScoresKeyA, jsonEndScoresA);
                jsonObject.put(jEndScoresKeyB, jsonEndScoresB);

                int emptyCellsA = endA[endIndex].getEmptyCellsAmount();
                int emptyCellsB = endB[endIndex].getEmptyCellsAmount();
                jsonObject.put(jEmptyCellsKeyA, emptyCellsA);
                jsonObject.put(jEmptyCellsKeyB, emptyCellsB);
            }
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }

        JsonFileUtility jfile = new JsonFileUtility(getApplicationContext());
        jfile.saveJson(jsonObject, filename);
        isSaved = true;
    }

    private void printToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
    private void printLog(String s){
        Log.i("Kroko",s);
    }

}

