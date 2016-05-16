package com.mikaela.hhtimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity {

    Button about;
    Button timerbtn;
    Button calendarBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        about = (Button) findViewById(R.id.Aboutbtn);
        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, About.class);
                startActivity(i);
            }


        });

        timerbtn = (Button) findViewById(R.id.TimerBtn);
        timerbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, Timer.class);
                startActivity(i);
            }


        });

        calendarBtn = (Button) findViewById(R.id.CalendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, Calendar.class);
                startActivity(i);
            }
        });




    }
}
