package com.mikaela.hhtimer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.concurrent.TimeUnit;


public class Rest extends Fragment {



    Button start;
    TextView text;
    long startT = 6000;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.resttime, container, false);

        start = (Button) view.findViewById(R.id.startBtn);
        text = (TextView) view.findViewById(R.id.textTime);

        long minutes = (int) ((startT / (1000*60)) % 60);
        long seconds = (int) (startT / 1000) % 60 ;


        text.setText("0" + minutes + ":0" + seconds);

        start.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View v) {


                                         new CountDownTimer(startT, 1000) {


                                             public void onTick(long millisUntilFinished) {
                                                 start.setClickable(false);
                                                 start.setVisibility(View.INVISIBLE);
                                                 //long remindSecs = millisUntilFinished / (long) 1000;
                                                 text.setText("" + String.format("%02d:%02d",
                                                         TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                                         TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                                 TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                             }

                                             public void onFinish() {
                                                 Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                                 v.vibrate(1000);

                                                 FragmentManager fragmentManager = getFragmentManager();
                                                 Study study = new Study();
                                                 fragmentManager.beginTransaction().replace(R.id.mainContainer, study).commit();

                                             }
                                         }.start();


                                     }

                                 }

        );

        return view;

    }
}