package com.mikaela.hhtimer.appBlock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.mikaela.hhtimer.R;

public class Why extends AppCompatActivity {

    Button ok_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        ok_button = (Button) findViewById(R.id.ok_button);

        getWindow().setLayout(width, height);

        Button close = (Button) findViewById(R.id.ok_button);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

    }


}
