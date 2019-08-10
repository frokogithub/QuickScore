package com.example.quickscore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import interfacesPackage.OnMenuItemClickListener;

public class PopUpMenu {

    private Context context;
    private Activity  activity;
    private PopupWindow popupWindow;
    private View popupView;
    private View view;
    private  OnMenuItemClickListener menuListener;
    private String  command;
    private final static int X_OFFSET = -380;
    private final static int Y_OFFSET = -20;


    public void showPopupWindow(final View view, AppCompatActivity activity, Context context) {

        this.activity = activity;
        this.context = context;
        this.view = view;

        setBackgroundAlfa(0.2f);
        initPopUp();
        initButtons();
    }
    public void setOnMenuItemClick(OnMenuItemClickListener listener){
        menuListener = listener;
    }
    private  void newRound(){
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

    private void initPopUp(){
        //Create a View object yourself through inflater
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_menu, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlfa(1f);
            }
        });

        // Kliknięcie poza popup window zamyka je . dummy musi być foccusable i clicable
        ConstraintLayout dummy = popupView.findViewById(R.id.popup);
        dummy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        popupWindow.showAsDropDown(view, X_OFFSET, Y_OFFSET);
    }

    private void initButtons(){
        Button close = popupView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Button bNewRound = popupView.findViewById(R.id.b_new_round);
        bNewRound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                command = "NEW";
                popupWindow.dismiss();
                showSaveDialog();
            }
        });
    }

    private void setBackgroundAlfa(float level){
        ConstraintLayout layout_MainMenu = activity.findViewById( R.id.bckgd);
        layout_MainMenu.setAlpha(level);
    }


    private void showSaveDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_save, viewGroup, false);
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
                printToast("Let's save");
                if(menuListener!=null) menuListener.onMenuItemClick(command);
            }
        });

        bDsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDialog.dismiss();
                printToast("Fuck this fuckin' file");
                if(menuListener!=null) menuListener.onMenuItemClick(command);
            }
        });


    }


    private void printToast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
