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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import interfacesPackage.OnMenuItemClickListener;

public class ShowPopupWindow {

    private Context context;
    private Activity  activity;
    private PopupWindow popupWindow;
    private View popupView;
    private View view;
    private  OnMenuItemClickListener menuListener;
    private String  command;
    private final static int X_OFFSET = -380;
    private final static int Y_OFFSET = -20;


    public ShowPopupWindow(final View view, AppCompatActivity activity) {

        this.activity = activity;
        this.view = view;
        context = activity.getBaseContext();

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
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_menu, null);


        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;


        popupWindow = new PopupWindow(popupView, width, height, true);
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

        Button bNewRound = popupView.findViewById(R.id.b_new_round);
        TextView tvNewRound = popupView.findViewById(R.id.tv_popup_new);
        Button bSecondRound = popupView.findViewById(R.id.b_second_round);
        TextView tvSecondRound = popupView.findViewById(R.id.tv_popup_second);
        Button bTimer = popupView.findViewById(R.id.b_timer);
        TextView tvTimer = popupView.findViewById(R.id.tv_popup_timer);
        Button bSave = popupView.findViewById(R.id.b_save);
        TextView tvSave = popupView.findViewById(R.id.tv_popup_save);
        Button bLoad = popupView.findViewById(R.id.b_load);
        TextView tvLoad = popupView.findViewById(R.id.tv_popup_load);
        Button bBow = popupView.findViewById(R.id.b_bow);
        TextView tvBow = popupView.findViewById(R.id.tv_popup_bow);
        Button bSettings = popupView.findViewById(R.id.b_settings);
        TextView tvSettings = popupView.findViewById(R.id.tv_popup_settings);



        final String activityName = activity.getLocalClassName();
        switch (activityName){
            case "SingleActivity":
                                    bNewRound.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            command = "NEW";
                                            popupWindow.dismiss();
                                            if(menuListener!=null) menuListener.onMenuItemClick(command);
//                                            showSaveDialog();
                                        }
                                    });
                                    bSecondRound.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            popupWindow.dismiss();
                                        }
                                    });
                                    bTimer.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            Intent in = new Intent(context,TimerActivity.class);
                                            in.putExtra("actName", activityName);
                                            activity.startActivity(in);
                                            popupWindow.dismiss();
                                        }
                                    });
                                    bSave.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            popupWindow.dismiss();
                                        }
                                    });

                                    bLoad.setVisibility(View.GONE);
                                    tvLoad.setVisibility(View.GONE);
                                    bBow.setVisibility(View.GONE);
                                    tvBow.setVisibility(View.GONE);

                                    bSettings.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            Intent in = new Intent(context,SettingsActivity.class);
                                            //in.putExtra("actName", activityName);
                                            activity.startActivity(in);
                                            popupWindow.dismiss();
                                        }
                                    });
                                    break;
            case "MatchActivity":
                                    bNewRound.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            command = "NEW";
                                            popupWindow.dismiss();
//                                            showSaveDialog();
                                        }
                                    });

                                    bTimer.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            popupWindow.dismiss();
                                            Intent in = new Intent(context,TimerActivity.class);
                                            in.putExtra("actName", activityName);
                                            activity.startActivity(in);
                                        }
                                    });
                                    bSave.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            command = "SAVE";
                                            popupWindow.dismiss();
                                        }
                                    });

                                    bSecondRound.setVisibility(View.GONE);
                                    tvSecondRound.setVisibility(View.GONE);
                                    bLoad.setVisibility(View.GONE);
                                    tvLoad.setVisibility(View.GONE);
                                    bBow.setVisibility(View.GONE);
                                    tvBow.setVisibility(View.GONE);

                                    bSettings.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            Intent in = new Intent(context,SettingsActivity.class);
                                            //in.putExtra("actName", activityName);
                                            activity.startActivity(in);
                                            popupWindow.dismiss();
                                        }
                                    });
                                    break;
            case "StartActivity":
                                    bTimer.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            popupWindow.dismiss();
                                            Intent in = new Intent(context,TimerActivity.class);
                                            in.putExtra("actName", activityName);
                                            activity.startActivity(in);
                                        }
                                    });
                                    bLoad.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            popupWindow.dismiss();
                                            Intent in = new Intent(context,LoadActivity.class);
                                            in.putExtra("actName", activityName);
                                            activity.startActivity(in);
                                        }
                                    });


                                    bNewRound.setVisibility(View.GONE);
                                    tvNewRound.setVisibility(View.GONE);
                                    bSecondRound.setVisibility(View.GONE);
                                    tvSecondRound.setVisibility(View.GONE);
                                    bSave.setVisibility(View.GONE);
                                    tvSave.setVisibility(View.GONE);
                                    bBow.setVisibility(View.GONE);
                                    tvBow.setVisibility(View.GONE);

                                    bSettings.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            Intent in = new Intent(context,SettingsActivity.class);
                                            //in.putExtra("actName", activityName);
                                            activity.startActivity(in);
                                            popupWindow.dismiss();
                                        }
                                    });
                                    break;


        }

    }

    private void setBackgroundAlfa(float level){
        ConstraintLayout layout_MainMenu = activity.findViewById( R.id.bckgd);
        layout_MainMenu.setAlpha(level);
    }


//    private void showSaveDialog() {
//        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
//        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
//
//        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_save, viewGroup, false);
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setView(dialogView);
//        final AlertDialog saveDialog = builder.create();
//        saveDialog.show();
//
//        Button bSave = dialogView.findViewById(R.id.bSave);
//        Button bDsave = dialogView.findViewById(R.id.bdont_save);
//
//        bSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveDialog.dismiss();
//                printToast("Let's save");
//                if(menuListener!=null) menuListener.onMenuItemClick(command);
//            }
//        });
//
//        bDsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveDialog.dismiss();
//                printToast("Fuck this fuckin' file");
//                if(menuListener!=null) menuListener.onMenuItemClick(command);
//            }
//        });
//    }

    private void printToast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
