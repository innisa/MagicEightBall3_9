package com.example.innisa.magiceightball3_9;

/**
 * Created by innisa on 3/17/2017.
 */

//class from mrs kelly /andrew to read in a file
import android.app.Activity;
        import android.content.Context;
        import android.content.res.AssetManager;


        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;

public class Dictionary extends Activity {
    private ArrayList<String> myWords = new ArrayList<>();

    public Dictionary (Context myContext, String fileName) {
        if (fileName.equals(""))
            manuallyFillWords();
        else
            readTheWords(myContext,fileName);
    }

    private void readTheWords(Context myContext, String fName){
        BufferedReader myReader = null;
        AssetManager assets = myContext.getAssets();
        try{
            InputStream is = assets.open(fName);
            int howManyWords;
            myReader = new BufferedReader(new InputStreamReader(is));
            howManyWords = countWords(is);
            int wordIndex=0;
            String word = "";

            while (myReader.ready()&&wordIndex<howManyWords){
                word = myReader.readLine();
                if(!word.equals("")){
                    myWords.add(word);
                }
            }
            myReader.close();
        } catch (IOException e){
            manuallyFillWords();
        }

    }
    public ArrayList<String> getMyWords(){
        return myWords;
    }


    private int countWords(InputStream fileSteam){
        int numWords=0;
        int intLetter;
        char letter;
        try {
            fileSteam.mark(999999);
            intLetter = fileSteam.read();
            while (intLetter != -1){
                letter = (char) intLetter;
                if (letter == '\n')
                    numWords++;

                intLetter = fileSteam.read();
            }
            fileSteam.reset();
        }
        catch (IOException e){ numWords=0; }
        return  numWords;
    }
    private void manuallyFillWords(){
        myWords.add("Yes");
        myWords.add("No");
          myWords.add("Ask again later");
          myWords.add("Seems likely");
          myWords.add("Probably");
          myWords.add("It is certain");
          myWords.add("It is decidedly so");
          myWords.add("Without a doubt");
    }
}