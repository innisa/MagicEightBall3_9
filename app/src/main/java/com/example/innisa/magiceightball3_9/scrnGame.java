package com.example.innisa.magiceightball3_9;

import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//later create a splash screen
//later read in a document
//later use accelerometer

public class scrnGame extends AppCompatActivity implements SensorEventListener {

    // variables for shake detection
    private static final float SHAKE_THRESHOLD = 3.25f; // m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    private SensorManager mSensorMgr;
    public TextView txtAnswer;
    final ArrayList<String> lstAnswer= new ArrayList<>();
    //Todo test app
    //todo share
    // TODO: fix file path
    /// //todo put stuff on git

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras= getIntent().getExtras();
        String value="";
        if(extras!=null)
            value= extras.getString("key");
        else
            value= "Blue and White";
        //set theme based on previous screen (made these themes in style.xml)
        switch(value){
            case "Dark": setTheme(R.style.DarkTheme);
                break;
            case "Light": setTheme(R.style.LightTheme);
                break;
            case "Blue and White": setTheme(R.style.AppTheme);
                break;
            default: setTheme(R.style.AppTheme);
                break;
        }

        setContentView(R.layout.activity_scrn_game);


        //set txtAnswer
        txtAnswer= (TextView) findViewById(R.id.txtAnswer);
        //txtAnswer.setText(value);
        //add to the array list
        addToArrayList(lstAnswer);

        //create/ set the id of imgBall and txtAnswer
        //final ImageView imgBall= (ImageView) findViewById(R.id.imgBall);


        // Get a sensor manager to listen for shakes
        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Listen for shakes
        Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                //ATYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    public void addToArrayList(ArrayList<String> lst){
        //String filePath =  Path.Combine(Path.GetTempPath(),"ConnectFour.txt");
        //String filePath = System.IO.Path.GetDirectoryName(System.Windows.Forms.Application.ExecutablePath) + "\\ConnectFour.txt";

        //try to read in a rile and put each line in an array list
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("MagicEightBall3_9/answers.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                lst.add(str);
            }
            //if the file not found, put those words in the array list
        } catch (FileNotFoundException e) {
            lst.add("No");
            lst.add("Maybe");
            lst.add("Yes");
            lst.add("It is decisively so");
            lst.add("Probably");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try{in.close();}
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }





    //sensorEventLister methods to override
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                double acceleration = Math.sqrt(Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
                //Log.d(APP_NAME, "Acceleration is " + acceleration + "m/s^2");

                //if there is detected shaking display a random answer
                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
                    int randNum= (int)(Math.random()*lstAnswer.size());
                    txtAnswer.setText(lstAnswer.get(randNum));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ignore
    }
}
