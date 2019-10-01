package scoresPackage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    private final String FOLDERNAME = "QUICKSCORE SAVINGS";
    private final String TEMP_FOLDERNAME = "QUICKSCORE TEMP";

    public JsonFileUtility(Context context){

        this.context = context;
    }

    public void saveJson(JSONObject jsonObject, String filename, boolean saveToTempFolder){
//        String FILENAME = filename;

        String jsonString;

        jsonString = String.valueOf(jsonObject);

        try {
            String folder;
            if(saveToTempFolder){
                folder = context.getFilesDir().getAbsolutePath() + File.separator + TEMP_FOLDERNAME;
            }else{
                folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;
            }
//            String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;

            File subFolder = new File(folder);



            if (!subFolder.exists()) {
                if(subFolder.mkdirs()){
                    Log.d("Files", "Directory creation failed");
                }

            }

            FileOutputStream outputStream = new FileOutputStream(new File(subFolder, filename));

            outputStream.write(jsonString.getBytes());
            outputStream.close();

        } catch (FileNotFoundException e) {
            Log.e("ERROR", e.toString());
        } catch (IOException e) {
            Log.e("ERROR", e.toString());
        }
    }


    public JSONObject loadJson(String fileName, boolean loadFromTempFolder){

        JSONObject jo = null;
        String receivedString = "";

        try {

//            String FILENAME = fileName;

            byte[] bytes = new byte[1024];

            String folder;
            if(loadFromTempFolder){
                folder = context.getFilesDir().getAbsolutePath() + File.separator + TEMP_FOLDERNAME;
            }else{
                folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;
            }

//            String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;

            File subFolder = new File(folder);

            System.out.println("kkk: przed FleInputStream - "+fileName);//**********************************************************************************
            FileInputStream outputStream = new FileInputStream(new File(subFolder, fileName));

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
            if(files.length>0){
                filesNames = new String[files.length];
                for (int i = 0; i < files.length; i++)
                {
                    filesNames[i] = files[i].getName();
                }
            }else{
//            filesNames = {"No saved files"};
                filesNames = new String[1];
                filesNames[0] = "No files";
            }
        }else{
            filesNames = new String[1];
            filesNames[0] = "No files";
        }


        return filesNames;
    }

    public void deleteJfile(String filename){
        String path = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME + File.separator + filename;

        try{
            File fileToDelete = new File(path);
            if(fileToDelete.exists()){
                boolean resault = fileToDelete.delete();
//                if(resault) System.out.println("Deleted file: " + fileToDelete.getName());
            }else{
                System.out.println("Can't delete file: " + fileToDelete.getName());
            }

        }catch (Exception e){
            Log.e("Files", "Exception while deleting file " + e.getMessage());
        }
    }

}
