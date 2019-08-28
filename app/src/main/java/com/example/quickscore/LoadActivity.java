package com.example.quickscore;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class LoadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        final ListView list = findViewById(R.id.list);
        final Drawable icon = getDrawable(R.drawable.arch_icon);

        ArrayList<LoadRowData> arrayList = new ArrayList<LoadRowData>();
        arrayList.add(new LoadRowData("jeden", icon));
        arrayList.add(new LoadRowData("dwa", icon));
        arrayList.add(new LoadRowData("trzy", icon));
        arrayList.add(new LoadRowData("cztery", icon));
        arrayList.add(new LoadRowData("pięć", icon));
        LoadListAdapter loadListAdapter = new LoadListAdapter(this, arrayList);
        list.setAdapter(loadListAdapter);
    }
}
