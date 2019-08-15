package com.example.quickscore;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends BaseActivity {

    private int shootingphase; // 0 Start timera/koniec strzelania, 1 wejście, 2 strzelanie
    private TextView tvTimer;
    ViewGroup timerBackG;
    private int count;

    private final int SETUP_TIME = 3; // (s)
    private final int SHOOTING_TIME = 5; // (s)

    MediaPlayer mp_1_whistle;
    MediaPlayer mp_2_whistles;
    MediaPlayer mp_3_whistles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initTimer();
    }
    private void initTimer(){

        mp_1_whistle = MediaPlayer.create(this, R.raw.whistle_1);
        mp_2_whistles = MediaPlayer.create(this, R.raw.whistles_2);
        mp_3_whistles = MediaPlayer.create(this, R.raw.whistles_3);

        timerBackG = findViewById(R.id.timer_bckgrnd);
        tvTimer = findViewById(R.id.tvTimer);
        timerBackG.setBackgroundColor(getResources().getColor(R.color.black));
        resetTimer();

        timerBackG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shootingphase == 0)counting();
            }
        });
    }

  private void counting(){
      final Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
          @Override
            public void run() {
                runOnUiThread(new Runnable()
              {
                  @Override
                  public void run()
                  {
                      if(count==0){
                          switch (shootingphase){
                              case 0:
                                  timerBackG.setBackgroundColor(getResources().getColor(R.color.black));
                                  count = SETUP_TIME;
                                  mp_2_whistles.start();
                                  shootingphase = 1;
                                  break;
                              case 1:
                                  count = SHOOTING_TIME;
                                  timerBackG.setBackgroundColor(getResources().getColor(R.color.timer_green));
                                  mp_1_whistle.start();
                                  shootingphase = 2;
                                  break;
                              case 2:
                                  timerBackG.setBackgroundColor(getResources().getColor(R.color.timer_red));
                                  mp_3_whistles.start();
                                  timer.cancel();
                                  resetTimer();
                                  return;
                              default:
                                  break;
                          }
                      }
                      tvTimer.setText(String.valueOf(count));
                      count--;
                  }
              });
            }
          }, 0, 1000);
  }

  private void resetTimer(){
        shootingphase = 0;
        tvTimer.setText("START");
        //timerBackG.setBackgroundColor(getResources().getColor(R.color.white_background));
  }

}

