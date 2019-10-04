package com.example.quickscore;

import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarUtil {
    public  void setSeekBar(SeekBar mSeekbar, int minVal, int initVal, int maxVal, int intervalVal, final TextView mTextView) {

        int totalCount = (maxVal - minVal) / intervalVal;
        int initProgress = (initVal -minVal) / intervalVal;
        mTextView.setText(String.valueOf(initVal));
        mSeekbar.setMax(totalCount);
        mSeekbar.setProgress(initProgress);
        mSeekbar.setOnSeekBarChangeListener(new CustomSeekBarListener(minVal, maxVal, intervalVal) {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //progress = ((int)Math.round(progress/interval))*interval;
                int val;
                if (interval == totalCount) {
                    val = max;
                } else {
                    val = min + (progress * interval);
                }
                seekBar.setProgress(progress);
                mTextView.setText(String.valueOf(val));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {  }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {   }
        });

    }

    class CustomSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        int min, max, interval;
        int totalCount;
        public CustomSeekBarListener(int min, int max, int interval) {
            this.min = min;
            this.max = max;
            this.interval = interval;
            totalCount= (max - min) / interval;
        }
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) { }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    }
}