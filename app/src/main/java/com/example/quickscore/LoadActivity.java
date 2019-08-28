package com.example.quickscore;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import interfacesPackage.OnLoadItemClickListener;
import scoresPackage.JsonFileUtility;

public class LoadActivity extends BaseActivity {


    Drawable icon;
    ListView list;
    String[] filesNames;
    LoadListAdapter loadListAdapter;
    ArrayList<LoadRowData> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);


        list = findViewById(R.id.list);
        icon = getDrawable(R.drawable.arch_icon);

        makeLoadList();
    }

    private void makeLoadList(){
        JsonFileUtility jFileUtil = new JsonFileUtility(getApplicationContext());
        filesNames = jFileUtil.getFilesNames();
        arrayList = new ArrayList<LoadRowData>();
        for (int i=0;i<filesNames.length;i++) {
            arrayList.add(new LoadRowData(filesNames[i], icon));
        }
        loadListAdapter = new LoadListAdapter(this, arrayList);
        loadListAdapter.setOnLoadClickListener(new OnLoadItemClickListener() {
            @Override
            public void onLoadItemClick(int position, boolean isLongClicked) {
                if(isLongClicked){
                    Toast.makeText(getApplicationContext(),"loooong", Toast.LENGTH_SHORT).show();
                    arrayList.remove(position);
//                    list.removeViewAt(position);
                    loadListAdapter.notifyDataSetChanged();
//                    getApplicationContext().deleteFile(filesNames[position]);
                }else{
                    loadFile(filesNames[position]);
                }

//                Toast.makeText(getApplicationContext(),String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        list.setAdapter(loadListAdapter);
    }

    private void loadFile(String fileName){
        JSONObject jsonObject = new JsonFileUtility(getApplicationContext()).loadJson(fileName);
        Toast.makeText(getApplicationContext(),String.valueOf(jsonObject), Toast.LENGTH_SHORT).show();
        //ToDo: do SingleActivity lub MatchActivity
    }

    private void delFile(String fileName){

    }

}
