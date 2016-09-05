package com.mikaela.hhtimer;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

import com.mikaela.hhtimer.service.CoreService;

import java.util.concurrent.TimeUnit;

public class Study extends Fragment {
    private OnFragmentInteractionListener Lis;
    Button start;
    TextView text;
    long startT = 0;
    String MyPrefrences = "TimerValues";

public interface OnFragmentInteractionListener{
        public Long getTime();
        public void addTime(Long am);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            Lis = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }
//TODO make fragment use sharedPrefrences instead of using interface
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.studytime, container, false);

        start = (Button) view.findViewById(R.id.RedButton);
        text = (TextView) view.findViewById(R.id.RedTextTime);

        startT = Lis.getTime();
        setTimerText();

        start.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View v) {
                                         new CountDownTimer(startT, 1000) {


                                             public void onTick(long millisUntilFinished) {
                                                 configureTimerOn(millisUntilFinished);
                                                 //App block starts
                                                 getActivity().startService(new Intent(getActivity(), CoreService.class));
                                             }

                                             public void onFinish() {
                                                 makePhoneVibrate();
                                                 Lis.addTime(startT);
                                                 putInFG();
                                                 startBreak();

                                                 NotificationCompat.Builder mBuilder =
                                                         new NotificationCompat.Builder(getContext()) //Added getContext(), was an error before, assumed it was supposed o be there /A
                                                                 .setSmallIcon(R.drawable.e)
                                                                 .setContentTitle("My notification")
                                                                 .setContentText("Hello World!");

                                                 //App block stops
                                                 //getActivity().stopService(new Intent(getActivity(), CoreService.class));
                                             }
                                         }.start();
                                     }
                                 }
        );
        return view;
    }

    private void configureTimerOn(long timeUntililFinished){
        start.setClickable(false);
        start.setVisibility(View.INVISIBLE);
        text.setText("" + String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(timeUntililFinished),
                TimeUnit.MILLISECONDS.toSeconds(timeUntililFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeUntililFinished))));
    }

    public void makePhoneVibrate(){
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);
    }

    public void putInFG(){
        Intent intent = new Intent(getActivity(), NotificationClass.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void startBreak(){
        FragmentManager fragmentManager = getFragmentManager();
        Rest rest = new Rest();
        try {
            fragmentManager.beginTransaction().replace(R.id.mainContainer, rest).commit();
        } catch (IllegalStateException e) {
            fragmentManager.beginTransaction().replace(R.id.mainContainer, rest).commitAllowingStateLoss();
        }
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





