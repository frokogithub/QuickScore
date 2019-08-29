package com.example.quickscore;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import interfacesPackage.OnLoadItemClickListener;

class LoadListAdapter extends BaseAdapter {
    ArrayList<LoadRowData> arrayList;
    Context context;

    OnLoadItemClickListener onLoadItemClickListener;


    private static class ViewHolder {
        TextView fileName;
        ImageView icon;
    }


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
//        LoadRowData loadRowData = arrayList.get(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.load_list_row,  null, true);

            viewHolder.fileName = convertView.findViewById(R.id.file_name);
            viewHolder.icon = convertView.findViewById(R.id.icon);
//            TextView tittle = convertView.findViewById(R.id.file_name);
//            ImageView icon = convertView.findViewById(R.id.icon);
//            tittle.setText(loadRowData.fileName);
//            icon.setImageDrawable(loadRowData.icon);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.fileName.setText(loadRowData.fileName);
        viewHolder.icon.setImageDrawable(loadRowData.icon);
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

    public void setOnLoadClickListener(OnLoadItemClickListener listener){
        onLoadItemClickListener = listener;
    }
}