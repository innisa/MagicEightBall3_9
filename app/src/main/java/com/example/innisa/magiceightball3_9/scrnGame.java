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


public class scrnGame extends AppCompatActivity implements SensorEventListener {

    // variables for shake detection
    private static final float SHAKE_THRESHOLD = 3.25f; // m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    private SensorManager mSensorMgr;

    public TextView txtAnswer;
    public ArrayList<String> lstAnswer= new ArrayList<>();
    private static Dictionary myDictionary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the info from the previous screen--use that info to set the theme
        Bundle extras= getIntent().getExtras();
        String value="";
        if(extras!=null)
            value= extras.getString("key");
        else
            value= "Blue and White";

        makeTheme(value);


        setContentView(R.layout.activity_scrn_game);


        //set txtAnswer
        txtAnswer= (TextView) findViewById(R.id.txtAnswer);

        //add to the array list
        addToArrayList(lstAnswer);


        // Get a sensor manager to listen for shakes
        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Listen for shakes
        Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            mSensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    public void makeTheme(String s){
        //set theme based on previous screen (made these themes in style.xml)
        switch(s){
            case "Dark": setTheme(R.style.DarkTheme);
                break;
            case "Light": setTheme(R.style.LightTheme);
                break;
            case "Blue and White": setTheme(R.style.AppTheme);
                break;
            default: setTheme(R.style.AppTheme);
                break;
        }
    }
    public void addToArrayList(ArrayList<String> lst){
        myDictionary = new Dictionary(getApplicationContext(),"answers.txt");
       // ArrayList<String> tempArray= myDictionary.getMyWords();
         lstAnswer=myDictionary.getMyWords();
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
                //Log.d(APP_NAME, "Acceleration is " + acceleration + "m/s^2");---probably useful if i use the accelerometer again

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
