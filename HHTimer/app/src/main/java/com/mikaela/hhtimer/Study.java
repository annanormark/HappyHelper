package com.mikaela.hhtimer;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

import java.util.concurrent.TimeUnit;


public class Study extends Fragment {
    private OnFragmentInteractionListener Lis;
    Button start;
    TextView text;
    long startT = 0;

public interface OnFragmentInteractionListener{
        public Long getTime();
        public void addTime(Long am);
    }


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     /*   //1
      Intent i = getIntent();
        long intValue = i.getIntExtra("Minutes", 0);
        intValue = startT;

        //2
        Bundle savedInstanceState) {
            String strtext = getArguments().getString("edttext");
            return inflater.inflate(R.layout.fragment, container, false);
*/


        View view = inflater.inflate(R.layout.studytime, container, false);

        start = (Button) view.findViewById(R.id.RedButton);
        text = (TextView) view.findViewById(R.id.RedTextTime);
       // start.setVisibility(View.INVISIBLE);

       long minutes = (int) ((startT / (1000*60)) % 60);
        long seconds = (int) (startT / 1000) % 60 ;

        startT = Lis.getTime();
        //text.setText("0" + minutes + ":0" + seconds);
        setTimerText();

        start.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View v) {
                                         new CountDownTimer(startT, 1000) {


                                             public void onTick(long millisUntilFinished) {
                                                 start.setClickable(false);
                                                 start.setVisibility(View.INVISIBLE);
                                                 //long remindSecs = millisUntilFinished / (long) 1000;
                                                 //setTimerText(millisUntilFinished);
                                                 text.setText("" + String.format("%02d:%02d",
                                                         TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                                         TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                                 TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                             }

                                             public void onFinish() {

                                                 Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                                 v.vibrate(1000);

                                                 FragmentManager fragmentManager = getFragmentManager();
                                                 Rest rest = new Rest();
                                                 fragmentManager.beginTransaction().replace(R.id.mainContainer, rest).commit();

                                                 Lis.addTime(startT);

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





