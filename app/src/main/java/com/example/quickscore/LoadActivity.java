package com.example.quickscore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import interfacesPackage.OnLoadItemClickListener;
import scoresPackage.JsonFileUtility;

public class LoadActivity extends BaseActivity {


    Drawable icon;
    ListView list;
    String[] filesNames;
    LoadListAdapter loadListAdapter;
    ArrayList<LoadRowData> arrayList;
    int editedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);


//        list = findViewById(R.id.list);
        icon = getDrawable(R.drawable.arch_icon);

        makeLoadList();
    }

    private void makeLoadList(){
        JsonFileUtility jFileUtil = new JsonFileUtility(getApplicationContext());
        filesNames = jFileUtil.getFilesNames();
        arrayList = new ArrayList<>();

        for (int i=0;i<filesNames.length;i++) {
            arrayList.add(new LoadRowData(filesNames[i], icon));
        }
        loadListAdapter = new LoadListAdapter(this, arrayList);
        list = findViewById(R.id.list);
        list.setAdapter(loadListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                editedPosition = position;
                showDeleteAlert();
//                arrayList.remove(position);
//                list.requestLayout();
//                loadListAdapter.notifyDataSetChanged();
//                Toast.makeText(getApplicationContext(),String.valueOf(position), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void loadFile(String fileName){
        JSONObject jsonObject = new JsonFileUtility(getApplicationContext()).loadJson(fileName);
        Toast.makeText(getApplicationContext(),String.valueOf(jsonObject), Toast.LENGTH_SHORT).show();
        //ToDo: do SingleActivity lub MatchActivity
    }

    private void delFile(){
        String fileName = filesNames[editedPosition];
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
                        arrayList.remove(editedPosition);
                        list.requestLayout();
                        loadListAdapter.notifyDataSetChanged();
                        delFile();
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
}
