package com.example.quickscore;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import interfacesPackage.OnSaveAlertItemClik;

public class SaveAlert {

    private OnSaveAlertItemClik saveAlertListener;
    private final EditText etFileName;


    public SaveAlert(Activity activity, String fileName) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_save, viewGroup, false);

        etFileName = dialogView.findViewById(R.id.et_file_name);
        etFileName.setText(fileName);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);
        final AlertDialog saveDialog = builder.create();
        saveDialog.show();

        Button bSave = dialogView.findViewById(R.id.bSave);
        Button bDsave = dialogView.findViewById(R.id.bdont_save);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDialog.dismiss();
                if(saveAlertListener!=null) saveAlertListener.onItemClick(true);
            }
        });

        bDsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDialog.dismiss();
                if(saveAlertListener!=null) saveAlertListener.onItemClick(false);
            }
        });
    }

    public String getFilename(){
        return etFileName.getText().toString();
    }

    public void setOnSaveAlertItemClickListener(OnSaveAlertItemClik listener){
        saveAlertListener = listener;
    }
}
