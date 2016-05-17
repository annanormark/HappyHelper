package com.mikaela.hhtimer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private OnFragmentInteractionListener Lis;

    public interface OnFragmentInteractionListener{
        public Long getTimeRest();
        public void addTimeR(Long am);
    }


    Button start;
    TextView text;
    long startT = 6000;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            Lis = (OnFragmentInteractionListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.resttime, container, false);

        start = (Button) view.findViewById(R.id.startBtn);
        text = (TextView) view.findViewById(R.id.textTime);

        long minutes = (int) ((startT / (1000*60)) % 60);
        long seconds = (int) (startT / 1000) % 60 ;

        startT = Lis.getTimeRest();
        //text.setText("0" + minutes + ":0" + seconds);
        setTimerText();



        start.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View v) {

                                         Intent intent = new Intent(getActivity(), Pop.class);
                                         startActivity(intent);


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

                                                 /*** Makes phone vibrate ***/
                                                 Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                                 v.vibrate(1000);
                                                 Lis.addTimeR(startT);

                                                 /*** Makes application appear in foreground ***/
                                                 Intent intent = new Intent(getActivity(), NotificationClass.class);
                                                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                 startActivity(intent);

                                                 /*** Changes to study-session ***/
                                                 FragmentManager fragmentManager = getFragmentManager();
                                                 Study study = new Study();
                                                 try {
                                                     fragmentManager.beginTransaction().replace(R.id.mainContainer, study).commit();
                                                 }catch(IllegalStateException e){
                                                     fragmentManager.beginTransaction().replace(R.id.mainContainer, study).commitAllowingStateLoss();
                                                 }

                                             }
                                         }.start();


                                     }

                                 }

        );

        return view;

    }

    private void setTimerText(){
        long minutes = (int) ((startT / (1000*60)) % 60);
        long seconds = (int) (startT / 1000) % 60 ;

        text.setText("0" + minutes + ":0" + seconds);


    }
    public  void setTimer(int time){
        this.startT = time;
        setTimerText();
    }
}