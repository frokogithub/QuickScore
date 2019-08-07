package scoresPackage;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quickscore.R;

import java.util.Arrays;

import interfacesPackage.OnChangeIndexListener;

public class ScoreEnd {

    private Context context;
    private int arrowsInEnd;
    private int cellIndex = 0;
    private TextView[] cellArray;
    private TextView sumTextV;
    private TextView indexTextV;
    private int[] scoreArray;
    private  View view;
    private int index;
    private LinearLayout markLeftLine;
    private LinearLayout markTopLine;
    private LinearLayout markRightLine;
    private LinearLayout markBottomLine;
    private OnChangeIndexListener indexListener;
    private int sum;



    public ScoreEnd(View lastMarkLine){
        markTopLine = lastMarkLine.findViewById(R.id.ll_mark_top_line);
    }

    public ScoreEnd(Context context, int index, View view, View markLine, int arrowsInEnd) {
        this.context = context;
        this.view = view;
        this.index = index;
        this.arrowsInEnd = arrowsInEnd;
        cellArray =  new TextView[arrowsInEnd];
        scoreArray = new int[arrowsInEnd];


        indexTextV = view.findViewById(R.id.tv_index);
        indexTextV.setText(String.valueOf(index+1));

        markTopLine = markLine.findViewById(R.id.ll_mark_top_line);
        markLeftLine = view.findViewById(R.id.ll_mark_line_left);
        markRightLine = view.findViewById(R.id.ll_mark_line_right);


        cellArray =  new TextView[arrowsInEnd];
        String viewId;
        int resId;
        for(int i=0; i<arrowsInEnd; i++){
            viewId = "tv"+i;
            resId = context.getResources().getIdentifier(viewId,"id", context.getPackageName());
            cellArray[i] = view.findViewById(resId);
            cellArray[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    eraseCell(view);
                    return false;
                }
            });
        }

        sumTextV = view.findViewById(R.id.tv_sum);

    }

    private void eraseCell(View cell){
        ((TextView)cell).setText("");
    }

    public void setOnIndexListener(OnChangeIndexListener listener){
        indexListener = listener;
    }

    public void enterScore(int score){

        if(cellIndex <arrowsInEnd){
            scoreArray[cellIndex] = score;

            if (cellIndex > 0) prepareArray(scoreArray);
            for(int i = 0; i< cellIndex +1; i++){
                cellArray[i].setText(String.valueOf(scoreArray[i]));
            }
            cellIndex++;
            if (isFull()){
                showSum();
                if(indexListener != null) indexListener.onChange();
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

    private boolean isFull(){
        if(cellIndex < arrowsInEnd){
            return false;
        }else {
            return true;
        }
    }

    private void showSum(){

        sum=0;
        for(int i=0; i<arrowsInEnd;i++){
            sum+=Integer.parseInt(cellArray[i].getText().toString());
        }
        sumTextV.setText(String.valueOf(sum));
    }

    public int getSum(){
        return sum;
    }


    public void setFrameColor(int whichLines, int color){
        if(whichLines==1){
            markLeftLine.setBackgroundResource(color);
            markRightLine.setBackgroundResource(color);
        }

        markTopLine.setBackgroundResource(color);
    }
}
