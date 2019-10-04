package com.example.quickscore;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

class LoadListAdapter extends BaseAdapter {
    private final ArrayList<LoadRowData> arrayList;
    private final Context context;


    public LoadListAdapter(Context context, ArrayList<LoadRowData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LoadRowData loadRowData = arrayList.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.load_list_row,  null, true);

            TextView tittle = convertView.findViewById(R.id.file_name);
            ImageView icon = convertView.findViewById(R.id.icon);
            String subFileName = loadRowData.fileName.substring(2); // wycięcie tagu
            tittle.setText(subFileName);
            icon.setImageDrawable(loadRowData.icon);
        }

        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}