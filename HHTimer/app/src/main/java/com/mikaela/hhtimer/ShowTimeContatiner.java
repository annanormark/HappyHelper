package com.mikaela.hhtimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ShowTimeContatiner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time_contatiner);

        ListView timeTable = (ListView) findViewById(R.id.timeTable);
    }

}
