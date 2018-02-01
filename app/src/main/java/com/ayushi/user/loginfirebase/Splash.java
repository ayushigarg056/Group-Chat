package com.ayushi.user.loginfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor edi;
    String logg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp=getSharedPreferences("mydata", Context.MODE_PRIVATE);

        logg=sp.getString("logged","false");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(logg.equals("false")) {
                    Intent in = new Intent(Splash.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }
                else if(logg.equals("true"))
                {
                    Intent in1 = new Intent(Splash.this, Recyler_message.class);
                    startActivity(in1);
                    finish();
                }
            }
        }, 3000);



    }
}
