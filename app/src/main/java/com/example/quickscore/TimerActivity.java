package com.example.quickscore;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends BaseActivity {

    private int countingState; // 0 Start timera, 1 przygotowanie, 2 strzelanie, 3 ostrzeżenie, koniec strzelania
    private TextView tvCountdown;
    private TextView tvTapScreen;
    private ViewGroup timerBackG;
    private Button bReset;
    private Button bPause;
    private Timer timer;
    private int count;

    private final int SETUP_TIME = 5; // (s)
    private final int SHOOTING_TIME = 10; // (s)
    private final int WARNING_TIME = 3; // (s)

    private SoundPool soundPool;
    private int sound1_id;
    private int sound2_id;
    private int sound3_id;
    private final float volume = 0.5f;

    private boolean running = true;

    private  String activityName;


    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPool.release();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null) timer.cancel();
    }

    private void initTimer(){

        Intent intent = getIntent();
        activityName = intent.getStringExtra("actName");

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        sound1_id = soundPool.load(this, R.raw.whistle_1, 1);
        sound2_id = soundPool.load(this, R.raw.whistle_2, 1);
        sound3_id = soundPool.load(this, R.raw.whistle_3, 1);

        timerBackG = findViewById(R.id.timer_bckgrnd);
        tvCountdown = findViewById(R.id.tv_countdown);
        tvTapScreen = findViewById(R.id.tv_timer_tap_screen);
        bPause = findViewById(R.id.b_timer_pause);
        bReset = findViewById(R.id.b_timer_reset);

        timerBackG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countingState == 0)counting();
                if(countingState == 4) resetTimer();
            }
        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running){
                    timer.cancel();
                    running = false;
                }else{
                    counting();
                    running = true;
                }
            }
        });
        resetTimer();
    }//initTimer()

  private void counting(){
      timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
          @Override
            public void run() {
                runOnUiThread(new Runnable()
              {
                  @Override
                  public void run()
                  {
                      switch (countingState){
                          case 0:
                              bPause.setVisibility(View.VISIBLE);
                              bReset.setVisibility(View.VISIBLE);
                              tvCountdown.setVisibility(View.VISIBLE);
                              tvTapScreen.setVisibility(View.INVISIBLE);
                              soundPool.play(sound2_id, volume, volume, 1, 0, 1f);
                              count = SETUP_TIME;
                              countingState = 1;
                              break;
                          case 1:
                              if(count==0){
                                  timerBackG.setBackgroundColor(getResources().getColor(R.color.timer_green));
                                  soundPool.play(sound1_id, volume, volume, 1, 0, 1f);
                                  count = SHOOTING_TIME;
                                  countingState = 2;
                              }
                              break;
                          case 2:
                              if(count == WARNING_TIME){
                                  timerBackG.setBackgroundColor(getResources().getColor(R.color.timer_yellow));
                                  countingState = 3;
                              }
                              break;
                          case 3:
                              if(count==0){
                                  timerBackG.setBackgroundColor(getResources().getColor(R.color.timer_red));
                                  timer.cancel();
                                  tvCountdown.setText("");
                                  soundPool.play(sound3_id, volume, volume, 1, 0, 1f);
                                  bPause.setVisibility(View.INVISIBLE);
                                  bReset.setVisibility(View.INVISIBLE);
                                  countingState = 4;
                                  if(activityName.equals("SingleActivity") || activityName.equals("MatchActivity")) finish();
                                  return;
                              }
                              break;
                          default:
                              break;
                      }
                      tvCountdown.setText(String.valueOf(count));
                      count--;
                  }
              });
            }
      }, 0, 1000);
  }

  private void resetTimer(){
        if(timer!=null) timer.cancel();
        timerBackG.setBackgroundColor(getResources().getColor(R.color.timer_red));
        bPause.setVisibility(View.INVISIBLE);
        bReset.setVisibility(View.INVISIBLE);
        tvCountdown.setVisibility(View.INVISIBLE);
        tvTapScreen.setVisibility(View.VISIBLE);
        countingState = 0;
  }
}

