package com.example.quickscore;

/*
W końcowej wersji jako dummy użyć ConstraintLayout
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class PopUpMenu {
    public void showPopupWindow(final View view, final Context context) {


        //Create a View object yourself through inflater
        //LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_menu, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        //Set the location of the window on the screen
        //View parent = inflater.inflate(R.layout.activity_main, null);
        //popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popupWindow.showAsDropDown(view, -350, -20);



        // Kliknięcie poza popup window zamyka je . dummy musi być foccusable i clicable
        ConstraintLayout dummy = popupView.findViewById(R.id.popup);
        dummy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        Button close = popupView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Button doit = popupView.findViewById(R.id.btn);
        doit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }
}