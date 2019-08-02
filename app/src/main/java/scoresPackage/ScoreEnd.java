package scoresPackage;


import android.widget.TextView;

import java.util.Arrays;

public class ScoreEnd {

    private int ARROWS_IN_END = 6;
    private int arrowIndex = 0;
    private TextView[] arrTextV = new TextView[ARROWS_IN_END];
    private TextView sumTextV;
    private int[] arrScores = new int[ARROWS_IN_END];

    public ScoreEnd(int index, TextView[] arrTextV) {
        //this.arrTextV = arrTextV;
        for(int i=0; i<ARROWS_IN_END+1; i++){
            if(i<ARROWS_IN_END){
                this.arrTextV[i] = arrTextV[i];
            }else{
                sumTextV = arrTextV[i];
            }

        }
    }

    public void setTextViews(int index, TextView textView){

    }
    public void enterScore(int score){
        if(arrowIndex <6){
            arrScores[arrowIndex] = score;
            if (arrowIndex > 0) prepareArray(arrScores);
            for(int i = 0; i< arrowIndex +1; i++){
                arrTextV[i].setText(String.valueOf(arrScores[i]));
            }
            arrowIndex++;
            if (arrowIndex==6){
                showSum();
            }

        }
    }

    private void prepareArray(int[] array){
        if (array == null) return;
        int i = 0;
        int j = array.length - 1;
        int tmp;
        Arrays.sort(array);
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public boolean isFull(){
        if(arrowIndex < 6){
            return false;
        }else {
            return true;
        }
    }

    private void showSum(){

        int s=0;
        for(int i=0; i<ARROWS_IN_END;i++){
            s+=Integer.parseInt(arrTextV[i].getText().toString());
        }
        sumTextV.setText(String.valueOf(s));
        int x=0;
    }
}
