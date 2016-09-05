package com.mikaela.hhtimer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mikaela.hhtimer.service.CoreService;


public class MainActivity extends AppCompatActivity implements Study.OnFragmentInteractionListener, Rest.OnFragmentInteractionListener {
    static boolean active = false;
    private long studySessionTime = 16000;
    private long breakSessionTime = 80000;
    private long totalTime = 2000*60*60;
    public int timeSpended = 0;
    GifView gifView;
    Button menubtn;
    String MyPrefrences = "TimerValues";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTimeFromMenu();
        setupTimerPage();

        menubtn = (Button) findViewById(R.id.menubtn);
        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getTimeFromMenu(){
        SharedPreferences sharedpreferences = getSharedPreferences(MyPrefrences, Context.MODE_PRIVATE);
        if( sharedpreferences != null) {
            studySessionTime = sharedpreferences.getLong("studySession", 50 * 60000);
            breakSessionTime = sharedpreferences.getLong("breakSession", 10 * 60000);
            totalTime = sharedpreferences.getLong("totalTime", 120 * 60000);
        }
    }

    public void setupTimerPage(){
        gifView = (GifView) (findViewById(+R.id.gif_view));
        FragmentManager fragmentManager = getSupportFragmentManager();
        Study study = new Study();
        fragmentManager.beginTransaction().replace(R.id.mainContainer, study).commit();
    }

    public Long getTime(){
        return studySessionTime;
    }

    public void addTime(Long time){
        timeSpended += time;
        if(timeSpended >= totalTime){
            endOfTotalTime();
        }
    }

    public Long getTimeRest(){
        return breakSessionTime;
    }

    public void addTimeR(Long time){
        timeSpended += time;
        if(timeSpended >= totalTime){
            endOfTotalTime();
        }
    }

    private void endOfTotalTime(){
        //run popup and end appblock
        Intent intent = new Intent(this, PopDoneStudy.class);
        startActivity(intent);
        getApplicationContext().stopService(new Intent(this, CoreService.class));
    }


}






