package com.example.quickscore;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import scoresPackage.JsonFileUtility;

public class LoadActivity extends BaseActivity {


    Context context;
    ListView list;
    String[] filesNames;
    LoadListAdapter loadListAdapter;
    ArrayList<LoadRowData> arrayList;
    int editedPosition;
    boolean isLongClickAchieved = false;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Load");
            actionBar.setElevation(0);
            actionBar.show();
        }

        context = this;

        JsonFileUtility jFileUtil = new JsonFileUtility(context);
        filesNames = jFileUtil.getFilesNames();
        if(filesNames[0].equals("No files")){
            showNoFilesAlert();
        }else{
            makeLoadList();
        }
    }

    private void makeLoadList(){

        Drawable iconSingle, iconMatch;
        iconSingle = getDrawable(R.drawable.load_single_ic);
        iconMatch = getDrawable(R.drawable.load_mach_ic);
        arrayList = new ArrayList<>();

        for (String fileName:filesNames) {
            // wybór ikony Single, Match. "tempJSON" pomija
            if(fileName.charAt(0)=='s'){
                arrayList.add(new LoadRowData(fileName, iconSingle));
            }else if(fileName.charAt(0)=='m'){
                arrayList.add(new LoadRowData(fileName, iconMatch));
            }
        }
        loadListAdapter = new LoadListAdapter(this, arrayList);
        list = findViewById(R.id.list);
        list.setAdapter(loadListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(isLongClickAchieved){
                    isLongClickAchieved = false;
                }else{
                    startFileInActivity(filesNames[position]);
                }
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                isLongClickAchieved = true;
                editedPosition = position;
                showDeleteAlert();
                return false;
            }
        });
    }

//    private void startFileInActivity(String fileName){
//        JSONObject jsonObject = new JsonFileUtility(context).loadJson(fileName);
//        Intent intent = null;
//        if(fileName.charAt(0)=='s'){
//            intent = new Intent(getApplicationContext(),SingleActivity.class);
//            if(fileName.charAt(1)=='o'){
//                intent.putExtra("eventType", "outdoor");
//            }else if(fileName.charAt(1)=='i'){
//                intent.putExtra("eventType", "indoor");
//            }
//        }else if(fileName.charAt(0)=='m'){
//            intent = new Intent(getApplicationContext(),MatchActivity.class);
//            if(fileName.charAt(1)=='r'){
//                intent.putExtra("division", "recurve");
//            }else if(fileName.charAt(1)=='c'){
//                intent.putExtra("division", "compound");
//            }
//        }
//
//        intent.putExtra("loadedFileName", fileName);
//        if(intent!=null) startActivity(intent);
//        finish();
//    }


    private void startFileInActivity(String fileName){
        if(fileName.charAt(0)=='s'){
            SingleActivity.start(LoadActivity.this,true, fileName);
        }else if(fileName.charAt(0)=='m'){
            MatchActivity.start(LoadActivity.this,true, fileName);
        }

        finish();
    }






    private void delete(String fileName){
        JsonFileUtility jsonFileUtility = new JsonFileUtility(getApplicationContext());
        jsonFileUtility.deleteJfile(fileName);
    }


    private void showDeleteAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(LoadActivity.this).create();
        alertDialog.setTitle("Delete file?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String fileName = arrayList.get(editedPosition).fileName;
                        arrayList.remove(editedPosition);
                        if(arrayList.size()==0){
                            list.requestLayout();
                            loadListAdapter.notifyDataSetChanged();
                            showNoFilesAlert();
                        }else{
                            loadListAdapter = new LoadListAdapter(context, arrayList);
                            list.setAdapter(loadListAdapter);
                        }
                        delete(fileName);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    private void showNoFilesAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(LoadActivity.this).create();
        alertDialog.setTitle("No files");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        alertDialog.show();
    }
}
