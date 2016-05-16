package com.mikaela.hhtimer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ExpandableListView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    GifView gifView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gifView = (GifView) findViewById(+R.id.gif_view);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Study study = new Study();
        fragmentManager.beginTransaction().replace(R.id.mainContainer, study).commit();

    }

}
