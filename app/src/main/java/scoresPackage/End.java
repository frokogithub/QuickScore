package scoresPackage;


import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickscore.R;

import java.util.Arrays;

import interfacesPackage.OnChangeIndexListener;
import interfacesPackage.OnEraseListener;

public class End {

    private Context context;
    private int arrowsInEnd;
    private TextView[] cellArray;
    private TextView sumTextV;
    private TextView indexTextV;
    private int[] scoreArray;
    private  View view;
    private int index;
    private LinearLayout markLeftLine;
    private LinearLayout markTopLine;
    private LinearLayout markRightLine;
    private OnChangeIndexListener indexListener;
    private OnEraseListener eraseListener;
    private int sum;
    private boolean isEditable = false;
    private int emptyCells = 3;




    public End(View lastMarkLine){
        markTopLine = lastMarkLine.findViewById(R.id.ll_mark_top_line);
    }//End(1)

    public End(Context context, int index, View view, View markLine, int arrowsInEnd) {
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
                    if(isEditable)eraseCell(view);
                    return false;
                }
            });
        }

        sumTextV = view.findViewById(R.id.tv_sum);
    }//End(2)


    public void setOnIndexListener(OnChangeIndexListener listener){
        indexListener = listener;
    }
    public  void setOnEraseListener(OnEraseListener listener){
        eraseListener = listener;
    }


    private void eraseCell(View cell){
        try {
            Vibrator vib = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
            if(vib.hasVibrator()) vib.vibrate(100);
        }
        catch (Exception e){
            return;
        }

        String s = cell.getTag().toString();
        int erasedCellIndex = Integer.parseInt(s);
        scoreArray[erasedCellIndex] = 0;
        emptyCells++;

        prepareArray(scoreArray);
        for(int i=0; i<emptyCells; i++){
            cellArray[arrowsInEnd-emptyCells].setText(null);
        }
        updateCells(false);

        sum = 0;
        sumTextV.setText(null);

        if(eraseListener!=null) eraseListener.onErase(index);
    }//eraseCell


    public void addScore(int score){
            scoreArray[arrowsInEnd-emptyCells] = score;
            emptyCells--;
            //if (cellIndex > 0) prepareArray(scoreArray);
            if (emptyCells < 3) prepareArray(scoreArray);
            updateCells(true);

    }


    private void updateCells(boolean scoreEntered){

            for(int i = 0; i<arrowsInEnd-emptyCells; i++){
                if(scoreArray[i]==11){
                    cellArray[i].setText("X");
                }else if(scoreArray[i]==0){
                    cellArray[i].setText("M");
                }else{
                    cellArray[i].setText(String.valueOf(scoreArray[i]));
                }
            }

            if (isFull()){
                showSum();
                if(indexListener != null) indexListener.onChange();
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
        return emptyCells==0;
    }

    private void showSum(){
        sum=0;
        for(int i=0; i<arrowsInEnd;i++){
            if(scoreArray[i]==11){
                sum += 10;
            }else{
                sum += scoreArray[i];
            }
        }
        sumTextV.setText(String.valueOf(sum));
    }

    public int getSum(){
        return sum;
    }


    public void setFrameColor(boolean threeLines, int color){
        if(threeLines){
            markLeftLine.setBackgroundResource(color);
            markRightLine.setBackgroundResource(color);
        }
        markTopLine.setBackgroundResource(color);
    }

    public void setEditable(boolean state){
        isEditable = state;
    }


    private void printToast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
