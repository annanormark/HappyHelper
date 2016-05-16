package com.mikaela.hhtimer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mikaela on 16-05-09.
 */
public class Pop extends Activity {

    Button okbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Random rand = new Random();
        int randbak = rand.nextInt(3);

        switch (randbak){
            case 0:  setContentView(R.layout.popup);
                break;
            case 1: setContentView(R.layout.pop2);
                break;
            case 2: setContentView(R.layout.pop1);
                break;
        }





        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        okbtn = (Button) findViewById(R.id.startBtn);

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.7));

        Button close = (Button) findViewById(R.id.okbtn);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
    }
}

