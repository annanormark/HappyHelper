package com.mikaela.hhtimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.mikaela.hhtimer.service.CoreService;

public class PopDoneStudy extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pop_done_study);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width), (int) (height));


        Button close = (Button) findViewById(R.id.appGo);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //App block stops
                finish();
            }
        });
    }
}
