package com.mikaela.hhtimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

public class ShowTimeContatiner extends AppCompatActivity {
    dbHandler datesOfDeadLines;
    private static final String COLUMN_WORK_PER_DAY = "work_per_day";
    private static final String COLUMN_PRODUCTNAME = "productname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time_contatiner);

        ListView timeTable = (ListView) findViewById(R.id.timeTable);
        datesOfDeadLines = new dbHandler(this, null, null, 1);
        String[] eventlist = datesOfDeadLines.eventsToArray();
        timeListItem itemList = new timeListItem(this, eventlist, datesOfDeadLines.getWorkRelatedTitles());
        timeTable.setAdapter(itemList);

    }

}
