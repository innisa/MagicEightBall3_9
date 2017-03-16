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

    //Todo test app
    //todo share
    //// TODO: fix file path
    //todo splash screen
    //todo maybe show image of back side only when shaken

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrn_game);

        //create an array list ---later read in a document with more answers
        final ArrayList<String> lstAnswer= new ArrayList<>();
        addToArrayList(lstAnswer);
        //lstAnswer.add("No");
        //lstAnswer.add("Yes");
       // lstAnswer.add("Maybe");

        //create/ set the id of imgBall and txtAnswer
        final ImageView imgBall= (ImageView) findViewById(R.id.imgBall);
        txtAnswer= (TextView) findViewById(R.id.txtAnswer);

        // Get a sensor manager to listen for shakes
        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Listen for shakes
        Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                //ATYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        //on click listener for imgBall-----eventually use the accelerometer
        imgBall.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int randNum= (int)(Math.random()*lstAnswer.size()); //check could be infinite or null
                txtAnswer.setText(lstAnswer.get(randNum));
            }
        });

    }

    public void addToArrayList(ArrayList<String> lst){
        //String filePath =  Path.Combine(Path.GetTempPath(),"ConnectFour.txt");
        //String filePath = System.IO.Path.GetDirectoryName(System.Windows.Forms.Application.ExecutablePath) + "\\ConnectFour.txt";

//        Scanner s = new Scanner(new File("filepath"));
//        while (s.hasNextLine()){
//            lst.add(s.next());
//        }
//        s.close();

        BufferedReader in = null;
        try {
            //TODO fix file path
            in = new BufferedReader(new FileReader("C:/Users/kinnis/Documents/ALICE_DOCUMENTS/MagicEightBall3_9/answers.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                lst.add(str);
            }
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

// Stop listening for shakes
          //  mSensorMgr.unregisterListener(this);
//DO I NEED THIS IF SO WHEERE



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

                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
                   // Log.d(APP_NAME, "Shake, Rattle, and Roll");
                    String display= "DID THE ACCELEROMETER DETECT SHAKING?";
                    txtAnswer.setText(display);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ignore
    }
}
