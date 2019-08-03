package scoresPackage;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.quickscore.R;

import java.util.Arrays;
import java.util.Comparator;

public class ScoreEnd {

    private int arrowsInEnd;
    private int cellIndex = 0;
    TextView[] cellArray;// = new TextView[];
    private TextView sumTextV;
    private int[] scoreArray;// = new int[arrowsInEnd];
    private View view;
    private int index;



    public ScoreEnd(Context context, int index, View view, int arrowsInEnd) {
        this.view = view;
        this.index = index;
        this.arrowsInEnd = arrowsInEnd;
        cellArray =  new TextView[arrowsInEnd];
        scoreArray = new int[arrowsInEnd];

        TextView tv_index = view.findViewById(R.id.tv_index);
        tv_index.setText(String.valueOf(index+1));

        cellArray =  new TextView[arrowsInEnd];
        String viewId;
        int resId;
        for(int i=0; i<arrowsInEnd; i++){
            viewId = "tv"+i;
            resId = context.getResources().getIdentifier(viewId,"id", context.getPackageName());
//                arrTextViews[i] = findViewById(resId);
            cellArray[i] = view.findViewById(resId);
        }

        sumTextV = view.findViewById(R.id.tv_sum);

    }

    public void doit(){
//        TextView t = view.findViewById(R.id.tv_index);
//        t.setText(String.valueOf(index+1));
    }

    public void setTextViews(int index, TextView textView){

    }
    public void enterScore(int score){

        if(cellIndex <arrowsInEnd){
            scoreArray[cellIndex] = score;

            if (cellIndex > 0) prepareArray(scoreArray);
            for(int i = 0; i< cellIndex +1; i++){
                cellArray[i].setText(String.valueOf(scoreArray[i]));
            }
            cellIndex++;
            if (cellIndex ==arrowsInEnd){
                showSum();
            }

        }
    }

    private void prepareArray(int[] array){

        if (array == null) return;
        Arrays.sort(array);

        int i = 0;
        int j = array.length - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public boolean isFull(){
        if(cellIndex < arrowsInEnd){
            return false;
        }else {
            return true;
        }
    }

    private void showSum(){

        int s=0;
        for(int i=0; i<arrowsInEnd;i++){
            s+=Integer.parseInt(cellArray[i].getText().toString());
        }
        sumTextV.setText(String.valueOf(s));
        int x=0;
    }
}
