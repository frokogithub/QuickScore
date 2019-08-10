package com.example.quickscore;

/*
W końcowej wersji jako dummy użyć ConstraintLayout
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import interfacesPackage.OnMenuItemClickListener;

public class PopUpMenu {

    private Context context;
    private Activity  activity;
    private PopupWindow popupWindow;
    private View popupView;
    private View view;
    private  OnMenuItemClickListener menuListener;

    public void showPopupWindow(final View view, Activity activity, Context context) {

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

        //Create a window with our parameters
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


        popupWindow.showAsDropDown(view, -380, -20);
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
                popupWindow.dismiss();
                if(menuListener!=null) menuListener.onMenuItemClick("NEW");
            }
        });
    }

    private void setBackgroundAlfa(float level){
        ConstraintLayout layout_MainMenu = activity.findViewById( R.id.bckgd);
        layout_MainMenu.setAlpha(level);
    }

    private void printToast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
