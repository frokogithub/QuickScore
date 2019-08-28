package scoresPackage;

import android.content.Context;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class JsonFileUtility {

    private  Context context;
    private final String FOLDERNAME = "quickscore_savings";

    public JsonFileUtility(Context context){

        this.context = context;
    }

    public void saveJson(JSONObject jsonObject, String filename){
        String FILENAME = filename;

        String jsonString;

        jsonString = String.valueOf(jsonObject);

        try {
            String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;

            File subFolder = new File(folder);



            if (!subFolder.exists()) {
                if(subFolder.mkdirs()){
                    Log.d("Files", "Directory creation failed");
                }

            }

            FileOutputStream outputStream = new FileOutputStream(new File(subFolder, FILENAME));

            outputStream.write(jsonString.getBytes());
            outputStream.close();

        } catch (FileNotFoundException e) {
            Log.e("ERROR", e.toString());
        } catch (IOException e) {
            Log.e("ERROR", e.toString());
        }
    }


    public JSONObject loadJson(String fileName){

        JSONObject jo = null;
        String receivedString = "";

        try {

            String FILENAME = fileName;

            byte[] bytes = new byte[1024];

            String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;

            File subFolder = new File(folder);

            FileInputStream outputStream = new FileInputStream(new File(subFolder, FILENAME));

            outputStream.read(bytes);
            outputStream.close();

            receivedString = new String(bytes);

        } catch (FileNotFoundException e) {
            Log.e("ERROR", e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        try {
            jo = new JSONObject(receivedString);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jo;
    }

    public void showFiles(){

        String path = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "Plik:" + files[i].getName());
        }
    }

    public String[] getFilesNames(){

        String path = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;
        File directory = new File(path);
        File[] files = directory.listFiles();
        String[] filesNames;
        if(files!=null){
            filesNames = new String[files.length];
            for (int i = 0; i < files.length; i++)
            {
                filesNames[i] = files[i].getName();
            }
        }else{
//            filesNames = {"No saved files"};
            filesNames = new String[1];
            filesNames[0] = "No saved files";
        }

        return filesNames;
    }

    private String dateString(){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd_HH.mm");
        //Calendar calendar = Calendar.getInstance();
        //Date now = calendar.getTime();

        Date dt = new Date();
        return sd.format(dt);
    }
}
