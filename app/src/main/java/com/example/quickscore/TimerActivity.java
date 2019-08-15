package com.example.quickscore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends BaseActivity {

    private int shootingphase=0; // 0 Start timera/koniec strzelania, 1 wejście, 2 strzelanie
    private TextView tvTimer;
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initTimer();
    }
    private void initTimer(){
        ViewGroup timerBackG = findViewById(R.id.timer_bckgrnd);
        tvTimer = findViewById(R.id.tvTimer);

        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                count = 120;
                Timer T=new Timer();
                T.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                tvTimer.setText(String.valueOf(count));
                                count--;
                            }
                        });
                    }
                }, 0, 1000);
            }
        });
    }

}

