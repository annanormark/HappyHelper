package com.mikaela.hhtimer;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Study.OnFragmentInteractionListener, Rest.OnFragmentInteractionListener {

    static boolean active = false;
    private long newT = 16000;
    private long newTR = 80000;
    private long AmountT = 2000*60*60; //2h
    public int timeSpended = 0;
    GifView gifView;
    Button menubtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**** Get the time set in Timer-menu ****/
        Bundle Intent = getIntent().getExtras();
        if(Intent != null) {
            newT = Intent.getLong("Milli");
            newTR = Intent.getLong("MilliR");
            AmountT = Intent.getLong("Amount");
        }

        /**** Makes the happy helper and the timer ****/
        gifView = (GifView) findViewById(+R.id.gif_view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Study study = new Study();
        fragmentManager.beginTransaction().replace(R.id.mainContainer, study).commit();

        /***** Menu-button click ******/
        menubtn = (Button) findViewById(R.id.menubtn);
        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }

        });

    }

    public Long getTime(){
        return newT;
    }

    public void addTime(Long time){
        timeSpended += time;
    }

    public Long getTimeRest(){
        return newTR;
    }

    public void addTimeR(Long time){
        timeSpended += time;
    }

    }






