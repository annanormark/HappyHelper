package com.mikaela.hhtimer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;

import java.util.Date;
import java.util.List;

public class Calendar extends AppCompatActivity {
    dbHandler datesOfDeadLines;
    Date selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final Context context = getApplicationContext();
        final TextView tex = (TextView) findViewById(R.id.annasText);
        final CompactCalendarView Cal = (CompactCalendarView) findViewById(R.id.Cal);

        assert tex != null;
        tex.setText("");


        datesOfDeadLines = new dbHandler(this, null, null,1);

        //Makes the events show in the interface as dots when starting
        assert Cal != null;
        Cal.drawSmallIndicatorForEvents(true);
        List<Long> dates = datesOfDeadLines.getDates();
        for (int i = 0; i <dates.size(); i++){
            Cal.addEvent(new CalendarDayEvent(dates.get(i), Color.RED), false);
        }
        Cal.invalidate();



        /***********Sets listener for what happens when a new date is clicked in the calendar***************/
        Cal.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                ListView eventList = (ListView) findViewById(R.id.eventList);
                selectedDate = dateClicked;

                tex.setText("" + selectedDate.getDate() + "/" + selectedDate.getMonth() + " ");
                ListAdapter adapter = new customAdapter(getBaseContext(), datesOfDeadLines.getTitles(dateClicked.getTime()));
                assert eventList != null;
                eventList.setAdapter(adapter);


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //  actionBar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });


    }

    /*************** clicked button "add event", starts activity "makeDeadLine" *****************/
    public void addDeadLine(View view){
        Context context = getApplicationContext();
        if (selectedDate==null) {
            Toast.makeText(context, "Select a date", Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(this, makeDeadLine.class);
        i.putExtra("Time", selectedDate);
        startActivity(i);

    }
}
