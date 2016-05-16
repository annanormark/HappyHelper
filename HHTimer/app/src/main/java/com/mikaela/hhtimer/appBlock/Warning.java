package com.mikaela.hhtimer.appBlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.mikaela.hhtimer.R;
//import android.widget.TextView;

public class Warning extends AppCompatActivity {

    //private TextView warning_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_warning);

        //  warning_text = (TextView) findViewById(R.id.warningText);

    }
}
