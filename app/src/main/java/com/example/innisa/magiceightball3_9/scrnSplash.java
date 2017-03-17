package com.example.innisa.magiceightball3_9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class scrnSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrn_splash);

        //open screen game after 3 seconds
        new Timer().schedule(new TimerTask(){
            public void run() {
                scrnSplash.this.runOnUiThread(new Runnable() {
                    public void run() {
                       // startActivity(new Intent(this,))
                        //startActivity(new Intent(this, scrnGame.class));
                        Intent intent = new Intent(getApplicationContext(),scrnChoose.class);
                        startActivity(intent);

                    }
                });
            }
        }, 3000);
    }
}
