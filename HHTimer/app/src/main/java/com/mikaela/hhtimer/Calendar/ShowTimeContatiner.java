package com.mikaela.hhtimer.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.mikaela.hhtimer.R;
import com.mikaela.hhtimer.Database.dbHandler;

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
        studyAssignmentAdapter itemList = new studyAssignmentAdapter(this, eventlist, datesOfDeadLines.getWorkRelatedTitles());
        timeTable.setAdapter(itemList);

    }

}
