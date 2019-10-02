package com.example.quickscore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import interfacesPackage.OnChangeIndexListener;
import interfacesPackage.OnEraseListener;
import interfacesPackage.OnMenuItemClickListener;
import interfacesPackage.OnSaveAlertItemClik;
import scoresPackage.End;
import scoresPackage.JsonFileUtility;


public class SingleActivity extends BaseActivity {

    private final static int INDOOR_ARROWS_IN_END = 3;
    private final static int OUTDOOR_ARROWS_IN_END = 6;
    private final static int INDOOR_NUMBER_OF_ENDS = 10;
    private final static int OUTDOOR_NUMBER_OF_ENDS = 6;
    private String eventType = "outdoor"; //1 indoor, 2 outdoor,
    private static  int ARROWS_IN_END;
    private static  int NUMBER_OF_ENDS;
    private int activeRow = 0;
    private static End[] end;
    private ViewGroup endsDummy;
    private TextView totalSum;
    private TextView record;
    private ViewGroup insertDummy;
    private int scoringStatus = 0; // 0 przed rozpoczęciem, 1 w trakcie, 2 zakończone TODO: dorobić 0 do 1 i 1 do 0
    static boolean RECREATE_FLAG;
    boolean isSaved = true;
    private JSONObject scoresJSON = null;
    boolean isRefilled = true;

    private static final String TEMP_JSON_FILENAME = "tempJSON";
    private static final String KEY_LOADED_FILENAME = "loadedFileName";
    private static final String KEY_IS_FILE_LOADED = "isFileLoaded";
    private static final String KEY_EVENT_TYPE = "eventType";




    // Starter Pattern
    public static void start(Context context, boolean isFileLoaded, String _loadedFileName) {
        Intent starter = new Intent(context, SingleActivity.class);
        starter.putExtra(KEY_IS_FILE_LOADED, isFileLoaded);
        starter.putExtra(KEY_LOADED_FILENAME,_loadedFileName);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

    }



    @Override
    protected void onResume() {
        super.onResume();

        if(RECREATE_FLAG){
            RECREATE_FLAG = false;
            recreate();
        }

        boolean wereClosedByUser = pref.getBoolean(KEY_PREF_CLOSED_BY_USER, false);
        //zeruję flagę
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putBoolean(KEY_PREF_CLOSED_BY_USER, false);
        prefEditor.apply();



        Intent intent = getIntent();
        String jsonName;

        boolean isFileLoaded = intent.getBooleanExtra(KEY_IS_FILE_LOADED,false);

        if(wereClosedByUser){
            jsonName = intent.getStringExtra(KEY_LOADED_FILENAME);
        }else{
            jsonName = TEMP_JSON_FILENAME;
        }

        System.out.println("kkk closedByUser:  "+wereClosedByUser);
        System.out.println("kkk isLoaded:  "+isFileLoaded);
        System.out.println("kkk jsonName:  "+jsonName);

        boolean loadFromTempFolder = jsonName.equals(TEMP_JSON_FILENAME);
        scoresJSON = new JsonFileUtility(getApplicationContext()).loadJson(jsonName, loadFromTempFolder);
        try {
            if(scoresJSON!=null){
                eventType = scoresJSON.getString(KEY_EVENT_TYPE);
            }else{
                eventType = "outdoor"; // TODO: wziąć z preferences
            }
        }catch (JSONException e){
            e.printStackTrace();
        }


        setInitialState();
        initEnds();

        if((!wereClosedByUser || isFileLoaded) && scoresJSON!=null) fillScores();

        initButtons();
        activateFirstIncompleteEnd();
    }

    private void fillScores(){
        for (int i=0;i<NUMBER_OF_ENDS;i++) {
            String arrayKey = "endScores"+i;
            String emptyCellsKey = "emptyCells" +i;
            int[] tempScoreArray = null;
            int emptyCells = 0;
            tempScoreArray = new int[ARROWS_IN_END];

            try {
                JSONArray jsonArray = scoresJSON.getJSONArray(arrayKey);
                for (int a = 0; a < ARROWS_IN_END; a++) {
                    tempScoreArray[a] = jsonArray.getInt(a);
                }
                emptyCells = scoresJSON.getInt(emptyCellsKey);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(end[i]!=null && tempScoreArray!=null) end[i].fillEnd(tempScoreArray, emptyCells);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        makeJsonFile(TEMP_JSON_FILENAME);
//        Intent intent = getIntent();
//        intent.putExtra(KEY_IS_FILE_LOADED, true);
//        intent.putExtra(KEY_LOADED_FILENAME, TEMP_JSON_FILENAME);
    }


    @Override
    public void onBackPressed() {
        if(!isSaved){
            showSaveAlert("BACK");
        }else{
            //zamknięcie przez użytkownika
            SharedPreferences.Editor prefEditor = pref.edit();
            prefEditor.putBoolean(KEY_PREF_CLOSED_BY_USER, true);
            prefEditor.apply();

            finish();
        }
    }

    private void setInitialState(){
        insertDummy=findViewById(R.id.cl_insert_dummy);
        insertDummy.removeAllViews();
        switch (eventType){
            case "indoor":
                ARROWS_IN_END = INDOOR_ARROWS_IN_END;
                NUMBER_OF_ENDS = INDOOR_NUMBER_OF_ENDS;
                View ins3 = LayoutInflater.from(this).inflate(R.layout.cl_insert_3arr, null);
                insertDummy.addView(ins3);
                break;
            case "outdoor":
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
        record = findViewById(R.id.tv_record);
    }


    void initEnds(){
        endsDummy = findViewById(R.id.ends_dummy);
        end = new End[NUMBER_OF_ENDS+1];

        int endHorizontalLineId=0, endViewId=0;
        switch (eventType){
            case "indoor":
                endHorizontalLineId = R.layout.end_horizontal_line;
                endViewId = R.layout.end_3arrows;
                break;
            case "outdoor":
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
                            if(!isSaved){
                                showSaveAlert("NEW");
                            }else{
                                newBoard();
                            }
                        }
                    }
                });
            }
        });

        Button bInOut =findViewById(R.id.b_in_out_switch);
        bInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (eventType){
                    case "indoor":
                        eventType = "outdoor";
                        break;
                    case "outdoor":
                        eventType = "indoor";
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


    private void showSaveAlert(final String command){
        final String filename = "single\n"+dateString();

        final SaveAlert saveAlert = new SaveAlert(this, filename);
        saveAlert.setOnSaveAlertItemClickListener(new OnSaveAlertItemClik() {
            @Override
            public void onItemClick(boolean choice) {
                if(choice){
                    String eventTypePrefix = "";
                    if(eventType.equals("indoor")) eventTypePrefix = "i";
                    if(eventType.equals("outdoor")) eventTypePrefix = "o";
                    String fullFileName = "s" + eventTypePrefix + saveAlert.getFilename();
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
                newBoard();
                break;
            case "BACK":
                //zamknięcie przez użytkownika
                SharedPreferences.Editor prefEditor = pref.edit();
                prefEditor.putBoolean(KEY_PREF_CLOSED_BY_USER, true);
                prefEditor.apply();
                finish();
                break;

            default:
                break;
        }
    }

    private void enterScore (int score){
        if(scoringStatus < 2){
            end[activeRow].addScore(score);
            isSaved = false;
        }
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
        int total=0;
        for(int i = 0; i< NUMBER_OF_ENDS; i++){//i< activeRow+1
            total += end[i].getSum();
        }
        totalSum.setText(String.valueOf(total));
        if(end[NUMBER_OF_ENDS-1].getSum()>0) checkRecord(total);
//        if(activeRow==NUMBER_OF_ENDS-1) checkRecord(total);
    }

    private void checkRecord(int total){
        record.setText("");
        switch (eventType){
            case "indoor":
                if(total > pref.getInt(KEY_PREF_INDOOR_RECORD,0)){
                    record.setText("NEW\nRECORD!");
                    pref.edit().putInt(KEY_PREF_INDOOR_RECORD,total).apply();
                }
                break;
            case "outdoor":
                if(total > pref.getInt(KEY_PREF_OUTDOOR_RECORD,0)){
                    record.setText("NEW RECORD!");
                    pref.edit().putInt(KEY_PREF_OUTDOOR_RECORD,total).apply();
                }
                break;
            default:
                break;
        }
    }

    private  void newBoard(){
        for(int i=0;i<NUMBER_OF_ENDS;i++){
            end[i].clear();
        }
        unmarkEnd();
        activeRow = 0;
        markEnd();
        totalSum.setText("0");
        scoringStatus = 0;
        record.setText("");
    }


    void makeJsonFile(String filename){

        boolean saveToTempFolder = filename.equals(TEMP_JSON_FILENAME);
//        if(filename.equals(TEMP_JSON_FILENAME)){
//            saveToTempFolder = true;
//        }else{
//            saveToTempFolder = false;
//        }
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(KEY_EVENT_TYPE, eventType);

            for (int endIndex=0;endIndex<NUMBER_OF_ENDS;endIndex++) {
                String jEndScoresKey = "endScores"+endIndex;
                String jEmptyCellsKey = "emptyCells"+endIndex;

                int[] tempArray = end[endIndex].getScores();

                JSONArray jsonEndScores = new JSONArray();
                for (int arrowIndex=0;arrowIndex<ARROWS_IN_END;arrowIndex++) {
                    jsonEndScores.put(tempArray[arrowIndex]);
                }
                jsonObject.put(jEndScoresKey, jsonEndScores);

                int emptyCells = end[endIndex].getEmptyCellsAmount();
                jsonObject.put(jEmptyCellsKey, emptyCells);
            }
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }

        JsonFileUtility jfile = new JsonFileUtility(getApplicationContext());
        jfile.saveJson(jsonObject, filename, saveToTempFolder );
        isSaved = true;
    }




    private void printToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
    private void printLog(String s){
        Log.i("Kroko",s);
    }

}


