package com.example.innisa.magiceightball3_9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class scrnChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrn_choose2);

        final Button btnLight= (Button)findViewById(R.id.btnLight);
        final Button btnDark= (Button)findViewById(R.id.btnDark);
        final Button btnBandW=(Button)findViewById(R.id.btnBandW);

        //do all onclick listener
        //got errors when i tried to modularize this more
        btnLight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                btnClickListener(btnLight);
            }
        });
        btnDark.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                btnClickListener(btnDark);
            }
        });
        btnBandW.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                btnClickListener(btnBandW);
            }
        });

    }

    //when each button is clicked send the text of that button to scrngame and open it
    public void btnClickListener(Button b){
        Intent i=new Intent(getApplicationContext(),scrnGame.class);
        i.putExtra("key",b.getText());
        startActivity(i);
    }
}
