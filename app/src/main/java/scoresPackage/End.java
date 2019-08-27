package scoresPackage;


import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickscore.BaseActivity;
import com.example.quickscore.R;

import java.util.Arrays;

import interfacesPackage.OnChangeIndexListener;
import interfacesPackage.OnEraseListener;
import interfacesPackage.OnScoreBoardClickListener;

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
    private OnScoreBoardClickListener scoreClicklistener;
    private int sum=0;
    private int emptyCells;




    public End(View lastMarkLine) {
        markTopLine = lastMarkLine.findViewById(R.id.ll_mark_top_line);
    }//End(1)

    public End(Context context, int index, View view, View markLine, int arrowsInEnd) {
        this.context = context;
        this.view = view;
        this.index = index;
        this.arrowsInEnd = arrowsInEnd;
        cellArray =  new TextView[arrowsInEnd];
        scoreArray = new int[arrowsInEnd];
        emptyCells = arrowsInEnd;


        indexTextV = view.findViewById(R.id.tv_index);
        if(indexTextV!=null)indexTextV.setText(String.valueOf(index+1));

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
                   if(!((TextView)view).getText().toString().equals("")) eraseCell(view);
                   return false;
                }
            });
            cellArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(scoreClicklistener!=null) scoreClicklistener.onScoreBoardClick();
                }
            });

            scoreArray[i]=-1;
        }

        sumTextV = view.findViewById(R.id.tv_sum);

    }//End(2)


    public void setOnIndexListener(OnChangeIndexListener listener){
        indexListener = listener;
    }
    public  void setOnEraseListener(OnEraseListener listener){
        eraseListener = listener;
    }

    public void setOnScoreBoardClickListener(OnScoreBoardClickListener listener){
        scoreClicklistener = listener;
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
        scoreArray[erasedCellIndex] = -1;
        emptyCells++;

        prepareArray(scoreArray);
        for(int i=0; i<emptyCells; i++){
            cellArray[arrowsInEnd-emptyCells].setText(null);
            colorizeCells(arrowsInEnd-emptyCells, false);
        }
        updateCells();

        sum = 0;
        sumTextV.setText(null);

        if(eraseListener!=null) eraseListener.onErase(index);
    }//eraseCell


    public void addScore(int score){
            scoreArray[arrowsInEnd-emptyCells] = score;
            emptyCells--;
            if (emptyCells < arrowsInEnd) prepareArray(scoreArray);
            updateCells();
    }

    public void fillEnd(int[] endScores, int _emptyCells){
        scoreArray = endScores;
//        for (int i=0;i<arrowsInEnd;i++) {
//            if(scoreArray[i] != -1) emptyCells--;
//        }
        emptyCells = _emptyCells;
        prepareArray(scoreArray);
        updateCells();
    }


    public void updateCells(){
        for(int i = 0; i<arrowsInEnd-emptyCells; i++){
                if(scoreArray[i]==-1){
                    cellArray[i].setText(null);
                }else if(scoreArray[i]==11){
                    cellArray[i].setText("X");
                }else if(scoreArray[i]==0){
                    cellArray[i].setText("M");
                }else{
                   cellArray[i].setText(String.valueOf(scoreArray[i]));
                }
                if(BaseActivity.COLORED_CELLS_FLAG) colorizeCells(i, true);
        }

        if (isFull()){
                showSum();
                if(indexListener != null) indexListener.onChange();
        }
    }

    private void colorizeCells(int index, boolean hasColor){
        TypedValue tV = new TypedValue();
        Resources.Theme theme = context.getTheme();

        if(hasColor){
            if(scoreArray[index]==11 || scoreArray[index]==10 ||  scoreArray[index]==9) theme.resolveAttribute(R.attr.cell_yellow_color, tV, true);
            if(scoreArray[index]==8 || scoreArray[index]==7)                            theme.resolveAttribute(R.attr.cell_red_color, tV, true);
            if(scoreArray[index]==6 || scoreArray[index]==5)                            theme.resolveAttribute(R.attr.cell_blue_color, tV, true);
            if(scoreArray[index]==4 || scoreArray[index]==3)                            theme.resolveAttribute(R.attr.cell_black_color, tV, true);
            if(scoreArray[index]==2 || scoreArray[index]==1 || scoreArray[index]==0)    theme.resolveAttribute(R.attr.cell_white_color, tV, true);
            if(scoreArray[index]==-1)                                                   theme.resolveAttribute(R.attr.background_color, tV, true);
        }else{
            theme.resolveAttribute(R.attr.background_color, tV, true);
        }
        cellArray[index].setBackgroundColor(tV.data);
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
        return emptyCells==0;
    }

    private void showSum(){
        sum=0;
        for(int i=0; i<arrowsInEnd;i++){
            if(scoreArray[i]>-1){
                if(scoreArray[i]==11){
                    sum += 10;
                }else{
                    sum += scoreArray[i];
                }
            }
        }
        sumTextV.setText(String.valueOf(sum));
    }

    public int getSum(){
        return sum;
    }

    public int getEmptyCellsAmount(){
        return emptyCells;
    }

    public int[] getScores(){
        return scoreArray;
    }


    public void setFrameColor(boolean threeLines, int color){
        if(threeLines){
            markLeftLine.setBackgroundResource(color);
            markRightLine.setBackgroundResource(color);
        }
        markTopLine.setBackgroundResource(color);
    }


    public void clear(){
        for(int i=0;i<arrowsInEnd;i++){
            scoreArray[i]=-1;
            cellArray[i].setText(null);
            emptyCells = arrowsInEnd;
            sum = 0;
            sumTextV.setText(null);
            colorizeCells(i, false);
        }
    }


    private void printToast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
