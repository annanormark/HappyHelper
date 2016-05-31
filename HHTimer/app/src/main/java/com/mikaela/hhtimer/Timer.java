package com.mikaela.hhtimer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.String;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class Timer extends AppCompatActivity  {

    Button setButton;
    SeekBar studySession;
    SeekBar breakSession;
    SeekBar totalTime;
    private GoogleApiClient client;
    String MyPrefrences = "TimerValues";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        studySession = (SeekBar) findViewById(R.id.StudyTime);
        breakSession = (SeekBar) findViewById(R.id.PauseTime);
        totalTime = (SeekBar) findViewById(R.id.TotalTime);
        setButton = (Button) findViewById(R.id.setBtn);

        /*** SharedPrefrences setup ***/
        SharedPreferences sharedpreferences = getSharedPreferences(MyPrefrences, Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit = sharedpreferences.edit();

        /*** Numbers shown next to seekbar ***/
        final TextView studyNumber = (TextView) findViewById(R.id.StudySNumber);
        final TextView breakNumber = (TextView) findViewById(R.id.BreakSNumber);
        final TextView totalNumber = (TextView) findViewById(R.id.StudyNumber);

        /*** Sets progressed value in seekbar ***/
        long studySes =  (sharedpreferences.getLong("studySession", 50*60000 + 1000) - 1000)/ 60000;
        long breakSes =  (sharedpreferences.getLong("breakSession", 10*60000 + 1000) - 1000)/ 60000;
        long total =  (sharedpreferences.getLong("totalTime", 120*60000 + 1000) - 1000) / 60000;
        
        studySession.setProgress((int) studySes);
        breakSession.setProgress((int) breakSes);
        totalTime.setProgress((int) total);
        studyNumber.setText("" + studySes + " min");
        breakNumber.setText("" + breakSes + " min");
        totalNumber.setText("" + total + " min");


        /*** Sets seekbars ***/
        studySession.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                studyNumber.setText(progress + " min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        breakSession.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                breakNumber.setText(progress + " min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        totalTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                totalNumber.setText(progress + " min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        setButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*** Gets number from user input ***/
                String studyString = studyNumber.getText().toString();
                long sst = Long.parseLong(studyString.substring(0, studyString.length() - 4)) * 60000 + 1000;
                String breakString = breakNumber.getText().toString();
                long bst = Long.parseLong(breakString.substring(0, breakString.length() - 4)) * 60000 + 1000;
                String totaltString = totalNumber.getText().toString();
                long tt = Long.parseLong(totaltString.substring(0, totaltString.length() - 4)) * 60000 + 1000;

                /*** Adds user input to DB ***/
                edit.putLong("studySession", sst);
                edit.putLong("breakSession", bst);
                edit.putLong("totalTime", tt);
                edit.commit();
                
                /*** go to start-page ***/
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Timer Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.mikaela.hhtimer/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Timer Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.mikaela.hhtimer/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
